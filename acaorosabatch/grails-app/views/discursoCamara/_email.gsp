<p>Foi proferido um interessante discurso na Câmara dos Deputados:</p><br/>
<i>Autor: <b>${autor}</b></i>
<p>
	<i>Resumo</i><br/>
	${titulo}
</p>
<p>
	Para ler o discurso na íntegra, acesse: 
	<b>${url}</b>.
</p>

<p>Quer falar com ${autor}?</p>
<ul>
	<g:if test="${autorEmail}">
		<li>Email: ${autorEmail}</li>
	</g:if>
	<g:if test="${autorTelefones}">
		<li>Telefone: ${autorTelefones}</li>
	</g:if>
	<g:if test="${autorSite}">
		<li>Página no site da Câmara dos Deputados: ${autorSite}</li>
	</g:if>
	<g:if test="${autorTwitter}">
		<li>Twitter: http://twitter.com/${autorTwitter}</li>
	</g:if>
	<g:if test="${autorFacebook}">
		<li>Facebook: http://facebook.com/${autorFacebook}</li>
	</g:if>
</ul>