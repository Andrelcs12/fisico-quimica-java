package br.com.unit.fisicoquimica.shared;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Component;

public final class Dialogos {
    private Dialogos() { }

    public static double numero(JTextField campo, String nome) {
        try {
            String texto = campo.getText().trim();
            if (texto.isEmpty()) throw new NumberFormatException();
            double valor = Double.parseDouble(texto.replace(',', '.'));
            if (!Double.isFinite(valor)) throw new NumberFormatException();
            return valor;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Informe um valor numérico válido para " + nome + ".");
        }
    }

    public static void erro(Component componente, String mensagem) {
        JOptionPane.showMessageDialog(componente, mensagem, "Atenção", JOptionPane.WARNING_MESSAGE);
    }

    public static void info(Component componente, String mensagem) {
        JOptionPane.showMessageDialog(componente, mensagem, "Resultado", JOptionPane.INFORMATION_MESSAGE);
    }
}
