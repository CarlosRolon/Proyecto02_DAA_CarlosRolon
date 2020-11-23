/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proyecto02;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Carlos Rolon
 */
public class GenerarGrafo {
       
    public void Barabasi_Albert(String rutaArch ,int n , int d  ) throws IOException
    {
        HashMap<Integer,Set<Integer>> grafo =  Metodo_Barabasi_Albert( n ,  d );
        GrafoaArchivo(grafo , rutaArch);
    }

    public void GeograficoSimple(String rutaArch, int n , double r) throws IOException
    {
        HashMap<Integer,Set<Integer>> grafo =  Metodo_GeograficoSimple( n , r );
        GrafoaArchivo(grafo , rutaArch);
    }
    
    public void ErdosRenyi(String rutaArch, int n , int m) throws IOException
    {
        HashMap<Integer,Set<Integer>> grafo =  Metodo_ErdosRenyi( n , m );
        GrafoaArchivo(grafo , rutaArch);
    } 
    
    public void Gilbert(String rutaArch, int n , double p) throws IOException
    {
        HashMap<Integer,Set<Integer>> grafo =  Metodo_Gilbert( n , p );
        GrafoaArchivo(grafo , rutaArch);
    } 
        
   // Metodos de creacion de grafos
  
     private static  HashMap<Integer,Set<Integer>>  Metodo_Barabasi_Albert(int n , int d ){
        HashMap<Integer,Set<Integer>> grafo = new HashMap<>();
        double probNodo,probRandom ;
        double nV = 0 , grado;
        Set<Integer> vertices;

         // Genera Nodos Iniciales
        for (int i = 0; i < d; i++) {
            Set<Integer> ini = new HashSet<>();
            for (int j = 0; j < d; j++) {
                if ( i != j){
                    ini.add(j);
                } 
            }
            grafo.put(i,ini);
        }              
        // Genera Conexiones 
        for (int i = d; i < n ; i++) {
            // Agrega el nodo nuevo
            Set<Integer> ini = new HashSet<>();
            grafo.put(i,ini);          
            nV = 0;            
            // Busca conectarse
            for (int j = 0 ; j < i && nV < d ; j++) {
                
                vertices = grafo.get(j);
                grado  = vertices.size();                
                // Probabilidad del nodo
                probNodo = 1 - (grado/ d);                 
                // Probabilidad alteatoria
                probRandom = Math.random();    
                // Verifica que este dentro de la probablidad
                if (probNodo >= probRandom )
                {
                    Set<Integer> num1 = grafo.get(i);
                    Set<Integer> num2 = grafo.get(j);
                    num1.add(j);
                    num2.add(i);
                    nV++;
                }
            }
        }
        return grafo;
    }  
   
     
    private static  HashMap<Integer,Set<Integer>>  Metodo_GeograficoSimple(int n , double r )
    {
        HashMap<Integer,Set<Integer>> grafo = new HashMap<>();
        double [][]posiciones =  new double[n][2];
        double x , y  , distancia ;
        //Genera Nodos 
        for (int i = 0; i < n; i++) {
            Set<Integer> v = new HashSet<>();
            x = Math.random();
            y = Math.random();
            grafo.put(i,v);
            posiciones[i][0] = x;
            posiciones[i][1] = y;
        }        
        // Genera Conexiones 
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1 ; j < n; j++) {
                distancia = Math.hypot(posiciones[i][0] - posiciones[j][0] , posiciones[i][1] - posiciones[j][1]);
                if ( distancia <= r )
                {
                    Set<Integer> num1 = grafo.get(i);
                    Set<Integer> num2 = grafo.get(j);
                    num1.add(j);
                    num2.add(i);
                }
            }
        }
        return grafo;
    }
    
    private static  HashMap<Integer,Set<Integer>>  Metodo_ErdosRenyi(int n , int m ){
        HashMap<Integer,Set<Integer>> grafo = new HashMap<>();
        int numero1 , numero2;
        //Genera Nodos 
        for (int i = 0; i < n; i++) {
            Set<Integer> x = new HashSet<>();
            grafo.put(i,x);
        }        
        // Genera Conexiones 
        for (int i = 0; i < m; i++) {
            numero1 = (int)(Math.random()*n);
            numero2 = (int)(Math.random()*n);
            
            if(numero1 == numero2)
                continue;
            
            Set<Integer> num1 = grafo.get(numero1);
            Set<Integer> num2 = grafo.get(numero2);
            num1.add(numero2);
            num2.add(numero1);
        }
        return grafo;
    }
    
    
    private static  HashMap<Integer,Set<Integer>>  Metodo_Gilbert(int n , double p ){
        HashMap<Integer,Set<Integer>> grafo = new HashMap<>();
        double probablidad;

        //Genera Nodos 
        for (int i = 0; i < n; i++) {
            Set<Integer> x = new HashSet<>();
            grafo.put(i,x);
        }        
        // Genera Conexiones 
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1 ; j < n; j++) {
                // Genera un probabilidad alteatoria
                probablidad = Math.random();                
                // Verifica que este dentro de la probablidad
                if (probablidad <= p )
                {
                    Set<Integer> num1 = grafo.get(i);
                    Set<Integer> num2 = grafo.get(j);
                    num1.add(j);
                    num2.add(i);
                }
            }
        }
        return grafo;
    }  
   
    //Metodo para escribir el grafo en un archivo 
    
    private static void GrafoaArchivo( HashMap<Integer, Set<Integer>> grafo , String rutaArch) throws IOException
    {   
        // Iniciliza el archivo
        File output = new File(rutaArch);
        FileWriter writer = new FileWriter(output);
        // Inicializa el grafo
        writer.write("digraph { \n");
        //Recorre el grafo
        for(Map.Entry<Integer, Set<Integer> > entry : grafo.entrySet()) {
            
            Integer key = entry.getKey();
            Set<Integer> value = entry.getValue();
            //Escribe cada conexion
            for (int elemento : value) {
                writer.write(Integer.toString(key)  + " -- " +   
                                Integer.toString(elemento) + "\n");
            }
            
        }
        //Cierra el grafo
        writer.write("}");
        // Cierra el archivo
        writer.flush();
        writer.close();
        
    }
   
}
