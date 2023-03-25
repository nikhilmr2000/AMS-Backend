package com.ams.AttendanceManagementSystem.Security;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class AMSSecurityConfiguration {
	
	@Bean
	SecurityFilterChain getSecurityFilter(HttpSecurity http) throws Exception{
		
		http.csrf().disable()
		.cors().configurationSource(new CorsConfigurationSource() {

			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				
				CorsConfiguration cors=new CorsConfiguration();
				
				cors.setAllowedHeaders(Collections.singletonList("*"));
				cors.setAllowedMethods(Collections.singletonList("*"));
				cors.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
				cors.setAllowCredentials(true);
			
				return cors;
			}
		
			
		})
		.and()
		.authorizeHttpRequests()
		.requestMatchers("/user","/profile","/image","/maxsize","/teacher","/teacherimage","/getProfile/**","/getImage/**","/image/**",
				"/listofprofilestatus","/allProfiles","/getActiveProfiles","/getProfileByRollNumber/**","/getProfileByStandard/**","/getTeacherByName/**",
				"/subset/**","/getTeacherList","/allTeacherList","/getTeacherByBranch/**","/getTeacherByExperience/**","getProfileFirst","/updateProfileStatus"
				,"/updateImage/**","/updateprofileimage/**","/updateTeacher","/updateTeacherImage/**","/teacherImage/**","/persistProfile","/persistTeacher"
				,"/updateProfileByName/**","/updateProfileByRollNumber/**","/updateProfileByStandard/**","/updateTeacherByName/**","/updateTeacherBySubject/**"
				,"/updateTeacherByExperience/**","/addclass","/getTeacherById/**","/getProfileById/**","/listofstandards","/listofsections").authenticated()
		.requestMatchers("/register","/logger/**").permitAll()
		.and().formLogin()
		.and().httpBasic();
		
		return http.build();
	}
	
	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}
