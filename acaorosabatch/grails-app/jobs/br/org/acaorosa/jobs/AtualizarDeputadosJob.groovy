package br.org.acaorosa.jobs

import br.org.acaorosa.services.atualizacoes.AtualizarDeputadoService



class AtualizarDeputadosJob {
/*
	 cronExpression: "s m h D M W Y"
					  | | | | | | `- Year [optional]
					  | | | | | `- Day of Week, 1-7 or SUN-SAT, ?
					  | | | | `- Month, 1-12 or JAN-DEC
					  | | | `- Day of Month, 1-31, ?
					  | | `- Hour, 0-23
					  | `- Minute, 0-59
					  `- Second, 0-59
	 */
	 
	static triggers = {
		//simple name: 'atualizarDeputadosJob', repeatInterval: 15000l // execute job once in 5 seconds
		cron name: "atualizarDeputadosJob", cronExpression: "0 0 2 * * ?", startDelay:1000
	}

	def atualizarDeputadoService

	def concurrent = false
	
	def execute() {
		try {
//			atualizarDeputadoService.atualizar()
			log.debug("Atualização Geral dos registros de Cadastro de Deputados concluída com sucesso")
		} catch (Exception e) {
			log.error("Erro ao tentar a Atualização Geral dos registros de Cadastro de Deputados: ${e.message}")
			e.printStackTrace()
		}
	}
}
