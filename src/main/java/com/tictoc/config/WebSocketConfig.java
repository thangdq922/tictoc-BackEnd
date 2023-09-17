package com.tictoc.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.tictoc.auth.CusTomDetailService;
import com.tictoc.auth.jwt.JwtTokenProvider;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private CusTomDetailService customUserDetailsService;

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.setApplicationDestinationPrefixes("/app");
		config.enableSimpleBroker("/topic","/queue");
		config.setUserDestinationPrefix("/user");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(new MyChannelInterceptor());
	}

	public class MyChannelInterceptor implements ChannelInterceptor {

		@Override
		public Message<?> preSend(Message<?> message, MessageChannel channel) {
			StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
			if (StompCommand.CONNECT == accessor.getCommand()) {
				String token = getTokenFromRequest(accessor);
				if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
					String username = jwtTokenProvider.getUsername(token);
					UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					accessor.setUser(authenticationToken);
				}
			}
			return message;
		}

		private String getTokenFromRequest(StompHeaderAccessor accessor) {

			List<String> authorization = accessor.getNativeHeader("Authorization");
			logger.debug("Authorization: {}", authorization);

			String accessToken = authorization.get(0);
			if (StringUtils.hasText(accessToken) && accessToken.startsWith("Bearer ")) {
				return accessToken.substring(7, accessToken.length());
			} else {
				return null;
			}
		}
	}

}