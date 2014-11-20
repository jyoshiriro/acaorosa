package br.org.acaorosa.dominio;


class Deputado {

	String id
	String nome
	String apelido
	String telefones
	String email
	String perfilCD
	String perfilFacebook
	String perfilTwitter
	byte[] minifoto
	
	static constraints = {
		telefones unique:true
		perfilCD unique:true
		apelido blank:true, nullable:true,unique:true
		email blank:true, nullable:true, unique:true, email:true
		perfilFacebook blank:true, nullable:true, unique:true  
		perfilTwitter blank:true , nullable:true, unique:true   
		minifoto nullable:true 
	}
	
	static mapping = {
		//index nome:"text", apelido: "text"
		//index apelido:"text2"
	}
	
}
