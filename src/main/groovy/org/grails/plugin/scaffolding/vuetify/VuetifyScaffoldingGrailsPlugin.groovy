package org.grails.plugin.scaffolding.vuetify

import grails.plugins.Plugin
import org.grails.plugin.scaffolding.angular.markup.AngularContextMarkupRenderer
import org.grails.plugin.scaffolding.angular.markup.AngularPropertyMarkupRenderer
import org.grails.plugin.scaffolding.angular.registry.AngularDomainRendererRegisterer
import org.grails.plugin.scaffolding.angular.template.AngularModuleEditorImpl
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

        propertyMarkupRenderer(AngularPropertyMarkupRenderer)

        contextMarkupRenderer(AngularContextMarkupRenderer)

        angularModuleEditor(AngularModuleEditorImpl)

        angularDomainRendererRegisterer(AngularDomainRendererRegisterer)
    }}
}
