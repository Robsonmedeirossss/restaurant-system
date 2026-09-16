# Restaurant System

## Sobre o projeto

Este projeto foi criado para praticar conceitos relacionados a Spring Boot, Docker, PostgreSQL, AWS S3 e comunicacao assincrona com filas AWS SQS.

A aplicacao simula um sistema para restaurante. Ela possui funcionalidades relacionadas a mesas, produtos, pedidos, contas e pagamentos. O pagamento e processado por um servico separado, que recebe a solicitacao por uma fila SQS e envia o resultado por outra fila.

O projeto e dividido em dois servicos:

- `restaurant-service`: responsavel pela operacao principal do restaurante, pelo banco de dados, pelo armazenamento de imagens no S3 e pela comunicacao com o servico de pagamento.
- `payment-service`: responsavel por receber e processar as solicitacoes de pagamento. O pagamento e apenas simulado para fins de estudo.

## Requisitos

Antes de executar o projeto, instale e configure:

- Java 21;
- Docker e Docker Compose;
- AWS CLI;
- uma conta AWS com credenciais configuradas na maquina.

As credenciais AWS precisam ter permissao para enviar, receber e excluir mensagens nas filas SQS utilizadas pelo projeto e para gravar objetos no bucket S3.

## Recursos necessarios na AWS

Crie na mesma regiao AWS utilizada pela aplicacao os seguintes recursos:

- fila `payment-request`;
- fila `payment-response`;
- bucket S3 `restaurant-products-imgs`.

O nome do bucket pode ser alterado em `restaurant-service/src/main/resources/application-dev.yml`, caso necessario. O codigo atual gera os links dos arquivos usando a regiao `us-east-1`.

As credenciais podem ser configuradas pela AWS CLI, (acesse a doc):

```bash
aws configure
```

## Configuracao do banco de dados

O `docker-compose.yml` do `restaurant-service` cria um PostgreSQL local na porta `5444`. Antes de iniciar o banco, crie o arquivo `restaurant-service/.env` com:

```env
DB_USER=root
DB_PASSWORD=root
DB_NAME=restaurant
```

No perfil de desenvolvimento, a aplicacao usa estas mesmas informacoes para acessar o banco:

```text
Host: localhost
Porta: 5444
Banco: restaurant
Usuario: root
Senha: root
```

As migracoes do Flyway sao executadas automaticamente quando o `restaurant-service` inicia.

## Como executar

Abra um terminal para o banco:

```bash
cd restaurant-service
docker compose up -d
```

Em outro terminal, inicie o servico principal:

```bash
cd restaurant-service
./mvnw spring-boot:run
```

Em um terceiro terminal, inicie o servico de pagamento:

```bash
cd payment-service
./mvnw spring-boot:run
```

O `payment-service` utiliza a porta `8081`. A porta do `restaurant-service` segue a configuracao padrao do Spring Boot, caso nenhuma porta diferente seja definida.

## Comunicacao de pagamento

Quando um pedido solicita pagamento, o `restaurant-service` envia uma mensagem para a fila `payment-request`. O `payment-service` consome essa mensagem, simula o processamento e publica o resultado na fila `payment-response`. O `restaurant-service` consome a resposta e atualiza o estado do pagamento.

## API

Os principais recursos da API do restaurante sao:

- produtos;
- categorias de produtos;
- mesas;
- pedidos;
- contas;
- indicadores.

A aplicacao usa Springdoc para documentacao da API. Com o servico principal em execucao, a interface do Swagger pode ser acessada em:

```text
http://localhost:8080/swagger-ui/index.html
```

