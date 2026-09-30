package br.com.unit.fisicoquimica.service;

import br.com.unit.fisicoquimica.model.MedicaoExperimental;
import br.com.unit.fisicoquimica.model.PontoAbsorbancia;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CsvService {
    public List<MedicaoExperimental> lerMedicoes(File arquivo) throws IOException {
        if (arquivo == null) throw new IOException("Nenhum arquivo foi selecionado.");
        if (!arquivo.isFile()) throw new IOException("Arquivo não encontrado: " + arquivo.getPath());

        List<MedicaoExperimental> medicoes = new ArrayList<>();
        List<String> linhas = Files.readAllLines(arquivo.toPath(), StandardCharsets.UTF_8);
        for (int indice = 0; indice < linhas.size(); indice++) {
            String linha = linhas.get(indice).trim();
            if (linha.isEmpty()) continue;

            String[] colunas = linha.contains(";") ? linha.split(";", -1) : linha.split(",", -1);
            if (indice == 0 && !possuiDadosNumericos(colunas)) continue;
            if (colunas.length != 2) throw new IOException("Linha " + (indice + 1) + " deve possuir exatamente Time e Signal.");
            try {
                medicoes.add(new MedicaoExperimental(converterNumero(colunas[0]), converterNumero(colunas[1])));
            } catch (NumberFormatException e) {
                throw new IOException("Valores inválidos na linha " + (indice + 1) + ".", e);
            }
        }
        if (medicoes.isEmpty()) throw new IOException("O arquivo não contém medições numéricas.");
        return medicoes;
    }

    public List<PontoAbsorbancia> calcularAbsorbancia(List<MedicaoExperimental> dados) {
        if (dados == null || dados.isEmpty()) throw new IllegalArgumentException("Não há dados para processar.");
        double referencia = dados.stream().mapToDouble(MedicaoExperimental::valor).filter(valor -> valor > 0).max()
                .orElseThrow(() -> new IllegalArgumentException("Não há sinais positivos para usar como referência."));
        double tempoZero = dados.stream().min(Comparator.comparingDouble(MedicaoExperimental::valor)).orElseThrow().tempo();
        List<PontoAbsorbancia> resultado = new ArrayList<>();
        for (MedicaoExperimental medicao : dados) {
            if (medicao.valor() > 0) {
                resultado.add(new PontoAbsorbancia(medicao.tempo() - tempoZero, -Math.log10(medicao.valor() / referencia)));
            }
        }
        return resultado;
    }

    public void exportar(File arquivo, String cabecalho, List<String> linhas) throws IOException {
        if (arquivo == null) throw new IOException("Selecione um arquivo de destino.");
        if (cabecalho == null || cabecalho.isBlank()) throw new IllegalArgumentException("O cabeçalho do CSV é obrigatório.");
        if (linhas == null) throw new IllegalArgumentException("Os dados para exportação são obrigatórios.");

        Path destino = arquivo.toPath().toAbsolutePath();
        Path diretorio = destino.getParent();
        if (diretorio != null) Files.createDirectories(diretorio);
        try (BufferedWriter escritor = Files.newBufferedWriter(destino, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
            escreverLinha(escritor, cabecalho);
            for (String linha : linhas) escreverLinha(escritor, linha);
        }
    }

    private void escreverLinha(BufferedWriter escritor, String linha) throws IOException {
        escritor.write(linha == null ? "" : linha);
        escritor.write(System.lineSeparator());
    }

    private boolean possuiDadosNumericos(String[] colunas) {
        return colunas.length >= 2 && ehNumero(colunas[0]) && ehNumero(colunas[1]);
    }

    private boolean ehNumero(String texto) {
        try {
            converterNumero(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private double converterNumero(String texto) {
        double numero = Double.parseDouble(texto.trim().replace(',', '.'));
        if (!Double.isFinite(numero)) throw new NumberFormatException("Número não finito");
        return numero;
    }
}
