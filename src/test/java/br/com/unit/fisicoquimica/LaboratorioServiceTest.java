package br.com.unit.fisicoquimica;

import br.com.unit.fisicoquimica.domain.calculo.Calculadora;
import br.com.unit.fisicoquimica.domain.calculo.CelsiusParaKelvin;
import br.com.unit.fisicoquimica.domain.calculo.DecaimentoNuclear;
import br.com.unit.fisicoquimica.domain.calculo.KelvinParaCelsius;
import br.com.unit.fisicoquimica.domain.calculo.TaxaComparativa;
import br.com.unit.fisicoquimica.service.LaboratorioService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LaboratorioServiceTest {
    @Test
    void aplicaPolimorfismoParaCalculosDiferentes() {
        List<Calculadora> calculadoras = List.of(
                new CelsiusParaKelvin(), new KelvinParaCelsius(), new TaxaComparativa());
        LaboratorioService laboratorio = new LaboratorioService(calculadoras);

        assertEquals(273.15, laboratorio.calcular("celsius-kelvin", 0), 0.0001);
        assertEquals(0, laboratorio.calcular("kelvin-celsius", 273.15), 0.0001);
        assertEquals(0.5, laboratorio.calcular("taxa-reacao", 2), 0.0001);
    }

    @Test
    void rejeitaCodigoQueNaoFoiRegistrado() {
        LaboratorioService laboratorio = new LaboratorioService(List.of(new CelsiusParaKelvin()));
        assertThrows(IllegalArgumentException.class, () -> laboratorio.calcular("inexistente", 10));
    }

    @Test
    void executaDecaimentoPeloMesmoContratoDasOutrasCalculadoras() {
        LaboratorioService laboratorio = new LaboratorioService(List.of(new DecaimentoNuclear()));

        assertEquals(25, laboratorio.calcular("decaimento-nuclear", 100, 10, 20), 0.0001);
        assertThrows(IllegalArgumentException.class,
                () -> laboratorio.calcular("decaimento-nuclear", 100, 0, 20));
    }
}
