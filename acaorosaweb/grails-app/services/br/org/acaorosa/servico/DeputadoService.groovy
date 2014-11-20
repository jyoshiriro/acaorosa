package br.org.acaorosa.servico

import br.org.acaorosa.dominio.Deputado

class DeputadoService {

	def transactional = true
	
	def novo(Deputado dep) {
		dep.insert()
		dep.save()
	}
}
