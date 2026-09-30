package br.com.unit.fisicoquimica.service;

import br.com.unit.fisicoquimica.model.AmostraColorimetrica;
import br.com.unit.fisicoquimica.model.MedicaoExperimental;
import br.com.unit.fisicoquimica.model.PontoAbsorbancia;
import br.com.unit.fisicoquimica.model.PontoDecaimento;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.List;

public class GraficoService {
    public XYSeriesCollection dadosDecaimento(List<PontoDecaimento> pontos) {
        XYSeries serie = new XYSeries("Quantidade restante");
        pontos.forEach(ponto -> serie.add(ponto.tempo(), ponto.quantidade()));
        return new XYSeriesCollection(serie);
    }

    public XYSeriesCollection dadosExperimentais(List<MedicaoExperimental> dados) {
        XYSeries serie = new XYSeries("Signal");
        dados.forEach(medicao -> serie.add(medicao.tempo(), medicao.valor()));
        return new XYSeriesCollection(serie);
    }

    public XYSeriesCollection dadosAbsorbancia(List<PontoAbsorbancia> dados) {
        XYSeries serie = new XYSeries("Absorbância");
        dados.forEach(ponto -> serie.add(ponto.tempo(), ponto.absorbancia()));
        return new XYSeriesCollection(serie);
    }

    public XYSeriesCollection dadosColorimetria(List<AmostraColorimetrica> dados) {
        XYSeries serie = new XYSeries("Amostras");
        dados.stream()
                .filter(amostra -> amostra.concentracao() != null)
                .forEach(amostra -> serie.add(amostra.concentracao().doubleValue(), amostra.absorbancia()));
        return new XYSeriesCollection(serie);
    }
}
