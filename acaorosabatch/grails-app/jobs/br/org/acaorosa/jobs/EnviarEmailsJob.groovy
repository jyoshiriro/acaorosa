package br.org.acaorosa.jobs

import javassist.bytecode.stackmap.BasicBlock.Catch;
import org.hibernate.SessionFactory
import br.org.acaorosa.dominio.Noticia;
import br.org.acaorosa.dominio.Usuario;
import grails.gsp.PageRenderer
import groovy.util.logging.Log4j;

@Log4j
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
//      cron name: "EnviarEmails", cronExpression: "* 30 5 * * ?"
	  simple repeatInterval: 20000l, startDelay:90000 // execute job once in 5 seconds
    }
	
    def mailService
	PageRenderer groovyPageRenderer
	SessionFactory sessionFactory
	
	void init() {}
	
    def execute() {
		def usuariosEmail = Usuario.where{
			receberEmail==true && enabled==true && email!=null
		}.list()
		def noticiasNaoPrecessadas = Noticia.findAllByProcessadaEmail(false)
		for (Noticia noticia in noticiasNaoPrecessadas) {
			def enviados = 0
			for (Usuario usuario:usuariosEmail) {
				def atributos = usuario.properties
				atributos += [conteudo:noticia.conteudoEmail]
				def conteudo = groovyPageRenderer.render(template:"/cabecalho-email", model:atributos)
				try {
			        mailService.sendMail {
						from "Ação Rosa <acaorosa@gmail.com>"
						to atributos.email
						subject "Informe Ação Rosa"
						html conteudo
			        } 
					enviados++
				} catch (e) {
					log.error("Erro ao tentar enviar o email: "+e.getMessage())
					e.printStackTrace()
		        }
			}
			// se ao menos 1 for enviado, marque a mensagem como "processadaEmail"
			// para não ser mais enviada no outro job
			if (enviados) {
				noticia.processadaEmail=true
				noticia.save()
			}
		}
    }
}
