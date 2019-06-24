package com.nelioalves.cursomc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nelioalves.cursomc.domain.PagamentoBoleto;
import com.nelioalves.cursomc.domain.PagamentoCartao;

@Configuration
public class JacksonConfig {

	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			public void configure(ObjectMapper objectMapper) {
				
				objectMapper.registerSubtypes(PagamentoCartao.class, PagamentoBoleto.class);
				super.configure(objectMapper);
			}
		};
		
		return builder;
	}
}
