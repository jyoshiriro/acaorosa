<p>Foi realizada uma interessante votação na Câmara dos Deputados:</p><br/>
<i>Em <b>${dia.format('dd/MM/yyyy')}</b></i> <br>
<i>Proposição: <b>${titulo}</b></i> <br>
<p>
	<i>Resumo</i><br/>
	${detalhes}
</p>
<p>
	Para maiores detalhes, acesse: 
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