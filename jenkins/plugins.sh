#! /bin/bash

# Parse a support-core plugin -style txt file as specification for jenkins plugins to be installed
# in the reference directory, so user can define a derived Docker image with just :
#
# FROM jenkins
# COPY plugins.txt /plugins.txt
# RUN /usr/local/bin/plugins.sh /plugins.txt
#

set -e

REF_LOCAL=/usr/share/jenkins/ref/plugins-local
REF=/usr/share/jenkins/ref/plugins
mkdir -p $REF

while read spec || [ -n "$spec" ]; do
    plugin=(${spec//:/ });
    [[ ${plugin[0]} =~ ^# ]] && continue
    [[ ${plugin[0]} =~ ^\s*$ ]] && continue
    [[ -z ${plugin[1]} ]] && plugin[1]="latest"

    if [ -z "$JENKINS_UC_DOWNLOAD" ]; then
      JENKINS_UC_DOWNLOAD=$JENKINS_UC/download
    fi
    if [ -e "${REF_LOCAL}/${plugin[0]}-${plugin[1]}.jpi" ]; then
        echo "Plugin ${plugin[0]}-${plugin[1]}.jpi exists locally, it will not be downloaded from ${JENKINS_UC_DOWNLOAD}"
        cp $REF_LOCAL/${plugin[0]}-${plugin[1]}.jpi $REF/${plugin[0]}.jpi
    else
        echo "Plugin does not exist locally, it will be downloaded from ${JENKINS_UC_DOWNLOAD}"
        echo "Downloading ${plugin[0]}:${plugin[1]}"
        curl -sSL -f ${JENKINS_UC_DOWNLOAD}/plugins/${plugin[0]}/${plugin[1]}/${plugin[0]}.hpi -o $REF/${plugin[0]}.jpi
        unzip -qqt $REF/${plugin[0]}.jpi
    fi
done  < $1