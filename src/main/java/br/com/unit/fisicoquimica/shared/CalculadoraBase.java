package br.com.unit.fisicoquimica.shared;

/** Classe abstrata que concentra validações compartilhadas. */
public abstract class CalculadoraBase implements Calculadora {
    protected void exigirQuantidade(double[] valores, int quantidade) {
        if (valores == null || valores.length != quantidade) {
            throw new IllegalArgumentException("Quantidade de valores inválida para o cálculo.");
        }
    }

    protected void exigirFinito(double valor, String nome) {
        if (!Double.isFinite(valor)) {
            throw new IllegalArgumentException(nome + " deve ser um número válido.");
        }
    }
}
