/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;

/**
 *
 * @author SeeD
 */
public class Consulta {
    private ArrayList<Double> datos;
    private int N;
    private double media;
    private double varianza;
    private double desviacion;
    private double zInferior;
    private double zSuperior;
    
    //----------------------------------------
    private ArrayList<Double> nDatos;
    public ArrayList<Integer> elementos;
    private int n;
    private double nMedia;
    private double nVarianza;
    private double nDesviacion;
    
    public Consulta(){
        this.datos = new ArrayList<>();
        this.N = 0;
        this.media = 0;
        this.varianza = 0;
        this.desviacion = 0;
        //---------------------------------------
        this.nDatos = new ArrayList<>();
        this.elementos = new ArrayList<>();
        this.nMedia = 0;
        this.nVarianza = 0;
        this.nDesviacion = 0;
    }

    public double getzInferior() {
        return zInferior;
    }

    public void setzInferior(double zInferior) {
        this.zInferior = zInferior;
    }

    public double getzSuperior() {
        return zSuperior;
    }

    public void setzSuperior(double zSuperior) {
        this.zSuperior = zSuperior;
    }
    
    public ArrayList<Integer> getElementos() {
        return elementos;
    }

    public void setElementos(ArrayList<Integer> elementos) {
        this.elementos = elementos;
    }
    
    
    public ArrayList<Double> getnDatos() {
        return nDatos;
    }

    public void setnDatos(ArrayList<Double> nDatos) {
        this.nDatos = nDatos;
    }

    public double getnMedia() {
        return nMedia;
    }

    public void setnMedia(double nMedia) {
        this.nMedia = nMedia;
    }

    public double getnVarianza() {
        return nVarianza;
    }

    public void setnVarianza(double nVarianza) {
        this.nVarianza = nVarianza;
    }

    public double getnDesviacion() {
        return nDesviacion;
    }

    public void setnDesviacion(double nDesviacion) {
        this.nDesviacion = nDesviacion;
    }
    public void setn(int n){
        this.n = n;
    }
    public int getn(){
        return this.n;
    }
    
    
//----------------------------------------------------------------------------------------
    //--------------- GETTERS && SETTERS POBLACION 
    public ArrayList<Double> getDatos() {
        return datos;
    }

    public void setDatos(ArrayList<Double> datos) {
        this.datos = datos;
    }

    public int getN() {
        return N;
    }

    public void setN(int N) {
        this.N = N;
    }

    public double getMedia() {
        return media;
    }

    public void setMedia(double media) {
        this.media = media;
    }

    public double getVarianza() {
        return varianza;
    }

    public void setVarianza(double varianza) {
        this.varianza = varianza;
    }

    public double getDesviacion() {
        return desviacion;
    }

    public void setDesviacion(double desviacion) {
        this.desviacion = desviacion;
    }
    
}
