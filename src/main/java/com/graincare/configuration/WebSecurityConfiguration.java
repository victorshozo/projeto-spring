package com.graincare.configuration;

import org.glassfish.jersey.internal.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Value("${security.cookie.name}")
	private String cookieName;
	
	@Autowired 
	private UserDetailsDAO userDetailsService;
	@Autowired 
	private RememberMeAuthenticationProvider rememberMeAuthenticationProvider;
	@Autowired
	private LoginFailureHandler failureLogin;
	@Autowired
	private LoginSuccessHandler successHandler;
	@Autowired
	private LogoutHandler logoutSuccessHandler;
	
	@Override 
	protected void configure(HttpSecurity http) throws Exception {
        http
        	.csrf().disable()
            .authorizeRequests()
                .antMatchers("/register").permitAll()
                .antMatchers("/sensor/history").permitAll()
                .antMatchers("/user/password/reset").permitAll()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/dist/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/vendor/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/home").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
            	.loginPage("/login")
	            .loginProcessingUrl("/login")
	            .defaultSuccessUrl("/home", true)
	            .successHandler(successHandler)
	            .failureHandler(failureLogin)
	            .permitAll()
	            .and()
            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
            	.clearAuthentication(true)
                .deleteCookies(cookieName)
                .invalidateHttpSession(true)
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
            .exceptionHandling()
            	.authenticationEntryPoint(new Http403ForbiddenEntryPoint())
            	.and()
            .rememberMe().rememberMeServices(rememberMeServices());
    }

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
		auth.authenticationProvider(rememberMeAuthenticationProvider);
	}

	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new PasswordEncoder() {
			@Override
			public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
				if(encPass != null && encPass.equals(Base64.encodeAsString(rawPass.getBytes()))){
					return true;
				}
				return false;
			}
			
			@Override
			public String encodePassword(String rawPass, Object salt) {
				return Base64.encodeAsString(rawPass.getBytes());
			}
		};
	}
	
	@Bean   
	TokenBasedRememberMeService rememberMeServices() {
		TokenBasedRememberMeService service = new TokenBasedRememberMeService(cookieName, userDetailsService);
		service.setAlwaysRemember(true);
		service.setCookieName(cookieName);
		return service;
	}

	@Bean
	RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
		return new RememberMeAuthenticationProvider(cookieName);
	}
}