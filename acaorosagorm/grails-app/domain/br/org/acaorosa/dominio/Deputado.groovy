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

	static constraints = {
		telefones unique:true, nullable:true
		perfilCD unique:true, nullable:true
		apelido blank:true, nullable:true,unique:true
		email blank:true, nullable:true, unique:true, email:true
		perfilFacebook blank:true, nullable:true, unique:true
		perfilTwitter blank:true , nullable:true, unique:true
		minifoto nullable:true
	}

	static transients = [
	     'nomePartidoCompleto',
		'partidoCompleto',
		'urlDetalhes'
	]
			
	String getNomePartidoCompleto() {
		"${nomeParlamentar} - ${partidoCompleto}"
	}

	String getPartidoCompleto() {
		"${siglaPartido}/${uf}"
	}

	static mapping = {
		//index nome:"text", apelido: "text"
		//index apelido:"text2"
	}

	public String getUrlDetalhes() {

		def texto = Parametro.findBySigla('url_deputado').valor
		def urllonga = texto+ideCadastro
		urllonga
	}
}
