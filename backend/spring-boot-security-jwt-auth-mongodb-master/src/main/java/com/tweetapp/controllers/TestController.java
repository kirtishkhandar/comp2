package com.tweetapp.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.kafka.KafkaProducer;
import com.tweetapp.models.Tweet;
import com.tweetapp.models.TweetReply;
import com.tweetapp.models.User;
import com.tweetapp.payload.request.LoginRequest;
import com.tweetapp.repository.TweetRepository;
import com.tweetapp.repository.UserRepository;

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

	String ErrMsg = "Error: Tweet is not found.";

	@PostMapping("/{username}/add")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Boolean> postTweet(@RequestBody Tweet tweet, @PathVariable String username) {
		Tweet tweeted = tweetRepository.save(tweet);
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Error: User is not found."));
		Set<Tweet> tweets = user.getTweets();
		tweets.add(tweeted);
		user.setTweets(tweets);
		//kafkaProducer.sendMessage(tweeted);
		userRepository.save(user);
		return ResponseEntity.ok(Boolean.TRUE);
	}

	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Boolean> updateTweet(@RequestBody Tweet updatedTweet, @PathVariable String id) {
		Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new RuntimeException(ErrMsg));
		tweet.setBody(updatedTweet.getBody());
		tweet.setDatetime(updatedTweet.getDatetime());
		tweetRepository.save(tweet);
		return ResponseEntity.ok(Boolean.TRUE);
	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Boolean> deleteTweet(@PathVariable String id) {
		tweetRepository.deleteById(id);
		return ResponseEntity.ok(Boolean.TRUE);
	}

	@PutMapping("/like/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Boolean> likeTweet(@RequestBody LoginRequest username, @PathVariable String id) {
		String username1 = username.getUsername();
		Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new RuntimeException(ErrMsg));
		List<String> likedBy = tweet.getLikedBy();
		if (likedBy == null) {
			likedBy = Arrays.asList(username1);
			}
		else
			likedBy.add(username1);
		tweet.setLikedBy(likedBy);
		tweetRepository.save(tweet);
		return ResponseEntity.ok(Boolean.TRUE);
	}

	@PostMapping("/reply/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Boolean> replyTweet(@RequestBody TweetReply reply, @PathVariable String id) {
		Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new RuntimeException(ErrMsg));
		List<TweetReply> replies = tweet.getReplies();
		if (replies == null) {
			replies = Arrays.asList(reply);
			}
		else
			replies.add(reply);
		tweet.setReplies(replies);
		tweetRepository.save(tweet);
		return ResponseEntity.ok(Boolean.TRUE);
	}

	@GetMapping("/all")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<Tweet>> getTweets() {
		List<Tweet> tweets = (List<Tweet>) tweetRepository.findAll();
		ArrayList<Tweet> sortedTweets = new ArrayList<Tweet>();
		if(tweets != null) {
			sortedTweets.addAll(tweets);
			Collections.sort(sortedTweets, Collections.reverseOrder());
		}
		return new ResponseEntity<List<Tweet>>(sortedTweets, HttpStatus.OK);
	}

	@GetMapping("/{username}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<Tweet>> getTweets(@PathVariable String username) {
		List<Tweet> tweets = tweetRepository.findByUsername(username);
		return new ResponseEntity<List<Tweet>>(tweets, HttpStatus.OK);
	}

	@GetMapping("/users/all")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<User>> getUsers() {
		List<User> users = (List<User>) userRepository.findAll();
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@GetMapping("/user/search/{username}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<User> getUser(@PathVariable String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Error: User is not found."));
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

}
