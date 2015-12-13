FROM jenkins

ENV JENKINS_TEMP /tmp/jenkins

ENV JENKINS_REF /usr/share/jenkins/ref
ENV JENKINS_INITD $JENKINS_REF/init.groovy.d

USER root
RUN apt-get install -y maven npm
USER jenkins

RUN mkdir -p $JENKINS_REF/default-jobs

ADD jenkins-plugins.txt $JENKINS_REF/

ADD users.groovy $JENKINS_INITD/
ADD sonar.groovy $JENKINS_INITD/
ADD add-default-jobs.groovy $JENKINS_INITD/
#ADD disable-master-executors.groovy $JENKINS_INITD/

ADD default-jobs $JENKINS_REF/default-jobs/

# Add all scripts found such that Jenkins Docker image
# will automatically run them.
ADD scripts $JENKINS_INITD/

ADD sonar.properties $JENKINS_REF/sonar.properties

RUN /usr/local/bin/plugins.sh $JENKINS_REF/jenkins-plugins.txt

# ssh configuration
RUN mkdir -p $JENKINS_TEMP/.ssh
ADD config/.ssh/jenkins-ci-templates $JENKINS_TEMP/.ssh/
ADD config/.ssh/jenkins-ci-templates.pub $JENKINS_TEMP/.ssh/

# Shared folder, we cannot modify permissions.
USER root
RUN chown jenkins $JENKINS_TEMP/.ssh/jenkins-ci-templates
RUN chown jenkins $JENKINS_TEMP/.ssh/jenkins-ci-templates.pub

RUN chmod 500 $JENKINS_TEMP/.ssh/jenkins-ci-templates
USER jenkins

ADD config/credentials.xml.tpl $JENKINS_TEMP/credentials.xml

ENTRYPOINT cp -ra $JENKINS_TEMP/* $JENKINS_TEMP/.ssh $JENKINS_HOME/ \
           && \
           /bin/tini -- /usr/local/bin/jenkins.sh