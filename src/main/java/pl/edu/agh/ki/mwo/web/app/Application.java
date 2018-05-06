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
	
	//private int maxUploadSizeInMb = 10 * 1024 * 1024; // 10 MB

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @PreDestroy
    public void stop() {
       DatabaseConnector.getInstance().teardown();
    }
    
    //Tomcat large file upload connection reset
    //http://www.mkyong.com/spring/spring-file-upload-and-connection-reset-issue/
    /*@Bean
    public TomcatEmbeddedServletContainerFactory tomcatEmbedded() {

    	TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();

    	tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
    		if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
    			//-1 means unlimited
    			((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
    		}
    	});
        
    	return tomcat;
    }*/
    
}