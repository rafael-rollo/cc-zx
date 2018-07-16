# ZX Ventures Code Challenge

Java Web App para localizar os pontos de venda mais próximos e manter o churrasco da galera com a geladeira ou isopor abastecidos.

## Tecnologias utilizadas no projeto

- Java 8
- MySQL 5.7
- Maven e Docker (docker-compose)

## Rodando em dev

Para rodar o projeto em dev é simples. Você só precisa rodar o comando `make run`. A app vai ser compilada, testada, empacotada e servida na porta 8080. O docker compose vai subir o container mysql, e em seguida o container java, já inserindo na base a massa de dados inicial.

Basta acessar no browser `http://localhost:8080/swagger-ui.html`. Você terá acesso a uma documentação interativa da API (que usa o swagger-ui), pra poder chamar de forma fácil os endpoints.
 

## Deploy

No mesmo arquivo *Makefile usado como base, existe um target `deploy` que pode ser usado como base para implantação e run e produção. Você poderia usar `make deploy` para isso.


*Makefile:
```
compile:
	@ echo compilando
	@ mvn compile
	
test: compile
	@ echo testando
	@ docker-compose -f test.yml up -d
	@ mvn test
	@ docker-compose down

package: test
	@ echo empacotando o projeto
	@ mvn -DskipTests package
	
run: package
	@ echo rodando
	@ docker-compose up
	
deploy: package
	@ echo deploying
	@ docker-compose -f prod.yml up -d
```