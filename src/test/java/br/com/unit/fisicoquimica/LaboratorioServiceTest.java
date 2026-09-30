package br.com.unit.fisicoquimica;

import br.com.unit.fisicoquimica.domain.calculo.Calculadora;
import br.com.unit.fisicoquimica.colorimetria.Absorbancia;
import br.com.unit.fisicoquimica.calculos.CelsiusParaKelvin;
import br.com.unit.fisicoquimica.decaimento.DecaimentoNuclear;
import br.com.unit.fisicoquimica.calculos.KelvinParaCelsius;
import br.com.unit.fisicoquimica.reacao.TaxaComparativa;
import br.com.unit.fisicoquimica.service.LaboratorioService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LaboratorioServiceTest {
    @Test
    void aplicaPolimorfismoParaCalculosDiferentes() {
        LaboratorioService laboratorio = new LaboratorioService();
        Calculadora celsiusParaKelvin = new CelsiusParaKelvin();
        Calculadora kelvinParaCelsius = new KelvinParaCelsius();
        Calculadora taxaReacao = new TaxaComparativa();
        Calculadora absorbancia = new Absorbancia();

        assertEquals(273.15, laboratorio.calcular(celsiusParaKelvin, 0), 0.0001);
        assertEquals(0, laboratorio.calcular(kelvinParaCelsius, 273.15), 0.0001);
        assertEquals(0.5, laboratorio.calcular(taxaReacao, 2), 0.0001);
        assertEquals(1, laboratorio.calcular(absorbancia, 100, 10), 0.0001);
    }

    @Test
    void rejeitaCalculadoraNula() {
        LaboratorioService laboratorio = new LaboratorioService();
        assertThrows(IllegalArgumentException.class, () -> laboratorio.calcular(null, 10));
    }

    @Test
    void executaDecaimentoPeloMesmoContratoDasOutrasCalculadoras() {
        LaboratorioService laboratorio = new LaboratorioService();
        Calculadora decaimento = new DecaimentoNuclear();

        assertEquals(25, laboratorio.calcular(decaimento, 100, 10, 20), 0.0001);
        assertThrows(IllegalArgumentException.class,
                () -> laboratorio.calcular(decaimento, 100, 0, 20));
    }
}
