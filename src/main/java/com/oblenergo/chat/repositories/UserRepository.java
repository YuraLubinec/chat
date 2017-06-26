package com.oblenergo.chat.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.oblenergo.chat.models.User;

public interface UserRepository extends MongoRepository<User, String> {
  
  User findOneByName(String username);

}
