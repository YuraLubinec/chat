package com.oblenergo.chat.services.userDetails;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oblenergo.chat.models.User;
import com.oblenergo.chat.repositories.UserRepository;

@Service
public class ChatUserDetailsService implements UserDetailsService {
  
  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    
    final User user = userRepository.findOneByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    return new org.springframework.security.core.userdetails.User(username, user.getPassword(), true, true, true, true,
        Arrays.asList(new SimpleGrantedAuthority(user.getRole())));
  }

  
  
}
