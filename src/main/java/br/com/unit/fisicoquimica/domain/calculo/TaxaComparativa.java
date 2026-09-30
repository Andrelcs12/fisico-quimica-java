package br.com.unit.fisicoquimica.domain.calculo;

/** Taxa comparativa simplificada: 1 / tempo. */
public final class TaxaComparativa extends CalculadoraBase {
    @Override public String codigo() { return "taxa-reacao"; }
    @Override public String nome() { return "Taxa comparativa da reação"; }

    @Override
    public double calcular(double... valores) {
        exigirQuantidade(valores, 1);
        exigirFinito(valores[0], "O tempo");
        if (valores[0] <= 0) throw new IllegalArgumentException("O tempo deve ser maior que zero.");
        return 1d / valores[0];
    }
}
