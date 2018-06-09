#!/usr/bin/env bash

mvn clean
mvn fabric8:deploy -Popenshift -pl distribution/broker