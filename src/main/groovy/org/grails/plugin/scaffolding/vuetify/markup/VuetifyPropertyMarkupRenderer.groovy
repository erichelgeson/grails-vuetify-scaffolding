package org.grails.plugin.scaffolding.vuetify.markup

import grails.util.GrailsNameUtils
import org.grails.scaffolding.markup.PropertyMarkupRendererImpl
import org.grails.scaffolding.model.property.DomainProperty

class VuetifyPropertyMarkupRenderer extends PropertyMarkupRendererImpl {

    @Override
    Map getStandardAttributes(DomainProperty property) {
        final String objectName = GrailsNameUtils.getPropertyName(property.rootBeanType)
        Map attributes = super.getStandardAttributes(property)
        attributes["[(ngModel)]"] = "$objectName.${attributes.name}"
        attributes
    }
}
