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

import br.org.acaorosa.dominio.Deputado;
import br.org.acaorosa.dominio.Proposicao
import br.org.acaorosa.dominio.TipoProposicao;
import br.org.acaorosa.util.PalavrasChaveUtil;
import groovy.util.logging.Log4j
import groovy.util.slurpersupport.GPathResult


/**
 * Atualizar a tabela de Proposicões. Essa classe Inclui e Exclui registros também.
 * A exclusão ocorre quando a proposição chega como "arquivada"  
 */
@Log4j
class AtualizarProposicaoCamaraService extends AtualizadorEntidade {

	@Override
	public String getSiglaDeParametro() {
		//"http://www.camara.gov.br/SitCamaraWS/Proposicoes.asmx/ListarProposicoes?numero=&datApresentacaoIni=&datApresentacaoFim=&autor=&parteNomeAutor=&siglaPartidoAutor=&siglaUFAutor=&generoAutor=&codEstado=&codOrgaoEstado=&emTramitacao=&idTipoAutor=&ano=${ano}&sigla=${sigla}"
		return 'url_listagem_proposicoes';
	}

	def atualizar() {

		def tipos = TipoProposicao.list().collect{it.sigla} //['PL','PEC']
		def anos = [2006]//[2012,2013] //Proposicao.PRIMEIRO_ANO..(new Date().calendarDate.year)

		l1:for (tipo in tipos) {
			l2:for (ano in anos) {
				
				def urlT = getUrlAtualizacao([ano:ano.toString(),sigla:tipo])
				
				GPathResult xmlr = null
				try {
					xmlr = getXML(urlT)
				} catch (Exception e) {
					log.error("A url ${urlT} não retornou XML válido: ${e.message}")
					continue l2;
				}
				
				log.debug("${xmlr.childNodes().size()} proposições chegaram no XML")
				
				l3:for (prop in xmlr.proposicao) { 
					
					// WA
//					if (prop.numero?.toString()!='1992')
//						return false
					// WA
					// Se a proposição estiver arquivada, não será cadastrada no banco
					def situacaoA = prop.situacao.descricao?.toString()
					
					def siglaA = prop.tipoProposicao.sigla.toString()
					def numeroA = prop.numero?.toString()?.toInteger()
					def anoA = prop.ano?.toString()
					def desc = "${siglaA} ${numeroA}/${anoA}"
					
					def conteudoTotal = prop.apreciacao.txtApreciacao.toString()+" "+prop.txtEmenta.toString()+" "+prop.txtExplicacaoEmenta.toString()+" "+prop.ultimoDespacho.txtDespacho?.toString()
					if (!PalavrasChaveUtil.isConteudoCandidato(conteudoTotal)) {
						log.debug("Proposição ${desc} não contém as palavras chaves necessárias")
						continue l3;
					}
					
					if (situacaoA.toLowerCase().equals("arquivada")) {
						def tipoPropA =	TipoProposicao.findBySigla(siglaA)
						Proposicao proposicaoT = Proposicao.findByNumeroAndAnoAndTipoProposicao(numeroA,anoA,tipoPropA)
						if (proposicaoT) {
							proposicaoT.delete()
							log.debug("Proposição ${desc} agora está arquivada e foi excluída do banco.")
						} else {
							log.debug("Proposição ${desc} já está arquivada e não será salva.")
							continue l3;
						}
					}
					
					def idA = prop.id.toString().trim()
					TipoProposicao tipoP = TipoProposicao.findBySigla(siglaA)
					
					def atributos = [idProposicao:prop.id?.toString()?.toInteger(), tipoProposicao:tipoP, numero: numeroA, ano:anoA, dataApresentacao: Date.parse('d/M/yyyy',prop.datApresentacao.toString()), situacao: situacaoA, txtApreciacao: prop.apreciacao.txtApreciacao.toString(), txtEmenta: prop.txtEmenta.toString(), txtExplicacaoEmenta: prop.txtExplicacaoEmenta.toString(),txtUltimoDespacho: prop.ultimoDespacho.txtDespacho?.toString()]
					
					if (prop.ultimoDespacho.datDespacho.toString()) 
						atributos+=[ultimoDespacho: Date.parse('d/M/yyyy',prop.ultimoDespacho.datDespacho?.toString())]
					
					Deputado autor = Deputado.findByIdeCadastro(prop.autor1.idecadastro?.toString())
					if (autor) {
						atributos+=[autor:autor]
					} else {
						atributos+=[nomeAutor:prop.autor1.txtNomeAutor?.toString()]
					}
					
					
					Proposicao entidade = Proposicao.where {idProposicao==idA}.find()
					
					if (entidade) { // já existe o registro, atualize os dados
						entidade.properties=atributos
						entidade.save()
						log.debug("Tipo de proposição ${desc} atualizado")
					} else { // ainda não existe. Persista agora
						entidade = new Proposicao(atributos)
						entidade.insert()
						
						if (entidade.errors.errorCount>0) {
							log.error("Proposição ${desc} NÃO foi salva devido a erros: ${entidade.errors}")
						} else {
							log.debug("Proposição ${desc} salva no banco")
						}
					}
					
				}
			
			} // for de anos
		} // for de tipos
		log.debug("Atualização de Proposições concluída com sucesso")
	}

}
