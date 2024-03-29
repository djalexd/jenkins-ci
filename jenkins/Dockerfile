FROM java:8-jdk

RUN apt-get update && apt-get install -y wget git curl zip maven npm

ENV JENKINS_HOME /var/jenkins_home
ENV JENKINS_SLAVE_AGENT_PORT 50000

# Jenkins is run with user `jenkins`, uid = 1000
# If you bind mount a volume from the host or a data container,
# ensure you use the same uid
RUN useradd -d "$JENKINS_HOME" -u 1000 -m -s /bin/bash jenkins

# Jenkins home directory is a volume, so configuration and build history
# can be persisted and survive image upgrades
VOLUME /var/jenkins_home

# `/usr/share/jenkins/ref/` contains all reference configuration we want
# to set on a fresh new installation. Use it to bundle additional plugins
# or config file with your custom jenkins Docker image.
RUN mkdir -p /usr/share/jenkins/ref/init.groovy.d

ENV TINI_SHA 066ad710107dc7ee05d3aa6e4974f01dc98f3888

# Use tini as subreaper in Docker container to adopt zombie processes
RUN curl -fL https://github.com/krallin/tini/releases/download/v0.5.0/tini-static -o /bin/tini && chmod +x /bin/tini \
  && echo "$TINI_SHA /bin/tini" | sha1sum -c -

ENV JENKINS_VERSION 1.625.2
ENV JENKINS_SHA 395fe6975cf75d93d9fafdafe96d9aab1996233b

# could use ADD but this one does not check Last-Modified header
# see https://github.com/docker/docker/issues/8331
RUN curl -fL http://mirrors.jenkins-ci.org/war-stable/$JENKINS_VERSION/jenkins.war -o /usr/share/jenkins/jenkins.war \
  && echo "$JENKINS_SHA /usr/share/jenkins/jenkins.war" | sha1sum -c -

ENV JENKINS_UC https://updates.jenkins-ci.org
RUN chown -R jenkins "$JENKINS_HOME" /usr/share/jenkins/ref

# for main web interface:
EXPOSE 8080

# will be used by attached slave agents:
EXPOSE 50000

ENV COPY_REFERENCE_FILE_LOG $JENKINS_HOME/copy_reference_file.log

USER jenkins

COPY jenkins.sh /usr/local/bin/jenkins.sh

# from a derived Dockerfile, can use `RUN plugins.sh active.txt` to setup /usr/share/jenkins/ref/plugins from a support bundle
COPY plugins.sh /usr/local/bin/plugins.sh
ADD plugins-local /usr/share/jenkins/ref/plugins-local

ENV JENKINS_TEMP /tmp/jenkins

ENV JENKINS_REF /usr/share/jenkins/ref
ENV JENKINS_INITD $JENKINS_REF/init.groovy.d

RUN mkdir -p $JENKINS_REF/default-jobs

ADD jenkins-plugins.txt $JENKINS_REF/

ADD default-jobs $JENKINS_REF/default-jobs/

# Add all scripts found such that Jenkins Docker image
# will automatically run them.
ADD scripts $JENKINS_INITD/

ADD sonar.properties $JENKINS_REF/sonar.properties

RUN /usr/local/bin/plugins.sh $JENKINS_REF/jenkins-plugins.txt

# ssh configuration
#ADD config/.ssh $JENKINS_TEMP/.ssh

# Shared folder, we cannot modify permissions.
#USER root
#RUN chown -R jenkins $JENKINS_TEMP/.ssh
#RUN chmod 500 $JENKINS_TEMP/.ssh/jenkins-ci
#USER jenkins

ADD config/credentials.xml.tpl $JENKINS_HOME/credentials.xml

ENTRYPOINT /bin/tini -- /usr/local/bin/jenkins.sh