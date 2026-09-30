package br.com.unit.fisicoquimica.model;

/** Resumo imutável para apresentação de um experimento. */
public record RelatorioExperimental(String titulo, Estatisticas estatisticas) {
    public RelatorioExperimental {
        if (titulo == null || titulo.isBlank()) throw new IllegalArgumentException("O título do relatório é obrigatório.");
        if (estatisticas == null) throw new IllegalArgumentException("As estatísticas são obrigatórias.");
    }

    public String comoTexto() {
        return "RELATÓRIO: " + titulo + System.lineSeparator()
                + "Quantidade de medições: " + estatisticas.quantidade() + System.lineSeparator()
                + String.format("Soma dos sinais: %.4f%n", estatisticas.soma())
                + String.format("Média do sinal: %.4f%n", estatisticas.media())
                + String.format("Menor sinal: %.4f%n", estatisticas.minimo())
                + String.format("Maior sinal: %.4f%n", estatisticas.maximo())
                + String.format("Amplitude: %.4f%n", estatisticas.amplitude())
                + String.format("Mediana: %.4f", estatisticas.mediana());
    }
}
