package com.weekone.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
    public class SwaggerConfiguration{

        //...

	springfox.documentation.service.Parameter authHeader = new ParameterBuilder()
			  .parameterType("header")
			  .name("Authorization")
			  .modelRef(new ModelRef("string"))
			  .build();
	
          @Bean
          public Docket api(ServletContext servletContext) {
            return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(Arrays.asList(apiKey()))
                .globalOperationParameters(Collections.singletonList(authHeader))
                .securityContexts(Collections.singletonList(securityContext()));
            
            
          }

          private springfox.documentation.spi.service.contexts.SecurityContext securityContext() {
            return springfox.documentation.spi.service.contexts.SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.regex("/.*")).build();
          }

          private List<SecurityReference> defaultAuth() {
            final AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
            final AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
            return Collections.singletonList(new SecurityReference("Bearer", authorizationScopes));
          }

          private ApiKey apiKey() {
            return new ApiKey("Bearer", "Authorization", "header");
          }
    } 