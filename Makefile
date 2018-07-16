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