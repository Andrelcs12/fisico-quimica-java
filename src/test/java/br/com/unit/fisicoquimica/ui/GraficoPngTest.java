package br.com.unit.fisicoquimica.ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.imageio.ImageIO;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GraficoPngTest {
    @TempDir Path temporario;

    @Test
    void salvaGraficoPngComResolucaoDeApresentacao() throws Exception {
        XYSeries serie = new XYSeries("Teste");
        serie.add(0, 10);
        serie.add(1, 12);
        JFreeChart grafico = ChartFactory.createXYLineChart("Teste", "Tempo", "Signal", new XYSeriesCollection(serie));
        Path destino = temporario.resolve("graficos").resolve("resultado");

        Ui.salvarGrafico(destino.toFile(), grafico);

        Path png = temporario.resolve("graficos").resolve("resultado.png");
        assertTrue(png.toFile().isFile());
        var imagem = ImageIO.read(png.toFile());
        assertEquals(1200, imagem.getWidth());
        assertEquals(700, imagem.getHeight());
    }
}
