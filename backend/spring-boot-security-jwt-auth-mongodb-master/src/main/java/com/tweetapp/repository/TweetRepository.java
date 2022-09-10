package com.tweetapp.repository;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.tweetapp.models.Tweet;

@EnableScan
public interface TweetRepository extends CrudRepository<Tweet, String> {

	List<Tweet> findByUsername(String username);

}
