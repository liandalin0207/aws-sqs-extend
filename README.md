# Grails AWS SQS Extend Plugin
=========================

# Introduction

The maximum size of SQS message is 262,144 bytes (256 KB). If message size is larger than 256 KB, SQS will throw exception com.amazonaws.services.sqs.model.AmazonSQSException: One or more parameters are invalid. Reason: Message must be shorter than 262144 bytes. 
To resolve it, Amazon provides one solution that store the message itself in S3 and send message reference into queue. And Amazon also provides Java library [Amazon SQS Extended Client Library for Java](https://github.com/awslabs/amazon-sqs-java-extended-client-lib).
This plugin is based on [AWS SDK SQS Grails Plugin](https://github.com/agorapulse/grails-aws-sdk/tree/master/grails-aws-sdk-sqs), and invoke [Amazon SQS Extended Client Library for Java](https://github.com/awslabs/amazon-sqs-java-extended-client-lib) to send normal and large size messages. In order to send large message, one S3 bucket for message is necessary.

## Message management

```groovy
// Send large message
amazonSQSExtendService.sendMessage(inputQueueName, messageBody, sqsS3BucketName)

// Send normal message
amazonSQSExtendService.sendMessage(inputQueueName, messageBody)
```

# Bugs

To report any bug, please use the project [Issues](https://github.com/liandalin0207/aws-sqs-extend/issues) section on GitHub.

Feedback and pull requests are welcome!
