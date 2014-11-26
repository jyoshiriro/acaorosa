Foi proferido um interessante discurso na Câmara dos Deputados:

Autor: ${autor.encodeAsRaw()}. Em ${dia.format('dd/MM/yyyy')}.
Resumo:
${detalhes.encodeAsRaw()}

Para ler o discurso na íntegra, acesse: ${url.encodeAsRaw()}

Quer falar com ${autor.encodeAsRaw()}?
<g:if test="${autorFacebook}">
Facebook: http://facebook.com/${autorFacebook}
</g:if>
<g:if test="${autorEmail}">
Email: ${autorEmail}
</g:if>
<g:if test="${autorTelefones}">
Telefone: ${autorTelefones.encodeAsRaw()}
</g:if>
<g:if test="${autorSite}">
Página no site da Câmara dos Deputados: ${autorSite}
</g:if>
<g:if test="${autorTwitter}">
Twitter: http://twitter.com/${autorTwitter}
</g:if>