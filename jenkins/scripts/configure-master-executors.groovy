import jenkins.model.*


import hudson.model.*;
import jenkins.model.*;

Thread.start {
	sleep 10000
	println "--> setting master num executors"
	def env = System.getenv()
	def envMasterNumExecutorsProp = env['JENKINS_MASTER_NUM_EXECUTORS']
	int numExecutors = envMasterNumExecutorsProp != null ? envMasterNumExecutorsProp.toInteger() : 4
	Jenkins.instance.setNumExecutors(numExecutors)
	println "--> setting master num executors... done"
}