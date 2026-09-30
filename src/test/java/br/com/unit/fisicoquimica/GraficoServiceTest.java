package br.com.unit.fisicoquimica;

import br.com.unit.fisicoquimica.colorimetria.AmostraColorimetrica;
import br.com.unit.fisicoquimica.decaimento.PontoDecaimento;
import br.com.unit.fisicoquimica.model.MedicaoExperimental;
import br.com.unit.fisicoquimica.service.GraficoService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GraficoServiceTest {
    private final GraficoService service = new GraficoService();

    @Test
    void montaSerieCompletaDeDecaimentoEmOrdem() {
        var dataset = service.dadosDecaimento(List.of(
                new PontoDecaimento(30, 12.5), new PontoDecaimento(0, 100),
                new PontoDecaimento(20, 25), new PontoDecaimento(10, 50)
        ));

        assertEquals(4, dataset.getItemCount(0));
        assertEquals(0, dataset.getXValue(0, 0));
        assertEquals(100, dataset.getYValue(0, 0));
        assertEquals(10, dataset.getXValue(0, 1));
        assertEquals(50, dataset.getYValue(0, 1));
        assertEquals(20, dataset.getXValue(0, 2));
        assertEquals(25, dataset.getYValue(0, 2));
        assertEquals(30, dataset.getXValue(0, 3));
        assertEquals(12.5, dataset.getYValue(0, 3));
    }

    @Test
    void montaPontosDeColorimetriaComConcentracao() {
        var dataset = service.dadosColorimetria(List.of(
                new AmostraColorimetrica("C", 100, 50, 3d, .3),
                new AmostraColorimetrica("A", 100, 80, 1d, .1),
                new AmostraColorimetrica("Sem concentração", 100, 60, null, .2),
                new AmostraColorimetrica("B", 100, 63, 2d, .2)
        ));

        assertEquals(3, dataset.getItemCount(0));
        assertEquals(1, dataset.getXValue(0, 0));
        assertEquals(.1, dataset.getYValue(0, 0));
        assertEquals(2, dataset.getXValue(0, 1));
        assertEquals(.2, dataset.getYValue(0, 1));
        assertEquals(3, dataset.getXValue(0, 2));
        assertEquals(.3, dataset.getYValue(0, 2));
    }

    @Test
    void montaCincoPontosExperimentaisEmOrdemEPermiteSerieVazia() {
        var dataset = service.dadosExperimentais(List.of(
                new MedicaoExperimental(4, 18), new MedicaoExperimental(0, 10),
                new MedicaoExperimental(3, 16), new MedicaoExperimental(1, 12), new MedicaoExperimental(2, 14)
        ));

        assertEquals(5, dataset.getItemCount(0));
        for (int indice = 0; indice < 5; indice++) {
            assertEquals(indice, dataset.getXValue(0, indice));
            assertEquals(10 + indice * 2, dataset.getYValue(0, indice));
        }
        assertEquals(0, service.dadosExperimentais(List.of()).getItemCount(0));
    }
}
