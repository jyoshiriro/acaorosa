package br.org.acaorosa.dominio

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate

import br.org.acaorosa.dominio.Usuario

class FacebookUser {

	static mapping = { datasource 'mysql' }

	Long uid
	String accessToken
	Date accessTokenExpires

	static belongsTo = [user: Usuario]

	static constraints = { uid unique: true }

	void atualizarNomeCompleto() {
		if (!user?.nome) {
			Facebook facebook = new FacebookTemplate(accessToken)
			def pf = facebook.userOperations().userProfile
			def nomeCompleto = pf.firstName
			if (pf.middleName) nomeCompleto+=" ${pf.middleName}"
			if (pf.lastName) nomeCompleto+=" ${pf.lastName}"
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
