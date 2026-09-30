package br.com.unit.fisicoquimica.service;

import br.com.unit.fisicoquimica.model.PontoDecaimento;

import java.util.ArrayList;
import java.util.List;

public class DecaimentoNuclearService {
    /** N(t) = N0 * (1/2)^(t/meiaVida). */
    public double calcularQuantidade(double quantidadeInicial, double meiaVida, double tempo) {
        validar(quantidadeInicial, meiaVida, tempo);
        return quantidadeInicial * Math.pow(.5, tempo / meiaVida);
    }

    public List<PontoDecaimento> simular(double quantidadeInicial, double meiaVida, double tempoFinal, int pontos) {
        validar(quantidadeInicial, meiaVida, tempoFinal);
        if (pontos < 2) throw new IllegalArgumentException("Informe ao menos 2 pontos.");
        List<PontoDecaimento> simulacao = new ArrayList<>();
        for (int indice = 0; indice < pontos; indice++) {
            double tempo = tempoFinal * indice / (pontos - 1);
            simulacao.add(new PontoDecaimento(tempo, calcularQuantidade(quantidadeInicial, meiaVida, tempo)));
        }
        return simulacao;
    }

    private void validar(double quantidadeInicial, double meiaVida, double tempo) {
        if (quantidadeInicial < 0) throw new IllegalArgumentException("A quantidade inicial não pode ser negativa.");
        if (meiaVida <= 0) throw new IllegalArgumentException("A meia-vida deve ser maior que zero.");
        if (tempo < 0) throw new IllegalArgumentException("O tempo não pode ser negativo.");
    }
}
