package com.example.candidatetracker.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
    private JwtRequestFilter jwtRequestFilter;
    
    @Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().
        //Allow all to /authenticate
        authorizeRequests().antMatchers("/authenticate").permitAll()

        //securing endpoints
        
        //candidates
        .antMatchers(HttpMethod.GET, "/candidates*", "/candidates/**").hasAnyAuthority("recruiter", "admin", "interviewer")
        .antMatchers(HttpMethod.PUT, "/candidates*", "/candidates/**").hasAnyAuthority("recruiter","admin")
        .antMatchers(HttpMethod.POST, "/candidates*", "/candidates/**").hasAnyAuthority("recruiter","admin")
        //skills and roles
        .antMatchers("/skills*","/skills/**").permitAll()
        .antMatchers("/roles*", "/roles/**").permitAll()
        //stats
        .antMatchers("/stats/global").hasAnyAuthority("root", "admin", "ops", "recruiter")
        .antMatchers("/stats/local").hasAnyAuthority("root", "admin", "ops", "recruiter")
        //interviews
        .antMatchers(HttpMethod.GET, "/interviews", "/interviews/").hasAnyAuthority("recruiter", "interviewer")
        .antMatchers(HttpMethod.POST, "/interviews", "/interviews/").hasAnyAuthority("recruiter")
        .antMatchers(HttpMethod.PUT, "/interviews/feedback", "/interviews/feedback/").hasAnyAuthority("interviewer")
        .antMatchers(HttpMethod.PUT, "/interviews/**").hasAnyAuthority("recruiter", "interviewer")
        //users
        .antMatchers(HttpMethod.PUT, "/users", "/users/").permitAll()
        .antMatchers("/users", "/users/").hasAnyAuthority("root", "admin", "ops")
        .antMatchers(HttpMethod.GET, "/users/interviewers", "/users/interviewers/").hasAnyAuthority("recruiter")
        .antMatchers(HttpMethod.GET, "/users/role/**").hasAnyAuthority("root", "admin", "ops")
        .antMatchers("/users/**").permitAll()        
        
        //Deny all other                                                                                              

        .antMatchers("**").denyAll()

        //authenticate rest all requests
        .anyRequest().authenticated().and()
        // make sure we use stateless session; session won't be used to store user's state.
        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS); 

        // Add a filter to validate the tokens with every request
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
    
//    @Bean
//    public UserDetailsService jwtUserDetailsService() {
//    	return new JwtUserDetailsService();
//    }

}
