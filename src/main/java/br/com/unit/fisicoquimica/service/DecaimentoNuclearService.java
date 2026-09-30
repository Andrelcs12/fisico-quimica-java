package br.com.unit.fisicoquimica.service;

import br.com.unit.fisicoquimica.domain.calculo.Calculadora;
import br.com.unit.fisicoquimica.domain.calculo.DecaimentoNuclear;
import br.com.unit.fisicoquimica.model.PontoDecaimento;

import java.util.ArrayList;
import java.util.List;

public class DecaimentoNuclearService {
    private final Calculadora calculadora;

    public DecaimentoNuclearService() {
        this(new DecaimentoNuclear());
    }

    public DecaimentoNuclearService(Calculadora calculadora) {
        if (calculadora == null) throw new IllegalArgumentException("A calculadora de decaimento é obrigatória.");
        this.calculadora = calculadora;
    }

    public double calcularQuantidade(double quantidadeInicial, double meiaVida, double tempo) {
        return calculadora.calcular(quantidadeInicial, meiaVida, tempo);
    }

    public List<PontoDecaimento> simular(double quantidadeInicial, double meiaVida, double tempoFinal, int pontos) {
        calcularQuantidade(quantidadeInicial, meiaVida, tempoFinal);
        if (pontos < 2) throw new IllegalArgumentException("Informe ao menos 2 pontos.");
        List<PontoDecaimento> simulacao = new ArrayList<>();
        for (int indice = 0; indice < pontos; indice++) {
            double tempo = tempoFinal * indice / (pontos - 1);
            simulacao.add(new PontoDecaimento(tempo, calcularQuantidade(quantidadeInicial, meiaVida, tempo)));
        }
        return simulacao;
    }
}
