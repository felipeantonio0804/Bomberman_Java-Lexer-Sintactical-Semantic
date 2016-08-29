/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventana;


import Almacenamiento.*;
import AnalizadorEscenario.LexicoEscenario;
import AnalizadorEscenario.SintacticoEscenario;
import AnalizadorInteligencia.LexicoInteligencia;
import AnalizadorInteligencia.SintacticoInteligencia;
import AnalizadorRecursos.LexicoRecurso;
import AnalizadorRecursos.SintacticoRecurso;
import Clases.*;
import de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;


/**
 *
 * @author Felipe
 */
public class Ventana implements ActionListener,CaretListener{
    private boolean realizacionAnalisis = false, contieneErrores = true;
    //JFrame
    private JFrame ventana;
    //JFileChooser
    private JFileChooser choser;
    //JMenuBar
    private JMenuBar barraMenu;
    private JMenuBar barraEstado;
    //JLabel
    private JLabel posicionActual;
    //JTextArea
    private JScrollPane scrollRecursos;
    private JTextArea textoRecursos;
    private JScrollPane scrollEscenario;
    private JTextArea textoEscenario;
    private JScrollPane scrollInteligencia;
    private JTextArea textoInteligencia;
    
    //JTabbedPane para las pestanas
    private JTabbedPane panelConPestanas;
    
    //JMenu
    private JMenu archivo;
    private JMenu analisis;
    private JMenu juego;
    private JMenu ayuda;
    
    //JMenuItem
    private JMenuItem nuevo;
    private JMenuItem abrir;
    private JMenuItem guardar;
    private JMenuItem guardarComo;
    private JMenuItem buscar;
    private JMenuItem salir;
    
    private JMenuItem analizar;
    private JMenuItem mostrarErrores;
    
    private JMenuItem vistaPrevia;
    private JMenuItem editorEscenarios;
    private JMenuItem iniciarJuego;
    
    private JMenuItem manualUsuario;
    private JMenuItem manualTecnico;
    private JMenuItem acercaDe;
    
    //JPanel 
    private JPanel fondoBasico;
    
    //JEditorPane
    private JEditorPane editorError;
    private JScrollPane scrollError;
    private JEditorPane editorTablaSimbolos;
    private JScrollPane scrollTablaSimbolos;
            
    //para guardar las rutas de los archivos seleccionados
    private String [] rutasArchivos = new String[3];
    
    //PARA EDITOR DE ESCENARIOS
    //matriz botones para mostrar los objetos en editor escenarios
    private JButton[] listaObjetosEditorEscenarios;
    private JScrollPane scrollListaObjetosEditorEscenarios;
    private JPanel fondoListaObjetosEditorEscenarios;
    
    private JScrollPane scrollTableroEditorEscenarios;
    private PanelFondo fondoTableroEditorEscenarios;
    public static JButton[][] casillasEditorEscenarios;
    
    //para el trasmisor de variableEventoEditorEscenarios en los botones
    public static Variable variableEventoEditorEscenarios = null;
    
    //para el bloqueo de botones al posicionar el primer heroe
    public static Variable primerHeroe = null;
    
    //para guardar el resto de instrucciones en el escenario
    public static ArrayList<Posicionamiento> listaPosiciones = null;
    
    //para cuando se desee guardar en el editor de escenarios
    private JRadioButton archivoNuevo, aplicacion;
    private JButton exportar;
    private JTextArea areaAuxiliar;
    
    //PARA VISTA DE ESCENARIO
    private String rutaImagenEscenario;
    private JScrollPane scrollVistaPrevia;
    private PanelFondo fondoVistaPrevia;
    private JLabel[][] casillasVistaPrevia;
    
    //PARA EL AREA DEL JUEGO EN SI
    public static JLabel[][] casillasJuego = null;
    public static ArrayList<PosicionInicial> listaPosicionesIniciales = null;
     //matrices para que las pueda alterar los botones
    public static Variable principales[][]=null;
    public static Variable secundarias[][]=null;
    
    private JScrollPane scrollDetallesJuego;
    private JPanel fondoDetallesJuego;
    private PanelFondoEvento fondoJuego;
    
    //para el detalle de los objetos enemigos y heroes
    public static JLabel[] detalleObjetos;
    
    //para los hilos de los enemigos
    private HiloEnemigo[] hilosEnemigos =null;
    private ListaMetodos hashMetodosNodo;
    
        
    public Ventana() {
       try
        {
            UIManager.setLookAndFeel(new SyntheticaBlackEyeLookAndFeel());    
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error en el Look and Feel","Error",JOptionPane.ERROR_MESSAGE);
        }                     
        
        for(int i=0;i<rutasArchivos.length;i++){
            rutasArchivos[i]="";
        }
        
        final int ancho = 800;
        final int alto = 690;
        
        // La ventana principal
        ventana = new JFrame("Bomberman");
                
        //JPanel de las pestanas
        JPanel panelRecursos = new JPanel();
        JPanel panelEscenario = new JPanel();
        JPanel panelInteligencia = new JPanel();
        JPanel panelError = new JPanel();
        JPanel panelTablaSimbolos = new JPanel();
        JPanel panelEditorEscenarios = new JPanel();
        JPanel panelVistaPrevia = new JPanel();
        JPanel panelJuego= new JPanel();
        
        
        
        panelRecursos.setLayout(null);
        panelEscenario.setLayout(null);
        panelInteligencia.setLayout(null);
        panelError.setLayout(null);
        panelTablaSimbolos.setLayout(null);
        panelEditorEscenarios.setLayout(null);
        panelVistaPrevia.setLayout(null);
        panelJuego.setLayout(null);
               
        //JLabel
        posicionActual = new JLabel();
        posicionActual.setBounds(0,0,(ancho/2)-100,200);
               
        //JFileChooser
        choser = new JFileChooser();
           
        //JMenu
        archivo = new JMenu("Archivo");
        analisis = new JMenu("Analisis");
        juego = new JMenu("Juego");
        ayuda = new JMenu("Ayuda");
        
         //JTextArea
        textoRecursos = new JTextArea();
        textoRecursos.addCaretListener(this);
        textoRecursos.setEnabled(true);
        
        textoEscenario = new JTextArea();
        textoEscenario.addCaretListener(this);
        textoEscenario.setEnabled(true);
        
        textoInteligencia = new JTextArea();
        textoInteligencia.addCaretListener(this);
        textoInteligencia.setEnabled(true);
       
        //JScrollPane
        scrollRecursos = new JScrollPane(textoRecursos);
        scrollRecursos.setBounds(10,10,ancho-120,alto-150);
        
        scrollEscenario = new JScrollPane(textoEscenario);
        scrollEscenario.setBounds(10,10,ancho-120,alto-150);
        
        scrollInteligencia = new JScrollPane(textoInteligencia);
        scrollInteligencia.setBounds(10,10,ancho-120,alto-150);
        
        //JEditorPane
        editorError = new JEditorPane();
        editorError.setContentType("text/html");
        editorError.setEditable(false);
        scrollError = new JScrollPane(editorError);
        scrollError.setBounds(10,10,ancho-120,alto-150);
        panelError.add(scrollError);
       
        
        editorTablaSimbolos = new JEditorPane();
        editorTablaSimbolos.setContentType("text/html");
        editorTablaSimbolos.setEditable(false);
        scrollTablaSimbolos = new JScrollPane(editorTablaSimbolos);
        scrollTablaSimbolos.setBounds(10,10,ancho-120,alto -150);
        panelTablaSimbolos.add(scrollTablaSimbolos);
            
        //JMenuItem
        nuevo = new JMenuItem("Nuevo");
        abrir = new JMenuItem("Abrir");
        guardar = new JMenuItem("Guardar");
        guardarComo = new JMenuItem("Guardar Como");
        buscar = new JMenuItem("Buscar");
        salir = new JMenuItem("Salir");
        
        analizar= new JMenuItem("Analizar");
        mostrarErrores = new JMenuItem("Mostrar Errores");
        
        vistaPrevia = new JMenuItem("Vista Previa");
        editorEscenarios = new JMenuItem("Editor Escenarios");
        iniciarJuego = new JMenuItem("Inciar Juego");
        
        manualUsuario = new JMenuItem("Manual Usuario");
        manualTecnico = new JMenuItem("Manual Tecnico");
        acercaDe = new JMenuItem("Acerca De");
        
        //adhiriendo a los listeners
        
        nuevo.addActionListener(this);
        abrir.addActionListener(this);
        guardar.addActionListener(this);
        guardarComo.addActionListener(this);
        buscar.addActionListener(this);
        salir.addActionListener(this);
        
        analizar.addActionListener(this);
        mostrarErrores.addActionListener(this);
        
        vistaPrevia.addActionListener(this);
        editorEscenarios.addActionListener(this);
        iniciarJuego.addActionListener(this);
        
        manualUsuario.addActionListener(this);
        manualTecnico.addActionListener(this);
        acercaDe.addActionListener(this);
        
        //adhiriendo a los jmenu respectivos
        archivo.add(nuevo);
        archivo.add(abrir);
        archivo.add(guardar);
        archivo.add(guardarComo);
        archivo.add(buscar);
        archivo.add(salir);
        
        analisis.add(analizar);
        analisis.add(mostrarErrores);
        
        juego.add(vistaPrevia);
        juego.add(editorEscenarios);
        juego.add(iniciarJuego);
        
        ayuda.add(manualUsuario);
        ayuda.add(manualTecnico);
        ayuda.add(acercaDe);
        
        
        //Adheriendo a los paneles respectivos
        
        panelRecursos.add(scrollRecursos);
        panelEscenario.add(scrollEscenario);
        panelInteligencia.add(scrollInteligencia);
        
        
        // El JTabbedPane con sus pestañas e iconos en las mismas.
        panelConPestanas = new JTabbedPane();
        
        panelConPestanas.add("Recursos", panelRecursos);
        panelConPestanas.add("Escenario", panelEscenario);
        panelConPestanas.add("Inteligencia", panelInteligencia);
        panelConPestanas.add("Errores", panelError);
        panelConPestanas.add("Tabla de Simbolos", panelTablaSimbolos);
        panelConPestanas.add("Editor Escenarios", panelEditorEscenarios);
        panelConPestanas.add("Vista Previa", panelVistaPrevia);
        panelConPestanas.add("Juego", panelJuego);
        panelConPestanas.setEnabledAt(5, false);
        panelConPestanas.setEnabledAt(6, false);
        panelConPestanas.setEnabledAt(7, false);
       
        panelConPestanas.setBounds(0,30,ancho, alto-120);
        
        
        
        // Los colores de fondo de las pestanas
        /*
        panelConPestanas.setBackgroundAt(0, Color.YELLOW);
        panelConPestanas.setBackgroundAt(1, Color.YELLOW);
        panelConPestanas.setBackgroundAt(2, Color.YELLOW);
        panelConPestanas.setBackgroundAt(3, Color.YELLOW);
        panelConPestanas.setBackgroundAt(4, Color.YELLOW);
       */

        
        //JmenuBar
        barraMenu = new JMenuBar();
        barraEstado = new JMenuBar();
        
        barraMenu.setBounds(0,0,ancho,30);
        barraEstado.setBounds(0,alto-90,ancho,40);
        
        //adhiriendo a las respectivas barras
        barraMenu.add(archivo);
        barraMenu.add(analisis);
        barraMenu.add(juego);
        barraMenu.add(ayuda);
        
        barraEstado.add(posicionActual);
       
        //Jpanel principal o fondo       
        fondoBasico = new JPanel();
        fondoBasico.setLayout(null);
        fondoBasico.add(barraMenu);
        fondoBasico.add(panelConPestanas);
        fondoBasico.add(barraEstado);
        
        
        //para la lista de objetos y casillas del editor de escenarios
        fondoListaObjetosEditorEscenarios = new JPanel();
        fondoTableroEditorEscenarios = new PanelFondo();
        fondoTableroEditorEscenarios.setLayout(null);
        fondoTableroEditorEscenarios.setBackground(Color.blue);
        
        scrollListaObjetosEditorEscenarios = new JScrollPane(fondoListaObjetosEditorEscenarios);
        scrollListaObjetosEditorEscenarios.setBounds(10,10,100,alto - 140);
        scrollListaObjetosEditorEscenarios.setBackground(Color.blue);
        
        scrollTableroEditorEscenarios = new JScrollPane(fondoTableroEditorEscenarios);
        scrollTableroEditorEscenarios.setBounds(110,10,650,alto - 200);
        scrollTableroEditorEscenarios.setBackground(Color.blue);
        
        panelEditorEscenarios.add(scrollListaObjetosEditorEscenarios);
        panelEditorEscenarios.add(scrollTableroEditorEscenarios);
        
        //para exportar de editor de escenarios
        JPanel fondoGuardarEscenario = new JPanel();
        fondoGuardarEscenario.setLayout(null);
        fondoGuardarEscenario.setBackground(Color.GRAY);
        
        JScrollPane scroll = new JScrollPane(fondoGuardarEscenario);
        scroll.setBounds(110,500,650,50);
        panelEditorEscenarios.add(scroll);
        
        ButtonGroup grupo = new ButtonGroup();
        
        aplicacion = new JRadioButton("Aplicacion");
        aplicacion.setBounds(200,5, 100,35);
        
        archivoNuevo = new JRadioButton("Archivo Nuevo");
        archivoNuevo.setBounds(300,5, 200,35);
        
        exportar = new JButton("Exportar");
        exportar.setBounds(50,5, 150,35);
        exportar.addActionListener(this);
        
        grupo.add( archivoNuevo );
        grupo.add( aplicacion );
        
        fondoGuardarEscenario.add(archivoNuevo);
        fondoGuardarEscenario.add(aplicacion);
        fondoGuardarEscenario.add(exportar);
        
        areaAuxiliar = new JTextArea();
        areaAuxiliar.setText("");
        
        //Para la visualizacion del tablero de vista previa
        fondoVistaPrevia = new PanelFondo();
        fondoVistaPrevia.setLayout(null);
        fondoVistaPrevia.setBackground(Color.red);
        
        scrollVistaPrevia = new JScrollPane(fondoVistaPrevia);
        scrollVistaPrevia.setBounds(10,10,750,alto - 140);
        scrollVistaPrevia.setBackground(Color.red);
        
        panelVistaPrevia.add(scrollVistaPrevia);
       
        //para la visualizacion del tablero del juego
        fondoJuego = new PanelFondoEvento();
        fondoJuego.setLayout(null);
        fondoJuego.setBackground(Color.GREEN);
        fondoJuego.setBounds(110,10,650,alto - 140);
        
        fondoDetallesJuego = new JPanel();
                
        scrollDetallesJuego = new JScrollPane(fondoDetallesJuego);
        scrollDetallesJuego.setBounds(10,10,100,alto - 140);
        //scrollDetallesJuego.setBackground(Color.GREEN);
        
        panelJuego.add(fondoJuego);
        panelJuego.add(scrollDetallesJuego);
        
        // Se visualiza todo
        ventana.getContentPane().add(fondoBasico);
        ventana.setSize(ancho,alto);
        ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ventana.setVisible(true);
        ventana.setResizable(false);
    }

    
       
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==nuevo){
            nuevo();
        }
       else if(e.getSource()==abrir){
           seleccionarArchivo();
       }
       else if(e.getSource()==guardar){
           opcionGuardar(1);
       }
       else if(e.getSource()==guardarComo){
           opcionGuardar(2);
       }
       else if(e.getSource()==buscar){
           buscarTexto();
       }
       else if(e.getSource()==salir){
           System.exit(0);
        }
        
       else if(e.getSource()==analizar){
           panelConPestanas.setSelectedIndex(4);
           escribirDocumentos();
           analizarDocumentos();
           realizacionAnalisis = true;
       }
       else if(e.getSource()==mostrarErrores){
           mostrarDocumento("Errores.html");
       }
       
       else if(e.getSource()==vistaPrevia){
                        if(realizacionAnalisis == true){
                            if(contieneErrores ==false){
                                panelConPestanas.setSelectedIndex(6);
                            }
                            else{
                                JOptionPane.showMessageDialog(ventana,"SE ENCONTRARON ARCHIVOS AUN CON ERRORES, CORREGIRLOS");
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(ventana,"AUN NO HA REALIZADO UN ANALISIS DE LOS ARCHIVOS");
                        }
           
       }
       
       else if(e.getSource()==editorEscenarios){
                if(panelConPestanas.getSelectedIndex()==1){
                        if(realizacionAnalisis == true){
                            if(contieneErrores ==false){
                                panelConPestanas.setSelectedIndex(5);
                            }
                            else{
                                JOptionPane.showMessageDialog(ventana,"SE ENCONTRARON ARCHIVOS AUN CON ERRORES, CORREGIRLOS");
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(ventana,"AUN NO HA REALIZADO UN ANALISIS DE LOS ARCHIVOS");
                        }
                }
                else{
                        JOptionPane.showMessageDialog(ventana,"SELECCIONE EL AREA DE ESCENARIOS");
                }
       }
       
       else if(e.getSource()==iniciarJuego){
                        if(realizacionAnalisis == true){
                            if(contieneErrores ==false){
                                    if(listaPosicionesIniciales!=null){
                                        if(listaPosicionesIniciales.size()>=2&&primerHeroe!=null){
                                            mostrar("INICIA EL JUEGO");
                                            pintarTableroJuego();
                                            panelConPestanas.setSelectedIndex(7);
                                            fondoJuego.setPosicionInicial(getPosicionInicialHeroe("heroe"));
                                            iniciarHilosEnemigos();
                                        }
                                        else{
                                            mostrar("EL JUEGO NECESITA POR LO MENOS UN ENEMIGO Y UN HEROE PARA EMPEZAR");
                                        }
                                    }
                            }
                            else{
                                JOptionPane.showMessageDialog(ventana,"SE ENCONTRARON ARCHIVOS AUN CON ERRORES, CORREGIRLOS");
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(ventana,"AUN NO HA REALIZADO UN ANALISIS DE LOS ARCHIVOS");
                        }
           
           
       }
       
        else if(e.getSource()==manualTecnico){
           mostrarDocumento("Manual_Tecnico.pdf");
       }
       else if(e.getSource()==manualUsuario){
           mostrarDocumento("Manual_Usuario.pdf");
       }
        else if(e.getSource()==acercaDe){
           JOptionPane.showMessageDialog(ventana,"Juego desarrolado por:\n\tFelipe Antonio Lopez De Leon\n\t201122936");
       }
        
        else if(e.getSource()==exportar){
            areaAuxiliar.setText("");
            if(aplicacion.isSelected()){
                String texto =obtenerTextoDeListaPosiciones(Ventana.listaPosiciones);
                textoEscenario.setText(texto);
            }
            else if(archivoNuevo.isSelected()){
                String texto =obtenerTextoDeListaPosiciones(Ventana.listaPosiciones);
                areaAuxiliar.setText(texto);
                guardarComoEscenario(".maze",areaAuxiliar);
            }
        }
        
    }//actionPerformed

    @Override
    public void caretUpdate(CaretEvent e) {
        if(panelConPestanas.getSelectedIndex()==0){
            mandarResultadoPosicion(e,textoRecursos);
        }
        else if(panelConPestanas.getSelectedIndex()==1){
            mandarResultadoPosicion(e,textoEscenario);
        }
        else if(panelConPestanas.getSelectedIndex()==2){
            mandarResultadoPosicion(e,textoInteligencia);
        }
        else{
            posicionActual.setText("");
        }
    }// PARA LA LINEA Y COLUMNA ACTUAL
  
    private void mandarResultadoPosicion(CaretEvent e,JTextArea areaTexto) {
        int posicion = e.getDot(); 
                    try { 
                        int fila = areaTexto.getLineOfOffset( posicion ) + 1; 
                        int columna = posicion - areaTexto.getLineStartOffset( fila - 1 );
                       String cadenaConcatenar = "<html>POSICION ACTUAL<br>Línea: " + fila + " Columna: " + columna+"</html>"; 
                        posicionActual.setFont(new Font("Comic Sans MS",Font.BOLD,12));
                        posicionActual.setText(cadenaConcatenar); 
                    } 
                    catch(BadLocationException exc){ 
                        System.out.println(exc); 
                    } 
    }
    
    private void nuevo() {
        for(int i =0;i<rutasArchivos.length;i++){
            rutasArchivos[i]="";
        }
        textoRecursos.setText("");
        textoEscenario.setText("");
        textoInteligencia.setText("");
    }
    
    
    private void seleccionarArchivo() {
        choser.setAcceptAllFileFilterUsed(false);//quitando todos los tipos de archivo
            FileNameExtensionFilter filtro= new FileNameExtensionFilter("archivos .rr, .maze, .ia","rr","maze","ia");
            choser.setFileFilter(filtro);
            
            int seleccion = choser.showOpenDialog(ventana);
            
                if(seleccion == JFileChooser.APPROVE_OPTION){
                    File archivoEntrada = choser.getSelectedFile();
                    leerArchivo(archivoEntrada);
                }
                else{
                    JOptionPane.showMessageDialog(ventana,"Seleccione un archivo");
                }
    }// fin seleccionarArchivo
    
    private void leerArchivo(File archivoEntrada){
        try {
            String cadena = archivoEntrada.getPath();
            //System.out.println(cadena);
            String extension = cadena.substring(cadena.lastIndexOf('.'));
            //System.out.println(extension);
            if(".rr".equals(extension)){
                //System.out.println("cadena es:"+ obtenerTextoDeArchivo(archivoEntrada));
                textoRecursos.setText(obtenerTextoDeArchivo(archivoEntrada));
                rutasArchivos[0]=archivoEntrada.getPath();
            }
            else if(".maze".equals(extension)){
                //System.out.println("cadena es:"+ obtenerTextoDeArchivo(archivoEntrada));
                textoEscenario.setText(obtenerTextoDeArchivo(archivoEntrada));
                rutasArchivos[1]=archivoEntrada.getPath();
            }
            else if(".ia".equals(extension)){
                //System.out.println("cadena es:"+ obtenerTextoDeArchivo(archivoEntrada));
                textoInteligencia.setText(obtenerTextoDeArchivo(archivoEntrada));
                rutasArchivos[2]=archivoEntrada.getPath();
            }
            
            
        } catch (Exception e) {
           System.out.println("Error al obtener extension de archivo");
        }
        
    }
    
    private String obtenerTextoDeArchivo(File archivoEntrada){
        String texto = "";
         if(archivoEntrada != null){
                FileReader reader = null;
                BufferedReader buffer = null;
 
                try {
                    reader = new FileReader (archivoEntrada);
                    buffer = new BufferedReader(reader);
 
                    String linea;
                    String unionTexto = "";
                    
                    while((linea=buffer.readLine())!=null){
                        unionTexto = unionTexto+linea+"\n";
                    }
                    texto = unionTexto;
                    archivoEntrada = null;//para sacarlo de la memoria
                }
                catch(Exception e2){
                    System.out.println("No se ha podido leer el archivo de origen, posiblemente tenga problemas");
                }
                finally{
                    try{                   
                        if( null != reader ){  
                            reader.close();  
                            buffer.close();
                        }                 
                    }
                    catch (Exception e3){
                        System.out.println("No se ha podido detener el flujo de entrada del archivo");
                    }
                    return texto;
                }//finally
            }//if archivo!=null
            return texto;
    }
    
    
    private void opcionGuardar(int opcion){//opcion 2 es guardar como
        if(panelConPestanas.getSelectedIndex()==0){
            eleccionGuardar(".rr",textoRecursos,opcion);
        }
        else if(panelConPestanas.getSelectedIndex()==1){
            eleccionGuardar(".maze",textoEscenario,opcion);
        }
        else if(panelConPestanas.getSelectedIndex()==2){
            eleccionGuardar(".ia",textoInteligencia,opcion);
        }
        else{
            JOptionPane.showMessageDialog(ventana, "No esta disponible el guardar los archivos HTML");
        }
    }
    
    private void eleccionGuardar(String extension, JTextArea area,int opcion) {
        if(".rr".equals(extension)){
            if(!"".equals(rutasArchivos[0])&&opcion==1){
                guardar(area,0);
            }
            else if(!"".equals(rutasArchivos[0])&&opcion==2){
                guardarComo(extension,area);
            }
            else{
                guardarComo(extension,area);
            }
        }
        
        if(".maze".equals(extension)){
            if(!"".equals(rutasArchivos[1])){
                guardar(area,1);
            }
            else if(!"".equals(rutasArchivos[1])&&opcion==2){
                guardarComo(extension,area);
            }
            else{
                guardarComo(extension,area);
            }
        }
        
        if(".ia".equals(extension)){
            if(!"".equals(rutasArchivos[2])){
                guardar(area,2);
            }
            else if(!"".equals(rutasArchivos[2])&&opcion==2){
                guardarComo(extension,area);
            }
            else{
                guardarComo(extension,area);
            }
        }
        
    }

    
    private void guardarComo(String extension, JTextArea area){
          File archivo = null;
          FileWriter fichero = null;
          BufferedWriter escritor = null;    
            try
            {
                choser.setAcceptAllFileFilterUsed(false);//quitando todos los tipos de archivo
                FileNameExtensionFilter filtro = new FileNameExtensionFilter("archivos "+extension, "rr","maze","ia");
                choser.setFileFilter(filtro);
            
                int opcion = choser.showSaveDialog(ventana);
                if (opcion== JFileChooser.APPROVE_OPTION)
                    {
                        archivo =  new File(choser.getSelectedFile().getAbsolutePath()+extension);
                        fichero = new FileWriter(archivo,false);
                        escritor = new BufferedWriter(fichero);    
                        escritor.write(area.getText());
                        
                        if(".rr".equals(extension)){rutasArchivos[0]=archivo.getPath();}
                        else if(".maze".equals(extension)){rutasArchivos[1]=archivo.getPath();}
                        else if(".ia".equals(extension)){rutasArchivos[2]=archivo.getPath();}
                    }
            } 
            catch (Exception e) {
                System.out.println("Error al guardar");
            }
            finally {
                try {
                        escritor.close();
                        fichero.close();
                } 
                catch (Exception e2) {
                   System.out.println("Error al cerrar los flujos de guardar");
                }
            }//finally
    }//metodo crear un documento
    
    private void guardar(JTextArea area, int posicion){
          File archivo = null;
          FileWriter fichero = null;
          BufferedWriter escritor = null;    
            try
            {
                        archivo =  new File(rutasArchivos[posicion]);
                        fichero = new FileWriter(archivo,false);
                        escritor = new BufferedWriter(fichero);    
                        escritor.write(area.getText());
                   
            } 
            catch (Exception e) {
                System.out.println("Error al guardar");
            }
            finally {
                try {
                        escritor.close();
                        fichero.close();
                } 
                catch (Exception e2) {
                   System.out.println("Error al cerrar los flujos de guardar");
                }
            }//finally
    }//metodo crear un documento
    
    private void buscarTexto() {
        String textoABuscar = JOptionPane.showInputDialog(ventana, "Texto a buscar", "");
        if(textoABuscar!=null){
            JTextArea areaTexto;
            if(panelConPestanas.getSelectedIndex()==0){
                areaTexto = textoRecursos;
            }
            else if(panelConPestanas.getSelectedIndex()==1){
                areaTexto = textoEscenario;
            }
            else if(panelConPestanas.getSelectedIndex()==2){
                areaTexto = textoInteligencia;
            }
            else{
                JOptionPane.showMessageDialog(ventana,"No se puede hacer la busqueda en los archivos html");
                return;
            }
            buscarTextoYPintar(areaTexto.getText(),areaTexto,Color.WHITE);
            buscarTextoYPintar(textoABuscar,areaTexto,Color.YELLOW);    
        }
    }
    
    
   
    private void buscarTextoYPintar(String textoBuscar, JTextArea areaTexto,Color color){
        Highlighter hilit;
        HighlightPainter painter;
        hilit = new DefaultHighlighter();
        painter = new DefaultHighlighter.DefaultHighlightPainter(color);
        
        areaTexto.setHighlighter(hilit);
        hilit.removeAllHighlights();
                
        if (textoBuscar.length() > 0) {
            String contenido = areaTexto.getText();
            int indice = contenido.indexOf(textoBuscar, 0);
                if (indice >= 0) {
                    try {
                        int fin = indice + textoBuscar.length();
                        hilit.addHighlight(indice, fin, painter);
                        areaTexto.setCaretPosition(fin);
                        if(color!=Color.WHITE){
                            JOptionPane.showMessageDialog(ventana,"Texto Encontrado");
                        }
                    } 
                    catch (BadLocationException e) {
                        System.out.println(e);
                    }
               }
               else {
                    JOptionPane.showMessageDialog(ventana,"No se ha encontrado: " + textoBuscar);
               }
        }
        else{
            JOptionPane.showMessageDialog(ventana,"Debe escribir la palabra a buscar");
        }
    }
        
    
    
    private void escribirDocumentos() {
        guardarDocumentoAnalizar("recursos.txt",textoRecursos);
        guardarDocumentoAnalizar("escenario.txt",textoEscenario);
        guardarDocumentoAnalizar("inteligencia.txt",textoInteligencia);
    }
    
    
     private void guardarDocumentoAnalizar(String nombreArchivo , JTextArea area){
          File archivo = null;
          FileWriter fichero = null;
          BufferedWriter escritor = null;    
            try
            {
                        archivo =  new File(nombreArchivo);
                        fichero = new FileWriter(archivo,false);
                        escritor = new BufferedWriter(fichero);    
                        escritor.write(area.getText());
            } 
            catch (Exception e) {
                System.out.println("Error al guardar documento analizar");
            }
            finally {
                try {
                        escritor.close();
                        fichero.close();
                } 
                catch (Exception e2) {
                   System.out.println("Error al cerrar los flujos de guardar documentos analizar");
                }
            }//finally
    }//guardar documento analizar
    
     
      private void guardarComoEscenario(String extension, JTextArea area){
          File archivo = null;
          FileWriter fichero = null;
          BufferedWriter escritor = null;    
            try
            {
                choser.setAcceptAllFileFilterUsed(false);//quitando todos los tipos de archivo
                FileNameExtensionFilter filtro = new FileNameExtensionFilter("archivos "+extension, "maze");
                choser.setFileFilter(filtro);
            
                int opcion = choser.showSaveDialog(ventana);
                if (opcion== JFileChooser.APPROVE_OPTION)
                    {
                        archivo =  new File(choser.getSelectedFile().getAbsolutePath()+extension);
                        fichero = new FileWriter(archivo,false);
                        escritor = new BufferedWriter(fichero);    
                        escritor.write(area.getText());
                    }
            } 
            catch (Exception e) {
                System.out.println("Error al guardar");
            }
            finally {
                try {
                        escritor.close();
                        fichero.close();
                } 
                catch (Exception e2) {
                   System.out.println("Error al cerrar los flujos de guardar");
                }
            }//finally
    }//metodo crear un documento
      
    private void analizarDocumentos() {
        //LIMPIAR LA LISTA
        listaPosicionesIniciales =null;
        //LIMPIAR LOS HILOS
        hilosEnemigos = null;
        
        LexicoRecurso lexicoRecursos=null;
        SintacticoRecurso sintacticoRecursos = null;
        LexicoEscenario lexicoEscenario=null;
        SintacticoEscenario sintacticoEscenario=null;
        LexicoInteligencia lexicoInteligencia = null;
       SintacticoInteligencia sintacticoInteligencia = null;
       
        File archivo;
        BufferedReader reader;
        
        try {
            archivo = new File("inteligencia.txt");
            reader = new BufferedReader(new FileReader(archivo));
            lexicoInteligencia= new LexicoInteligencia(reader);
            sintacticoInteligencia = new SintacticoInteligencia(lexicoInteligencia);
            sintacticoInteligencia.parse();
            hashMetodosNodo = sintacticoInteligencia.listaMetodosNodo;
//            Nodo algoritmo = hashMetodosNodo.buscar("dif_facil");
//               if(algoritmo.hijos.get(1)!=null){
//                   int retardo = (int) (Double.parseDouble(algoritmo.hijos.get(0).valor)*1000.00);
//                   mostrar(retardo+"");
//                   ejecutar(algoritmo.hijos.get(1));
//               }
//            System.out.println("---------------ERRORES SINTACTICOS-SEMANTICOS  INTELIGENCIA---------------");
//            for(int i=0;i<sintacticoInteligencia.getListaErrores().size();i++){
//                System.out.println("Lexema: "+sintacticoInteligencia.getListaErrores().get(i).getLexema()+", Fila: "+sintacticoInteligencia.getListaErrores().get(i).getFila()+", Columna "+sintacticoInteligencia.getListaErrores().get(i).getColumna()+", Descripcion: "+sintacticoInteligencia.getListaErrores().get(i).getDescripcion());
//            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ventana,e);
            //JOptionPane.showMessageDialog(ventana,"Error en el analisis del archivo inteligencia.txt");
        }
        
        try {
            archivo = new File("recursos.txt");
            reader = new BufferedReader(new FileReader(archivo));
            lexicoRecursos = new LexicoRecurso(reader);
            sintacticoRecursos = new SintacticoRecurso(lexicoRecursos);
            sintacticoRecursos.setListaMetodos(sintacticoInteligencia.getListaMetodos());
            sintacticoRecursos.parse();
            
            if(sintacticoRecursos.getPrimeroEnemigo()==false||sintacticoRecursos.getPrimeroHeroe()==false){
                sintacticoRecursos.ingresarErrorSintactico("-------", 0, 0,"Error Semantico, No existe por lo menos un heroe o por lo menos un enemigo, Archivo Recursos.rr");
            }
            
            if(sintacticoRecursos.getVariable("escenario")==null){//NO EXISTE AUN UN ESCENARIO
                sintacticoRecursos.ingresoVariable("escenario","escenario",0,0);
                Variable escenario = sintacticoRecursos.getVariable("escenario");
                escenario.setRuta("\"Imagenes/escenarioDefault.jpeg\"");
            }
            
//            System.out.println("---------------ERRORES SINTACTICOS-SEMANTICOS   RECURSOS---------------");
//            for(int i=0;i<sintacticoRecursos.getListaErrores().size();i++){
//                System.out.println("Lexema: "+sintacticoRecursos.getListaErrores().get(i).getLexema()+", Fila: "+sintacticoRecursos.getListaErrores().get(i).getFila()+", Columna "+sintacticoRecursos.getListaErrores().get(i).getColumna()+", Descripcion: "+sintacticoRecursos.getListaErrores().get(i).getDescripcion());
//            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ventana,"Error en el analisis del archivo recursos.txt");
        }
        
        try {
            if(!textoEscenario.getText().isEmpty()){//SOLO LO REALIZA CUANDO EL AREA DE TEXTO DE ESCENARIO TIENE CARACTERES
                archivo = new File("escenario.txt");
                reader = new BufferedReader(new FileReader(archivo));
                lexicoEscenario = new LexicoEscenario(reader);
                sintacticoEscenario = new SintacticoEscenario(lexicoEscenario);
                sintacticoEscenario.setListaVariables(sintacticoRecursos.getListaVariables());
                sintacticoEscenario.parse();
                listaPosicionesIniciales = sintacticoEscenario.getListaPosicionInicial();
                PanelFondoEvento.numeroEnemigos = listaPosicionesIniciales.size()-1;
            }
            else{
                listaPosicionesIniciales = new ArrayList<PosicionInicial>();
                PanelFondoEvento.numeroEnemigos=0;
            }
            
//            System.out.println("---------------LISTA DE POSICIONAMIENTO---------------");
//            for(int i=0;i<sintacticoEscenario.getListaPosicionamiento().size();i++){
//                System.out.println("Id: "+sintacticoEscenario.getListaPosicionamiento().get(i).getIdentificador()+", X: "+sintacticoEscenario.getListaPosicionamiento().get(i).getX()+", Y: "+sintacticoEscenario.getListaPosicionamiento().get(i).getY());
//            }
            
//            System.out.println("---------------ERRORES SINTACTICOS-SEMANTICOS  ESCENARIO---------------");
//            for(int i=0;i<sintacticoEscenario.getListaErrores().size();i++){
//                System.out.println("Lexema: "+sintacticoEscenario.getListaErrores().get(i).getLexema()+", Fila: "+sintacticoEscenario.getListaErrores().get(i).getFila()+", Columna "+sintacticoEscenario.getListaErrores().get(i).getColumna()+", Descripcion: "+sintacticoEscenario.getListaErrores().get(i).getDescripcion());
//            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ventana,"Error en el analisis del archivo escenario.txt");
        }
                
        if(sintacticoInteligencia!=null&&sintacticoEscenario!=null&&sintacticoRecursos!=null){//CUANDO LOS TRES ARCHIVOS RECIBEN ENTRADA
            crearHtmlError(lexicoRecursos.getListaErrores(),lexicoEscenario.getListaErrores(),lexicoInteligencia.getListaErrores(),sintacticoRecursos.getListaErrores(),sintacticoEscenario.getListaErrores(),sintacticoInteligencia.getListaErrores());
            editorError.setText(obtenerTextoDeArchivo(new File("Errores.html")));
            crearHtmlTablaSimbolos(sintacticoRecursos.getListaVariables(),sintacticoInteligencia.getListaMetodos());
            editorTablaSimbolos.setText(obtenerTextoDeArchivo(new File("TablaSimbolos.html")));
        }
        
        else if(sintacticoInteligencia!=null&&sintacticoRecursos!=null&&sintacticoEscenario==null){//CUANDO SOLO INTELIGENCIA Y RECURSOS RECIBEN ENTRADA
            crearHtmlError(lexicoRecursos.getListaErrores(),null,lexicoInteligencia.getListaErrores(),sintacticoRecursos.getListaErrores(),null,sintacticoInteligencia.getListaErrores());
            editorError.setText(obtenerTextoDeArchivo(new File("Errores.html")));
            crearHtmlTablaSimbolos(sintacticoRecursos.getListaVariables(),sintacticoInteligencia.getListaMetodos());
            editorTablaSimbolos.setText(obtenerTextoDeArchivo(new File("TablaSimbolos.html")));
        }
        
        if(sintacticoEscenario!=null){
            if(sintacticoRecursos.getListaErrores().size()==0&&lexicoRecursos.getListaErrores().size()==0&&sintacticoEscenario.getListaErrores().size()==0&&lexicoEscenario.getListaErrores().size()==0&&sintacticoInteligencia.getListaErrores().size()==0&&lexicoInteligencia.getListaErrores().size()==0){
                contieneErrores = false;
                agregarBotonesListaObjetosYPintarEscenario(sintacticoRecursos,sintacticoEscenario);
                pintarTableroVistaPrevia();
            }
            else{
                contieneErrores = true;
            }
        }
        else{
            if(sintacticoRecursos.getListaErrores().size()==0&&lexicoRecursos.getListaErrores().size()==0&&sintacticoInteligencia.getListaErrores().size()==0&&lexicoInteligencia.getListaErrores().size()==0){
                contieneErrores = false;
                agregarBotonesListaObjetosYPintarEscenario(sintacticoRecursos,null);
                pintarTableroVistaPrevia();
            }
            else{
                contieneErrores = true;
            }
        }
    }
    
     
   private void crearHtmlError(ArrayList<ErrorLexico> lexicoRecurso,ArrayList<ErrorLexico> lexicoEscenario, ArrayList<ErrorLexico> lexicoInteligencia, ArrayList<ErrorSintactico> sintacticoRecurso,ArrayList<ErrorSintactico> sintacticoEscenario, ArrayList<ErrorSintactico> sintacticoInteligencia){//VA A DEVOLVER EL NUMERO DE ERRORES SI ES QUE TIENE DEL ANALISIS LEXICO
        int numeroError = 0;
        File archivo = null;
        FileWriter fichero = null;
        BufferedWriter escritor = null;
        
            try
            {
                archivo = new File("Errores.html");
                fichero = new FileWriter(archivo,false);
                escritor = new BufferedWriter(fichero);
                
         
                     escritor.write("<html>\n<head>\n<title>\nERRORES LEXICOS </title>\n<body bgcolor=\"#005500\" text=\"#994499\" background=\"Imagenes/agua.jpeg\"> " );
                     escritor.write("<p align=justify>");
                     escritor.write("<center><h1>ANALISIS DE ERRORES LEXICOS</h1></center>");
                     if(lexicoEscenario!=null&&sintacticoEscenario!=null){
                        escritor.write("<table bgcolor=\"#0000050\" width="+5*70+" height="+(lexicoRecurso.size()+lexicoEscenario.size()+lexicoInteligencia.size()+sintacticoRecurso.size()+sintacticoEscenario.size()+sintacticoInteligencia.size()-6)*25+" border=1 align=center>");
                     }
                     else{
                         escritor.write("<table bgcolor=\"#0000050\" width="+5*70+" height="+(lexicoRecurso.size()+lexicoInteligencia.size()+sintacticoRecurso.size()+sintacticoInteligencia.size()-4)*25+" border=1 align=center>");
                     }
                     escritor.write("<tr>");
                        escritor.write("<th align=center valign=middle>NO. ERROR</th>");
                        escritor.write("<th align=center valign=middle>SIMBOLO O LEXEMA</th>");
                        escritor.write("<th align=center valign=middle>NO. LINEA</th>");
                        escritor.write("<th align=center valign=middle>NO. COLUMNA</th>");
                        escritor.write("<th align=center valign=middle>DESCRIPCION</th>");
	escritor.write("</tr>");  
                    
	
                    for (int i = 0; i <= (lexicoRecurso.size()-1); i++){
                        escritor.write("<tr>");
                            numeroError = numeroError+1;
                            escritor.write("<td align=center valign=middle>"+numeroError+"</td>");
                            escritor.write("<td align=center valign=middle>"+lexicoRecurso.get(i).getLexema()+"</td>");
                            escritor.write("<td align=center valign=middle>"+lexicoRecurso.get(i).getFila()+"</td>");
                            escritor.write("<td align=center valign=middle>"+lexicoRecurso.get(i).getColumna()+"</td>");
                             escritor.write("<td align=center valign=middle>"+lexicoRecurso.get(i).getDescripcion()+"</td>");
                        escritor.write("</tr>");  
                    }
                    
                    if(lexicoEscenario!=null){
                        for (int i = 0; i <= (lexicoEscenario.size()-1); i++){
                            escritor.write("<tr>");
                                numeroError = numeroError+1;
                                escritor.write("<td align=center valign=middle>"+numeroError+"</td>");
                                escritor.write("<td align=center valign=middle>"+lexicoEscenario.get(i).getLexema()+"</td>");
                                escritor.write("<td align=center valign=middle>"+lexicoEscenario.get(i).getFila()+"</td>");
                                escritor.write("<td align=center valign=middle>"+lexicoEscenario.get(i).getColumna()+"</td>");
                                escritor.write("<td align=center valign=middle>"+lexicoEscenario.get(i).getDescripcion()+"</td>");
                            escritor.write("</tr>");  
                        }
                    }
                    
                    for (int i = 0; i <= (lexicoInteligencia.size()-1); i++){
                        escritor.write("<tr>");
                            numeroError = numeroError+1;
                            escritor.write("<td align=center valign=middle>"+numeroError+"</td>");
                            escritor.write("<td align=center valign=middle>"+lexicoInteligencia.get(i).getLexema()+"</td>");
                            escritor.write("<td align=center valign=middle>"+lexicoInteligencia.get(i).getFila()+"</td>");
                            escritor.write("<td align=center valign=middle>"+lexicoInteligencia.get(i).getColumna()+"</td>");
                             escritor.write("<td align=center valign=middle>"+lexicoInteligencia.get(i).getDescripcion()+"</td>");
                        escritor.write("</tr>");  
                    }
                    
                    for (int i = 0; i <= (sintacticoRecurso.size()-1); i++){
                        escritor.write("<tr>");
                            numeroError = numeroError+1;
                            escritor.write("<td align=center valign=middle>"+numeroError+"</td>");
                            escritor.write("<td align=center valign=middle>"+sintacticoRecurso.get(i).getLexema()+"</td>");
                            escritor.write("<td align=center valign=middle>"+sintacticoRecurso.get(i).getFila()+"</td>");
                            escritor.write("<td align=center valign=middle>"+sintacticoRecurso.get(i).getColumna()+"</td>");
                             escritor.write("<td align=center valign=middle>"+sintacticoRecurso.get(i).getDescripcion()+"</td>");
                        escritor.write("</tr>");  
                    }
                    
                    if(lexicoEscenario!=null){
                        for (int i = 0; i <= (sintacticoEscenario.size()-1); i++){
                            escritor.write("<tr>");
                                numeroError = numeroError+1;
                                escritor.write("<td align=center valign=middle>"+numeroError+"</td>");
                                escritor.write("<td align=center valign=middle>"+sintacticoEscenario.get(i).getLexema()+"</td>");
                                escritor.write("<td align=center valign=middle>"+sintacticoEscenario.get(i).getFila()+"</td>");
                                escritor.write("<td align=center valign=middle>"+sintacticoEscenario.get(i).getColumna()+"</td>");
                                escritor.write("<td align=center valign=middle>"+sintacticoEscenario.get(i).getDescripcion()+"</td>");
                            escritor.write("</tr>");  
                        }
                    }
                    
                    for (int i = 0; i <= (sintacticoInteligencia.size()-1); i++){
                        escritor.write("<tr>");
                            numeroError = numeroError+1;
                            escritor.write("<td align=center valign=middle>"+numeroError+"</td>");
                            escritor.write("<td align=center valign=middle>"+sintacticoInteligencia.get(i).getLexema()+"</td>");
                            escritor.write("<td align=center valign=middle>"+sintacticoInteligencia.get(i).getFila()+"</td>");
                            escritor.write("<td align=center valign=middle>"+sintacticoInteligencia.get(i).getColumna()+"</td>");
                             escritor.write("<td align=center valign=middle>"+sintacticoInteligencia.get(i).getDescripcion()+"</td>");
                        escritor.write("</tr>");  
                    }
                    
                    escritor.write("</table>");
                    escritor.write("</p align=justify>");
                    escritor.write("</body>\n</head>\n</html>");
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                try {
                    //if (null != fichero)
                        escritor.close();
                        fichero.close();
                } 
                catch (Exception e2) {
                   e2.printStackTrace();
                }
            }//finally
    }//cierre metodo crea html errores

   
   public void crearHtmlTablaSimbolos(ArrayList<Variable> listaVariables,ArrayList<Metodo> listaMetodos){//VA A DEVOLVER EL NUMERO DE ERRORES SI ES QUE TIENE DEL ANALISIS LEXICO
        int numeroVariableMetodo = 0;
        File archivo = null;
        FileWriter fichero = null;
        BufferedWriter escritor = null;
        
            try
            {
                archivo = new File("TablaSimbolos.html");
                fichero = new FileWriter(archivo,false);
                escritor = new BufferedWriter(fichero);
                
         
                     escritor.write("<html>\n<head>\n<title>\nTABLA SIMBOLOS</title>\n<body bgcolor=\"#005500\" text=\"#994499\" background=\"Imagenes/agua.jpeg\"> " );
                     escritor.write("<p align=justify>");
                     escritor.write("<center><h1>REPRESENTACION DE LA TABLA DE SIMBOLOS</h1></center>");
                     escritor.write("<table bgcolor=\"#0000050\" width="+7*70+" height="+(listaVariables.size()-1)*25+" border=1 align=center>");
                     escritor.write("<tr>");
                        escritor.write("<th align=center valign=middle>NO.</th>");
                        escritor.write("<th align=center valign=middle>IDENTIFICADOR</th>");
                        escritor.write("<th align=center valign=middle>TIPO</th>");
                        escritor.write("<th align=center valign=middle>RUTA</th>");
                        escritor.write("<th align=center valign=middle>CORAZONES</th>");
                        escritor.write("<th align=center valign=middle>ALGORITMO</th>");
                        escritor.write("<th align=center valign=middle>DESTRUIBLE</th>");
                         escritor.write("<th align=center valign=middle>RETARDO</th>");
                    escritor.write("</tr>");  
                    
	
                    for (int i = 0; i <= (listaVariables.size()-1); i++){
                        escritor.write("<tr>");
                            numeroVariableMetodo = numeroVariableMetodo+1;
                            String identificador = listaVariables.get(i).getIdentificador();
                            String tipo = listaVariables.get(i).getTipo();
                            String ruta = listaVariables.get(i).getRuta();
                            String corazones = listaVariables.get(i).getCorazones();
                            String algoritmo = listaVariables.get(i).getAlgoritmo();
                            String destruible = listaVariables.get(i).getDestruible();
                                                                    
                            escritor.write("<td align=center valign=middle>"+numeroVariableMetodo+"</td>");
                            if(identificador!=null){
                                escritor.write("<td align=center valign=middle>"+identificador+"</td>");
                            }
                            else{
                                escritor.write("<td align=center valign=middle>------</td>");
                            }
                            if(tipo!=null){
                                escritor.write("<td align=center valign=middle>"+tipo+"</td>");
                            }
                            else{
                                escritor.write("<td align=center valign=middle>------</td>");
                            }
                            if(ruta!=null){
                                escritor.write("<td align=center valign=middle>"+ruta+"</td>");
                            }
                            else{
                                escritor.write("<td align=center valign=middle>------</td>");
                            }
                            if(corazones!=null){
                                escritor.write("<td align=center valign=middle>"+corazones+"</td>");
                            }
                            else{
                                escritor.write("<td align=center valign=middle>------</td>");
                            }
                            if(algoritmo!=null){
                                escritor.write("<td align=center valign=middle>"+algoritmo+"</td>");
                            }
                            else{
                                escritor.write("<td align=center valign=middle>------</td>");
                            }
                            if(destruible!=null){
                                escritor.write("<td align=center valign=middle>"+destruible+"</td>");
                            }
                            else{
                                escritor.write("<td align=center valign=middle>------</td>");
                            }
                            escritor.write("<td align=center valign=middle>------</td>");//IMPLICA LA DE RETARDO
                        escritor.write("</tr>");  
                    }
                    for (int i = 0; i <= (listaMetodos.size()-1); i++){
                        escritor.write("<tr>");
                            numeroVariableMetodo = numeroVariableMetodo+1;                                           
                            escritor.write("<td align=center valign=middle>"+numeroVariableMetodo+"</td>");
                            escritor.write("<td align=center valign=middle>"+listaMetodos.get(i).getIdentificador() +"</td>");
                            escritor.write("<td align=center valign=middle>------</td>");
                            escritor.write("<td align=center valign=middle>------</td>");
                            escritor.write("<td align=center valign=middle>------</td>");
                            escritor.write("<td align=center valign=middle>------</td>");
                            escritor.write("<td align=center valign=middle>------</td>");
                            escritor.write("<td align=center valign=middle>"+listaMetodos.get(i).getRetardo() +"</td>");
                        escritor.write("</tr>");  
                    }
                    escritor.write("</table>");
                    
                    
	
                                       
                    
                    escritor.write("</p align=justify>");
                    escritor.write("</body>\n</head>\n</html>");
                    
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                try {
                    //if (null != fichero)
                        escritor.close();
                        fichero.close();
                } 
                catch (Exception e2) {
                   e2.printStackTrace();
                }
            }//finally
    }//cierre metodo crea html tabla de simbolos
   
   private void mostrarDocumento(String nombre) {
       try {
            File archivo = new File(nombre);
            Desktop.getDesktop().open(archivo);
        } 
        catch(Exception e) {
           JOptionPane.showMessageDialog(ventana,"No ha sido posible abrir el documento");
        }   
    }

   
    private void agregarBotonesListaObjetosYPintarEscenario(SintacticoRecurso sintacticoRecursos,SintacticoEscenario sintacticoEscenario) {
      //PARA LIMPIAR LAS MATRICES LOGICAS Y EL PRIMER HEROE Y LA LISTA DE POSCIONAMIENTO
      principales = null;
      secundarias = null;
     ArrayList<Posicionamiento> listaPosiciones = null;
        if(sintacticoEscenario!=null){
           listaPosiciones = sintacticoEscenario.getListaPosicionamiento();
           principales = sintacticoEscenario.getVariablesPrincipales();
           secundarias = sintacticoEscenario.getVariablesSecundarias();    
            //PARA LIMPIAR EL HEROE CUANDO SE VUELVE A ANALIZAR 
           Ventana.primerHeroe = null;
            if(sintacticoEscenario.getPrimerHeroe()==null){
                Ventana.primerHeroe = null;
            }
            else{
                Ventana.primerHeroe = sintacticoEscenario.getPrimerHeroe();
            }
        }
        else{
            listaPosiciones = new ArrayList<Posicionamiento>();
            principales = new Variable[16][16];
            secundarias = new Variable[16][16];
        }
        
        ArrayList<Variable> listaVariables = sintacticoRecursos.getListaVariables();
        
        //PARA LIMPIAR LA LISTA DE POSIONES CUANDO SE VUELVA A ANALIZAR
        Ventana.listaPosiciones = null;
        if(listaPosiciones!=null){
            Ventana.listaPosiciones = listaPosiciones;
        }
        else{
            Ventana.listaPosiciones = new ArrayList<Posicionamiento>();
        }
        
         //PARA LIMPIAR LOS EVENTOS PARA CUANDO SE VUELVA A ANALIZAR
        EventoBotonTablero[][] eventoTablero = null;
        eventoTablero = new EventoBotonTablero[16][16];
        
        listaObjetosEditorEscenarios = null;
        listaObjetosEditorEscenarios = new JButton[listaVariables.size()];
        
        casillasEditorEscenarios = null;
        casillasEditorEscenarios = new JButton[16][16];
        
        fondoListaObjetosEditorEscenarios.removeAll();
        fondoListaObjetosEditorEscenarios.repaint();
        fondoListaObjetosEditorEscenarios.setLayout(new GridLayout(listaVariables.size(),1));
        
        fondoTableroEditorEscenarios.removeAll();
        fondoTableroEditorEscenarios.pintarFondo("");
        fondoTableroEditorEscenarios.repaint();
        fondoTableroEditorEscenarios.setLayout(null);
        
        
        for(int i= 0;i<listaVariables.size();i++){//PONER FONDO AL PANEL DEL TABLERO
            if("escenario".equals(listaVariables.get(i).getIdentificador())){
                fondoTableroEditorEscenarios.pintarFondo(quitarComillas(listaVariables.get(i).getRuta()));
                rutaImagenEscenario = quitarComillas(listaVariables.get(i).getRuta());
            }
        }
            
        
        for(int i=0;i<listaVariables.size();i++){//PARA LA LISTA DE OBJETOS EN EL EDITOR DE ESCENARIOS
            if(!"escenario".equals(listaVariables.get(i).getIdentificador().toLowerCase())){
                listaObjetosEditorEscenarios[i] = new JButton();
                listaObjetosEditorEscenarios[i].setToolTipText(listaVariables.get(i).getTipo()+":"+listaVariables.get(i).getIdentificador());
                listaObjetosEditorEscenarios[i].setSize(30,30);
                listaObjetosEditorEscenarios[i].addActionListener(new EventoBotonLista(listaVariables.get(i)));
                File archivoAuxiliar = new File(quitarComillas(listaVariables.get(i).getRuta()));
                if(!archivoAuxiliar.exists()){
                    listaObjetosEditorEscenarios[i].setEnabled(false);
                    listaObjetosEditorEscenarios[i].setText("No imagen");
                }
                ImageIcon imagen = new ImageIcon(quitarComillas(listaVariables.get(i).getRuta()));
                Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(listaObjetosEditorEscenarios[i].getWidth(), listaObjetosEditorEscenarios[i].getHeight(), Image.SCALE_DEFAULT));
                listaObjetosEditorEscenarios[i].setIcon(icono);
                fondoListaObjetosEditorEscenarios.add(listaObjetosEditorEscenarios[i]);
            }
        }
        
        int anchoBoton = 30;
        int altoBoton = 30;
        int altoPanel = fondoTableroEditorEscenarios.getHeight() - 5;
      
        
        if(listaPosiciones!=null){//PARA DIBUJAR LA LISTA DE POSICIONES OCUPADAS
            for(int i = 0;i<listaPosiciones.size();i++){
                Variable variable = sintacticoRecursos.getVariable(listaPosiciones.get(i).getIdentificador());
                String tipo = variable.getTipo();
                int x = listaPosiciones.get(i).getX();
                int y = listaPosiciones.get(i).getY();
                
                
                if("heroe".equals(tipo)||"enemigo".equals(tipo)||"obstaculo".equals(tipo)){
                        casillasEditorEscenarios[x][y] = new JButton();
                        casillasEditorEscenarios[x][y].setToolTipText(x+"-"+y);
                        fondoTableroEditorEscenarios.add(casillasEditorEscenarios[x][y]);
                        casillasEditorEscenarios[x][y].setBounds((anchoBoton*(x+2))+x,(altoPanel-altoBoton*(y))-y, anchoBoton,altoBoton);
                        eventoTablero[x][y] = new EventoBotonTablero(x,y,variable);
                        casillasEditorEscenarios[x][y].addActionListener(eventoTablero[x][y]);
                }
                else if("fuego".equals(tipo)||"corazon".equals(tipo)||"bombar".equals(tipo)||"bomba".equals(tipo)){
                        casillasEditorEscenarios[x][y].setToolTipText(x+"-"+y+". "+tipo);
                        eventoTablero[x][y].setVariableSecundaria(variable);
                }
            }
        }
                
        for(int i=1;i<casillasEditorEscenarios.length;i++){//PARA DIBUJAR LOS BOTONES QUE NO CONTIENEN NADA EN SUS POSICIONES
            for(int j=1;j<casillasEditorEscenarios.length;j++){
                if(casillasEditorEscenarios[i][j]==null){
                    casillasEditorEscenarios[i][j] = new JButton();
                    casillasEditorEscenarios[i][j].setToolTipText(i+"-"+j);
                    fondoTableroEditorEscenarios.add(casillasEditorEscenarios[i][j]);
                    casillasEditorEscenarios[i][j].setBounds((anchoBoton*(i+2))+i,(altoPanel-altoBoton*(j))-j, anchoBoton,altoBoton);
                    eventoTablero[i][j] = new EventoBotonTablero(i,j);
                    casillasEditorEscenarios[i][j].addActionListener(eventoTablero[i][j]);
                    //JOptionPane.showMessageDialog(ventana,"InicioX: "+((anchoBoton*(i+1))+i)+", InicicioY: "+((altoPanel-altoBoton*(j))-j));
                }
            }
        }
    }
    
    
    private String quitarComillas(String ruta){
        String cadena = ruta.substring(1, ruta.length()-1);
        return cadena;
    }
    
    private String obtenerTextoDeListaPosiciones(ArrayList<Posicionamiento> listaPosiciones){
        String cadena = "";
                for(int i=0;i<listaPosiciones.size();i++){
                    String linea =  listaPosiciones.get(i).getIdentificador()+"->("+listaPosiciones.get(i).getX()+","+listaPosiciones.get(i).getY()+");\n";
                    cadena = cadena + linea;
                }
         return cadena;
    }

    private void pintarTableroVistaPrevia() {
        int anchoBoton = 30;
        int altoBoton = 30;
        int altoPanel = fondoVistaPrevia.getHeight() - 5;
        
        casillasVistaPrevia = null;
        casillasVistaPrevia = new JLabel[16][16];
        
        fondoVistaPrevia.removeAll();
        fondoVistaPrevia.pintarFondo("");
        fondoVistaPrevia.repaint();     
      
        if(!"".equals(rutaImagenEscenario)){//PONER EL FONDO AL ESCENARIO
                fondoVistaPrevia.pintarFondo(rutaImagenEscenario);
        }
       
               
        for(int i=1;i<casillasVistaPrevia.length;i++){//PARA DIBUJAR LA MATRIZ EN ORDEN
            for(int j=1;j<casillasVistaPrevia.length;j++){
                if(casillasVistaPrevia[i][j]==null){
                    casillasVistaPrevia[i][j] = new JLabel();
                    casillasVistaPrevia[i][j].setToolTipText(i+"-"+j);
                    fondoVistaPrevia.add(casillasVistaPrevia[i][j]);
                    casillasVistaPrevia[i][j].setBounds((anchoBoton*(i+2))+i,(altoPanel-altoBoton*(j+2))-j, anchoBoton,altoBoton);
                    if(principales[i][j]!=null){
                        pintarLabel(casillasVistaPrevia[i][j],principales[i][j]);
                    }
                    if(secundarias[i][j]!=null){
                        casillasVistaPrevia[i][j].setToolTipText(i+"-"+j+". "+secundarias[i][j].getTipo());
                    }
                }
            }
        }    
    }
    
    private void pintarLabel(JLabel etiqueta, Variable variable){
                ImageIcon imagen = new ImageIcon(quitarComillas(variable.getRuta()));
                Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(etiqueta.getWidth(),etiqueta.getHeight(), Image.SCALE_DEFAULT));
                etiqueta.setIcon(icono);
    }
    
    
    private void pintarTableroJuego() {
        detalleObjetos = null;
        detalleObjetos = new JLabel[listaPosicionesIniciales.size()];  
        
        fondoDetallesJuego.setLayout(new GridLayout(listaPosicionesIniciales.size(),1));
        fondoDetallesJuego.removeAll();
        fondoDetallesJuego.repaint();
        
        fondoJuego.setValoresDefaultHeroe();
        
        int anchoBoton = 30;
        int altoBoton = 30;
        int altoPanel = fondoJuego.getHeight() - 5;
       
        casillasJuego = null;
        casillasJuego = new JLabel[16][16];
        
        fondoJuego.removeAll();
        fondoJuego.pintarFondo("");
        fondoJuego.repaint();
        
        for(int i= 0;i<listaPosicionesIniciales.size();i++){
            detalleObjetos[i] = new JLabel();
            detalleObjetos[i].setSize(anchoBoton+20,altoBoton+30);
            if("heroe".equals(listaPosicionesIniciales.get(i).getTipo())){
                Variable variable = listaPosicionesIniciales.get(i).getVariable();
                pintarLabel(detalleObjetos[i],variable);
                detalleObjetos[i].setToolTipText("<html>Identificador: "+variable.getIdentificador()+"<br>Corazones: "+variable.getCorazones()+"<br>Fuego: 1<br>Bomba: 1<br>BombaR: 0</html>");
                fondoDetallesJuego.add(detalleObjetos[i]);
            }
            else{//es enemigo
                Variable variable = listaPosicionesIniciales.get(i).getVariable();
                pintarLabel(detalleObjetos[i],variable);
                detalleObjetos[i].setToolTipText("<html>Identificador: "+variable.getIdentificador()+"<br>Corazones: "+variable.getCorazones()+"</html>");
                fondoDetallesJuego.add(detalleObjetos[i]);
            }
        }
        
        if(!"".equals(rutaImagenEscenario)){//PONER EL FONDO AL ESCENARIO
                fondoJuego.pintarFondo(rutaImagenEscenario);
        }
               
        for(int i=1;i<casillasJuego.length;i++){//PARA DIBUJAR LA MATRIZ EN ORDEN
            for(int j=1;j<casillasJuego.length;j++){
                if(casillasJuego[i][j]==null){
                    casillasJuego[i][j] = new JLabel();
                    casillasJuego[i][j].setToolTipText(i+"-"+j);
                    fondoJuego.add(casillasJuego[i][j]);
                    casillasJuego[i][j].setBounds((anchoBoton*(i+2))+i,(altoPanel-altoBoton*(j+2))-j, anchoBoton,altoBoton);
                    if(principales[i][j]!=null){
                        pintarLabel(casillasJuego[i][j],principales[i][j]);
                    }
                    if(secundarias[i][j]!=null){
                        casillasJuego[i][j].setToolTipText(i+"-"+j+". "+secundarias[i][j].getTipo());
                    }
                }
            }
        }
    }
    

    private PosicionInicial getPosicionInicialHeroe(String tipo) {
        if(listaPosicionesIniciales!=null){
            for(int i=0;i<listaPosicionesIniciales.size();i++){
                if(listaPosicionesIniciales.get(i).getTipo().equals(tipo)){
                    return listaPosicionesIniciales.get(i);
                }
            }
            return null;
        }
        return null;
    }
    
    //SUPONIENDO QUE EL NODO QUE LE MANDO ES EL DE CUERPO DESDE EL INICIO 
    private void ejecutar(Nodo instrucciones) throws Exception {
        int i = 0;
        Nodo instruccion;
        while (i < instrucciones.hijos.size()) {
            instruccion = instrucciones.hijos.get(i);
            
            if (instruccion.valor.equals("IF")) {
                //if ((Boolean) evaluarExpresion(instruccion.hijos.get(0))) {//CONDICION
                    mostrar("VINO IF");
                    if(instruccion.hijos.get(1)!=null){
                        ejecutar(instruccion.hijos.get(1));//RECURSIVO Y ESTE LA MANDA  NODO CUERPO
                    }
                    
                //} 
                //else {
                    if (instruccion.hijos.size() == 3) {
                        //PARA CUANDO HALLA ELSE IFS Y ELSE
                        ejecutar(instruccion.hijos.get(2));//NODO OPCIONES
                    }
                //}
            }
            
            else if (instruccion.valor.equals("WHILE")) {
                //while ((Boolean) evaluarExpresion(instruccion.hijos.get(0))) {//CONDICION
                    mostrar("VINO WHILE");
                    ejecutar(instruccion.hijos.get(1));//RECURSIVO Y ESTE LE MANDA NODO CUERPO
               // }
            }
            
            else if (instruccion.valor.equals("MOVE")) {
                mostrar("Move: "+instruccion.hijos.get(0).valor);
            }
            
            else if (instruccion.valor.equals("BOMBA")) {
                mostrar("Bomba");
            }
            
            else if (instruccion.valor.equals("ESPERA")) {
                mostrar("Espera: "+instruccion.hijos.get(0).valor);
            }
            
            else if(instruccion.valor.equals("ELSE IF")){
               // if ((Boolean) evaluarExpresion(instruccion.hijos.get(0))) {//CONDICION
                    mostrar("VINO ELSE IF");
                    if(instruccion.hijos.get(1)!=null){
                        ejecutar(instruccion.hijos.get(1));//RECURSIVO Y ESTE LA MANDA  NODO CUERPO
                       
                    }
              //  i = instrucciones.hijos.size();//PARA QUE PARE Y NO SIGA CORRIENDO EN LA LISTA DE LOS DEMAS ELSE IF O ELSE
                  
               // } 
            }
            
            else if(instruccion.valor.equals("ELSE")){
                mostrar("VINO ELSE");
                if(instruccion.hijos.get(0)!=null){
                    ejecutar(instruccion.hijos.get(0));//PORQUE NO TIENE CONDICION
                } 
            }
            
            i++;
        }
    }

    private Object evaluarExpresion(Nodo nodo) throws Exception {
        Nodo auxiliar = nodo.hijos.get(0);
        
        if(auxiliar.valor.equals("+X")){
            //RETURN UN BOOLEAN
            return (Boolean)true;
        }
        else if(auxiliar.valor.equals("-X")){
            //RETURN UN BOOLEAN
            return (Boolean)true;
        }
        else if(auxiliar.valor.equals("+Y")){
            //RETURN UN BOOLEAN
            return (Boolean)true;
        }
        else if(auxiliar.valor.equals("-Y")){
            //RETURN UN BOOLEAN
            return (Boolean)true;
        }
        return null;
    }
    
    private void mostrar(String cadena){
        System.out.println(cadena);
        JOptionPane.showMessageDialog(ventana,cadena);
    }

    private void iniciarHilosEnemigos() {
        hilosEnemigos = new HiloEnemigo[listaPosicionesIniciales.size()-1];//por el del heroe
        int contadorInterno = 0;
        for(int i=0;i<listaPosicionesIniciales.size();i++){
            if("enemigo".equals(listaPosicionesIniciales.get(i).getTipo())){
                hilosEnemigos[contadorInterno] = new HiloEnemigo();
                hilosEnemigos[contadorInterno].setEnemigo(listaPosicionesIniciales.get(i));
                if(listaPosicionesIniciales.get(i).getVariable().getAlgoritmo()!=null){
                    Nodo nodo = hashMetodosNodo.buscar(listaPosicionesIniciales.get(i).getVariable().getAlgoritmo());
                    if(nodo!=null){
                        hilosEnemigos[contadorInterno].setNodoAlgoritmo(hashMetodosNodo.buscar(listaPosicionesIniciales.get(i).getVariable().getAlgoritmo()));
                    }
                }
                else{
                       hilosEnemigos[contadorInterno].setNodoAlgoritmo(null);
                }
                hilosEnemigos[contadorInterno].comenzar();
                contadorInterno++;
            }
        }
    }
    
}