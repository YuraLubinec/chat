package com.oblenergo.chat.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

//@Configuration
//@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final static String REALM = "Oblenergo chat service";

  @Autowired
  private RestUnauthorizedEntryPoint entryPoint;

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic().realmName(REALM).authenticationEntryPoint(entryPoint).and().cors()
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    String[] antPatternsGet = { "/customer/physical/{accountNumber:\\d+}", "/customer/physical/{accountNumber:\\d+}/report",
        "/customer/physical/{accountNumber:\\d+}/bill" };
    String[] antPatternsPost = { "/customer/physical/{accountNumber:\\d+}/indicator/onezone", "/customer/physical/{accountNumber:\\d+}/indicator/twozone",
        "/customer/physical/{accountNumber:\\d+}/indicator/threezone", "/customer/juridical/report", "/customer/juridical" };

    web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    web.ignoring().antMatchers(HttpMethod.GET, antPatternsGet);
    web.ignoring().antMatchers(HttpMethod.POST, antPatternsPost);
  }

}
