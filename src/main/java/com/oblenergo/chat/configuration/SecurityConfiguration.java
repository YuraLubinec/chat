package com.oblenergo.chat.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import com.oblenergo.chat.services.userDetails.ChatUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private AuthenticationEntryPoint authenticationEntryPoint;

  @Autowired
  private AuthenticationSuccessHandler authenticationSuccessHandler;

  @Autowired
  private ChatUserDetailsService userDetailsService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests().antMatchers("/", "/signin/**", "/signup/**").permitAll().anyRequest().authenticated().and().exceptionHandling()
        .authenticationEntryPoint(authenticationEntryPoint).and().formLogin().loginPage("/login").successHandler(authenticationSuccessHandler)
        .failureHandler(new SimpleUrlAuthenticationFailureHandler()).loginProcessingUrl("/loginCheck").usernameParameter("username")
        .passwordParameter("password").and().logout().logoutUrl("/logout").logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
        .deleteCookies("JSESSIONID").and().csrf().disable().cors();
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

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
  }

}
