# JSON Logger Extension with SQS Destination

This extension provides the ability to log messages in JSON format and send them to a designated Amazon Simple Queue Service (SQS) queue.

#Prerequisites
    A Mule 4.x runtime
    An Amazon Web Services (AWS) account and access to the AWS Management Console
    A created SQS queue in the AWS Management Console
#Features

## Installation
* Clone or download the code from this repository
* Import the code into your Mule project as a Mule plugin
* Add the following dependency to your pom.xml file:
    
    ```<dependency><groupId>com.amazonaws</groupId><artifactId>aws-java-sdk-sqs</artifactId><version>1.11.xxx</version></dependency>```
    
    where xxx is the latest version of the AWS SDK for Java. For more information, see the [AWS SDK for Java documentation](https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-project-maven.html).
* 
## 2.2.0 version - Release notes
* Logs messages in JSON format
* Sends messages to a designated SQS queue
* Supports logging to private SQS queues

## 2.1.0 version - Release notes

* Minimum supported mule runtime 4.3
* Upgraded dependencies to fix known vulnerabilities

## 2.0.1 version - Release notes

Bug fixes:
* Added support for large payloads

## 2.0.0 version - Release notes

New features:
* External Destinations
* Data masking

Improvements:
* Field ordering

More details in the coming blog post (stay tuned!)

## 1.1.0 version - Release notes

New features:
* Scoped loggers to capture "scope bound elapsed time". Great for performance tracking of specific components (e.g. outbound calls)
* Added "Parse content fields in json output" flag so that content fields can become part of final JSON output rather than a "stringified version" of the content

Improvements:
* Removed Guava and caching in general with a more efficient handling of timers (for elapsed time)
* Optimized generation of JSON output
* Code optimizations
* Minimized dependency footprint (down from ~23MB to ~13MB)
* Optimized parsing of TypedValue content fields

#License

This project is licensed under the Apache 2.0 License.