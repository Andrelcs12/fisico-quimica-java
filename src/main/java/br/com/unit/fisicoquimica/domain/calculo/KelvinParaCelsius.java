package br.com.unit.fisicoquimica.domain.calculo;

public final class KelvinParaCelsius extends CalculadoraBase {
    @Override public String codigo() { return "kelvin-celsius"; }
    @Override public String nome() { return "Kelvin para Celsius"; }

    @Override
    public double calcular(double... valores) {
        exigirQuantidade(valores, 1);
        exigirFinito(valores[0], "A temperatura");
        if (valores[0] < 0) throw new IllegalArgumentException("A temperatura em Kelvin não pode ser negativa.");
        return valores[0] - 273.15;
    }
}
