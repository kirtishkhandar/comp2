package com.tweetapp.models;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel.DynamoDBAttributeType;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;

@DynamoDBTable(tableName = "tweets")
public class Tweet implements Comparable<Tweet> {

	private String id;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	// @Size(max = 20)
	private Date datetime;

	@NotBlank
	@Size(max = 144)
	private String body;

	private List<String> likedBy;

	private List<TweetReply> replies;

	public Tweet(@NotBlank @Size(max = 20) String username, @NotBlank Date datetime,
			@NotBlank @Size(max = 144) String body) {
		super();
		this.username = username;
		this.datetime = datetime;
		this.body = body;
	}

	public Tweet() {
		super();
	}

	@DynamoDBHashKey
	@DynamoDBAutoGeneratedKey
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@DynamoDBAttribute
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@DynamoDBAttribute
	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	@DynamoDBAttribute
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@DynamoDBTyped(DynamoDBAttributeType.L)
	public List<String> getLikedBy() {
		return likedBy;
	}

	public void setLikedBy(List<String> likedBy) {
		this.likedBy = likedBy;
	}

	@DynamoDBTyped(DynamoDBAttributeType.L)
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

	@Override
	public String toString() {
		return "Tweet [id=" + id + ", username=" + username + ", datetime=" + datetime + ", body=" + body + ", likedBy="
				+ likedBy + ", replies=" + replies + "]";
	}

}
