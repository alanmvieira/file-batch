![](https://github.com/iurymarques/file-batch/workflows/tests/badge.svg)

# Processamento Arquivos de Venda

## Sobre

A aplicação construída é um processador de arquivos de venda em formato .dat, com o objetivo de compilar um relatório com as informações contidas no arquivo original . Para atingir esse objetivo foram utlizadas as seguintes tecnologias:

- Java 11
- Maven
- Spring Boot
- Spring Batch
- Cucumber
- JUnit

## Execução Local
### Requisitos
- Java 11 ou Superior
- Git
- Intellij IDEA

### Execução
1. Definir a váriavel de ambiente $HOME que deverá ser o diretório utilizado pela aplicação para ler as entradas e escrever as saídas
3. Clonar o repositório com o seguinte comando `git clone https://github.com/iurymarques/file-batch.git`
4. Importe o projeto no Intellij IDEA
5. Iniciar a aplicação

OU

1. Definir a váriavel de ambiente $HOME que deverá ser o diretório utilizado pela aplicação para ler as entradas e escrever as saídas
2. Clonar o repositório com o seguinte comando `git clone https://github.com/iurymarques/file-batch.git`
3. Compilar o projeto com o comando `./mvnw clean package`
4. Iniciar a aplicação com o comando `java -jar target/*.jar`

## Exemplos
- Adicionando um arquivo .dat com o seguinte conteúdo na pasta `"$HOME"/data/in`
```
001ç1234567891234çPedroç50000
001ç3245678865434çPauloç40000.99
002ç2345675434544345çJose da SilvaçRural
002ç2345675433444345çEduardo PereiraçRural
003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro
003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo
```
- Deverá ser obtido  o seguinte relatório na pasta  `"$HOME"/data/ou`
```
Total customer count: 2  
Total salesman count: 2  
ID most expensive sale: 10  
Worst salesman: Paulo
```
