package org.grails.plugin.scaffolding.vuetify.registry.output

import org.grails.scaffolding.model.property.DomainProperty

/**
 * Created by Jim on 5/25/2016.
 */
class VuetifyDateOutputRenderer extends VuetifyDomainOutputRenderer {

    @Override
    boolean supports(DomainProperty property) {
        property.type in [Date, Calendar, java.sql.Date]
    }

    @Override
    protected Closure renderOutput(String propertyName, String propertyPath) {
        { ->
            span("{{${propertyPath} | date:'yyyy-MM-dd HH:mm:ss Z'}}")
        }
    }
}
