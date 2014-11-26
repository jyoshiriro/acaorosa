<p>Nova Enquete da Câmara dos Deputados:</p>
<h2>
	${titulo}
</h2>

<p>
	Para votar, acesse 
	<b>${url}</b>.
</p>
<p>
	Para ler a notícia da enquete, acesse 
	<b>${urlAuxiliar}</b>.
</p>

<p>Para falar com a Câmara dos Deputados:</p>
<ul>
	<g:if test="${autorEmail}">
		<li>Email: ${autorEmail}</li>
	</g:if>
	<g:if test="${autorTelefones}">
		<li>Telefone: ${autorTelefones}</li>
	</g:if>
	<g:if test="${autorTwitter}">
		<li>Twitter: http://twitter.com/${autorTwitter}</li>
	</g:if>
	<g:if test="${autorFacebook}">
		<li>Facebook: http://facebook.com/${autorFacebook}</li>
	</g:if>
</ul>