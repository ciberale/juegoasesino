package cf.logica.estados;

import cf.util.Dimension;
import cf.util.Posicion;





/**
 *
 * @author luigi
 */
public class Estado implements Cloneable,Comparable {

    int casillas [][];
    int numColumnas;
    int numFilas;
    double valorHeuristico;
    Estado padre;



    public Estado(Dimension dim){

        casillas = new int[dim.getColumnas()][dim.getFilas()];
        numColumnas = dim.getColumnas();
        numFilas = dim.getFilas();

    }

    public void setEstadoPadre(Estado estado){
        this.padre = estado;
    }

    public int getCasilla(int columna, int fila) {
        try{
            return casillas[columna][fila];

        }catch(IndexOutOfBoundsException e){

            System.out.println("Te ha salido");
           return -1;   // vaya mierda de tratamiento de excepcion.
        }
    }

    public int getFilas(){
       return numFilas;
    }

    public int getColumnas(){
        return numColumnas;
    }

    public int[][] getEstado(){
        return casillas;
    }

    public void setNumero(Posicion pos,int numero){
        try {
        casillas[pos.getEjeX()][pos.getEjeY()] = numero;
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("Te has salido tio");
        }
    }


    public boolean equals(Estado estado){

        boolean igual = true;
        int i = 0;
        while (igual && i < numColumnas){
             int j = 0;
             while(igual && j < numFilas){
                igual = getCasilla(i,j) == estado.getCasilla(i, j);
                j++;
            }
           i++;
        }
        return igual;

        /**
         *  Hacer test para comprobar si esto está bien.
         */
    }

    public boolean enRango(Posicion pos) {

        return pos.getEjeX() < getColumnas() && pos.getEjeX() >= 0 && pos.getEjeY() < getFilas() && pos.getEjeY() >= 0;
    }


    public String dibujaEstado(){

     String estado ="*********************************************\n";
        for (int i = 0; i< numFilas;i++)
             for (int j = 0; j< numColumnas;j++)
                 estado = estado + getCasilla(j,i) + "\n";

     return estado;
    }




    @Override
    public String toString(){
      /*  String estado = new String();
   for (int i = 0; i< numFilas;i++){
     for (int j = 0; j< numColumnas;j++)
            estado = estado + getCasilla(j,i);
         estado = estado + '\n';
        }
        return estado;*/

   String estado = new String();
   for (int i = 0; i< numFilas;i++){
     for (int j = 0; j< numColumnas;j++)
            estado = estado + getCasilla(j,i);
         estado = estado + '\n';
        }
        return estado;
    }

    @Override
     public Object clone(){
        Estado obj= new Estado(new Dimension(getColumnas(),getFilas()));
         for (int i = 0; i < numFilas;i++)
            for (int j= 0;j < numColumnas;j++)
                obj.setNumero(new Posicion(j,i),getCasilla(j, i));

        if (padre != null)
            obj.setEstadoPadre((Estado) padre.clone());
        return obj;

    }

    public Estado getEstadoPadre() {
        return padre;
    }

    public void setNumero(int ejeX, int ejeY, int num) {

        setNumero(new Posicion(ejeX,ejeY), num);
    }

    public int getCasilla(Posicion pos) {
        
        return getCasilla(pos.getEjeX(),pos.getEjeY());
    }

    public int compareTo(Object estado) {

         /** Poner aqui un booleano diciendo si la prioridad es maximizar o minimizar
         * el valor heuristico.
         */


        Estado comparado = (Estado)estado;
        double resultado = valorHeuristico - comparado.getCosteHeuristico();
        if (resultado > 0)
            return 1;
        else if (resultado == 0)
            return 0;
        else return -1;

    }

    public double getCosteHeuristico() {

        return valorHeuristico;
    }
        
    public void setCosteHeuristico(double valor){

        this.valorHeuristico = valor;
    }
}
