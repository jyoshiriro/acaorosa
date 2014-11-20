package br.org.acaorosa.jobs



class DeputadosJob {
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
		cron name: "deputadosJob", cronExpression: "* 30 23 * * ?", startDelay:1000
	}

    def execute() {
        println "fooooooooi!!!"
    }
}
