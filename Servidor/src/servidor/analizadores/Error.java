package servidor.analizadores;

/**
 *
 * @author erick
 */
public class Error {
    
    
    public static String logico(int codigo, String clase, String descripcion){
        String error;
        
        error = "[\n" +
                "	\"paquete\": \"error\",\n" +
                "	\"validar\": " + codigo + ",\n" +
                "	\"tipo\": \"" + clase + "\",\n" +
                "	\"login\": " + descripcion + "\n" +
                "	\n" +
                "]\n";
        
        error = "[\n" +
                "\n" +
                "\"paquete\": \"login\",\n" +
                "\n" +
                "\"validar\": 1500,\n" +
                "\n" +
                "\"datos\": [\n" +
                "\n" +
                "\n" +
                "\"user\": \"admin\",\n" +
                "\n" +
                "\n" +
                "\"login\": false\n" +
                "[\n" +
                "\"usuarios\": [\n" +
                "\"nombre\": \"admin\",]\n" +
                "]\n" +
                "\n" +
                "]\n" +
                "]";
        
        return error;
    }
    
    
    public static String lenguaje(int codigo, String lenguaje, String cadena, String clase, int row, int col, String descripcion){
        String error;
        
        error = "[\n" +
                "	\"paquete\": \"error\",\n" +
                "	\"validar\": " + codigo + ",\n" +
                "	\"tipo\": \"lenguaje\",\n" +
                "	\"detalles\": [\n" +
                "		\"Lenguaje\": \"" + lenguaje + "\",\n" +
                "		\"Instruccion\": \"" + cadena + "\",\n" +
                "		\"Tipo\": \"" + clase + "\",\n" +
                "		\"Fila\": " + row + ",\n" +
                "		\"Columna\": " + col + ",\n" +
                "		\"Descripcion\": \"" + descripcion + "\"\n" +
                "	]\n" +
                "]\n";
        
        return error;
    }
    
}
