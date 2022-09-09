package com.tweetapp.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.tweetapp.models.Tweet;

@Service
public class KafkaProducer {

//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    public void sendMessage(Tweet tweet){
//        kafkaTemplate.send(AppConstants.TOPIC_NAME, tweet.toString());
//    }
}
