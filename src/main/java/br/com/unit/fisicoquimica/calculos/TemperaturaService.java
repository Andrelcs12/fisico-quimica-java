package br.com.unit.fisicoquimica.calculos;

import br.com.unit.fisicoquimica.shared.Calculadora;

public class TemperaturaService {
    private final Calculadora celsiusParaKelvin = new CelsiusParaKelvin();
    private final Calculadora kelvinParaCelsius = new KelvinParaCelsius();

    public double celsiusParaKelvin(double celsius) {
        return celsiusParaKelvin.calcular(celsius);
    }

    public double kelvinParaCelsius(double kelvin) {
        return kelvinParaCelsius.calcular(kelvin);
    }
}
