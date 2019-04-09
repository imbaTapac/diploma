package com.student.rating.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.student.rating.encoder.ShaPasswordEncoder;

/**
 * Created by Тарас on 01.03.2018.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public UserDetailsService userDetailsService;

	@Bean
	public ShaPasswordEncoder shaPasswordEncoder() {
		return new ShaPasswordEncoder();
	}

	@Autowired
	public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.userDetailsService(userDetailsService)
				.passwordEncoder(shaPasswordEncoder());
	}

	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		// включаем защиту от CSRF атак
		http.csrf()
				.disable()
				.authorizeRequests()
				.antMatchers("/welcome").hasAnyRole("STUDENT", "HEAD_OF_GROUP", "HEAD_OF_SO")
				.antMatchers("/rating/**").hasAnyRole("STUDENT", "HEAD_OF_GROUP", "HEAD_OF_SO")
				.antMatchers("/resources/**", "/**").permitAll()
				.anyRequest().permitAll()
				.anyRequest().authenticated()
				.and();

		http.formLogin()
				// указываем страницу с формой логина
				.loginPage("/login")
				// указываем action с формы логина
				.loginProcessingUrl("/j_spring_security_check")
				// указываем URL при неудачном логине
				.failureUrl("/login?error")
				.defaultSuccessUrl("/welcome")
				// Указываем параметры логина и пароля с формы логина
				.usernameParameter("username")
				.passwordParameter("password")
				// даем доступ к форме логина всем
				.permitAll();

		http.logout()
				// разрешаем делать логаут всем
				.permitAll()
				// указываем URL логаута
				.logoutUrl("/logout")
				// указываем URL при удачном логауте
				.logoutSuccessUrl("/login?logout")
				// делаем не валидной текущую сессию
				.invalidateHttpSession(true);
	}
}
