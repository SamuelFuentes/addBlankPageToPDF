import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.File;
import java.io.IOException;

public class AddBlankPageToPDF {
    //Lee la ruta del directorio desde la variable de entorno previamente definida y agrega una página en blanco a cada
    //archivo PDF
    public static void main(String[] args) {
        String directoryPath = System.getenv("VIAFIRMA_PATH");
        if(directoryPath != null) {
            verifyPath(directoryPath);
        } else {
            System.out.println("La variable de entorno directoryPath no está definida, por favor corrija este error" +
                    "antes de volver a ejecutar la aplicación");
            return;
        }
    }

    //Verifica si la ruta del directorio es válida y si contiene archivos PDF, entonces agrega una página en blanco a
    //cada archivo PDF
    private static void verifyPath(String directoryPath) {
        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("La ruta del directorio es inválida");
            return;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            System.out.println("No se encontraron archivos en el directorio");
            return;
        }

        for (File file : files) {
            if (isPDFFile(file)) {
                addBlankPage(file);
            }
        }
    }

    //Abre el archivo PDF y agrega una página en blanco al final del archivo
    private static void addBlankPage(File file) {
        try {
            PDDocument document = PDDocument.load(file);
            PDPage blankPage = new PDPage();
            document.addPage(blankPage);
            document.save(file);
            document.close();
        } catch (IOException e) {
            System.out.println("Error al agregar una página en blanco al archivo " + file.getName());
        }
    }

    //Verifica si el archivo es un PDF
    private static boolean isPDFFile(File file) {
        return file.isFile() && file.getName().endsWith(".pdf");
    }
}
