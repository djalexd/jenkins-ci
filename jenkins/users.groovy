/*
 * Source of inspiration:
 * https://github.com/jenkinsci/jenkins-scripts
 */

import hudson.model.*;
import jenkins.model.*;
import hudson.security.HudsonPrivateSecurityRealm;
import hudson.security.GlobalMatrixAuthorizationStrategy;
import hudson.security.FullControlOnceLoggedInAuthorizationStrategy;

def addUser(fullName, email, password, keys) {

	user = hudson.model.User.get(email)
    user.setFullName(fullName)

    email = new hudson.tasks.Mailer.UserProperty(email)
    user.addProperty(email)

    password = hudson.security.HudsonPrivateSecurityRealm.Details.fromPlainPassword(password)
    user.addProperty(password)

    //keys = new org.jenkinsci.main.modules.cli.auth.ssh.UserPropertyImpl('#{new_resource.public_keys.join('\n')}')
    //user.addProperty(keys)
    
    user.save()
}

def instance = Jenkins.getInstance()

def hudsonRealm = new HudsonPrivateSecurityRealm(false)
instance.setSecurityRealm(hudsonRealm)

addUser("Admin", "admin@payu.ro", "12345", null)
//addUser("JohnDoe", "john.doe@payu.ro", "12345", null)

/*def strategy = new GlobalMatrixAuthorizationStrategy()
strategy.add(Jenkins.ADMINISTER, "admin")
strategy.add(Jenkins.READ, "admin")
strategy.add(Jenkins.RUN_SCRIPTS, "admin")
strategy.add(Jenkins.READ, "JohnDoe")
instance.setAuthorizationStrategy(strategy)*/

def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
instance.setAuthorizationStrategy(strategy)

instance.save()