package com.intuit.cg.backendtechassessment.util;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	/*
	 * Generate unique request id for each request. It will be helpful to track each request that is traveling through different 
	 * micro services.
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new HandlerInterceptorAdapter() {
			private final Logger log = Logger.getLogger(WebConfig.class);
			@Override
			public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
				try {
					String requestUUID = request.getHeader("requestUUID");	
					if(requestUUID == null) {
						requestUUID = request.getParameter(requestUUID);
						if((requestUUID == null) || requestUUID.trim().equals(""))
							requestUUID = UUID.randomUUID().toString().toUpperCase();
					}
					MDC.put("requestUUID", requestUUID);
					MDC.put("requestUUIDLogStr", new StringBuilder("requestUUID=").append(requestUUID));
				}
				catch(Exception exp) {
					log.error("Unable to create requestUUID " + exp.getMessage());
				}
				
				return true;
			}
				
		}).addPathPatterns("/**");

	}
}
