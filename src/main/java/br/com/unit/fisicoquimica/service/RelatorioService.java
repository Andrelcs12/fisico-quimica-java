package br.com.unit.fisicoquimica.service;

import br.com.unit.fisicoquimica.model.MedicaoExperimental;
import br.com.unit.fisicoquimica.model.RelatorioExperimental;

import java.util.List;

/** Cria relatórios sem depender de CSV ou da interface gráfica. */
public class RelatorioService {
    private final EstatisticaService estatisticaService;

    public RelatorioService(EstatisticaService estatisticaService) {
        if (estatisticaService == null) throw new IllegalArgumentException("O serviço de estatística é obrigatório.");
        this.estatisticaService = estatisticaService;
    }

    public RelatorioExperimental gerar(String titulo, List<MedicaoExperimental> medicoes) {
        return new RelatorioExperimental(titulo, estatisticaService.calcular(medicoes));
    }
}
