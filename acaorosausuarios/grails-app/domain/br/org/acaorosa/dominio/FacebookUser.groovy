package br.org.acaorosa.dominio

import br.org.acaorosa.dominio.Usuario

class FacebookUser {

	static mapping = {
		datasource 'mysql'
	 }
	
    Long uid
    String accessToken
    Date accessTokenExpires

    static belongsTo = [user: Usuario]

    static constraints = {
        uid unique: true
    }
}
