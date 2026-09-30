package br.com.unit.fisicoquimica.colorimetria;

import br.com.unit.fisicoquimica.shared.Calculadora;

public class ColorimetriaService {
    private final Calculadora absorbancia = new Absorbancia();

    /** A = log10(I0/I), equivalente a -log10(I/I0). */
    public double calcularAbsorbancia(double i0, double i) {
        return absorbancia.calcular(i0, i);
    }
}
