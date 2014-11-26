package br.org.acaorosa.jobs

import grails.gsp.PageRenderer;
import br.org.acaorosa.dominio.Noticia
import br.org.acaorosa.dominio.PalavrasChave;
import br.org.acaorosa.dominio.Tipos

abstract class AbstractJob {

	private Tipos tipos
	
	abstract void init()
	
	abstract execute()
	
	public Tipos getTipos() {
		if (!tipos) {
			tipos = Tipos.first()
		}
		tipos
	}
	
}
