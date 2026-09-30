# Validação do Projeto

## Ambiente

- Java 17 utilizado para compilar o código principal e os testes.
- Maven não está disponível no `PATH` deste ambiente.
- O Maven Wrapper foi executado, mas retornou `Acesso negado`.

## Testes

- Quantidade: 31.
- Aprovados: 31.
- Falhas: 0.
- Erros: 0.
- Ignorados: 0.

Os testes foram executados diretamente com Java 17, JUnit 5 e as dependências locais já disponíveis.

## Fórmulas verificadas

- Conversão entre Celsius e Kelvin, incluindo zero absoluto.
- Taxa comparativa da reação `1 / tempo` e tempo inválido.
- Decaimento radioativo, validações e série decrescente.
- Absorbância `log10(I0 / I)` e entradas inválidas.
- Estatísticas: quantidade, soma, média, mínimo, máximo, amplitude e mediana.

## Dados e interface

- Leitura CSV: cabeçalho `Time,Signal`, linhas vazias, valores inválidos e dados de exemplo.
- Exportação CSV: dados de reação, decaimento, colorimetria e dados experimentais.
- Gráficos: datasets de decaimento, colorimetria e dados experimentais.
- Interface: os cinco painéis oficiais foram instanciados no EDT por smoke test.

## Build

```text
Compilação direta com Java 17: PASS
Inicialização em modo headless: PASS
mvn test: não executado (Maven indisponível)
mvn clean package: não executado (Maven indisponível)
mvnw.cmd test: não executado (Acesso negado)
mvnw.cmd clean package: não executado (Acesso negado)
```
