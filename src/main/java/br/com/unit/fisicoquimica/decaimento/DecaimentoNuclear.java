package br.com.unit.fisicoquimica.decaimento;

import br.com.unit.fisicoquimica.shared.CalculadoraBase;

/** N(t) = N0 × (1/2)^(t / meia-vida). */
public final class DecaimentoNuclear extends CalculadoraBase {
    @Override
    public double calcular(double... valores) {
        exigirQuantidade(valores, 3);
        double quantidadeInicial = valores[0];
        double meiaVida = valores[1];
        double tempo = valores[2];
        exigirFinito(quantidadeInicial, "A quantidade inicial");
        exigirFinito(meiaVida, "A meia-vida");
        exigirFinito(tempo, "O tempo");
        if (quantidadeInicial < 0) throw new IllegalArgumentException("A quantidade inicial não pode ser negativa.");
        if (meiaVida <= 0) throw new IllegalArgumentException("A meia-vida deve ser maior que zero.");
        if (tempo < 0) throw new IllegalArgumentException("O tempo não pode ser negativo.");
        return quantidadeInicial * Math.pow(0.5, tempo / meiaVida);
    }
}
