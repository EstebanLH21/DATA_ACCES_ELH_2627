import java.io.FileInputStream;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String ruta = "D:\\imagen.png";
        try(FileInputStream fIn = new FileInputStream(ruta)){
            byte[] cabecera = new byte[6];
            int bytesRead = fIn.read(cabecera);
            if (bytesRead < 2) {
                System.out.println("Archivo demasiado pequeño.");
                return;
            }
            String formato = detectarFormato(cabecera, bytesRead);
            System.out.println("Formato detectado: " + formato);

        }catch(IOException e){
            System.out.println("Error: " + e.getMessage());
        }

    }
    private static String detectarFormato(byte[] c, int n){
        // BMP: 42 4D
        if (n >= 2 && (c[0] & 0xFF) == 0x42 && (c[1] & 0xFF) == 0x4D) {
            return "BMP";
        }
        // GIF: 47 49 46 38 (39 o 37) 61
        if (n >= 6 && (c[0] & 0xFF) == 0x47 && (c[1] & 0xFF) == 0x49
                && (c[2] & 0xFF) == 0x46 && (c[3] & 0xFF) == 0x38
                && ((c[4] & 0xFF) == 0x39 || (c[4] & 0xFF) == 0x37)
                && (c[5] & 0xFF) == 0x61) {
            return "GIF";
        }
        // ICO: 00 00 01 00
        if (n >= 4 && c[0] == 0x00 && c[1] == 0x00
                && c[2] == 0x01 && c[3] == 0x00) {
            return "ICO";
        }
        // JPEG: FF D8 FF
        if (n >= 3 && (c[0] & 0xFF) == 0xFF && (c[1] & 0xFF) == 0xD8
                && (c[2] & 0xFF) == 0xFF) {
            return "JPEG";
        }
        // PNG: 89 50 4E 47
        if (n >= 4 && (c[0] & 0xFF) == 0x89 && (c[1] & 0xFF) == 0x50
                && (c[2] & 0xFF) == 0x4E && (c[3] & 0xFF) == 0x47) {
            return "PNG";
        }

        return "Desconocido";
    }
}