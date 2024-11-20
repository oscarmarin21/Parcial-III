package uniquindio.edu.co;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class TextFileProcessor {

    private static final List<String> listaCompartida = new ArrayList<>();
    private static final List<String> listaProcesada = new ArrayList<>();
    private static int caracteresTotalesRemovidos = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el archivo de entrada:");
        String inputFile = scanner.nextLine();

        String outputFile = "salida.txt";

        try {
            ReaderThread reader = new ReaderThread(inputFile, listaCompartida);
            ProcessorThread processor = new ProcessorThread(listaCompartida, listaProcesada);
            WriterThread writer = new WriterThread(listaProcesada, outputFile);

            reader.start();
            reader.join();

            processor.start();
            processor.join();

            writer.start();
            writer.join();

            printStatistics();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void printStatistics() {
        synchronized (listaProcesada) {
            int totalLines = listaProcesada.size();
            String longestLine = listaProcesada.stream()
                    .max(Comparator.comparingInt(String::length))
                    .orElse("");
            System.out.println("Total de líneas procesadas: " + totalLines);
            System.out.println("Línea más larga: " + longestLine + " (Longitud: " + longestLine.length() + ")");
            System.out.println("Total de caracteres eliminados: " + caracteresTotalesRemovidos);
        }
    }

    public static synchronized void addRemovedChars(int count) {
        caracteresTotalesRemovidos += count;
    }
}
