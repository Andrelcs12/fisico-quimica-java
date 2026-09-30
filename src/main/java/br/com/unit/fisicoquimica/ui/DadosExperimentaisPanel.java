package br.com.unit.fisicoquimica.ui;

import br.com.unit.fisicoquimica.model.Estatisticas;
import br.com.unit.fisicoquimica.model.MedicaoExperimental;
import br.com.unit.fisicoquimica.service.CsvService;
import br.com.unit.fisicoquimica.service.EstatisticaService;
import br.com.unit.fisicoquimica.service.GraficoService;
import br.com.unit.fisicoquimica.util.Dialogos;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.util.List;

class DadosExperimentaisPanel extends JPanel {
    private static final List<MedicaoExperimental> DADOS_DEMONSTRACAO = List.of(
            new MedicaoExperimental(0, 10),
            new MedicaoExperimental(1, 12),
            new MedicaoExperimental(2, 14),
            new MedicaoExperimental(3, 16),
            new MedicaoExperimental(4, 18)
    );
    final DefaultTableModel tabela = new DefaultTableModel(new String[]{"Tempo", "Valor / Signal"}, 0) {
        @Override public boolean isCellEditable(int linha, int coluna) { return false; }
    };
    final JLabel resumo = new JLabel("Selecione um CSV no formato Time,Signal.");
    List<MedicaoExperimental> dados = List.of();
    final CsvService csv = new CsvService();

    DadosExperimentaisPanel() {
        setLayout(new BorderLayout());
        JPanel raiz = Ui.painel("MVP 5 — Análise de dados experimentais");
        add(raiz);
        JButton abrir = new JButton("Selecionar CSV");
        JButton demonstracao = new JButton("Executar demonstração");
        JButton grafico = new JButton("Gráfico dos dados");
        JButton absorbancia = new JButton("Processar absorbância");
        JButton exportar = new JButton("Exportar dados");
        JButton exportarAbsorbancia = new JButton("Exportar absorbância");
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        for (JButton botao : List.of(abrir, demonstracao, grafico, absorbancia, exportar, exportarAbsorbancia)) botoes.add(botao);
        raiz.add(botoes, BorderLayout.NORTH);
        JTable tabelaVisual = new JTable(tabela);
        tabelaVisual.setFillsViewportHeight(true);
        raiz.add(new JScrollPane(tabelaVisual), BorderLayout.CENTER);
        raiz.add(resumo, BorderLayout.SOUTH);
        abrir.addActionListener(evento -> abrir());
        demonstracao.addActionListener(evento -> executarDemonstracao());
        grafico.addActionListener(evento -> graficoDados());
        absorbancia.addActionListener(evento -> graficoAbsorbancia());
        exportar.addActionListener(evento -> exportarDados());
        exportarAbsorbancia.addActionListener(evento -> exportarAbs());
    }

    void abrir() {
        JFileChooser seletor = new JFileChooser();
        if (seletor.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
        try {
            atualizarDados(csv.lerMedicoes(seletor.getSelectedFile()));
        } catch (IOException | IllegalArgumentException e) {
            Dialogos.erro(this, "CSV inválido: " + e.getMessage());
        }
    }

    void executarDemonstracao() {
        atualizarDados(DADOS_DEMONSTRACAO);
        graficoDados();
    }

    void atualizarDados(List<MedicaoExperimental> novosDados) {
        dados = List.copyOf(novosDados);
        tabela.setRowCount(0);
        for (MedicaoExperimental medicao : dados) tabela.addRow(new Object[]{medicao.tempo(), medicao.valor()});
        Estatisticas estatisticas = new EstatisticaService().calcular(dados);
        resumo.setText(String.format("%d registros | Média: %.2f | Mínimo: %.2f | Máximo: %.2f",
                estatisticas.quantidade(), estatisticas.media(), estatisticas.minimo(), estatisticas.maximo()));
    }

    void graficoDados() {
        if (dados.isEmpty()) {
            Dialogos.erro(this, "Selecione um CSV primeiro.");
            return;
        }
        JFreeChart grafico = ChartFactory.createXYLineChart("Dados experimentais", "Tempo", "Signal",
                new GraficoService().dadosExperimentais(dados), PlotOrientation.VERTICAL, false, true, false);
        Ui.mostrarGrafico(this, "Gráfico dos dados experimentais", grafico, "grafico-dados-experimentais.png");
    }

    void graficoAbsorbancia() {
        try {
            var absorbancia = csv.calcularAbsorbancia(dados);
            JFreeChart grafico = ChartFactory.createXYLineChart("Absorbância transiente", "Tempo normalizado", "Absorbância",
                    new GraficoService().dadosAbsorbancia(absorbancia), PlotOrientation.VERTICAL, false, true, false);
            Ui.mostrarGrafico(this, "Gráfico de absorbância", grafico, "grafico-absorbancia.png");
        } catch (IllegalArgumentException e) {
            Dialogos.erro(this, e.getMessage());
        }
    }

    void exportarDados() {
        if (dados.isEmpty()) {
            Dialogos.erro(this, "Não há dados para exportar.");
            return;
        }
        File arquivo = Ui.salvar(this, "dados_processados.csv");
        if (arquivo == null) return;
        try {
            csv.exportar(arquivo, "Time,Signal", dados.stream().map(medicao -> medicao.tempo() + "," + medicao.valor()).toList());
            Dialogos.info(this, "CSV exportado com sucesso.");
        } catch (IOException e) {
            Dialogos.erro(this, "Não foi possível exportar: " + e.getMessage());
        }
    }

    void exportarAbs() {
        try {
            var absorbancia = csv.calcularAbsorbancia(dados);
            File arquivo = Ui.salvar(this, "absorbancia_processada.csv");
            if (arquivo == null) return;
            csv.exportar(arquivo, "TempoNormalizado,Absorbancia", absorbancia.stream()
                    .map(ponto -> ponto.tempo() + "," + ponto.absorbancia()).toList());
            Dialogos.info(this, "CSV de absorbância exportado com sucesso.");
        } catch (IllegalArgumentException | IOException e) {
            Dialogos.erro(this, "Não foi possível exportar: " + e.getMessage());
        }
    }
}
