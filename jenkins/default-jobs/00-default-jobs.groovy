
job("branch-auto-configurer") {
	displayName "Project branch auto-configurer"
	keepDependencies(false)
	disabled(false)
	parameters {
		stringParam("project", null, "Base name of this job, e.g. project name")
		stringParam("git_scm_url", null, "Git SCM URL of this job")
	}
	scm {
		git {
			remote {
				github('djalexd/jenkins-job-templates', 'ssh')
				credentials('2c1fa93e-c1fd-407c-b771-aa724d20ffa6')
			}
			clean()
			relativeTargetDir('jenkins-job-templates')
		}
	}
	steps {
		dsl {
			text(readFileFromWorkspace('jenkins-job-templates/branch-auto-configurer.groovy'))
			removeAction('DISABLE')
			ignoreExisting()
		}
	}
}

job("default-java-pipeline-factory") {
	displayName "Default Java pipeline factory"
	keepDependencies(false)
	disabled(false)
	parameters {
		stringParam("project", null, "Base name of this job, e.g. project name")
		stringParam("git_scm_url", null, "Git SCM URL of this job")
	}
	scm {
		git {
			remote {
				github('djalexd/jenkins-job-templates', 'ssh')
				credentials('2c1fa93e-c1fd-407c-b771-aa724d20ffa6')
			}
			clean()
			relativeTargetDir('jenkins-job-templates')
		}
	}
	steps {
		dsl {
			text(readFileFromWorkspace('jenkins-job-templates/pipeline-factory.groovy'))
			removeAction('DISABLE')
			ignoreExisting()
		}
	}
}

job("pipeline-generator") {
	keepDependencies(false)
	disabled(false)
	parameters {
		stringParam("prefix", null, "Job prefix")
	}
	scm {
		git {
			remote {
				github('djalexd/jenkins-job-templates', 'ssh')
				credentials('2c1fa93e-c1fd-407c-b771-aa724d20ffa6')
			}
			clean()
			relativeTargetDir('jenkins-job-templates')
		}
	}
	steps {
		dsl {
			text(readFileFromWorkspace('jenkins-job-templates/pipeline-generator.groovy'))
			removeAction('DISABLE')
			ignoreExisting()
		}
	}
}