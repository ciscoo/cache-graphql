package io.mateo.graphql.cacherequest.controller;

import io.mateo.graphql.cacherequest.model.Customer;
import io.mateo.graphql.cacherequest.model.CustomerInfo;
import io.mateo.graphql.cacherequest.service.CustomerInfoService;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@SchemaMapping(typeName = "Customer")
@Controller
public class CustomerController {

    private final CustomerInfoService customerInfoService;

    public CustomerController(CustomerInfoService customerInfoService) {
        this.customerInfoService = customerInfoService;
    }

    @SchemaMapping
    public Mono<CustomerInfo> info(Customer customer) {
        return this.customerInfoService.getCustomerInfo(customer.getId());
    }
}
