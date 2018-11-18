/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Control.ControlConsulta;
import Control.Globales;
import static Vista.VistaPrincipal.escritorio;
import static Vista.VistaPrincipal.jtMuestra;
import static Vista.VistaPrincipal.jtTables;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author SeeD
 */
public class convConsDisc extends javax.swing.JPanel {

    /**
     * Creates new form convConsDisc
     */
    public convConsDisc(ControlConsulta control) {
        initComponents();
        this.control = control;
        jlPoblacion.setModel(Globales.tablaAlista(this.control.getConsulta().getDatos()));
        modelList = new DefaultListModel();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jlPoblacion = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jlMuestra = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jbAgregar = new javax.swing.JButton();
        jbEliminar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jbGenerateMu = new javax.swing.JButton();

        jlPoblacion.setBorder(javax.swing.BorderFactory.createTitledBorder("Poblacion"));
        jScrollPane1.setViewportView(jlPoblacion);

        jlMuestra.setBorder(javax.swing.BorderFactory.createTitledBorder("MuestraAux"));
        jlMuestra.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jScrollPane2.setViewportView(jlMuestra);

        jLabel1.setText("Seleccione los elemtos o intevalos en la lista");

        jbAgregar.setText("Incluir");
        jbAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAgregarActionPerformed(evt);
            }
        });

        jbEliminar.setText("Eliminar");
        jbEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEliminarActionPerformed(evt);
            }
        });

        jLabel2.setText(" de la poblacion que desea incluir en su muestra");

        jButton1.setText("Salir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jbGenerateMu.setText("Generar Muestra");
        jbGenerateMu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGenerateMuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jbAgregar)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jbEliminar)
                                    .addComponent(jButton1)))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jbGenerateMu))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAgregar)
                    .addComponent(jbEliminar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addComponent(jbGenerateMu, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEliminarActionPerformed
        // TODO add your handling code here:
        if (jlMuestra.getSelectedIndex() >= 0) {
            jlMuestra.setModel(control.eliminarJlmuestra(jlMuestra.getSelectedIndices(), modelList));
        } else {
            JOptionPane.showMessageDialog(this, "No Has Seleccionado Ningun Elemento en la Muestra");
        }
    }//GEN-LAST:event_jbEliminarActionPerformed

    private void jbAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAgregarActionPerformed
        // TODO add your handling code here:
        if (jlPoblacion.getSelectedIndex() >= 0) {
            if (modelList.getSize() == control.getConsulta().getn()) {
                JOptionPane.showMessageDialog(this, "El Tamaño de la muestra ya fue Suplido ");
            } else {
                jlMuestra.setModel(control.agregarAlista(jlPoblacion.getSelectedIndices(), modelList));
            }
        } else {
            JOptionPane.showMessageDialog(this, "No Has Seleccionado Ningun Elemento en la Poblacion");
        }
    }//GEN-LAST:event_jbAgregarActionPerformed

    private void jbGenerateMuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGenerateMuActionPerformed
        // TODO add your handling code here:
        if (modelList.getSize() > 0) {
            control.jlistAtabla(modelList);
            jtMuestra.setModel(Globales.cargarDatosJtable(control.getConsulta().getnDatos(), control.getConsulta().getElementos()));
            jtTables.setSelectedIndex(1);
        } else {
            JOptionPane.showMessageDialog(this, "No hay elementos para añadir a la muestra");
        }
    }//GEN-LAST:event_jbGenerateMuActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        escritorio.getSelectedFrame().dispose();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbAgregar;
    private javax.swing.JButton jbEliminar;
    private javax.swing.JButton jbGenerateMu;
    private javax.swing.JList<String> jlMuestra;
    private javax.swing.JList<String> jlPoblacion;
    // End of variables declaration//GEN-END:variables
    private ControlConsulta control;
    private DefaultListModel modelList;
}
