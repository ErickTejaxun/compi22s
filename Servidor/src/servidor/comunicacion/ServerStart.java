package servidor.comunicacion;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import servidor.gui.Consola;
import servidorfisql.server.Server;

/**
 *
 * @author erick
 */
public class ServerStart extends Thread{
    
    

    @Override
    public void run() 
    { 

        try 
        {
            ServerSocket serverSock = new ServerSocket(2222);
            Consola.writeln("Servidor escuchando en el puerto 2222...");

            while (true) 
            {
                            Socket clientSock = serverSock.accept();
                            PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
                            OutputStreamWriter outw = new OutputStreamWriter(clientSock.getOutputStream(), "UTF8");
                            InputStreamReader inw = new InputStreamReader(clientSock.getInputStream(), "UTF8");
                            
                            Server.clientes.agregarCliente(writer);

                            Thread listener = new Thread(new ClientHandler(clientSock, writer, outw, inw));
                            listener.start();
                            Consola.writeln("Conexion establecida con el cliente [" + writer.toString() + "]...");
            }
        }
        catch (Exception ex)
        {
            Consola.writeln("Error estableciendo una conexion!!!");
        }
    }
}
