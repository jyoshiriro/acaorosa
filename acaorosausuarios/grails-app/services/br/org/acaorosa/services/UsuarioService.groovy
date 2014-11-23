/*
 * Copyright 2013 de José Yoshiriro (jyoshiriro@gmail.com) e Raimundo Norberto (raimundonorberto@gmail.com)
 * Este arquivo é parte do programa Olho na Câmara.
 * 
 * O Olho na Câmara é um software livre; você pode redistribuí-lo e/ou modificá-lo dentro
 * dos termos da GNU Affero General Public License como publicada pela Fundação do Software Livre
 * (FSF); na versão 3 da Licença. Este programa é distribuído na esperança que possa ser
 * útil, mas SEM NENHUMA GARANTIA; sem uma garantia implícita de ADEQUAÇÃO a qualquer
 * MERCADO ou APLICAÇÃO EM PARTICULAR. Veja a licença para maiores detalhes. Você deve ter
 * recebido uma cópia da GNU Affero General Public License, sob o título 'LICENCA.txt', junto com
 * este programa, se não, acesse http://www.gnu.org/licenses/
 */
package br.org.acaorosa.services

import groovy.util.logging.Log4j
import org.springframework.social.facebook.api.Facebook
import org.springframework.social.facebook.api.FacebookProfile
import org.springframework.social.facebook.api.impl.FacebookTemplate
import br.org.acaorosa.dominio.*

@Log4j
class UsuarioService {
	

	/**
	 * Atualiza o nome do usuário com seu nome em sua Rede Social no momento do cadastro 
	 * @param usuario
	 * @return
	 */
	def atualizaNome(Usuario usuario) {
		FacebookUser usuarioFacebook = FacebookUser.findByUser(usuario)
		Facebook facebook = new FacebookTemplate(usuarioFacebook.accessToken)
		FacebookProfile fbProfile = facebook.userOperations().userProfile
		usuario.nome = fbProfile.name
		usuario.save()
		log.debug "Usuario ${usuario.username} atualizado. Nome: ${usuario.nome}"
	}
			
}
