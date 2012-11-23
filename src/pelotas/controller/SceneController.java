/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pelotas.controller;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextField;
import pelotas.connection.ConnectionController;
import pelotas.connection.ConnectionManager;
import pelotas.connection.message.MessageConnection;
import pelotas.connection.message.MessageSaver;
import pelotas.connection.phase.PhaseConnection;
import pelotas.connection.phase.PhaseLoader;
import pelotas.connection.score.ScoreConnection;
import pelotas.connection.score.ScoreSaver;
import pelotas.drawable.Ball;
import pelotas.drawable.Boundary;
import pelotas.drawable.Brick;
import pelotas.drawable.Composite;
import pelotas.drawable.Drawable;
import pelotas.ranking.Ranking;
import pelotas.renderer.Renderer;
import pelotas.score.Score;

/**
 *
 * @author David
 */
public class SceneController implements ActionListener {

    private ConnectionManager conexion;
    private PhaseLoader conexionPhase;
    private ScoreSaver conexionScore;
    private MessageSaver conexionMessage;
    private Composite escena;
    private Composite escenaCargada;
    private Renderer renderer;
    private AnimationController animationcontroller;
    private JTextField mensajeU, mensajeO, mensajeS, mensajeM;
    private String usuario;
    private Ranking ranking;
    private Score score;

    /**
     * @param g
     * @param mensaje
     * @param operacion
     * @param ventanaprincipal
     */
    public SceneController(Graphics g, JTextField mensajeU, JTextField mensajeO, JTextField mensajeS, JTextField mensajeM, JApplet ventanaprincipal) {
        super();
        score = new Score();
        escena = new Composite();
        renderer = new Renderer(escena, g);
        animationcontroller = new AnimationController(escena, score);
        Boundary contorno = new Boundary(0, 0, ventanaprincipal.getWidth(), ventanaprincipal.getHeight() - 170);
        escena.add(contorno);
        renderer.start();
        animationcontroller.start();
        ventanaprincipal.addMouseListener(animationcontroller);//Control de Mouse
        conexionPhase = new PhaseConnection(ventanaprincipal);
        conexionScore = new ScoreConnection(ventanaprincipal);
        conexionMessage = new MessageConnection(ventanaprincipal);
        conexion = new ConnectionController(ventanaprincipal);
        this.mensajeU = mensajeU;
        this.mensajeO = mensajeO;
        this.mensajeS = mensajeS;
        this.mensajeM = mensajeM;
    }
    //retorna un drawable

    public Drawable loadScene(String name, String operacion) {
        return conexion.receiveScene(name, operacion);
    }

    public String saveScene(Drawable scene, String name, String operacion) {
        return conexion.sendScene(scene, name, operacion);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        
        if(e.getActionCommand().equals("Enviar Sms")){
            mensajeM.setText(conexionMessage.saveMessage(mensajeM.getText()));
        }

        if (e.getActionCommand().equals("Terminar")) {
            JFrame marco = new JFrame("Ranking");
            marco.setSize(200, 600);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int height = screenSize.height;
            int width = screenSize.width;
            marco.setLocation((width / 2)-100, 0);
            JList nombre = new JList();
            JList puntos = new JList();
            DefaultListModel modeloN = new DefaultListModel();
            DefaultListModel modeloP = new DefaultListModel();
            nombre.setModel(modeloN);
            puntos.setModel(modeloP);
            //----------------------
            Ranking rankingCargado;
            score.setScoreId(mensajeU.getText());
            conexionPhase.loadPhase(0);
            rankingCargado = conexionScore.saveScore(score);
            List<Score> scorescargados = rankingCargado.getScores();
            modeloN.clear();
            modeloP.clear();

            for (Score sco : scorescargados) {
                modeloN.addElement(sco.getScoreId());
                modeloP.addElement(sco.getScore());
            }
            //----------------------
            modeloN.addElement("Fin de Scores");
            marco.setLayout(new GridLayout(1, 2));
            marco.add(nombre);
            marco.add(puntos);
            marco.setVisible(true);
        }

        if (e.getActionCommand().equals("Siguiente Fase")) {
            List<Drawable> childDrawableCargado = Collections.synchronizedList(new ArrayList<Drawable>());
            List<Ball> ballDrawableCargada = Collections.synchronizedList(new ArrayList<Ball>());

            usuario = mensajeU.getText();
            escenaCargada = (Composite) conexionPhase.loadPhase(1);

            for (Drawable draw : escenaCargada.getChildDrawable()) {
                if (draw instanceof Brick) {
                    Brick brick = (Brick) draw;
                    brick.setScore(score);
                }
                if (draw instanceof Ball) {
                    ballDrawableCargada.add((Ball) draw);
                }
                childDrawableCargado.add(draw);
            }
            escena.setChildDrawable(childDrawableCargado);
            animationcontroller.setBallDrawable(ballDrawableCargada);
            mensajeO.setText("next de BD");
        }

        if (e.getActionCommand().equals("Actualizar Score")) {
            mensajeS.setText(score.getScore().toString());
        }
        //Guardamos la escema en un Fichero
        if (e.getActionCommand().equals("GuardarFL")) {
            usuario = mensajeU.getText();
            mensajeO.setText(this.saveScene(animationcontroller.getEscena(), usuario, "GUARDARFL"));
        }

        //Cargamos la Escena de un Fichero
        if (e.getActionCommand().equals("CargarFL")) {
            List<Drawable> childDrawableCargado = Collections.synchronizedList(new ArrayList<Drawable>());
            List<Ball> ballDrawableCargada = Collections.synchronizedList(new ArrayList<Ball>());

            usuario = mensajeU.getText();
            escenaCargada = (Composite) loadScene(usuario, "CARGARFL");

            for (Drawable draw : escenaCargada.getChildDrawable()) {
                if (draw instanceof Brick) {
                    ((Brick) draw).setScore(score);
                }
                if (draw instanceof Ball) {
                    ballDrawableCargada.add((Ball) draw);
                }
                childDrawableCargado.add(draw);

            }
            escena.setChildDrawable(childDrawableCargado);
            animationcontroller.setBallDrawable(ballDrawableCargada);
            mensajeO.setText("Cargado de Fichero");
        }

        //Guardamos la escema de una BD
        if (e.getActionCommand().equals("GuardarBD")) {
            usuario = mensajeU.getText();
            mensajeO.setText(this.saveScene(animationcontroller.getEscena(), usuario, "GUARDARBD"));
        }
        //Cargamos la Escena de una BD
        if (e.getActionCommand().equals("CargarBD")) {
            List<Drawable> childDrawableCargado = Collections.synchronizedList(new ArrayList<Drawable>());
            List<Ball> ballDrawableCargada = Collections.synchronizedList(new ArrayList<Ball>());

            usuario = mensajeU.getText();
            escenaCargada = (Composite) loadScene(usuario, "CARGARBD");

            for (Drawable draw : escenaCargada.getChildDrawable()) {
                childDrawableCargado.add(draw);
                if (draw instanceof Ball) {
                    ballDrawableCargada.add((Ball) draw);
                }
            }
            escena.setChildDrawable(childDrawableCargado);
            animationcontroller.setBallDrawable(ballDrawableCargada);
            mensajeO.setText("Cargado de BD");
        }
    }
}
