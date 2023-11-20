package org.springframework.samples.petclinic.graphql.runtime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.graphql.server.webmvc.GraphiQlHandler;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class GraphiQlConfiguration implements WebMvcConfigurer {

    @Bean
    @Order(0)
    public RouterFunction<ServerResponse> graphiQlRouterFunction(@Autowired GraphQlProperties graphQlProperties) {
        RouterFunctions.Builder builder = RouterFunctions.route();
        ClassPathResource graphiQlPage = new ClassPathResource("/ui/graphiql/index.html");
        GraphiQlHandler graphiQLHandler = new GraphiQlHandler(
            graphQlProperties.getPath(),
            graphQlProperties.getWebsocket().getPath(),
            graphiQlPage
        );
        builder = builder.GET("/", graphiQLHandler::handleRequest);
        return builder.build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/graphiql/**")
            .addResourceLocations("classpath:/ui/graphiql/");
    }
}
