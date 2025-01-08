package dev.springharvest.expressions.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import graphql.scalars.ExtendedScalars;

/**
 * This class is used to configure GraphQL in the application
 *
 * @author Gilles Djawa (NeroNemesis)
 */
@Configuration
public class GraphQLConfig {

    /**
     * Configures the runtime wiring for GraphQL
     *
     * @return the runtime wiring configurer
     */
    @Bean
    RuntimeWiringConfigurer runtimeWiringConfigurer(){
        return wiringBuilder ->
                wiringBuilder.scalar(ExtendedScalars.Date) // Adding Date scalar
                        .scalar(ExtendedScalars.Json) // Adding Json scalar
                        .scalar(ExtendedScalars.GraphQLLong) // Adding long scalar
                        .scalar(ExtendedScalars.Object); // Adding object scalar
    }
}