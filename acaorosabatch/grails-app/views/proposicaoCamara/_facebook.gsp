Foi apresentada uma interessante proposição na Câmara dos Deputados:
Autor: ${autor.encodeAsRaw()}, em ${dia.format('dd/MM/yyyy')}

${titulo.encodeAsRaw()}

${detalhes.encodeAsRaw()}

Para lê-la na íntegra e/ou marcá-la para acompanhamento, acesse: 
${url.encodeAsRaw()}.

Quer falar com ${autor}?
<g:if test="${autorEmail}">
Email: ${autorEmail}
</g:if>
<g:if test="${autorTelefones}">
Telefone: ${autorTelefones.encodeAsRaw()}
</g:if>
<g:if test="${autorSite}">
Página no site da Câmara dos Deputados: ${autorSite.encodeAsRaw()}
</g:if>
<g:if test="${autorTwitter}">
Twitter: http://twitter.com/${autorTwitter}
</g:if>
<g:if test="${autorFacebook}">
Facebook: http://facebook.com/${autorFacebook}
</g:if>
