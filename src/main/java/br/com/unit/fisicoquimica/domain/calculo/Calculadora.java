package br.com.unit.fisicoquimica.domain.calculo;

/** Contrato comum para os cálculos do laboratório. */
public interface Calculadora {
    double calcular(double... valores);
}
