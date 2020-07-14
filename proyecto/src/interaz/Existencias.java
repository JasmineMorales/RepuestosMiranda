/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interaz;

import Excepciones.*;
import clases.Base;
import java.sql.SQLException;


/**
 *
 * @author jonathan Miranda
 */
public class Existencias extends javax.swing.JDialog {

    /** Creates new form Existencias */
    private Base Conexion_DB = new Base();
    public Existencias(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
    }
    public Existencias(java.awt.Frame parent, boolean modal, String Codigo) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        try {
            tabla_Existencias.setModel(Conexion_DB.obtenerExistencias(Codigo));
        } catch (SQLException|NoSePuedeConectar ex) {
            DialogoOpcion dialogo = new DialogoOpcion(null, true, DialogoOpcion.ICONO_ERROR, "ERROR", ex.getMessage());
            dialogo.setVisible(true);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_Existencias = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        lbl_Titulo = new javax.swing.JLabel();
        btn_Aceptar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(96, 96, 96)));

        tabla_Existencias.setBackground(new java.awt.Color(240, 240, 240));
        tabla_Existencias.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        tabla_Existencias.setForeground(new java.awt.Color(64, 64, 64));
        tabla_Existencias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabla_Existencias.setGridColor(new java.awt.Color(64, 64, 64));
        tabla_Existencias.setRowHeight(24);
        tabla_Existencias.setSelectionBackground(new java.awt.Color(192, 192, 192));
        tabla_Existencias.setSelectionForeground(new java.awt.Color(64, 64, 64));
        tabla_Existencias.setShowVerticalLines(false);
        tabla_Existencias.getTableHeader().setReorderingAllowed(false);
        tabla_Existencias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabla_ExistenciasMousePressed(evt);
            }
        });
        tabla_Existencias.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabla_ExistenciasKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tabla_Existencias);

        jPanel2.setBackground(new java.awt.Color(96, 96, 96));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_Titulo.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lbl_Titulo.setForeground(new java.awt.Color(255, 255, 255));
        lbl_Titulo.setText("EXISTENCIAS");
        jPanel2.add(lbl_Titulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 130, 50));

        btn_Aceptar.setBackground(new java.awt.Color(255, 51, 51));
        btn_Aceptar.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        btn_Aceptar.setForeground(new java.awt.Color(255, 255, 255));
        btn_Aceptar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_Aceptar.setText("Aceptar");
        btn_Aceptar.setOpaque(true);
        btn_Aceptar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_AceptarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_Aceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_Aceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabla_ExistenciasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_ExistenciasMousePressed

    }//GEN-LAST:event_tabla_ExistenciasMousePressed

    private void tabla_ExistenciasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabla_ExistenciasKeyPressed

    }//GEN-LAST:event_tabla_ExistenciasKeyPressed

    private void btn_AceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_AceptarMouseClicked

        this.setVisible(false);
    }//GEN-LAST:event_btn_AceptarMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Existencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Existencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Existencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Existencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Existencias dialog = new Existencias(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btn_Aceptar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_Titulo;
    private javax.swing.JTable tabla_Existencias;
    // End of variables declaration//GEN-END:variables

}
