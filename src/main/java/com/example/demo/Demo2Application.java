package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zkoss.zk.au.http.DHtmlUpdateServlet;
import org.zkoss.zk.ui.http.DHtmlLayoutServlet;
import org.zkoss.zk.ui.http.HttpSessionListener;

@SpringBootApplication
@Configuration
public class Demo2Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo2Application.class, args);
    }
	@Bean
	public ServletRegistrationBean zkLoader() {
		ServletRegistrationBean zkLoader = new ServletRegistrationBean(new DHtmlLayoutServlet(), "*.zul", "*.zhtml");
		zkLoader.getInitParameters().put("update-uri", "/zkau");
		return zkLoader;
	}

	@Bean
	public ServletRegistrationBean auEngine() {
		ServletRegistrationBean auEngine = new ServletRegistrationBean(new DHtmlUpdateServlet(), "/zkau/*");
		return auEngine;
	}

	@Bean
	public HttpSessionListener httpSessionListener() {
		return new HttpSessionListener();
	}
}
