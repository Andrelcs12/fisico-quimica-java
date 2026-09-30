package br.com.unit.fisicoquimica.reacao;

import br.com.unit.fisicoquimica.shared.CalculadoraBase;

/** Taxa comparativa simplificada: 1 / tempo. */
public final class TaxaComparativa extends CalculadoraBase {
    @Override
    public double calcular(double... valores) {
        exigirQuantidade(valores, 1);
        exigirFinito(valores[0], "O tempo");
        if (valores[0] <= 0) throw new IllegalArgumentException("O tempo deve ser maior que zero.");
        return 1d / valores[0];
    }
}
