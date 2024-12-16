<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Visualizar Carrinho</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <style>
        .navbar-custom {
            background-color: #343a40;
        }
        .navbar-brand {
            color: white !important;
        }
        .navbar-toggler-icon {
            color: white !important;
        }
        .dropdown-menu .dropdown-item {
            color: black;
        }
        .dropdown-menu .dropdown-item:hover {
            background-color: #878787;
            color: white;
        }
        .card {
            margin-top: 20px;
        }
        .nav-link {
            color: white;
        }
        .dropdown-toggle:hover {
            color: #878787;
        }
        .corcinza:hover {
            color: #878787;
        }
        .img-livro {
            width: 60px;
            height: 60px;
            object-fit: cover;
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
    <h4 class="text-center">Visualizar Carrinho</h4>

    <c:if test="${not empty retorno}">
        <div class="alert alert-warning mt-3">${retorno}</div>
    </c:if>
    <c:if test="${empty livros}">
        <div class="alert alert-warning mt-3"><p>Seu carrinho está vazio</p></div>
    </c:if>

    <c:if test="${not empty livros}">
        <form action="carrinho" method="get">
            <div class="text-left mt-3">
                <button type="submit" class="btn btn-success">Finalizar compra</button><br>
            </div>
            <input type="hidden" name="idClienteComprador" value="${sessionScope.id}">
            <input type="hidden" name="opcao" value="compraslivroscarrinho">
            <table class="table table-bordered mt-3">
                <thead>
                <tr>
                    <th>Imagem</th>
                    <th>Título</th>
                    <th>Autor</th>
                    <th>Descrição</th>
                    <th>Preço Unitário</th>
                    <th>Quantidade Disponível</th>
                    <th>Quantidade</th>
                    <th>Opções</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="livro" items="${livros}">
                    <tr>
                        <td>
                        <div class="livrocentralizado">
                            <c:if test="${not empty livro.url}">
                                <img src="${livro.url}" class="img-livro"/>
                            </c:if>
                            <c:if test="${empty livro.url}">
                                <img src="${pageContext.request.contextPath}/static/images/capacinza.jpg" class="img-livro"/>
                            </c:if>
                        </div>
                        </td>
                        <td>${livro.titulo}</td>
                        <td>${livro.autor}</td>
                        <td>${livro.descricao}</td>
                        <td>${livro.preco}</td>
                        <td>${livro.qntdLivros}</td>
                        <td><input type="number" class="form-control" id="qntd_livros" name="qntd_livros" min="1" max="${livro.qntdLivros}" value="1" required></td>
                        <td>
                            <a href="carrinho?opcao=rmlivrocarrinho&&idLivro=${livro.id}" class="btn btn-danger">Remover</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </form>
    </c:if>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
