package br.com.unit.fisicoquimica.shared;

/** Executa qualquer cálculo pelo contrato comum. */
public class LaboratorioService {
    public double calcular(Calculadora calculadora, double... valores) {
        if (calculadora == null) throw new IllegalArgumentException("Informe uma calculadora.");
        return calculadora.calcular(valores);
    }
}
