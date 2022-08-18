package com.bezkoder.spring.jwt.mongodb.models;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

public class TweetReply {

	@Id
	private String id;

	@NotBlank
	@Size(max = 20)
	private String repliedby;

	@NotBlank
	// @Size(max = 20)
	private LocalDateTime datetime;

	@NotBlank
	private String tweetId;

	@NotBlank
	@Size(max = 144)
	private String reply;

	public TweetReply(@NotBlank @Size(max = 20) String repliedby, @NotBlank LocalDateTime datetime,
			@NotBlank String tweetId, @NotBlank @Size(max = 144) String reply) {
		super();
		this.repliedby = repliedby;
		this.datetime = datetime;
		this.tweetId = tweetId;
		this.reply = reply;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRepliedby() {
		return repliedby;
	}

	public void setRepliedby(String repliedby) {
		this.repliedby = repliedby;
	}

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}

	public String getTweetId() {
		return tweetId;
	}

	public void setTweetId(String tweetId) {
		this.tweetId = tweetId;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

}
