/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.io.File;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SeeD
 */
public class Globales extends JPanel {

    public Globales() {

    }

    public static boolean verificarSiEsNum(String cadena) {
        boolean siEs = false;
        try {
            Integer.parseInt(cadena);
            siEs = true;
        } catch (NumberFormatException exepcion) {
            siEs = false;
        }
        return siEs;
    }

    public static boolean verificarDouble(String cadena) {
        try {
            Double.parseDouble(cadena);
            return true;
        } catch (NumberFormatException exepcion) {
            return false;
        }
    }

    public static boolean verificarNumoDouble(String cadena) {
        if (verificarDouble(cadena) || verificarSiEsNum(cadena)) {
            return true;
        }
        return false;
    }

    public static File iniciarChooser(JFileChooser cho) {
        cho = new JFileChooser();
        File f;
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo de texto txt (*.txt)", "txt");
        cho.setFileFilter(filter);
        cho.setAcceptAllFileFilterUsed(false);
        cho.setMultiSelectionEnabled(false);
        cho.showOpenDialog(null);
        if (JFileChooser.APPROVE_OPTION == 0) {
            return cho.getSelectedFile();
        }
        return null;
    }

    public static DefaultTableModel cargarDatosJtable(ArrayList<Double> listDatos) {
        DefaultTableModel lm = new DefaultTableModel();
        lm.addColumn("No.");
        lm.addColumn("Dato");
        for (int i = 0; i < listDatos.size(); i++) {
            Object[] row = new Object[1];
            lm.addRow(row);
            lm.setValueAt((i + 1), i, 0);
            lm.setValueAt(listDatos.get(i), i, 1);
        }
        return lm;
    }

    public static DefaultTableModel cargarDatosJtable(ArrayList<Double> listDatos, ArrayList<Integer> elementos) {
        DefaultTableModel lm = new DefaultTableModel();
        lm.addColumn("No.");
        lm.addColumn("Elemento");
        lm.addColumn("Dato");
        for (int i = 0; i < listDatos.size(); i++) {
            Object[] row = new Object[1];
            lm.addRow(row);
            lm.setValueAt(i + 1, i, 0);
            lm.setValueAt(elementos.get(i), i, 1);
            lm.setValueAt(listDatos.get(i), i, 2);
        }
        return lm;
    }

    public static void agregarDatoTabla(JTable tabla, double num) {
        DefaultTableModel dt = (DefaultTableModel) tabla.getModel();
        Object[] row = new Object[2];
        row[0] = tabla.getRowCount() + 1;
        row[1] = num;
        dt.addRow(row);

    }

    public static void removeInList(JTable tabla, int num) {
        DefaultTableModel modelA = (DefaultTableModel) tabla.getModel();
        modelA.removeRow(num);
    }

    public static void removeList(JTable tabla) {
        DefaultTableModel modelA = (DefaultTableModel) tabla.getModel();
        modelA.setRowCount(0);
    }

    public static JInternalFrame returnInternal(JPanel panel, String tituloDelFrame) {
        JInternalFrame interno = new JInternalFrame(tituloDelFrame);//Creacion del internal frame
        interno.add(panel);//se le adiciona el panel correspondiente
        interno.setClosable(true);//se ouede cerrar
        interno.pack();
        interno.setVisible(true);
        return interno;//Retorna el internal frame 
    }

    public static void agregarColumnasFilasEstratos(JTable condiciones, JTable estratos) {
        DefaultTableModel def = (DefaultTableModel) estratos.getModel();
        def.setColumnCount(0);
        for (int i = 0; i < condiciones.getRowCount(); i++) {
            def.addColumn(condiciones.getValueAt(i, 0));
        }
    }

    public static boolean verificarConglomerado(String cadena) {
        String[] intervalo = cadena.split("-");
        if (verificarSiEsNum(intervalo[0]) && verificarSiEsNum(intervalo[1])) {
            return true;
        } else {
            return false;
        }
    }

    public static int suma(ArrayList<Integer> list) {
        return sumaRec(0, list, 0);
    }

    public static int sumaRec(int sum, ArrayList<Integer> lista, int i) {
        if (i < lista.size()) {
            sum = sum + lista.get(i);
            i++;
            return sumaRec(sum, lista, i);
        }
        return sum;
    }

    public static void cerrarVentanas(JDesktopPane escritorio) {
        JInternalFrame[] listaVentanas = escritorio.getAllFrames();
        if (listaVentanas.length > 0) {
            for (int i = 0; i < listaVentanas.length; i++) {
                listaVentanas[i].dispose();
            }
        }
    }

    public static DefaultListModel tablaAlista(ArrayList<Double> datos) {
        DefaultListModel lisModel = new DefaultListModel();
        for (int i = 0; i < datos.size(); i++) {
            lisModel.addElement((i + 1) + ")" + datos.get(i));
        }
        return lisModel;
    }
}
