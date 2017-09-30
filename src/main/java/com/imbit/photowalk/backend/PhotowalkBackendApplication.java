package com.imbit.photowalk.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@SpringBootApplication
@EnableSwagger2
public class PhotowalkBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotowalkBackendApplication.class, args);
	}


	@Bean
	public Docket generateDocumentation(){
		return new Docket(DocumentationType.SWAGGER_2).select().apis(basePackage("com.imbit.photowalk.backend.rest"))
				.build();
	}
}
