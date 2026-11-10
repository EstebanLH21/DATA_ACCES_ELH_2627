
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ContactosHandler extends DefaultHandler {
    // VARIABLES DE INSTANCIA
    // Son de instancia (no locales) porque deben persistir entre
    // eventos: un evento guarda el dato, otro lo usa después.
    // ---------------------------------------------------------------

    // Nombre de la etiqueta que se está procesando actualmente.
    // Se actualiza en startElement y se usa en characters para
    // saber dónde guardar el contenido.
    private String etiquetaActual = "";
    // Indica en qué sección estamos: "emails", "phones" o "".
    // Necesario porque la etiqueta <work> aparece tanto en <emails>
    // (como email) como en <phones> (como teléfono), y hay que
    // distinguirlas.
    private String seccion = "";
    // Datos del contacto actual.
    // Se van rellenando en characters y se usan en endElement("contact").
    private String nombre = "";
    private String apellido = "";
    private String cell = "";   // teléfono móvil
    private String work = "";   // teléfono del trabajo (solo si estamos en <phones>)
    private String home = "";   // teléfono de casa
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // Guardamos qué etiqueta se ha abierto.
        // Así en characters sabremos dónde guardar el contenido.
        etiquetaActual = qName;
        // Si empieza un contacto nuevo, limpiamos TODAS las variables.
        // ¿Por qué? Porque el manejador es el MISMO objeto para todo
        // el XML. Si no limpiamos, el contacto 2 arrastraría los datos
        // del contacto 1 (especialmente los campos que el contacto 2
        // no tenga, como un teléfono).
        if (qName.equals("contact")) {
            nombre = "";
            apellido = "";
            cell = "";
            work = "";
            home = "";
            seccion = "";
        }
        // Detectamos en qué sección estamos.
        // Esto nos permite distinguir <work> dentro de <emails>
        // (es un email) de <work> dentro de <phones> (es un teléfono).
        else if (qName.equals("emails")) {
            seccion = "emails";
        }
        else if (qName.equals("phones")) {
            seccion = "phones";
        }
    }
    // characters: se llama al encontrar CONTENIDO entre etiquetas.
    // Parámetros:
    //   ch     → array de caracteres con el texto
    //   start  → desde qué posición del array leer
    //   length → cuántos caracteres leer
    //
    // IMPORTANTE: puede llamarse VARIAS VECES para una misma etiqueta
    // si el texto es muy largo. En este ejemplo asumimos que se llama
    // una vez por etiqueta (suficiente para este ejercicio).
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String contenido = new String(ch, start, length).trim();
        if (contenido.isEmpty()) {
            return;
        }
        // Según qué etiqueta estemos procesando, guardamos el
        // contenido en la variable correspondiente.
        if(etiquetaActual.equals("name")) {
            nombre = contenido;
        }else if(etiquetaActual.equals("apellido")) {
            apellido = contenido;
        }
        // Para los teléfonos, comprobamos que estemos dentro de
        // <phones>. Si <work> aparece dentro de <emails>, NO lo
        // guardamos como teléfono (es un email).
        else if(etiquetaActual.equals("cell") && seccion.equals("phones")) {
            cell = contenido;

        }
        else if (etiquetaActual.equals("work") && seccion.equals("phones")) {
            work = contenido;
        }
        else if (etiquetaActual.equals("home") && seccion.equals("phones")) {
            home = contenido;
        }

    }
    // endElement: se llama al CERRAR una etiqueta </algo>
    //
    // Es el momento seguro para hacer cosas "de cierre":
    //   - Al cerrar </contact>, ya tenemos todos los datos del
    //     contacto → elegimos teléfono y mostramos.
    //   - Al cerrar </phones> o </emails>, ya no estamos en esa
    //     sección → limpiamos la variable seccion.
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        // ---- CIERRE DE CONTACTO ----
        // Al cerrar </contact>, ya hemos leído todos sus datos.
        // Es el momento de elegir el teléfono y mostrarlo.
        if (qName.equals("contact")) {

            // Elegimos el teléfono en el orden que pide el enunciado:
            // primero cell, si no hay, work, si no hay, home.
            String telefono = "";

            if (!cell.isEmpty()) {
                telefono = cell;
            } else if (!work.isEmpty()) {
                telefono = work;
            } else if (!home.isEmpty()) {
                telefono = home;
            }

            // Solo mostramos el contacto si tiene algún teléfono.
            // El enunciado dice "with a phone number".
            if (!telefono.isEmpty()) {
                System.out.println(nombre + " " + apellido + ": " + telefono);
            }
            // Si no tiene teléfono, no imprimimos nada.
            // ---- CIERRE DE SECCIÓN ----
            // Al cerrar </emails> o </phones>, ya no estamos en esa
            // sección. Limpiamos la variable para que no se arrastre
            // al siguiente elemento.
            if (qName.equals("emails") || qName.equals("phones")) {
                seccion = "";
            }

        }
    }

}
