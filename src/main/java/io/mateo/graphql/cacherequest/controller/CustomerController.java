package io.mateo.graphql.cacherequest.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;
import io.mateo.graphql.cacherequest.model.Customer;
import io.mateo.graphql.cacherequest.model.CustomerInfo;
import io.mateo.graphql.cacherequest.service.CustomerInfoService;
import reactor.core.publisher.Mono;

@DgsComponent
public class CustomerController {

    private final CustomerInfoService customerInfoService;

    public CustomerController(CustomerInfoService customerInfoService) {
        this.customerInfoService = customerInfoService;
    }

    @DgsData(parentType = "Customer")
    @DgsQuery
    public Mono<CustomerInfo> info(Customer customer) {
        return this.customerInfoService.getCustomerInfo(customer.getId());
    }
}
