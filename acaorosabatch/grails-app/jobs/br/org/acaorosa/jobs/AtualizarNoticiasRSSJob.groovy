package br.org.acaorosa.jobs

import groovy.util.logging.Log4j;


@Log4j
class AtualizarNoticiasRSSJob {
	
    def atualizarNoticiasRssService
	
	static triggers = {
      simple repeatInterval: 60000l // execute job once in 5 seconds
//	  cron name: "AtualizarNoticiasCNJ", cronExpression: "0 0 3 * * ?"
    }

    def execute() {
        try {
			atualizarNoticiasRssService.atualizar()
			log.debug("Atualização Geral de notícias de RSS concluída com sucesso")
		} catch (Exception e) {
			log.error("Erro ao tentar a Atualização Geral de notícias de RSS: ${e.message}")
			e.printStackTrace()
		}
    }
}
