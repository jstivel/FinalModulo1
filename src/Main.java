import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    private static final String BASE_PATH = "D:/CodeGym/modulo1_final/archivos/";
    private static final String EXTENSION = ".txt";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        // Obtener texto del usuario
        System.out.println("ingrese el texto que desea guardar");
        var inputText = scanner.nextLine();

        // Obtener nombre del archivo del usuario
        System.out.println("ingrese el nombre del archivo");
        var fileName = scanner.nextLine();

        // Obtener número de desplazamientos del usuario
        int desplazamiento = getDesplazamiento(scanner);

        // Crear y escribir en los archivos
        String filePath = BASE_PATH + fileName + EXTENSION;
        String filePathDesplazamiento = BASE_PATH + "desplazamientos.txt";
        writeArchivo(filePath, inputText);
        writeArchivo(filePathDesplazamiento, String.valueOf("Desplazamiento:"+desplazamiento));

        // Encriptado
        var pathEncryptedFile = BASE_PATH + fileName + "_encriptado" + EXTENSION;
        encriptarArchivo(filePath, pathEncryptedFile, desplazamiento);

        // Desencriptar contenido

        try {
            desencriptarArchivo(scanner, desplazamiento);
        } catch (Exception e) {
            System.out.println("El archivo de cambio ha sido alterado");
            System.exit(0);
            throw new RuntimeException(e);

        }


    }
    private static int getDesplazamiento(Scanner scanner) {
        int desplazamiento = 0;
        boolean entradaValida = false;

        while (!entradaValida) {
            System.out.println("Ingrese el número de desplazamientos:");
            if (scanner.hasNextInt()) {
                desplazamiento = scanner.nextInt();
                entradaValida = true;
            } else {
                System.out.println("El número de desplazamientos ingresado no es un número entero. Por favor, intente de nuevo.");
                scanner.next(); // Limpiar la entrada inválida
            }
        }
        scanner.nextLine(); // Consumir la nueva línea pendiente

        return desplazamiento;
    }
    private static void writeArchivo(String filePath, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
            System.out.println("El texto ha sido guardado en el archivo " + filePath);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    private static void encriptarArchivo(String inputFilePath, String outputFilePath, int desplazamiento) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(inputFilePath)));
            String encryptedContent = Cifrado.cifrado(content, desplazamiento);
            Files.write(Paths.get(outputFilePath), encryptedContent.getBytes());
            System.out.println("El archivo ha sido encriptado en " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error al encriptar el archivo: " + e.getMessage());
        }
    }

    private static void desencriptarArchivo(Scanner scanner, int deplazamiento1) {
        System.out.println("--------------------");
        System.out.println("    DESENCRIPTAR");
        System.out.println("--------------------");

        String pathDesencryptedFile = null;
        while (pathDesencryptedFile == null) {
            System.out.println("Por favor ingrese el nombre del archivo a desencriptar:");
            var desencriptar = scanner.nextLine();
            pathDesencryptedFile = BASE_PATH + desencriptar + "_encriptado" + EXTENSION;

            if (!Files.exists(Paths.get(pathDesencryptedFile))) {
                System.out.println("El nombre del archivo no existe. Por favor, intente de nuevo.");
                pathDesencryptedFile = null;
            }
        }

        String pathChangeFile = null;
        while (pathChangeFile == null) {
            System.out.println("Ingrese el nombre del archivo de cambio:");
            var cambioArchivo = scanner.nextLine();
            pathChangeFile = BASE_PATH + cambioArchivo + EXTENSION;

            if (!Files.exists(Paths.get(pathChangeFile))) {
                System.out.println("El archivo de cambio no existe. Por favor, intente de nuevo.");
                pathChangeFile = null;
            }
        }


        try {
            String content = new String(Files.readAllBytes(Paths.get(pathDesencryptedFile)));
            //int desplazamiento = -(Integer.parseInt(new String(Files.readAllBytes(Paths.get(pathChangeFile)))));
            String desplazamientoFile = new String(Files.readAllBytes(Paths.get(pathChangeFile))).split(":")[1].trim();
            int desplazamiento = -(Integer.parseInt(desplazamientoFile));

            if (!(-(deplazamiento1)==desplazamiento)){
                System.out.println("El archivo de cambio ha sido alterado");
                System.exit(0);
            }
            String decryptedContent = Cifrado.cifrado(content, desplazamiento); // Usar negativo para desencriptar
            String archivoDesEncriptado = pathDesencryptedFile.replace("_encriptado", "_desencriptado");
            Files.write(Paths.get(archivoDesEncriptado), decryptedContent.getBytes());
            System.out.println("El archivo ha sido desencriptado en " + archivoDesEncriptado);
        } catch (IOException e) {
            System.err.println("Error al desencriptar el archivo: " + e.getMessage());
        }

    }

}