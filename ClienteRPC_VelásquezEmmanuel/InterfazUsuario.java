import java.awt.*;
import java.awt.event.*;
import java.awt.Color;  
import javax.swing.border.Border;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.*;
import java.util.Hashtable;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon; 
import java.io.*;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;


public class InterfazUsuario extends JFrame implements ActionListener, ChangeListener, ItemListener, Runnable
{   
    JLabel Titulo, Foco1, Foco2, Foco3, Foco4, Foco5, StatusF1, StatusF2, StatusF3, StatusF4, StatusF5, IntensidadF, TemyHumedad, NotificacionIncendio, NotificacionIntruso;
    JTextField NotiTempHora, NotiTempHumedad, NotiTempTemp, NotiIncendioHora, NotiIncendio, NotiRoboHora, NotiRobo;
    JLabel iconCasa, iconFoco, iconIncendio, iconAlarma, iconTemperatura, iconPuerta;
    JButton EncenderTF, ApagarTF, GuardarAdvertencia, AbrirGarage, CerrarGarage, ActivarAlarma, DesactivarAlarma, Salir;
    JSlider IntensidadFocos;
    JCheckBox habitacion1, habitacion2, habitacion3, habitacion4, habitacion5;  
    JComboBox estados;
    Boolean flagFocos = false;
    Boolean flagAlarma = false;
    JSlider slider1,slider2,slider3,slider4,slider5;
    public final JTextArea mainConsole = new JTextArea("");
    ProcesoTemperatura elHilo1 = new ProcesoTemperatura();
    ProcesoHumo elHilo2 = new ProcesoHumo();
    ProcesoAlarma elHilo3 = new ProcesoAlarma();
    File miDir = new File (".");

    InterfazUsuario()
    {
        super("VIVIENDA DOMOTICA");
        setSize(900, 900);
        setVisible(true);
        setLayout(null);
        Color colorVentana=new Color(244,239,211);
        Color BotonColor=new Color(225,247,213);
        this.getContentPane().setBackground(colorVentana);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        Thread t = new Thread(this);
        t.start();

        elHilo1.start();
        elHilo2.start();
        elHilo3.start();

        Titulo = new JLabel("PANEL DE CONTROL DE VIVIENDA");
        Titulo.setBounds(40, 10, 1600, 50); 
        Titulo.setForeground(Color.BLACK);
        Titulo.setFont(new Font("Times New Roman", 1, 30));
        Titulo.setHorizontalAlignment(JTextField.CENTER);
        Titulo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        add(Titulo);

        mainConsole.setFont(new Font("Times New Roman", 1, 15));
        mainConsole.setText(mainConsole.getText() +"-----------------------------------------------------------------------------------------------------------------------------\n");
        mainConsole.setText(mainConsole.getText() +"Tipo de advertencia\t Fecha y hora del suceso\t\t Que sucedio\n");
        mainConsole.setText(mainConsole.getText() +"-----------------------------------------------------------------------------------------------------------------------------\n");
        JScrollPane scrollPane = new JScrollPane(mainConsole);
        scrollPane.setBounds(980,300,670,400);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);
        

        Foco1 = new JLabel("FOCO 1");
        Foco1.setBounds(80, 80, 60, 50); 
        Foco1.setForeground(Color.BLACK);
        Foco1.setFont(new Font("Arial", 1, 15));
        Foco1.setHorizontalAlignment(JTextField.CENTER);
        add(Foco1);

        JCheckBox habitacion1 = new JCheckBox("HABITACI\u00d3N 1");  
        habitacion1.setBounds(40, 130, 150, 50);
        habitacion1.setForeground(Color.BLACK);
        habitacion1.setBackground(colorVentana);
        habitacion1.setHorizontalAlignment(JTextField.CENTER);
        habitacion1.setFont(new Font("Arial", 1, 15));
        habitacion1.setSelected(false);
        add(habitacion1);
        habitacion1.addItemListener(this);

        StatusF1 = new JLabel("APAGADO");
        StatusF1.setBounds(65, 180, 100, 50); 
        StatusF1.setBackground(Color.RED);
        StatusF1.setForeground(Color.RED);
        StatusF1.setHorizontalAlignment(JTextField.CENTER);
        StatusF1.setFont(new Font("Arial", 1, 12));
        add(StatusF1);

        Foco2 = new JLabel("FOCO 2");
        Foco2.setBounds(270, 80, 60, 50); 
        Foco2.setForeground(Color.BLACK);
        Foco2.setFont(new Font("Arial", 1, 15));
        Foco2.setHorizontalAlignment(JTextField.CENTER);
        add(Foco2);

        JCheckBox habitacion2 = new JCheckBox("HABITACI\u00d3N 2");  
        habitacion2.setBounds(230, 130, 150, 50);
        habitacion2.setForeground(Color.BLACK);
        habitacion2.setBackground(colorVentana);
        habitacion2.setHorizontalAlignment(JTextField.CENTER);
        habitacion2.setFont(new Font("Arial", 1, 15));
        habitacion2.setSelected(false);
        add(habitacion2);
        habitacion2.addItemListener(this);

        StatusF2 = new JLabel("APAGADO");
        StatusF2.setBounds(255, 180, 100, 50); 
        StatusF2.setBackground(Color.RED);
        StatusF2.setForeground(Color.RED);
        StatusF2.setHorizontalAlignment(JTextField.CENTER);
        StatusF2.setFont(new Font("Arial", 1, 12));
        add(StatusF2);

        Foco3 = new JLabel("FOCO 3");
        Foco3.setBounds(460, 80, 60, 50); 
        Foco3.setForeground(Color.BLACK);
        Foco3.setFont(new Font("Arial", 1, 15));
        Foco3.setHorizontalAlignment(JTextField.CENTER);
        add(Foco3);

        JCheckBox habitacion3 = new JCheckBox("HABITACI\u00d3N 3");  
        habitacion3.setBounds(420, 130, 150, 50);
        habitacion3.setForeground(Color.BLACK);
        habitacion3.setBackground(colorVentana);
        habitacion3.setHorizontalAlignment(JTextField.CENTER);
        habitacion3.setFont(new Font("Arial", 1, 15));
        habitacion3.setSelected(false);
        add(habitacion3);
        habitacion3.addItemListener(this);

        StatusF3 = new JLabel("APAGADO");
        StatusF3.setBounds(445, 180, 100, 50); 
        StatusF3.setBackground(Color.RED);
        StatusF3.setForeground(Color.RED);
        StatusF3.setHorizontalAlignment(JTextField.CENTER);
        StatusF3.setFont(new Font("Arial", 1, 12));
        add(StatusF3);

        Foco4 = new JLabel("FOCO 4");
        Foco4.setBounds(650, 80, 60, 50); 
        Foco4.setForeground(Color.BLACK);
        Foco4.setFont(new Font("Arial", 1, 15));
        Foco4.setHorizontalAlignment(JTextField.CENTER);
        add(Foco4);

        JCheckBox habitacion4 = new JCheckBox("HABITACI\u00d3N 4");  
        habitacion4.setBounds(610, 130, 150, 50);
        habitacion4.setForeground(Color.BLACK);
        habitacion4.setBackground(colorVentana);
        habitacion4.setHorizontalAlignment(JTextField.CENTER);
        habitacion4.setFont(new Font("Arial", 1, 15));
        habitacion4.setSelected(false);
        add(habitacion4);
        habitacion4.addItemListener(this);

        StatusF4 = new JLabel("APAGADO");
        StatusF4.setBounds(635, 180, 100, 50); 
        StatusF4.setBackground(Color.RED);
        StatusF4.setForeground(Color.RED);
        StatusF4.setHorizontalAlignment(JTextField.CENTER);
        StatusF4.setFont(new Font("Arial", 1, 12));
        add(StatusF4);

        Foco5 = new JLabel("FOCO 5");
        Foco5.setBounds(840, 80, 60, 50); 
        Foco5.setForeground(Color.BLACK);
        Foco5.setFont(new Font("Arial", 1, 15));
        Foco5.setHorizontalAlignment(JTextField.CENTER);
        add(Foco5);

        JCheckBox habitacion5 = new JCheckBox("HABITACI\u00d3N 5");  
        habitacion5.setBounds(800, 130, 150, 50);
        habitacion5.setForeground(Color.BLACK);
        habitacion5.setBackground(colorVentana);
        habitacion5.setHorizontalAlignment(JTextField.CENTER);
        habitacion5.setFont(new Font("Arial", 1, 15));
        habitacion5.setSelected(false);
        add(habitacion5);
        habitacion5.addItemListener(this);

        StatusF5 = new JLabel("APAGADO");
        StatusF5.setBounds(825, 180, 100, 50); 
        StatusF5.setBackground(Color.RED);
        StatusF5.setForeground(Color.RED);
        StatusF5.setHorizontalAlignment(JTextField.CENTER);
        StatusF5.setFont(new Font("Arial", 1, 12));
        add(StatusF5);

        EncenderTF = new JButton("Encender Todos Los Focos");
        EncenderTF.setBounds(1000, 80, 260, 50);
        EncenderTF.setForeground(Color.BLACK);
        EncenderTF.setFont(new Font("Arial", 1, 14));
        add(EncenderTF);
        EncenderTF.addActionListener(this);
        
        ApagarTF = new JButton("Apagar Todos Los Focos");
        ApagarTF.setBounds(1000, 160, 260, 50);
        ApagarTF.setForeground(Color.BLACK);
        ApagarTF.setFont(new Font("Arial", 1, 14));
        add(ApagarTF);
        ApagarTF.addActionListener(this);

        IntensidadF = new JLabel("HISTORIAL DE SUCESOS");
        IntensidadF.setBounds(1100, 250, 400, 50); 
        IntensidadF.setBackground(Color.BLACK);
        IntensidadF.setForeground(Color.BLACK);
        IntensidadF.setHorizontalAlignment(JTextField.CENTER);
        IntensidadF.setFont(new Font("Arial", 1, 20));
        add(IntensidadF);

        TemyHumedad = new JLabel("TEMPERATURA Y HUMEDAD");
        TemyHumedad.setBounds(55, 350, 230, 50); 
        TemyHumedad.setForeground(Color.BLACK);
        TemyHumedad.setFont(new Font("Times New Roman", 1, 15));
        TemyHumedad.setHorizontalAlignment(JTextField.CENTER);
        add(TemyHumedad);

        //NotiTempHora, NotiTempHumedad, NotiTempTemp, NotiIncendioHora, NotiIncendio, NotiRoboHora, NotiRobo;
        NotiTempHora = new JTextField();
        NotiTempHora.setBounds(40, 410, 260, 50); 
        NotiTempHora.setFont(new Font("Times New Roman", 1, 17));
        NotiTempHora.setHorizontalAlignment(JTextField.CENTER);
        NotiTempHora.setBackground(Color.BLACK); 
        NotiTempHora.setForeground(Color.WHITE);
        NotiTempHora.setDisabledTextColor(Color.WHITE);
        NotiTempHora.setEnabled(false);
        add(NotiTempHora);

        NotiTempTemp = new JTextField();
        NotiTempTemp.setBounds(40, 470, 260, 50); 
        NotiTempTemp.setFont(new Font("Times New Roman", 1, 17));
        NotiTempTemp.setHorizontalAlignment(JTextField.CENTER);
        NotiTempTemp.setBackground(Color.BLACK); 
        NotiTempTemp.setForeground(Color.WHITE);
        NotiTempTemp.setDisabledTextColor(Color.WHITE);
        NotiTempTemp.setEnabled(false);
        add(NotiTempTemp);

        NotiTempHumedad = new JTextField();
        NotiTempHumedad.setBounds(40, 530, 260, 50); 
        NotiTempHumedad.setFont(new Font("Times New Roman", 1, 17));
        NotiTempHumedad.setHorizontalAlignment(JTextField.CENTER);
        NotiTempHumedad.setBackground(Color.BLACK); 
        NotiTempHumedad.setForeground(Color.WHITE);
        NotiTempHumedad.setDisabledTextColor(Color.WHITE);
        NotiTempHumedad.setEnabled(false);
        add(NotiTempHumedad);

        NotificacionIncendio = new JLabel("NOTIFICACI\u00d3N DE INCENDIO");
        NotificacionIncendio.setBounds(555, 350, 230, 50); 
        NotificacionIncendio.setForeground(Color.BLACK);
        NotificacionIncendio.setFont(new Font("Times New Roman", 1, 15));
        NotificacionIncendio.setHorizontalAlignment(JTextField.CENTER);
        add(NotificacionIncendio);

        NotiIncendioHora = new JTextField();
        NotiIncendioHora.setBounds(550, 410, 260, 50); 
        NotiIncendioHora.setFont(new Font("Times New Roman", 1, 17));
        NotiIncendioHora.setHorizontalAlignment(JTextField.CENTER);
        NotiIncendioHora.setBackground(Color.BLACK); 
        NotiIncendioHora.setForeground(Color.WHITE);
        NotiIncendioHora.setDisabledTextColor(Color.WHITE);
        NotiIncendioHora.setEnabled(true);
        add(NotiIncendioHora);

        NotiIncendio = new JTextField();
        NotiIncendio.setBounds(550, 530, 260, 50); 
        NotiIncendio.setFont(new Font("Times New Roman", 1, 17));
        NotiIncendio.setHorizontalAlignment(JTextField.CENTER);
        NotiIncendio.setBackground(Color.BLACK); 
        NotiIncendio.setForeground(Color.WHITE);
        NotiIncendio.setDisabledTextColor(Color.WHITE);
        NotiIncendio.setEnabled(true);
        add(NotiIncendio);

        GuardarAdvertencia = new JButton("GUARDAR DATOS ACCIDENTES");
        GuardarAdvertencia.setBounds(40, 880, 260, 60);
        GuardarAdvertencia.setForeground(Color.BLACK);
        GuardarAdvertencia.setFont(new Font("Times New Roman", 1, 14));
        add(GuardarAdvertencia);
        GuardarAdvertencia.addActionListener(this);

        NotificacionIntruso = new JLabel("NOTIFICACI\u00d3N DE INTRUSO");
        NotificacionIntruso.setBounds(60, 600, 230, 50); 
        NotificacionIntruso.setForeground(Color.BLACK);
        NotificacionIntruso.setFont(new Font("Times New Roman", 1, 15));
        NotificacionIntruso.setHorizontalAlignment(JTextField.CENTER);
        add(NotificacionIntruso);

        //NotiRoboHora NotiRobo
        NotiRoboHora = new JTextField();
        NotiRoboHora.setBounds(40, 650, 260, 100); 
        NotiRoboHora.setFont(new Font("Times New Roman", 1, 17));
        NotiRoboHora.setHorizontalAlignment(JTextField.CENTER);
        NotiRoboHora.setBackground(Color.BLACK); 
        NotiRoboHora.setForeground(Color.WHITE);
        NotiRoboHora.setDisabledTextColor(Color.WHITE);
        NotiRoboHora.setEnabled(false);
        add(NotiRoboHora);

        NotiRobo = new JTextField();
        NotiRobo.setBounds(40, 760, 260, 100); 
        NotiRobo.setFont(new Font("Times New Roman", 1, 17));
        NotiRobo.setHorizontalAlignment(JTextField.CENTER);
        NotiRobo.setBackground(Color.BLACK); 
        NotiRobo.setForeground(Color.WHITE);
        NotiRobo.setDisabledTextColor(Color.WHITE);
        NotiRobo.setEnabled(false);
        add(NotiRobo);
        

        AbrirGarage = new JButton("Abrir Garage");
        AbrirGarage.setBounds(500, 650, 260, 60);
        AbrirGarage.setForeground(Color.BLACK);
        AbrirGarage.setFont(new Font("Times New Roman", 1, 14));
        add(AbrirGarage);
        AbrirGarage.addActionListener(this);

        CerrarGarage = new JButton("Cerrar Garage");
        CerrarGarage.setBounds(500, 760, 260, 60);
        CerrarGarage.setForeground(Color.BLACK);
        CerrarGarage.setFont(new Font("Times New Roman", 1, 14));
        add(CerrarGarage);
        CerrarGarage.addActionListener(this);

        ActivarAlarma = new JButton("Activar Alarma");
        ActivarAlarma.setBounds(320, 880, 260, 60);
        ActivarAlarma.setForeground(Color.BLACK);
        ActivarAlarma.setFont(new Font("Times New Roman", 1, 14));
        add(ActivarAlarma);
        ActivarAlarma.addActionListener(this);

        DesactivarAlarma = new JButton("Desactivar Alarma");
        DesactivarAlarma.setBounds(600, 880, 260, 60);
        DesactivarAlarma.setForeground(Color.BLACK);
        DesactivarAlarma.setFont(new Font("Times New Roman", 1, 14));
        add(DesactivarAlarma);
        DesactivarAlarma.addActionListener(this);

        Salir = new JButton("SALIR");
        Salir.setBounds(980, 865, 260, 100);
        Salir.setForeground(Color.BLACK);
        Salir.setFont(new Font("Arial", 1, 20));
        add(Salir);
        Salir.addActionListener(this);

        estados=new JComboBox();
        estados.setBounds(320,410,150,40);
        estados.setFont(new Font("Arial", 1, 18));
        add(estados);
        estados.addItem("Celsius");
        estados.addItem("Fahrenheit");
        estados.addItem("Kelvin");
        estados.addItem("Rankine");
        elHilo1.TipoDeTemperatura(estados.getSelectedItem().toString());
        estados.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                elHilo1.TipoDeTemperatura(estados.getSelectedItem().toString());
            }
        });

        //iconCasa, iconFoco, iconIncendio, iconAlarma, iconTemperatura, iconPuerta;
        iconCasa = new JLabel();
        iconCasa.setBounds(1200, 650, 600, 500); 
        iconCasa.setForeground(Color.BLACK);
        iconCasa.setFont(new Font("Arial", 1, 15));
        iconCasa.setHorizontalAlignment(JTextField.CENTER);
        iconCasa.setIcon(new ImageIcon(getClass().getResource("/Images/home.png")));
        add(iconCasa);

        iconTemperatura = new JLabel();
        iconTemperatura.setBounds(200, 250, 600, 500); 
        iconTemperatura.setForeground(Color.BLACK);
        iconTemperatura.setFont(new Font("Arial", 1, 15));
        iconTemperatura.setHorizontalAlignment(JTextField.CENTER);
        iconTemperatura.setIcon(new ImageIcon(getClass().getResource("/Images/temperatura.png")));
        add(iconTemperatura);

        iconIncendio = new JLabel();
        iconIncendio.setBounds(600, 250, 600, 500); 
        iconIncendio.setForeground(Color.BLACK);
        iconIncendio.setFont(new Font("Arial", 1, 15));
        iconIncendio.setHorizontalAlignment(JTextField.CENTER);
        iconIncendio.setIcon(new ImageIcon(getClass().getResource("/Images/casaincendio.png")));
        add(iconIncendio);

        iconAlarma = new JLabel();
        iconAlarma.setBounds(80, 520, 600, 500); 
        iconAlarma.setForeground(Color.BLACK);
        iconAlarma.setFont(new Font("Arial", 1, 15));
        iconAlarma.setHorizontalAlignment(JTextField.CENTER);
        iconAlarma.setIcon(new ImageIcon(getClass().getResource("/Images/alarma.png")));
        add(iconAlarma);

        iconPuerta = new JLabel();
        iconPuerta.setBounds(600, 520, 600, 500); 
        iconPuerta.setForeground(Color.BLACK);
        iconPuerta.setFont(new Font("Arial", 1, 15));
        iconPuerta.setHorizontalAlignment(JTextField.CENTER);
        iconPuerta.setIcon(new ImageIcon(getClass().getResource("/Images/puerta.png")));
        add(iconPuerta);

        slider1 = new JSlider();
        slider1.setBounds(80, 240, 70, 100); 
        slider1.addChangeListener(this);
        slider1.setOrientation(SwingConstants.VERTICAL);
        slider1.setEnabled(false);
        add(slider1);

        slider2 = new JSlider();
        slider2.setBounds(270, 240, 70, 100); 
        slider2.addChangeListener(this);
        slider2.setOrientation(SwingConstants.VERTICAL);
        slider2.setEnabled(false);
        add(slider2);

        slider3 = new JSlider();
        slider3.setBounds(460, 240, 70, 100); 
        slider3.addChangeListener(this);
        slider3.setOrientation(SwingConstants.VERTICAL);
        slider3.setEnabled(false);
        add(slider3);

        slider4 = new JSlider();
        slider4.setBounds(650, 240, 70, 100); 
        slider4.addChangeListener(this);
        slider4.setOrientation(SwingConstants.VERTICAL);
        slider4.setEnabled(false);
        add(slider4);

        slider5 = new JSlider();
        slider5.setBounds(840, 240, 70, 100); 
        slider5.addChangeListener(this);
        slider5.setOrientation(SwingConstants.VERTICAL);
        slider5.setEnabled(false);
        add(slider5);


        

        mainConsole.setText("");
        mainConsole.setText(mainConsole.getText() +"-----------------------------------------------------------------------------------------------------------------------------\n");
        mainConsole.setText(mainConsole.getText() +"Tipo de advertencia\t Fecha y hora del suceso\t\t Que sucedio\n");
        mainConsole.setText(mainConsole.getText() +"-----------------------------------------------------------------------------------------------------------------------------\n");

        String cadena;
        try
        {
            FileReader f = new FileReader(miDir.getCanonicalPath()+"/log.txt");
        BufferedReader b = new BufferedReader(f);
        while((cadena = b.readLine())!=null) {
            String[] parts = cadena.split("\\+");
            if(parts.length==3)
                mainConsole.setText(mainConsole.getText() + parts[0] +"\t\t"+ parts[1] +"\t\t"+ parts[2] +"\n");

        }
        }
        catch(Exception e5) 
            {
                e5.printStackTrace();
            }

        
    }
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        if (source==EncenderTF) 
        {
            System.out.println("Encender Todos Focos");
            PeticionesCliente("1-ENCENDER TODOS LOS FOCOS", "focos");
            StatusF1.setBackground(Color.GREEN);
            StatusF1.setForeground(Color.GREEN);
            StatusF1.setText("ENCENDIDO");
            StatusF2.setBackground(Color.GREEN);
            StatusF2.setForeground(Color.GREEN);
            StatusF2.setText("ENCENDIDO");
            StatusF3.setBackground(Color.GREEN);
            StatusF3.setForeground(Color.GREEN);
            StatusF3.setText("ENCENDIDO");
            StatusF4.setBackground(Color.GREEN);
            StatusF4.setForeground(Color.GREEN);
            StatusF4.setText("ENCENDIDO");
            StatusF5.setBackground(Color.GREEN);
            StatusF5.setForeground(Color.GREEN);
            StatusF5.setText("ENCENDIDO");
            slider1.setEnabled(true);
            slider2.setEnabled(true);
            slider3.setEnabled(true);
            slider4.setEnabled(true);
            slider5.setEnabled(true);
        }
        if (source==ApagarTF) 
        {
            System.out.println("Apagar Todos Focos");
            PeticionesCliente("1-APAGAR TODOS LOS FOCOS", "focos");
            StatusF1.setBackground(Color.RED);
            StatusF1.setForeground(Color.RED);
            StatusF1.setText("APAGADO");
            StatusF2.setBackground(Color.RED);
            StatusF2.setForeground(Color.RED);
            StatusF2.setText("APAGADO");
            StatusF3.setBackground(Color.RED);
            StatusF3.setForeground(Color.RED);
            StatusF3.setText("APAGADO");
            StatusF4.setBackground(Color.RED);
            StatusF4.setForeground(Color.RED);
            StatusF4.setText("APAGADO");
            StatusF5.setBackground(Color.RED);
            StatusF5.setForeground(Color.RED);
            StatusF5.setText("APAGADO");
            slider1.setEnabled(false);
            slider2.setEnabled(false);
            slider3.setEnabled(false);
            slider4.setEnabled(false);
            slider5.setEnabled(false);
        }
        if (source==GuardarAdvertencia) 
        {
            
            try 
            {
                if(!NotiIncendioHora.getText().equals("") && !NotiIncendio.getText().equals(""))
                {
                    FileWriter fichero = null;
                    PrintWriter pw = null;
                    try
                    {
                        fichero = new FileWriter(miDir.getCanonicalPath()+"/log.txt",true);
                        pw = new PrintWriter(fichero);

                        pw.println("GASES+"+NotiIncendioHora.getText()+"+"+NotiIncendio.getText());

                    } catch (Exception e3) {
                        e3.printStackTrace();
                    } finally {
                       try {
                       if (null != fichero)
                          fichero.close();
                       } catch (Exception e2) {
                          e2.printStackTrace();
                       }
                    }
                NotiIncendioHora.setText("");
                NotiIncendio.setText("");
                }
                if(!NotiRoboHora.getText().equals("") && !NotiRobo.getText().equals(""))
                {
                    FileWriter fichero = null;
                    PrintWriter pw = null;
                    try
                    {
                        fichero = new FileWriter(miDir.getCanonicalPath()+"/log.txt",true);
                        pw = new PrintWriter(fichero);
                        pw.println("ROBO+"+NotiRobo.getText()+"+"+NotiRoboHora.getText()+"\n");
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    } finally {
                       try {
                       if (null != fichero)
                          fichero.close();
                       } catch (Exception e2) {
                          e2.printStackTrace();
                       }
                    }
                    NotiRoboHora.setText("");
                    NotiRobo.setText("");
                }
                else
                {
                    JOptionPane.showMessageDialog(new JFrame(), "SIN DATOS", "Dialog", JOptionPane.ERROR_MESSAGE);
                }

                mainConsole.setText("");
                mainConsole.setText(mainConsole.getText() +"-----------------------------------------------------------------------------------------------------------------------------\n");
                mainConsole.setText(mainConsole.getText() +"Tipo de advertencia\t Fecha y hora del suceso\t\t Que sucedio\n");
                mainConsole.setText(mainConsole.getText() +"-----------------------------------------------------------------------------------------------------------------------------\n");

                String cadena;

                FileReader f = new FileReader(miDir.getCanonicalPath()+"/log.txt");
                BufferedReader b = new BufferedReader(f);
                while((cadena = b.readLine())!=null) {
                    String[] parts = cadena.split("\\+");
                    if(parts.length==3)
                        mainConsole.setText(mainConsole.getText() + parts[0] +"\t\t"+ parts[1] +"\t\t"+ parts[2] +"\n");
                }
                b.close();

            }
            catch(Exception e5) 
            {
                e5.printStackTrace();
            }
              
        }
        if (source==AbrirGarage) 
        {
            System.out.println("Abriendo Garage");
            PeticionesCliente("2-ABRIR GARAGE", "garage");
        }
        if (source==CerrarGarage) 
        {
            System.out.println("Cerrar Garage");
            PeticionesCliente("2-CERRAR GARAGE", "garage");
        }
        if (source==ActivarAlarma) 
        {
            System.out.println("Activar Alarma de Casa");
            PeticionesCliente("6-ACTIVAR ALARMA", "alarma");   

        }
        if (source==DesactivarAlarma) 
        {
            System.out.println("Desactivar Alarma de Casa"); 
            PeticionesCliente("8-DESACTIVAR ALARMA", "intruso");   
            elHilo2.setValueAlarma();
            flagAlarma = false;
            JOptionPane.showMessageDialog(new JFrame(), "SE DESACTIVO LA ALARMA", "Dialog", JOptionPane.ERROR_MESSAGE);
            AbrirGarage.setEnabled(true);
            CerrarGarage.setEnabled(true);
            PeticionesCliente("8-DESACTIVAR ALARMA", "intruso");   
            elHilo2.setValueAlarma();
            flagAlarma = false;
            PeticionesCliente("8-DESACTIVAR ALARMA", "intruso");   
            elHilo2.setValueAlarma();
            flagAlarma = false;
        }
        if (source==Salir) 
        {   
                dispose();
        }
    }
    public void stateChanged(ChangeEvent ev) 
    { 
            if(ev.getSource() == slider1)
            {
                PeticionesCliente("7-FOCO1-"+slider1.getValue(), "regulacion");
                StatusF1.setBackground(Color.GREEN);
                StatusF1.setForeground(Color.GREEN);
                StatusF1.setText("ENCENDIDO "+slider1.getValue());
            }
            if(ev.getSource() == slider2)
            {
                PeticionesCliente("7-FOCO2-"+slider2.getValue(), "regulacion");
                StatusF2.setBackground(Color.GREEN);
                StatusF2.setForeground(Color.GREEN);
                StatusF2.setText("ENCENDIDO "+slider2.getValue());
            }
            if(ev.getSource() == slider3)
            {
                PeticionesCliente("7-FOCO3-"+slider3.getValue(), "regulacion");
                StatusF3.setBackground(Color.GREEN);
                StatusF3.setForeground(Color.GREEN);
                StatusF3.setText("ENCENDIDO "+slider3.getValue());
            }
            if(ev.getSource() == slider4)
            {
                PeticionesCliente("7-FOCO4-"+slider4.getValue(), "regulacion");
                StatusF4.setBackground(Color.GREEN);
                StatusF4.setForeground(Color.GREEN);
                StatusF4.setText("ENCENDIDO "+slider4.getValue());
            }
            if(ev.getSource() == slider5)
            {
                PeticionesCliente("7-FOCO5-"+slider5.getValue(), "regulacion");
                StatusF5.setBackground(Color.GREEN);
                StatusF5.setForeground(Color.GREEN);
                StatusF5.setText("ENCENDIDO "+slider5.getValue());
            }
    } 
    public void itemStateChanged(ItemEvent e) 
    {
        JCheckBox cb = (JCheckBox) e.getItem();
        if(e.getStateChange() == ItemEvent.SELECTED)
        {
            if(cb.getText()=="HABITACI\u00d3N 1")
            {
                    PeticionesCliente("1-ENCENDER FOCO HABITACI\u00d3N 1", "focos");
                    slider1.setEnabled(true);
            }
            if(cb.getText()=="HABITACI\u00d3N 2")
            {
                    PeticionesCliente("1-ENCENDER FOCO HABITACI\u00d3N 2", "focos");
                    slider2.setEnabled(true);
            }
            if(cb.getText()=="HABITACI\u00d3N 3")
            {
                    PeticionesCliente("1-ENCENDER FOCO HABITACI\u00d3N 3", "focos");
                    slider3.setEnabled(true);
            }
            if(cb.getText()=="HABITACI\u00d3N 4")
            {
                    PeticionesCliente("1-ENCENDER FOCO HABITACI\u00d3N 4", "focos");
                    slider4.setEnabled(true);
            }
            if(cb.getText()=="HABITACI\u00d3N 5")
            {
                    PeticionesCliente("1-ENCENDER FOCO HABITACI\u00d3N 5", "focos");
                    slider5.setEnabled(true);
            }
        }
        else
        {
            if(cb.getText()=="HABITACI\u00d3N 1")
            {
                
                    PeticionesCliente("1-APAGAR FOCO HABITACI\u00d3N 1", "focos");
                    slider1.setEnabled(false);
            }
            if(cb.getText()=="HABITACI\u00d3N 2")
            {
                
                    PeticionesCliente("1-APAGAR FOCO HABITACI\u00d3N 2", "focos");
                    slider2.setEnabled(false);
            }
            if(cb.getText()=="HABITACI\u00d3N 3")
            {
                
                    PeticionesCliente("1-APAGAR FOCO HABITACI\u00d3N 3", "focos");
                    slider3.setEnabled(false);
            }
            if(cb.getText()=="HABITACI\u00d3N 4")
            {
                
                    PeticionesCliente("1-APAGAR FOCO HABITACI\u00d3N 4", "focos");
                    slider4.setEnabled(false);
            }
            if(cb.getText()=="HABITACI\u00d3N 5")
            {
                    PeticionesCliente("1-APAGAR FOCO HABITACI\u00d3N 5", "focos");
                    slider5.setEnabled(false);
            }
        }
    }
    public void PeticionesCliente(String Mensaje, String NombreQueue)
    {
        try(ClienteJAVA funcion = new ClienteJAVA()) {
            System.out.println(" [x] Solicitando que(" + Mensaje + ")");
            String response = funcion.call(Mensaje, NombreQueue);
            System.out.println(" [.] Que sucedio: '" + response + "'");
            String[] datos = response.split("\\+");
            if(datos.length==3)
            {
                NotiRoboHora.setText(datos[0]+" - "+datos[2]);
                NotiRobo.setText(datos[1]);
            }
            if(response.equals("FOCO 1 ENCENDIDO"))
            {
                StatusF1.setBackground(Color.GREEN);
                StatusF1.setForeground(Color.GREEN);
                StatusF1.setText("ENCENDIDO");
            }
            if(response.equals("FOCO 2 ENCENDIDO"))
            {
                StatusF2.setBackground(Color.GREEN);
                StatusF2.setForeground(Color.GREEN);
                StatusF2.setText("ENCENDIDO");
            }
            if(response.equals("FOCO 3 ENCENDIDO"))
            {
                StatusF3.setBackground(Color.GREEN);
                StatusF3.setForeground(Color.GREEN);
                StatusF3.setText("ENCENDIDO");
            }
            if(response.equals("FOCO 4 ENCENDIDO"))
            {
                StatusF4.setBackground(Color.GREEN);
                StatusF4.setForeground(Color.GREEN);
                StatusF4.setText("ENCENDIDO");
            }
            if(response.equals("FOCO 5 ENCENDIDO"))
            {
                StatusF5.setBackground(Color.GREEN);
                StatusF5.setForeground(Color.GREEN);
                StatusF5.setText("ENCENDIDO");
            }
            if(response.equals("FOCO 1 APAGADO"))
            {
                StatusF1.setBackground(Color.RED);
                StatusF1.setForeground(Color.RED);
                StatusF1.setText("APAGADO");
            }
            if(response.equals("FOCO 2 APAGADO"))
            {
                StatusF2.setBackground(Color.RED);
                StatusF2.setForeground(Color.RED);
                StatusF2.setText("APAGADO");
            }
            if(response.equals("FOCO 3 APAGADO"))
            {
                StatusF3.setBackground(Color.RED);
                StatusF3.setForeground(Color.RED);
                StatusF3.setText("APAGADO");
            }
            if(response.equals("FOCO 4 APAGADO"))
            {
                StatusF4.setBackground(Color.RED);
                StatusF4.setForeground(Color.RED);
                StatusF4.setText("APAGADO");
            }
            if(response.equals("FOCO 5 APAGADO"))
            {
                StatusF5.setBackground(Color.RED);
                StatusF5.setForeground(Color.RED);
                StatusF5.setText("APAGADO");
            }
            if(response.equals("ALARMA NO ACTIVADA LADRON"))
            {
                flagAlarma = false;
                JOptionPane.showMessageDialog(new JFrame(), "NO SE ACTIVO LA ALARMA", "Dialog", JOptionPane.ERROR_MESSAGE);
            }
            if(response.equals("ALARMA ACTIVADA LADRON"))
            {
                flagAlarma = true;
                AbrirGarage.setEnabled(false);
                CerrarGarage.setEnabled(false);
                JOptionPane.showMessageDialog(new JFrame(), "SE ACTIVO LA ALARMA", "Dialog", JOptionPane.ERROR_MESSAGE);
            }
            } catch (IOException | TimeoutException | InterruptedException err) {
                err.printStackTrace();
            }
    }
    public void run()
    {
        while(true)
        {
            try
            {
                Thread.sleep(1000);
                NotiTempHora.setText(elHilo1.getValueHora());
                NotiTempHumedad.setText(elHilo1.getValueTemperatura());
                NotiTempTemp.setText(elHilo1.getValueHumedad());
                if(flagAlarma == true)
                {
                    PeticionesCliente("3-ACTIVA ALARMA", "alarmaLadron");
                }
                if(elHilo2.getValueHumoHora().equals("") || elHilo2.getValueHumo().equals(""))
                {
                    if(elHilo2.getValueAlarma().equals("ACTIVAR"))
                    {
                        PeticionesCliente("8-ACTIVAR ALARMA", "intruso");
                    }
                    
                }
                else
                {
                    NotiIncendioHora.setText(elHilo2.getValueHumoHora());
                    NotiIncendio.setText(elHilo2.getValueHumo());
                }

            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
        }
    }
}