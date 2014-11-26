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

import groovy.text.SimpleTemplateEngine
import br.org.acaorosa.util.URLUtil

class Discurso {

	static mapWith = "mongo"
	
	String id
	String codigo
	Date data
	Integer numeroSessao
	String cdFaseSessao
	Integer numeroOrador
	String horaInicio
	Integer numeroQuarto
	Integer numeroInsercao
	String sumario
	
	Deputado deputado
	String nomePartidoDeputadoAntigo // caso o deputado não esteja na base
	
	Boolean mensagemMontadaEmail = false
	Boolean mensagemMontadaFacebook = false
	Boolean mensagemMontadaTwitter = false
	Boolean mensagemMontadaMobile = false
	
	static transients = ['urlDetalhes']
	
	static constraints = {
		codigo(maxSize:16)
		cdFaseSessao(maxSize:5)
		horaInicio(maxSize:8)
		sumario(maxSize:4096)
		nomePartidoDeputadoAntigo nullable:true 
		cdFaseSessao nullable:true 
	}
	
	static mapping = {
		deputado(cascade:'all')
	}

	public String getUrlDetalhes() {

// http://www.camara.gov.br/internet/sitaqweb/TextoHTML.asp
// ?etapa=${etapa}&nuSessao=${nuSessao}&nuQuarto=${nuQuarto}&nuOrador=${nuOrador}&nuInsercao=${nuInsercao}
// &dtHorarioQuarto=${horario}&sgFaseSessao=${cdFaseSessao}&Data=${data}&txApelido=${nomeParlamentar}

		def texto = Parametro.findBySigla('url_discurso_deputado_dia').valor
		def valores = [etapa:numeroSessao,nuSessao:codigo,nuQuarto:numeroQuarto,nuOrador:numeroOrador,nuInsercao:numeroInsercao,horario:horaInicio,data:data.format('dd/MM/yyyy'),nomeParlamentar:"${deputado.nomeParlamentar} - ${deputado.getPartidoCompleto()}".encodeAsURL()]
		valores.each{
			//it.value=it.value.toString().encodeAsURL()
		} 
		Writable template = new SimpleTemplateEngine().createTemplate(texto).make(valores)
		def urllonga = template.toString()
		
		urllonga
	}

}




