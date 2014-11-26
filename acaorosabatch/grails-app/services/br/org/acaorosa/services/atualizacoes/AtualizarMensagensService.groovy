package br.org.acaorosa.services.atualizacoes

import br.org.acaorosa.dominio.Discurso;
import br.org.acaorosa.dominio.Noticia;
import br.org.acaorosa.dominio.Parametro;
import br.org.acaorosa.dominio.Proposicao;
import br.org.acaorosa.dominio.Tipos;
import br.org.acaorosa.services.util.MontarMensagensService;
import groovy.util.logging.Log4j;

@Log4j
class AtualizarMensagensService {
	
	def montarMensagensService
	
	private Tipos tipos
	
	def atualizar() {
		atualizarDiscursosCamara()
		atualizarProposicoesCamara()
	}
	
	/**
	 * @param origem DiscursosCamara, ProposicoesCamara, VotacaoCamara, NoticiasCNJ
	 * @return
	 */
	def atualizar(String origem) {
		this."atualizar${origem}"()
	}
	
	Tipos getTipos() {
		if (!tipos) {
			tipos = Tipos.first()
		}
		tipos
	}

	def atualizarDiscursosCamara() {
		def discursos = Discurso.findAllByMensagemMontada(false)
		for (Discurso discurso in discursos) {
			Noticia noticia = new Noticia()
			
			noticia.tipo=getTipos().tipoDiscursoCamara
			
			noticia.url=discurso.urlDetalhes
			noticia.detalhes=discurso.sumario
			noticia.dia=discurso.data
			
			montarMensagensService.inserirDadosDeputado(noticia, discurso)
			montarMensagensService.preencherConteudos("discursoCamara", noticia)
			noticia.insert()
			
			discurso.mensagemMontada=true
			discurso.save()
		}
	}
	
	def atualizarProposicoesCamara() {
		def proposicoes = Proposicao.findAllByMensagemMontada(false)
		for (Proposicao proposicao in proposicoes) {
			Noticia noticia = new Noticia()
			
			noticia.tipo=getTipos().tipoProposicaoCamara
			
			noticia.url=proposicao.getUrlDetalhes()
			noticia.titulo=proposicao.txtApreciacao
			noticia.detalhes=proposicao.txtEmenta
			noticia.dia=proposicao.dataApresentacao
			
			montarMensagensService.inserirDadosDeputado(noticia, proposicao)
			montarMensagensService.preencherConteudos("proposicaoCamara", noticia)
			noticia.insert()
			
			proposicao.mensagemMontada=true
			proposicao.save()
		}
	}
	
	def atualizarDiscursoAleatorio() {
		
	}
	
	def atualizarProposicaoAleatoria() {
		
	}
	
}
