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
import br.org.acaorosa.dominio.Discurso;
import br.org.acaorosa.util.PalavrasChaveUtil;
import groovy.util.logging.Log4j
import groovy.util.slurpersupport.GPathResult

@Log4j
class AtualizarDiscursoCamaraService extends AtualizadorEntidade {

	def usuarioService
	
	@Override
	public String getSiglaDeParametro() {
		// http://www.camara.gov.br/sitcamaraws/SessoesReunioes.asmx/ListarDiscursosPlenario?codigoSessao=&parteNomeParlamentar=&siglaPartido=&siglaUF=&dataIni=${data}&dataFim=${data}
		return "url_discursos";
	}
	
	@Override
	public Object atualizar() {
		Date proximaAtualizacao = new Date()-20//Date.parse('dd/MM/yyyy', '26/09/2013') // new Date()
		Date fim = new Date()//Date.parse('dd/MM/yyyy', '26/09/2013') // new Date()
		
		String urlT = null
		GPathResult xmlr = null
		int limite = 45
		def quant = 0
		while (!quant) {
			
			urlT = "file:///home/yoshiriro/Downloads/ListarDiscursosPlenario.xml"
//			urlT = getUrlAtualizacao([data1:proximaAtualizacao.format("dd/MM/yyyy"), data2:fim.format("dd/MM/yyyy")])
			try {
				xmlr = getXML(urlT)
				quant = xmlr.childNodes()?.size()
			} catch (Exception e) {
				log.error("A url ${urlT} não retornou XML válido: ${e.message}")
			}
			proximaAtualizacao--
			
			if (--limite<0)  {
				def msg = "Mais de $limite tentativas não conseguiram recuperar um XML válido de Atualização de Discurso"
				log.error(msg)
				throw new Exception(msg)
			}
		}
		proximaAtualizacao++
		
		log.debug("Chegaram ${xmlr.childNodes()?.size()} discursos dos deputados chegaram no XML de ${urlT}")
		
		for (sessao in xmlr.sessao) {
			
			def nosSessao = sessao.childNodes()
			// o numeroSessao tem que ser incrementado pois no site de discurso o valor é +1			
			def attrSessao = [codigo:nosSessao[0].text().trim(), data:Date.parse('dd/MM/yyyy',nosSessao[0].text().trim()), numeroSessao:nosSessao[0].text().trim().toInteger()+1]
			String codSessao = attrSessao.codigo 
			Date dataSessao = attrSessao.data
			
			l1:for (faseSessao in sessao.childNodes()[4].childNodes()) {
				
				// etapa=${etapa}&nuSessao=${nuSessao}&nuQuarto=${nuQuarto}&nuOrador=${nuOrador}&nuInsercao=${nuInsercao}&dtHorarioQuarto=${horario}&Data=${data}&txApelido=${nomeParlamentar}
				
				l2:for (discurso in faseSessao.childNodes()[2].childNodes()) {
					
					def sumario = discurso.childNodes()[4].text()
					
					if (!PalavrasChaveUtil.isConteudoCandidato(sumario)) {
						log.debug("Discurso ${codSessao} de ${dataSessao} não contém as palavras chaves necessárias")
						continue l2;
					}
					
					if (Discurso.countByCodigoAndData(codSessao,dataSessao)) {
						log.debug("Discurso ${codSessao} de ${dataSessao} já existe na base")
						continue l2;
					}
					
					def orador = discurso.childNodes()[0]
					def siglaA = orador.childNodes()[2].text().trim()
					
					// verificando se é um Deputado (há casos em que o orador não é Deputado)
					/*if (!siglaA) {
						log.debug('Orador que não é Deputado - Este discurso não será salvo')
						continue
					}*/
					

					def horaInicioA = discurso.childNodes()[1].text().trim()
					horaInicioA = horaInicioA.substring(horaInicioA.indexOf(" ")+1,horaInicioA.lastIndexOf(":"))
					
					def nomeDeputadoA = orador.childNodes()[1].text()
					for (ch in ["(","[","-",","]) { 
						nomeDeputadoA = nomeDeputadoA.indexOf(ch)>0?nomeDeputadoA.substring(0,nomeDeputadoA.indexOf(ch)):nomeDeputadoA
					}
					nomeDeputadoA = nomeDeputadoA.trim().toUpperCase()
					def ufA = orador.childNodes()[3].text().trim()
					
					def deputadoA = Deputado.findByNomeParlamentarAndUf(nomeDeputadoA,ufA) 
					if (!deputadoA) {
						log.debug("Deputado ${nomeDeputadoA}/${ufA} não estava na base.")
						// se ele não existia, nenhum usuário o acompanha
						attrSessao+=[nomePartidoDeputadoAntigo:"${nomeDeputadoA} ${siglaA} ${ufA}"]
					}
					
					def numeroOrador = orador.childNodes()[0].text().toInteger()
					def numeroQuarto = discurso.childNodes()[2].text().toInteger()
					def numeroInsercao = discurso.childNodes()[3].text().toInteger()
					
					def atributos = [horaInicio:horaInicioA,numeroOrador:numeroOrador, numeroQuarto:numeroQuarto, numeroInsercao:numeroInsercao, sumario:sumario]
					atributos+=attrSessao
					
					atributos+=[deputado:deputadoA]
					Discurso entidade = new Discurso(atributos)
					entidade.save()
					log.debug("Discurso ${entidade.id} salvo no banco")
					
					// esse 'ultimoDiaDiscurso' de Deputado é atualizado em PostagemDiscursoDeputado
					/*def isMaisRecente = deputadoA.ultimoDiaDiscurso?dataSessao.after(deputadoA.ultimoDiaDiscurso):true
					if (isMaisRecente) { // só persiste a despesa se for mais recente que a última data de atualização
						if (Discurso.where{deputado==deputadoA && data==dataSessao && codigo==codSessao && horaInicio==horaInicioA}.count()==0) {
							atributos+=[deputado:deputadoA]
							Discurso entidade = new Discurso(atributos)
							entidade.save()
							log.debug("Discurso ${entidade.id} salvo no banco")
						}
					}*/
					
				} // loop de discursos
			} // loop de fasesSessao
		}
		log.debug("Atualização de Discursos de Deputados concluída com sucesso")
	}

}
