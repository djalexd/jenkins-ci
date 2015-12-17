import jenkins.model.*
import hudson.plugins.sonar.*
import hudson.plugins.sonar.model.*

def inst = Jenkins.getInstance()

def sonarFile = new File("/usr/share/jenkins/ref/sonar.properties")

// Only provision Sonar if properties file exists
if (sonarFile.exists()) {

    def desc = inst.getDescriptor("hudson.plugins.sonar.SonarPublisher")

    // Merge from environment and sonar.properties (environment is more important).
    def sonarProperties = new Properties()
    sonarProperties.load(new FileInputStream(sonarFile))

    for (final Map.Entry<String, String> e : System.getenv().entrySet()) {
        if (e.getKey().startsWith("sonar.")) {
            sonarProperties.put(e.getKey(), e.getValue())
        }
    }

    def sinst = new SonarInstallation(
            sonarProperties.getProperty("sonar.name"),
            Boolean.valueOf(sonarProperties.getProperty("sonar.disabled")),
            sonarProperties.getProperty("sonar.serverUrl"),
            sonarProperties.getProperty("sonar.databaseUrl"),
            sonarProperties.getProperty("sonar.databaseLogin"),
            sonarProperties.getProperty("sonar.databasePassword"),
            sonarProperties.getProperty("sonar.mojoVersion"),
            sonarProperties.getProperty("sonar.additionalProperties"),
            new TriggersConfig(),
            sonarProperties.getProperty("sonar.sonarLogin"),
            sonarProperties.getProperty("sonar.sonarPassword"))

    desc.setInstallations(sinst)
    desc.save()

    // TODO Maybe update the current Sonar installation.

} else {
    // Meaningful debug message
    println "No Sonar installation occurred because: Cannot find file " + sonarFile.getPath()
}