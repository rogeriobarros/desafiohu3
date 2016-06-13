# desafiohu3

Passo a passo

1 - git clone https://github.com/rogeriobarros/desafiohu3.git
2 - cd <pasta-base>/desafiohu3
3 - Dependências
	Node.js
	Bower(npm install -g bower)
	Gulp(npm install -g gulp)
	gradle build
4 - Executar a aplicação
	gradle bootRun
5 - Executar os testes back end e front end
	gradle test
	gulp test
6 - Executar em modo stand-alone
	java -jar <pasta-base>/desafiohu3/stand-alone/desafiohu-3-0.0.1-SNAPSHOT.war --spring.profiles.active=dev