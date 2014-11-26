package br.org.acaorosa.jobs

import br.org.acaorosa.services.atualizacoes.AtualizarDiscursoCamaraService;
import br.org.acaorosa.services.atualizacoes.AtualizarProposicaoCamaraService
import groovy.util.logging.Log4j;


@Log4j
class AtualizarDiscursosCamaraJob {
	
	def atualizarDiscursoCamaraService
	
    static triggers = {
//      simple repeatInterval: 550000l // execute job once in 5 seconds
	  cron name: "AtualizarDiscursosCamara", cronExpression: "* * 3 * * ?"
    }

    def execute() {
        try {
			atualizarDiscursoCamaraService.atualizar()
			log.debug("Atualização Geral dos registros de Discursos concluída com sucesso")
		} catch (Exception e) {
			log.error("Erro ao tentar a Atualização Geral dos registros de Cadastro de Discursos: ${e.message}")
			e.printStackTrace()
		}
    }
}
