package br.org.acaorosa.dominio

class Usuario {

	transient springSecurityService

	String nome
	String username
	String password
	String email
	
	Long idTemp
	Date datatoken
	
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	
	boolean receberEmail = false
	boolean receberFacebook = false
	boolean receberTwitter = false
	boolean receberMobile = false

	static transients = ['springSecurityService']

	static constraints = {
		nome nullable:true 
		username blank: false, unique: true
		password blank: false
		email nullable:true, unique: true
		datatoken nullable:true 
		idTemp nullable:true 
	}

	static mapping = {
		password column: '`password`'
		datasource 'mysql'
	}

	Set<User> getAuthorities() {
		try {
		UsuarioUser.findAllByUsuario(this).collect { it.user }
		} catch (e){
		[]
		}
	}
	
	def beforeValidate() {
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
