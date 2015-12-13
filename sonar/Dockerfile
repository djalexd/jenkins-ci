FROM sonarqube:latest

ENV BUILD_BREAKER_PLUGIN_VERSION 1.1
ENV CLOVER_PLUGIN_VERSION 3.0

RUN curl -sLo /opt/sonarqube/extensions/plugins/sonar-build-breaker-plugin-$BUILD_BREAKER_PLUGIN_VERSION.jar http://repo1.maven.org/maven2/org/codehaus/sonar-plugins/sonar-build-breaker-plugin/$BUILD_BREAKER_PLUGIN_VERSION/sonar-build-breaker-plugin-$BUILD_BREAKER_PLUGIN_VERSION.jar
RUN curl -sLo sonar-clover-plugin-$CLOVER_PLUGIN_VERSION.jar http://repo1.maven.org/maven2/org/codehaus/sonar-plugins/sonar-clover-plugin/$CLOVER_PLUGIN_VERSION$/sonar-clover-plugin-$CLOVER_PLUGIN_VERSION.jar