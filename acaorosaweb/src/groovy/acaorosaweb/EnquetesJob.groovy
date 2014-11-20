package acaorosaweb

import com.sun.org.apache.xml.internal.dtm.ref.DTMNodeList
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

class EnquetesJob {
	
	// TODO: preencher a partir do banco no "init()"
	static String URL_ENQUETES
	static String URL_PESQUISA_ENQUETE

	static main(args) {
		EnquetesJob.init() 
		new EnquetesJob().execute() 
	}
	
	// TODO: preencher a partir do banco no "init()"
	static void init() {
		URL_ENQUETES = "http://www2.camara.leg.br/agencia-app/listaEnquete"
		URL_PESQUISA_ENQUETE = "http://www2.camara.leg.br/agencia-app/votarEnquete/enquete/"
	}
	
	def execute() {
		
		String url = EnquetesJob.URL_ENQUETES
		def xpath = "//a[.='Clique aqui para saber mais']"
		DTMNodeList nodes = getNodes(url, xpath)

		def linkNoticiaMaisRecente = ""
		
		nodes.eachWithIndex { it, id ->
			if (id==0) linkNoticiaMaisRecente = it
		}
		// println linkNoticiaMaisRecente
		
		// link da página da enquete mais recente
		def urlNoticia = linkNoticiaMaisRecente.getAttribute("href")
		
		def urlEnquete = getURLEnquete(urlNoticia)
		println urlEnquete
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
		String inicioUrl = EnquetesJob.URL_PESQUISA_ENQUETE
		int ini = conteudoHTML.indexOf(inicioUrl)
		int fim = conteudoHTML.indexOf("\"", ini)
		String url = conteudoHTML.substring(ini,fim)
		url
	}
}
