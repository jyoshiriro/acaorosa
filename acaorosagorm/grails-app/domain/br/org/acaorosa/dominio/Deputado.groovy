package br.org.acaorosa.dominio;

import br.org.acaorosa.dominio.Partido;


class Deputado {
	
	static mapWith = "mongo"
	
	String id
	String ideCadastro
	String matricula
	String nome
	String nomeParlamentar
	String condicao
	String apelido
	String telefones
	String email
	String sexo
	String uf
	String siglaPartido
	boolean ativo
	String perfilCD
	String perfilFacebook
	String perfilTwitter
	byte[] minifoto
	
	Partido partido
	
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
