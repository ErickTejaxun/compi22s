package servidor.comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidor.gui.Consola;
import servidor.analizadores.InterpretePlyCS;
import servidorfisql.server.Server;

/**
 *
 * @author erick
 */
public class ClientHandler extends Thread{
    
    BufferedReader reader;
    Socket sock;
    PrintWriter pwClient;
    OutputStreamWriter outw;
    InputStreamReader inw;
    
    InterpretePlyCS interpretePlyCS;
    

    public ClientHandler(Socket clientSocket, PrintWriter user, OutputStreamWriter outw , InputStreamReader inw) 
    {
        interpretePlyCS = new InterpretePlyCS();
        pwClient = user;
        this.outw = outw;
        this.inw = inw;
        try 
        {
            sock = clientSocket;
            InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(isReader);
            
            Consola.writeln("Creando instancia de socket para el cliente...");
        }
        catch (Exception ex) 
        {
            Consola.writeln("Error inesperado en la comunicacion...");
        }

    }

    @Override
    public void run() 
    {
        String request ="", response = "";
        //Consola.writeln("Mensaje \n" + this.reader.readLine());
        Consola.writeln("Creando instancia de socket para el cliente...");
                    
        
        String recibido = "", enviado= "";        
        try{
        char[] cbuf = new char[1024];
        
        while (true) 
        {
            Consola.writeln("Esperando mensaje del cliente en python");
            System.out.println("Esperando mensaje del cliente en python");
            for(int cont = 0 ; cont<1024;cont++){ cbuf[cont] = 32;}
            inw.read(cbuf);            
            for (char c : cbuf) 
            {
                recibido += c;
                if (c == 00) {
                    break;
                }
            }
                
                
            Consola.writeln("Cliente dice: " + recibido);
            System.out.println("Cliente dice: " + recibido);                                    
            request = recibido;
            recibido.trim();
            Consola.writeln("Esperando mensaje del cliente ....");
            /*ANALIZAR SOLICITUD DE LENGUAJE DE COMUNICACION PLYCS*/
            Consola.writeln("request original: \n" + request);
            request = request.replace("!@#", "\n");
            Consola.writeln("request transformada: \n" + request);
            System.out.println(request + "-------------------");

            response = interpretePlyCS.analizar(pwClient, request);
            enviado = response;
            if(response != null){

                
                response = response.replace("\n", "!@#").replace("\r", "$%^").replace("\t", "&*(");
                System.out.println("Respondiendo...: \n" + response.replace("!@#", "\n"));
                
                if(!response.equals("LOGOUT")){
                if(!response.equals("LOGOUT")){  
                    responderCliente(pwClient, response);
                }else{
                    Server.actualDB = "";
                }
            }else{
                Consola.writeln("interpretePlyCS retorno null a request " + request);
            } 
                
            System.out.println("Enviar a cliente: >>>" + enviado);
            recibido = "S:" + recibido;
            outw.write(enviado.toCharArray());
            outw.flush();
            recibido = "";
            cbuf = new char[1024];
        }   
        }
        }
        catch( Exception ex )
        {
            System.out.println( ex.getMessage() );
            Consola.writeln("Conexion perdida..." + ex.getLocalizedMessage());
            ex.printStackTrace();
            Server.clientes.removeClient(pwClient);
        }
        
        
        /*try{
            while ((request = this.reader.readLine()) != null) {
                Consola.writeln("Esperando mensaje del cliente ....");
                //ANALIZAR SOLICITUD DE LENGUAJE DE COMUNICACION PLYCS
                Consola.writeln("request original: \n" + request);
                request = request.replace("!@#", "\n");
                Consola.writeln("request transformada: \n" + request);
                
                response = interpretePlyCS.analizar(pwClient, request);
                
                if(response != null){
                    
                    response = response.replace("\n", "!@#").replace("\r", "$%^").replace("\t", "&*(");
                    System.out.println("Respondiendo...: \n" + response.replace("!@#", "\n"));
                    
                    //if(!response.equals("LOGOUT")){
                    if(!response.equals("LOGOUT")){  
                        responderCliente(pwClient, response);
                    }else{
                        Server.actualDB = "";
                    }
                }else{
                    Consola.writeln("interpretePlyCS retorno null a request " + request);
                }
            
            }

            
          } 
          catch (Exception ex) 
          {
             Consola.writeln("Conexion perdida..." + ex.getLocalizedMessage());
             ex.printStackTrace();
             Server.clientes.removeClient(pwClient);
          } */
     }
    
    private void responderCliente(PrintWriter cliente, String respuesta){
        
        if(Server.clientes.existsClient(cliente)){
            String user = Server.clientes.getUsername(cliente);
            
            try{
                
                cliente.println(respuesta);
                Consola.writeln("Respondiendo a [" + user + "] =>> " + respuesta + "\n");
                cliente.flush();
                
            }catch(Exception ex){
                Consola.writeln("Error respondiendo a [" + user + "]");
            }
        }else{
            Consola.writeln("Error, el cliente no se encuentra logeado en el sistema...");
        }
    }
}
