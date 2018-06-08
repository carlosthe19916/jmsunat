#!/usr/bin/env bash

mvn clean package -DskipTests
java -jar -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5007 boot/consumer/target/*-swarm.jar -Dswarm.port.offset=200