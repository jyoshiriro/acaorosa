package br.org.acaorosa.jobs



class AtualizarProposicoesCamaraJob {
    static triggers = {
      simple repeatInterval: 5000l // execute job once in 5 seconds
    }

    def execute() {
        // execute job
    }
}
