package br.com.unit.fisicoquimica.ui;

import br.com.unit.fisicoquimica.model.MedicaoExperimental;
import br.com.unit.fisicoquimica.service.CsvService;
import br.com.unit.fisicoquimica.service.EstatisticaService;
import br.com.unit.fisicoquimica.service.RelatorioService;
import br.com.unit.fisicoquimica.util.Dialogos;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.util.List;

class RelatorioPanel extends JPanel {
    private final JTextField titulo = new JTextField("Experimento de físico-química", 24);
    private final JLabel arquivoSelecionado = new JLabel("Nenhum arquivo selecionado.");
    private final JTextArea resultado = new JTextArea();
    private final CsvService csvService = new CsvService();
    private final RelatorioService relatorioService = new RelatorioService(new EstatisticaService());
    private File arquivo;

    RelatorioPanel() {
        setLayout(new BorderLayout());
        JPanel formulario = Ui.formulario();
        Ui.linha(formulario, 0, "Título:", titulo);
        JButton escolher = new JButton("Escolher CSV");
        JButton gerar = new JButton("Gerar relatório");
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoes.add(escolher);
        botoes.add(gerar);
        Ui.linha(formulario, 1, "Arquivo:", arquivoSelecionado);
        Ui.linha(formulario, 2, "", botoes);

        resultado.setEditable(false);
        resultado.setLineWrap(true);
        resultado.setWrapStyleWord(true);
        JPanel raiz = Ui.painel("MVP 6 — Relatório experimental");
        raiz.add(formulario, BorderLayout.NORTH);
        raiz.add(new JScrollPane(resultado), BorderLayout.CENTER);
        add(raiz, BorderLayout.CENTER);

        escolher.addActionListener(evento -> escolherArquivo());
        gerar.addActionListener(evento -> gerarRelatorio());
    }

    private void escolherArquivo() {
        JFileChooser seletor = new JFileChooser();
        if (seletor.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            arquivo = seletor.getSelectedFile();
            arquivoSelecionado.setText(arquivo.getName());
        }
    }

    private void gerarRelatorio() {
        try {
            List<MedicaoExperimental> medicoes = csvService.lerMedicoes(arquivo);
            resultado.setText(relatorioService.gerar(titulo.getText().trim(), medicoes).comoTexto());
        } catch (IOException | IllegalArgumentException e) {
            Dialogos.erro(this, e.getMessage());
        }
    }
}
