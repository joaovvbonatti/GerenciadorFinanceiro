# Gerenciador Financeiro Desktop

Aplicação desktop para gerenciamento de finanças pessoais, desenvolvida em Java, com interface gráfica em JavaFX e persistência de dados em SQLite.
O sistema permite registrar entradas e saídas financeiras, categorizar transações, visualizar gráficos de gastos e acompanhar a evolução do saldo ao longo do tempo.

## Screenshots

<img src="docs/images/Saldo e extrato.png" width="700">
<img src="docs/images/Gráfico do extrato.png" width="700">
<img src="docs/images/Gráfico do saldo.png" width="700">

## Funcionalidades

- Autenticação local por senha
- Cadastro, edição e exclusão de transações
- Classificação de transações por categoria
- Visualização de gastos por categoria em gráfico
- Histórico completo de transações (extrato)
- Cálculo automático do saldo
- Persistência local com banco de dados SQLite

## Como executar

### Pré-requisitos
- Java JDK 21 ou superior
- Maven (ou uso do Maven Wrapper incluído)

### Executando a aplicação

Clone o repositório e, na raiz do projeto, execute:

```bash
./mvnw javafx:run
```

## Credenciais padrão

Para fins acadêmicos e de demonstração, a aplicação utiliza uma autenticação simples.
- **Senha:** admin

*(não há distinção de usuários)*

## Observações

- O banco de dados é criado localmente na primeira execução.
- As credenciais e o banco de dados não devem ser utilizados em ambiente de produção.
- Este projeto tem finalidade acadêmica.
## Tecnologias Utilizadas

- Java 21
- JavaFX
- Maven
- SQLite
- JDBC


## Contexto Acadêmico

Projeto desenvolvido como trabalho final da disciplina **Análise e Projeto de Sistemas**, do curso de Engenharia de Computação, com o objetivo de aplicar conceitos de modelagem, organização de software e desenvolvimento de aplicações desktop.
### Licenças de Terceiros

- Ícone da aplicação:  
  Coins icons created by cah nggunung — Flaticon  
  https://www.flaticon.com/free-icons/coins

- AtlantaFX — Apache License 2.0
