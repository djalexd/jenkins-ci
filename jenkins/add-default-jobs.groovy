/*
 * Install default jobs. Source of inspiration:
 * http://stackoverflow.com/a/28176509/863412
 */


import javaposse.jobdsl.dsl.DslScriptLoader
import jenkins.model.*
import hudson.model.*
import javaposse.jobdsl.plugin.JenkinsJobManagement
import java.util.logging.Logger

def pattern = 'default-jobs/**/*.groovy'

/*
 * project is the seed for default jobs.
 */
def project = new FreeStyleProject(Jenkins.getInstance(), "Seed job")
def build = new FreeStyleBuild(project)

def TaskListener taskListener = new hudson.util.LogTaskListener(
		Logger.getLogger("add-default-jobs"),
		java.util.logging.Level.INFO)

def jm = new JenkinsJobManagement(
		taskListener.getLogger(),
		build.getEnvironment(taskListener),
		build)

new FileNameFinder().getFileNames('/usr/share/jenkins/ref', pattern).each { String fileName ->
	println "\nprocessing file: $fileName"
	File file = new File(fileName)
	DslScriptLoader.runDslEngine(file.text, jm)
}