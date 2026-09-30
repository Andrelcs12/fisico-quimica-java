package br.com.unit.fisicoquimica.calculos;

import br.com.unit.fisicoquimica.domain.calculo.CalculadoraBase;

public final class KelvinParaCelsius extends CalculadoraBase {
    @Override
    public double calcular(double... valores) {
        exigirQuantidade(valores, 1);
        exigirFinito(valores[0], "A temperatura");
        if (valores[0] < 0) throw new IllegalArgumentException("A temperatura em Kelvin não pode ser negativa.");
        return valores[0] - 273.15;
    }
}
