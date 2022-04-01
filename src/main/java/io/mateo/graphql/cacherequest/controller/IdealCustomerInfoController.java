package io.mateo.graphql.cacherequest.controller;

import graphql.GraphQLContext;
import io.mateo.graphql.cacherequest.model.Customer;
import io.mateo.graphql.cacherequest.model.CustomerInfo;
import io.mateo.graphql.cacherequest.model.IdealCustomer;
import io.mateo.graphql.cacherequest.service.CustomerInfoService;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@SchemaMapping(typeName = "IdealCustomer")
@Controller
public class IdealCustomerInfoController {

    private final CustomerInfoService customerInfoService;

    public IdealCustomerInfoController(CustomerInfoService customerInfoService) {
        this.customerInfoService = customerInfoService;
    }

    @SchemaMapping
    public Mono<String> firstName(IdealCustomer customer, GraphQLContext graphQLContext) {
        return getFromContextOrGet(customer, graphQLContext).map(CustomerInfo::getFirstName);
    }

    @SchemaMapping
    public Mono<String> lastName(IdealCustomer customer, GraphQLContext graphQLContext) {
        return getFromContextOrGet(customer, graphQLContext).map(CustomerInfo::getLastName);
    }

    private Mono<CustomerInfo> getFromContextOrGet(IdealCustomer customer, GraphQLContext context) {
        Object cached = context.get("customerInfo");
        if (cached != null) {
            return Mono.just((CustomerInfo) cached);
        }
        return this.customerInfoService.getCustomerInfo(customer.getId());
    }

}
