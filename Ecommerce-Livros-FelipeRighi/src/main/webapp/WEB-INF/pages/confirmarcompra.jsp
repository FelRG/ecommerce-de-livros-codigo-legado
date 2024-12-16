<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirmar compra do livro</title>
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
    <h4 class="text-center">Confirmar compra do livro</h4>
    
    <!-- Cards para o livro -->
    <div class="d-flex flex-wrap justify-content-center">

            <div class="card m-2" style="width: 30rem;">
                <div class="card-body livrocentralizado">
                    <c:if test="${not empty livro.url}">
                        <img src="${livro.url}" class="img-livro" alt="${livro.titulo}"/>
                    </c:if>
                    <c:if test="${empty livro.url}">
                        <img src="${pageContext.request.contextPath}/static/images/capacinza.jpg" class="img-livro"/>
                    </c:if>
                </div>
                <div class="card-body">
                    <h5 class="card-title text-center">${livro.titulo}</h5>
                    <p class="card-text text-center">${livro.descricao}</p>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item text-center">Autor: ${livro.autor}</li>
                    <li class="list-group-item text-center">Preço unitário: R$ ${livro.preco}</li>
                    <li class="list-group-item text-center">Quantidades disponíveis: ${livro.qntdLivros}</li>
                </ul>


                <form action="venda" method="get">
                    <div class="form-group">
                        <input type="hidden" name="opcao" value="comprarlivro">
                        <input type="hidden" name="idLivro" value="${livro.id}">
                        <input type="hidden" name="idClienteComprador" value="<%= session.getAttribute("id") %>">
                        <input type="hidden" name="titulo" value="${livro.titulo}">
                        <input type="hidden" name="autor" value="${livro.autor}">
                        <input type="hidden" name="descricao" value="${livro.descricao}">
                        <input type="hidden" name="preco" value="${livro.preco}">
                        <input type="hidden" name="url" value="${livro.url}">
                    </div>
                    <div class="form-group">
                        <label for="qntd_livros">Escolha a quantidade:</label>
                        <input type="number" class="form-control" id="qntd_livros" name="qntd_livros" min="1" max="${livro.qntdLivros}" value="1" required>
                    </div>
                    <div class="form-group">
                        <label for="valor_total">Valor Total:</label>
                        <input type="number" class="form-control" id="valor_total" name="valor_total" readonly>
                    </div>
                    <div class="card-body col-md-12 text-center">
                        <a href="livro?opcao=mostrarlivrosavenda" class="btn btn-danger col-md-5">Cancelar</a>
                        <button type="submit" class="btn btn-success col-md-5">Confirmar compra</button>
                    </div>
                </form>
            </div>
        
    </div>
</div>

<script>
    function atualizarValorTotal() {
        var precoUnitario = ${livro.preco}; // Preço unitário do livro
        var quantidade = document.getElementById("qntd_livros").value;
        var valorTotal = precoUnitario * quantidade;
        document.getElementById("valor_total").value = valorTotal.toFixed(2); // Formata o valor para duas casas decimais
    }

    document.addEventListener("DOMContentLoaded", function() {
        // Chama a função ao carregar a página
        atualizarValorTotal();

        // Adiciona o evento input para atualizar o valor enquanto digita
        document.getElementById("qntd_livros").addEventListener("input", atualizarValorTotal);
    });
</script>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
