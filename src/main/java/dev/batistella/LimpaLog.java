package dev.batistella;

import org.json.JSONArray;

import java.io.*;

public class LimpaLog {
    public static void main(String[] args) throws IOException {

        final String CAMINHO_RESOURCES = "src/main/resources/%s";

        final String NOME_ARQUIVO_LOG_ORIGINAL = "log_original.log";

        final String NOME_ARQUIVO_CLEANER = "cleaner.json";

        final String NOME_ARQUIVO_SAIDA = "log_tratado.log";

        /*
         * Armazena o endereço que será disponibilizado o arquivo com os tratamentos
         * A localização exata é: [Diretório do Projeto]/src/main/resources/log_tratado.log
         */
        File logTratado = new File(String.format(CAMINHO_RESOURCES, NOME_ARQUIVO_LOG_ORIGINAL));

        /*
         * Armazena o endereço que será consumidas as frases para realizar os filtros de limpeza
         * A localização exata é: [Diretório do Projeto]/src/main/resources/cleaner.json
         */
        File tratamentoFile = new File(String.format(CAMINHO_RESOURCES, NOME_ARQUIVO_CLEANER));

        /*
         * Deverá conter o arquivo de log original que será tratado
         * A localização exata é: [Diretório do Projeto]/src/main/resources/log_original.log
         */
        File arquivoBase = new File(String.format(CAMINHO_RESOURCES, NOME_ARQUIVO_SAIDA));

        /*
         * OBS: Qual valor de nome de variável pode ser editado, conforme a necessidade
         */


        String linha="";
        StringBuilder tratamento = new StringBuilder();
        JSONArray tratamentos;
        try (

                FileReader frAB = new FileReader(arquivoBase);
                BufferedReader readerAB = new BufferedReader(frAB);
                FileWriter fwlogTratado = new FileWriter(logTratado);
                FileReader frTratamentos = new FileReader(tratamentoFile);
                BufferedWriter bwLogTratado = new BufferedWriter(fwlogTratado);
                BufferedReader readerTratamentos = new BufferedReader(frTratamentos)

                ) {

            while (linha != null) {
                tratamento.append(linha);
                linha = readerTratamentos.readLine();
            }

            tratamentos = new JSONArray(tratamento.toString());

            linha = "";

            StringBuilder gravaLogTratado = new StringBuilder();
            while (linha != null) {
                boolean insere = true;
                for (Object obj : tratamentos) {
                    String tmp = (String) obj;
                    if (linha.contains(tmp)) {
                        insere = false;
                        break;
                    }
                }
                if (insere && !linha.trim().isBlank()) {

                    gravaLogTratado.append(linha).append("\n");
                }
                linha = readerAB.readLine();
            }

            bwLogTratado.append(gravaLogTratado);
        }
    }
}