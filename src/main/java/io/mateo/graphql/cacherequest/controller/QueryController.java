package io.mateo.graphql.cacherequest.controller;

import io.mateo.graphql.cacherequest.model.Todo;
import io.mateo.graphql.cacherequest.model.TodoIdentifierInput;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class QueryController {

    @QueryMapping("todo")
    public Mono<Todo> todo(@Argument TodoIdentifierInput input) {
        var todo = new Todo();
        todo.setId(input.getId());
        return Mono.just(todo);
    }

}
