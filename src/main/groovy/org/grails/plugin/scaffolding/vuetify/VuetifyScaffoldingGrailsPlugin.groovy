package org.grails.plugin.scaffolding.vuetify

import grails.plugins.Plugin
import org.grails.plugin.scaffolding.vuetify.markup.VuetifyContextMarkupRenderer
import org.grails.plugin.scaffolding.vuetify.markup.VuetifyPropertyMarkupRenderer
import org.grails.plugin.scaffolding.vuetify.registry.VuetifyDomainRendererRegisterer
import org.grails.plugin.scaffolding.vuetify.template.VuetifyModuleEditorImpl
import org.grails.scaffolding.ScaffoldingBeanConfiguration

class VuetifyScaffoldingGrailsPlugin extends Plugin {


    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.3.0 > *"

    // TODO Fill in these fields
    def title = "Grails Vuetify Scaffolding" // Headline output name of the plugin
    def author = "Eric Helgeson"
    def authorEmail = "erichelgeson@gmail.com"
    def description = '''\
This plugin provides the ability to generate an Vuetify CRUD components based on a domain class
'''
    String documentation = 'http://grails-plugins.github.io/grails-vuetify-scaffolding/latest'
    String license = 'APACHE'
    def organization = [name: 'Grails', url: 'http://www.grails.org/']
    def issueManagement = [url: 'https://github.com/grails-plugins/grails-vuetify-scaffolding/issues']
    def scm = [url: 'https://github.com/grails-plugins/grails-vuetify-scaffolding']

    Closure doWithSpring() {{ ->
        scaffoldingCoreConfig(ScaffoldingBeanConfiguration)

        propertyMarkupRenderer(VuetifyPropertyMarkupRenderer)

        contextMarkupRenderer(VuetifyContextMarkupRenderer)

        angularModuleEditor(VuetifyModuleEditorImpl)

        angularDomainRendererRegisterer(VuetifyDomainRendererRegisterer)
    }}
}
