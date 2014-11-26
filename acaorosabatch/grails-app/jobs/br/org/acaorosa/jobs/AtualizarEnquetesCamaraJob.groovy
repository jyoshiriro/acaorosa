package br.org.acaorosa.jobs

import groovy.util.logging.Log4j;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import br.org.acaorosa.dominio.Noticia;
import br.org.acaorosa.dominio.PalavrasChave
import br.org.acaorosa.dominio.Parametro;
import br.org.acaorosa.dominio.Tipos;
import br.org.acaorosa.services.util.MontarMensagensService;
import br.org.acaorosa.util.PalavrasChaveUtil;

import com.sun.org.apache.xml.internal.dtm.ref.DTMNodeList;

@Log4j
class AtualizarEnquetesCamaraJob extends AbstractJob {
	
    static triggers = {
		cron name: "AtualizarEnquetesCamara", cronExpression: "0 0 1 * * ?"
		//simple repeatInterval: 20000l // execute job once in 5 seconds
    }

	def montarMensagensService
	
	private String urlEnquentesCamara
	private String urlPesquisaEnquete
	
	// TODO: preencher a partir do banco no "init()"
	void init() {
		urlEnquentesCamara = Parametro.findBySigla('url_enquete_camara').valor
		urlPesquisaEnquete = Parametro.findBySigla('url_pesquisa_enquete_camara').valor
	}
	
	def execute() {
		init()
		
		String url = urlEnquentesCamara
		def xpathLinksEnquetes = "//a[.='Clique aqui para saber mais']"
		def xpathTitulosEnquetes = "//span[@class='titulo fechado']"
		DTMNodeList nodesLinks = getNodes(url, xpathLinksEnquetes)
		DTMNodeList nodesTitulos = getNodes(url, xpathTitulosEnquetes)

		def linkNoticiaMaisRecente = ""
		def tituloNoticiaMaisRecente = ""
		
		nodesLinks.eachWithIndex { it, id ->
			if (id==0) linkNoticiaMaisRecente = it
		}
		nodesTitulos.eachWithIndex { it, id ->
			if (id==0) tituloNoticiaMaisRecente = it.textContent
		}
		// println linkNoticiaMaisRecente
		
		// link da página da notícia da enquete mais recente
		def urlNoticia = linkNoticiaMaisRecente.getAttribute("href")
		
		// link da página da enquete mais recente
		def urlEnquete = getURLEnquete(urlNoticia)
		
		if (!urlEnquete) {
			return
		}
		if (Noticia.countByUrl(urlEnquete)>0) {
			return
		}
		
		Noticia noticia = new Noticia()
		
		noticia.tipo=tipos.tipoEnqueteCamara
		noticia.url=urlEnquete
		noticia.urlAuxiliar=urlNoticia
		noticia.titulo=tituloNoticiaMaisRecente
		
		noticia.autor=tipos.autorCamara
		noticia.autorSite=urlEnquentesCamara
		noticia.autorEmail=tipos.autorCamaraEmail
		noticia.autorFacebook=tipos.autorCamaraFacebook
		noticia.autorTwitter=tipos.autorCamaraTwitter
		noticia.autorTelefones=tipos.autorCamaraTelefones
		
		noticia.dia = new Date()
		montarMensagensService.preencherConteudos("enqueteCamara", noticia)
		
		noticia.insert()
		
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
