package io.mateo.graphql.cacherequest.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import io.mateo.graphql.cacherequest.model.AltogetherCustomer;
import io.mateo.graphql.cacherequest.model.Customer;
import io.mateo.graphql.cacherequest.model.CustomerIdentifierInput;
import io.mateo.graphql.cacherequest.model.IdealCustomer;
import io.mateo.graphql.cacherequest.service.CustomerInfoService;
import reactor.core.publisher.Mono;

@DgsComponent
public class QueryController {

    private final CustomerInfoService customerInfoService;

    public QueryController(CustomerInfoService customerInfoService) {
        this.customerInfoService = customerInfoService;
    }

    @DgsQuery
    public Mono<Customer> customer(@InputArgument CustomerIdentifierInput input) {
        var customer = new Customer();
        customer.setId(input.getId());
        return Mono.just(customer);
    }

    @DgsQuery
    public Mono<IdealCustomer> idealCustomer(@InputArgument CustomerIdentifierInput input) {
        var customer = new IdealCustomer();
        customer.setId(input.getId());
        return Mono.just(customer);
    }

    @DgsQuery
    public Mono<AltogetherCustomer> altogetherCustomer(@InputArgument CustomerIdentifierInput input) {
        return this.customerInfoService.getCustomerInfo(input.getId())
                .map(info -> {
                    var customer = new AltogetherCustomer();
                    customer.setId(input.getId());
                    customer.setInfo(info);
                    return customer;
                });
    }

}
