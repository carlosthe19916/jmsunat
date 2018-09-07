#!/usr/bin/env bash

mvn clean package -DskipTests
java -jar -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5007 distribution/consumer-client/target/*-thorntail.jar -Dswarm.port.offset=200