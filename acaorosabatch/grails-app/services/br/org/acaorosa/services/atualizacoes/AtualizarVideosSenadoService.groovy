package br.org.acaorosa.services.atualizacoes

import groovy.util.logging.Log4j

import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

import br.org.acaorosa.dominio.Parametro
import br.org.acaorosa.util.PalavrasChaveUtil

import com.sun.org.apache.xml.internal.dtm.ref.DTMNodeList

@Log4j
class AtualizarVideosSenadoService {

	def atualizar() {
		String urlVideos = Parametro.findBySigla('url_videos_senado').valor
		String xml = urlVideos.toURL().text
		
		def xpathLinksEnquetes = "//a[.='Clique aqui para saber mais']"
		def xpathTitulosEnquetes = "//span[@class='titulo fechado']"
		DTMNodeList nodesLinks = getNodes(urlVideos, xpathLinksEnquetes)
		DTMNodeList nodesTitulos = getNodes(urlVideos, xpathTitulosEnquetes)

		def linkNoticiaMaisRecente = ""
		def tituloNoticiaMaisRecente = ""
		
		nodesLinks.eachWithIndex { it, id ->
			if (id==0) linkNoticiaMaisRecente = it
		}
		nodesTitulos.eachWithIndex { it, id ->
			if (id==0) tituloNoticiaMaisRecente = it.textContent
		}
	}
	
	private DTMNodeList getNodes(String url, String xpath) {
		String xml = url.toURL().text
		def builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
		def doc     = builder.parse(new ByteArrayInputStream(xml.bytes))
		def expr    = XPathFactory.newInstance().newXPath().compile(xpath)
		def nodes   = expr.evaluate(doc, XPathConstants.NODESET)
		return nodes
	}
	
	private String getURLEnquete(String urlNoticia) {
		String conteudoHTML = urlNoticia.toURL().text
		if (!PalavrasChaveUtil.isConteudoCandidato(conteudoHTML)) {
			return null
		}
		String inicioUrl = urlPesquisaEnquete
		int ini = conteudoHTML.indexOf(inicioUrl)
		int fim = conteudoHTML.indexOf("\"", ini)
		String url = conteudoHTML.substring(ini,fim)
		url
	}
}
