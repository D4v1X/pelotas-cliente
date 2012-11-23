package pelotas;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.DefaultListModel;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import pelotas.controller.SceneController;

/**
 *
 * @author David
 */
@SuppressWarnings("serial")
public class Principal extends JApplet {

    /**
     * Initialization method that will be called after the applet is loaded into
     * the browser.
     */
    private JPanel fila1, fila2, fila3, fila4, matriz;
    private JButton botonGuardar, botonCargar, botonGuardarBD, botonCargarBD;
    private JTextField mensaje, operacion, score , sms;
    private JLabel labelmensaje, labeloperacion, labelscore;
    private SceneController scenecontroller;
    private JButton botonUpdateScore, botonNextPhase, botonFinish, botonSms;

    @Override
    public void init() {
        setSize(600, 650);
        System.out.println("Ejecutando init()");
        // Interfaz
        matriz = new JPanel();
        fila1 = new JPanel();
        fila2 = new JPanel();
        fila3 = new JPanel();
        fila4 = new JPanel();
        matriz.setLayout(new GridLayout(4, 1));
        botonGuardar = new JButton("GuardarFL");
        mensaje = new JTextField(10);
        mensaje.setText("Player1");
        labelmensaje = new JLabel("Usuario:");
        labelmensaje.setLabelFor(mensaje);
        score = new JTextField(6);
        labelscore = new JLabel("Score:");
        labelscore.setLabelFor(score);
        botonCargar = new JButton("CargarFL");
        botonGuardarBD = new JButton("GuardarBD");
        operacion = new JTextField(20);
        labeloperacion = new JLabel("Operacion:");
        labeloperacion.setLabelFor(operacion);
        botonCargarBD = new JButton("CargarBD");
        botonUpdateScore = new JButton("Actualizar Score");
        botonNextPhase = new JButton("Siguiente Fase");
        botonFinish = new JButton("Terminar");
        //-----
        sms = new JTextField(35);
        botonSms = new JButton("Enviar Sms");
        //-----
        fila1.setLayout(new FlowLayout());
        fila1.add(botonGuardar);
        fila1.add(labelmensaje);
        fila1.add(mensaje);
        fila1.add(labelscore);
        fila1.add(score);
        fila1.add(botonCargar);
        fila2.setLayout(new FlowLayout());
        fila2.add(botonGuardarBD);
        fila2.add(labeloperacion);
        fila2.add(operacion);
        fila2.add(botonCargarBD);
        fila3.setLayout(new FlowLayout());
        fila3.add(botonUpdateScore);
        fila3.add(botonNextPhase);
        fila3.add(botonFinish);
        fila4.add(sms);
        fila4.add(botonSms);
        matriz.add(fila1);
        matriz.add(fila2);
        matriz.add(fila3);
        matriz.add(fila4);
        this.setLayout(new BorderLayout());
        add("South", matriz);
        // --------------
        Graphics g = getGraphics();// Cogemos entorno grafico
        scenecontroller = new SceneController(g, mensaje, operacion, score, sms,this);
        // --------------
        botonGuardar.addActionListener(scenecontroller);
        botonCargar.addActionListener(scenecontroller);
        botonGuardarBD.addActionListener(scenecontroller);
        botonCargarBD.addActionListener(scenecontroller);
        botonUpdateScore.addActionListener(scenecontroller);
        botonNextPhase.addActionListener(scenecontroller);
        botonFinish.addActionListener(scenecontroller);
        botonSms.addActionListener(scenecontroller);
    }

    // TODO overwrite start(), stop() and destroy() methods
    @Override
    public void start() {
        System.out.println("Ejecutando start()");
    }

    @Override
    public void stop() {
        System.out.println("Ejecutando stop()");
    }

    @Override
    public void destroy() {
        System.out.println("Ejecutando init()");
    }
}

/*
 * Separar 3 entidades: comun, applet , server, a�adir los proyectos como librerias
 * en el index.jsp a�adir el applet code y archive
 * crear dos paneles uno de dibujo y otro botones
 * dentro den render repaint del panel 
 * 
 * cliente
 * servidor
 * control de nivel : motor de juego conexion con el servidor
 * entondeades de motor de juego
 * interfaces para todo
 * 
 * Capas de arquitectura
 * sistema :
 * middleware : JAva---> serializable
 * generia de aplicacion : entidades
 * especifica de aplicacion : motor de juego , control de niveles
 */
