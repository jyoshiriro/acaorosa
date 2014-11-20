<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta name="layout" content="main"/>
<title>Cadastro de Deputado</title>
</head>
<body>
  <div class="body">
  	<g:form action="salvar">
  		<g:hiddenField name="id" value="${dep.id}"/>
  		Nome: <g:textField name="nome" value="${dep.nome}"/> <br>
  		Apelido: <g:textField name="apelido" value="${dep.apelido}"/> <br>
  		Telefones: <g:textField name="telefones" value="${dep.telefones}"/> <br>
  		E-mail: <g:textField name="email" value="${dep.email}"/> <br>
  		Perfil na CD: <g:textField name="perfilCD" value="${dep.perfilCD}"/> <br>
  		Perfil no Facebook: <g:textField name="perfilFacebook" value="${dep.perfilFacebook}"/> <br>
  		Perfil no Twitter: <g:textField name="perfilTwitter" value="${dep.perfilTwitter}"/> <br>
  		<g:submitButton name="submit" value="Salvar"/>
  	</g:form>
  </div>
</body>
</html>