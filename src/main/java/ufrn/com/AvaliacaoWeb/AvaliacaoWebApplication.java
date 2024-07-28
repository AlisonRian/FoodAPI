package ufrn.com.AvaliacaoWeb;

import ufrn.com.AvaliacaoWeb.domain.Comidas;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class AvaliacaoWebApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(AvaliacaoWebApplication.class, args);
		Comidas comidas = new Comidas();
	}


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Register resource handler for images
		registry.addResourceHandler("/images/**").addResourceLocations("file:./images/")
				.setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
		/*
		registry.addResourceHandler("/images/**").addResourceLocations("/images/")
		.setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());*/
	}
}
