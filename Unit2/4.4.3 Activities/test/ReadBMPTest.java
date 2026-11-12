import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReadBMPTest {

    @Test
    public void testParseHeader() {
        byte[] header = new byte[54];

        // Firma BMP
        header[0] = 0x42;
        header[1] = 0x4D;

        // Size = 4150
        header[2] = 0x36;
        header[3] = 0x10;
        header[4] = 0x00;
        header[5] = 0x00;

        // Width = 320
        header[18] = 0x40;
        header[19] = 0x01;
        header[20] = 0x00;
        header[21] = 0x00;

        // Height = 240
        header[22] = (byte) 0xF0;
        header[23] = 0x00;
        header[24] = 0x00;
        header[25] = 0x00;

        // Bits = 24
        header[28] = 0x18;
        header[29] = 0x00;

        // Llamamos al método de ReadBMP
        int[] result = ReadBMP.parseHeader(header);

        // Comprobamos
        assertEquals(4150, result[0]);
        assertEquals(320, result[1]);
        assertEquals(240, result[2]);
        assertEquals(24, result[3]);
    }
}
