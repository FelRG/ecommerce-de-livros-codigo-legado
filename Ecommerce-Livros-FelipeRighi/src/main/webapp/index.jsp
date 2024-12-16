<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bem-vindo ao E-Commerce LivroLumina</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style2.css">
</head>
<body>
    <div class="container">
        <img src="${pageContext.request.contextPath}/static/images/logo.jpg" width="100" height="100" class="d-inline-block align-top" alt="" loading="lazy">
        <h1>Bem-vindo ao E-Commerce LivroLumina</h1>
        <a href="${pageContext.request.contextPath}/cadastro.jsp" class="btn btn-primary btn-lg">Cadastre-se</a>
        <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-success btn-lg">Entrar</a>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>