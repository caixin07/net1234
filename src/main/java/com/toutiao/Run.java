package com.toutiao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan
@EnableScheduling
public class Run {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Run.class, args);
	}

	@Bean
	public EmbeddedServletContainerFactory servletCon() {
		TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
		factory.setPort(8002);
		return factory;
	}
}
