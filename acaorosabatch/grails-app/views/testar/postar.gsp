<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta name="layout" content="main"/>
<title>Teste de envio de mensagem</title>
</head>
<body>
  <div class="body">
  	<g:form action="postar">
  		Mensagem Timeline:
  		<input name="mp"/><br>
  		Rede: Facebook <input name="rede" type="radio" value="face"/>
  		Twitter <input name="rede" type="radio" value="twitter"/><br>
  		<input type="submit" value="Postar"/>
  	</g:form>
  </div>
</body>
</html>