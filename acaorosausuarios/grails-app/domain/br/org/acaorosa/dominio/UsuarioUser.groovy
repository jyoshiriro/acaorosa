package br.org.acaorosa.dominio

import org.apache.commons.lang.builder.HashCodeBuilder

class UsuarioUser implements Serializable {

	
	private static final long serialVersionUID = 1

	Usuario usuario
	User user

	boolean equals(other) {
		if (!(other instanceof UsuarioUser)) {
			return false
		}

		other.usuario?.id == usuario?.id &&
		other.user?.id == user?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (usuario) builder.append(usuario.id)
		if (user) builder.append(user.id)
		builder.toHashCode()
	}

	static UsuarioUser get(long usuarioId, long userId) {
		UsuarioUser.where {
			usuario == Usuario.load(usuarioId) &&
			user == User.load(userId)
		}.get()
	}

	static boolean exists(long usuarioId, long userId) {
		UsuarioUser.where {
			usuario == Usuario.load(usuarioId) &&
			user == User.load(userId)
		}.count() > 0
	}

	static UsuarioUser create(Usuario usuario, User user, boolean flush = false) {
		def instance = new UsuarioUser(usuario: usuario, user: user)
		instance.save(flush: flush, insert: true)
		instance
	}

	static boolean remove(Usuario u, User r, boolean flush = false) {
		if (u == null || r == null) return false

		int rowCount = UsuarioUser.where {
			usuario == Usuario.load(u.id) &&
			user == User.load(r.id)
		}.deleteAll()

		if (flush) { UsuarioUser.withSession { it.flush() } }

		rowCount > 0
	}

	static void removeAll(Usuario u, boolean flush = false) {
		if (u == null) return

		UsuarioUser.where {
			usuario == Usuario.load(u.id)
		}.deleteAll()

		if (flush) { UsuarioUser.withSession { it.flush() } }
	}

	static void removeAll(User r, boolean flush = false) {
		if (r == null) return

		UsuarioUser.where {
			user == User.load(r.id)
		}.deleteAll()

		if (flush) { UsuarioUser.withSession { it.flush() } }
	}

	static constraints = {
		user validator: { User r, UsuarioUser ur ->
			if (ur.usuario == null) return
			boolean existing = false
			UsuarioUser.withNewSession {
				existing = UsuarioUser.exists(ur.usuario.id, r.id)
			}
			if (existing) {
				return 'userRole.exists'
			}
		}
	}

	static mapping = {
		id composite: ['user', 'usuario']
		version false
		datasource 'mysql'
	}
}
