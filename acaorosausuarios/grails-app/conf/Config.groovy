// configuration for plugin testing - will not be included in the plugin zip

log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'
}

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'br.org.acaorosa.dominio.Usuario'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'br.org.acaorosa.dominio.UsuarioUser'
grails.plugin.springsecurity.authority.className = 'br.org.acaorosa.dominio.User'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	'/':                              ['permitAll'],
	'/index':                         ['permitAll'],
	'/index.gsp':                     ['permitAll'],
	'/index_':                     ['permitAll'],
	'/usuario/**':                     ['permitAll'],
	'/assets/**':                     ['permitAll'],
	'/**/js/**':                      ['permitAll'],
	'/**/css/**':                     ['permitAll'],
	'/**/images/**':                  ['permitAll'],
	'/**/favicon.ico':                ['permitAll']
]

grails.plugin.springsecurity.facebook.domain.classname='br.org.acaorosa.dominio.FacebookUser'
grails.plugin.springsecurity.facebook.appId='1500821580194465'
grails.plugin.springsecurity.facebook.secret='196a6db9809b96cb2ab8a83fe380872a'

grails.plugin.springsecurity.twitter.domain.classname='br.org.acaorosa.dominio.TwitterUser'
grails.plugin.springsecurity.twitter.key='ieORzf7pbEQ3BoQ9LLQTdu1Qo'
grails.plugin.springsecurity.twitter.consumerKey='ieORzf7pbEQ3BoQ9LLQTdu1Qo'
grails.plugin.springsecurity.twitter.consumerSecret='SSAaEAXJn4aQ9uAzL5hzPfpVTkv5uD6JDsvFGoyOnWkbx5w3va'