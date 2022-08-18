package com.bezkoder.spring.jwt.mongodb.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jwt.mongodb.kafka.KafkaProducer;
import com.bezkoder.spring.jwt.mongodb.models.Tweet;
import com.bezkoder.spring.jwt.mongodb.models.TweetReply;
import com.bezkoder.spring.jwt.mongodb.models.User;
import com.bezkoder.spring.jwt.mongodb.payload.request.LoginRequest;
import com.bezkoder.spring.jwt.mongodb.repository.TweetRepository;
import com.bezkoder.spring.jwt.mongodb.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	TweetRepository tweetRepository;
	
	@Autowired
	private KafkaProducer kafkaProducer;

	@PostMapping("/all1")
	public String allAccess() {
		return "Public Content.";
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@PostMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}

	@PostMapping("/{username}/add")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> postTweet(@RequestBody Tweet tweet, @PathVariable String username) {
		Tweet tweeted = tweetRepository.save(tweet);
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Error: User is not found."));
		Set<Tweet> tweets = user.getTweets();
		tweets.add(tweeted);
		user.setTweets(tweets);
		userRepository.save(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> updateTweet(@RequestBody Tweet tweet, @PathVariable String id) {
		tweet.setId(id);
		tweetRepository.save(tweet);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> deleteTweet(@PathVariable String id) {
		tweetRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/like/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> likeTweet(@RequestBody LoginRequest username, @PathVariable String id) {
		String username1 = username.getUsername();
		Tweet tweet = tweetRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Error: Tweet is not found."));
		List<String> likedBy = tweet.getLikedBy();
		if (likedBy == null)
			likedBy = Arrays.asList(username1);
		else
			likedBy.add(username1);
		tweet.setLikedBy(likedBy);
		tweetRepository.save(tweet);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/reply/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> replyTweet(@RequestBody TweetReply reply, @PathVariable String id) {
		Tweet tweet = tweetRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Error: Tweet is not found."));
		List<TweetReply> replies = tweet.getReplies();
		if (replies == null)
			replies = Arrays.asList(reply);
		else
			replies.add(reply);
		tweet.setReplies(replies);
		tweetRepository.save(tweet);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/all")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> getTweets() {
		List<Tweet> tweets = tweetRepository.findAll();
		return new ResponseEntity<List<Tweet>>(tweets, HttpStatus.OK);
	}

	@GetMapping("/{username}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> getTweets(@PathVariable String username) {
		List<Tweet> tweets = tweetRepository.findByUsername(username);
		return new ResponseEntity<List<Tweet>>(tweets, HttpStatus.OK);
	}

	@GetMapping("/users/all")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> getUsers() {
		List<User> users = userRepository.findAll();
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@GetMapping("/user/search/{username}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> getUser(@PathVariable String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Error: User is not found."));
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
    @GetMapping("/publish")
    public ResponseEntity<String> publish(@RequestParam("message") String message){
        kafkaProducer.sendMessage(message);
        return ResponseEntity.ok("Message sent to kafka topic");
    }

}
