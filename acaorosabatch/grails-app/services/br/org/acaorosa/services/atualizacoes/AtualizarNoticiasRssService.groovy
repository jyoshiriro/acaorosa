package br.org.acaorosa.services.atualizacoes

import groovy.util.logging.Log4j;
import br.org.acaorosa.dominio.Noticia;
import br.org.acaorosa.dominio.Parametro;
import br.org.acaorosa.dominio.Tipos;
import br.org.acaorosa.services.util.MontarMensagensService;
import br.org.acaorosa.services.util.RssService;

@Log4j
class AtualizarNoticiasRssService {

	def montarMensagensService
	def rssService

	// TODO: Refatorar para não haver tanta repetição de código
	def atualizar() {
		Tipos tipos = Tipos.first()

		String url = Parametro.findBySigla("url_noticias_cnj").valor
		def noticiasCNJ = rssService.getNoticiasFeed(url)
		for (Noticia noticia in noticiasCNJ) {
			montarMensagensService.inserirDadosCNJ(noticia)
			montarMensagensService.preencherConteudos("noticiaGenerica", noticia)
			noticia.insert()
			if (noticia.hasErrors())
				log.error("Erro ao tentar salvar noticia CNJ: "+noticia.errors)
			else
				log.debug("Noticia CNJ salva com sucesso")
		}

		String urlSenado = Parametro.findBySigla("url_noticias_senado").valor
		def noticiasSenado = rssService.getNoticiasFeed(urlSenado)
		for (Noticia noticia in noticiasSenado) {
			montarMensagensService.inserirDadosSenado(noticia)
			montarMensagensService.preencherConteudos("noticiaGenerica", noticia)
			noticia.insert()
			if (noticia.hasErrors())
				log.error("Erro ao tentar salvar noticia Senado: "+noticia.errors)
			else
				log.debug("Noticia Senado salva com sucesso")
		}

		String urlStf = Parametro.findBySigla("url_noticias_stf").valor
		def noticiasStf = rssService.getNoticiasFeed(urlStf)
		for (Noticia noticia in noticiasStf) {
			montarMensagensService.inserirDadosSTF(noticia)
			montarMensagensService.preencherConteudos("noticiaGenerica", noticia)
			noticia.insert()
			if (noticia.hasErrors())
				log.error("Erro ao tentar salvar noticia STF: "+noticia.errors)
			else
				log.debug("Noticia STF salva com sucesso")
		}

		def siglasEBC = [
			"url_noticias_ebc",
			"url_noticias_cidadania_ebc"
		]

		for (sigla in siglasEBC) {
			String urlE = Parametro.findBySigla(sigla).valor
			def noticiasE = []
			try {
				noticiasE = rssService.getNoticiasFeed(urlE)
			} catch (e) {
				e.printStackTrace()
			}

			for (Noticia noticia in noticiasE) {
				montarMensagensService.inserirDadosEBC(noticia)
				montarMensagensService.preencherConteudos("noticiaGenerica", noticia)
				noticia.insert()
				if (noticia.hasErrors())
					log.error("Erro ao tentar salvar noticia EBC: "+noticia.errors)
				else
					log.debug("Noticia EBC salva com sucesso")
			}
		}
	}
}
