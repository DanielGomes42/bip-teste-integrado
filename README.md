# bip-teste-integrado

Projeto do desafio dividido em 3 módulos:

- `ejb-module/` – módulo EJB original do teste (continha o bug de transferência).
- `backend-module/` – API Spring Boot que expõe o CRUD de Benefícios e chama o EJB para transferências.
- `frontend/` – aplicação Angular que consome o backend e lista os benefícios.

---

## 1. Pré-requisitos

Instale / tenha na máquina:

- **Java 17+** (projeto usa Spring Boot 3.2.5)
- **Maven 3.x**
- **Node.js 20.19+** (Angular CLI 20 pede isso)
- **npm**
- **Angular CLI** global:


npm install -g @angular/cli

## 2. Módulo EJB (corrigir o bug e instalar no Maven)

O teste entregou um EJB com um bug na transferência (não validava saldo, nem origem/destino e não fazia locking).

- **Arquivo corrigido:
  ejb-module/src/main/java/com/example/ejb/BeneficioEjbService.java

A versão corrigida faz:

valida origem e destino;

impede origem == destino;

valida que o valor é positivo;

busca os dois benefícios com LockModeType.PESSIMISTIC_WRITE;

verifica saldo antes de debitar;

atualiza os dois;

dá em.flush().

2.2. Instalar o EJB localmente

Entre na pasta do módulo e rode:

cd ejb-module
mvn clean install

Isso gera e instala o jar ejb-module-1.0-SNAPSHOT no seu ~/.m2, para o backend conseguir usar:

<dependency>
    <groupId>com.example</groupId>
    <artifactId>ejb-module</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>


##  3 Backend (Spring Boot)


código: backend-module/

sobe em http://localhost:8080

executa os scripts:

src/main/resources/schema.sql (cria tabela beneficio)

src/main/resources/data.sql (insere 2 registros)

expõe CRUD de benefícios

expõe POST /api/transferencias que chama o EJB

expõe Swagger

3.3. Configuração necessária

Arquivo: backend-module/src/main/resources/application.properties

spring.datasource.url=jdbc:h2:mem:desafio_bip;DB_CLOSE_DELAY=-1
spring.h2.console.enabled=true

spring.jpa.hibernate.ddl-auto=none
spring.sql.init.mode=always


Isso garante que o Spring não vai criar a tabela sozinho (“ddl-auto=none”) e vai usar os scripts que estão na pasta (schema.sql e data.sql).

Rodar o backend
cd backend-module
mvn spring-boot:run
ou:
mvn clean install
java -jar target/backend-module-0.0.1-SNAPSHOT.jar


##  4 Endpoints do backend

Base: http://localhost:8080

4.1. Swagger (documentação)

http://localhost:8080/swagger-ui/index.html

http://localhost:8080/v3/api-docs

4.2. Benefícios (CRUD)

GET /api/beneficios → lista todos

GET /api/beneficios/{id} → detalhe

POST /api/beneficios → cria

PUT /api/beneficios/{id} → atualiza

DELETE /api/beneficios/{id} → remove

Como temos data.sql, ao chamar:

GET http://localhost:8080/api/beneficios

4.3. Transferência (integração com EJB)

POST /api/transferencias

Exemplo de body:

{
"origemId": 1,
"destinoId": 2,
"valor": 100.00
}
O controller do backend recebe isso e chama o EJB corrigido (que valida e debita/credite com lock).


##  5. Frontend (Angular)
Frontend simples só pra consumir o backend e mostrar a lista.


frontend/frontend-app

cd frontend/frontend-app
npm install


ng serve --open


Vai abrir em http://localhost:4200

A tela faz uma chamada para http://localhost:8080/api/beneficios
e mostra:

ID

Nome

Descrição

Valor

Ativo

Observação: o backend precisa estar rodando na porta 8080.


##  6. Testes do backend
cd backend-module
mvn test

##  Estrutura final do projeto
 
bip-teste-integrado-main/
├── ejb-module/          # EJB corrigido
├── backend-module/      # Spring Boot (CRUD + transferência + Swagger + H2)
├── frontend/
│   └── frontend-app/    # Angular consumindo /api/beneficios
├── db/                  # scripts 
└── README.md