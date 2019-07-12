package com.pruqa.acmedemocompany.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    // ==== useful methods ====
    /**
     * Define which type of messages are accepted when consuming or producing the current APIs
     */
    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
            new HashSet<>(Collections.singletonList("application/json"));

    // ==== beans ====
    /**
     * Define the API rules that will apply to the Swagger UI
     *
     * @return docket
     */
    @Bean
    public Docket restApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.pruqa.acmedemocompany.controller"))
                .build()
                .apiInfo(getApiInfo())
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
                .globalOperationParameters(Arrays.asList(new ParameterBuilder()
                                .name("requestId")
                                .description("Id of the request")
                                .modelRef(new ModelRef("string"))
                                .parameterType("header")
                                .required(true)
                                .build(),
                        new ParameterBuilder()
                                .name("sessionId")
                                .description("Id of the session")
                                .modelRef(new ModelRef("string"))
                                .parameterType("header")
                                .required(true)
                                .build()));
    }

    /**
     * Define the information about the API provided
     *
     * @return apiInfo
     */
    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("Match Maker Company Server API")
                .description("\"Match maker tool\"")
                .version("0.0.1")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
                .contact(new Contact("Unknown", "https://www.paoloamosso.com", "email@gmail.com"))
                .build();
    }
}
