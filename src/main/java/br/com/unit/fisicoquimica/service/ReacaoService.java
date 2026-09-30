package br.com.unit.fisicoquimica.service;

import br.com.unit.fisicoquimica.domain.calculo.Calculadora;
import br.com.unit.fisicoquimica.domain.calculo.TaxaComparativa;

public class ReacaoService {
    private final Calculadora taxaComparativa = new TaxaComparativa();

    /** Taxa comparativa simplificada: 1/tempo; não é uma lei cinética completa. */
    public double calcularTaxa(double tempo) {
        return taxaComparativa.calcular(tempo);
    }
}
