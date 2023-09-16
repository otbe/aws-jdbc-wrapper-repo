# aws-jdbc-wrapper-repo

Repro for https://github.com/awslabs/aws-advanced-jdbc-wrapper/issues/618

Execute `com.example.demo.DemoApplicationTests#failingTest`. It should bootstrap a pg 15.3 db and establish the conenction via AWS JDBC wrapper.
Depending on your machine you might have to play a little bit with the loop count.
