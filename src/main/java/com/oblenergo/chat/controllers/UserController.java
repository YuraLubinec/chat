package com.oblenergo.chat.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oblenergo.chat.dto.RoleDTO;
import com.oblenergo.chat.dto.UserAuthorityDTO;
import com.oblenergo.chat.editors.RoleEditor;
import com.oblenergo.chat.enums.Roles;
import com.oblenergo.chat.models.User;
import com.oblenergo.chat.repositories.UserRepository;
import com.oblenergo.chat.validators.RegistrationValidator;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private RoleEditor roleEditor;

  @Autowired
  private RegistrationValidator registrationValidator;

  @Autowired
  private UserRepository userRepository;

  @InitBinder("user")
  public void initBinder(WebDataBinder binder) {

    binder.registerCustomEditor(Roles.class, roleEditor);
    binder.addValidators(registrationValidator);
  }

  @GetMapping("/authority")
  public UserAuthorityDTO checkAuthority(Authentication authentication) {

    UserAuthorityDTO user = new UserAuthorityDTO();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    user.setUsername(userDetails.getUsername());
    for (GrantedAuthority authority : userDetails.getAuthorities()) {
      user.setRole(authority.getAuthority());
    }
    return user;
  }

  @GetMapping
  public List<User> getAllUser() {
    
    List<User> users = userRepository.findAll();
    users.forEach(user -> user.setPassword(null));
    return users;
  }

  @GetMapping("/{username}")
  public User getUserDeatails(@PathVariable String username) {
    
    User user = userRepository.findOneByUsername(username);
    user.setPassword(null);
    return user;
  }

  @GetMapping("/role")
  public List<RoleDTO> getRoles() {

    List<RoleDTO> roles = new ArrayList<>();
    Arrays.stream(Roles.values()).forEach(role -> roles.add(new RoleDTO(role.toString(), role.getRole())));
    return roles;
  }

  @PostMapping
  public void saveUser(@Validated @RequestBody User user) {

    userRepository.insert(user);
  }
  
  @PutMapping
  public void updateUser(@Validated @RequestBody User user) {
    
    userRepository.save(user);
  }
  
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteUser(@PathVariable String id){
    
    userRepository.delete(id);
  }
}
