package grails.plugin.awssdk.sqs.extend

import grails.plugins.*

class AwsSdkSqsExtendGrailsPlugin extends Plugin {

    def grailsVersion = "3.1.4 > *"
    def title = "AWS SQS extend plugin" // Headline display name of the plugin
    def author = "Dalin Lian"
    def authorEmail = "liandalin0207@gmail.com"
    def description = 'Based on Grails AWS SDK SQS Plugin, use the Amazon SQS Extended Client Library for Java to send messages larger than 256 KB.'
    def documentation = "http://grails.org/plugin/grails-plugin-awssdk-sqs"
    def license = "APACHE"
}
