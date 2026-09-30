package br.com.unit.fisicoquimica.calculos;

import br.com.unit.fisicoquimica.shared.CalculadoraBase;

public final class CelsiusParaKelvin extends CalculadoraBase {
    @Override
    public double calcular(double... valores) {
        exigirQuantidade(valores, 1);
        exigirFinito(valores[0], "A temperatura");
        double kelvin = valores[0] + 273.15;
        if (kelvin < 0) throw new IllegalArgumentException("A temperatura resultante não pode ser inferior a 0 K.");
        return kelvin;
    }
}
