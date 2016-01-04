
job('Spring petclinic - Seed job for default-java-pipeline-factory') {
    steps {
        downstreamParameterized {
            trigger('default-java-pipeline-factory') {
                parameters {
                    predefinedProp("project", "Spring petclinic")
                    predefinedProp("git_scm_url", "https://github.com/spring-projects/spring-petclinic.git")
                }
            }
        }
    }
}

/*queue('Acme airlines - Seed job for pipeline-generator')*/
queue('Spring petclinic - Seed job for default-java-pipeline-factory')