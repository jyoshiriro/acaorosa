package br.org.acaorosa.controllers

import org.springframework.social.facebook.api.Facebook
import org.springframework.social.facebook.api.impl.FacebookTemplate
import org.springframework.social.twitter.api.Twitter
import org.springframework.social.twitter.api.impl.TwitterTemplate

import br.org.acaorosa.dominio.Discurso
import br.org.acaorosa.dominio.FacebookUser
import br.org.acaorosa.dominio.Proposicao
import br.org.acaorosa.dominio.TwitterUser
import br.org.acaorosa.jobs.EnviarEmailsJob;

class TestarController {

	EnviarEmailsJob enviarEmailsJob
	
	def deputados() {
		String pp = "<table>"
		Set deps = []
		def props = Proposicao.list()
		for (prop in props.findAll {it.autor}) {
			deps.add(prop?.autor?.nome+" "+prop?.autor?.partidoCompleto)
		}
		def discs = Discurso.list()
		for (disc in discs.findAll {it.deputado}) {
			deps.add(disc?.deputado?.nome+" "+disc?.deputado?.partidoCompleto)
		}
		
		def i = 1
		deps = deps.sort()
		for (dep in deps) {
			def hrefFace = "https://www.google.com.br/webhp?ion=1&espv=2&ie=UTF-8#q=${dep}%20site%3Afacebook.com"
			def hrefTwitter = "https://www.google.com.br/webhp?ion=1&espv=2&ie=UTF-8#q=${dep}%20site%3Atwitter.com"
			pp+="<tr>"
			pp+="<td>"+(i++)+" "+dep+"</td><td> <a href='${hrefFace}'>Procurar no Face</a> </td> <td><a href='${hrefTwitter}'>Procurar no Twitter</a></td>"
			pp+="</tr>"
		}
		pp+="</table>"
		render pp
	}
	
	def postar() {
		if (!params.mp) {
			return
		}
		if (params.rede=='face') {
			FacebookUser usuarioFacebook = FacebookUser.last()
			Facebook facebook = new FacebookTemplate(usuarioFacebook.accessToken)
			facebook.feedOperations().updateStatus(params.mp)
		}
		if (params.rede=='twitter') {
			TwitterUser usuarioFacebook = TwitterUser.last()
			Twitter facebook = new TwitterTemplate(usuarioFacebook.token)
			facebook.timelineOperations().updateStatus(params.mp)
		}
		flash.message="Postagem enviada com sucesso. Veja a timeline"
	}
	
	def enviarEmails() {
		enviarEmailsJob.execute()
	}
}
