package br.org.acaorosa.controllers

import grails.gsp.PageRenderer;
import br.org.acaorosa.dominio.Usuario;

class UsuarioController {

	static allowedMethods = [cadastrar:'POST']

	def springSecurityService

	def mailService
	PageRenderer groovyPageRenderer

	def index() {}
	
	def cadastrar() {
		Usuario usuario = Usuario.findByUsernameAndEmail(params.nome,params.email)
		if (usuario && usuario.enabled) {
			// já existe e confirmado
			flash.message="Você já está cadastrado conosco :)"
			redirect(uri:"/")
			return
		}
		if (usuario && !usuario.enabled) {
			usuario.delete()
		}
		usuario = new Usuario()
		usuario.receberEmail=true
		usuario.username = params.nome
		usuario.email = params.email
		usuario.password = "123"
		usuario.enabled=false
		def dataToken = new Date()
		dataToken+=7
		usuario.datatoken=dataToken
		usuario.idTemp=usuario.hashCode().longValue()*2
		def url = "usuario/confirmarCadastro/${usuario.idTemp}"
		def conteudo = groovyPageRenderer.render(template:"/email-cadastro",model:usuario.properties+[urlConfirmacao:url])
		try {
			mailService.sendMail {
				from "Ação Rosa <acaorosa@gmail.com>"
				to params.email
				subject "Confirme seu e-mail"
				html conteudo
			}
			flash.message="Foi enviado um email pra você. Acesse ele, verifique se não caiu no spam e confirme sua inscrição. Muito obrigado."
			usuario.insert()

		} catch (e) {
			flash.message="Erro ao tentar enviar o email. Por favor, tente novamente."
			log.error("Erro ao tentar enviar email para ${params.email}: "+e.getMessage())
			e.printStackTrace()
		}
		redirect(uri:"/")
	}
	
	def confirmarCadastro() {
		Usuario usuario = Usuario.where{idTemp==params.id.toLong()}.find()
		if (usuario && usuario?.datatoken?.time>=new Date().time) {
			usuario.enabled=true
			usuario.datatoken=null
			usuario.idTemp=null
			usuario.save()
			flash.message="${usuario.username}, seu cadastro foi confirmado com sucesso!"
		} else {
			flash.message="Seu pedido de cancelamento expirou. Solicite um novo cancelamento por motivos de segurança."
		}
		redirect(uri:"/")
	}
	
	def confirmarSaida() {
		Usuario usuario = Usuario.get(params.id)
		if (usuario?.datatoken?.time>=new Date().time) {
			usuario.delete()
			flash.message="${usuario.username}, seu cadastro foi cancelado com sucesso!"
		} else {
			flash.message="${usuario.username}, seu pedido de cancelamento expirou. Solicite um novo cancelamento por motivos de segurança."
		}
		redirect(uri:"/")
	}

	def unsubscribe() {
		Usuario usuario = Usuario.findByEmail(params.email?.trim())
		if (!usuario) {
			redirect(uri:"/")
			return
		}	
		def dataToken = new Date()
		dataToken+=1
		usuario.datatoken=dataToken
		def url = "usuario/confirmarSaida/${usuario.id}"
		mailService.sendMail {
			async true
			from "Ação Rosa <acaorosa@gmail.com>"
			to params.email
			subject "Confirme seu pedido de cancelamento"
			html groovyPageRenderer.render(template:"/email-cancelamento",model:usuario.properties+[urlConfirmacao:url])
		}
		usuario.save()
		flash.message="Foi enviado um email pra você. Acesse ele, verifique se não caiu no spam e confirme seu cancelamento. Muito obrigado."
		redirect(uri:"/")
	}
}
