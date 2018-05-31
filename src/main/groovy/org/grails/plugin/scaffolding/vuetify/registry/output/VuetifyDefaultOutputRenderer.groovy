package org.grails.plugin.scaffolding.vuetify.registry.output

import org.grails.scaffolding.model.property.DomainProperty

/**
 * Created by Jim on 5/25/2016.
 */
class VuetifyDefaultOutputRenderer extends VuetifyDomainOutputRenderer {

    @Override
    boolean supports(DomainProperty property) {
        true
    }

    @Override
    protected Closure renderOutput(String propertyName, String propertyPath) {
        { ->
            span("{{${propertyPath}}}")
        }
    }

}
