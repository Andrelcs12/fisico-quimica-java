package br.com.unit.fisicoquimica.shared;

/** Contrato comum para os cálculos do laboratório. */
public interface Calculadora {
    double calcular(double... valores);
}
