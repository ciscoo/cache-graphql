package io.mateo.graphql.cacherequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration(proxyBeanMethods = false)
public class WebClientsConfiguration {

    @Bean
    public WebClient customerInfoWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl("https://jsonplaceholder.typicode.com").build();
    }

}
