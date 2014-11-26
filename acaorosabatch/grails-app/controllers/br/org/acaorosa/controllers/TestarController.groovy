package br.org.acaorosa.controllers

import grails.plugin.springsecurity.annotation.Secured;
import br.org.acaorosa.dominio.Discurso;
import br.org.acaorosa.dominio.Proposicao;

class TestarController {

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
}
