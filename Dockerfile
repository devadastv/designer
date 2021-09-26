FROM ibmcom/websphere-liberty:21.0.0.6-full-java8-ibmjava-ubi
RUN configure.sh

USER root
# Step 1: Install Java SDK (ibmjava sdk).
RUN yum install -y openssl ca-certificates \
    && yum update; yum clean all

# 1.1 - Removing the JRE installed in the ibmcom/websphere-liberty image.
RUN rm -rf /opt/ibm/java/*

# 1.2 - Installing SDK by copying the installation directory from ibmjava image and setting the
# required env variables.
COPY --chown=1001:0 --from=ibmjava:8-sdk /opt/ibm/java /opt/ibm/java

ENV JAVA_HOME=/opt/ibm/java/jre \
    PATH=/opt/ibm/java/bin:$PATH \
    IBM_JAVA_OPTIONS="-XX:+UseContainerSupport"

# Install maven
COPY --chown=1001:0 --from=maven:3.8.2-ibmjava-8 /usr/share/maven /usr/share/maven
ENV MAVEN_HOME=/usr/share/maven \
    PATH=/usr/share/maven/bin:$PATH

COPY --chown=1001:0 ./server.xml /opt/ibm/wlp/usr/servers/defaultServer/
COPY --chown=1001:0 ./.m2/ /home/default/.m2
RUN chmod --recursive 777 /home/default/.m2 && \
    ln -sf /home/default/.m2 / && \
    chmod --recursive 777 /.m2

COPY --chown=1001:0 deploy_project.sh /opt/ibm/
RUN chmod +x /opt/ibm/deploy_project.sh

COPY --chown=1001:0 ./backend/target/backend.war /opt/ibm/wlp/usr/servers/defaultServer/dropins/
COPY --chown=1001:0 ./frontend/target/frontend.war /opt/ibm/wlp/usr/servers/defaultServer/dropins/

USER 1001

CMD /opt/ibm/wlp/bin/server run defaultServer --clean