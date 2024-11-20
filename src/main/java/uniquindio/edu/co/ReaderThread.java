package uniquindio.edu.co;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ReaderThread extends Thread {
    private final String inputFile;
    private final List<String> sharedList;

    public ReaderThread(String inputFile, List<String> sharedList) {
        this.inputFile = inputFile;
        this.sharedList = sharedList;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                synchronized (sharedList) {
                    sharedList.add(line);
                    if (sharedList.size() >= 5){
                        sharedList.notify();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
