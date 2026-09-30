package br.com.unit.fisicoquimica.domain.calculo;

public final class CelsiusParaKelvin extends CalculadoraBase {
    @Override public String codigo() { return "celsius-kelvin"; }
    @Override public String nome() { return "Celsius para Kelvin"; }

    @Override
    public double calcular(double... valores) {
        exigirQuantidade(valores, 1);
        exigirFinito(valores[0], "A temperatura");
        double kelvin = valores[0] + 273.15;
        if (kelvin < 0) throw new IllegalArgumentException("A temperatura resultante não pode ser inferior a 0 K.");
        return kelvin;
    }
}
