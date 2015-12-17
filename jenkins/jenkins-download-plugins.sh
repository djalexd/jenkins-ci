#! /bin/bash
set -e

JENKINS_UC=https://updates.jenkins-ci.org/download
REF=plugins-local

while read spec || [ -n "$spec" ]; do
    plugin=(${spec//:/ });
    [[ ${plugin[0]} =~ ^# ]] && continue
    [[ ${plugin[0]} =~ ^\s*$ ]] && continue
    [[ -z ${plugin[1]} ]] && plugin[1]="latest"

    echo "Downloading ${JENKINS_UC}/plugins/${plugin[0]}/${plugin[1]}/${plugin[0]}.hpi"
    if [ -e "${REF}/${plugin[0]}-${plugin[1]}.jpi" ]; then
        echo "Skipping plugin ${REF}/${plugin[0]}.jpi (you know what you are doing, if never plugins should be " \
             "downloaded, please remove the locally stored ones before. "
    else
        curl -sSL -f ${JENKINS_UC}/plugins/${plugin[0]}/${plugin[1]}/${plugin[0]}.hpi -o $REF/${plugin[0]}-${plugin[1]}.jpi
    fi
done  < $1