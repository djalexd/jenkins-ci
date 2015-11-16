/*
 * Install default jobs. Source of inspiration:
 * http://stackoverflow.com/a/28176509/863412
 */

import jenkins.model.*
import groovy.io.FileType

/*
 * TODO Magic value - path location: /usr/share/jenkins/ref/default-jobs
 */

def jobPath = "/usr/share/jenkins/ref/default-jobs"

new File(jobPath).eachFile() { file ->
    def jobName = file.getName()
    def jobContentsAsXml = file.getText("UTF-8")

    println "About to create job named " + jobName

	def xmlStream = new ByteArrayInputStream(jobContentsAsXml.getBytes())
	try {
		Jenkins.instance.createProjectFromXML(jobName, xmlStream)
	} catch (Exception e) {
		println "Unable to create job named " + jobName + ". A probable reason is that job already exists"
	}
}  