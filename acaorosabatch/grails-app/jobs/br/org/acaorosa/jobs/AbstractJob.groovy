package br.org.acaorosa.jobs

import grails.gsp.PageRenderer;
import br.org.acaorosa.dominio.Noticia
import br.org.acaorosa.dominio.PalavrasChave;
import br.org.acaorosa.dominio.Tipos

abstract class AbstractJob {

	private List<PalavrasChave> palavrasChave
	private Tipos tipos
	PageRenderer groovyPageRenderer
	
	String tipoConteudo
	
	private isNoticiaCandidata(String conteudo) {
		conteudo = conteudo.toLowerCase()
		for (pc in getPalavrasChave()) {
			if (conteudo.contains(pc.getPc())) {
				return true
			}
		}
		return false
	}
	
	
	abstract void init()
	
	abstract execute()
	
	public List<PalavrasChave> getPalavrasChave() {
		if (!palavrasChave)
			palavrasChave = PalavrasChave.list()
		return palavrasChave
	}
	
	public Tipos getTipos() {
		if (!tipos) {
			tipos = Tipos.first()
		}
		tipos
	}
	
	public getConteudo(String tipo, Map atributos) {
		def conteudo = groovyPageRenderer.render(template:"/${tipoConteudo}/${tipo}", model:atributos)
		conteudo
	}
	
	
	private void preencherConteudos(Noticia noticia) {
		noticia.conteudoEmail=getConteudo("email", noticia.properties)
//		noticia.conteudoFacebook=getConteudo("facebook", noticia.properties)
//		noticia.conteudoTwitter=getConteudo("twitter", noticia.properties)
//		noticia.conteudoMobile=getConteudo("mobile", noticia.properties)
	}
}
