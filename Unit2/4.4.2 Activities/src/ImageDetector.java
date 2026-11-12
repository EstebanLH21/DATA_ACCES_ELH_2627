import java.io.*;

public class ImageDetector {
    public static void main(String[] args) {
        try (FileInputStream fIn = new FileInputStream("image.png")) {
            byte[] header = new byte[6];
            int bytesRead = fIn.read(header);
            String format = "Unknown";

            // Check BMP: 42 4D
            if (bytesRead >= 2 &&
                    (header[0] & 0xFF) == 0x42 &&
                    (header[1] & 0xFF) == 0x4D) {
                format = "BMP";
            }

            // Check JPEG: FF D8 FF
            else if (bytesRead >= 3 &&
                    (header[0] & 0xFF) == 0xFF &&
                    (header[1] & 0xFF) == 0xD8 &&
                    (header[2] & 0xFF) == 0xFF) {
                format = "JPEG";
            }

            // Check PNG: 89 50 4E 47
            else if (bytesRead >= 4 &&
                    (header[0] & 0xFF) == 0x89 &&
                    (header[1] & 0xFF) == 0x50 &&
                    (header[2] & 0xFF) == 0x4E &&
                    (header[3] & 0xFF) == 0x47) {
                format = "PNG";
            }

            // Check ICO: 00 00 01 00
            else if (bytesRead >= 4 &&
                    header[0] == 0x00 &&
                    header[1] == 0x00 &&
                    header[2] == 0x01 &&
                    header[3] == 0x00) {
                format = "ICO";
            }

            // Check GIF: 47 49 46 38 39 61 (GIF89a) or 47 49 46 38 37 61 (GIF87a)
            else if (bytesRead >= 6 &&
                    (header[0] & 0xFF) == 0x47 &&
                    (header[1] & 0xFF) == 0x49 &&
                    (header[2] & 0xFF) == 0x46 &&
                    (header[3] & 0xFF) == 0x38 &&
                    ((header[4] & 0xFF) == 0x39 || (header[4] & 0xFF) == 0x37) &&
                    (header[5] & 0xFF) == 0x61) {
                format = "GIF";
            }

            System.out.println("Format: " + format);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}