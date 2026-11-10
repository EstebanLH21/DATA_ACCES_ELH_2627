import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            // 3. Parsear el archivo XML con nuestro manejador.
            //    SAX leerá el XML y llamará a los métodos del
            //    manejador cada vez que encuentre algo.
            parser.parse("contacts.xml", new ContactosHandler());
        } catch (Exception e) {

            e.printStackTrace();
        }

    }
}