/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor.comunicacion;

import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import servidor.analizadores.InterpretePlyCS;
import servidor.gui.Consola;
import servidorfisql.server.Server;

public class Servidor {
    static final int PUERTO=5000;
  public Servidor( ) {
    try {
 System.out.println("Inicializando SERVIDOR");
ServerSocket socketServidor = new ServerSocket(PUERTO);

System.out.println("Escucho el puerto " + PUERTO );
System.out.println("Esperando conexiones de clientes ..." );


while(true){
    
    Socket socketCliente = socketServidor.accept();   
    System.out.println("Sirvo al cliente   en el puerto de comunicaciones "+socketCliente.getPort());
    PrintWriter writer = new PrintWriter(socketCliente.getOutputStream());
    BufferedReader reader;
    Socket sock = socketCliente;
    PrintWriter pwClient = null;    
    InterpretePlyCS interpretePlyCS;
    interpretePlyCS = new InterpretePlyCS();
    InputStreamReader isReader = new InputStreamReader(socketCliente.getInputStream());
    reader = new BufferedReader(isReader);
    OutputStream mensajeParaCliente = socketCliente.getOutputStream();
    DataOutputStream flujoSecuencial= new DataOutputStream( mensajeParaCliente );
    
    Server.clientes.agregarCliente(writer);
    String request, response;
    try{
    while ((request = reader.readLine()) != null) {
        Consola.writeln("Esperando mensaje del cliente ....");
        /*ANALIZAR SOLICITUD DE LENGUAJE DE COMUNICACION PLYCS*/
        Consola.writeln("request original: \n" + request);
        request = request.replace("!@#", "\n");
        Consola.writeln("request transformada: \n" + request);

        response = interpretePlyCS.analizar(pwClient, request);
        

        if(response != null){

            response = response.replace("\n", "!@#").replace("\r", "$%^").replace("\t", "&*(");
            flujoSecuencial.writeUTF(response.replace("!@#", "\n"));
            System.out.println("Respondiendo...: \n" + response.replace("!@#", "\n"));

            //if(!response.equals("LOGOUT")){
            if(!response.equals("LOGOUT")){  
                //responderCliente(pwClient, response);
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
     //Server.clientes.removeClient(pwClient);
    } 
    
    
    
    
    
    
    socketCliente.close();

  
}
     } catch( Exception e ) {
System.out.println( e.getMessage() );
    }
  }
  
}