package br.com.unit.fisicoquimica.ui;

import br.com.unit.fisicoquimica.shared.Dialogos;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Locale;

public final class Ui {
    private static final int LARGURA_GRAFICO_PNG = 1200;
    private static final int ALTURA_GRAFICO_PNG = 700;

    private Ui() { }

    public static JPanel painel(String titulo) {
        JPanel painel = new JPanel(new BorderLayout(12, 12));
        painel.setBorder(BorderFactory.createTitledBorder(new EmptyBorder(18, 18, 18, 18), titulo));
        return painel;
    }

    public static JPanel formulario() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(new EmptyBorder(8, 8, 8, 8));
        return painel;
    }

    public static void linha(JPanel painel, int linha, String texto, JComponent componente) {
        GridBagConstraints restricoes = new GridBagConstraints();
        restricoes.insets = new Insets(5, 5, 5, 5);
        restricoes.anchor = GridBagConstraints.WEST;
        restricoes.gridx = 0;
        restricoes.gridy = linha;
        painel.add(new javax.swing.JLabel(texto), restricoes);
        restricoes.gridx = 1;
        restricoes.fill = GridBagConstraints.HORIZONTAL;
        restricoes.weightx = 1;
        painel.add(componente, restricoes);
    }

    public static File salvar(Component componente, String nome) {
        JFileChooser seletor = new JFileChooser();
        seletor.setSelectedFile(new File(nome));
        return seletor.showSaveDialog(componente) == JFileChooser.APPROVE_OPTION ? seletor.getSelectedFile() : null;
    }

    public static void mostrarGrafico(Component origem, String titulo, JFreeChart grafico, String nomePadrao) {
        ChartPanel painelGrafico = new ChartPanel(grafico);
        painelGrafico.setMouseWheelEnabled(true);
        JFrame janela = new JFrame(titulo);
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janela.setLayout(new BorderLayout());
        janela.add(painelGrafico, BorderLayout.CENTER);
        JButton salvar = new JButton("Salvar gráfico");
        salvar.addActionListener(evento -> salvarGraficoComDialogo(origem, grafico, nomePadrao));
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rodape.add(salvar);
        janela.add(rodape, BorderLayout.SOUTH);
        janela.setMinimumSize(new Dimension(640, 420));
        janela.setSize(850, 550);
        janela.setLocationRelativeTo(origem);
        janela.setVisible(true);
    }

    private static void salvarGraficoComDialogo(Component origem, JFreeChart grafico, String nomePadrao) {
        JFileChooser seletor = new JFileChooser();
        seletor.setFileFilter(new FileNameExtensionFilter("Imagem PNG (*.png)", "png"));
        seletor.setSelectedFile(new File(nomePadrao));
        if (seletor.showSaveDialog(origem) != JFileChooser.APPROVE_OPTION) return;
        try {
            salvarGrafico(seletor.getSelectedFile(), grafico);
            Dialogos.info(origem, "Gráfico salvo em PNG com sucesso.");
        } catch (IOException e) {
            Dialogos.erro(origem, "Não foi possível salvar o gráfico: " + e.getMessage());
        }
    }

    static void salvarGrafico(File arquivo, JFreeChart grafico) throws IOException {
        if (arquivo == null || grafico == null) throw new IllegalArgumentException("Informe o gráfico e o arquivo de destino.");
        File destino = comExtensaoPng(arquivo);
        File diretorio = destino.getAbsoluteFile().getParentFile();
        if (diretorio != null) Files.createDirectories(diretorio.toPath());
        ChartUtils.saveChartAsPNG(destino, grafico, LARGURA_GRAFICO_PNG, ALTURA_GRAFICO_PNG);
    }

    private static File comExtensaoPng(File arquivo) {
        return arquivo.getName().toLowerCase(Locale.ROOT).endsWith(".png") ? arquivo : new File(arquivo.getPath() + ".png");
    }
}
