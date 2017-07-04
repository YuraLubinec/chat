package com.oblenergo.chat.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import com.oblenergo.chat.models.User;

@Transactional(readOnly=true)
public interface UserRepository extends MongoRepository<User, String> {
  
  User findOneByUsername(String username);

}
