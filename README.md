# Projeto — Java aplicado à Físico-Química

## Contexto e objetivo

Protótipo educacional inspirado conceitualmente no artigo *“Modular Integration of Python Programming in Undergraduate Physical Chemistry Experiments”*. Ele adapta a progressão de cálculos, dados e modelagem para Java, sem tentar reproduzir integralmente o artigo.

## Tecnologias

- Java 17 e Maven
- Swing para interface desktop
- JFreeChart para gráficos
- JUnit 5 para testes

## Funcionalidades

1. **Cálculos básicos:** conversões Celsius/Kelvin com validação física.
2. **Reação química:** medições de temperatura e tempo; taxa comparativa simplificada `1/tempo`.
3. **Decaimento nuclear:** `N(t) = N0 × (1/2)^(t/meiaVida)`, tabela, gráfico e CSV.
4. **Colorimetria:** absorbância `A = log10(I0/I)`, tabela, gráfico de concentração e CSV.
5. **Dados experimentais:** leitura `Time,Signal`, estatísticas, gráficos e processamento simplificado de absorbância transiente.
6. **Relatório experimental:** importa um CSV e apresenta quantidade, média, mínimo e máximo das medições.

## Orientação a objetos

O projeto usa os conceitos pedidos para a disciplina sem colocar regras científicas dentro da tela:

- **Encapsulamento:** cada calculadora guarda suas validações e expõe apenas o método `calcular`.
- **Herança:** `CalculadoraBase` reúne as validações usadas pelas calculadoras concretas.
- **Polimorfismo:** `LaboratorioService` trabalha com a interface `Calculadora`; Celsius, Kelvin, taxa e absorbância podem ser executados pelo mesmo contrato.
- **Baixo acoplamento:** a tela de conversão chama `LaboratorioService` pelo código do cálculo e não executa fórmulas diretamente.

## Arquitetura

```text
src/main/java/br/com/unit/fisicoquimica/
├── Main.java
├── model/       objetos do domínio
├── domain/      contratos e calculadoras polimórficas
├── service/     cálculos, CSV e estatística
├── ui/          MainFrame e painéis Swing
└── util/        validação e mensagens
```

## Como executar

No IntelliJ, abra o projeto Maven e execute `br.com.unit.fisicoquimica.Main`.

Requisito: Java 17 ou superior. Pelo terminal, com Maven disponível:

```bash
mvn clean package
java -jar target/fisico-quimica-java-1.0.0.jar
```

Também é possível usar o Maven Wrapper no Windows: `./mvnw.cmd test` e `./mvnw.cmd clean package`.

## Testes

Execute `mvn test` (ou `./mvnw.cmd test`). A suíte valida fórmulas, entradas inválidas, CSVs temporários, exportação, dados de demonstração, datasets de gráficos e a construção dos painéis Swing no EDT.

## Dados de demonstração

Os arquivos [experimento-exemplo.csv](sample-data/experimento-exemplo.csv) e [absorbancia-exemplo.csv](sample-data/absorbancia-exemplo.csv) podem ser carregados diretamente no módulo 5.

## Relação com a referência Python e limitações

O processamento em Java usa o mesmo raciocínio conceitual: maior sinal positivo como referência, menor sinal como tempo zero, normalização temporal e `-log10(signal/referência)`. Não há integração com Arduino, banco de dados nem análises cinéticas avançadas. Múltiplos CSVs e interpolação não fazem parte deste MVP, para não ocultar aproximações científicas.
