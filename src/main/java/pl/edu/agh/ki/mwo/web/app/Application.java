package pl.edu.agh.ki.mwo.web.app;


import javax.annotation.PreDestroy;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import pl.edu.agh.ki.mwo.persistence.DatabaseConnector;

@SpringBootApplication
@ComponentScan(basePackages = "pl.edu.agh.ki.mwo.web.controllers")
public class Application {
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @PreDestroy
    public void stop() {
       DatabaseConnector.getInstance().teardown();
    }    
    
}