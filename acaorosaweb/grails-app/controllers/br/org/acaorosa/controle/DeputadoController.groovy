package br.org.acaorosa.controle

import br.org.acaorosa.dominio.Deputado;

class DeputadoController {
	
	def mongo
	
	def index() {
		//Deputado dep = new Deputado(nome:"Zé Ruela", email:"wwww@teste.com", perfilCD: "pp1", minifoto: "".bytes, telefones:"9090902")
		//dep.insert()
		
		def lista = Deputado.list() 
		//def lista2 = Deputado.findAllByNome("//Ruela//")
		

//		def db = mongo.getDB("acaorosa")
//		db.deputado.insert(nome:'kkkkk',email:'kkkk@wwwwww.com.br')

		[lista:lista]
	}
	
	def filtrar() {
		def lista = Deputado.findAllByNomeLike("%${params.filtro}%")
//		def lista = Deputado.search(params.filtro)
		flash.lista2 = lista
		redirect(action:'index')
	}
	
	
	def salvar() {
		def dep = new Deputado(params)
		dep.insert()
		flash.novo = dep
		if (!dep.hasErrors()) {
			flash.message="Deputado salvo com sucesso"
		}
		redirect(action:'index')
	}
	
	def novo() {
		def dep = new Deputado(params)
		render(view:'form', model:[dep:dep])
	}
	
	def excluir() {
		def dep = Deputado.get(params.id)
		dep.delete()
		flash.message="Deputado excluido com sucesso"
		redirect(action:'index')
	}
}
