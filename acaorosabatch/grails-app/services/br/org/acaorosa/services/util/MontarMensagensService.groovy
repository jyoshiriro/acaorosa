package br.org.acaorosa.services.util

import grails.gsp.PageRenderer;
import groovy.text.SimpleTemplateEngine

import java.util.Map;

import br.org.acaorosa.dominio.Deputado
import br.org.acaorosa.dominio.Discurso
import br.org.acaorosa.dominio.Noticia;
import br.org.acaorosa.dominio.Parametro;
import br.org.acaorosa.dominio.Proposicao;
import br.org.acaorosa.dominio.Tipos;

class MontarMensagensService {

	PageRenderer groovyPageRenderer

	String tipoConteudo

	private Tipos itipos

	public Tipos getTipos() {
		if (!itipos) {
			itipos = Tipos.first()
		}
		itipos
	}

	public inserirDadosCamara(Noticia noticia) {
		noticia.autor = tipos.autorCamara
		noticia.autorEmail = tipos.autorCamaraEmail
		noticia.autorFacebook = tipos.autorCamaraFacebook
		noticia.autorTwitter = tipos.autorCamaraTwitter
		noticia.autorTelefones = tipos.autorCamaraTelefones
		noticia.autorSite = tipos.autorCamaraSite
	}

	public inserirDadosDeputado(Noticia noticia, Discurso discurso) {
		if (discurso?.deputado) {
			noticia.autor = discurso.deputado.nomePartidoCompleto
			noticia.autorEmail = discurso.deputado.email
			noticia.autorFacebook = discurso.deputado.perfilFacebook
			noticia.autorTwitter = discurso.deputado.perfilTwitter
			noticia.autorTelefones = discurso.deputado.telefones
			noticia.autorSite = discurso.deputado.getUrlDetalhes()
		} else {
			noticia.autor=discurso.nomePartidoDeputadoAntigo
		}
	}

	public inserirDadosDeputado(Noticia noticia, Proposicao proposicao) {
		if (proposicao?.autor) {
			noticia.autor = proposicao.autor.nomePartidoCompleto
			noticia.autorEmail = proposicao.autor.email
			noticia.autorFacebook = proposicao.autor.perfilFacebook
			noticia.autorTwitter = proposicao.autor.perfilTwitter
			noticia.autorTelefones = proposicao.autor.telefones
			noticia.autorSite = proposicao.autor.getUrlDetalhes()
		} else {
			noticia.autor=proposicao.nomeAutor
		}
	}

	public inserirDadosCNJ(Noticia noticia) {
		noticia.autor = tipos.autorCNJ
		noticia.autorEmail = tipos.autorCNJEmail
		noticia.autorFacebook = tipos.autorCNJFacebook
		noticia.autorSite = tipos.autorCNJSite
		noticia.autorTelefones = tipos.autorCNJTelefones
		noticia.autorTwitter = tipos.autorCNJTwitter
	}

	public inserirDadosSenado(Noticia noticia) {
		noticia.autor = tipos.autorSenado
		noticia.autorEmail = tipos.autorSenadoEmail
		noticia.autorFacebook = tipos.autorSenadoFacebook
		noticia.autorSite = tipos.autorSenadoSite
		noticia.autorTelefones = tipos.autorSenadoTelefones
		noticia.autorTwitter = tipos.autorSenadoTwitter
	}

	public inserirDadosEBC(Noticia noticia) {
		noticia.autor = tipos.autorEBC
		noticia.autorEmail = tipos.autorEBCEmail
		noticia.autorFacebook = tipos.autorEBCFacebook
		noticia.autorSite = tipos.autorEBCSite
		noticia.autorTelefones = tipos.autorEBCTelefones
		noticia.autorTwitter = tipos.autorEBCTwitter
	}

	public inserirDadosSTF(Noticia noticia) {
		noticia.autor = tipos.autorSTF
		noticia.autorEmail = tipos.autorSTFEmail
		noticia.autorFacebook = tipos.autorSTFFacebook
		noticia.autorSite = tipos.autorSTFSite
		noticia.autorTelefones = tipos.autorSTFTelefones
		noticia.autorTwitter = tipos.autorSTFTwitter
	}

	/**
	 * @param tipoConteudo enqueteCamara, proposicaoCamara, votacaoCamara, discursoCamara, noticiaCNJ
	 * @param tipoMensagem
	 * @param atributos
	 * @return
	 */
	public getConteudo(String tipoConteudo, String tipoMensagem, Map atributos) {
		def conteudo = groovyPageRenderer.render(template:"/${tipoConteudo}/${tipoMensagem}", model:atributos)
		if (!conteudo) {
			throw new IllegalArgumentException("Não foi possível preencher o conteúdo de mensagem solicitado para ${tipoConteudo}/${tipoMensagem} - ${atributos}.")
		}
		conteudo
	}

	/**
	 * @param tipoConteudo tipoConteudo enqueteCamara, proposicaoCamara, votacaoCamara, discursoCamara, noticiaCNJ
	 * @param noticia
	 */
	public void preencherConteudos(String tipoConteudo, Noticia noticia) {
		noticia.conteudoEmail=getConteudo(tipoConteudo, "email", noticia.properties)
		noticia.conteudoFacebook=getConteudo(tipoConteudo, "facebook", noticia.properties)
		noticia.conteudoTwitter=getConteudo(tipoConteudo, "twitter", noticia.properties)
		//		noticia.conteudoMobile=getConteudo(tipoConteudo, "mobile", noticia.properties)
	}

	/**
	 * Recuperar a URL dos Parametros montada a partir de parâmetros injetados na URL
	 * @param parametrosValores {@link Map} com os parâmetros a serem substituidos (opcional)
	 * @return A String com a URL e seus parâmetros preenchidos, se for o caso
	 */
	String getUrlAtualizacao(siglaParametro, parametrosValores) {
		def texto = Parametro.findBySigla(siglaParametro).valor

		Writable template = new SimpleTemplateEngine().createTemplate(texto).make(parametrosValores)

		return template.toString();
	}
}
