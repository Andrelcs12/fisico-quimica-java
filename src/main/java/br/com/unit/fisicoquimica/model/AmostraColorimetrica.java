package br.com.unit.fisicoquimica.model;

public record AmostraColorimetrica(
        String identificacao,
        double intensidadeInicial,
        double intensidadeMedida,
        Double concentracao,
        double absorbancia
) { }
