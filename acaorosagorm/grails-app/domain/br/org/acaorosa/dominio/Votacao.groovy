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
package br.org.acaorosa.dominio

class Votacao {
	
	static mapWith = "mongo"
	
	String id
	String resumo
	Date dataHoraVotacao
	String objVotacao
	boolean enviado = false
	
	static belongsTo = [proposicao:Proposicao]
	static hasMany = [votos:Voto]
	
	static mapping = {
		votos(sort:'deputado')
	}
	
	static constraints = {
		resumo(maxSize:2048)
		objVotacao(maxSize:256)
	}
}
