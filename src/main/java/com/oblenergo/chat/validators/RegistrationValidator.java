package com.oblenergo.chat.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.oblenergo.chat.models.User;
import com.oblenergo.chat.repositories.UserRepository;

@Component
public class RegistrationValidator implements Validator {
  
  @Autowired
  private UserRepository userRepository;

  @Override
  public boolean supports(Class<?> clazz) {
    
    return User.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    User user = (User) target;
    if (userRepository.findOneByUsername(user.getUsername()) != null){
      errors.rejectValue("username", "not.unique.username");
    }
    
    
  }

}
