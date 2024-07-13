import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LeerArchivos {
    public static String leerArchivos(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }
    public static int leerDesplazamiento(String desplazamiento) throws IOException {
        return Integer.parseInt(new String(Files.readAllBytes(Paths.get(String.valueOf(desplazamiento)))));
    }

}
