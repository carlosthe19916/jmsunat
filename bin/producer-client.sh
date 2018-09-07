#!/usr/bin/env bash

mvn clean package -DskipTests
java -jar -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5006 distribution/producer-client/target/*-thorntail.jar -Dswarm.port.offset=100