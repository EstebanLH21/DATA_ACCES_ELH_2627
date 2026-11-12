import java.io.*;

public class ReadBMP {
    public static void main(String[] args) {
        try (FileInputStream fIn = new FileInputStream("image.bmp")) {
            byte[] header = new byte[54];
            int bytesRead = fIn.read(header);

            if (bytesRead < 54) {
                System.out.println("File too small.");
                return;
            }


            int[] result = parseHeader(header);

            System.out.println("Size: " + result[0]);
            System.out.println("Width: " + result[1]);
            System.out.println("Height: " + result[2]);
            System.out.println("Bits: " + result[3]);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //Metodo para el test
    public static int[] parseHeader(byte[] header) {
        if ((header[0] & 0xFF) != 0x42 || (header[1] & 0xFF) != 0x4D) {
            throw new IllegalArgumentException("Not a BMP file.");
        }

        int size = (header[2] & 0xFF) | ((header[3] & 0xFF) << 8) | ((header[4] & 0xFF) << 16) | ((header[5] & 0xFF) << 24);
        int width = (header[18] & 0xFF) | ((header[19] & 0xFF) << 8) | ((header[20] & 0xFF) << 16) | ((header[21] & 0xFF) << 24);
        int height = (header[22] & 0xFF) | ((header[23] & 0xFF) << 8) | ((header[24] & 0xFF) << 16) | ((header[25] & 0xFF) << 24);
        int bits = (header[28] & 0xFF) | ((header[29] & 0xFF) << 8);

        return new int[]{size, width, height, bits};
    }
}