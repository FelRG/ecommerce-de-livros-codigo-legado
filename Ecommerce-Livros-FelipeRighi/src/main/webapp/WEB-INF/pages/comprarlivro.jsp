<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<c:set var="sessionId" value="${sessionScope.id}" />

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Comprar Livro</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <style>
        .navbar-custom {
            background-color: #343a40; /* Azul escuro */
        }
        .navbar-brand {
            color: white !important; /* Cor branca para o texto da logo */
        }
        .navbar-toggler-icon {
            color: white !important; /* Cor branca para o ícone */
        }
        .dropdown-menu .dropdown-item {
            color: black; /* Cor do texto do dropdown */
        }
        .dropdown-menu .dropdown-item:hover {
            background-color: #878787; /* Cor de fundo do item do dropdown ao passar o mouse */
            color: white; /* Cor do texto do dropdown ao passar o mouse */
        }
        .card {
            margin-top: 20px;
        }
        .nav-link {
            color:white;
        }
        .dropdown-toggle:hover {
            color: #878787; /* Altera a cor do ícone ao passar o mouse */
        }
        .corcinza:hover {
            color: #878787; /* Altera a cor do ícone ao passar o mouse */
        }
        .img-livro {
            width: 200px; /* Defina a largura desejada */
            height: 300px; /* Defina a altura desejada */
            object-fit: cover; /* Mantém o aspecto da imagem e preenche o espaço */
        }
        .livrocentralizado {
            display: flex;
            justify-content: center;
            align-items: center;
        }
    </style>
</head>
<body>
<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-custom">
    <a class="navbar-brand" href="reddashbord">
        <img src="${pageContext.request.contextPath}/static/images/logo.jpg" width="30" height="30" class="d-inline-block align-top" alt="" loading="lazy">
        LivroLumina
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="livrosDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Livros
                </a>
                <div class="dropdown-menu" aria-labelledby="livrosDropdown">
                    <a class="dropdown-item" href="livro?opcao=redaddlivro">Adicionar Livro</a>
                    <a class="dropdown-item" href="livro?opcao=mostrarlivrosavenda">Comprar Livro</a>
                </div>
            </li>
        </ul>
        <ul class="navbar-nav">
		
			<li class="nav-item">
                <a class="nav-link corcinza" href="carrinho?opcao=visualizarcarrinho">
                    <i class="fas fa-shopping-cart"></i> <!-- Ícone de carrinho de compras -->
                </a>
            </li>
			
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <i class="fas fa-user"></i> <!-- Ícone de usuário -->
                </a>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="usuario?opcao=redmeusdados">Meus Dados</a>
                    <a class="dropdown-item" href="venda?opcao=mostrarpedidos&&idCliente=<%= session.getAttribute("id") %>">Meus Pedidos</a>
                    <a class="dropdown-item" href="livro?opcao=leitura&&id=<%= session.getAttribute("id") %>">Meus Livros</a>
                    <a class="dropdown-item" href="usuario?opcao=redexcluirconta">Excluir Conta</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="logout">Sair</a>
                </div>
            </li>
        </ul>
    </div>
</nav>

<div class="container mt-5">
    <!-- Barra de Busca -->
    <form class="form-inline my-4 justify-content-center" action="livro" method="get">
        <input type="hidden" name="opcao" value="buscarLivroFiltro">
        <input class="form-control mr-sm-2" type="search" placeholder="Buscar livro" aria-label="Buscar" name="titulo">
        <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Buscar</button>
    </form>

    <h4 class="text-center">Comprar Livro</h4>
    <c:if test="${not empty retorno}">
        <div class="alert alert-warning mt-3">${retorno}</div>
    </c:if>
    <c:if test="${empty livros}">
        <div class="alert alert-warning mt-3">LIVROS não encontrados</div>
    </c:if>
    <!-- Cards para os livros -->
    <div class="d-flex flex-wrap justify-content-center">
        <c:forEach var="livro" items="${livros}">
            <div class="card m-2" style="width: 18rem;">
                <div class="card-body livrocentralizado">
                    <c:if test="${not empty livro.url}">
                        <img src="${livro.url}" class="img-livro"/>
                    </c:if>
                    <c:if test="${empty livro.url}">
                        <img src="${pageContext.request.contextPath}/static/images/capacinza.jpg" class="img-livro"/>
                    </c:if>
                </div>
                <div class="card-body">
                    <h5 class="card-title">${livro.titulo}</h5>
                    <p class="card-text">${livro.descricao}</p>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">Autor: ${livro.autor} </li>
                    <li class="list-group-item">Preço unitário: R$ ${livro.preco}</li>
                    <li class="list-group-item">Quantidades disponíveis: ${livro.qntdLivros}</li>
                </ul>
                <div class="card-body">
                    <c:if test="${livro.idClienteDono != sessionId}">
                        <a href="venda?opcao=mostrarlivrovenda&&idLivro=${livro.id}" class="btn btn-success">Comprar Agora</a> <br><br>
                        <a href="carrinho?opcao=addlivrocarrinho&&idLivro=${livro.id}" class="btn btn-primary">Adicionar ao Carrinho</a>
                    </c:if>
                    <c:if test="${livro.idClienteDono == sessionId}">
                        (Seu livro)
                    </c:if>
                </div>
            </div>
        </c:forEach>
        
    </div>
</div>



<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
