package br.org.acaorosa.dominio

class User {

	String authority

	static mapping = {
		cache true
		datasource 'mysql'
	}

	static constraints = {
		authority blank: false, unique: true
	}
}
