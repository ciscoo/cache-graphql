package io.mateo.graphql.cacherequest.service;

import io.mateo.graphql.cacherequest.model.CustomerInfo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class CustomerInfoService {

    private final ParameterizedTypeReference<Map<String, Object>> MAP_TYPE = new ParameterizedTypeReference<>() {};

    private final WebClient client;

    public CustomerInfoService(WebClient customerInfoWebClient) {
        this.client = customerInfoWebClient;
    }

    public Mono<CustomerInfo> getCustomerInfo(int id) {
        return this.client.get().uri("/users/{id}", id)
                .retrieve()
                .bodyToMono(MAP_TYPE)
                .map(map -> {
                    var parts = map.get("name").toString().split(" ");
                    var info = new CustomerInfo();
                    info.setFirstName(parts[0]);
                    info.setLastName(parts[1]);
                    return info;
                });
    }
}
