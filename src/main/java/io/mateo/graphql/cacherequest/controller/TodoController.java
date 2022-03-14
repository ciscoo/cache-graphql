package io.mateo.graphql.cacherequest.controller;

import io.mateo.graphql.cacherequest.model.Todo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@SchemaMapping(typeName = "Todo")
@Controller
public class TodoController {

    private static final ParameterizedTypeReference<Map<String, Object>> MAP_TYPE = new ParameterizedTypeReference<>() {};

    private static final String RESPONSE_ATTR = TodoController.class.getName() + ".todoResponse";

    private final WebClient todosWebClient;

    public TodoController(WebClient todosWebClient) {
        this.todosWebClient = todosWebClient;
    }

    @SchemaMapping(field = "title")
    public Mono<String> resolveTitle(Todo todo) {
        return Mono.deferContextual(contextView -> {
            Map<String, Object> cached = contextView.getOrDefault(RESPONSE_ATTR, null);
            if (cached != null) {
                return Mono.just(cached.get("title").toString());
            }
            return this.todosWebClient.get().uri("/todos/{id}", todo.getId())
                    .retrieve()
                    .bodyToMono(MAP_TYPE)
                    .flatMap(res -> Mono.just(res).contextWrite(context -> context.put(RESPONSE_ATTR, res)))
                    .flatMap(res -> Mono.just(res.get("title").toString()));
        });
    }

    @SchemaMapping(field = "completed")
    public Mono<Boolean> resolveCompleted(Todo todo) {
        return Mono.deferContextual(contextView -> {
            Map<String, Object> cached = contextView.getOrDefault(RESPONSE_ATTR, null);
            if (cached != null) {
                return Mono.just(Boolean.parseBoolean(cached.get("completed").toString()));
            }
            return this.todosWebClient.get().uri("/todos/{id}", todo.getId())
                    .retrieve()
                    .bodyToMono(MAP_TYPE)
                    .flatMap(res -> Mono.just(res).contextWrite(context -> context.put(RESPONSE_ATTR, res)))
                    .flatMap(res -> Mono.just(Boolean.parseBoolean(res.get("completed").toString())));
        });
    }

}
