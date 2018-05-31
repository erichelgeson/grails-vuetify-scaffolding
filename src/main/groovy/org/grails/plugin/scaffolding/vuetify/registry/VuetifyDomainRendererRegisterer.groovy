package org.grails.plugin.scaffolding.vuetify.registry

import org.grails.plugin.scaffolding.vuetify.registry.input.VuetifyAssociationInputRenderer
import org.grails.plugin.scaffolding.vuetify.registry.input.VuetifyBidirectionalToManyInputRenderer
import org.grails.plugin.scaffolding.vuetify.registry.input.VuetifyDateInputRenderer
import org.grails.plugin.scaffolding.vuetify.registry.input.VuetifyFileInputRenderer
import org.grails.plugin.scaffolding.vuetify.registry.output.*
import org.grails.scaffolding.registry.DomainInputRendererRegistry
import org.grails.scaffolding.registry.DomainOutputRendererRegistry
import org.springframework.beans.factory.annotation.Autowired
import javax.annotation.PostConstruct

class VuetifyDomainRendererRegisterer {

    @Autowired
    DomainInputRendererRegistry domainInputRendererRegistry

    @Autowired
    DomainOutputRendererRegistry domainOutputRendererRegistry

    @PostConstruct
    void registerRenderers() {

        domainInputRendererRegistry.registerDomainRenderer(new VuetifyAssociationInputRenderer(), 0)
        domainInputRendererRegistry.registerDomainRenderer(new VuetifyFileInputRenderer(), 0)
        domainInputRendererRegistry.registerDomainRenderer(new VuetifyBidirectionalToManyInputRenderer(), 0)
        domainInputRendererRegistry.registerDomainRenderer(new VuetifyDateInputRenderer(), 0)

        domainOutputRendererRegistry.registerDomainRenderer(new VuetifyDefaultOutputRenderer(), 0)
        domainOutputRendererRegistry.registerDomainRenderer(new VuetifyDateOutputRenderer(), 0)
        domainOutputRendererRegistry.registerDomainRenderer(new VuetifyIdOutputRenderer(), 0)
        domainOutputRendererRegistry.registerDomainRenderer(new VuetifyToManyOutputRenderer(), 0)
        domainOutputRendererRegistry.registerDomainRenderer(new VuetifyToOneOutputRenderer(), 0)
    }
}
