<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta name="layout" content="main"/>
<title>Insert title here</title>
</head>
<body>
  <div class="body">
  	<g:renderErrors bean="${flash.novo}"/>
  	${flash.message}
  	<hr>
  	<g:each in="${lista}" var="d">
  		<g:link action="excluir" id="${d.id}">[x]</g:link>
  		${d.id} - ${d.nome} - ${d.telefones}
  		<br>
  	</g:each>
  	<hr>
  	<g:link action="novo"><b>Novo</b></g:link>
  	<hr>
  	<h2>Filtro</h2>
  	<g:form action="filtrar">
  		Buscar: <g:textField name="filtro"/> <br>
		<g:submitButton name="submit" value="Filtrar"/>  		
  	</g:form>
  	<g:each in="${flash.lista2}" var="d">
  		${d.id} - ${d.nome} - ${d.telefones} <br>
  	</g:each>
  	
  </div>
</body>
</html>