package grails.plugin.awssdk.sqs.extend

import com.amazon.sqs.javamessaging.AmazonSQSExtendedClient
import com.amazon.sqs.javamessaging.ExtendedClientConfiguration
import com.amazonaws.services.sqs.model.AmazonSQSException

class AmazonSQSExtendService {

    private String charset = 'UTF-8'  //SQS use UTF-8 for binary message encoding
    private ExtendedClientConfiguration extendedClientConfiguration = new ExtendedClientConfiguration()

    def amazonSQSService
    def amazonS3Service

    /**
     * SQS message size maximum is 262,144 bytes (256 KB).
     * if message size is smaller than 256 KB, call the AmazonSQSService.sendMessage of aws-sdk-sqs plugin,
     * if message size is larger than 256 KB, call extended library, which store the message in S3 bucket
     * and send message reference into queue. The message reference like below,
     * [
     *   "com.amazon.sqs.javamessaging.MessageS3Pointer",
     *   {
     *     "s3BucketName": "test-bucket",
     *     "s3Key": "a967abcd-8795-47ee-8091-cdee2f3d68eb"
     *   }
     * ]
     *
     * @param queueName
     * @param messageBody
     * @param messageS3BucketName
     * @param delaySeconds
     * @return message id
     * @throws AmazonSQSException
     */
    String sendMessage(String queueName, String messageBody, String messageS3BucketName, Integer delaySeconds = 0) {
        if (!queueName) {
            throw new AmazonSQSException("Queue name could not be null or empty")
        }
        int messageSize = messageBody?.getBytes(charset)?.length ?: 0
        int messageSizeThreshold = extendedClientConfiguration.messageSizeThreshold
        if (messageSize < messageSizeThreshold) {
            amazonSQSService.sendMessage(queueName, messageBody, delaySeconds)
        } else {
            if (!messageS3BucketName) {
                throw new AmazonSQSException("S3 bucket name could not be null or empty")
            }
            extendedClientConfiguration.setLargePayloadSupportEnabled(amazonS3Service.client, messageS3BucketName)
            new AmazonSQSExtendedClient(amazonSQSService.client, extendedClientConfiguration).
                    sendMessage(amazonSQSService.getQueueUrl(queueName), messageBody)?.messageId
        }
    }

    /**
     * Send message by calling the AmazonSQSService.sendMessage of aws-sdk-sqs plugin.
     * throw exception when message size exceed 262,144 bytes (256 KB).
     *
     * @param queueName
     * @param messageBody
     * @param delaySeconds
     * @return message id
     * @throws AmazonSQSException
     */
    String sendMessage(String queueName, String messageBody, Integer delaySeconds = 0) {
        if (!queueName) {
            throw new AmazonSQSException("Queue name could not be null or empty")
        }
        amazonSQSService.sendMessage(queueName, messageBody, delaySeconds)
    }

}
