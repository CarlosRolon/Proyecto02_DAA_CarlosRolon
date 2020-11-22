/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proyecto02;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;


/**
 *
 * @author Carlos Rolon
 */
public class RecorridoGrafo {
    
    public void BFS (HashMap<Integer,Set<Integer>> grafo, int nodoInicio , String rutaArch) throws IOException {
        HashMap<Integer,Set<Integer>> arbolBFS = BFS_Metodo (grafo , nodoInicio);
        GrafoaArchivo(arbolBFS , rutaArch);
    }
    
    public void DFS_I (HashMap<Integer,Set<Integer>> grafo, int nodoInicio , String rutaArch) throws IOException {
        HashMap<Integer,Set<Integer>> arbolBFS = DFS_I_Metodo (grafo , nodoInicio);
        GrafoaArchivo(arbolBFS , rutaArch);
    }
    
    
    private HashMap<Integer,Set<Integer>>  BFS_Metodo (HashMap<Integer,Set<Integer>> grafo, int nodoInicio) {      
        HashMap<Integer,Set<Integer>> arbolBFS = new HashMap<>();
        List<Integer> visitado = new ArrayList<>();
        
        visitado.add(nodoInicio);
               
        for( int i = 0 ; i < visitado.size() ; i++){           
            Set<Integer> conexiones = new HashSet<>();
            Set<Integer> adyacentes = grafo.get(visitado.get(i));
            
            for(Integer a : adyacentes){                
                if( !visitado.contains(a)){
                    visitado.add(a);
                    conexiones.add(a);
                }
            }
            
            if(conexiones.size() > 0)
                arbolBFS.put(visitado.get(i),conexiones);
            
        }
        return arbolBFS;
    }
    
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

   
    private HashMap<Integer,Set<Integer>>  DFS_I_Metodo (HashMap<Integer,Set<Integer>> grafo, int nodoInicio) {
        HashMap<Integer,Set<Integer>> arbolDFS = new HashMap<>();
        List<Integer> visitado = new ArrayList<>();
        Stack<Integer> porVisitar = new Stack<>(); 
        int nodoActual ;
        porVisitar.push(nodoInicio);
        
        while(  0 < porVisitar.size() ){
            nodoActual = porVisitar.peek();
            porVisitar.pop();
            visitado.add(nodoActual);
            Set<Integer> adyacentes = grafo.get(nodoActual);            
             for(Integer a : adyacentes){
                
                if( !visitado.contains(a) && !porVisitar.contains(a)){
                    porVisitar.push(a);
                }
            }
             
            if (porVisitar.size() > 0 ) 
            {
                generarArista(arbolDFS , grafo , porVisitar.peek() ,visitado  );
            }
            
        }
        return arbolDFS;
    }
      
    public static void generarArista( HashMap<Integer,Set<Integer>> arbolDFS, HashMap<Integer,Set<Integer>> grafo  , int destino ,List<Integer> visitado ) {
        boolean seCreoConexion = false;
        int nodo ;
        for (int x =  visitado.size()-1; x >= 0 && !seCreoConexion; x-- )
        {
            nodo = visitado.get(x) ;
            Set<Integer> conexiones = grafo.get(nodo);
            if (conexiones.contains(destino)) {
                seCreoConexion = true;
                if(arbolDFS.containsKey(nodo)){
                    Set<Integer> v = arbolDFS.get(nodo);
                    v.add(destino);
                }else{
                    Set<Integer> newV = new HashSet<>();
                    newV.add(destino);
                    arbolDFS.put(nodo,newV);
                }              
            }
        }  
    }
}
