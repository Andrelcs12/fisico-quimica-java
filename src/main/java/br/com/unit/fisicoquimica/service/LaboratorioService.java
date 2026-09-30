package br.com.unit.fisicoquimica.service;

import br.com.unit.fisicoquimica.domain.calculo.Calculadora;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Fachada do domínio. Recebe abstrações por injeção e aplica polimorfismo
 * ao escolher a calculadora pelo código, sem conhecer a fórmula em si.
 */
public class LaboratorioService {
    private final Map<String, Calculadora> calculadoras;

    public LaboratorioService(Collection<? extends Calculadora> calculadoras) {
        if (calculadoras == null || calculadoras.isEmpty()) {
            throw new IllegalArgumentException("Informe ao menos uma calculadora.");
        }
        this.calculadoras = new LinkedHashMap<>();
        for (Calculadora calculadora : calculadoras) {
            if (calculadora == null) throw new IllegalArgumentException("A calculadora não pode ser nula.");
            if (this.calculadoras.put(calculadora.codigo(), calculadora) != null) {
                throw new IllegalArgumentException("Código de cálculo repetido: " + calculadora.codigo());
            }
        }
    }

    public double calcular(String codigo, double... valores) {
        Calculadora calculadora = calculadoras.get(codigo);
        if (calculadora == null) throw new IllegalArgumentException("Cálculo não encontrado: " + codigo);
        return calculadora.calcular(valores);
    }
}
