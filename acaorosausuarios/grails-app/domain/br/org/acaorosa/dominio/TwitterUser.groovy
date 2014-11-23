package br.org.acaorosa.dominio
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
}
