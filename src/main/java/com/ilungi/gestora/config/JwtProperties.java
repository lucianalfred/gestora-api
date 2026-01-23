package com.ilungi.gestora.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
	private String secret = "ilungi-key-private-gestora2026110211";
	private Long experation = 8640000L;
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public Long getExperation() {
		return experation;
	}
	public void setExperation(Long experation) {
		this.experation = experation;
	}
	
	
}
