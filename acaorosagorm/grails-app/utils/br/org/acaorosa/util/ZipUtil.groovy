/*
 * Copyright 2013 de José Yoshiriro (jyoshiriro@gmail.com) e Raimundo Norberto (raimundonorberto@gmail.com)
 * Este arquivo é parte do programa Olho na Câmara.
 * 
 * O Olho na Câmara é um software livre; você pode redistribuí-lo e/ou modificá-lo dentro
 * dos termos da GNU Affero General Public License como publicada pela Fundação do Software Livre
 * (FSF); na versão 3 da Licença. Este programa é distribuído na esperança que possa ser
 * útil, mas SEM NENHUMA GARANTIA; sem uma garantia implícita de ADEQUAÇÃO a qualquer
 * MERCADO ou APLICAÇÃO EM PARTICULAR. Veja a licença para maiores detalhes. Você deve ter
 * recebido uma cópia da GNU Affero General Public License, sob o título 'LICENCA.txt', junto com
 * este programa, se não, acesse http://www.gnu.org/licenses/
 */
package br.org.acaorosa.util

import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

import org.apache.commons.io.IOUtils;


/**
 * Classe Utilitária para compactar e descompactar uma {@link String}
 * @author jyoshiriro
 *
 */
class ZipUtil {

	/**
	 * Compacta uma string em um array de bytes
	 * @param original
	 * @return O array de byte compactado ou <b>null</b> caso o original seja vazio
	 */
	static byte[] compactar(String original)  {
		if (!original)
			return null
		def targetStream = new ByteArrayOutputStream()
		def zipStream = new GZIPOutputStream(targetStream)
		zipStream.write(original.bytes)
		zipStream.close()
		def zipado = targetStream.toByteArray()
		targetStream.close()
		zipado
	}
	
	
	/**
	 * Descompacta um array de bytes em uma String
	 * @param original
	 * @return A String descompactada ou <b>null</b> caso o original seja vazio 
	 */
	static String descompactar(byte[] original)  {
		if (!original)
			return null
		GZIPInputStream zipStream = new GZIPInputStream(new ByteArrayInputStream(original))
		def descompactado = zipStream.text
		zipStream.close()
		descompactado
	}
	
	
	/**
	 * Descompacta o arquivo "AnoAtual.zip" em uma pasta acima da de trabalho da app e
	 * cria (sobrescrevendo) o "AnoAtual.xml"
	 * @param conteudoZip Conteúdo do arquivo "AnoAtual.zip"
	 * @return O conteúdo do arquivo "AnoAtual.xml"
	 */
	static byte[] descompactarAnoAtualZip(byte[] conteudoZip) {
		new File("../AnoAtual.zip").bytes=conteudoZip
		Process p = Runtime.getRuntime().exec("unzip -o -d ../ ../AnoAtual.zip")
		int exitVal = p.waitFor()
		def fis = new FileInputStream("../AnoAtual.xml")
		def conteudoXml = IOUtils.toByteArray(fis)
		fis.close()
		return conteudoXml
	}
	
	
}
