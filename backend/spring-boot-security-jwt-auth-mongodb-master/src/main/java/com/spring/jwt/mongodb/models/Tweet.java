package com.spring.jwt.mongodb.models;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tweets")
public class Tweet implements Comparable<Tweet>{

	@Id
	private String id;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	// @Size(max = 20)
	private LocalDateTime datetime;

	@NotBlank
	@Size(max = 144)
	private String body;

	private List<String> likedBy;
	
	private List<TweetReply> replies;

	public Tweet(@NotBlank @Size(max = 20) String username, @NotBlank LocalDateTime datetime,
			@NotBlank @Size(max = 144) String body) {
		super();
		this.username = username;
		this.datetime = datetime;
		this.body = body;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public List<String> getLikedBy() {
		return likedBy;
	}

	public void setLikedBy(List<String> likedBy) {
		this.likedBy = likedBy;
	}

	public List<TweetReply> getReplies() {
		return replies;
	}

	public void setReplies(List<TweetReply> replies) {
		this.replies = replies;
	}

	@Override
	public int compareTo(Tweet o) {
		return this.getDatetime().compareTo(o.datetime);
	}

}
