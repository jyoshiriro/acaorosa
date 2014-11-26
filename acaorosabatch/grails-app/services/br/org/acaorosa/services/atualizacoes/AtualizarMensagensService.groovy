package br.org.acaorosa.services.atualizacoes

import br.org.acaorosa.dominio.Discurso;
import br.org.acaorosa.dominio.Noticia;
import br.org.acaorosa.dominio.Parametro;
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
		def discursos = Discurso.findAllByMensagemMontadaEmail(false)
		for (discurso in discursos) {
			Noticia noticia = new Noticia()
			
			noticia.tipo=getTipos().tipoDiscursoCamara
			
			noticia.url=discurso.urlDetalhes
			noticia.titulo=discurso.sumario
			
			montarMensagensService.inserirDadosDeputado(noticia, discurso)
			montarMensagensService.preencherConteudos("discursoCamara", noticia)
			noticia.insert()
			
			discurso.mensagemMontadaEmail=true
			discurso.save()
		}
	}
	
	def atualizarProposicoesCamara() {
		
	}
	
	def atualizarDiscursoAleatorio() {
		
	}
	
	def atualizarProposicaoAleatoria() {
		
	}
	
}
