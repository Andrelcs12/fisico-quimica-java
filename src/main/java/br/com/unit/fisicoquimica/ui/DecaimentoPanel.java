package br.com.unit.fisicoquimica.ui;

import br.com.unit.fisicoquimica.model.PontoDecaimento;
import br.com.unit.fisicoquimica.service.CsvService;
import br.com.unit.fisicoquimica.service.DecaimentoNuclearService;
import br.com.unit.fisicoquimica.service.GraficoService;
import br.com.unit.fisicoquimica.util.Dialogos;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.util.List;

class DecaimentoPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    final JTextField inicial = new JTextField("100");
    final JTextField meiaVida = new JTextField("10");
    final JTextField tempoFinal = new JTextField("50");
    final JTextField pontos = new JTextField("11");
    final JLabel resumo = new JLabel("Simule para visualizar os resultados.");
    final DefaultTableModel tabela = new DefaultTableModel(new String[]{"Tempo", "Quantidade"}, 0) {
        @Override public boolean isCellEditable(int linha, int coluna) { return false; }
    };
    List<PontoDecaimento> dados = List.of();
    final DecaimentoNuclearService service = new DecaimentoNuclearService();

    DecaimentoPanel() {
        setLayout(new BorderLayout());
        JPanel raiz = Ui.painel("MVP 3 — Simulação nuclear");
        add(raiz);
        JPanel formulario = Ui.formulario();
        Ui.linha(formulario, 0, "Quantidade inicial:", inicial);
        Ui.linha(formulario, 1, "Meia-vida:", meiaVida);
        Ui.linha(formulario, 2, "Tempo final:", tempoFinal);
        Ui.linha(formulario, 3, "Número de pontos:", pontos);
        JButton simular = new JButton("Simular e gerar gráfico");
        JButton exportar = new JButton("Exportar CSV");
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoes.add(simular);
        botoes.add(exportar);
        Ui.linha(formulario, 4, "", botoes);
        Ui.linha(formulario, 5, "Resultado final:", resumo);
        raiz.add(formulario, BorderLayout.NORTH);
        raiz.add(new JScrollPane(new JTable(tabela)), BorderLayout.CENTER);
        simular.addActionListener(evento -> simular());
        exportar.addActionListener(evento -> exportar());
    }

    void simular() {
        try {
            double quantidadeInicial = Dialogos.numero(inicial, "quantidade inicial");
            double meiaVidaInformada = Dialogos.numero(meiaVida, "meia-vida");
            double fim = Dialogos.numero(tempoFinal, "tempo final");
            int quantidadePontos = numeroInteiroDePontos();
            dados = service.simular(quantidadeInicial, meiaVidaInformada, fim, quantidadePontos);
            tabela.setRowCount(0);
            for (PontoDecaimento ponto : dados) tabela.addRow(new Object[]{ponto.tempo(), ponto.quantidade()});
            double restante = dados.get(dados.size() - 1).quantidade();
            double percentual = quantidadeInicial == 0 ? 0 : 100 * restante / quantidadeInicial;
            resumo.setText(String.format("Restante: %.4f | %.2f%% | Decaída: %.4f", restante, percentual, quantidadeInicial - restante));
            grafico();
        } catch (IllegalArgumentException e) {
            Dialogos.erro(this, e.getMessage());
        }
    }

    private int numeroInteiroDePontos() {
        double valor = Dialogos.numero(pontos, "número de pontos");
        if (valor != Math.rint(valor)) throw new IllegalArgumentException("O número de pontos deve ser um número inteiro.");
        if (valor > Integer.MAX_VALUE) throw new IllegalArgumentException("O número de pontos informado é muito grande.");
        return (int) valor;
    }

    void grafico() {
        JFreeChart grafico = ChartFactory.createXYLineChart("Decaimento radioativo", "Tempo", "Quantidade restante",
                new GraficoService().dadosDecaimento(dados), PlotOrientation.VERTICAL, false, true, false);
        Ui.mostrarGrafico(this, "Gráfico de decaimento", grafico, "grafico-decaimento.png");
    }

    void exportar() {
        if (dados.isEmpty()) {
            Dialogos.erro(this, "Execute uma simulação antes de exportar.");
            return;
        }
        File arquivo = Ui.salvar(this, "decaimento.csv");
        if (arquivo == null) return;
        try {
            new CsvService().exportar(arquivo, "Tempo,Quantidade", dados.stream()
                    .map(ponto -> ponto.tempo() + "," + ponto.quantidade()).toList());
            Dialogos.info(this, "CSV exportado com sucesso.");
        } catch (IOException e) {
            Dialogos.erro(this, "Não foi possível exportar: " + e.getMessage());
        }
    }
}
