package com.tictoc.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import com.tictoc.auth.MyUser;



@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfig {
	
	@Bean
	public AuditorAware<Long> auditorProvider() {
		return new AuditorAwareImpl();
	}
	
	public class AuditorAwareImpl implements AuditorAware<Long> {

	

	 @Override
	    public Optional<Long> getCurrentAuditor() {
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser") {
				return null;
			}
				
			Long user = ((MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId();
	        return Optional.ofNullable(user);
	 }
	        
	 
	}
}