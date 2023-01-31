# addBlankPageToPDF
**¿Cómo harías para poder ejecutar N veces el proceso sobre el mismo directorio y solo modificar cada pdf una sola 
vez?**

Para poder ejecutar N veces el proceso, le daría valor a N mediante un *scanner* para definir el número de veces que
se desea ejecutar la aplicación, en el método main.
Primero deberíamos importar las librerías de *Scanner* de *java.util*
```
import java.util.Scanner;
```
Después, definiríamos la variable *n*:

```
System.out.print("Introduce el número de veces que deseas repetir el proceso: ");
int n = scanner.nextInt();
```
Tras esto, recorrería N veces la lista de archivos
```
for (int i = 0; i < n; i++) {
    for (File file : files) {
        if (isPDFFile(file)) {
           addBlankPageToPDF(file);
        }
    }
}
```
Para evitar que se aplicase más de una vez al mismo archivo el proceso, podemos utilizar la siguiente forma:

Comprobando si la última página está en blanco, para ello primero debemos de importar la librería *InputStream* de 
*java.io*

`import java.io.InputStream;`

Tras esto, modificaremos el método addBlankPage para que compruebe si la última página está en blanco, si es así, no
modificará el archivo, si no, le añadirá la página en blanco al final.

```
    private static void addBlankPage(File file) {
        try {
            PDDocument document = PDDocument.load(file);
            PDPage lastPage = document.getPage(document.getNumberOfPages() - 1);
            InputStream lastPageStream = lastPage.getContents();
            if (lastPageStream.available() > 0) {
                PDPage blankPage = new PDPage();
                document.addPage(blankPage);
            }
            lastPageStream.close();
            document.save(file);
            document.close();
        } catch (IOException e) {
            System.out.println("Error al agregar una página en blanco al archivo " + file.getName());
        }
    }
```

**¿Qué pasa si el directorio contiene un fichero que no es un pdf?**

En este caso en concreto, tan solo lo omitirá, dado que se ha definido un método para comprobar el formato del archivo
con el nombre:
```
    private static boolean isPDFFile(File file) {
        return file.isFile() && file.getName().endsWith(".pdf");
    }
```

**¿Cómo probar / ejecutar la aplicación?**

Para probar la aplicación, en este caso, al estar utilizando un Sistema Operativo Windows 10, se ha creado una variable
de entorno llamada *"VIAFIRMA_PATH"* desde el panel de control, esta variable se le ha definido la ruta a un fichero 
llamado *PDFs* en la raíz de la unidad *C:*, donde se han introducido varios archivos, para realizar las pruebas.

La aplicación puede ser ejecutada desde el mismo *IDE IntelliJ IDEA* desde el que se ha desarrollado con el comando 
*"MAYUS + F10"* o realizando una *build* y ejecutándola desde una consola. 

**¿Cómo podemos ver los logs?**
