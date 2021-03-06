/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interaz;

import clases.*;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Roberto
 */
public class RecursosHumanos extends javax.swing.JPanel {
    private Base conexion;
    Color color_reset = new Color(96,96,96);
    Color color_set = new Color(128,128,128);
    /**
     * Creates new form Financiero
     */
    public RecursosHumanos(Base conexion) {
        initComponents();
        this.conexion=conexion;
    }
    public void desactivarBotonoes(JLabel exclusion)
    {
        exclusion.setBackground(color_set);
        if(btn_deudas!=exclusion)
            btn_deudas.setBackground(color_reset);
        if(btn_trabajadores!=exclusion)
            btn_trabajadores.setBackground(color_reset);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pn_contenido = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        Salir = new javax.swing.JLabel();
        btn_deudas = new javax.swing.JLabel();
        btn_trabajadores = new javax.swing.JLabel();
        Minimizar = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(96, 96, 96)));
        setMinimumSize(new java.awt.Dimension(940, 690));
        setPreferredSize(new java.awt.Dimension(940, 690));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pn_contenido.setPreferredSize(new java.awt.Dimension(939, 689));

        javax.swing.GroupLayout pn_contenidoLayout = new javax.swing.GroupLayout(pn_contenido);
        pn_contenido.setLayout(pn_contenidoLayout);
        pn_contenidoLayout.setHorizontalGroup(
            pn_contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 937, Short.MAX_VALUE)
        );
        pn_contenidoLayout.setVerticalGroup(
            pn_contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        add(pn_contenido, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 40, 937, 640));

        jPanel3.setBackground(new java.awt.Color(96, 96, 96));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Salir.setBackground(new java.awt.Color(96, 96, 96));
        Salir.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosSCE/cancel (2).png"))); // NOI18N
        Salir.setOpaque(true);
        Salir.setPreferredSize(new java.awt.Dimension(40, 40));
        Salir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SalirMouseClicked(evt);
            }
        });
        jPanel3.add(Salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 0, 40, -1));

        btn_deudas.setBackground(new java.awt.Color(96, 96, 96));
        btn_deudas.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        btn_deudas.setForeground(new java.awt.Color(255, 255, 255));
        btn_deudas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_deudas.setText("DEUDAS");
        btn_deudas.setName(""); // NOI18N
        btn_deudas.setOpaque(true);
        btn_deudas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_deudasMouseClicked(evt);
            }
        });
        jPanel3.add(btn_deudas, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 0, 140, 40));

        btn_trabajadores.setBackground(new java.awt.Color(96, 96, 96));
        btn_trabajadores.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        btn_trabajadores.setForeground(new java.awt.Color(255, 255, 255));
        btn_trabajadores.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_trabajadores.setText("TRABAJADORES");
        btn_trabajadores.setName(""); // NOI18N
        btn_trabajadores.setOpaque(true);
        btn_trabajadores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_trabajadoresMouseClicked(evt);
            }
        });
        jPanel3.add(btn_trabajadores, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 40));

        Minimizar.setForeground(new java.awt.Color(255, 255, 255));
        Minimizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconosSCE/minus-symbol.png"))); // NOI18N
        jPanel3.add(Minimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 0, -1, 30));

        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 40));
    }// </editor-fold>//GEN-END:initComponents

    private void btn_trabajadoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_trabajadoresMouseClicked
        desactivarBotonoes(btn_trabajadores);
        Trabajadores trabajadores= new Trabajadores(conexion);
        trabajadores.setLocation(0,0);
        trabajadores.setSize(pn_contenido.getSize());
        pn_contenido.removeAll();
        pn_contenido.add(trabajadores,BorderLayout.CENTER);
        pn_contenido.revalidate();
        pn_contenido.repaint();
    }//GEN-LAST:event_btn_trabajadoresMouseClicked

    private void btn_deudasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_deudasMouseClicked
        desactivarBotonoes(btn_deudas);
        DeudasTrabajador deudas= new DeudasTrabajador(conexion);
        deudas.setLocation(0,0);
        deudas.setSize(pn_contenido.getSize());
        pn_contenido.removeAll();
        pn_contenido.add(deudas,BorderLayout.CENTER);
        pn_contenido.revalidate();
        pn_contenido.repaint();

    }//GEN-LAST:event_btn_deudasMouseClicked

    private void SalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SalirMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_SalirMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Minimizar;
    private javax.swing.JLabel Salir;
    private javax.swing.JLabel btn_deudas;
    private javax.swing.JLabel btn_trabajadores;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel pn_contenido;
    // End of variables declaration//GEN-END:variables
}
