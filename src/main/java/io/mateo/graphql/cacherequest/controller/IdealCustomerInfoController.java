package io.mateo.graphql.cacherequest.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.GraphQLContext;
import graphql.schema.DataFetchingEnvironment;
import io.mateo.graphql.cacherequest.model.CustomerInfo;
import io.mateo.graphql.cacherequest.model.IdealCustomer;
import io.mateo.graphql.cacherequest.service.CustomerInfoService;
import reactor.core.publisher.Mono;

@DgsComponent
public class IdealCustomerInfoController {

    private static final String RESPONSE_ATTR = IdealCustomerInfoController.class.getName() + ".todoResponse";

    private final CustomerInfoService customerInfoService;

    public IdealCustomerInfoController(CustomerInfoService customerInfoService) {
        this.customerInfoService = customerInfoService;
    }

    @DgsData.List(
            value = {
                    @DgsData(parentType = "IdealCustomer", field = "firstName"),
                    @DgsData(parentType = "IdealCustomer", field = "lastName")
            }
    )
    public Mono<String> resolveInfo(DataFetchingEnvironment environment) {
        return getFromContextOrGet(environment.getSource(), environment.getGraphQlContext())
                .map(info -> {
                    if (environment.getField().getName().equals("firstName")) {
                        return info.getFirstName();
                    }
                    if (environment.getField().getName().equals("lastName")) {
                        return info.getLastName();
                    }
                    throw new UnsupportedOperationException("Unsupported field: " + environment.getField().getName());
                });
    }


    private Mono<CustomerInfo> getFromContextOrGet(IdealCustomer customer, GraphQLContext context) {
        Object cached = context.get(RESPONSE_ATTR);
        if (cached != null) {
            return Mono.just((CustomerInfo) cached);
        }
        return this.customerInfoService.getCustomerInfo(customer.getId());
    }

}
