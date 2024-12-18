package com.sks.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ReactiveHttpInputMessage;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {
	private static final Logger logger = LoggerFactory.getLogger(GlobalFilter.class);
	private StopWatch stopWatch;
	
	public GlobalFilter() {
		super(Config.class);
//		stopWatch = new StopWatch("Gateway");
	}

	@Override
	public GatewayFilter apply(Config config) {
		// 람다 표현식을 사용하지 않고 구현
		return new GatewayFilter() {
			@Override
			public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
				ServerHttpRequest request = exchange.getRequest();
				ServerHttpResponse response = exchange.getResponse();
		        if (request.getMethod() == HttpMethod.OPTIONS) {
		        	response.setStatusCode(HttpStatus.OK);
		            return response.setComplete();
		        } else {
		        	
		        	
		        }
//				stopWatch.start();
				
//				logger.info("Request Headers: " + exchange.getRequest().getHeaders());
//				
//				logger.info("[글로벌필터] Request요청>>>>> IP : {}, URI : {}",request.getRemoteAddress().getAddress(), request.getURI());
//				
////				if (config.isEnabled() && exchange.getRequest().getHeaders().containsKey("X-Block")) {
////					exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
////					stopWatch.stop();
//					logger.info("[글로벌필터] Response 응답 >>>>> IP : {}, URI : {}, 응답코드 : {}, 처리시간 : {} ",
//							request.getRemoteAddress().getAddress(), 
//							request.getURI(),
//							response.getStatusCode(),
//							1
////							stopWatch.getTotalTime(TimeUnit.MILLISECONDS)
//							);
////					return exchange.getResponse().setComplete();
////				}
				// 다음 필터로 요청 전달
				return chain.filter(exchange);
			}
		};
	}

	
	// Config 클래스 정의
	public static class Config {
		private boolean enabled;

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}
	}
}
