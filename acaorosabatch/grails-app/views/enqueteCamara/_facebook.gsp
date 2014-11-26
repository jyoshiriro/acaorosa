Nova Enquete da Câmara dos Deputados: ${titulo.encodeAsRaw()}

Para votar, acesse ${url.encodeAsRaw()}.

Para ler a notícia da enquete, acesse ${urlAuxiliar.encodeAsRaw()}.

Para falar com a Câmara dos Deputados:
<g:if test="${autorEmail}">
Email: ${autorEmail}
</g:if>
<g:if test="${autorTelefones}">
Telefone: ${autorTelefones.encodeAsRaw()}
</g:if>
<g:if test="${autorTwitter}">
Twitter: http://twitter.com/${autorTwitter}
</g:if>
<g:if test="${autorFacebook}">
Facebook: http://facebook.com/${autorFacebook}
</g:if>
