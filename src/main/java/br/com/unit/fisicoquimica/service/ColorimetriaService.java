package br.com.unit.fisicoquimica.service;

import br.com.unit.fisicoquimica.domain.calculo.Absorbancia;

public class ColorimetriaService {
    /** A = log10(I0/I), equivalente a -log10(I/I0). */
    public double calcularAbsorbancia(double i0, double i) {
        return new Absorbancia().calcular(i0, i);
    }
}
