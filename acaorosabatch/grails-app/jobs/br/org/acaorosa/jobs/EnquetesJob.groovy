package br.org.acaorosa.jobs

import com.sun.org.apache.xml.internal.dtm.ref.DTMNodeList;



class EnquetesJob {
	
    static triggers = {
      simple repeatInterval: 5000l // execute job once in 5 seconds
    }

   	// TODO: preencher a partir do banco no "init()"
	private String urlEnquentes
	private String urlPesquisaEnquete

	// TODO: preencher a partir do banco no "init()"
	void init() {
		urlEnquentes = "http://www2.camara.leg.br/agencia-app/listaEnquete"
		urlPesquisaEnquete = "http://www2.camara.leg.br/agencia-app/votarEnquete/enquete/"
	}
	
	def execute() {
		init() 
		
		String url = urlEnquentes
		def xpath = "//a[.='Clique aqui para saber mais']"
		DTMNodeList nodes = getNodes(url, xpath)

		def linkNoticiaMaisRecente = ""
		
		nodes.eachWithIndex { it, id ->
			if (id==0) linkNoticiaMaisRecente = it
		}
		// println linkNoticiaMaisRecente
		
		// link da página da notícia da enquete mais recente
		def urlNoticia = linkNoticiaMaisRecente.getAttribute("href")
		
		def urlEnquete = getURLEnquete(urlNoticia)
		
		// link da página da enquete mais recente
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
		String inicioUrl = urlPesquisaEnquete
		int ini = conteudoHTML.indexOf(inicioUrl)
		int fim = conteudoHTML.indexOf("\"", ini)
		String url = conteudoHTML.substring(ini,fim)
		url
	}
}
