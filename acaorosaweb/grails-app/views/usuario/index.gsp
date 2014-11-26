<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<title>Novo usuário assinante de e-mail</title>
</head>
<body>
	<div class="body">
		<g:form action="cadastrar">
			<div>
				<label for="login">Como devemos te chamar?</label>
				<g:textField name="nome" required="required" />
			</div>
			<div>
				<label for="email">Para qual e-email devemos te enviar
					nossas mensagens?</label>
				<g:textField name="email" type="email" required="required" />
			</div>
			<div>
				<input type="submit" value="Enviar">
			</div>
		</g:form>
	</div>
	<div>
		<sec:ifNotLoggedIn>
			<a href="/acaorosa/j_spring_security_facebook_redirect"
				title="Entrar com Facebook"> <img class="redes" alt=""
				src="${resource(dir:'images', file:'botao-face.png')}">
			</a>

			<a href="/acaorosa/j_spring_twitter_security_check" title="Entrar com Twitter">
				<img class="redes" alt=""
				src="${resource(dir:'images', file:'botao-twitter.png')}">
			</a>
		</sec:ifNotLoggedIn>
		<sec:ifLoggedIn>
			<sec:loggedInUserInfo field="username"></sec:loggedInUserInfo>
			
		</sec:ifLoggedIn>
	</div>
</body>
</html>