package uniquindio.edu.co;

import java.util.List;

public class ProcessorThread extends Thread {
    private final List<String> listaCompartida;
    private final List<String> processedList;

    public ProcessorThread(List<String> sharedList, List<String> processedList) {
        this.listaCompartida = sharedList;
        this.processedList = processedList;
    }

    @Override
    public void run() {
        synchronized (listaCompartida) {
            for (String line : listaCompartida) {
                String processedLine = line.toUpperCase().replaceAll("[^A-Z0-9\\s]", "");
                TextFileProcessor.addRemovedChars(line.length() - processedLine.length());
                synchronized (processedList) {
                    processedList.add(processedLine);
                }
            }
        }
    }
}
