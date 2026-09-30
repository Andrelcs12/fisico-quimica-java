package br.com.unit.fisicoquimica.reacao;

import br.com.unit.fisicoquimica.shared.Calculadora;

public class ReacaoService {
    private final Calculadora taxaComparativa = new TaxaComparativa();

    /** Taxa comparativa simplificada: 1/tempo; não é uma lei cinética completa. */
    public double calcularTaxa(double tempo) {
        return taxaComparativa.calcular(tempo);
    }
}
