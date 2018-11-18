/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import static Control.Globales.agregarColumnasFilasEstratos;
import Modelo.Consulta;
import static Vista.VistaPrincipal.jtMuestra;
import static Vista.VistaPrincipal.jtTables;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author SeeD
 */
public class ControlConsulta {

    private Consulta consulta;

    public ControlConsulta() {
        this.consulta = new Consulta();
    }

    public Consulta getConsulta() {
        return this.consulta;
    }
//--------------------------------------------------------------------------------------------------

    public boolean cargarDatos(File archivo) throws FileNotFoundException, IOException {
        getConsulta().getDatos().clear();
        boolean sinProblemas = true;
        if (archivo != null) {
            FileReader fl = new FileReader(archivo);
            BufferedReader bu = new BufferedReader(fl);
            String linea;
            while ((linea = bu.readLine()) != null) {
                for (String da : linea.split(" ")) {
                    if (Globales.verificarSiEsNum(da)) {
                        consulta.getDatos().add(Double.parseDouble(da));
                        consulta.setN(consulta.getN() + 1);
                    } else {
                        sinProblemas = false;
                    }
                }
            }
        } else {
            return false;
        }
        return sinProblemas;
    }
//--------------------------------------------------------------------------------------------------
    //--------------- CALCULA DATOS DE LA POBLACION

    public void calcularMedia() {
        double media = 0;
        for (double dato : this.consulta.getDatos()) {
            media += dato;
        }
        this.consulta.setMedia(media / this.consulta.getN());
    }

    public void calcularVarianza() {
        double var = 0;
        for (double dat : this.consulta.getDatos()) {
            var += Math.pow((dat - this.consulta.getMedia()), 2);
        }
        this.consulta.setVarianza(var / this.consulta.getN());
    }

    public void calcularDesviacion() {
        this.consulta.setDesviacion(Math.sqrt(this.consulta.getVarianza()));
    }

    public void calcularDatosN() {
        calcularMedia();
        calcularVarianza();
        calcularDesviacion();
    }
//--------------------------------------------------------------------------------------------------
    //--------------- CALCULA DATOS DE LA MUESTRA

    public void calcularMedian() {
        double media = 0;
        for (double dato : this.consulta.getnDatos()) {
            media += dato;
        }
        this.consulta.setnMedia(media / this.consulta.getn());
        System.out.println("la Fucking media: " + this.consulta.getnMedia());
    }

    public void calcularVarianzan() {
        double var = 0;
        for (double dat : this.consulta.getnDatos()) {
            var += Math.pow((dat - this.consulta.getnMedia()), 2);
        }
        this.consulta.setnVarianza(var / this.consulta.getn());
    }

    public void calcularDesviacionn() {
        this.consulta.setnDesviacion(Math.sqrt(this.consulta.getnVarianza()));
    }

    public void calcularDatosn() {
        calcularMedian();
        calcularVarianzan();
        calcularDesviacionn();
    }

    //--------------------------------------------------------------------------------------------------
    //----------- CONTROL DE MUESTREOS
    public void aleatorioSimple() {
        ArrayList<Double> listaAux = (ArrayList<Double>) getConsulta().getDatos();
        ArrayList<Integer> numsIndex = new ArrayList<>();
        for (int i = 0; i < listaAux.size(); i++) {
            numsIndex.add(i);
        }
        getConsulta().getnDatos().clear();
        getConsulta().getElementos().clear();
        Random ran = new Random();
        int i = 0;
        while (i < getConsulta().getn()) {
            int index = ran.nextInt(numsIndex.size());
            getConsulta().getnDatos().add(listaAux.get(numsIndex.get(index)));
            getConsulta().getElementos().add(numsIndex.get(index) + 1);
            numsIndex.remove(index);
            i++;
        }
    }

    public void aleatorioSistematico() {
        getConsulta().getnDatos().clear();
        getConsulta().getElementos().clear();
        int k = getConsulta().getN() / getConsulta().getn();
        int l = new Random().nextInt(k);
        for (int i = 0; i < getConsulta().getn(); i++) {
            if (l + (i * k) < getConsulta().getN()) {
                getConsulta().getnDatos().add(getConsulta().getDatos().get((l + (i * k))));
                getConsulta().getElementos().add((l + (i * k)) + 1);
            } else {
                JOptionPane.showMessageDialog(null, "El tamaño de la Muestra es muy Grande Para Un Muestreo Sistematico\nGenere Una Muestra Mas Pequeña");
                break;
            }
        }
    }

    public void calcularPoblacionTotalStratos(JTable stratos, int nstratos) {
        int nTotal = 0;
        int[] nXstrato = new int[nstratos];
        for (int k = 0; k < nstratos; k++) {
            for (int j = 0; j < stratos.getRowCount(); j++) {
                if (stratos.getValueAt(j, k) != null) {
                    nXstrato[k]++;
                    nTotal++;
                } else {
                    break;
                }
            }
        }
        if (nTotal < getConsulta().getn()) {
            JOptionPane.showMessageDialog(null, "El tamaño de los estratos no cumple para generar un muestra de este tamaño " + getConsulta().getn());
        } else {
            generarMuestraEstratificada(nTotal, nXstrato, stratos);
            jtTables.setSelectedIndex(1);
            jtMuestra.setModel(Globales.cargarDatosJtable(getConsulta().getnDatos(), getConsulta().getElementos()));
            jtMuestra.getColumnModel().getColumn(0).setPreferredWidth(30);
        }
    }

    public void generarMuestraEstratificada(int nTotal, int[] nCadaStrato, JTable stratos) {

        double[] porcentajesXstrato = new double[nCadaStrato.length];
        Random ran = new Random();
        ArrayList<Integer> indicesParaEscoger = new ArrayList<>();
        getConsulta().getnDatos().clear();
        getConsulta().getElementos().clear();
        for (int i = 0; i < nCadaStrato.length; i++) {
            porcentajesXstrato[i] = (double) nCadaStrato[i] / (double) nTotal;
            int nStratoParaLaMuestra = (int) ((double) (Math.round(porcentajesXstrato[i] * 100d) / 100d) * getConsulta().getn());
            indicesParaEscoger.clear();
            for (int l = 0; l < nCadaStrato[i]; l++) {
                indicesParaEscoger.add(l);
            }
            for (int j = 0; j < nStratoParaLaMuestra + 1; j++) {
                if (getConsulta().getnDatos().size() < getConsulta().getn()) {
                    int index = ran.nextInt(indicesParaEscoger.size());
                    String datoCompleto = (String) stratos.getValueAt(indicesParaEscoger.get(index), i);
                    double[] datos = new double[2];
                    String[] txt = datoCompleto.split("\\)");
                    datos[0] = Double.parseDouble(txt[0].replace(" ", ""));
                    datos[1] = Double.parseDouble(txt[1].replace(" ", ""));
                    getConsulta().getnDatos().add(datos[1]);
                    getConsulta().getElementos().add((int) datos[0]);
                    indicesParaEscoger.remove(index);
                }
            }
        }
    }
//---------------------------------------------------------------------------------------------------------------

    public boolean validarCondicionEstratos(JTable tablaCond) {
        boolean isCorrecto = true;
        DefaultTableModel def = (DefaultTableModel) tablaCond.getModel();
        for (int i = 0; i < def.getRowCount(); i++) {
            if (def.getValueAt(i, 0) != null) {
                String con = ((String) def.getValueAt(i, 0));
                if (!con.equals("")) {
                    if (con.contains("-")) {
                        String[] cond = con.split("-");
                        if (!Globales.verificarNumoDouble(cond[0]) || !Globales.verificarNumoDouble(cond[1])) {
                            isCorrecto = false;
                        }
                    } else {
                        if (!Globales.verificarNumoDouble(con)) {
                            isCorrecto = false;
                        }
                    }
                } else {
                    isCorrecto = false;
                }
            } else {
                isCorrecto = false;
            }
        }
        return isCorrecto;
    }

    public void generarEstratos(JTable condiciones, JTable estratos) {
        agregarColumnasFilasEstratos(condiciones, estratos);
        DefaultTableModel defEstra = (DefaultTableModel) estratos.getModel();
        DefaultTableModel defCond = (DefaultTableModel) condiciones.getModel();
        TableColumn columna;
        defEstra.setRowCount(0);
        getConsulta().getElementos().clear();
        ArrayList<Double> listAux = getConsulta().getDatos();
        for (int i = 0; i < condiciones.getRowCount(); i++) {
            int indiceFila = 0;
            String numsA = (String) defCond.getValueAt(i, 0);
            System.out.println(numsA + "\n");
            double[] nums = new double[2];
            if (numsA.split("-").length > 1) {
                nums[0] = Double.parseDouble(numsA.split("-")[0]);
                nums[1] = Double.parseDouble(numsA.split("-")[1]);
                for (int k = 0; k < listAux.size(); k++) {
                    if (nums[0] <= listAux.get(k) && listAux.get(k) < nums[1]) {
                        if (estratos.getRowCount() - 1 < indiceFila) {
                            defEstra.addRow(new Object[condiciones.getRowCount()]);
                        }
                        estratos.setValueAt(String.format("% 4d) ", (k + 1)) + listAux.get(k), indiceFila, i);
                        indiceFila++;
                    }
                }
                columna = estratos.getColumnModel().getColumn(i);
                columna.setHeaderValue(defEstra.getColumnName(i) + "(" + indiceFila + ")");
                estratos.getTableHeader().repaint();
                indiceFila = 0;
            } else {
                nums[0] = Double.parseDouble((String) condiciones.getValueAt(i, 0));
                for (int j = 0; j < listAux.size(); j++) {
                    if (listAux.get(j) == nums[0]) {
                        if (estratos.getRowCount() - 1 < indiceFila) {
                            defEstra.addRow(new Object[condiciones.getRowCount()]);
                        }
                        estratos.setValueAt(String.format("% 4d) ", (j + 1)) + listAux.get(j), indiceFila, i);
                        indiceFila++;
                    }
                }
                columna = estratos.getColumnModel().getColumn(i);
                columna.setHeaderValue(defEstra.getColumnName(i) + "(" + indiceFila + ")");
                estratos.getTableHeader().repaint();
            }

        }
    }

    //--------------------------------------------------------------------------------------------------
    public void aleatorioConglomerados(JTable conglomerados) {
        DefaultTableModel defT = (DefaultTableModel) conglomerados.getModel();
        int n = 0;
        Random ran = new Random();
        ArrayList<Integer> nXConglomerados = new ArrayList<>();
        nXConglomerados = calcularPoblacionTotalConglomerados(conglomerados);
        ArrayList<Integer> conglomEscogidos = new ArrayList<Integer>();
        getConsulta().getnDatos().clear();
        getConsulta().getElementos().clear();
        if (Globales.suma(nXConglomerados) < getConsulta().getn()) {
            JOptionPane.showMessageDialog(null, "Los elementos dentro de los conglomerados no satisfacen el tamaño de la Muestra");
        } else {
            if (Globales.suma(nXConglomerados) == getConsulta().getn()) {
                for (int i = 0; i < conglomerados.getColumnCount(); i++) {
                    int k = 0;
                    while (k < defT.getRowCount() && defT.getValueAt(k, i) != null) {
                        String[] nums = ((String) defT.getValueAt(k, i)).split("\\)");
                        getConsulta().getnDatos().add(Double.parseDouble(nums[1]));
                        getConsulta().getElementos().add(Integer.parseInt(nums[0]));
                        k++;
                    }
                }
            } else {
                int tamañ = nXConglomerados.size() - 1;
                ArrayList<Integer> indices = new ArrayList<>();
                for (int i = 0; i <= tamañ; i++) {
                    indices.add(i);
                }
                while (n < getConsulta().getn() && tamañ > 0) {
                    int index = ran.nextInt(tamañ);
                    conglomEscogidos.add(indices.get(index));
                    n += nXConglomerados.get(conglomEscogidos.get(conglomEscogidos.size() - 1));
                    indices.remove(index);
                    tamañ--;
                }

                for (int conglomAux : conglomEscogidos) {
                    double nPorcen = (double) nXConglomerados.get(conglomAux) / (double) n;
                    int cuantos = (int) (nPorcen * getConsulta().getn());
                    ArrayList<Integer> indicesDeM = new ArrayList<>();
                    for (int k = 0; k < nXConglomerados.get(conglomAux); k++) {
                        indicesDeM.add(k);
                    }
                    for (int i = 0; i < cuantos + 1; i++) {
                        if (getConsulta().getnDatos().size() == getConsulta().getn()) {
                            break;
                        } else {
                            int index = ran.nextInt(indicesDeM.size());
                            String[] datos = ((String) defT.getValueAt(indicesDeM.get(index), conglomAux)).split("\\)");
                            getConsulta().getnDatos().add(Double.parseDouble(datos[1]));
                            getConsulta().getElementos().add(Integer.parseInt(datos[0]));
                            indicesDeM.remove(index);
                        }
                    }

                }

            }

        }
    }

    public ArrayList<Integer> calcularPoblacionTotalConglomerados(JTable stratos) {
        ArrayList<Integer> nXConglom = new ArrayList<>();
        for (int k = 0; k < stratos.getColumnCount(); k++) {
            int n = 0;
            for (int j = 0; j < stratos.getRowCount() - 1; j++) {
                if (stratos.getValueAt(j, k) != null) {
                    n++;
                } else {
                    break;
                }
            }
            nXConglom.add(n);
        }
        return nXConglom;
    }

    public DefaultTableModel agregarConglomerado(JTable taGrupos, int[] inter, int column) {
        DefaultTableModel defT = (DefaultTableModel) taGrupos.getModel();
        int fila = 0;
        for (int i = inter[0] - 1; i < inter[1]; i++) {
            if (defT.getRowCount() - 1 < fila) {
                defT.addRow(new Object[taGrupos.getColumnCount()]);
            }
            defT.setValueAt((i + 1) + ")" + getConsulta().getDatos().get(i), fila, column);
            fila++;
        }
        return defT;
    }

    //--------------------------------------------------------------------------------------------------
    public void removerDatoN(int num) {
        getConsulta().getDatos().remove(num);
    }

    //-------------------------------------------------------------------------------------------------------
    public DefaultListModel agregarAlista(int[] indices, DefaultListModel modelList) {
        for (int index : indices) {
            modelList.addElement((index + 1) + ")" + getConsulta().getDatos().get(index));
        }
        return modelList;
    }

    public DefaultListModel eliminarJlmuestra(int[] indices, DefaultListModel modelList) {
        modelList.removeRange(indices[0], indices[indices.length - 1]);
        return modelList;
    }

    public void jlistAtabla(DefaultListModel modelList) {
        getConsulta().getnDatos().clear();
        getConsulta().getElementos().clear();
        for (int i = 0; i < modelList.size(); i++) {
            if (i < getConsulta().getn()) {
                String dat = ((String) modelList.get(i)).split("\\)")[0];
                int ind = Integer.parseInt(dat) - 1;
                getConsulta().getElementos().add(ind + 1);
                getConsulta().getnDatos().add(getConsulta().getDatos().get(ind));
            } else {
                break;
            }
        }
    }

    //---------------------------------------------------------------------------------------------------------------
    public DefaultTableModel generarGrupo(JTable taGrupos, int[] caract, int colom) {
        DefaultTableModel taModel = (DefaultTableModel) taGrupos.getModel();
        int fila = 0;
        for (int i = 0; i < getConsulta().getDatos().size(); i++) {
            double dato = getConsulta().getDatos().get(i);
            if (dato >= caract[0] && dato < caract[1]) {
                if (taModel.getRowCount() - 1 < fila) {
                    taModel.addRow(new Object[taModel.getColumnCount()]);
                }
                taModel.setValueAt((i + 1) + ")" + dato, fila, colom);
                fila++;
            }
        }
        return taModel;
    }

    public DefaultTableModel generarGrupo(JTable taGrupos, int caract, int colom) {
        DefaultTableModel taModel = (DefaultTableModel) taGrupos.getModel();
        int fila = 0;
        for (int i = 0; i < getConsulta().getDatos().size(); i++) {
            double dato = getConsulta().getDatos().get(i);
            if (dato == caract) {
                if (taModel.getRowCount() - 1 < fila) {
                    taModel.addRow(new Object[taModel.getColumnCount()]);
                }
                taModel.setValueAt((i + 1) + ")" + dato, fila, colom);
                fila++;
            }
        }
        return taModel;
    }

    public void agregarAlist(DefaultListModel defList, int[] colums, int[] rows, JTable tablaGru) {

        for (int colum : colums) {
            for (int row : rows) {
                defList.addElement(tablaGru.getValueAt(row, colum));
            }
        }
    }

    //-------------------------------------------------------------------------------------------------------------
    public double calcularZ(String dat, String signo) throws IOException {
        System.out.println("dato : " + dat + "   signo : " + signo);
        double dato = Double.parseDouble(dat);
        int linea;
        int colum;
        String url;
        if (dato < -4.49) {
            if (signo.equals("<")) {
                return 0.0;
            }
            return 1.0;
        } else {
            if (dato > 4.49) {
                if (signo.equals("<")) {
                    return 1.0;
                }
                return 0.0;
            } else {
                if (dato < 0) {
                    url = "-z.txt";
                    linea = Integer.parseInt((dat.substring(1, 4).replace(".", "")));
                    colum = Integer.parseInt(dat.substring(4));
                } else {
                    url = "z.txt";
                    linea = Integer.parseInt((dat.substring(0, 3).replace(".", "")));
                    colum = Integer.parseInt(dat.substring(3));
                }
                if (signo.equals("<")) {
                    return Double.parseDouble(Files.readAllLines(Paths.get(url)).get(linea + 1).split("   ")[colum + 1]);
                }
                if (signo.equals(">")) {
                    return Math.round((1.0 - Double.parseDouble((Files.readAllLines(Paths.get(url))).get(linea + 1).split("   ")[colum + 1])) * 10000d) / 10000d;
                }
            }
        }
        return 0.0;
    }

    public double calcularZporIntervalo(double numUno, double numDos, String sigUno, String sigDos, String limitInferior, String limtSuperior) throws IOException {
        double resultado = 0.0;
        if (numUno <= numDos) {
            if (sigUno.equals("<") && sigDos.equals("<")) {
                resultado = calcularZ(limtSuperior, "<") - calcularZ(limitInferior, "<");
            }
            if (sigUno.equals("<") && sigDos.equals(">")) {
                resultado = calcularZ(limitInferior, ">");
            }
            if (sigUno.equals(">") && sigDos.equals("<")) {
                resultado = calcularZ(limtSuperior, sigDos);
            }
            if (sigUno.equals(">") && sigDos.equals(">")) {
                resultado = calcularZ(limitInferior, "<") + calcularZ(limtSuperior, ">");
            }
            return resultado;
        } else {
            String sigAw = new String(sigDos);
            if (sigUno.equals("<")) {
                sigDos = ">";
            } else {
                sigDos = "<";
            }
            if (sigAw.equals("<")) {
                sigUno = ">";
            } else {
                sigUno = "<";
            }
            return calcularZporIntervalo(numDos, numUno, sigUno, sigDos, limtSuperior, limitInferior);
        }
    }

    public void cargarDatosPz(JTextField desviacion, JTextField desviacionuno, JTextField miu, JTextField miuuno, JTextField n) {
        if (this.getConsulta().getMedia() >= 0 && this.getConsulta().getDesviacion() >= 0 && this.getConsulta().getn() >= 0) {
            desviacion.setText(String.format("%.2f", this.getConsulta().getDesviacion()).replace(",", "."));
            miu.setText(String.format("%.2f", this.getConsulta().getMedia()).replace(",", "."));
            desviacionuno.setText(String.format("%.2f", this.getConsulta().getDesviacion()).replace(",", "."));
            miuuno.setText(String.format("%.2f", this.getConsulta().getMedia()).replace(",", "."));
            n.setText(Integer.toString(this.getConsulta().getn()).replace(",", "."));
        }
    }

    //--------------------------------------------------------------------------------------------------
    public String intervaloTstudent(double x, int n, double s, double nc) throws IOException {
        int v = n - 1;
        double alfaM = Double.parseDouble(String.format("%.3f", (1.0 - (nc / 100)) / 2.0).replace(",", "."));
        System.out.println("alfa medios : " + alfaM + "   v: " + v);
        double tStudent = -1.0;
        double limInfe = 0.0;
        double limSupe = 0.0;
        String intervalo = "";
        int index = cargarAlfaMediosTstudent(alfaM);
        if (index == -1) {
            JOptionPane.showMessageDialog(null, "El nivel de confianza no es valido. digite");
        } else {
            tStudent = Double.parseDouble(Files.readAllLines(Paths.get("tStudent.txt")).get(v).split("   ")[index]);
            limInfe = x - (tStudent * (s / Math.sqrt(n)));
            limSupe = x + (tStudent * (s / Math.sqrt(n)));
            intervalo = "[ " + String.format("%.2f", limInfe) + " ; " + String.format("%.2f", limSupe) + " ]";
        }
        return intervalo;
    }

    //--------------------------------------------------------------------------------------------------
    public String intervaloAlfaMediosZ(double x, double nc, double s, int n) throws IOException {
        String signo = "";
        double alfaMedios = getAlfaMedios(nc, "-z.txt");
        double limiInfer = x - (alfaMedios * (s / Math.sqrt(n)));
        double limiSuper = x + (alfaMedios * (s / Math.sqrt(n)));
        return "[ " + String.format("%.2f", limiInfer) + " ; " + String.format("%.2f", limiSuper) + " ]";
    }

    public double getAlfaMedios(double nivelC, String urlArchivo) throws FileNotFoundException, IOException {
        FileReader f = new FileReader(urlArchivo);
        BufferedReader b = new BufferedReader(f);
        double numFila = 0.0;
        String linea = "";
        double num = getAlfamediosPred(nivelC);
        boolean encontrado = false;
        double getNum = 0.0;
        if (num != -1) {
            return num;
        } else {
            nivelC = (Math.round(((1 - (nivelC / 100)) / 2) * 100d) / 100d);
            while ((linea = b.readLine()) != null && encontrado == false) {
                if (numFila > 0) {
                    String[] datLinea = linea.split("   ");
                    for (int i = datLinea.length - 1; i > 0; i--) {
                        double numredondeado = Double.parseDouble(String.format("%.2f", Double.parseDouble(datLinea[i].substring(0, 4))).replace(",", "."));
                        if (nivelC == numredondeado) {
                            System.out.println("Nivel C " + nivelC + "  comparada con: " + datLinea[i].substring(0, 4) + "    dato a retornar : " + (Double.toString(numFila / 10) + Integer.toString(i)));
                            getNum = Double.parseDouble(Double.toString(numFila / 10) + Integer.toString(i));
                            encontrado = true;
                        }
                    }
                }
                numFila++;
            }
            b.close();
        }
        if (encontrado == false) {
            return getAlfaMedios(nivelC, "-z.txt");
        } else {
            return getNum;
        }
    }

    public double getAlfamediosPred(double nc) {
        double[][] niveles = {{90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100},
        {1.64, 1.70, 1.75, 1.81, 1.88, 1.96, 2.05, 2.7, 2.33, 2.58, 4.43}};
        for (int i = 0; i < niveles[0].length; i++) {
            if (nc == niveles[0][i]) {
                System.out.println("putos!!");
                return niveles[1][i];
            }
        }
        return -1;
    }
    //--------------------------------------------------------------------------------------------------

    public String intervalosPGorro(double p, double nc, int n) throws IOException {
        String interv = "";
        double zMedios = getAlfaMedios(nc, "-z.txt");
        if (p > 1) {
            p = p / 100.0;
        }
        double limiUno = p - (Double) (zMedios * (Math.sqrt(((p * (1.0 - p)) / n))));
        double limiDos = p + (Double) (zMedios * (Math.sqrt(((p * (1.0 - p)) / n))));
        return "[ " + String.format("%.2f", limiUno) + " ; " + String.format("%.2f", limiDos) + " ]";
    }

    //--------------------------------------------------------------------------------------------------
    public String diferenciaMediasVarDesconocida(double xUno, double xDos, int nUno, int nDos, double nc, double sUno, double sDos) throws IOException {
        String intervaDiferencia = "";
        int v = (nUno + nDos) - 2;
        double alfaMedios = Double.parseDouble(String.format("%.3f", ((1 - (nc / 100)) / 2)).replace(",", "."));
        int columTstudent = cargarAlfaMediosTstudent(alfaMedios);
        if (columTstudent != -1) {
            double tStudentNum = Double.parseDouble(Files.readAllLines(Paths.get("tStudent.txt")).get(v).split("   ")[columTstudent]);
            double limiteInfe = (xUno - xDos) - (tStudentNum * (Math.sqrt(((sUno / nUno) + (sDos / nDos)))));
            double limiteSuper = (xUno - xDos) + (tStudentNum * (Math.sqrt(((sUno / nUno) + (sDos / nDos)))));
            intervaDiferencia = "[ " + String.format("%.2f", limiteInfe) + " ; " + String.format("%.2f", limiteSuper) + " ]";
        } else {
            JOptionPane.showMessageDialog(null, "Lo sentimos. El nivel de confianza no se encuentra registrado en la tabla tStudent !!!");
            intervaDiferencia = "[ Nan ; Nan ]";
        }

        return intervaDiferencia;
    }

    public String diferenciaMediasVarConocida(double xUno, double xDos, double vUno, double vDos, int nUno, int nDos, double nc) throws IOException {
        double alfaM = getAlfaMedios(nc, "z.txt");
        double limUno = (xUno - xDos) - (alfaM * Math.sqrt(((vUno / nUno) + (vDos / nDos))));
        double limDos = (xUno - xDos) + (alfaM * Math.sqrt(((vUno / nUno) + (vDos / nDos))));
        return "[ " + String.format("%2f", limUno) + " ; " + String.format("%.2f", limDos) + " ]";
    }
//--------------------------------------------------------------------------------------------------

    public int cargarAlfaMediosTstudent(double alfa) throws IOException {
        int alfaMedios = -1;
        String[] alfaM = Files.readAllLines(Paths.get("tStudent.txt")).get(0).split("    ");
        for (int i = 1; i < alfaM.length; i++) {
            System.out.println("alfa " + i + " :" + alfaM[i]);
            if (Double.parseDouble(alfaM[i]) == alfa) {
                System.out.println("indice alfa m :" + i);
                alfaMedios = i;
            }
        }
        return alfaMedios;
    }

    //--------------------------------------------------------------------------------------------------
    public String getIntervaloAlfaMediosChiCuadrado(double nc, int n, double sCuadrado) throws IOException {
        double alfaM = (Math.round(((1 - (nc / 100)) / 2) * 1000d) / 1000d);
         double uno = getAlfaMediosChiCuadrado(alfaM, n - 1);
         double dos = getAlfaMediosChiCuadrado(1-alfaM, n - 1);
        if (uno != -1 && dos != -1) {
            double limUno = (sCuadrado * (n - 1)) / uno;
            double limDos = (sCuadrado * (n - 1)) / dos;
            return "[" + String.format("%.2f", limUno) + " ; " + String.format("%.2f", limDos) + " ]";
        }
        return "[ Nan" + " ; " + "Nan ]";
    }

    public double getAlfaMediosChiCuadrado(double numColum, int nFila) throws IOException {
        int c = getColumChiC(numColum);
        double num = -1;
        if (c != -1) {
            num = Double.parseDouble(Files.readAllLines(Paths.get("chiC.txt")).get(nFila).split("  ")[c]);
        } else {
            JOptionPane.showMessageDialog(null, "Lo sentimos el nivel de confianza no esta registrado !!!");
        }
        return num;
    }

    public int getColumChiC(double numColum) throws IOException {
        String[] nums = Files.readAllLines(Paths.get("chiC.txt")).get(0).split("  ");
        System.out.println("este es el error : "+ nums[1]);
        for (int i = nums.length -1 ; i > 0 ; i--) {
            System.out.println(numColum+" a la mierda : "+ nums[i]);
            if (numColum == Double.parseDouble(nums[i])) {
               return i;
            }
        }
        return -1;
    }

    //--------------------------------------------------------------------------------------------------
//    public static void main(String[] str) throws IOException {
//        ControlConsulta con = new ControlConsulta();
//        System.out.println(con.getColumChiC(0.980));
//        //System.out.println(String.format("%.2f",0.5067));
//    }
}
