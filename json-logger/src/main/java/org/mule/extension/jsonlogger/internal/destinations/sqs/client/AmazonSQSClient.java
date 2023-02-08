package org.mule.extension.jsonlogger.internal.destinations.sqs.client;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AmazonSQSClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AmazonSQSClient.class);

    private AmazonSQS sqsClient;
    private String queueUrl;

    public AmazonSQSClient(String queueUrl, String accessKey, String secretKey) {
        this.queueUrl = queueUrl;
        sqsClient = AmazonSQSClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .withRegion(Regions.US_EAST_1)
                .build();
    }

    public void sendMessage(List<String> messages) {
        for (String message : messages) {
            SendMessageRequest send_msg_request = new SendMessageRequest()
                    .withQueueUrl(queueUrl)
                    .withMessageBody(message);
            sqsClient.sendMessage(send_msg_request);
            LOGGER.debug("Sent message: " + message);
        }
    }

    public void dispose() {
        if (this.sqsClient != null) {
            this.sqsClient.shutdown();
        }
    }

    public void stop() {
        if (this.sqsClient != null) {
            this.sqsClient.shutdown();
        }
    }

}
