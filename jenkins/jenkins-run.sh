#!/bin/bash

#docker run -d -p 8080:8080 -p 50000:50000 -v /Users/alexdobjanschi/workspace/jenkins-ci/data/jenkins:/var/jenkins_home djalexd/jenkins:0.1
docker run -d -p 8080:8080 -p 50000:50000 djalexd/jenkins:0.1
