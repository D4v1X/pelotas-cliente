/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pelotas.connection.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.JApplet;

/**
 *
 * @author davidsantiagobarrera
 */
public class MessageConnection implements MessageSaver {

    private URLConnection servletConnection;
    private URL servletUrl;
    private final URL host;
    private final String peticion;

    public MessageConnection(JApplet ventanaprincipal) {
        host = ventanaprincipal.getCodeBase();
        peticion = "/PelotasEnterprise-war/MessagesServlet";
    }

    @Override
    public String saveMessage(String msg) {
        String sms = "Wep";
        try {
            // Direccion con el Uri del recurso a invocar:
            servletUrl = new URL("http://localhost:8080/PelotasEnterprise-war/MessagesServlet");
            //servletUrl = new URL(host, peticion);
            // Intentamos abrir la conexion
            servletConnection = servletUrl.openConnection();
            System.out.println("Cliente SendScene: Servlet Conectado a URL");

            // Activamos la Salida
            servletConnection.setDoInput(true);
            servletConnection.setDoOutput(true);
            servletConnection.setUseCaches(false);
            servletConnection.setDefaultUseCaches(false);
            servletConnection.setRequestProperty("Content-Type", "application/x-java-serialized-object");

            // Creamos el Stream de Salida
            OutputStream outstream = servletConnection.getOutputStream();
            ObjectOutputStream bufferSalida = new ObjectOutputStream(outstream);

            // (W2)Escribimos dato y los enviamos
            bufferSalida.writeUTF(msg);
            bufferSalida.flush();

            // --------------------
            // Creamos el Stream de Lectura:
            InputStream instream = servletConnection.getInputStream();
            ObjectInputStream bufferentrada = new ObjectInputStream(instream);

            // Creamos la Referencia que recogera los datos:
            // (R1)leemos la respuesta y leemos el texto
            sms = bufferentrada.readUTF();
            System.out.println(sms);

            bufferentrada.close();
            // --------------------
            bufferSalida.close();

        } catch (MalformedURLException e) {
            System.out.println("Catch formato url" + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error IO en Applet (Enviando): "
                    + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error Desconocido en Applet(Enviando):"
                    + e.getMessage());
        }
        return sms;
    }
}
