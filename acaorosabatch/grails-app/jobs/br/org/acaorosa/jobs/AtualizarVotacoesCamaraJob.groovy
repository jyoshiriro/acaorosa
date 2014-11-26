package br.org.acaorosa.jobs

import br.org.acaorosa.services.atualizacoes.AtualizarProposicaoCamaraService;



class AtualizarVotacoesCamaraJob {
	
	def atualizarVotacaoCamaraService
	
    static triggers = {
      //simple repeatInterval: 60000l, startDelay: 60000l // execute job once in 5 seconds
	  cron name: "AtualizarVotacoesCamara", cronExpression: "0 45 3 * * ?"
    }

    def execute() {
        try {
			atualizarVotacaoCamaraService.atualizar()
			log.debug("Atualização Geral dos registros de Votações de Proposições concluída com sucesso")
		} catch (Exception e) {
			log.error("Erro ao tentar a Atualização Geral dos registros de Votações de Proposições: ${e.message}")
			e.printStackTrace()
		}
    }
}
