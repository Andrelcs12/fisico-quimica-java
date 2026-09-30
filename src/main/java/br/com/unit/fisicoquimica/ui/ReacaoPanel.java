package br.com.unit.fisicoquimica.ui;

import br.com.unit.fisicoquimica.model.MedicaoReacao;
import br.com.unit.fisicoquimica.service.CsvService;
import br.com.unit.fisicoquimica.service.ReacaoService;
import br.com.unit.fisicoquimica.util.Dialogos;

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

class ReacaoPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    final JTextField temperatura = new JTextField();
    final JTextField tempo = new JTextField();
    final DefaultTableModel tabela = new DefaultTableModel(new String[]{"Temperatura", "Tempo", "Taxa (1/tempo)"}, 0) {
        @Override public boolean isCellEditable(int linha, int coluna) { return false; }
    };
    final List<MedicaoReacao> dados = new ArrayList<>();
    final ReacaoService service = new ReacaoService();

    ReacaoPanel() {
        setLayout(new BorderLayout());
        JPanel raiz = Ui.painel("MVP 2 — Análise de reação química");
        add(raiz);
        JPanel formulario = Ui.formulario();
        Ui.linha(formulario, 0, "Temperatura:", temperatura);
        Ui.linha(formulario, 1, "Tempo/período:", tempo);
        JButton adicionar = new JButton("Adicionar medição");
        JButton remover = new JButton("Remover selecionada");
        JButton limpar = new JButton("Limpar todas");
        JButton exportar = new JButton("Exportar CSV");
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        for (JButton botao : List.of(adicionar, remover, limpar, exportar)) botoes.add(botao);
        Ui.linha(formulario, 2, "", botoes);
        raiz.add(formulario, BorderLayout.NORTH);
        JTable tabelaVisual = new JTable(tabela);
        tabelaVisual.setFillsViewportHeight(true);
        raiz.add(new JScrollPane(tabelaVisual), BorderLayout.CENTER);
        adicionar.addActionListener(evento -> adicionar());
        remover.addActionListener(evento -> { int selecionada = tabelaVisual.getSelectedRow(); if (selecionada >= 0) { dados.remove(selecionada); tabela.removeRow(selecionada); } });
        limpar.addActionListener(evento -> { dados.clear(); tabela.setRowCount(0); });
        exportar.addActionListener(evento -> exportar());
    }

    void adicionar() {
        try {
            double temperaturaInformada = Dialogos.numero(temperatura, "temperatura");
            double tempoInformado = Dialogos.numero(tempo, "tempo");
            double taxa = service.calcularTaxa(tempoInformado);
            dados.add(new MedicaoReacao(temperaturaInformada, tempoInformado, taxa));
            tabela.addRow(new Object[]{temperaturaInformada, tempoInformado, String.format("%.6f", taxa)});
            temperatura.setText("");
            tempo.setText("");
        } catch (IllegalArgumentException e) {
            Dialogos.erro(this, e.getMessage());
        }
    }

    void exportar() {
        File arquivo = Ui.salvar(this, "reacao.csv");
        if (arquivo == null) return;
        try {
            new CsvService().exportar(arquivo, "Temperatura,Tempo,Taxa", dados.stream()
                    .map(medicao -> medicao.temperatura() + "," + medicao.tempo() + "," + medicao.taxa()).toList());
            Dialogos.info(this, "CSV exportado com sucesso.");
        } catch (IOException e) {
            Dialogos.erro(this, "Não foi possível exportar: " + e.getMessage());
        }
    }
}
