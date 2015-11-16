Repository that holds infrastructure core, related to Continuous Integration & Continuous Delivery.


For now, only Docker images for basic infrastructure are in place: Sonar, Artifactory, semi-automated Jenkins.

Requirements
============
* These requirements are only valid until we use a scheduler like Marathon.
* Get the latest infrastructure code (git checkout master && git pull)
* Build each docker image
* `cd jenkins && packer build jenkins.json`


Jenkins
=======
* After building jenkins master Docker container, we need to run it: 
    `sudo docker run -p 8080:8080 -p 50000:50000 -v /var/data/wallet-ci/jenkins_home:/var/jenkins_home <container_id>`
* <container_id> is extracted from the packer command above (no container tagging for now)
* port 8080 is used to access Jenkins master
* port 50000 is used by slaves
* Jenkins slaves are run in a separate terminal: 
    `sudo docker run -e JENKINS_USERNAME=admin@payu.ro -e JENKINS_PASSWORD=12345 -e JENKINS_MASTER=http://<container_external_ip>:8080 andrewgortonuk/dockerjenkinswithgitbuildagent`
* <container_external_id> is only required because this env variable is passed inside container (hence, localhost, 127.0.0.1 will not work)

Sonar
=======
* Build packer template for sonar
* `cd sonar && packer build sonar.json`
* MySql user and database should be configured in another container with /var/lib/mysql persistent volume
* After building sonar container, we need to run it with configured mysql variables:
    `sudo docker run -p 9000:9000 -e "SONARQUBE_JDBC_USERNAME=sonar" -e "SONARQUBE_JDBC_PASSWORD=sonar" -e "SONARQUBE_JDBC_URL=jdbc:mysql://localhost:3306/sonar?useUnicode=true&characterEncoding=utf8&rewriteBatchedStatements=true" <image_id>`

Artifactory
===========

