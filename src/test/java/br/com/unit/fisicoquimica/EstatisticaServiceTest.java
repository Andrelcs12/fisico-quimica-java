package br.com.unit.fisicoquimica;

import br.com.unit.fisicoquimica.dados.MedicaoExperimental;
import br.com.unit.fisicoquimica.dados.EstatisticaService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EstatisticaServiceTest {
    private final EstatisticaService service = new EstatisticaService();

    @Test
    void calculaSomaAmplitudeEMedianaComQuantidadeImpar() {
        var estatisticas = service.calcular(List.of(
                new MedicaoExperimental(0, 7),
                new MedicaoExperimental(1, 1),
                new MedicaoExperimental(2, 4)));

        assertEquals(12, estatisticas.soma());
        assertEquals(6, estatisticas.amplitude());
        assertEquals(4, estatisticas.mediana());
    }

    @Test
    void calculaMedianaComQuantidadeParERejeitaValorInvalido() {
        var estatisticas = service.calcular(List.of(
                new MedicaoExperimental(0, 8), new MedicaoExperimental(1, 2),
                new MedicaoExperimental(2, 4), new MedicaoExperimental(3, 6)));

        assertEquals(5, estatisticas.mediana());
        assertThrows(IllegalArgumentException.class,
                () -> service.calcular(List.of(new MedicaoExperimental(0, Double.NaN))));
    }
}
