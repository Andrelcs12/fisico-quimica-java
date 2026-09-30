package br.com.unit.fisicoquimica;

import br.com.unit.fisicoquimica.model.*;
import br.com.unit.fisicoquimica.decaimento.DecaimentoNuclearService;
import br.com.unit.fisicoquimica.service.*;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.Test;
import java.nio.file.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CsvEIntegracaoTest {
 @TempDir Path temporario;
 private final CsvService csv = new CsvService();
 @Test void leCsvValidoELinhasVazias() throws Exception { Path f=temporario.resolve("dados.csv");Files.writeString(f,"Time,Signal\n\n0,100\n1,90\n\n2,80\n");var dados=csv.lerMedicoes(f.toFile());assertEquals(3,dados.size());assertEquals(1,dados.get(1).tempo());var e=new EstatisticaService().calcular(dados);assertEquals(90,e.media(),1e-9);assertEquals(80,e.minimo(),1e-9);assertEquals(100,e.maximo(),1e-9); }
 @Test void rejeitaCsvComNumeroInvalido() throws Exception { Path f=temporario.resolve("invalido.csv");Files.writeString(f,"Time,Signal\n0,100\n1,erro\n");assertThrows(java.io.IOException.class,()->csv.lerMedicoes(f.toFile())); }
 @Test void validaCabecalhoMesmoComLinhasVaziasNoInicio() throws Exception { Path valido=temporario.resolve("valido.csv");Files.writeString(valido,"\n\nTime,Signal\n0,100\n");assertEquals(1,csv.lerMedicoes(valido.toFile()).size());Path invalido=temporario.resolve("cabecalho-invalido.csv");Files.writeString(invalido,"Tempo,Valor\n0,100\n");var erro=assertThrows(java.io.IOException.class,()->csv.lerMedicoes(invalido.toFile()));assertTrue(erro.getMessage().contains("Time,Signal")); }
 @Test void rejeitaColunasExtrasENumerosNaoFinitos() throws Exception { Path extras=temporario.resolve("extras.csv");Files.writeString(extras,"Time,Signal\n0,10,20\n");assertThrows(java.io.IOException.class,()->csv.lerMedicoes(extras.toFile()));Path infinito=temporario.resolve("infinito.csv");Files.writeString(infinito,"Time,Signal\n0,NaN\n");assertThrows(java.io.IOException.class,()->csv.lerMedicoes(infinito.toFile())); }
 @Test void rejeitaArquivoInexistenteEVazioOuSomenteComCabecalho() throws Exception { assertThrows(java.io.IOException.class,()->csv.lerMedicoes(temporario.resolve("ausente.csv").toFile())); Path vazio=temporario.resolve("vazio.csv");Files.writeString(vazio,"");assertThrows(java.io.IOException.class,()->csv.lerMedicoes(vazio.toFile()));Path cabecalho=temporario.resolve("cabecalho.csv");Files.writeString(cabecalho,"Time,Signal\n");assertThrows(java.io.IOException.class,()->csv.lerMedicoes(cabecalho.toFile())); }
 @Test void processaAbsorbanciaENormalizaTempoZero() { var p=csv.calcularAbsorbancia(List.of(new MedicaoExperimental(0,50),new MedicaoExperimental(1,30),new MedicaoExperimental(2,10),new MedicaoExperimental(3,20)));assertEquals(-2,p.get(0).tempo());assertEquals(0,p.get(0).absorbancia(),1e-9);assertEquals(Math.log10(5),p.get(2).absorbancia(),1e-9);assertTrue(p.stream().allMatch(x->Double.isFinite(x.absorbancia()))); }
 @Test void absorbanciaUsaMaiorSinalComoReferencia() { var p=csv.calcularAbsorbancia(List.of(new MedicaoExperimental(0,100),new MedicaoExperimental(1,10)));assertEquals(0,p.get(0).absorbancia(),1e-9);assertEquals(1,p.get(1).absorbancia(),1e-9); }
 @Test void ignoraSinalNaoPositivoNoProcessamento() { var p=csv.calcularAbsorbancia(List.of(new MedicaoExperimental(0,100),new MedicaoExperimental(1,0),new MedicaoExperimental(2,-5),new MedicaoExperimental(3,10)));assertEquals(2,p.size());assertTrue(p.stream().allMatch(x->Double.isFinite(x.absorbancia()))); }
 @Test void exportaConteudoDeDecaimento() throws Exception { var dados=new DecaimentoNuclearService().simular(100,10,20,3);Path f=temporario.resolve("saida").resolve("decaimento.csv");csv.exportar(f.toFile(),"Tempo,Quantidade",dados.stream().map(x->x.tempo()+","+x.quantidade()).toList());var linhas=Files.readAllLines(f);assertEquals("Tempo,Quantidade",linhas.get(0));assertEquals(4,linhas.size());assertEquals("10.0,50.0",linhas.get(2));assertEquals("0.0,100.0",linhas.get(1));assertEquals("20.0,25.0",linhas.get(3));assertFalse(linhas.get(0).contains("0.0,100.0"));assertEquals(System.lineSeparator(), Files.readString(f).substring(Files.readString(f).indexOf("Quantidade") + "Quantidade".length(), Files.readString(f).indexOf("Quantidade") + "Quantidade".length() + System.lineSeparator().length())); }
 @Test void leCsvComPontoEVirgulaDecimal() throws Exception { Path f=temporario.resolve("virgula.csv");Files.writeString(f,"Time;Signal\n0,5;10,2\n1,5;12,8\n");var dados=csv.lerMedicoes(f.toFile());assertEquals(0.5,dados.get(0).tempo());assertEquals(12.8,dados.get(1).valor()); }
 @Test void leSampleDataDoRepositorio() throws Exception { var dados=csv.lerMedicoes(Path.of("sample-data","experimento-exemplo.csv").toFile());assertEquals(11,dados.size());assertTrue(dados.stream().allMatch(x->Double.isFinite(x.tempo())&&Double.isFinite(x.valor()))); }
}
