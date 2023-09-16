package com.example.demo.configuaration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

@Configuration
public class AmazonSQSConfiguaration {

	@Value("${cloud.aws.credentials.access-key}")
	private String accessKey;
	
	@Value("${cloud.aws.credentials.secret-key}")
	private String secretKey;
	
	@Bean
	public QueueMessagingTemplate queueMessagingTemplate() {
		return new QueueMessagingTemplate(buildSqsAsync());
	}

	private AmazonSQSAsync buildSqsAsync() {
		return AmazonSQSAsyncClientBuilder.standard().withRegion(Regions.US_EAST_1)
				.withCredentials(new AWSStaticCredentialsProvider
						(new BasicAWSCredentials(accessKey, secretKey)))
				.build();
	}
	
}
