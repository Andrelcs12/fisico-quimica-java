package br.com.unit.fisicoquimica.dados;

import br.com.unit.fisicoquimica.calculos.CalculosPanel;
import br.com.unit.fisicoquimica.colorimetria.ColorimetriaPanel;
import br.com.unit.fisicoquimica.decaimento.DecaimentoPanel;
import br.com.unit.fisicoquimica.reacao.ReacaoPanel;
import org.junit.jupiter.api.Test;

import javax.swing.SwingUtilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PainelSmokeTest {
    @Test
    void constroiCincoPaineisNoEdt() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            assertNotNull(new CalculosPanel());
            assertNotNull(new ReacaoPanel());
            assertNotNull(new DecaimentoPanel());
            assertNotNull(new ColorimetriaPanel());
            assertNotNull(new DadosExperimentaisPanel());
        });
    }

    @Test
    void demonstracaoPreencheDadosEEstatisticas() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            DadosExperimentaisPanel painel = new DadosExperimentaisPanel();
            painel.atualizarDados(java.util.List.of(
                    new MedicaoExperimental(0, 10),
                    new MedicaoExperimental(1, 12),
                    new MedicaoExperimental(2, 14),
                    new MedicaoExperimental(3, 16),
                    new MedicaoExperimental(4, 18)
            ));
            assertEquals(5, painel.tabela.getRowCount());
            assertTrue(painel.resumo.getText().contains("5 registros"));
            assertTrue(painel.resumo.getText().contains("Média: 14"));
        });
    }
}
