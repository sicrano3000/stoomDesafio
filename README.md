# stoomDesafio
Desafio da Stoom - Cadastro de endereço com integração à API de geolocalização da google

- GET Listar - http://localhost:8080/endereco/
- GET Listar por Id - http://localhost:8080/endereco/2
- POST Cadastrar - http://localhost:8080/endereco/
- PUT Atualizar - http://localhost:8080/endereco/
- DELETE - http://localhost:8080/endereco/

- Json de exemplo:
{
    "rua": "Rua Astrogildo do Amaral",
    "numero": 16,
    "complemento": "casa 4",
    "bairro": "Galo Branco",
    "cidade": "São Gonçalo",
    "estado": "Rio de Janeiro",
    "pais": "Brasil",
    "cep": "24421-660"
}
