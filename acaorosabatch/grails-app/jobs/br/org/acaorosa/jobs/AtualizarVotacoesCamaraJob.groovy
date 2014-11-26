package br.org.acaorosa.jobs



class AtualizarVotacoesCamaraJob {
    static triggers = {
      simple repeatInterval: 5000l // execute job once in 5 seconds
	  cron name: "AtualizarVotacoesCamara", cronExpression: "0 45 3 * * ?"
    }

    def execute() {
        // execute job
    }
}
