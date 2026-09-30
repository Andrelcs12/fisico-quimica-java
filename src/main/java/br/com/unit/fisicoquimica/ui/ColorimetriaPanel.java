package br.com.unit.fisicoquimica.ui;

import br.com.unit.fisicoquimica.model.AmostraColorimetrica;
import br.com.unit.fisicoquimica.service.ColorimetriaService;
import br.com.unit.fisicoquimica.service.CsvService;
import br.com.unit.fisicoquimica.service.GraficoService;
import br.com.unit.fisicoquimica.util.Dialogos;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ColorimetriaPanel extends JPanel {
    final JTextField id = new JTextField();
    final JTextField i0 = new JTextField();
    final JTextField i = new JTextField();
    final JTextField conc = new JTextField();
    final DefaultTableModel tabela = new DefaultTableModel(new String[]{"Amostra", "I0", "I", "Concentração", "Absorbância"}, 0) {
        @Override public boolean isCellEditable(int linha, int coluna) { return false; }
    };
    final List<AmostraColorimetrica> dados = new ArrayList<>();
    final ColorimetriaService service = new ColorimetriaService();

    ColorimetriaPanel() {
        setLayout(new BorderLayout());
        JPanel raiz = Ui.painel("MVP 4 — Colorimetria");
        add(raiz);
        JPanel formulario = Ui.formulario();
        Ui.linha(formulario, 0, "Identificação:", id);
        Ui.linha(formulario, 1, "I0 (referência):", i0);
        Ui.linha(formulario, 2, "I (medida):", i);
        Ui.linha(formulario, 3, "Concentração (opcional):", conc);
        JButton adicionar = new JButton("Adicionar amostra");
        JButton remover = new JButton("Remover");
        JButton limpar = new JButton("Limpar");
        JButton grafico = new JButton("Gráfico");
        JButton exportar = new JButton("Exportar CSV");
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        for (JButton botao : List.of(adicionar, remover, limpar, grafico, exportar)) botoes.add(botao);
        Ui.linha(formulario, 4, "", botoes);
        raiz.add(formulario, BorderLayout.NORTH);
        JTable tabelaVisual = new JTable(tabela);
        tabelaVisual.setFillsViewportHeight(true);
        raiz.add(new JScrollPane(tabelaVisual), BorderLayout.CENTER);
        adicionar.addActionListener(evento -> adicionar());
        remover.addActionListener(evento -> {
            int selecionada = tabelaVisual.getSelectedRow();
            if (selecionada >= 0) { dados.remove(selecionada); tabela.removeRow(selecionada); }
        });
        limpar.addActionListener(evento -> { dados.clear(); tabela.setRowCount(0); });
        grafico.addActionListener(evento -> grafico());
        exportar.addActionListener(evento -> exportar());
    }

    void adicionar() {
        try {
            String nome = id.getText().trim();
            if (nome.isEmpty()) throw new IllegalArgumentException("Informe a identificação da amostra.");
            Double concentracao = conc.getText().trim().isEmpty() ? null : Dialogos.numero(conc, "concentração");
            double inicial = Dialogos.numero(i0, "I0");
            double medida = Dialogos.numero(i, "I");
            double absorbancia = service.calcularAbsorbancia(inicial, medida);
            AmostraColorimetrica amostra = new AmostraColorimetrica(nome, inicial, medida, concentracao, absorbancia);
            dados.add(amostra);
            tabela.addRow(new Object[]{nome, inicial, medida, concentracao == null ? "—" : concentracao, absorbancia});
            id.setText(""); i0.setText(""); i.setText(""); conc.setText("");
        } catch (IllegalArgumentException e) {
            Dialogos.erro(this, e.getMessage());
        }
    }

    void grafico() {
        List<AmostraColorimetrica> comConcentracao = dados.stream().filter(amostra -> amostra.concentracao() != null).toList();
        if (comConcentracao.isEmpty()) {
            Dialogos.erro(this, "Adicione ao menos uma amostra com concentração.");
            return;
        }
        JFreeChart grafico = ChartFactory.createScatterPlot("Colorimetria: concentração × absorbância", "Concentração", "Absorbância",
                new GraficoService().dadosColorimetria(comConcentracao), PlotOrientation.VERTICAL, false, true, false);
        Ui.mostrarGrafico(this, "Gráfico de colorimetria", grafico, "grafico-colorimetria.png");
    }

    void exportar() {
        File arquivo = Ui.salvar(this, "colorimetria.csv");
        if (arquivo == null) return;
        try {
            new CsvService().exportar(arquivo, "Amostra,I0,I,Concentracao,Absorbancia", dados.stream()
                    .map(amostra -> amostra.identificacao() + "," + amostra.intensidadeInicial() + "," + amostra.intensidadeMedida()
                            + "," + (amostra.concentracao() == null ? "" : amostra.concentracao()) + "," + amostra.absorbancia()).toList());
            Dialogos.info(this, "CSV exportado com sucesso.");
        } catch (IOException e) {
            Dialogos.erro(this, "Não foi possível exportar: " + e.getMessage());
        }
    }
}
