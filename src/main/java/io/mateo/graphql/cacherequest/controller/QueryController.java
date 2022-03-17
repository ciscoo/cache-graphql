package io.mateo.graphql.cacherequest.controller;

import io.mateo.graphql.cacherequest.model.AltogetherCustomer;
import io.mateo.graphql.cacherequest.model.Customer;
import io.mateo.graphql.cacherequest.model.CustomerIdentifierInput;
import io.mateo.graphql.cacherequest.model.IdealCustomer;
import io.mateo.graphql.cacherequest.service.CustomerInfoService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class QueryController {

    private final CustomerInfoService customerInfoService;

    public QueryController(CustomerInfoService customerInfoService) {
        this.customerInfoService = customerInfoService;
    }

    @QueryMapping
    public Mono<Customer> customer(@Argument CustomerIdentifierInput input) {
        var customer = new Customer();
        customer.setId(input.getId());
        return Mono.just(customer);
    }

    @QueryMapping
    public Mono<IdealCustomer> idealCustomer(@Argument CustomerIdentifierInput input) {
        var customer = new IdealCustomer();
        customer.setId(input.getId());
        return Mono.just(customer);
    }

    @QueryMapping
    public Mono<AltogetherCustomer> altogetherCustomer(@Argument CustomerIdentifierInput input) {
        return this.customerInfoService.getCustomerInfo(input.getId())
                .map(info -> {
                    var customer = new AltogetherCustomer();
                    customer.setId(input.getId());
                    customer.setInfo(info);
                    return customer;
                });
    }

}
