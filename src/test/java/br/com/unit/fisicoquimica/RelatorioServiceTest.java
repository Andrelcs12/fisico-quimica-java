package br.com.unit.fisicoquimica;

import br.com.unit.fisicoquimica.model.MedicaoExperimental;
import br.com.unit.fisicoquimica.service.EstatisticaService;
import br.com.unit.fisicoquimica.service.RelatorioService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RelatorioServiceTest {
    @Test
    void geraResumoDasMedicoes() {
        var service = new RelatorioService(new EstatisticaService());
        var relatorio = service.gerar("Teste de sinal", List.of(
                new MedicaoExperimental(0, 2), new MedicaoExperimental(1, 4)));

        assertEquals(2, relatorio.estatisticas().quantidade());
        assertEquals(3, relatorio.estatisticas().media());
        assertTrue(relatorio.comoTexto().contains("Teste de sinal"));
    }
}
