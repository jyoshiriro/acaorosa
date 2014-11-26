package br.org.acaorosa.jobs

import br.org.acaorosa.services.atualizacoes.AtualizarProposicaoCamaraService
import groovy.util.logging.Log4j;


@Log4j
class AtualizarProposicoesCamaraJob {
	
	def atualizarProposicaoCamaraService
	
    static triggers = {
      //simple repeatInterval: 60000l // execute job once in 5 seconds
	  cron name: "AtualizarProposicoesCamara", cronExpression: "0 0 3 * * ?"
    }

    def execute() {
        try {
			atualizarProposicaoCamaraService.atualizar()
			log.debug("Atualização Geral dos registros de Cadastro de Proposições concluída com sucesso")
		} catch (Exception e) {
			log.error("Erro ao tentar a Atualização Geral dos registros de Cadastro de Proposições: ${e.message}")
			e.printStackTrace()
		}
    }
}
