package uniandes.dpoo.estructuras.logica;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Esta clase tiene un conjunto de métodos para practicar operaciones sobre mapas.
 *
 * Todos los métodos deben operar sobre el atributo mapaCadenas que se declara como un Map.
 * 
 * En este mapa, las llaves serán cadenas y los valores serán también cadenas. La relación entre los dos será que cada llave será igual a la cadena del valor, pero invertida.
 * 
 * El objetivo de usar el tipo Map es que sólo puedan usarse métodos de esa interfaz y no métodos adicionales provistos por la implementación concreta (HashMap).
 * 
 * No pueden agregarse nuevos atributos.
 */
public class SandboxMapas
{
    /**
     * Un mapa de cadenas para realizar varias de las siguientes operaciones.
     * 
     * Las llaves del mapa son cadenas, así como los valores.
     * 
     * Las llaves corresponden a invertir la cadena que aparece asociada a cada llave.
     */
    private Map<String, String> mapaCadenas;

    /**
     * Crea una nueva instancia de la clase con las dos listas inicializadas pero vacías
     */
    public SandboxMapas( )
    {
        mapaCadenas = new HashMap<String, String>();
    }

   
    private String invertir(String s) {
        if (s == null) return null;
        StringBuilder sb = new StringBuilder();
        for (int i = s.length() - 1; i >= 0; i--) {
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }
    /**
     * Retorna una lista con las cadenas del mapa (los valores) ordenadas lexicográficamente
     * @return Una lista ordenada con las cadenas que conforman los valores del mapa
     */
    public List<String> getValoresComoLista( )
    {
    	 ArrayList<String> lista = new ArrayList<String>(mapaCadenas.values());
         Collections.sort(lista);
         return lista;
    }

    /**
     * Retorna una lista con las llaves del mapa ordenadas lexicográficamente de mayor a menor
     * @return Una lista ordenada con las cadenas que conforman las llaves del mapa
     */
    public List<String> getLlavesComoListaInvertida( )
    {
    	 ArrayList<String> llaves = new ArrayList<String>(mapaCadenas.keySet());
         Collections.sort(llaves, Collections.reverseOrder());
         return llaves;
    }

    /**
     * Retorna la cadena que sea lexicográficamente menor dentro de las llaves del mapa .
     * 
     * Si el mapa está vacío, debe retornar null.
     * @return
     */
    public String getPrimera( )
    {
    	 if (mapaCadenas.isEmpty()) return null;

         String menor = null;
         for (String k : mapaCadenas.keySet()) {
             if (menor == null || (k != null && k.compareTo(menor) < 0) || (menor != null && k == null)) {
                 menor = k;
             }
         }
         return menor;
    }

    /**
     * Retorna la cadena que sea lexicográficamente mayor dentro de los valores del mapa
     * 
     * Si el conjunto está vacío, debe retornar null.
     * @return
     */
    public String getUltima( )
    {
    	 if (mapaCadenas.isEmpty()) return null;

         String mayor = null;
         for (String v : mapaCadenas.values()) {
             if (mayor == null || (v != null && v.compareTo(mayor) > 0)) {
                 mayor = v;
             }
         }
         return mayor;
    }

    /**
     * Retorna una colección con las llaves del mapa, convertidas a mayúsculas.
     * 
     * El orden de las llaves retornadas no importa.
     * @return Una lista de cadenas donde todas las cadenas están en mayúsculas
     */
    public Collection<String> getLlaves( )
    {
    	  ArrayList<String> res = new ArrayList<String>();
          for (String k : mapaCadenas.keySet()) {
              if (k == null) {
                  res.add(null);
              } else {
                  res.add(k.toUpperCase());
              }
          }
          return res;
    }

    /**
     * Retorna la cantidad de *valores* diferentes en el mapa
     * @return
     */
    public int getCantidadCadenasDiferentes( )
    {
    	  Set<String> diferentes = new HashSet<String>(mapaCadenas.values());
          return diferentes.size();
    }

    /**
     * Agrega un nuevo valor al mapa de cadenas: el valor será el recibido por parámetro, y la llave será la cadena invertida
     * 
     * Este método podría o no aumentar el tamaño del mapa, dependiendo de si ya existía la cadena en el mapa
     * 
     * @param cadena La cadena que se va a agregar al mapa
     */
    public void agregarCadena( String cadena )
    {
    	 String llave = invertir(cadena);
         mapaCadenas.put(llave, cadena);
    }

    /**
     * Elimina una cadena del mapa, dada la llave
     * @param cadena La llave para identificar el valor que se debe eliminar
     */
    public void eliminarCadenaConLLave( String llave )
    {
    	  mapaCadenas.remove(llave);
    }

    /**
     * Elimina una cadena del mapa, dado el valor
     * @param cadena El valor que se debe eliminar
     */
    public void eliminarCadenaConValor( String valor )
    {
    	 String llaveEliminada = null;

         for (Map.Entry<String, String> par : mapaCadenas.entrySet()) {
             String v = par.getValue();
             if ((valor == null && v == null) || (valor != null && valor.equals(v))) {
                 llaveEliminada = par.getKey();
                 break;
             }
         }

         if (llaveEliminada != null) {
             mapaCadenas.remove(llaveEliminada);
         }
     }
    

    /**
     * Reinicia el mapa de cadenas con las representaciones como Strings de los objetos contenidos en la lista del parámetro 'objetos'.
     * 
     * Use el método toString para convertir los objetos a cadenas.
     * @param valores Una lista de objetos
     */
    public void reiniciarMapaCadenas( List<Object> objetos )
    {
    	  mapaCadenas.clear();
          if (objetos == null) return;

          for (Object o : objetos) {
              if (o != null) {
                  String val = o.toString();
                  String key = invertir(val);
                  mapaCadenas.put(key, val);
              }
          }
    }

    /**
     * Modifica el mapa de cadenas reemplazando las llaves para que ahora todas estén en mayúsculas pero sigan conservando las mismas cadenas asociadas.
     */
    public void volverMayusculas( )
    {
    	   HashMap<String, String> nuevo = new HashMap<String, String>();
           for (Map.Entry<String, String> par : mapaCadenas.entrySet()) {
               String k = par.getKey();
               String v = par.getValue();
               if (k == null) {
                   nuevo.put(null, v);
               } else {
                   nuevo.put(k.toUpperCase(), v);
               }
           }
           mapaCadenas = nuevo;
       }
    

    /**
     * Verifica si todos los elementos en el arreglo de cadenas del parámetro hacen parte del mapa de cadenas (de los valores)
     * @param otroArreglo El arreglo de enteros con el que se debe comparar
     * @return True si todos los elementos del arreglo están dentro de los valores del mapa
     */
    public boolean compararValores( String[] otroArreglo )
    {
    	  if (otroArreglo == null) return true;

          HashSet<String> valores = new HashSet<String>(mapaCadenas.values());
          for (int i = 0; i < otroArreglo.length; i++) {
              String s = otroArreglo[i];
              if (!valores.contains(s)) {
                  return false;
              }
          }
          return true;
    }

}
