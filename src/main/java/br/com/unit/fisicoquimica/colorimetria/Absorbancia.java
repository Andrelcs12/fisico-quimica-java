package br.com.unit.fisicoquimica.colorimetria;

import br.com.unit.fisicoquimica.domain.calculo.CalculadoraBase;

/** Lei de Beer-Lambert na forma A = log10(I0 / I). */
public final class Absorbancia extends CalculadoraBase {
    @Override
    public double calcular(double... valores) {
        exigirQuantidade(valores, 2);
        exigirFinito(valores[0], "I0");
        exigirFinito(valores[1], "I");
        if (valores[0] <= 0 || valores[1] <= 0) throw new IllegalArgumentException("I0 e I devem ser maiores que zero.");
        return Math.log10(valores[0] / valores[1]);
    }
}
