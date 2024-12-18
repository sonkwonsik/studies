package com.sks.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class SalesServiceFilter extends AbstractGatewayFilterFactory<SalesServiceFilter.Config> {
	private static final Logger logger = LoggerFactory.getLogger(SalesServiceFilter.class);
	private StopWatch stopWatch;
	
	public SalesServiceFilter() {
		super(Config.class);
		stopWatch = new StopWatch("Sales Service Gateway");
	}

	@Override
	public GatewayFilter apply(Config config) {
		config.setEnabled(true);
		// 람다 표현식을 사용하지 않고 구현
//		stopWatch.start();
		return new GatewayFilter() {
			@Override
			public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
				ServerHttpRequest request = exchange.getRequest();
				ServerHttpResponse response = exchange.getResponse();
				logger.info("[세일즈서비스필터] Request요청>>>>> IP : {}, URI : {}, 경로 : {} ",
						request.getRemoteAddress().getAddress(), 
						request.getURI(), 
						request.getMethod().toString() + request.getPath().toString());
				for (Entry<String, List<String>> header : request.getHeaders().entrySet()) {
				    logger.info("	[세일즈서비스필터]{} : {}:{}", this.getClass().getName(), header.getKey(), header.getValue());
				}
				
				
//				if (config.isEnabled() && exchange.getRequest().getHeaders().containsKey("X-Block")) {
//					exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
					logger.info("[세일즈서비스필터] Response 응답 >>>>> IP : {}, URI : {}, 응답코드 : {}, 처리시간 : {} ",
							request.getRemoteAddress().getAddress(), 
							request.getURI(),
							response.getStatusCode()
//							stopWatch.getTotalTime(TimeUnit.MILLISECONDS)
							);
					for (Entry<String, List<String>> header : response.getHeaders().entrySet()) {
					    logger.info("	[세일즈서비스필터]{} : {}:{}", this.getClass().getName(), header.getKey(), header.getValue());
					}
//					return exchange.getResponse().setComplete();
//				}
//				stopWatch.stop();
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
