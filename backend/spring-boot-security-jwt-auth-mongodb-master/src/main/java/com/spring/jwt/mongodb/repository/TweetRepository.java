package com.spring.jwt.mongodb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.spring.jwt.mongodb.models.Tweet;

public interface TweetRepository extends MongoRepository<Tweet, String> {

	List<Tweet> findByUsername(String username);

}
