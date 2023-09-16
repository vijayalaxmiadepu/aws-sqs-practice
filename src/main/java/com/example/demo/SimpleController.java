package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {
	
	@Autowired
	private QueueMessagingTemplate queueMessagingTemplate;
	
	@Value("${cloud.aws.sqs.endpoint}")
	private String endpointUri;

    @GetMapping("/test")
    public String homePage() {
        return "home";
    }
    
    @GetMapping("/sendMessagesToQueue/{msg}")
    public void sendMessagesToQueue(@PathVariable("msg") String message) {
    	queueMessagingTemplate.send(endpointUri, MessageBuilder.withPayload(message).build());
    }
    
    @SqsListener(value = {"https://sqs.us-east-1.amazonaws.com/116116812277/sqs-queue1"}, deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveMessagesFromQueue(String msg) {
    	System.out.println("messages from queue::"+msg);
    }
    
    
}
