package br.org.acaorosa.jobs

import br.org.acaorosa.services.atualizacoes.AtualizarMensagensService;



class AtualizarMensagensJob {
	
    static triggers = {
      simple repeatInterval: 50000l // execute job once in 5 seconds
	  //cron name: "AtualizarMensagens", cronExpression: "* 55 5 * * ?"
    }
	
	def atualizarMensagensService

    def execute() {
		
		try {
			atualizarMensagensService.atualizar()
			log.debug("Geração de Mensagens concluída com sucesso")
		} catch (Exception e) {
			log.error("Erro ao tentar a gerar as mensagens: ${e.message}")
			e.printStackTrace()
		}
    }
}
