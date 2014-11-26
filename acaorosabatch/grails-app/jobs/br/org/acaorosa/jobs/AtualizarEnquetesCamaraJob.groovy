package br.org.acaorosa.jobs

import groovy.util.logging.Log4j;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import br.org.acaorosa.dominio.Noticia;
import br.org.acaorosa.dominio.PalavrasChave
import br.org.acaorosa.dominio.Parametro;
import br.org.acaorosa.dominio.Tipos;
import br.org.acaorosa.services.atualizacoes.AtualizarEnqueteCamaraService;
import br.org.acaorosa.services.util.MontarMensagensService;
import br.org.acaorosa.util.PalavrasChaveUtil;

import com.sun.org.apache.xml.internal.dtm.ref.DTMNodeList;

@Log4j
class AtualizarEnquetesCamaraJob {
	
	
	def atualizarEnqueteCamaraService
	
    static triggers = {
		cron name: "AtualizarEnquetesCamara", cronExpression: "0 0 1 * * ?"
//		simple repeatInterval: 20000l // execute job once in 5 seconds
    }

	def execute() {
		try {
			atualizarEnqueteCamaraService.atualizar()
			log.debug("Atualização de Enquete da Câmara concluída com sucesso")
		} catch (Exception e) {
			log.error("Erro ao tentar a Atualização de Enquete da Câmara : ${e.message}")
			e.printStackTrace()
		}
		
	}
	
}
