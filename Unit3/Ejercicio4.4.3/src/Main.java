import java.io.FileInputStream;
import java.io.IOException;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String ruta = "D:\\imagen.bmp";
        try(FileInputStream fIn = new FileInputStream(ruta)){
            byte[] cabecera = new byte[54];
            int bytesRead = fIn.read(cabecera);
            if (bytesRead < 54) {
                System.out.println("Archivo demasiado pequeño para ser BMP.");
                return;
            }
            if ((cabecera[0] & 0xFF) != 0x42 || (cabecera[1] & 0xFF) != 0x4D) {
                System.out.println("No es un archivo BMP válido.");
                return;
            }
            // Crear el ByteBuffer en little-endian
            ByteBuffer bb = ByteBuffer.wrap(cabecera);
            bb.order(ByteOrder.LITTLE_ENDIAN);
            // Leer los campos
            int tamanoArchivo = bb.getInt(2);              // offset 2  → 4 bytes
            int ancho         = bb.getInt(18);             // offset 18 → 4 bytes
            int alto          = bb.getInt(22);             // offset 22 → 4 bytes
            int bitsPorPixel  = bb.getShort(28) & 0xFFFF;  // offset 28 → 2 bytes
            // Mostrar resultados
            System.out.println("Formato:        BMP");
            System.out.println("Tamaño:         " + tamanoArchivo + " bytes");
            System.out.println("Ancho:          " + ancho + " píxeles");
            System.out.println("Alto:           " + alto + " píxeles");
            System.out.println("Bits por píxel: " + bitsPorPixel);
        }catch(IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}