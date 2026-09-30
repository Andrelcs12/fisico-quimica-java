package br.com.unit.fisicoquimica.colorimetria;

public record AmostraColorimetrica(
        String identificacao,
        double intensidadeInicial,
        double intensidadeMedida,
        Double concentracao,
        double absorbancia
) { }
