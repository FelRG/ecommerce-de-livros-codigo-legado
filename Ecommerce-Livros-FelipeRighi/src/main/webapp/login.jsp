<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style1.css">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="login-container">
    <c:if test="${not empty erro}">
        <div class="alert alert-warning mt-3">${erro}</div>
    </c:if>
    <h4 class="text-center">Entrar no e-commerce LivroLumina</h4>
    <form action="login" method="post">
        <div class="form-group">
            <label for="email"><b>Email</b></label>
            <input type="email" class="form-control" id="email" placeholder="Email" name="email" maxlength="255" required>
        </div>
        <div class="form-group">
            <label for="senha"><b>Senha</b></label>
            <input type="password" class="form-control" id="senha" placeholder="Senha" name="senha" maxlength="255" required>
        </div>
        <button type="submit" class="btn btn-success btn-block" name="login" value="LOGAR">Entrar</button>
        <a href="${pageContext.request.contextPath}/" class="btn btn-secondary btn-block">Voltar</a>
    </form>

</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
