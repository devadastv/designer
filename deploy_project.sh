#!/bin/sh

WAR_NAME=project.war

if [ -e /opt/ibm/wlp/usr/servers/defaultServer/dropins/$WAR_NAME ] ; then
    rm -f /opt/ibm/wlp/usr/servers/defaultServer/dropins/$WAR_NAME
fi

cd /projects/jaxrs-app
/usr/share/maven/bin/mvn clean install
cp -f target/$WAR_NAME /opt/ibm/wlp/usr/servers/defaultServer/dropins/
