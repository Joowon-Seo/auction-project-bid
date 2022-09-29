package com.sjw.bid.configuration.security;

import com.sjw.bid.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final RequestMatcher LOGIN_REQUEST_MATCHER = new AntPathRequestMatcher(
		"/login", "POST");
	private final UserDetailsService userDetailsService;
	private final UserDetailsServiceImpl userDetailsServiceImpl;
	@Qualifier("userAuthenticationEntryPoint")
	private final AuthenticationEntryPoint authenticationEntryPoint;

	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new LoginFailHandler();
	}

	private static final String[] AUTH_WHITELIST = {
		// -- swagger ui
		"/v2/api-docs",
		"/v3/api-docs/**",
		"/configuration/ui",
		"/swagger-resources/**",
		"/configuration/security",
		"/swagger-ui.html",
		"/webjars/**",
		"/file/**",
		"/image/**",
		"/swagger/**",
		"/swagger-ui/**",
		// other public endpoints of your API may be appended to this array
		"/h2/**"
	};

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(
			new EmailPasswordAuthenticationProvider(userDetailsService,
				PasswordEncoderFactories.createDelegatingPasswordEncoder(),
				new SimpleAuthorityMapper()));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		JsonEmailPasswordAuthenticationFilter jsonAuthenticationFilter = new JsonEmailPasswordAuthenticationFilter(
			LOGIN_REQUEST_MATCHER);
		jsonAuthenticationFilter.setAuthenticationManager(
			authenticationManagerBean());

		http.csrf().disable();

		http
			.addFilterAt(jsonAuthenticationFilter,
				UsernamePasswordAuthenticationFilter.class);

		http
			.userDetailsService(userDetailsServiceImpl);

		http
			.addFilter(new UsernamePasswordAuthenticationFilter());

		http
			.authorizeRequests()
			.antMatchers("/user/**")
			.permitAll()
			.antMatchers("/admin").hasRole("ADMIN");
//			.anyRequest().authenticated();

		http
			.formLogin()
			.loginPage("/login")
			.permitAll()
			.failureHandler(authenticationFailureHandler());

		http
			.exceptionHandling()
			.authenticationEntryPoint(authenticationEntryPoint);

		super.configure(http);
	}


	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(WebSecurity web) {
		// 정적인 파일 요청에 대해 무시
		web.ignoring().antMatchers(AUTH_WHITELIST);
	}
}
