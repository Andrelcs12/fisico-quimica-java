package br.com.unit.fisicoquimica.calculos;

import br.com.unit.fisicoquimica.util.Dialogos;
import br.com.unit.fisicoquimica.ui.Ui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

public class CalculosPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private final JTextField valor = new JTextField(12);
    private final JComboBox<String> tipo = new JComboBox<>(new String[]{"Celsius → Kelvin", "Kelvin → Celsius"});
    private final JLabel resultado = new JLabel("Informe um valor e calcule.");
    private final TemperaturaService service = new TemperaturaService();

    public CalculosPanel() {
        setLayout(new BorderLayout());
        JPanel formulario = Ui.formulario();
        Ui.linha(formulario, 0, "Conversão:", tipo);
        Ui.linha(formulario, 1, "Temperatura:", valor);
        JButton calcular = new JButton("Calcular");
        JButton limpar = new JButton("Limpar");
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoes.add(calcular);
        botoes.add(limpar);
        Ui.linha(formulario, 2, "", botoes);
        resultado.setFont(resultado.getFont().deriveFont(Font.BOLD, 18f));
        Ui.linha(formulario, 3, "Resultado:", resultado);
        JPanel raiz = Ui.painel("MVP 1 — Cálculos básicos");
        raiz.add(formulario, BorderLayout.NORTH);
        add(raiz, BorderLayout.CENTER);
        calcular.addActionListener(evento -> calcular());
        limpar.addActionListener(evento -> { valor.setText(""); resultado.setText("Informe um valor e calcule."); });
    }

    private void calcular() {
        try {
            double temperatura = Dialogos.numero(valor, "temperatura");
            double resultadoCalculado = tipo.getSelectedIndex() == 0
                    ? service.celsiusParaKelvin(temperatura)
                    : service.kelvinParaCelsius(temperatura);
            resultado.setText(String.format("%.4f %s", resultadoCalculado, tipo.getSelectedIndex() == 0 ? "K" : "°C"));
        } catch (IllegalArgumentException e) {
            Dialogos.erro(this, e.getMessage());
        }
    }
}
