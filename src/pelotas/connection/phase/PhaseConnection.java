/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pelotas.connection.phase;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.JApplet;
import pelotas.drawable.Drawable;

/**
 *
 * @author davidsantiagobarrera
 */
public class PhaseConnection implements PhaseLoader, Serializable {

    private URLConnection servletConnection;
    private URL servletUrl;
    private final URL host;
    private final String peticion;
    private String mySessionID = null;

    public PhaseConnection(JApplet ventanaprincipal) {
        host = ventanaprincipal.getCodeBase();
        peticion = "/PelotasEnterprise-war/PhaseServlet";
    }

    @Override
    public Drawable loadPhase(int P) {
            // Connection
        Drawable scene = null;
        try {
            // Direccion con el Uri del recurso a invocar:
            servletUrl = new URL("http://localhost:8080/PelotasEnterprise-war/PhaseServlet");
            //servletUrl = new URL(host, peticion);
            
            // Intentamos abrir la conexion
            servletConnection = servletUrl.openConnection();
            System.out.println("Cliente PhaseServlet: Servlet Conectado a URL");

            // Activamos la Entrada
            servletConnection.setDoInput(true);
            servletConnection.setDoOutput(true);
            servletConnection.setUseCaches(false);
            servletConnection.setDefaultUseCaches(false);
            servletConnection.setRequestProperty("Content-Type", "application/x-java-serialized-object");
            
            if(mySessionID != null){
                servletConnection.setRequestProperty("Cookie", "JSESSIONID=" + mySessionID);
            }

            // ----------------------------------
            // Creamos el Stream de Salida
            
            
            OutputStream outstream = servletConnection.getOutputStream();
            ObjectOutputStream bufferSalida = new ObjectOutputStream(outstream);

            // (W1)Escribimos mensaje para el server de quien lo va hacer 
            bufferSalida.writeObject(P);

            // (W2)Enviamos el mensaje
            bufferSalida.flush();
            // ----------------------------------
            // Creamos el Stream de Entrada
            InputStream instream = servletConnection.getInputStream();
            ObjectInputStream bufferentrada = new ObjectInputStream(instream);

            // (R1)Recogemos datos
            scene = (Drawable) bufferentrada.readObject();
            
            // (R2)Recogemos la id de la session:
            mySessionID = bufferentrada.readUTF();

            bufferSalida.close();
            bufferentrada.close();

        } catch (MalformedURLException e) {
            System.out.println("Catch formato url: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error IO en Applet(Recibiendo): "
                    + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error en Applet Clase no Encontrada: "
                    + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error Desconocido (Recibiendo): "
                    + e.getMessage());
        }
        return scene;
    }
}
