package br.com.unit.fisicoquimica.ui;

import br.com.unit.fisicoquimica.domain.calculo.CelsiusParaKelvin;
import br.com.unit.fisicoquimica.domain.calculo.KelvinParaCelsius;
import br.com.unit.fisicoquimica.service.LaboratorioService;
import br.com.unit.fisicoquimica.util.Dialogos;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

class CalculosPanel extends JPanel {
    private final JTextField valor = new JTextField(12);
    private final JComboBox<String> tipo = new JComboBox<>(new String[]{"Celsius → Kelvin", "Kelvin → Celsius"});
    private final JLabel resultado = new JLabel("Informe um valor e calcule.");
    private final LaboratorioService service = new LaboratorioService(List.of(
            new CelsiusParaKelvin(), new KelvinParaCelsius()));

    CalculosPanel() {
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
            String codigo = tipo.getSelectedIndex() == 0 ? "celsius-kelvin" : "kelvin-celsius";
            double resultadoCalculado = service.calcular(codigo, temperatura);
            resultado.setText(String.format("%.4f %s", resultadoCalculado, tipo.getSelectedIndex() == 0 ? "K" : "°C"));
        } catch (IllegalArgumentException e) {
            Dialogos.erro(this, e.getMessage());
        }
    }
}
