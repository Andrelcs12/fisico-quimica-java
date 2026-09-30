package br.com.unit.fisicoquimica;

import br.com.unit.fisicoquimica.ui.MainFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.GraphicsEnvironment;

public class Main {
    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless()) {
            System.out.println("Ambiente sem interface gráfica: inicialização Swing não exibida.");
            return;
        }
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Usa o tema padrão quando o tema do sistema não estiver disponível.
            }
            new MainFrame().setVisible(true);
        });
    }
}
