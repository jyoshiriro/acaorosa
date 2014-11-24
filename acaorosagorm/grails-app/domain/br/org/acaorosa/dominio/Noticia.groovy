package br.org.acaorosa.dominio

class Noticia {

	static mapWith = "mongo"
	
	static mapping = {
		version(false)
	}
	
	String id
	
	Date dia
	
	String url
	String urlAuxiliar
	
	String tipo
	
	String titulo
	
	String conteudoEmail
	String conteudoFacebook
	String conteudoTwitter
	String conteudoMobile
	
	String autor
	String autorSite
	String autorTwitter
	String autorFacebook
	String autorEmail
	String autorTelefones
	
	boolean processadaEmail
	boolean processadaFacebook
	boolean processadaTwitter
	boolean processadaMobile
	
	static constraints = {
		url nullable:true 
		urlAuxiliar nullable:true 
		tipo nullable:true 
		titulo nullable:true 
		conteudoEmail nullable:true 
		conteudoTwitter nullable:true 
		conteudoFacebook nullable:true 
		conteudoMobile nullable:true 
		autorSite nullable:true 
		autorFacebook nullable:true 
		autorTwitter nullable:true 
		autorEmail nullable:true 
		autorTelefones nullable:true
		
		processadaEmail nullable:true  
		processadaFacebook nullable:true  
		processadaTwitter nullable:true  
		processadaMobile nullable:true  
	}
	
	def beforeValidate() {
		if (!dia) {
			dia = new Date()
			processadaEmail = false
			processadaTwitter = false
			processadaFacebook = false
			processadaMobile = false
		}
	}
	
}
