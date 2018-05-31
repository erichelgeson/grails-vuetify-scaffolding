package org.grails.plugin.scaffolding.vuetify.template

import grails.codegen.model.Model

interface VuetifyModuleEditor {

    boolean addModuleImport(File module, Model model)

    boolean addModuleProvider(File module, Model model)

    boolean addImport(File module, String className, String path, String relativeDir)

    boolean addDependency(File module, Model model, String relativeDir)

    boolean addProvider(File module, Model model, String relativeDir)

}