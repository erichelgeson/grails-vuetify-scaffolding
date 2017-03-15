package org.grails.plugin.scaffolding.angular2.template

import grails.codegen.model.Model
import groovy.json.JsonException
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j

import java.util.regex.Matcher

@Slf4j
class AngularModuleEditorImpl implements AngularModuleEditor {

    @Override
    boolean addModuleImport(File module, Model model) {
        String jsonString
        String moduleName = getModuleName(model)
        try {
            StringBuilder sb = new StringBuilder()
            final String moduleText = module.text

            Matcher group = (moduleText =~ /(?s)@NgModule(?:\s*)\((?:\s*)\{(?:.*?)}/)
            if (group.size() > 0) {
                final String moduleDefinition = group[0]
                Matcher imports = (moduleDefinition =~ /imports:(?:\s*?)\[/)
                if (imports.size() > 0) {
                    //unused on purpose.. has to initialize [0] before .start() works
                    final String importDefinition = imports[0]
                    int startingIndex = group.start() + imports.end() -1

                    sb.append(moduleText.substring(0, startingIndex))

                    String temp = moduleText.substring(startingIndex + 1)

                    //edit import array
                    int endingIndex = temp.indexOf(']')
                    //Remove trailing commas in the array
                    temp = temp.substring(0, endingIndex).replaceAll(/(\p{C}|\s)/, "")
                    //temp should contain the array contents without []
                    if (temp) {
                        jsonString = '["' + temp.split(',').join('","') + '"]'
                        def json = new JsonSlurper().parseText(jsonString)
                        if (!json.contains(moduleName)) {
                            json.add(moduleName)
                            jsonString = JsonOutput.prettyPrint(JsonOutput.toJson(json))
                        } else {
                            return true
                        }
                    } else {
                        jsonString = JsonOutput.prettyPrint(JsonOutput.toJson(new JsonSlurper().parseText("[\"${moduleName}\"]")))
                    }
                    sb.append(jsonString.replace('"', ''))
                    sb.append(moduleText.substring(startingIndex+endingIndex+2))
                    module.write(sb.toString())
                    true
                } else {
                    if ((moduleDefinition =~ /imports/).size() == 0) {
                        //create new imports array
                        Matcher moduleConfigMatcher = (moduleDefinition =~ /(?s)\{(\s*?)([a-z]*?):(\s*?)\[(.*?)](,?)(\s*?)}/)
                        int startingIndex = 0
                        def ls = System.getProperty('line.separator')
                        if (moduleConfigMatcher.size() > 0) {
                            //other config keys exist, need to append
                            int lastIdx = moduleConfigMatcher.size() - 1
                            String moduleConfig = moduleConfigMatcher[lastIdx][0].replaceAll(/\{/,'').replaceAll(/(\s*?)}$/, '')
                            startingIndex = group.start() + moduleDefinition.indexOf(moduleConfig) + moduleConfig.size()
                            sb.append(moduleText.substring(0, startingIndex))
                            if (!moduleConfig.endsWith(',')) {
                                sb.append(',')
                            }
                        } else {
                            startingIndex = group.start() + moduleDefinition.indexOf('{') + 1
                            sb.append(moduleText.substring(0, startingIndex))
                        }
                        sb.append("${ls}imports: [${ls}    ${moduleName}${ls}]${ls}")
                        sb.append(moduleText.substring(startingIndex+1))
                        module.write(sb.toString())
                        true
                    } else {
                        false
                    }
                }
            } else {
                false
            }
        } catch (JsonException e) {
            log.error("Could not add $moduleName dependency to $module.name because $jsonString is not valid JSON", e)
            false
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            log.error("Could not add $moduleName dependency to $module.name because the import: dependency array could not be found", e)
            false
        }
    }

    @Override
    boolean addImport(File module, Model model, String relativeDir = '.') {
        String importStatement = "import { ${getModuleName(model)} } from '${relativeDir}/${model.propertyName}/${model.propertyName}.module';"
        try {
            String moduleText = module.text
            Matcher group = (moduleText =~ /import .*(?:\r\n|\n|\r)?/)

            if (group.any { it =~ /import\s*\{\s*${getModuleName(model)}/ }) {
                return true
            }
            int index = 0
            StringBuilder sb = new StringBuilder()
            if (group.size() > 0) {
                int last = group.size() - 1
                //So group.end() will resolve to the last group
                group[last]
                index = group.end()
                sb.append(moduleText.substring(0, index))
            }
            sb.append(importStatement + System.getProperty("line.separator"))
            sb.append(moduleText.substring(index))
            module.write(sb.toString())
            true
        } catch (Exception e) {
            log.error("Could not add import statement: (${importStatement}) to the AppModule", e)
            false
        }
    }

    private String getModuleName(Model model) {
        "${model.className}Module"
    }

    @Override
    boolean addDependency(File module, Model model, String relativeDir = '.') {
        String originalText = module.text
        if (addModuleImport(module, model) && addImport(module, model, relativeDir)) {
            true
        } else {
            module.write(originalText)
            false
        }
    }
}
