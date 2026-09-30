package br.com.unit.fisicoquimica.domain.calculo;

/**
 * Contrato comum para os cálculos do laboratório.
 * A interface permite que a aplicação use vários cálculos sem depender
 * diretamente de suas classes concretas.
 */
public interface Calculadora {
    String codigo();

    String nome();

    double calcular(double... valores);
}
