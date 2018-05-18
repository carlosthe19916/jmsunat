[![Build Status](https://travis-ci.org/carlosthe19916/sunat-jms-thorntail.svg?branch=master)](https://travis-ci.org/carlosthe19916/sunat-jms-thorntail)
[![Coverage Status](https://coveralls.io/repos/github/carlosthe19916/sunat-jms-thorntail/badge.svg?branch=master)](https://coveralls.io/github/carlosthe19916/sunat-jms-thorntail?branch=master)
[![Maintainability](https://sonarcloud.io/api/project_badges/measure?project=carlosthe19916&metric=alert_status)](https://sonarcloud.io/dashboard?id=carlosthe19916)


= REST API Level 0 - WildFly Swarm Booster

IMPORTANT: This booster requires Java 8 JDK or greater and Maven 3.3.x.

IMPORTANT: As part of the process of creating this booster, developers.redhat.com/launch or the Fabric8 Launcher tool set up a project with a CI/CD deployment of this booster. You can see the status of this deployment in your Single-node OpenShift Cluster or OpenShift Online Web Console.

== Runing the Booster Locally

To run this booster on your local host:

[source,bash,options="nowrap",subs="attributes+"]
----
$ git clone git@github.com:carlosthe19916/sunat-jms-thorntail

$ cd sunat-jms-thorntail

$ mvn wildfly-swarm:run
----

== Interacting with the Booster Locally

To interact with your booster while it's running locally, use the form at `http://localhost:8080` or the `curl` command:

[source,bash,options="nowrap",subs="attributes+"]
----
$ curl http://localhost:8080/api/greeting
{"content":"Hello, World!"}

$ curl http://localhost:8080/api/greeting?name=Sarah
{"content":"Hello, Sarah!"}
----


== Updating the Booster
To update your booster:

. Stop your booster.
+
NOTE: To stop your running booster in a Linux or macOS terminal, use `CTRL+C`. In a Windows command prompt, you can use `CTRL + Break(pause)`.

. Make your change (e.g. edit `src/main/webapp/index.html`).
. Restart your booster.
. Confirm your change appears.


== Running the Booster on a Single-node OpenShift Cluster
If you have a single-node OpenShift cluster, such as Minishift or Red Hat Container Development Kit, link:http://appdev.openshift.io/docs/minishift-installation.html[installed and running], you can also deploy your booster there. A single-node OpenShift cluster provides you with access to a cloud environment that is similar to a production environment.

To deploy your booster to a running single-node OpenShift cluster:
[source,bash,options="nowrap",subs="attributes+"]
----
$ oc login -u developer -p developer

$ oc new-project MY_PROJECT_NAME

$ mvn clean fabric8:deploy -Popenshift
----

== More Information
You can learn more about this booster and rest of the WildFly Swarm runtime in the link:http://appdev.openshift.io/docs/wf-swarm-runtime.html[WildFly Swarm Runtime Guide].

NOTE: Run the set of integration tests included with this booster using `mvn clean verify -Popenshift,openshift-it`.