package br.org.acaorosa.services.util

import br.org.acaorosa.dominio.Noticia;
import br.org.acaorosa.util.PalavrasChaveUtil;

class RssService {

	def formatos = ['EEE, dd MMM yyyy HH:mm:ss Z',"yyyy-MM-dd'T'HH:mm:ss","yyyy-MM-dd'T'HH:mm:ss'Z'"]
	/**
	 * @param urlFeed
	 * @param noticiaBaseAutor instância de Noticia com os dados do autor
	 * @return
	 */
	List<Noticia> getNoticiasFeed(String urlFeed) {
		def xmlFeed = new XmlParser().parse(urlFeed);

		def itensFeed = []

		(0..< xmlFeed.channel.item.size()).each {

			def item = xmlFeed.channel?.item?.get(it);
			if (PalavrasChaveUtil.isConteudoCandidato(item.title?.text()) ||
				PalavrasChaveUtil.isConteudoCandidato(item.description?.text())) {
				Noticia noticia = new Noticia()
				noticia.url = (item.guids?.text())?:item.link.text()
				if (!Noticia.countByUrl(noticia.url)) {
					noticia.titulo = item.title.text()
					noticia.detalhes = item.description.text().replace("<div class=\"feed-description\">", "").replace("<br /></div>", "")
					for (fmt in formatos) {
						try {
							noticia.dia = Date.parse(fmt, item.pubDate.text());
						} catch (Exception e) {}
					}
					itensFeed << noticia
				}
			}
		}
		itensFeed
	}
}
