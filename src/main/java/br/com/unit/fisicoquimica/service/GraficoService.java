package br.com.unit.fisicoquimica.service;

import br.com.unit.fisicoquimica.model.AmostraColorimetrica;
import br.com.unit.fisicoquimica.model.MedicaoExperimental;
import br.com.unit.fisicoquimica.model.PontoAbsorbancia;
import br.com.unit.fisicoquimica.model.PontoDecaimento;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.List;

public class GraficoService {
    public XYSeriesCollection dadosDecaimento(List<PontoDecaimento> pontos) { return serieDecaimento(pontos); }
    public XYSeriesCollection dadosExperimentais(List<MedicaoExperimental> dados) { XYSeries s = new XYSeries("Signal"); dados.forEach(m -> s.add(m.tempo(), m.valor())); return new XYSeriesCollection(s); }
    public XYSeriesCollection dadosAbsorbancia(List<PontoAbsorbancia> dados) { XYSeries s = new XYSeries("Absorbância"); dados.forEach(m -> s.add(m.tempo(), m.absorbancia())); return new XYSeriesCollection(s); }
    public XYSeriesCollection dadosColorimetria(List<AmostraColorimetrica> dados) { XYSeries s = new XYSeries("Amostras"); dados.stream().filter(a -> a.concentracao() != null).forEach(a -> s.add(a.concentracao().doubleValue(), a.absorbancia())); return new XYSeriesCollection(s); }
    private XYSeriesCollection serieDecaimento(List<PontoDecaimento> pontos) { XYSeries s = new XYSeries("Quantidade restante"); pontos.forEach(p -> s.add(p.tempo(), p.quantidade())); return new XYSeriesCollection(s); }
}
