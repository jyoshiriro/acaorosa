package br.org.acaorosa.jobs

import grails.gsp.PageRenderer

class EnviarEmailsJob {
	
/*
	cronExpression: "s m h D M W Y"
					 | | | | | | `- Year [optional]
					 | | | | | `- Day of Week, 1-7 or SUN-SAT, ?
					 | | | | `- Month, 1-12 or JAN-DEC
					 | | | `- Day of Month, 1-31, ?
					 | | `- Hour, 0-23
					 | `- Minute, 0-59
					 `- Second, 0-59
	*/
	
    static triggers = {
      cron name: "enviarEmailsJob", cronExpression: "0/10 * * * * ?", startDelay:100
    }
	
    def mailService
	PageRenderer groovyPageRenderer

    def execute() {
		def conteudo = groovyPageRenderer.render(template:"/emailTeste", model:[nomeDest:'Yoshiiii!!'])
        mailService.sendMail {
			to "jyoshiriro@gmail.com"
			subject "Notícia Ação Rosa"
			html conteudo
        }
    }
}
