<?xml version='1.0' encoding='UTF-8'?>
<com.cloudbees.plugins.credentials.SystemCredentialsProvider plugin="credentials@1.24">
  <domainCredentialsMap class="hudson.util.CopyOnWriteMap$Hash">
    <entry>
      <com.cloudbees.plugins.credentials.domains.Domain>
        <specifications/>
      </com.cloudbees.plugins.credentials.domains.Domain>
      <java.util.concurrent.CopyOnWriteArrayList>
        <com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey plugin="ssh-credentials@1.10">
          <scope>GLOBAL</scope>
          <id>2c1fa93e-c1fd-407c-b771-aa724d20ffa6</id>
          <description>jenkins ci templates</description>
          <username>jenkins-ci-templates</username>
          <passphrase/>
          <privateKeySource class="com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey$FileOnMasterPrivateKeySource">
            <privateKeyFile>~/.ssh/jenkins-ci-templates</privateKeyFile>
          </privateKeySource>
        </com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey>
      </java.util.concurrent.CopyOnWriteArrayList>
    </entry>
  </domainCredentialsMap>
</com.cloudbees.plugins.credentials.SystemCredentialsProvider>