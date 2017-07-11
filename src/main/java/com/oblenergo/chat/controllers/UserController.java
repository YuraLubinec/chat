package com.oblenergo.chat.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oblenergo.chat.dto.UserAuthorityDTO;

@RestController
@RequestMapping("/user")
public class UserController {
  
  @GetMapping("/authority")
  public UserAuthorityDTO checkAuthority(Authentication authentication){

    UserAuthorityDTO user = new UserAuthorityDTO();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    user.setUsername(userDetails.getUsername());
    for (GrantedAuthority authority : userDetails.getAuthorities()){
      user.setRole(authority.getAuthority());
    } 
    return user;
    
  }
}
