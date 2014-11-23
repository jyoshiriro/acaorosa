package br.org.acaorosa.dominio

class Usuario {

	transient springSecurityService

	String username
	String password
	String email
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	
	boolean receberEmail
	boolean receberFacebook
	boolean receberTwitter
	boolean receberMobile

	static transients = ['springSecurityService']

	static constraints = {
		username blank: false, unique: true
		password blank: false
		email nullable:true
	}

	static mapping = {
		password column: '`password`'
		datasource 'mysql'
	}

	Set<User> getAuthorities() {
		UsuarioUser.findAllByUsuario(this).collect { it.user }
	}
	
	def beforeValidate() {
		if (receberEmail==null)
			receberEmail=false
		if (receberFacebook==null)
			receberFacebook=false
		if (receberTwitter==null)
			receberTwitter=false
		if (receberMobile==null)
			receberMobile=false
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}
}
