/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interaz;

import Excepciones.NoSePuedeConectar;
import clases.*;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;


public class Trabajadores extends javax.swing.JPanel {
    Base conexion;
    GestorActualizaciones gestor = new GestorActualizaciones();
    ObservadorUsuario observador = new ObservadorUsuario();
    Color color_reset = new Color(96,96,96);
    Color color_set = new Color(128,128,128);

    public Trabajadores() {
        initComponents();
    }
    /**
     * Crea un nuevo panel de Trabajadores
     * @param conexion objeto para conectarse a la BD
     */
    public Trabajadores(Base conexion){
        initComponents();
        this.conexion=conexion;
        //Inicia los paneles invisibles, para que se seleccione una opcion
        listadoPanel.setVisible(false);
        generalPanel.setVisible(false);
        //Limpia el formulario
        limpiar();
        gestor.agregarObservador(observador);
    }
    /**
     * Limpia el formulario
     */
    private void limpiar(){
        //Pone los botones de opcion como negros
        ingresarButton.setBackground(color_reset);
        modificarButton.setBackground(color_reset);
        eliminarButton.setBackground(color_reset);
        verButton.setBackground(color_reset);
        //Limpia los JFields
        dpiField.setText("");
        nombreField.setText("");
        apellidoField.setText("");
        telefonoField.setText("");
        direccionField.setText("");
        //Inicializa con 0 los campos de numero
        bonoField.setText("0.00");
        comisionField.setText("0.00");
        salarioField.setText("0.00");
        //Limpia los datePickers
        inicioDate.setDate(null);
        bonoDate.setDate(null);
        //Pone la fecha de hoy a los datesPickers
        Calendar calendario= Calendar.getInstance();
        inicioDate.setDate(null/*calendario.getTime()*/);
        bonoDate.setDate(null/*calendario.getTime()*/);
        //Setea un modelo vacio a la tabla
        listadoTable.setModel(new DefaultTableModel());
        listadoPanel.setEnabled(true);
    }
    /**
     * Carga los datos de la fila seleccionada en los campos, si se esta en modo modificación
     */
    private void filaSeleccionada(){
        if(modificarButton.getBackground()==color_set){
            int seleccion=listadoTable.getSelectedRow();
            if(seleccion!=-1){
                dpiField.setText(listadoTable.getValueAt(seleccion,1).toString());
                nombreField.setText(listadoTable.getValueAt(seleccion,2).toString());
                apellidoField.setText(listadoTable.getValueAt(seleccion,3).toString());
                telefonoField.setText(listadoTable.getValueAt(seleccion,4).toString());
                comisionField.setText(listadoTable.getValueAt(seleccion,5).toString());
                direccionField.setText(listadoTable.getValueAt(seleccion,6).toString());
                salarioField.setText(listadoTable.getValueAt(seleccion,7).toString());
                bonoField.setText(listadoTable.getValueAt(seleccion,8).toString());
                String fecha;
                int dia,mes,anio;
                fecha=listadoTable.getValueAt(seleccion,9).toString();
                if(!fecha.equals("N/A")){
                    anio=Integer.parseInt(fecha.substring(0,4));
                    if(fecha.charAt(6)=='-'){
                        mes=Integer.parseInt(fecha.substring(5,6));
                        dia=Integer.parseInt(fecha.substring(7,fecha.length()));
                    }
                    else
                    {
                        mes=Integer.parseInt(fecha.substring(5,7));
                        dia=Integer.parseInt(fecha.substring(8,fecha.length()));
                    }
                    inicioDate.setDate(new Date(anio-1900,mes-1,dia));
                }else
                    inicioDate.setDate(null);
                fecha=listadoTable.getValueAt(seleccion,10).toString();
                if(!fecha.equals("N/A")){
                    anio=Integer.parseInt(fecha.substring(0,4));
                     if(fecha.charAt(6)=='-'){
                        mes=Integer.parseInt(fecha.substring(5,6));
                        dia=Integer.parseInt(fecha.substring(7,fecha.length()));
                    }
                    else
                    {
                        mes=Integer.parseInt(fecha.substring(5,7));
                        dia=Integer.parseInt(fecha.substring(8,fecha.length()));
                    }
                    bonoDate.setDate(new Date(anio-1900,mes-1,dia));
                }else
                    bonoDate.setDate(null);
            }
            dpiField.requestFocus();
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        generalPanel = new javax.swing.JPanel();
        lbl_codigo = new javax.swing.JLabel();
        nombreField = new javax.swing.JTextField();
        lbl_codigo1 = new javax.swing.JLabel();
        apellidoField = new javax.swing.JTextField();
        lbl_codigo2 = new javax.swing.JLabel();
        telefonoField = new javax.swing.JTextField();
        lbl_codigo3 = new javax.swing.JLabel();
        dpiField = new javax.swing.JTextField();
        lbl_codigo4 = new javax.swing.JLabel();
        lbl_codigo5 = new javax.swing.JLabel();
        generalButton = new javax.swing.JLabel();
        lbl_codigo7 = new javax.swing.JLabel();
        bonoField = new javax.swing.JFormattedTextField();
        comisionField = new javax.swing.JFormattedTextField();
        salarioField = new javax.swing.JFormattedTextField();
        lbl_codigo8 = new javax.swing.JLabel();
        lbl_codigo9 = new javax.swing.JLabel();
        inicioDate = new com.toedter.calendar.JDateChooser();
        bonoDate = new com.toedter.calendar.JDateChooser();
        lbl_codigo10 = new javax.swing.JLabel();
        direccionField = new javax.swing.JTextField();
        verButton = new javax.swing.JLabel();
        listadoPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listadoTable = new javax.swing.JTable();
        lbl_codigo6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        ingresarButton = new javax.swing.JLabel();
        modificarButton = new javax.swing.JLabel();
        eliminarButton = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(939, 630));
        setPreferredSize(new java.awt.Dimension(937, 646));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        generalPanel.setOpaque(false);

        lbl_codigo.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lbl_codigo.setForeground(new java.awt.Color(64, 64, 64));
        lbl_codigo.setText("Nombre:");

        nombreField.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        nombreField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(185, 185, 185)));
        nombreField.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        nombreField.setNextFocusableComponent(apellidoField);
        nombreField.setSelectedTextColor(new java.awt.Color(64, 64, 64));
        nombreField.setSelectionColor(new java.awt.Color(192, 192, 192));
        nombreField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nombreFieldFocusGained(evt);
            }
        });

        lbl_codigo1.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lbl_codigo1.setForeground(new java.awt.Color(64, 64, 64));
        lbl_codigo1.setText("Apellido:");

        apellidoField.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        apellidoField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(185, 185, 185)));
        apellidoField.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        apellidoField.setNextFocusableComponent(telefonoField);
        apellidoField.setSelectedTextColor(new java.awt.Color(64, 64, 64));
        apellidoField.setSelectionColor(new java.awt.Color(192, 192, 192));
        apellidoField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                apellidoFieldFocusGained(evt);
            }
        });

        lbl_codigo2.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lbl_codigo2.setForeground(new java.awt.Color(64, 64, 64));
        lbl_codigo2.setText("Teléfono:");

        telefonoField.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        telefonoField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(185, 185, 185)));
        telefonoField.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        telefonoField.setNextFocusableComponent(direccionField);
        telefonoField.setSelectedTextColor(new java.awt.Color(64, 64, 64));
        telefonoField.setSelectionColor(new java.awt.Color(192, 192, 192));
        telefonoField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                telefonoFieldFocusGained(evt);
            }
        });

        lbl_codigo3.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lbl_codigo3.setForeground(new java.awt.Color(64, 64, 64));
        lbl_codigo3.setText("Salario base:");

        dpiField.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        dpiField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(185, 185, 185)));
        dpiField.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        dpiField.setNextFocusableComponent(nombreField);
        dpiField.setSelectedTextColor(new java.awt.Color(64, 64, 64));
        dpiField.setSelectionColor(new java.awt.Color(192, 192, 192));
        dpiField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dpiFieldFocusGained(evt);
            }
        });

        lbl_codigo4.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lbl_codigo4.setForeground(new java.awt.Color(64, 64, 64));
        lbl_codigo4.setText("DPI:");

        lbl_codigo5.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lbl_codigo5.setForeground(new java.awt.Color(64, 64, 64));
        lbl_codigo5.setText("% de comisión:");

        generalButton.setBackground(new java.awt.Color(255, 51, 51));
        generalButton.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        generalButton.setForeground(new java.awt.Color(255, 255, 255));
        generalButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        generalButton.setText("Guardar");
        generalButton.setName(""); // NOI18N
        generalButton.setOpaque(true);
        generalButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                generalButtonMouseClicked(evt);
            }
        });

        lbl_codigo7.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lbl_codigo7.setForeground(new java.awt.Color(64, 64, 64));
        lbl_codigo7.setText("Bono incentivo:");

        bonoField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        bonoField.setText("0.00");
        bonoField.setCaretColor(new java.awt.Color(64, 64, 64));
        bonoField.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        bonoField.setNextFocusableComponent(inicioDate);
        bonoField.setSelectedTextColor(new java.awt.Color(64, 64, 64));
        bonoField.setSelectionColor(new java.awt.Color(192, 192, 192));
        bonoField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                bonoFieldFocusGained(evt);
            }
        });

        comisionField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        comisionField.setText("0.00");
        comisionField.setCaretColor(new java.awt.Color(64, 64, 64));
        comisionField.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        comisionField.setNextFocusableComponent(salarioField);
        comisionField.setSelectedTextColor(new java.awt.Color(64, 64, 64));
        comisionField.setSelectionColor(new java.awt.Color(192, 192, 192));
        comisionField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                comisionFieldFocusGained(evt);
            }
        });

        salarioField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        salarioField.setText("0.00");
        salarioField.setCaretColor(new java.awt.Color(64, 64, 64));
        salarioField.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        salarioField.setNextFocusableComponent(bonoField);
        salarioField.setSelectedTextColor(new java.awt.Color(64, 64, 64));
        salarioField.setSelectionColor(new java.awt.Color(192, 192, 192));
        salarioField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                salarioFieldFocusGained(evt);
            }
        });

        lbl_codigo8.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lbl_codigo8.setForeground(new java.awt.Color(64, 64, 64));
        lbl_codigo8.setText("Fecha de inicio:");

        lbl_codigo9.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lbl_codigo9.setForeground(new java.awt.Color(64, 64, 64));
        lbl_codigo9.setText("Fecha de bono:");

        inicioDate.setDateFormatString("yyyy/MM/dd");
        inicioDate.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        inicioDate.setNextFocusableComponent(bonoDate);

        bonoDate.setDateFormatString("yyyy/MM/dd");
        bonoDate.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        bonoDate.setNextFocusableComponent(generalButton);

        lbl_codigo10.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lbl_codigo10.setForeground(new java.awt.Color(64, 64, 64));
        lbl_codigo10.setText("Dirección:");

        direccionField.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        direccionField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(185, 185, 185)));
        direccionField.setCaretColor(new java.awt.Color(64, 64, 64));
        direccionField.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        direccionField.setNextFocusableComponent(comisionField);
        direccionField.setSelectedTextColor(new java.awt.Color(64, 64, 64));
        direccionField.setSelectionColor(new java.awt.Color(192, 192, 192));
        direccionField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                direccionFieldFocusGained(evt);
            }
        });

        javax.swing.GroupLayout generalPanelLayout = new javax.swing.GroupLayout(generalPanel);
        generalPanel.setLayout(generalPanelLayout);
        generalPanelLayout.setHorizontalGroup(
            generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(generalPanelLayout.createSequentialGroup()
                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(generalPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, generalPanelLayout.createSequentialGroup()
                                    .addComponent(lbl_codigo2)
                                    .addGap(11, 11, 11))
                                .addGroup(generalPanelLayout.createSequentialGroup()
                                    .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbl_codigo4)
                                        .addComponent(lbl_codigo1)
                                        .addComponent(lbl_codigo))
                                    .addGap(15, 15, 15)))
                            .addComponent(lbl_codigo10))
                        .addGap(44, 44, 44)
                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, generalPanelLayout.createSequentialGroup()
                                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(dpiField, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(nombreField, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(apellidoField, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(telefonoField, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(123, 123, 123)
                                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_codigo8)
                                    .addComponent(lbl_codigo3)
                                    .addComponent(lbl_codigo7)
                                    .addComponent(lbl_codigo5)
                                    .addComponent(lbl_codigo9))
                                .addGap(32, 32, 32)
                                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bonoDate, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(salarioField, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comisionField, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bonoField, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(inicioDate, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(direccionField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(generalPanelLayout.createSequentialGroup()
                        .addGap(314, 314, 314)
                        .addComponent(generalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        generalPanelLayout.setVerticalGroup(
            generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(generalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lbl_codigo4)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comisionField, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_codigo5)))
                    .addComponent(dpiField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(salarioField, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_codigo)
                        .addComponent(nombreField, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_codigo3))
                .addGap(18, 18, 18)
                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(generalPanelLayout.createSequentialGroup()
                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_codigo1)
                            .addComponent(apellidoField, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, generalPanelLayout.createSequentialGroup()
                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bonoField, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_codigo7))
                        .addGap(18, 18, 18)))
                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_codigo2)
                    .addGroup(generalPanelLayout.createSequentialGroup()
                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(inicioDate, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(telefonoField, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl_codigo8)))
                        .addGap(18, 18, 18)
                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bonoDate, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lbl_codigo9)
                                .addComponent(lbl_codigo10))
                            .addComponent(direccionField, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(generalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(generalPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 900, 270));

        verButton.setBackground(new java.awt.Color(96, 96, 96));
        verButton.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        verButton.setForeground(new java.awt.Color(255, 255, 255));
        verButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        verButton.setText("VER TRABAJADORES");
        verButton.setName(""); // NOI18N
        verButton.setOpaque(true);
        verButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verButtonMouseClicked(evt);
            }
        });
        add(verButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 0, 210, 40));

        listadoPanel.setOpaque(false);

        listadoTable.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        listadoTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        listadoTable.setGridColor(new java.awt.Color(96, 96, 96));
        listadoTable.setRowHeight(24);
        listadoTable.setSelectionBackground(new java.awt.Color(192, 192, 192));
        listadoTable.setSelectionForeground(new java.awt.Color(64, 64, 64));
        listadoTable.setShowVerticalLines(false);
        listadoTable.getTableHeader().setReorderingAllowed(false);
        listadoTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                listadoTableMousePressed(evt);
            }
        });
        listadoTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                listadoTableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(listadoTable);

        lbl_codigo6.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lbl_codigo6.setForeground(new java.awt.Color(64, 64, 64));
        lbl_codigo6.setText("Listado de trabajadores");

        javax.swing.GroupLayout listadoPanelLayout = new javax.swing.GroupLayout(listadoPanel);
        listadoPanel.setLayout(listadoPanelLayout);
        listadoPanelLayout.setHorizontalGroup(
            listadoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, listadoPanelLayout.createSequentialGroup()
                .addGap(0, 15, Short.MAX_VALUE)
                .addGroup(listadoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_codigo6)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 875, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        listadoPanelLayout.setVerticalGroup(
            listadoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, listadoPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_codigo6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91))
        );

        add(listadoPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 890, 300));

        jPanel3.setBackground(new java.awt.Color(96, 96, 96));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ingresarButton.setBackground(new java.awt.Color(96, 96, 96));
        ingresarButton.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        ingresarButton.setForeground(new java.awt.Color(255, 255, 255));
        ingresarButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ingresarButton.setText("INGRESO");
        ingresarButton.setName(""); // NOI18N
        ingresarButton.setOpaque(true);
        ingresarButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ingresarButtonMouseClicked(evt);
            }
        });
        jPanel3.add(ingresarButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        modificarButton.setBackground(new java.awt.Color(96, 96, 96));
        modificarButton.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        modificarButton.setForeground(new java.awt.Color(255, 255, 255));
        modificarButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        modificarButton.setText("MODIFICACIÓN");
        modificarButton.setName(""); // NOI18N
        modificarButton.setOpaque(true);
        modificarButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                modificarButtonMouseClicked(evt);
            }
        });
        jPanel3.add(modificarButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, 160, 40));

        eliminarButton.setBackground(new java.awt.Color(96, 96, 96));
        eliminarButton.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        eliminarButton.setForeground(new java.awt.Color(255, 255, 255));
        eliminarButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eliminarButton.setText("ELIMINACIÓN");
        eliminarButton.setName(""); // NOI18N
        eliminarButton.setOpaque(true);
        eliminarButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eliminarButtonMouseClicked(evt);
            }
        });
        jPanel3.add(eliminarButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 0, 160, 40));

        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 40));
    }// </editor-fold>//GEN-END:initComponents

    private void modificarButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_modificarButtonMouseClicked
        try {
            //limpia el formulario
            limpiar();
            //Setea el color del boton a rojo
            modificarButton.setBackground(color_set);
            //Muestra los paneles
            listadoPanel.setVisible(true);
            generalPanel.setVisible(true);
            //Obtiene la lista de Trabajadores y la setea en la tabla
            listadoTable.setModel(conexion.obtenerTrabajadoresJP());
            //Setea el texto del boton
            generalButton.setText("Actualizar datos");
            generalButton.setEnabled(true);
            //Pone el foco en la tabla
            listadoTable.requestFocus();
        } catch (SQLException|NoSePuedeConectar ex) {
            DialogoOpcion dialogo= new DialogoOpcion(null, true, DialogoOpcion.ICONO_ERROR, "Modificación", "Error:\n"+ex.toString());
            dialogo.setVisible(true);
        }
    }//GEN-LAST:event_modificarButtonMouseClicked

    private void eliminarButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eliminarButtonMouseClicked
        try {
            //Limpia el formulario
            limpiar();
            //Pone el color del boton en rojo y muestra los paneles
            eliminarButton.setBackground(color_set);
            listadoPanel.setVisible(true);
            generalPanel.setVisible(true);
            //Cambia el texto del botón
            generalButton.setText("Eliminar selección");
            generalButton.setEnabled(true);
            //Obtiene la lista de trabajadores y la pone en la tabla, pone el foco en la misma
            listadoTable.setModel(conexion.obtenerTrabajadoresJP());
            listadoTable.requestFocus();
        } catch (SQLException|NoSePuedeConectar ex) {
            DialogoOpcion dialogo= new DialogoOpcion(null, true, DialogoOpcion.ICONO_ERROR, "Eliminación", "Error:\n"+ex.toString());
            dialogo.setVisible(true);
        }
    }//GEN-LAST:event_eliminarButtonMouseClicked

    private void verButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verButtonMouseClicked
        try {
            //Limpia el formulario
            limpiar();
            //Cambia el color del boton a rojo
            verButton.setBackground(color_set);
            //Obtiene la lista de trabajadores y la pone en la tabla
            listadoTable.setModel(conexion.obtenerTrabajadoresJP());
            //Muestra los paneles
            listadoPanel.setVisible(true);
            generalPanel.setVisible(true);
            //Inhabilita el botón
            generalButton.setEnabled(false);
        } catch (SQLException|NoSePuedeConectar ex) {
            DialogoOpcion dialogo= new DialogoOpcion(null, true, DialogoOpcion.ICONO_ERROR, "Visualización", "Error:\n"+ex.toString());
            dialogo.setVisible(true);
        }
    }//GEN-LAST:event_verButtonMouseClicked

    private void listadoTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listadoTableMousePressed
        filaSeleccionada();
    }//GEN-LAST:event_listadoTableMousePressed

    private void listadoTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listadoTableKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode()== KeyEvent.VK_DOWN)
        {
            filaSeleccionada();
        }
    }//GEN-LAST:event_listadoTableKeyReleased

    private void generalButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_generalButtonMouseClicked
        try {
            //Si esta en modo ingreso, modificación o eliminación, hace cosas distintas
            //Se comprueba en que modo está
            if(ingresarButton.getBackground()==color_set){
                //Comprobamos que hayan al menos ciertos datos
                boolean telefono=telefonoField.getText().trim().length()==8;
                if((dpiField.getText().trim().length()>10)&&!dpiField.getText().trim().equals("")&&!dpiField.getText().trim().equals("N/A")&&!nombreField.getText().trim().equals("")&&!nombreField.getText().trim().equals("N/A")||!apellidoField.getText().trim().equals("")&&!apellidoField.getText().trim().equals("N/A")&&!direccionField.getText().trim().equals("")&&!direccionField.getText().trim().equals("N/A")&&telefono){
                    //Valida los datos de los textos de numero
                    bonoField.commitEdit();
                    salarioField.commitEdit();
                    comisionField.commitEdit();
                    //Crea las fechas como Strings
                    String inicio="",bono="";
                    if(inicioDate.getDate()!=null)
                        inicio=""+(1900+inicioDate.getDate().getYear())+"-"+(1+inicioDate.getDate().getMonth())+"-"+inicioDate.getDate().getDate();
                    if(bonoDate.getDate()!=null)
                        bono=""+(1900+bonoDate.getDate().getYear())+"-"+(1+bonoDate.getDate().getMonth())+"-"+bonoDate.getDate().getDate();
                    //Hace el ingreso a la BD
                    int resultado=conexion.crearTrabajador(dpiField.getText().trim(),nombreField.getText().trim(),apellidoField.getText().trim(),telefonoField.getText().trim(),Float.parseFloat(comisionField.getText()),direccionField.getText().trim(),Float.parseFloat(salarioField.getText()),Float.parseFloat(bonoField.getText()),inicio,bono);
                    //Si el resultado es 1, significa que si se ingreso, si es 0 que no (ya existe)
                    if(resultado==1){
//                        DialogoOpcion dialogo= new DialogoOpcion(null, true, DialogoOpcion.ICONO_INFORMACION, "Ingreso", "Se ha ingresado correctamente");
//                        dialogo.setVisible(true);
                        gestor.notificar(nombreField.getText()+" "+apellidoField.getText());
                        //Limpia el formulario
                        limpiar();
                    }
                    else if(resultado==0){
                        DialogoOpcion dialogo= new DialogoOpcion(null, true, DialogoOpcion.ICONO_ERROR, "Ingreso", "Este trabajador ya existe");
                        dialogo.setVisible(true);
                    }
                }else{
                    DialogoOpcion dialogo= new DialogoOpcion(null, true, DialogoOpcion.ICONO_ERROR, "Ingreso", "Debe ingresar al menos el DPI, nombre, apellido, dirección y teléfono.\nVerifique que todos los campos estén ingresados correctamente.");
                    dialogo.setVisible(true);
                    dpiField.requestFocus();
                }
            }else if(modificarButton.getBackground()==color_set){
                //compruba que hayan un mínimo de datos ingresados
                boolean telefono=telefonoField.getText().trim().length()==8;
                if((dpiField.getText().trim().length()>10)&&!dpiField.getText().trim().equals("")&&!dpiField.getText().trim().equals("N/A")&&!nombreField.getText().trim().equals("")&&!nombreField.getText().trim().equals("N/A")||!apellidoField.getText().trim().equals("")&&!apellidoField.getText().trim().equals("N/A")&&!direccionField.getText().trim().equals("")&&!direccionField.getText().trim().equals("N/A")&&telefono){
                    //Valida los datos de los campos de numero
                    bonoField.commitEdit();
                    salarioField.commitEdit();
                    comisionField.commitEdit();
                    //Crea las fechas como Strings
                    String inicio="",bono="";
                    if(inicioDate.getDate()!=null)
                        inicio=""+(1900+inicioDate.getDate().getYear())+"-"+(1+inicioDate.getDate().getMonth())+"-"+inicioDate.getDate().getDate();
                    if(bonoDate.getDate()!=null)
                        bono=""+(1900+bonoDate.getDate().getYear())+"-"+(1+bonoDate.getDate().getMonth())+"-"+bonoDate.getDate().getDate();
                    //Hace la consulta de modificación y devuelve el número de filas cambiadas (Debe de ser 1)
                    int filasMod=conexion.modificarTrabajador(Integer.parseInt(listadoTable.getValueAt(listadoTable.getSelectedRow(),0).toString()),dpiField.getText().trim(),nombreField.getText().trim(),apellidoField.getText().trim(),telefonoField.getText().trim(),Float.parseFloat(comisionField.getText()),direccionField.getText().trim(),Float.parseFloat(salarioField.getText()),Float.parseFloat(bonoField.getText()),inicio,bono);
                    DialogoOpcion dialogo= new DialogoOpcion(null, true, DialogoOpcion.ICONO_INFORMACION, "Modificación", "Se ha actualizado correctamente\nRegistros actualizados: "+filasMod);
                    dialogo.setVisible(true);
                    //Limpia el formulario
                    limpiar();
                }else{
                    DialogoOpcion dialogo= new DialogoOpcion(null, true, DialogoOpcion.ICONO_ERROR, "Modificación", "Debe ingresar al menos el DPI, nombre, apellido, dirección y teléfono.\nVerifique que todos los campos estén ingresados correctamente.");
                    dialogo.setVisible(true);
                    dpiField.requestFocus();
                }
            }else if(eliminarButton.getBackground()==color_set)
            {
                //Muestra un dialogo para confirmar si se quiere borrar el trabajador
                DialogoOpcion dialogo = new DialogoOpcion(null, true, DialogoOpcion.ICONO_INTERROGANTE,"Eliminación", "¿Está seguro de eliminar al trabajador seleccionado?");
                dialogo.setVisible(true);
                //Si se acepta, entonces lo borra
                if(dialogo.isAceptar()){
                    //Manda la orden de eliminación a la BD, devuelve el número de filas cambiadas
                    int filasMod=conexion.eliminarTrabajador(Integer.parseInt(listadoTable.getValueAt(listadoTable.getSelectedRow(),0).toString()));
                    dialogo= new DialogoOpcion(null, true, DialogoOpcion.ICONO_INFORMACION, "Eliminación", "Se ha eliminado al trabajador.\nRegistros actualizados: "+filasMod);
                    dialogo.setVisible(true);
                    //Limpia el formulario
                    limpiar();
                }
            }
        } catch (SQLException|ParseException|NoSePuedeConectar ex) {
            DialogoOpcion dialogo= new DialogoOpcion(null, true, DialogoOpcion.ICONO_ERROR, "Ingreso", "Error:\n"+ex.toString());
            dialogo.setVisible(true);
        }
        
    }//GEN-LAST:event_generalButtonMouseClicked

    private void nombreFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nombreFieldFocusGained
        nombreField.selectAll();
    }//GEN-LAST:event_nombreFieldFocusGained

    private void apellidoFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_apellidoFieldFocusGained
        apellidoField.selectAll();
    }//GEN-LAST:event_apellidoFieldFocusGained

    private void dpiFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dpiFieldFocusGained
        dpiField.selectAll();
    }//GEN-LAST:event_dpiFieldFocusGained

    private void telefonoFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_telefonoFieldFocusGained
        telefonoField.selectAll();
    }//GEN-LAST:event_telefonoFieldFocusGained

    private void comisionFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_comisionFieldFocusGained
        comisionField.selectAll();
    }//GEN-LAST:event_comisionFieldFocusGained

    private void bonoFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bonoFieldFocusGained
        bonoField.selectAll();
    }//GEN-LAST:event_bonoFieldFocusGained

    private void salarioFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_salarioFieldFocusGained
        salarioField.selectAll();
    }//GEN-LAST:event_salarioFieldFocusGained

    private void direccionFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_direccionFieldFocusGained
        direccionField.selectAll();
    }//GEN-LAST:event_direccionFieldFocusGained

    private void ingresarButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ingresarButtonMouseClicked
        //Limpia el formulario
        limpiar();
        //Setea el boton de ingresar como rojo
        ingresarButton.setBackground(color_set);
        //Muestra los paneles
        listadoPanel.setEnabled(false);
        listadoPanel.setVisible(true);
        generalPanel.setVisible(true);
        //Cambia el texto del botón
        generalButton.setText("Ingresar");
        generalButton.setEnabled(true);
        //Pone el foco en el texto de DPI
        dpiField.requestFocus();
    }//GEN-LAST:event_ingresarButtonMouseClicked
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField apellidoField;
    private com.toedter.calendar.JDateChooser bonoDate;
    private javax.swing.JFormattedTextField bonoField;
    private javax.swing.JFormattedTextField comisionField;
    private javax.swing.JTextField direccionField;
    private javax.swing.JTextField dpiField;
    private javax.swing.JLabel eliminarButton;
    private javax.swing.JLabel generalButton;
    private javax.swing.JPanel generalPanel;
    private javax.swing.JLabel ingresarButton;
    private com.toedter.calendar.JDateChooser inicioDate;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_codigo;
    private javax.swing.JLabel lbl_codigo1;
    private javax.swing.JLabel lbl_codigo10;
    private javax.swing.JLabel lbl_codigo2;
    private javax.swing.JLabel lbl_codigo3;
    private javax.swing.JLabel lbl_codigo4;
    private javax.swing.JLabel lbl_codigo5;
    private javax.swing.JLabel lbl_codigo6;
    private javax.swing.JLabel lbl_codigo7;
    private javax.swing.JLabel lbl_codigo8;
    private javax.swing.JLabel lbl_codigo9;
    private javax.swing.JPanel listadoPanel;
    private javax.swing.JTable listadoTable;
    private javax.swing.JLabel modificarButton;
    private javax.swing.JTextField nombreField;
    private javax.swing.JFormattedTextField salarioField;
    private javax.swing.JTextField telefonoField;
    private javax.swing.JLabel verButton;
    // End of variables declaration//GEN-END:variables
}
