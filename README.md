# Projeto Java aplicado à Físico-Química

## Objetivo

Aplicação desktop simples para apoiar atividades de físico-química. O projeto permite fazer cálculos, simular decaimento nuclear, organizar dados de experimento e gerar gráficos e relatórios básicos.

## Tecnologias

- Java 17
- Maven
- Java Swing
- JFreeChart
- JUnit 5

## Funcionalidades

1. **Conversões de temperatura:** Celsius para Kelvin e Kelvin para Celsius, respeitando o zero absoluto.
2. **Reação química:** cadastro de temperatura e tempo, com taxa comparativa `1 / tempo`. É uma simplificação educacional, não uma lei cinética completa.
3. **Decaimento nuclear:** cálculo de `N(t) = N0 × (1/2)^(t / meia-vida)`, tabela, gráfico e exportação CSV.
4. **Colorimetria:** cálculo de absorbância com `A = log10(I0 / I)`, tabela de amostras, gráfico e exportação CSV.
5. **Dados experimentais:** importação de CSV, estatísticas, gráfico, processamento de absorbância e exportação.
6. **Relatório experimental:** resumo com quantidade, soma, média, mínimo, máximo, amplitude e mediana.

## Conceitos de Orientação a Objetos

- **Encapsulamento:** `LaboratorioService` mantém suas calculadoras em um atributo privado e `RelatorioService` mantém o serviço de estatística privado. As validações de cada fórmula ficam nas próprias classes de cálculo.
- **Herança:** `CalculadoraBase` é uma classe abstrata com validação de quantidade e de valores finitos. `CelsiusParaKelvin`, `KelvinParaCelsius`, `TaxaComparativa`, `Absorbancia` e `DecaimentoNuclear` herdam dela.
- **Polimorfismo:** todas essas classes implementam `Calculadora`. O método `calcular(double... valores)` é chamado pelo mesmo contrato, mesmo com fórmulas diferentes.
- **Abstração:** as fórmulas ficam em `domain/calculo`, as regras de aplicação em `service`, os dados em `model` e os componentes visuais em `ui`.
- **Baixo acoplamento:** `LaboratorioService` recebe uma coleção de `Calculadora` e não depende de uma fórmula específica. As telas Swing chamam serviços e não executam cálculos científicos diretamente.

## Estrutura do projeto

```text
src/main/java/br/com/unit/fisicoquimica/
├── Main.java
├── domain/calculo/  calculadoras e contrato polimórfico
├── model/           dados imutáveis do experimento
├── service/         regras, CSV, gráficos e relatório
├── ui/              telas Swing
└── util/            mensagens e leitura de campos
```

## Como executar

Abra o projeto como Maven no IntelliJ e execute `br.com.unit.fisicoquimica.Main`.

Pelo terminal, com Maven instalado:

```bash
mvn clean package
java -jar target/fisico-quimica-java-1.0.0.jar
```

No Windows também é possível usar o Maven Wrapper:

```bash
.\mvnw.cmd test
.\mvnw.cmd clean package
```

## Como executar os testes

```bash
mvn test
```

Os testes verificam fórmulas, zero absoluto, entradas inválidas, polimorfismo, estatísticas, CSV, exportação e datasets dos gráficos.

## Exemplos de CSV

Os arquivos em `sample-data/` seguem este formato:

```csv
Time,Signal
0,100
1,95
2,88
```

O cabeçalho `Time,Signal` é obrigatório. Também são aceitos ponto ou vírgula decimal quando o separador das colunas é `;`, por exemplo `0,5;10,2`.

## Limitações

- A taxa de reação é apenas comparativa.
- O processamento de absorbância usa uma referência simples baseada no maior sinal positivo.
- Não há integração com equipamentos, banco de dados ou análises cinéticas avançadas.
- O projeto trabalha com um arquivo CSV por vez.
