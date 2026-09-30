package br.com.unit.fisicoquimica.service;

import br.com.unit.fisicoquimica.domain.calculo.Calculadora;
import br.com.unit.fisicoquimica.domain.calculo.CelsiusParaKelvin;
import br.com.unit.fisicoquimica.domain.calculo.KelvinParaCelsius;

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
