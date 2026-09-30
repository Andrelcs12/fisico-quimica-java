package br.com.unit.fisicoquimica.service;

import br.com.unit.fisicoquimica.model.Estatisticas;
import br.com.unit.fisicoquimica.model.MedicaoExperimental;

import java.util.List;

public class EstatisticaService {
    public Estatisticas calcular(List<MedicaoExperimental> dados) {
        if (dados == null || dados.isEmpty()) throw new IllegalArgumentException("Não há dados para analisar.");
        double soma = 0;
        double minimo = Double.MAX_VALUE;
        double maximo = -Double.MAX_VALUE;
        for (MedicaoExperimental medicao : dados) {
            soma += medicao.valor();
            minimo = Math.min(minimo, medicao.valor());
            maximo = Math.max(maximo, medicao.valor());
        }
        return new Estatisticas(dados.size(), soma / dados.size(), minimo, maximo);
    }
}
