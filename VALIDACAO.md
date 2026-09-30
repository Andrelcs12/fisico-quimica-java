# Validação do Projeto

## Ambiente

- Java encontrado: OpenJDK Temurin 24.0.2.
- `javac`: 24.0.2.
- Maven utilizado: Apache Maven 3.9.16 temporário, com compilação `release 17`.
- Sistema: Windows 11 amd64.
- O Maven Wrapper foi encontrado, mas neste ambiente retornou `Acesso negado`; a validação foi executada com Maven oficial equivalente.

## Testes

- Quantidade: 18.
- Passaram: 18.
- Falharam: 0.
- Erros: 0.
- Ignorados: 0.

## Fórmulas verificadas

- Temperatura: conversões e Kelvin inválido.
- Reação: taxa comparativa `1/tempo` e tempo inválido.
- Decaimento: meia-vida, validações e série monotônica.
- Colorimetria: `log10(I0/I)` e entradas inválidas.
- Estatística: média, mínimo, máximo, único valor e coleção vazia.

## Arquivos

- Leitura CSV: válido, linhas vazias, inválido e `sample-data`.
- Exportação CSV: cabeçalho, linhas e valor de decaimento verificados.
- Absorbância: referência positiva máxima, tempo zero e ausência de valores não finitos.

## Interface

- Componentes: os cinco `JPanel` foram instanciados no EDT por smoke test.
- Gráficos: datasets de decaimento e colorimetria foram verificados programaticamente.
- Inspeção visual: não realizada; o ambiente de validação é headless.

## Build

```text
mvn test: PASS (18 testes)
mvn clean package: PASS
JAR: PASS em modo headless; manifesto e JFreeChart incorporado verificados
```
