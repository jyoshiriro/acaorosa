package br.org.acaorosa.util

import br.org.acaorosa.dominio.PalavrasChave

class PalavrasChaveUtil {

	private static List<PalavrasChave> palavrasChave
	
	public static isConteudoCandidato(String conteudo) {
		if (!PalavrasChaveUtil.palavrasChave) {
			PalavrasChaveUtil.palavrasChave=PalavrasChave.list()
		}
		conteudo = conteudo.toLowerCase()
		for (pc in palavrasChave) {
			if (conteudo.contains(pc.getPc())) {
				return true
			}
		}
		return false
	}
	
	/*public static setPalavrasChave(List<PalavrasChave> palavrasChave) {
		try {
			PalavrasChaveUtil.palavrasChave=palavrasChave
			println "foi"
		} catch (e) {
			e.printStackTrace()
		}
	}*/
}
