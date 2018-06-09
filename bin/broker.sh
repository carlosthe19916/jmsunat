#!/usr/bin/env bash

mvn clean package -DskipTests
java -jar -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 distribution/broker/target/*-swarm.jar -Djava.net.preferIPv4Stack=true -Dswarm.bind.address=127.0.0.1