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
package br.org.acaorosa.services.atualizacoes

import groovy.util.logging.Log4j
import groovy.util.slurpersupport.GPathResult
import br.org.acaorosa.dominio.Deputado
import br.org.acaorosa.util.ImagensUtil

/**
 * Atualizar a tabela de Deputados. Os que estiverem na tabela e não chegarem no XML são marcados com "ativo=false".
 */
@Log4j
class AtualizarDeputadoService extends AtualizadorEntidade {
	
	@Override
	public String getSiglaDeParametro() {
		// 'http://www.camara.gov.br/SitCamaraWS/Deputados.asmx/ObterDeputados'
		return 'url_listagem_deputados';
	}
	
	def atualizar() {
		
		GPathResult xmlr = getXML(getUrlAtualizacao(null))
		
		def chavesRecebidos = [] // coleta os Ids recebidos para saber quais deputados não são mais ativos 
		log.debug("${xmlr.childNodes().size()} deputados chegaram no XML")
		
		for (dep in xmlr.deputado) {
			Deputado.withNewTransaction { tx->
			 
			def ideCadastroA = dep.ideCadastro.toInteger()
			chavesRecebidos+=ideCadastroA
			
			def atributos = [ideCadastro: ideCadastroA, condicao: dep.condicao.toString(), matricula: dep.matricula.toInteger(), nome: dep.nome.toString().toUpperCase(), nomeParlamentar: dep.nomeParlamentar.toString().toUpperCase(),  sexo: dep.sexo.toString(), uf:dep.uf.toString(), siglaPartido: dep.partido.toString(), fone: dep.fone.toString(), email:dep.email.toString(), ativo:true]
			
			Deputado entidade = Deputado.where {ideCadastro==ideCadastroA && ativo}.find()
			if (!entidade) {
				// tenta pegar por "apelido", partido e uf, pois pode ter sido cadastrado no momento de registro de votos (isso não vem normalizado no XML de votações)
				entidade = Deputado.where{nomeParlamentar==dep.nomeParlamentar?.toString()?.trim() && partido.sigla==dep.partido?.toString()?.trim() && uf==dep.uf?.toString()?.trim()}.find()
			}
			
			if (entidade) { // já existe o registro, atualize os dados e limpa as comissões
				entidade.properties=atributos
				log.debug("Deputado ${ideCadastroA} possivelmente atualizado")
			} else { // ainda não existe. Persista agora
				entidade = new Deputado(atributos)
				
				entidade.save(flush:true)
				
				// salvando a miniatura local
				def nomeArquivo = "deputado-${entidade.id}.jpg"
				
				ImagensUtil.getMiniatura(entidade.getUrlFoto(),nomeArquivo)
				
				log.debug("Deputado ${ideCadastroA} salvo no banco")
			}

		}

	
		}
		
		def inativos = Deputado.executeUpdate("update Deputado set ativo=false where ideCadastro not in (:ids)",[ids:chavesRecebidos])
		
		if (inativos)	
			log.debug("${chavesRecebidos} deputados marcados como inativos")
			
		log.debug("Atualização de Deputados concluída com sucesso")
	}
}
