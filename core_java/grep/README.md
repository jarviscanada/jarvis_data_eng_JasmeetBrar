# Grep Project

## Introduction

In this project, I had to implement my version of grep, which is a command-line utility that would output all the lines that match the given regex string, from the files of a given directory. My implementation would pipe that to a given output file. In terms of technologies, I had implemented this project using Core Java, and some of its features and libraries like regex, lambda, and stream. In terms of tools, I had used the IntelliJ IDE, and the Git Flow methodology. As for deployment, I containerized my Grep implementation using Docker, so it can run on any platform using the same environment.

## Quick Start

If you want to run the app from the source itself, then execute the following:

````bash
mvn clean package
java -jar ./target/grep-1.0-SNAPSHOT.jar <regex expression> <path to folder> <path to output file>
````

If you wish to run the app in Docker, then execute the following:

````bash
docker pull jbrar/grep
docker run --rm \
-v <host path to its data folder>:<container path to its data folder> \
-v <host path to its log folder>:<container path to its log folder> \
jbrar/grep <regex string> <host path to its data folder> <host path to its log file>
````
Note that the log file must be in the host's `log` folder.

The result from either approach would be that you get the output of the program in the specified log file, and it should be the output as if you had used the `grep` command.

## Implementation

In terms of implementation, my first approach goes through each directory and recursively gets all the files, so that I would iterate through them and get the lines that match the regex and then from there output it to a file.

My second approach uses the same idea but lambdas and streams were used whenever possible to express operations more declaratively, and it would run more efficiently.

## Pseudocode

````
matchedLines = []
for file in all files obtained recursively from the given directory:
    for line in files:
        if line matches the given regex:
            matchedLines.append(line)
write matchedLines to the given output file
````

## Performance Issue

The implementation I made does have an issue regarding the amount of memory it uses. What I mean is that my app takes all the lines from a file and stores them in memory, and it does so for all the other files. If there is a file that is much larger than the memory that is allocated to the JVM, then it would crash the program.

The solution, in this case, is to use streams so that we are not required to have all the lines of all the files in memory. Instead, we can directly work with the stream provided by `BufferReader.lines()` and chain intermediate operations on it to check if the lines match the given regex expression and if so write it to the output file using a `BufferedWriter`.


## Test

I tested the application manually by preparing some sample data, and then running some test cases and verifying for myself that the application is executing the way it should.

## Deployment

1. I first added the Maven Shade Plugin to the `pom.xml` as part of Maven's build process so that when I try to package my app as a Jar, it will also package it as a Fat Jar, which allows my application to run on its own without needing to depend on another Jar file.
2. I execute `mvn clean package` to package my app as a Fat Jar.
3. I then create a new DockerFile, and in it, I specified the base image I would use that would contain the Java 8 JRE, and I also specified to copy the created Fat Jar over, and the command that needs to be executed to run my application in the Docker container.
4. I then execute `docker build -t jbrar/grep .` to build a new docker container using the Dockerfile I made.
5. I deployed my image to Docker Hub using `docker push jbrar/grep`.

## Improvement

1. I can fix the memory issue I described earlier by directly applying operations to the stream provided by `BufferedReader.lines()`.
2. Performance can be improved by making the application multi-threaded. I would make multiple threads and split the files among these threads. Then each thread would parse their given files, and write their outputs to the log file using `BufferedWriter`.
3. I can implement more features to comply with grep has such as adding the `-f` flag for getting regex strings from a file, or the `-v` flag to invert the match.
