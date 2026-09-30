package br.com.unit.fisicoquimica.service;

import br.com.unit.fisicoquimica.model.Estatisticas;
import br.com.unit.fisicoquimica.model.MedicaoExperimental;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EstatisticaService {
    public Estatisticas calcular(List<MedicaoExperimental> dados) {
        if (dados == null || dados.isEmpty()) throw new IllegalArgumentException("Não há dados para analisar.");
        double soma = 0;
        double minimo = Double.POSITIVE_INFINITY;
        double maximo = Double.NEGATIVE_INFINITY;
        List<Double> valoresOrdenados = new ArrayList<>();
        for (MedicaoExperimental medicao : dados) {
            if (medicao == null || !Double.isFinite(medicao.valor())) {
                throw new IllegalArgumentException("As medições devem possuir valores numéricos válidos.");
            }
            soma += medicao.valor();
            minimo = Math.min(minimo, medicao.valor());
            maximo = Math.max(maximo, medicao.valor());
            valoresOrdenados.add(medicao.valor());
        }
        Collections.sort(valoresOrdenados);
        int meio = valoresOrdenados.size() / 2;
        double mediana = valoresOrdenados.size() % 2 == 0
                ? (valoresOrdenados.get(meio - 1) + valoresOrdenados.get(meio)) / 2
                : valoresOrdenados.get(meio);
        return new Estatisticas(dados.size(), soma, soma / dados.size(), minimo, maximo, maximo - minimo, mediana);
    }
}
