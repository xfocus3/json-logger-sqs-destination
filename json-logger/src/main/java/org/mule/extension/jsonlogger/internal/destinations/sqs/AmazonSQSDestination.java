package org.mule.extension.jsonlogger.internal.destinations.sqs;

import org.mule.extension.jsonlogger.internal.destinations.Destination;
import org.mule.extension.jsonlogger.internal.destinations.sqs.client.AmazonSQSClientFactory;
import org.mule.runtime.extension.api.annotation.param.NullSafe;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.mule.runtime.extension.api.annotation.param.reference.ConfigReference;

import java.util.ArrayList;
import java.util.List;

public class AmazonSQSDestination implements Destination{


    @Parameter
    @ConfigReference(namespace = "SQS", name = "CONFIG")
    @DisplayName("Queue URL")
    @Summary("The URL of the SQS queue to send messages to")
    private String queueUrl = "";

    @Parameter
    @ConfigReference(namespace = "SQS", name = "CONFIG")
    @DisplayName("AWS Access Key")
    @Summary("The AWS Access Key used to authenticate to the SQS service")
    private String accessKey="";

    @Parameter
    @ConfigReference(namespace = "SQS", name = "CONFIG")
    @DisplayName("AWS Secret Key")
    @Summary("The AWS Secret Key used to authenticate to the SQS service")
    private String secretKey="";

    @Parameter
    @ConfigReference(namespace = "SQS", name = "CONFIG")
    @Optional
    @DisplayName("Region")
    @Summary("The region of the SQS service")
    private String region;


    private AmazonSQSClientFactory clientFactory;
    private AmazonSQS sqsClient;
    private List<String> logs = new ArrayList<>();

    @Parameter
    @Optional
    @NullSafe
    @Summary("Indicate which log categories should be send (e.g. [\"my.category\",\"another.category\"]). If empty, all will be send.")
    @DisplayName("Log Categories")
    private ArrayList<String> logCategories;

    @Parameter
    @Optional(defaultValue = "25")
    @Summary("Indicate max quantity of logs entries to be send to the external destination")
    @DisplayName("Max Batch Size")
    private int maxBatchSize;

    @Override
    public int getMaxBatchSize() {
        return this.maxBatchSize;
    }

    @Override
    public String getSelectedDestinationType() {
        return "JMS";
    }

    @Override
    public ArrayList<String> getSupportedCategories() {
        return logCategories;
    }

    @Override
    public void initialise() {
        clientFactory = new AmazonSQSClientFactory(accessKey, secretKey, "eu-central-1",queueUrl);
        sqsClient = clientFactory.createClient();
    }

    @Override
    public void sendToExternalDestination(String log) {
        logs.add(log);
        if (logs.size() >= maxBatchSize) {
            flushLogs();
        }
    }

    private void flushLogs() {
        SendMessageRequest sendMessageRequest = new SendMessageRequest(queueUrl, logs.toString());
        sqsClient.sendMessage(sendMessageRequest);
        logs.clear();
    }

    @Override
    public void dispose() {
        this.sqsClient.shutdown();
    }

}
