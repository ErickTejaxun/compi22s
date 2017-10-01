package servidorfisql.server;

import servidol.server.manejador.Archivos;
import servidor.comunicacion.Clientes;
import servidor.comunicacion.ServerStart;
import servidor.comunicacion.Servidor;
import servidor.gui.Consola;

/**
 *
 * @author erick
 */
public class Server {
    
    public static String user = "ddbbserver";
    public static Clientes clientes;
    public static String actualDB = "";
    
    public static void iniciarServidor(){
        
        Consola.writeln("Iniciando servidor...");
        
        clientes = new Clientes();
        
        Thread starter = new Thread(new ServerStart());
        starter.start();
        //Servidor miServidor=new Servidor();
        
        /*Iniciar sistema de archivos*/
        Archivos.inicializarSistemaDeArchivos();
        
        /*Cargar usuarios*/
        Archivos.cargarUsuarios();
        
        /*Cargar BBDD*/
        Archivos.cargarInformacion();
        
        Consola.writeln("Servidor iniciado...\n");
    }
    
    public static void detenerServidor(){
        try 
        {
            Consola.writeln("Deteniendo el servidor...");
            
            Archivos.guardarInformacion();
            Thread.sleep(1500);
            
            Consola.writeln("Servidor detenido...\n");
        } 
        catch(InterruptedException ex) 
        {
            Thread.currentThread().interrupt();
            Consola.writeln("Error deteniendo el servidor!!!");
        }
    }
    
    public static void reiniciarServidor(){
        detenerServidor();
        iniciarServidor();
    }
    
    
    
    
    
}
