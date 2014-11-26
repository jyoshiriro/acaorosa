package br.org.acaorosa.dominio
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate

import br.org.acaorosa.dominio.Usuario

class TwitterUser {

	static mapping = {
		datasource 'mysql'
	 }
	
    Long twitterId
    String username

    String tokenSecret
    String token

    static belongsTo = [user: Usuario]

    static constraints = {
        twitterId(unique: true, nullable: false)
        username(nullable: false, blank: false)
    }
	
	void atualizarNomeCompleto() {
		if (!user?.nome) { 
			Twitter tw = new TwitterTemplate(token)
			def pf = tw.userOperations().userProfile
			def nomeCompleto = pf.name
			user.nome=nomeCompleto
			user.save()
		}
	}
	
	def afterInsert() {
		//atualizarNomeCompleto()
	}
	
	def afterUpdate() {
		//atualizarNomeCompleto()
	}
}
