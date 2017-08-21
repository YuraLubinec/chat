package com.oblenergo.chat.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import com.oblenergo.chat.services.userDetails.ChatUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final static String REALM = "Oblenergo chat service";

  @Autowired
  private AuthenticationEntryPoint authenticationEntryPoint;

  @Autowired
  private ChatUserDetailsService userDetailsService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.csrf().disable().authorizeRequests().antMatchers("/", "/chat-websocket/**").permitAll()
        .antMatchers(HttpMethod.GET, "/user", "/user/authority", "/user/{username}").authenticated()
        .antMatchers("/admin", "/admin/**", "/user/**").hasAuthority("ADMIN").anyRequest().authenticated().and()
        .httpBasic().realmName(REALM).authenticationEntryPoint(authenticationEntryPoint).and().cors().and().logout()
        .logoutSuccessHandler((new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)));
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    String[] antPatternsGet = { "/customer/physical/{accountNumber:\\d+}",
        "/customer/physical/{accountNumber:\\d+}/report", "/customer/physical/{accountNumber:\\d+}/bill" };
    String[] antPatternsPost = { "/customer/physical/{accountNumber:\\d+}/indicator/onezone",
        "/customer/physical/{accountNumber:\\d+}/indicator/twozone",
        "/customer/physical/{accountNumber:\\d+}/indicator/threezone", "/customer/juridical/report",
        "/customer/juridical", "/settingRate/{dialog_id}/{rate}" };

    web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    web.ignoring().antMatchers(HttpMethod.GET, antPatternsGet);
    web.ignoring().antMatchers(HttpMethod.POST, antPatternsPost);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
  }

}
