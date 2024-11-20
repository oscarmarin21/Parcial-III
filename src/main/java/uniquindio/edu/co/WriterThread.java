package uniquindio.edu.co;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

class WriterThread extends Thread {
    private final List<String> listaProcesada;
    private final String archivoSalida;

    public WriterThread(List<String> listaProcesada, String archivoSalida) {
        this.listaProcesada = listaProcesada;
        this.archivoSalida = archivoSalida;
    }

    @Override
    public void run() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoSalida))) {
            synchronized (listaProcesada) {
                int lineNumber = 1;
                for (String line : listaProcesada) {
                    writer.write("Línea " + lineNumber + ": " + line);
                    writer.newLine();
                    lineNumber++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo: " + e.getMessage());
        }
    }
}

