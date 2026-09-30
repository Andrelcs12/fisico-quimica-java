package br.com.unit.fisicoquimica.service;

import br.com.unit.fisicoquimica.domain.calculo.CelsiusParaKelvin;
import br.com.unit.fisicoquimica.domain.calculo.KelvinParaCelsius;

public class TemperaturaService {
    public double celsiusParaKelvin(double celsius) {
        return new CelsiusParaKelvin().calcular(celsius);
    }

    public double kelvinParaCelsius(double kelvin) {
        return new KelvinParaCelsius().calcular(kelvin);
    }
}
