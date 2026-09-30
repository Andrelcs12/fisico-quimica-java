package br.com.unit.fisicoquimica.model;

/** Valores calculados a partir dos sinais de um experimento. */
public record Estatisticas(
        int quantidade,
        double soma,
        double media,
        double minimo,
        double maximo,
        double amplitude,
        double mediana
) { }
