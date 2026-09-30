package br.com.unit.fisicoquimica.ui;

import br.com.unit.fisicoquimica.calculos.CalculosPanel;
import br.com.unit.fisicoquimica.colorimetria.ColorimetriaPanel;
import br.com.unit.fisicoquimica.decaimento.DecaimentoPanel;
import br.com.unit.fisicoquimica.reacao.ReacaoPanel;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.Dimension;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    public MainFrame() {
        super("Físico-Química com Java");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1050, 720);
        setMinimumSize(new Dimension(850, 600));
        setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("1. Cálculos", new CalculosPanel());
        abas.addTab("2. Reação", new ReacaoPanel());
        abas.addTab("3. Decaimento", new DecaimentoPanel());
        abas.addTab("4. Colorimetria", new ColorimetriaPanel());
        abas.addTab("5. Dados experimentais", new DadosExperimentaisPanel());
        add(abas);
    }
}
