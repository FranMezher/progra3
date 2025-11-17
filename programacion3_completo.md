Programación 3
Clase 2


Temas de la clase
●Divide y Conquista. 
●Recursividad. 
●Algoritmos de Búsqueda.
●Cálculo de complejidad por método de división.
●Métodos de Ordenamiento. 
●QuickSort y MergeSort. 
●Resolución de recurrencias. 
●Teorema general de Divide y Conquista.

Divide y Conquista
Técnica de diseño de algoritmos que consiste en dividir un problema en 
subproblemas más pequeños, resolver cada subproblema de manera recursiva y 
combinar las soluciones de los subproblemas para resolver el problema original.

Ejemplo de aplicación: Torres de Hanoi
El juego consiste en pasar todos los discos de la varilla ocupada (es decir la que 
posee la torre) a una de las otras varillas vacantes. Para realizar este objetivo, es 
necesario seguir tres simples reglas:
-Sólo se puede mover un disco cada vez.
-Un disco de mayor tamaño no puede descansar sobre uno más pequeño que él 
mismo.
-Solo puedes desplazar el disco que se encuentre arriba en cada varilla.
https://www.geogebra.org/m/NqyWJVra
¿ Podría hacer un programa en java para resolver este problema ?

Resolución del ejercicio
public class TorresDeHanoi {
    // Método recursivo para resolver las Torres de Hanoi
    public static int resolverHanoi(int n, char origen, char auxiliar, char destino) {
        if (n == 1) {
            System.out.println("Mover disco 1 desde " + origen + " hasta " + destino);
            return 1;
        }
        int movimientos = 0;
        movimientos += resolverHanoi(n - 1, origen, destino, auxiliar);
        System.out.println("Mover disco " + n + " desde " + origen + " hasta " + destino);
        movimientos += 1;
        movimientos += resolverHanoi(n - 1, auxiliar, origen, destino);
        return movimientos;
    }
    public static void main(String[] args) {
        int n = 3; // Número de discos
        int movimientos = resolverHanoi(n, 'A', 'B', 'C'); // A es el origen, B es el auxiliar, C es el destino
        System.out.println("Número total de movimientos: " + movimientos);
    }
}

Recurrencia para las Torres de Hanoi
Para el problema de las Torres de Hanoi, la recurrencia que describe el número 
de movimientos necesarios para resolver el problema con n discos es:
T(n) = T(n-1) + 1 + T(n-1)
T(n) = 2T(n-1)+1
donde
T(n) es el número de movimientos necesarios para mover n discos
T(n-1) es el número de movimientos necesarios para mover n-1 discos

Recurrencia para las Torres de Hanoi
Expansión repetida
T(n) = 2T(n-1)+1
T(n-1)=2T(n-2)+1
T(n-2)=2T(n-3)+1

Recurrencia para las Torres de Hanoi
Sustituyendo
T(n)= 2  ( 2T(n-2)+1 ) + 1
T(n)= 2^2 T (n-2)+2+1
T(n)= 2^3 T (n-3) + 2^2 + 2 + 1
Al continuar este patrón:
T(n) = 2^k T (n-k) + 2^k -1 + 2^k - 2 + … + 2 + 1

Recurrencia para las Torres de Hanoi
Base de recurrencia
Cuando k = n
T(n) = 2^n T(0)  + ( 2^n-1 + 2^n-2 + … + 2 + 1)
Sabemos que T(0)=0 (no se necesitan movimientos para 0 discos), entonces:
T(n) = 2^n * 0 + ( 2^n-1 + 2^n-2 + … + 2 + 1)
T(n) = 2^n-1 + 2^n-2 + … + 2 + 1

Recurrencia para las Torres de Hanoi
La sumatoria 2n−1+2n−2+…+2+1
 es una serie geométrica que se puede simplificar: 
    n−1
∑ 2i = 2n−1
   i=0
Por lo que T(n) = 2 ^(n – 1)

Tiempo de ejecución, algoritmo de Torres de Hanoi
El algoritmo de las Torres de Hanói se describe con la siguiente recurrencia:
T(n)=T(n−1)+1+T(n−1)
Simplificando la ecuación, se obtiene:
T(n)=2T(n−1)+1
Esta es una recurrencia por sustracción, que se ajusta a la forma general 
T(n)=aT(n−b)+f(n).

Cálculo de complejidad, algoritmo recursivo torres Hanoi
En este caso, a=2  que es mayor a 1, la complejidad es de tipo O(a^(n div b))
Sustituyendo los valores de a=2 y b=1 , se obtiene la complejidad O(2^(n div 1))
Lo que resulta en O(2^n) 


Búsqueda Binaria
Es un algoritmo de búsqueda que encuentra la posición de un valor en un array 
ordenado. Compara el valor con el elemento en el medio del array, si no son 
iguales, la mitad en la cual el valor no puede estar es eliminada y la búsqueda 
continúa en la mitad restante hasta que el valor se encuentre.
La búsqueda binaria es computada en el peor de los casos en un tiempo 
logarítmico, realizando O(log n) comparaciones, donde n es el número de 
elementos del arreglo y log es el logaritmo.
Referencia: https://es.wikipedia.org/wiki/B%C3%BAsqueda_binaria

Búsqueda binaria. Análisis de recurrencia
La búsqueda binaria funciona de la siguiente manera:
Compara el elemento a buscar con el elemento central de una lista ordenada.
Si son iguales, el elemento es encontrado.
Si el elemento a buscar es menor, la búsqueda continúa en la mitad izquierda de 
la lista.
Si el elemento a buscar es mayor, la búsqueda continúa en la mitad derecha de 
la lista.

Búsqueda binaria. Análisis de recurrencia
Cada llamada recursiva reduce el tamaño del problema a la mitad. La función de 
recurrencia que describe el tiempo de ejecución en el peor de los casos es:
T(n)=T(n/2)+c
Donde:
T(n) es el tiempo para buscar en un array de tamaño n.
T(n/2) es la llamada recursiva en la mitad del array.
c es el costo constante de la comparación y las operaciones de índices.

Expansión por recurrencia de la búsqueda binaria
Comenzamos con la recurrencia original: 
T(n)=T(n/2)+c
Sustituimos T(n/2) con la misma fórmula, reemplazando n por n/2:
T(n/2)=T((n/2)/2)+c=T(n/4)+c
Sustituimos este resultado de nuevo en la ecuación original:
T(n)=(T(n/4)+c)+c=T(n/4)+2c
Continuamos el proceso, sustituyendo T(n/4):
T(n/4)=T((n/4)/2)+c=T(n/8)+c
T(n)=(T(n/8)+c)+2c=T(n/8)+3c
Podemos ver un patrón. Después de k sustituciones, la fórmula general es:
T(n)=T(n/2 k )+kc

Expansión por recurrencia de la búsqueda binaria
El proceso termina cuando llegamos al caso base, que es cuando el problema 
es lo suficientemente pequeño para resolverse directamente. En la búsqueda 
binaria, esto ocurre cuando el tamaño del array es 1. Así, n/2 k =1, lo que 
significa que n=2 k . Despejando k, obtenemos k=log 2   (n).
Sustituimos el valor de k en la ecuación general:
T(n)=T(1)+(log 2   (n))c
Como T(1) es una constante (el costo de resolver un problema de tamaño 1), y c 
también es una constante, la complejidad dominante es el término log 2   (n). Por 
lo tanto, la complejidad es O(log(n)).

Cálculo de complejidad de búsqueda binaria
La función de recurrencia:
T(n)=T(n/2)+c
Este es un caso de recurrencia por división, se aplica:
Para esta función, los valores son: a=1, b=2 y k=0
Como a=b^k , se aplica el caso O(n^k log(n)) 
Por lo que la búsqueda binaria es de complejidad: O(log(n))


Ejemplos en árboles binarios de búsqueda (BST)
Árbol Binario de Búsqueda:
Un BST es un árbol binario en el cual para cada nodo:
Los valores en el subárbol izquierdo son menores que el valor del nodo.
Los valores en el subárbol derecho son mayores que el valor del nodo.

Ejemplo con un BST
Buscar un valor x en un BST
Pasos:
División: Comparamos x con el valor del nodo raíz:
Si x es igual al valor del nodo, hemos encontrado el elemento.
Si  x es menor que el valor del nodo, nos dirigimos al subárbol izquierdo.
Si x es mayor que el valor del nodo, nos dirigimos al subárbol derecho.
Recursión: Aplicamos la misma lógica recursivamente al subárbol correspondiente hasta encontrar 
el elemento o llegar a un nodo nulo (lo que indicaría que el elemento no está en el árbol).
Combinar: En este caso, no hay combinación compleja porque estamos interesados en la 
búsqueda de un único valor. El resultado se obtiene directamente del proceso recursivo.

Actividad 1
Implementa el método getHeight para recalcular la altura de un Árbol Binario de 
Búsqueda utilizando la técnica de "Dividir y Vencerás". 
Calcular la complejidad por método de sustracción o división, para un árbol 
desbalanceado y para un árbol balanceado.
El código base está en el paquete clase2 del repo de la materia .

MergeSort
El algoritmo de ordenamiento por mezcla (merge sort en inglés) es un algoritmo 
de ordenamiento externo estable basado en la técnica divide y vencerás. Es de 
complejidad O(n log n)
En Java los métodos de ordenación de Arrays usan merge sort o una modificación 
de quicksort dependiendo de los tipos de datos y por cuestiones de eficiencia 
cambian a ordenamiento por inserción cuando se están ordenando menos de 
siete elementos en el array.
Referencia https://es.wikipedia.org/wiki/Ordenamiento_por_mezcla

MergeSort
Conceptualmente, el ordenamiento por mezcla funciona de la siguiente manera:
Si la longitud de la lista es 0 o 1, entonces ya está ordenada. En otro caso:
Dividir la lista desordenada en dos sublistas de aproximadamente la mitad del 
tamaño.
Ordenar cada sublista recursivamente aplicando el ordenamiento por mezcla.
Mezclar las dos sublistas en una sola lista ordenada.

Mergesort. Análisis de recurrencia
El algoritmo de Mergesort funciona de la siguiente manera:
Divide el array en dos mitades, lo que no toma tiempo significativo.
Llama a Mergesort recursivamente en cada una de las mitades.
Combina (merge) las dos mitades ordenadas en un solo array, lo que toma un 
tiempo lineal, Θ(n).
La función de recurrencia para el tiempo de ejecución en el peor de los casos de 
Mergesort es:
T(n)=2T(n/2)+Θ(n)

Mergesort. Aplicación del Método de División
Usando el método de división, identificamos los parámetros de la recurrencia:
a=2: Se realizan dos llamadas recursivas.
b=2: El array se divide en dos sub-arrays.
k=1: El costo de las operaciones fuera de las llamadas recursivas es Θ(n), por lo 
que el grado del polinomio es k=1.
Ahora, comparamos a con b^k : a = 2 y b^k = 1 = 2^1 = 2  =>   a = b^k
Dado que a=b^k , el método de división nos indica que la complejidad asintótica 
es Θ(n k log(n)).
Sustituyendo el valor de k=1, obtenemos la complejidad final para Mergesort:
Θ(n 1 log(n)) = Θ(n log n)


Quicksort
Es un algoritmo de ordenamiento. El algoritmo trabaja de la siguiente forma:
Elegir un elemento del conjunto de elementos a ordenar, al que llamaremos pivote.
Resituar los demás elementos de la lista a cada lado del pivote, de manera que a un 
lado queden todos los menores que él, y al otro los mayores. Los elementos iguales al 
pivote pueden ser colocados tanto a su derecha como a su izquierda, dependiendo de 
la implementación deseada. En este momento, el pivote ocupa exactamente el lugar 
que le corresponderá en la lista ordenada.
La lista queda separada en dos sublistas, una formada por los elementos a la 
izquierda del pivote, y otra por los elementos a su derecha.
Repetir este proceso de forma recursiva para cada sublista mientras éstas contengan 
más de un elemento. Una vez terminado este proceso todos los elementos estarán 
ordenados.
Referencia: https://es.wikipedia.org/wiki/Quicksort

Quicksort. Análisis de eficiencia
Como se puede suponer, la eficiencia del algoritmo depende de la posición en la que 
termine el pivote elegido.
En el mejor caso, el pivote termina en el centro de la lista, dividiéndola en dos 
sublistas de igual tamaño. En este caso, el orden de complejidad del algoritmo es O(n 
log n). En el peor caso, el pivote termina en un extremo de la lista. El orden de 
complejidad del algoritmo es entonces de O(n^2) El peor caso dependerá de la 
implementación del algoritmo, aunque habitualmente ocurre en listas que se 
encuentran ordenadas, o casi ordenadas. Pero principalmente depende del pivote, si 
por ejemplo el algoritmo implementado toma como pivote siempre el primer elemento 
del array, y el array que le pasamos está ordenado, siempre va a generar a su 
izquierda un array vacío, lo que es ineficiente.

Quicksort. Análisis de recurrencia.
La recurrencia de Quicksort para el peor caso es:
T(n)=T(n−1)+ Θ(n)
Esta ecuación de recurrencia no puede resolverse con el método de división, ya 
que es un problema de sustracción (el tamaño se reduce en 1 en lugar de 
dividirse). En este escenario, se usaría un método de expansión que resultaría 
en una complejidad de Θ(n^2 ).

Quicksort. Análisis complejidad en el peor caso
Usando el método de sustracción, identificamos los parámetros de la recurrencia:
a=1: Hay una llamada recursiva.
b=1: El tamaño del problema se reduce en 1 en cada paso.
k=1: El costo de la partición es lineal, Θ(n), por lo que el grado del polinomio es 1.
Según el método de sustracción, si 
a=1, la complejidad es Θ(n^(k+1))
Sustituyendo los valores de a=1 y k=1:
T(n)  Θ(n^(k+1) ) = Θ(n^(1+1)) =Θ(n^2 ) ∈

Actividad 2
La empresa GameScore mantiene un ranking de jugadores con sus puntajes. El equipo de 
desarrollo quiere implementar un sistema de clasificación que permita búsquedas complejas de 
manera óptima. Necesitan una función que, dado un rango de puntajes [p_min, p_max] y un 
número k, devuelva los k mejores jugadores que se encuentren en ese rango de puntajes.
Requisitos: La búsqueda debe ser más eficiente que un recorrido lineal del árbol, sin visitar nodos 
fuera del rango [p_min, p_max].La función debe ser recursiva. La implementación debe ser con un 
árbol AVL personalizado, ya que se necesita la flexibilidad para modificar la estructura y sus 
métodos de recorrido de manera precisa para esta consulta específica. Se quiere evitar el uso de 
librerías como TreeMap para tener un control total sobre la lógica.
Análisis de la complejidad: Explica por qué un AVL es la estructura adecuada para este problema. 
Determina la complejidad de la búsqueda, considerando tanto el tiempo para encontrar el rango 
como el tiempo para obtener los k jugadores.

Bibliografía
AHO, J.; HOPCROFT, John E. y ULLMAN, Jeffrey D.. Estructuras de Datos y
Algoritmos. 1988. Wilmington : Addison Wesley Iberoamericana. 438 p. 
ISBN:02016402449684443455.
  BRASSARD, Gilles. Fundamentals of algorithmics. Upper Saddle  
  River: Prentice Hall, 1996. ISBN: 9780133350685

Programación 3
Clase 3


Temas de la clase 3
●Definición Técnica de Diseño Greedy 
●Resolución de problemas Greedy 
●Análisis general de la complejidad de los problemas Greedy.

Definición Técnica de Diseño Greedy
Técnica de diseño de algoritmos donde se toma la mejor decisión localmente 
óptima en cada paso con la esperanza de encontrar una solución globalmente 
óptima.

Propiedades
Optimal Substructure: La solución óptima del problema contiene la solución 
óptima de sus subproblemas.
Greedy Choice Property: Una solución globalmente óptima se puede obtener 
mediante decisiones localmente óptimas.

Ejemplo 1: Problema del Cambio de Monedas
Problema del cambio de monedas: Dado un conjunto de denominaciones de 
monedas y un monto total, el objetivo es encontrar la cantidad mínima de 
monedas necesarias para hacer ese monto.

Problema del Cambio de Monedas
Problema del cambio de monedas: Dado un conjunto de denominaciones de 
monedas y un monto total, el objetivo es encontrar la cantidad mínima de 
monedas necesarias para hacer ese monto.
Supongamos que tenemos las siguientes denominaciones de monedas: {1, 5, 10, 
25} y queremos hacer un monto total de 36.
Un algoritmo greedy para este problema seleccionará siempre la moneda de 
mayor denominación que no exceda el monto restante.

Pasos del Algoritmo
Ordenar las monedas de mayor a menor.
Inicializar el monto restante al monto total.
Mientras el monto restante sea mayor que 0:
●Seleccionar la moneda de mayor denominación que no exceda el monto 
restante.
●Restar el valor de esa moneda del monto restante.
●Añadir la moneda a la solución.

Conjunto canónico
Un conjunto canónico de monedas es aquel en el que el enfoque greedy siempre 
produce una solución óptima para cualquier monto. El conjunto {1,5,10,25} es un 
ejemplo de un conjunto canónico.
Dado que tenemos una moneda de 1 centavo, es posible generar cualquier monto 
entero positivo. Esto significa que no existen valores que no se puedan formar 
utilizando este conjunto de monedas.

Pseudocódigo
función encontrarMinimoMonedas(monedas, monto)
    ordenar monedas de menor a mayor
    lista resultado = lista vacía
    
    para i desde el índice más alto de monedas hasta el índice 0 (de mayor a menor)
        mientras monto sea mayor o igual a monedas[i]
            restar monedas[i] a monto
            añadir monedas[i] a resultado
        fin mientras
    fin para
    
    devolver resultado
fin función

Implementación en Java
public class CambioMonedas {    
    // Función para encontrar la cantidad mínima de monedas
    public static List<Integer> encontrarMinimoMonedas(int[] monedas, int monto) {
        Arrays.sort(monedas);
        List<Integer> resultado = new ArrayList<>();        
        for (int i = monedas.length - 1; i >= 0; i--) {
            while (monto >= monedas[i]) {
                monto -= monedas[i];
                resultado.add(monedas[i]);
            }
        }        
        return resultado;
    }    
    public static void main(String[] args) {
        int[] monedas = {1, 5, 10, 25};
        int monto = 36;        
        List<Integer> resultado = encontrarMinimoMonedas(monedas, monto);        
        System.out.println("Monedas usadas para hacer " + monto + ": " + resultado);
    }
}

Descripción del algoritmo
En cualquier etapa individual, un algoritmo greedy, selecciona la opción que se 
“localmente óptimal” . Por ejemplo si tenemos monedas $25,$10,$5,$1 y de desea 
dar un cambio de $63. 
El algoritmo ordena de mayor a menor, y selecciona la moneda de mayor valor 
que no supere los $63, la agrega a la lista y resta el valor de los $63, quedando 
$38. De nuevo, se seleccionó la moneda mas grande cuyo valor no fuera mayor a 
$38 y se se añadió a la lista y así sucesivamente.
Nota: si las monedas tuvieran valores de $1,$5 y $11 y se requiere dar cambio de 
$15, el algoritmo greedy selecciona $11 y cuatro de $1 , en vez de 3 de $5.

Prueba de escritorio
63-25=38  es mayor a 25
38-25=13 no es mayor a 25, siguiente moneda $10
13-10= 3  no es mayor a 25, siguiente moneda $ 5, siguiente moneda $1
3-1 = 2 es mayor $1
2-1 = 1 fin

Conclusiones
Complejidad Temporal: O(n log n) debido a la ordenación inicial de las monedas. 
(selección monedas O(n)  entonces complejidad: 0(n) + O(n log n)  y por propiedad de 
algebra de órdenes, tomamos el mayor en este caso O(n log n)
El enfoque greedy no siempre produce una solución óptima para todos los conjuntos 
de denominaciones de monedas. Por ejemplo, si las denominaciones son {1, 3, 4} y el 
monto es 6, el algoritmo greedy daría {4, 1, 1} (3 monedas) en lugar de la solución 
óptima {3, 3} (2 monedas).
El problema del cambio de monedas es un ejemplo para ilustrar los algoritmos greedy, 
mostrando cómo se pueden utilizar para resolver problemas de manera eficiente en 
muchos casos, aunque no siempre producen la solución óptima para todos los 
conjuntos de entrada.

Actividad 1
Dada una lista de monedas con denominaciones convencionales (1, 5, 10, 25, 
etc.), implementar una función greedy que determine si es posible entregar un 
cambio exacto utilizando una lista de monedas disponible. 
Realizar pseudocódigo e implementación en Java. Indicar la complejidad 
algorítmica.

Actividad 2: Cambio de Moneda Extranjera con 
Múltiples Tipos de Comprobantes
Descripción del Problema:
Un sistema de tesorería tiene a disposición una variedad de comprobantes que 
incluyen monedas, cheques, bonos y otros documentos financieros. Cada 
comprobante tiene un valor específico. El objetivo es realizar una compra de 
moneda extranjera minimizando el número de comprobantes utilizados.
Resolver mediante pseudocódigo e implementación java.
Indicar la complejidad algorítmica.

Ejemplo 2: Problema Mochila Fraccional
En la versión fraccional del problema de la mochila (Fractional Knapsack), el 
objetivo es maximizar el valor total de los objetos seleccionados para incluir en 
una mochila con una capacidad limitada. Dado que se permite tomar fracciones 
de objetos, este problema se resuelve de manera óptima utilizando un algoritmo 
voraz (greedy).

Ejemplo 2: Problema Mochila Fraccional
Método greedy:
Calcular la relación valor/peso: Para cada objeto i , se calcula la relación Vi/Wi 
donde Vi es el valor del objeto y Wi es su peso.
Ordenar los objetos: Se ordenan los objetos en orden descendente según su 
relación valor/peso.
Selección: Se seleccionan los objetos en el orden determinado, llenando la 
mochila con tanto peso como sea posible de cada objeto. Si la mochila no puede 
contener el objeto completo, se toma la fracción del objeto que llena la capacidad 
restante.

Ejemplo 2: Problema Mochila Fraccional
Este método tiene una complejidad temporal de O(n log n) debido al paso de 
ordenamiento, seguido de un paso lineal O(n) para seleccionar los objetos, lo que 
lo hace eficiente para esta variante del problema.

Ejemplo 2: pseudocódigo
Entrada: 
  - Un conjunto de n objetos, cada uno con un valor v[i] y un peso w[i].
  - Capacidad total de la mochila W.
Salida:
  - Valor máximo que se puede obtener en la mochila.

Ejemplo 2: pseudocódigo
Procedimiento FractionalKnapsack(n, v, w, W)
  1. Para i de 1 a n
       a. Calcular la relación valor/peso para cada objeto: ratio[i] = v[i] / w[i]
  2. Ordenar los objetos en orden descendente según ratio[i]
  3. Inicializar maxValue = 0
  4. Para i de 1 a n
       a. Si W == 0, terminar
       b. Si w[i] <= W
            i. maxValue = maxValue + v[i]
            ii. W = W - w[i]
       c. Sino
            i. maxValue = maxValue + (v[i] * (W / w[i]))
            ii. W = 0
  5. Devolver maxValue

Ejemplo 2: implementación en java
La implementación está en el repo de la materia.

Prueba de escritorio
Objeto 1: Valor = 30, Peso = 10 (Relación valor/peso = 3.0)
Objeto 2: Valor = 50, Peso = 20 (Relación valor/peso = 2.5)
Objeto 3: Valor = 60, Peso = 30 (Relación valor/peso = 2.0)
Capacidad de la mochila: 50
Orden descendente (correcto): Objeto 1, Objeto 2, Objeto 3
Tomar Objeto 1 completo (Valor = 30, Peso = 10, Capacidad restante = 40)
Tomar Objeto 2 completo (Valor = 50, Peso = 20, Capacidad restante = 20)
Tomar 2/3 del Objeto 3 (Valor = 40, Peso = 20, Capacidad restante = 0)
Valor total = 30 + 50 + 40 = 120

Actividad 3
Una empresa distribuidora necesita cargar un camión con mercancía que se 
puede fraccionar. Indicar con una lista los elementos a subir al camión para 
maximizar el valor total, dado que el camión tiene una capacidad limitada.

Actividad 4
Ingresar a https://www.hackerrank.com/challenges/greedy-florist
Resolver el desafío y luego subir el código fuente a teams y github.

PROGRAMACIÓN 3
Clase 4


Temas de la clase 4
Algoritmo sobre Grafos no dirigidos: 
Concepto de Árboles de Recubrimiento Mínimo. 
Prim y Kruskal. 
Algoritmos de Grafos dirigidos: 
Caminos de Mínimos Peso. 
Resolución de problemas utilizando Dijkstra.

Grafos. Definición
Un grafo es una estructura matemática que modela una red de relaciones entre 
objetos. Un grafo G se define como un par ordenado (V,E) donde:
V es un conjunto de vértices (también llamados nodos o puntos).
E es un conjunto de aristas (también llamados enlaces o arcos) que conectan 
pares de vértices. Cada arista e se representa como un par de vértices (u,v) 
donde u y v son vértices en V

Tipos de grafos
Grafo no dirigido: Un grafo en el que las aristas no tienen dirección. Es decir, la arista 
(u,v) es la misma que (v,u)
Grafo dirigido (digráfo): Un grafo en el que las aristas tienen una dirección. Es decir, la 
arista (u,v) es distinta de (v,u)
Grafo ponderado: Un grafo en el que cada arista tiene un peso asociado, que puede 
representar costos, distancias, tiempos, etc.
Grafo conexo: Un grafo no dirigido es conexo si hay un camino entre cualquier par de 
vértices. En un grafo dirigido, se dice que es fuertemente conexo si hay un camino 
dirigido entre cualquier par de vértices.
Árbol: Un grafo conexo y acíclico. En otras palabras, es un grafo sin ciclos que está 
totalmente conectado.

Representaciones de Grafos
Lista de adyacencia: Se usa un array de listas donde cada índice representa un 
vértice y la lista en ese índice contiene los vértices adyacentes.

Lista de adyacencia en Java
// Lista de adyacencia en Java
List<List<Integer>> grafo = new ArrayList<>();
// Agregar listas de adyacencia para cada vértice
for (int i = 0; i < numVertices; i++) {
    grafo.add(new ArrayList<>());
}
// Agregar aristas
grafo.get(0).add(1); // A - B
grafo.get(0).add(2); // A - C
grafo.get(1).add(3); // B - D
grafo.get(2).add(3); // C - D

Actividad 1
Desarrolla un programa que modele un sistema de seguidores en una red social 
utilizando un grafo representado con una lista de adyacencia. En este sistema, 
cada usuario puede seguir a otros usuarios, y queremos almacenar y consultar 
estas relaciones de manera eficiente.
Especificaciones:
Representación del Grafo:
Utiliza una lista de adyacencia para representar el grafo. En esta representación, 
cada nodo (usuario) tiene una lista de nodos a los que sigue (usuarios que lo 
siguen).

Actividad 1 (continuación)
Estructuras de Datos:
Usa una clase o estructura Usuario que tenga un identificador único (por ejemplo, un 
nombre o un número de ID).
Utiliza un diccionario o un mapa para mantener la lista de adyacencia, donde cada 
clave es un Usuario y el valor asociado es una lista de usuarios que ese usuario sigue.
Operaciones Requeridas:
Agregar Usuario: Permite agregar un nuevo usuario al sistema.
Seguir: Permite que un usuario siga a otro. Si el usuario ya sigue al destinatario, la 
operación no debe tener efecto.
Dejar de Seguir: Permite que un usuario deje de seguir a otro. Si el usuario no sigue al 
destinatario, la operación no debe tener efecto.
Lista de Seguidores: Permite consultar la lista de usuarios que sigue un usuario dado.
Lista de Seguidores de: Permite consultar la lista de usuarios que siguen a un usuario 
dado.

Representaciones de Grafos
Matriz de adyacencia: Se usa una matriz n×n (donde n es el número de vértices) 
donde la entrada en la fila i y columna j es 1 si hay una arista entre los vértices 
i y j, y 0 en caso contrario.

Matriz de adyacencia en Java
// Matriz de adyacencia en Java
int[][] grafo = new int[numVertices][numVertices];
// Agregar aristas
grafo[0][1] = 1; // A - B
grafo[0][2] = 1; // A - C
grafo[1][3] = 1; // B - D
grafo[2][3] = 1; // C - D

Actividad 2
Implementación de un Grafo en una Matriz de Adyacencia
Objetivo: Implementar un grafo utilizando una matriz de adyacencia en Java y 
realizar varias operaciones para manipular y consultar el grafo.
Descripción del Problema:
Dado un grafo dirigido, tu tarea es implementar las siguientes operaciones 
utilizando una matriz de adyacencia:
Operaciones: Inicialización del Grafo, Agregar Arista, Eliminar Arista, Verificar 
Arista, Listar Adyacentes, Contar Grado de Entrada y Salida: Implementa métodos 
para contar el grado de salida (número de aristas que salen) y el grado de entrada 
(número de aristas que entran) de un vértice dado.

Peso de un grafo
El peso de una arista es un valor numérico que se asocia a la arista en el grafo. 
Este valor puede representar diversas cosas según el contexto del problema, 
como el costo, la distancia, el tiempo, la capacidad o cualquier otra medida que se 
pueda cuantificar y que sea relevante para el análisis del grafo.
Grafo ponderado, es un grafo en el que cada arista tiene un peso asociado se 
denomina grafo ponderado. Los pesos permiten que las aristas no sean 
simplemente conexiones entre vértices, sino que también aporten información 
adicional que puede ser crucial para resolver problemas de optimización y 
análisis.

Peso de un grafo, representación
En una matriz de adyacencia, los pesos de las aristas se almacenan en la matriz. 
Para un grafo dirigido, la entrada en la posición (i,j) de la matriz representa el 
peso de la arista desde el vértice i hasta el vértice j. En un grafo no dirigido, esta 
matriz es simétrica.
En una lista de adyacencia, cada lista de vértices adyacentes a un vértice dado 
puede contener tuplas o pares en lugar de simples vértices. Cada tupla contiene 
el vértice adyacente y el peso de la arista que lo conecta.

Ejemplo
Considera un grafo ponderado con tres vértices y aristas con pesos:
●Vértices: A, B, C
●Aristas y pesos:
○A → B con peso 5
○A → C con peso 10
○B → C con peso 2
La matriz de adyacencia para este grafo sería:
    A   B   C
A  [ 0,  5, 10 ]
B  [ 0,  0,  2 ]
C  [ 0,  0,  0 ]

Ejemplo (continuación)
En la lista de adyacencia, se representaría como:
A: (B, 5), (C, 10)
B: (C, 2)
C: (vacío)

Aplicaciones del peso de un grafo
Los pesos en un grafo son fundamentales para varios problemas en teoría de grafos y 
algoritmos, como:
Algoritmo de Dijkstra: Encuentra el camino más corto desde un vértice a todos los 
demás vértices en un grafo ponderado.
Algoritmo de Kruskal y Prim: Encuentra el árbol de expansión mínima en un grafo 
ponderado.
Problemas de flujo máximo: Determinan el flujo máximo en una red donde las aristas 
tienen capacidades (pesos).
Los pesos permiten modelar y resolver problemas complejos en diversos campos 
como redes de comunicación, logística, planificación y más.

Árbol de Recubrimiento Mínimo 
(Minimum Spanning Tree - MST):
Un Árbol de Recubrimiento Mínimo de un grafo ponderado es un subgrafo que 
cumple las siguientes propiedades:
Subgrafo Conexo: El árbol de recubrimiento mínimo debe ser un subgrafo conexo 
que incluye todos los vértices del grafo original.
Sin Ciclos: Como su nombre indica, es un árbol, por lo que no debe contener 
ciclos. Esto garantiza que sea una estructura acíclica.
Peso Total Mínimo: La suma de los pesos de las aristas en el árbol es la mínima 
posible entre todos los posibles árboles de recubrimiento. En otras palabras, es el 
árbol de recubrimiento con el menor costo total.

Aplicaciones del Árbol de Recubrimiento Mínimo:
Redes de comunicación: Al diseñar redes de telecomunicaciones, como redes de telefonía o 
Internet, el MST se utiliza para minimizar el costo de cables o conexiones entre nodos de la red.
Diseño de circuitos: En el diseño de circuitos eléctricos, el MST puede ayudar a minimizar la 
cantidad de cableado necesario para conectar varios componentes.
Problemas de rutas: En logística y transporte, se puede usar el MST para encontrar la ruta más 
barata o corta que conecte múltiples destinos.
Agrupamiento de datos: En algoritmos de agrupamiento (clustering), el MST puede servir para 
agrupar datos de manera eficiente, especialmente en técnicas como el clustering jerárquico.

Algoritmo de Prim
Es un algoritmo para encontrar el árbol de recubrimiento mínimo de un grafo no 
dirigido. Funciona expandiendo el MST un nodo a la vez, eligiendo siempre la 
arista de menor peso que conecta un nodo dentro del MST con un nodo fuera del 
MST.

Algoritmo de Prim
El algoritmo de Prim es un método para encontrar el árbol de costo mínimo en un 
grafo no dirigido y conectado. Funciona de la siguiente manera:
Inicialización : Comienza con un nodo arbitrario del grafo y se incluye en el árbol. El 
conjunto de nodos incluidos se llama T.
Expansión del árbol : Repetidamente, se selecciona la arista de menor peso que 
conecta un nodo en T con un nodo fuera de T. Este nuevo nodo se añade al conjunto 
T y la arista se incluye en el árbol.
Repetición: El proceso se repite hasta que todos los nodos del grafo estén incluidos 
en T.
Finalización : Una vez que todos los nodos están en T, el conjunto de aristas 
seleccionadas forma el árbol de costo mínimo.

Links
Visualization Tool - AlgoVis.io : En esta página, puedes dibujar tu propio grafo o 
generar uno al azar y ver cómo el algoritmo de Prim encuentra el árbol de 
recubrimiento mínimo paso a paso. Además, puedes ajustar la velocidad de la 
animación y entender los procesos que realiza el algoritmo en tiempo real.
Prim's Algorithm Visualization - USFCA : Esta herramienta te permite interactuar 
con un grafo y ver cómo se seleccionan las aristas para construir el árbol de 
recubrimiento mínimo. Es útil para observar el comportamiento del algoritmo en 
diferentes tipos de grafos.

Pseudocódigo del algoritmo de prim en base a lista
1- Inicialización:
Crear un arreglo key[] que almacene los pesos mínimos para incluir cada vértice 
en el árbol. Inicializar todos los valores como infinitos, excepto el primero que se 
inicializa en 0.
Crear un arreglo parent[] para almacenar el árbol de recubrimiento mínimo.
Crear un arreglo inMST[] que indique si un vértice ya está incluido en el árbol 
(inicialmente false para todos los vértices).

Pseudocódigo del algoritmo de prim en base a lista ady.
2 Para cada vértice:
Encuentra el vértice u que no está en inMST[] y tiene el valor key[] más bajo.
Incluir u en el árbol (inMST[u] = true).
3 Actualizar valores:
Para cada vértice adyacente v de u, si v no está en el árbol y el peso de la arista 
(u, v) es menor que key[v], entonces:
Actualiza key[v] al peso de la arista (u, v).
Actualiza parent[v] para que apunte a u.
4 Repetir hasta que todos los vértices estén en el árbol.

Implementación del algoritmo de prim en base a lista ady.
Una posible implementación está en el repo de la materia.

Actividad 3
Diseño de una red de distribución eléctrica
Una empresa de energía necesita conectar varias estaciones eléctricas en una 
región para asegurar que toda la zona esté alimentada de manera eficiente. Las 
estaciones están ubicadas en diferentes ciudades y los costos de instalación de 
las líneas eléctricas entre ellas varían según la distancia y el terreno.
Tareas:
Representar el grafo utilizando una lista de adyacencia.
Aplicar el algoritmo de Prim para determinar el Árbol de Recubrimiento Mínimo.
Mostrar el conjunto de conexiones resultante y calcular el costo total.

Algoritmo de Kruskal
Es otro algoritmo para encontrar el árbol de recubrimiento mínimo de un grafo no 
dirigido. Funciona ordenando todas las aristas por peso y añadiendo las una a 
una al Árbol de Recubrimiento Mínimo ( Minimum Spanning Tree -MST ), evitando 
la formación de ciclos, el objetivo es conectar todos los vértices del grafo con el 
menor costo posible.

Pseudocódigo del algoritmo de Kruskal
Inicialización:
Empieza con un conjunto de aristas vacío. Este conjunto se convertirá en el árbol de 
recubrimiento mínimo.
Ordena todas las aristas del grafo en orden creciente de peso.
Proceso:
Recorre las aristas, comenzando por la de menor peso.
Para cada arista, verifica si su adición al conjunto de aristas ya seleccionadas forma un ciclo. Esto 
se hace utilizando una estructura de datos como Union-Find (también conocida como Disjoint 
Set).
Si agregar la arista no forma un ciclo, la arista se agrega al árbol de recubrimiento mínimo.
Finalización:
El proceso se repite hasta que se hayan agregado V−1 aristas (donde V es el número de vértices 
del grafo).
El conjunto de aristas resultante constituye el árbol de recubrimiento mínimo.

Implementación del algoritmo de Kruskal
Una posible implementación está en el repo de la materia.

Ventajas del Algoritmo de Kruskal:
Simplicidad: Es más fácil de implementar, especialmente en grafos dispersos (es 
decir, grafos con pocas aristas en comparación con el número de nodos).
Aplicabilidad: Funciona bien para grafos dispersos y es independiente de la 
estructura inicial del grafo.

Complejidad del algoritmo de Kruskal 
Ordenación de las aristas: Para ordenar las aristas según sus pesos, el tiempo 
necesario es O(E log E), donde E es el número de aristas. Dado que E es como 
máximo V^2 (donde V es el número de vértices), este paso también se puede 
expresar como O(E log V)
Operaciones de Union-Find: El algoritmo utiliza una estructura de datos llamada 
Union-Find para gestionar y unir los conjuntos de vértices, y verificar si la adición 
de una arista formaría un ciclo. El tiempo para realizar todas estas operaciones es 
aproximadamente O(E log V)
Por lo tanto, la complejidad total del algoritmo de Kruskal es:  O(E log E)

Comparación entre algoritmo de Prim y Kruskal
Algoritmo de Prim:
Es más eficiente con grafos densos, donde el número de aristas E es cercano a 
V^2
Algoritmo de Kruskal:
Es más eficiente con grafos dispersos, donde el número de aristas E es mucho 
menor que V^2.

Grafos Dirigidos
Un grafo dirigido (también conocido como digrafo) es una estructura matemática 
compuesta por un conjunto de vértices V y un conjunto de aristas E donde cada 
arista tiene una dirección asociada, es decir, cada arista va de un vértice a otro, 
pero no necesariamente en sentido contrario. Esto contrasta con los grafos no 
dirigidos, donde las aristas no tienen dirección y simplemente conectan dos 
vértices.

Grafo dirigido  ( http://www.webgraphviz.com )


Conceptos Clave en Grafos Dirigidos:
Vértices y Aristas:
Los vértices (o nodos) son los puntos o entidades que el grafo conecta.
Las aristas (o enlaces) en un grafo dirigido se representan como pares ordenados 
de vértices (u,v), donde u es el vértice de origen y v es el vértice de destino.
Grado de un Vértice:
Grado de entrada (in-degree): Es el número de aristas que llegan a un vértice.
Grado de salida (out-degree): Es el número de aristas que salen de un vértice.
Camino Dirigido:
Un camino dirigido es una secuencia de aristas donde la dirección de cada arista 
sigue el recorrido especificado, es decir, si hay un camino desde u a v , entonces 
hay una sucesión de aristas (u,v 1),(v1,v2),…,(v sub k−1,v)

Conceptos Clave en Grafos Dirigidos:
Ciclo Dirigido:
Un ciclo dirigido es un camino dirigido en el que el vértice inicial y final son el 
mismo y no se repiten otros vértices en el camino.
Fuentes y Sumideros:
Un vértice en un grafo dirigido es una fuente si su grado de entrada es cero (no 
recibe aristas), y es un sumidero si su grado de salida es cero (no tiene aristas 
salientes).
Componentes Fuertemente Conexas:
Una componente fuertemente conexa de un grafo dirigido es un subgrafo en el 
que existe un camino dirigido entre cualquier par de vértices. Es un concepto 
clave en el análisis de la estructura interna de un grafo dirigido.

Aplicaciones de Grafos Dirigidos:
Modelos de Redes: Como en Internet, donde los enlaces tienen direcciones.
Diagramas de Dependencia: Como en proyectos de gestión (diagramas de 
PERT).
Modelado de Flujo: En procesos de negocios, flujos de trabajo, o circuitos.

Representación de grafos dirigidos
Los grafos dirigidos se pueden representar mediante matrices de adyacencia o 
listas de adyacencia, similar a los grafos no dirigidos, pero teniendo en cuenta la 
dirección de las aristas.
Ejemplo:
Imaginemos un grafo dirigido simple con los vértices 
A, B, y C y aristas (A→B), (B→C), (C→A). 
Este grafo tendría un ciclo dirigido que conecta los tres vértices.

Representacion grafos dirigidos
La implementación básica para un grafo dirigido y un grafo no dirigido utilizando listas de 
adyacencia es prácticamente la misma. La diferencia principal radica en la interpretación y las 
reglas que aplicas a la estructura. En un grafo no dirigido, cuando añades una arista entre dos 
vértices A y B, debes asegurarte de que ambos vértices se agreguen a las listas de adyacencia 
del otro. Es decir, si hay una conexión entre A y B, entonces:
B debe estar en la lista de adyacencia de A. A debe estar en la lista de adyacencia de B.
En un grafo dirigido, la arista tiene una dirección, y sólo añades un vértice a la lista de adyacencia 
de otro si existe una conexión específica en esa dirección. Por ejemplo, si hay una conexión de A 
a B pero no de B a A, entonces solo: B estará en la lista de adyacencia de A.
Esto actúa como una restricción o un invariante de representación: en un grafo dirigido, las aristas 
no tienen reciprocidad a menos que se añadan explícitamente ambas direcciones. La estructura 
subyacente, sin embargo, sigue siendo la misma lista de adyacencia.

Problema de los caminos más cortos con un solo origen
El problema de los caminos más cortos con un solo origen es un problema clásico 
en la teoría de grafos que consiste en encontrar la distancia mínima desde un 
vértice de origen específico a todos los demás vértices en un grafo. Este 
problema es fundamental en muchas aplicaciones, como en sistemas de 
navegación, redes de comunicación y análisis de redes.
Definición Formal : Dado un grafo ponderado G=(V,E), donde V es el conjunto de 
vértices y E es el conjunto de aristas con pesos asociados, el objetivo es 
encontrar el camino más corto desde un vértice de origen s (source) a todos los 
demás vértices v en V. Los pesos de las aristas pueden representar distancias, 
costos, tiempos o cualquier otra medida que se desee minimizar.

Algoritmos para Resolver el Problema
Algoritmo de Dijkstra
Algoritmo de Bellman-Ford
Algoritmo de Floyd-Warshall
Aplicaciones
El problema de los caminos más cortos con un solo origen es aplicable en una amplia 
variedad de escenarios:
Sistemas de rutas: Encontrar la ruta más corta desde una ubicación a todas las 
demás.
Redes de telecomunicaciones: Optimizar el enrutamiento de datos desde un servidor a 
todos los clientes.
Análisis de redes sociales: Identificar la influencia o conectividad de un individuo en 
relación con todos los demás en la red.

Algoritmo de Dijkstra
Este algoritmo determina la distancia mínima desde un vértice de origen 
específico a todos los demás vértices en un grafo ponderado, donde los pesos de 
las aristas son no negativos.
Definición Formal
Dado un grafo ponderado G=(V,E), donde V es el conjunto de vértices y E es el 
conjunto de aristas con pesos asociados, el objetivo del algoritmo de Dijkstra es 
encontrar la distancia más corta desde un vértice de origen s a todos los demás 
vértices v en V.

Links
https://visualgo.net/en/sssp
https://vizalgo.netlify.app/public/graph/graph.html

Supuestos del Algoritmo de Dijkstra
Pesos no negativos: El algoritmo de Dijkstra asume que todos los pesos de las 
aristas son no negativos. Si existen aristas con pesos negativos, el algoritmo 
puede producir resultados incorrectos.
Grafos dirigidos o no dirigidos: El algoritmo puede aplicarse tanto a grafos 
dirigidos como no dirigidos, siempre y cuando se respeten los pesos no negativos.

Funcionamiento del Algoritmo de Dijkstra
El algoritmo de Dijkstra sigue una estrategia greedy, seleccionando en cada paso 
el vértice con la distancia mínima conocida desde el origen que aún no ha sido 
procesado. A continuación, se detallan los pasos del algoritmo:
1-Inicialización:
Asignar una distancia infinita a todos los vértices excepto al vértice de origen 
s, al cual se le asigna una distancia de 0.
Crear un conjunto vacío S que almacenará los vértices cuyas distancias mínimas 
desde s ya han sido determinadas.
Utilizar una cola de prioridad (por ejemplo, un heap) para seleccionar el vértice 
con la distancia mínima en cada iteración.

Funcionamiento del Algoritmo de Dijkstra
2-Selección del Vértice de Distancia Mínima:
Extraer de la cola de prioridad el vértice u con la distancia mínima conocida desde s. 
Añadir u al conjunto S.
3-Relajación de Aristas:
Para cada vértice adyacente v a u, realizar la siguiente operación:
Calcular una posible nueva distancia d(u)+w(u,v), donde w(u,v) es el peso de la arista 
(u,v). Si esta nueva distancia es menor que la distancia actualmente conocida d(v), 
actualizar d(v) con el nuevo valor y actualizar la posición de 
v en la cola de prioridad.
4-Repetición:
Repetir los pasos 2 y 3 hasta que la cola de prioridad esté vacía o hasta que se hayan 
procesado todos los vértices.

Prueba de escritorio para el algoritmo de Dijkstra
Supongamos un grafo dirigido y ponderado con cuatro nodos: A, B, C, y D. 
Los pesos de las aristas son los siguientes:
A -> B: 1
A -> C: 4
B -> C: 2
B -> D: 6
C -> D: 3
Queremos encontrar el camino más corto desde el nodo A hasta todos los demás 
nodos.

Paso 1: Inicialización
Distancias inciales:
d[A] = 0 (distancia desde A a si mismo)
d[B] = ∞
d[C] = ∞
d[D]  = ∞

Paso 2: Evaluación del nodo A
Evaluamos sus vecinos B y C
d[B] = min(∞, 0+1) = 1
d[C] = min(∞, 0+4) = 4
Distancias:
d[A] = 0
d[B] = 1
d[C] = 4
d[D] = ∞
Marcamos A como visitado

Paso 3: Evaluación del nodo B
Seleccionamos el nodo con la menor distancia no visitado, que es B
Evaluamos sus vecinos C y D
d[C] = min(4, 1+2) = 3
d[D] = min(∞, 1+6) = 7
Distancias:
d[A] = 0d[B] = 1d[C] = 3d[D] = 7
Marcamos B como visitado

Paso 4: Evaluación del nodo C
Seleccionamos el siguiente nodo con la menor distancia no visitado, C
Evaluamos su vecino D
d[D] = min (7, 3+3) = 6
Distancias d[A] = 0, d[B] = 1, d[C] = 3, d[D] = 6
Marcamos C como visitado

Paso 5: Evaluación del nodo D
Solo queda D, y no tiene más vecinos que puedan actualizar distancias.
Marcamos D como visitado.

Resultado final
Las distancias más cortas desde A a los demás nodos son:
A -> B: 1
A -> C: 3
A -> D: 6
Esta prueba de escritorio muestra com Dijkstra determina de manera eficiente las 
distancias más cortas desde el nodo inicial a todos los demás nodos en un gráfico 
dirigido y ponderado.

Pseudocódigo del Algoritmo de Dijkstra
function Dijkstra(G, s):
    // G es el grafo, s es el vértice de origen
    dist = array of size |V|, inicializado a ∞
    prev = array of size |V|, inicializado a NIL
    dist[s] = 0
    // Crear una cola de prioridad Q y añadir todos los vértices
    Q = PriorityQueue()
    for cada vértice v en V:
        Q.insert(v, dist[v])
    while not Q.isEmpty():
        u = Q.extractMin()
        for cada vecino v de u:
            alt = dist[u] + peso(u, v)
            if alt < dist[v]:
                dist[v] = alt
                prev[v] = u
                Q.decreaseKey(v, alt)
    return dist, prev

Implementación en java del algoritmo de Dijkstra
La implementación está en el repo de la materia.

Complejidad del algoritmo de Dijkstra
La eficiencia del algoritmo de Dijkstra depende de la implementación de la cola de 
prioridad:
Usando una cola de prioridad basada en un heap binario: La complejidad 
temporal es 
O((V+E) log V ), donde V es el número de vértices y E es el número de aristas.
Usando una cola de prioridad basada en un heap de Fibonacci: La complejidad 
temporal mejora a 
O(E+V log V).

Actividad 4
Una empresa de logística tiene varios centros de distribución en diferentes 
ciudades de una región y necesita optimizar las rutas de entrega de sus 
camiones. Cada centro de distribución está conectado a otros centros mediante 
carreteras, y cada carretera tiene un tiempo de viaje asociado en minutos. La 
empresa desea minimizar el tiempo total de entrega desde su centro de 
distribución principal hasta todas las otras ciudades.
Objetivo:
Aplicar el algoritmo de Dijkstra para encontrar el tiempo mínimo de entrega desde 
el centro de distribución principal hasta los demás centros, considerando las 
diferentes rutas disponibles.

Programación 3
Clase 5
Bibliografía: AHO, J.; HOPCROFT, John E. y ULLMAN, Jeffrey D.. 
Estructuras de Datos y Algoritmos. 1988. Wilmington : Addison 
Wesley Iberoamericana. 438 p. ISBN: 02016402449684443455.


Temas clase 5
Introducción a Técnica Programación Dinámica. Conceptos.
Resolución de problemas. 
Ejemplos, Análisis general de la complejidad de los problemas de Programación 
Dinámica.

Programación dinámica. Definición
La programación dinámica es una técnica de diseño de algoritmos que se utiliza 
para resolver problemas complejos dividiéndolos en subproblemas más simples y 
almacenando los resultados de estos subproblemas para evitar cálculos 
redundantes.

Principios
Subproblemas Superpuestos:
Muchos problemas pueden dividirse en subproblemas que se solapan, es decir, 
los mismos subproblemas se resuelven una y otra vez. La programación dinámica 
ahorra tiempo al resolver cada subproblema solo una vez y almacenar su solución 
en una tabla (a menudo llamada memorización) para futuras referencias.

Principios
Optimalidad Estructural:
La solución óptima de un problema se construye a partir de las soluciones 
óptimas de sus subproblemas. Esto implica que para encontrar la mejor solución a 
un problema, primero se encuentran las mejores soluciones a sus subproblemas.

Ejemplos de aplicación de la programación dinámica
1. Cadena de ADN y problemas de bioinformática : Problemas como la alineación 
de secuencias y la predicción de estructuras de proteínas suelen resolverse con 
programación dinámica. Estos problemas implican encontrar similitudes entre 
secuencias de ADN o proteínas, y la programación dinámica permite comparar 
grandes cantidades de datos biológicos de manera eficiente.
2. Reconocimiento de Voz : En los sistemas de reconocimiento de voz, se utiliza 
programación dinámica en técnicas como el algoritmo de Viterbi. Este algoritmo se 
emplea para encontrar la secuencia más probable de estados ocultos (como palabras 
o fonemas) en un modelo de Markov oculto, dado una secuencia observada de 
características de audio. Esta técnica es fundamental en la conversión de habla a 
texto, permitiendo que los sistemas interpreten y transcriban el habla humana de 
manera eficiente.

Ejemplos de aplicación de la programación dinámica
3. Procesamiento de Imágenes y Visión por Computadora : La programación 
dinámica se aplica en la corrección de distorsión de imágenes y en la alineación 
de imágenes (por ejemplo, en la creación de panoramas). En la "correspondencia 
estéreo" para obtener la profundidad a partir de imágenes, se utiliza para 
encontrar la mejor coincidencia de píxeles entre dos imágenes tomadas desde 
diferentes ángulos. Esto es clave en aplicaciones como la generación de mapas 
de profundidad para sistemas de conducción autónoma o realidad aumentada.

Ejemplos de aplicación de la programación dinámica
4. Optimización de Portafolios en Finanzas : En la gestión de portafolios, los 
inversores buscan la mejor manera de distribuir sus activos para maximizar el 
rendimiento esperado mientras minimizan el riesgo. La programación dinámica se 
utiliza en la optimización estocástica de portafolios, donde se evalúan distintas 
combinaciones de activos a lo largo del tiempo, considerando rendimientos 
esperados, volatilidad y correlación entre activos. Esta técnica permite a los 
inversores ajustar dinámicamente sus estrategias de inversión basándose en la 
evolución del mercado.

Problemas Resueltos con Programación Dinámica:
Problema de la mochila (Knapsack problem)
Secuencia más larga común (Longest common subsequence)
Subcadena palindrómica más larga (Longest palindromic substring)
Caminos más cortos en grafos (algoritmo de Floyd-Warshall)
Algoritmo de alineación de secuencias en bioinformática

Ejemplo
Optimización de Estrategias de Juego (Asignación de Recursos)
Imagina que un equipo de fútbol tiene un presupuesto limitado para fichar 
jugadores y desea maximizar su rendimiento en una temporada. 
Objetivo: Maximizar el rendimiento general del equipo (por ejemplo, el puntaje 
esperado en una temporada) dado un presupuesto limitado para fichar jugadores.
Variables: Cada jugador tiene un costo (precio de fichaje) y una contribución al 
rendimiento del equipo.
Problema: Determinar qué combinación de jugadores debe ficharse para 
maximizar el rendimiento total sin exceder el presupuesto.
Aquí, se utiliza programación dinámica para calcular la mejor combinación de 
jugadores que maximiza el rendimiento mientras se ajusta al presupuesto.

Comparación de técnicas
 Problema: Determinar qué combinación de jugadores debe ficharse para 
maximizar el rendimiento total sin exceder el presupuesto.
Se puede solucionar con:
●Algoritmo de fuerza bruta 
●Algoritmo greedy
●Algoritmo de programación dinámica.

Solución al ejemplo con algoritmo de fuerza bruta
En este enfoque, generamos todas las posibles combinaciones de jugadores y 
calculamos el rendimiento total y el costo de cada combinación. Luego, 
seleccionamos la combinación que maximiza el rendimiento sin exceder el 
presupuesto.
Para un conjunto de n jugadores, cada jugador tiene dos posibles estados: ser 
fichado o no ser fichado. Esto genera 
2 ^n  combinaciones, ya que cada jugador tiene dos opciones. Por lo tanto, 
efectivamente, la complejidad temporal de esta solución O(2 ^ n  ).

Explicación de la implementación
La implementación está en el repo de la materia.
mejorCombinacion(): Recorre todas las combinaciones posibles de jugadores 
usando un bucle que va desde 0 hasta 2^n . Aquí, utilizamos los bits de un entero 
para representar si un jugador es fichado (bit activado) o no (bit apagado).
Para cada combinación, sumamos los costos y rendimientos de los jugadores 
seleccionados.
Si el costo total no excede el presupuesto y el rendimiento es mayor que el mejor 
encontrado hasta ahora, actualizamos el mejor rendimiento.

Prueba de escritorio 
Para el ejemplo con 3 jugadores, hay 2^3 =8 combinaciones posibles.
Combinación 000: No seleccionamos a nadie → Rendimiento: 0, Costo: 0
Combinación 001: Solo seleccionamos al Jugador 3 → Rendimiento: 70, Costo: 4
Combinación 010: Solo seleccionamos al Jugador 2 → Rendimiento: 50, Costo: 3
Combinación 011: Jugadores 2 y 3 → Rendimiento: 120, Costo: 7 (mejor)
Combinación 100: Solo seleccionamos al Jugador 1 → Rendimiento: 60, Costo: 5
Combinación 101: Jugadores 1 y 3 → Rendimiento: 130, Costo: 9 (Excede el 
presupuesto)
Combinación 110: Jugadores 1 y 2 → Rendimiento: 110, Costo: 8
Combinación 111: Jugadores 1, 2 y 3 → Rendimiento: 180, Costo: 12 (Excede el 
presupuesto)

Solución al ejemplo con técnica greedy
Seleccionamos primero al jugador 3, ya que tiene la mayor relación rendimiento/costo (17.5). Esto 
nos cuesta 4 unidades del presupuesto y obtenemos un rendimiento de 70. Ahora tenemos un 
presupuesto restante de 8−4=4. Con este presupuesto, seleccionamos al jugador 2, porque tiene 
la segunda mejor relación rendimiento/costo (16.67), y aún nos queda suficiente presupuesto para 
ficharlo. Esto nos cuesta 3 unidades más, dejando un presupuesto restante de 1, y obtenemos un 
rendimiento adicional de 50. Con un presupuesto de 1, no podemos fichar más jugadores, ya que 
el jugador 1 cuesta 5. Rendimiento: Jugador 3 (70 puntos) + Jugador 2 (50 puntos) = 120 puntos.Jugador Costo Rendimiento Costo/Rendimiento
1 5 60 12
2 3 50 16,67
3 4 70 17.5

Ejemplo donde la técnica greedy falla
Con un presupuesto de 8, el enfoque greedy seleccionaría al jugador 3 por su 
mayor relación rendimiento/costo ( 90/6 =15), quedando con un presupuesto de 2, 
pero sin poder fichar más jugadores. El rendimiento total sería 90.Jugador Costo Rendimiento Costo/Rendimiento
1 5 60 12
2 3 50 16,67
3 6 90 15

Pasos para Diseñar un Algoritmo de Programación Dinámica:
1.Definir la Estructura de la Solución Óptima: Describir la relación entre la solución 
óptima del problema y las soluciones óptimas de sus subproblemas.
2. Definir Recursivamente el Valor de una Solución Óptima: Especificar una 
recurrencia que exprese el valor de la solución óptima en términos de los valores de 
las soluciones de sus subproblemas.
3. Calcular el Valor de una Solución Óptima de Forma Iterativa:Construir una tabla que 
almacene los valores de las soluciones de los subproblemas y llenar la tabla de 
manera iterativa, desde los subproblemas más pequeños hasta el problema original.
4. Construir la Solución Óptima a Partir de los Resultados Calculados:Usar la 
información almacenada en la tabla para construir la solución óptima al problema 
original.

Pseudocódigo
Definiciones:
n: Número de jugadores.
B: Presupuesto total.
costos[]: Array donde costos[i] es el costo del jugador i.
rendimientos[]: Array donde rendimientos[i] es la contribución al rendimiento del 
jugador i.

Pseudocódigo (continuación)
Inicialización:
Crear una tabla dp de tamaño (n+1) x (B+1) donde dp[i][j] representa el 
rendimiento máximo que se puede obtener con un presupuesto j utilizando los 
primeros i jugadores.
Construcción de la tabla:
Para cada jugador i de 1 a n:
Para cada presupuesto j de 0 a B:
Si el costo del jugador i es menor o igual a j, actualizar dp[i][j] como el 
máximo entre:
No incluir el jugador i (dp[i-1][j]).
Incluir el jugador i (dp[i-1][j-costos[i-1]] + rendimientos[i-1]).

Resultado
El valor en dp[n][B] es el rendimiento máximo que se puede obtener con el 
presupuesto B.
La implementación en java, está en el repo de la materia.

Prueba de escritorio con programación dinámica
Jugador 1: (costo 5, rendimiento 60) Jugador 2: (costo 3, rendimiento 50)
Jugador 3: (costo 4, rendimiento 70) Presupuesto (P) = 8
Paso 1: Crear la tabla de programación dinámica
i/j012345678
0000000000
10000060606060
20005050606060110
30005070707070120

Prueba de escritorio con programación dinámica
Paso 2: Completar la tabla
Jugador 1: Con costo 5 y rendimiento 60:
Si el presupuesto es menor a 5, no podemos incluirlo, así que dp[1][j]=0.
A partir de un presupuesto de 5, podemos incluir al jugador y el rendimiento es 60.
Jugador 2: Con costo 3 y rendimiento 50:
Para un presupuesto de 3, el mejor rendimiento es 50 (fichamos al jugador 2).
Para presupuestos mayores, decidimos si fichamos al jugador 2 junto al 1 o solo al 1. Para un 
presupuesto de 8, podemos fichar a ambos (rendimiento 110).
Jugador 3: Con costo 4 y rendimiento 70:
Para un presupuesto de 4, fichamos al jugador 3 y obtenemos 70.
Para presupuestos mayores, si fichamos a los jugadores 2 y 3, obtenemos 120.
Paso 3: Resultado
El rendimiento máximo con un presupuesto de 8 es 120, seleccionando a los jugadores 2 y 3.

Análisis de complejidad de la programación dinámica
En el enfoque de programación dinámica, se construye una tabla dp[i][j], donde 
i representa los jugadores considerados y j el presupuesto disponible.
Cada celda dp[i][j] almacena el rendimiento máximo al fichar jugadores dentro de un presupuesto j.
Para cada jugador, se compara ficharlo o no ficharlo usando la recurrencia:
dp[i][j]=max(dp[i−1][j],dp[i−1][j−ci]+ri).
La tabla tiene tamaño n×P, donde n es el número de jugadores y P el presupuesto.
Resolviendo n×P subproblemas, cada uno en tiempo constante O(1).
Por tanto, la complejidad total es O(n×P).
En comparación, el enfoque de fuerza bruta explora 2^n combinaciones posibles, lo que resulta en una 
complejidad O(2 ^n ).
La programación dinámica reduce la complejidad exponencial a polinomial, lo que es mucho más 
eficiente.
En resumen, la solución dinámica resuelve subproblemas de forma eficiente reutilizando resultados 
previos.
Esto garantiza una solución óptima en tiempo O(n×P), mucho más rápida que la fuerza bruta.

Actividad 1
Dada una mochila con una capacidad máxima de peso P, y dispones de n objetos. 
Cada objeto tiene un peso w  y un valor v 
 El objetivo es seleccionar algunos objetos de manera que maximicen el valor total, 
sin superar la capacidad de la mochila, que es de 6
Se pide realizar una prueba de escritorio para fuerza bruta y para programación 
dinámica, indicando el resultado.Objeto Peso Valor
1 3 4
2 4 5
3 2 3

Actividad 2
Dada una mochila con una capacidad 
máxima de peso P, y dispones de n 
objetos. Cada objeto tiene un peso w  y un 
valor v 
 El objetivo es seleccionar algunos objetos 
de manera que maximicen el valor total, 
sin superar la capacidad de la mochila, 
que es de 10
Se pide realizar una prueba de escritorio 
para fuerza bruta y para programación 
dinámica, indicando el resultado.ObjetoPesoValor
124
252
361
476

Recuperación de los caminos
La "recuperación de los caminos" en un problema de programación dinámica 
como el de la mochila 0/1, implica reconstruir qué elementos (en este caso, 
"jugadores") formaron parte de la solución óptima. Una vez que hemos llenado la 
matriz dp[][] con los máximos rendimientos, necesitamos recorrer la matriz hacia 
atrás para identificar qué elementos fueron seleccionados.

Recuperación de los caminos
Modificaciones necesarias para la recuperación del camino
Una vez que obtengas el valor óptimo (el rendimiento máximo), puedes recorrer la 
matriz dp[][] desde la posición final dp[n][presupuesto] y, comparando el valor de 
cada celda con la anterior, determinar si un elemento fue seleccionado. Para 
hacerlo:
Verifica si un elemento fue seleccionado:
Si dp[i][j] es diferente de dp[i-1][j], significa que el jugador i fue seleccionado. Esto 
es porque, al incluirlo, el valor cambia al sumar el rendimiento del jugador actual.
Si dp[i][j] es igual a dp[i-1][j], entonces el jugador no fue seleccionado.
Resta el costo del jugador seleccionado al presupuesto disponible y continúa con 
los elementos anteriores.

Prueba de escritorio de recuperación de los caminos 
La tabla dp nos dice que el rendimiento máximo con un presupuesto de 8 es 120. 
Ahora, para saber qué jugadores fueron seleccionados, seguimos estos pasos 
desde dp[3][8]:
dp[3][8] = 120: Comparado con dp[2][8] = 110, podemos ver que el jugador 3 fue 
seleccionado (porque 120 != 110).
Restamos el costo del jugador 3 (4) al presupuesto: 8 - 4 = 4.
Continuamos con dp[2][4].

Prueba de escritorio de recuperación de los caminos 
dp[2][4] = 50: Comparado con dp[1][4] = 0, podemos ver que el jugador 2 fue 
seleccionado (porque 50 != 0).
Restamos el costo del jugador 2 (3) al presupuesto: 4 - 3 = 1.
Continuamos con dp[1][1].
dp[1][1] = 0: No queda presupuesto para seleccionar al jugador 1.
Jugadores seleccionados:
Jugador 3 (índice 2, costo 4, rendimiento 70)
Jugador 2 (índice 1, costo 3, rendimiento 50)
El rendimiento total es 120, que es el máximo que se puede obtener con un 
presupuesto de 8.

Actividad 3
Problema: Selección óptima de proyectos
Eres el gerente de una empresa que debe decidir en qué proyectos invertir. Cada 
proyecto tiene un costo asociado y un beneficio esperado. Tienes un presupuesto 
limitado y necesitas elegir qué combinación de proyectos maximiza el beneficio 
total sin exceder el presupuesto.
Requerimientos:
Te proporcionarán un arreglo de costos que representa el costo de cada proyecto.
También te proporcionarán un arreglo de beneficios que indica el beneficio que se 
espera de cada proyecto.
Implementa un algoritmo que determine qué proyectos deben seleccionarse para 
maximizar el beneficio total sin exceder el presupuesto.

Actividad 3
Ejemplo: Supón que tienes los siguientes datos:
Costos de los proyectos: [10, 15, 20, 25]
Beneficios esperados de los proyectos: [100, 200, 150, 300]
Presupuesto disponible: 40
El programa debe calcular cuál es el beneficio máximo que puedes obtener 
respetando el presupuesto disponible.
Calcular utilizando algoritmos de fuerza bruta, greedy y programación dinámica. 
Indicar complejidades.

Actividad 4
Problema: Selección de paquetes de inversión
Eres un gestor financiero y tienes la tarea de seleccionar entre varios paquetes de 
inversión para maximizar las ganancias. Cada paquete tiene un costo inicial y una 
ganancia estimada. Sin embargo, tu presupuesto es limitado, por lo que debes elegir 
cuidadosamente qué paquetes comprar para maximizar las ganancias sin exceder el 
presupuesto.
Requerimientos :
Te proporcionarán un arreglo de costos, donde cada elemento representa el costo de 
un paquete de inversión.
También recibirás un arreglo de ganancias que representa la ganancia esperada de 
cada paquete.
Debes implementar un algoritmo que determine la combinación de paquetes que 
maximiza las ganancias totales sin superar el presupuesto disponible.

Actividad 4
Datos de Ejemplo:
Costos de los paquetes de inversión: [12, 20, 15, 25]
Ganancias esperadas: [150, 200, 100, 300]
Presupuesto disponible: 35
El programa debe calcular cuál es la ganancia máxima que puedes obtener 
respetando el presupuesto.
Calcular utilizando algoritmos de fuerza bruta, greedy y programación dinámica. 
Indicar complejidades.

Programación 3
Clase 8
Bibliografía: AHO, J.; HOPCROFT, John E. y ULLMAN, 
Jeffrey D.. Estructuras de Datos y Algoritmos. 1988. 
Wilmington : Addison Wesley Iberoamericana. 438 p. 
ISBN: 02016402449684443455.


Temas de la clase 8
Introducción a Técnica Backtracking. 
Conceptos Básicos. 
Análisis general de la complejidad. Ejemplos. 
Presentación y análisis del TPO.

Definición
Técnica algorítmica para resolver problemas mediante la construcción incremental 
de soluciones, eliminando aquellas que no satisfacen las condiciones del 
problema.
Esquema General del Backtracking:
Se exploran todas las posibilidades de solución, retrocediendo (backtracking) 
cuando se determina que una solución parcial no puede llevar a una solución 
completa válida.

Árbol de Búsqueda:
Representación de todas las posibles soluciones del problema en un árbol donde 
cada nodo representa una elección.
Criterios de Poda:
Reglas para descartar ramas del árbol de búsqueda que no pueden conducir a 
una solución válida.
Exploración Profunda (Depth-First Search):
Estrategia utilizada por Backtracking para explorar el árbol de búsqueda hasta 
una profundidad antes de retroceder.

Ámbitos en los que se usa backtracking
Resolución de juegos y puzzles.
Inteligencia artificial y búsqueda de soluciones: Los algoritmos como minimax 
para juegos de dos jugadores (como ajedrez o damas) son formas de 
backtracking
Satisfacción de restricciones (CSP): Problemas como el de coloración de grafos
Criptografía: en la fuerza bruta y en ataques de búsqueda de claves, el 
backtracking puede ser útil cuando se necesita explorar un gran espacio de 
combinaciones para encontrar una clave que satisfaga una condición particular

Backtraking tipo 1
Número fijo de llamadas recursivas por nivel
•Ejemplo clásico: subconjuntos (sí/no para cada elemento).
•El número de ramas es constante, independiente del estado.
•Se suele implementar con dos (o k) llamadas recursivas explícitas.

Problema: Generar todas las combinaciones posibles de un conjunto de números
Dado un conjunto de números {1, 2, 3}, queremos generar todas las combinaciones 
posibles de estos números.
Idea detrás del algoritmo:
El algoritmo de backtracking explora cada número en el conjunto, agregándolo a la 
combinación actual, y luego procede a agregar el siguiente número. Si la 
combinación está completa (es decir, si ha alcanzado un tamaño determinado o ha 
recorrido todos los elementos), la guardamos como una posible solución. Si no, 
retrocede y prueba con otra combinación.Ejemplo con Número fijo de llamadas recursivas por nivel

Ramas
La idea de las "ramas" en backtracking se refiere a la estructura del proceso de 
toma de decisiones, que se asemeja a un árbol. Cada vez que tomamos una 
decisión (incluir o no un elemento en una combinación), estamos creando una 
"bifurcación" en ese árbol de decisiones. Cada bifurcación genera una nueva 
posibilidad (una rama) que se explora recursivamente.
En este caso, el problema de generar todas las combinaciones de {1, 2, 3} puede 
representarse como un árbol binario, donde en cada nodo decidimos si 
agregamos o no un elemento a la combinación actual. El proceso continúa de 
forma recursiva hasta que llegamos al final del conjunto (las hojas del árbol), 
donde imprimimos la combinación generada.

Representación gráfica de las "ramas":
                                []
                      /                    \
                  [1]                        []
               /        \                   /        \
          [1,2]        [1]            [2]            []
          /     \      /    \           /    \           /    \
[1,2,3]  [1,2]  [1,3] [1]     [2,3]  [2]    [3]    []
Podemos representar el proceso de decisiones de forma gráfica como un árbol binario. Aquí el 
conjunto es {1, 2, 3}, y cada decisión genera dos opciones: incluir o no incluir el elemento.

Explicación del gráfico
Nodo raíz ([]): El árbol comienza con la combinación vacía. Desde aquí, en cada nivel, tenemos 
dos opciones: incluir o no incluir un elemento del conjunto.
Primer nivel (1): El primer nodo tiene dos hijos:
La rama izquierda representa la decisión de incluir el 1 en la combinación.
La rama derecha representa la decisión de no incluir el 1.
Segundo nivel (2): Para cada una de esas ramas, repetimos el mismo proceso con el número 2.
A la izquierda, tenemos las combinaciones que incluyen el 2.
A la derecha, no lo incluyen.
Tercer nivel (3): Finalmente, hacemos lo mismo con el número 3, y obtenemos las combinaciones 
completas al llegar al final del conjunto.
Hojas: Las combinaciones completas están representadas en las hojas del árbol. Estas hojas 
corresponden a las combinaciones que se imprimen en la salida del programa.

Descripción del algoritmo del ejemplo
Algoritmo para generar todas las combinaciones posibles de un conjunto de 
números
El algoritmo de backtracking explora cada número en el conjunto, agregándolo a 
la combinación actual, y luego procede a agregar el siguiente número. Si la 
combinación está completa (es decir, si ha alcanzado un tamaño determinado o 
ha recorrido todos los elementos), la guardamos como una posible solución. Si 
no, retrocede y prueba con otra combinación.

Pseudocódigo del ejemplo
procedure generarCombinaciones(conjunto, combinación_actual, posición):
    if posición == tamaño(conjunto):
        imprimir(combinación_actual)
        return    
        // Incluir el elemento en la combinación
    combinación_actual.agregar(conjunto[posición])
    generarCombinaciones(conjunto, combinación_actual, posición + 1)    
    // Retroceder y no incluir el elemento actual
    combinación_actual.quitar_último_elemento()
    generarCombinaciones(conjunto, combinación_actual, posición + 1)
procedure principal():
    conjunto = {1, 2, 3}
    combinación_actual = []
    generarCombinaciones(conjunto, combinación_actual, 0)

Explicación del código en Java
Una posible implementación, está en el repo de la materia.
generarCombinaciones toma un conjunto de números y una lista que representa 
la combinación actual.
Si hemos llegado al final del conjunto, imprimimos la combinación.
La función se llama recursivamente, primero incluyendo el elemento actual y 
luego "retrocediendo" para intentar no incluirlo.
Utilizamos un ArrayList para almacenar temporalmente las combinaciones 
mientras exploramos las diferentes posibilidades.

Complejidad
Complejidad Temporal:
La complejidad temporal del Backtracking en el peor de los casos es generalmente 
exponencial, debido a la exploración de todas las posibles soluciones.
Complejidad Espacial:
La complejidad espacial está determinada por la profundidad del árbol de búsqueda, 
que en el peor de los casos es la longitud de la solución.
Factores que Afectan la Complejidad:
Tamaño del problema, número de soluciones parciales, y eficiencia de los criterios de 
poda.

Ejercicio ejemplo con fuerza bruta
Dado un conjunto de números {1, 2, 3, 4}, generar todas las combinaciones 
posibles de estos números, menos la combinación que tengan cuatro números 
con el 2 en la segunda o tercera posición y el 3 en segunda o tercera posición.
Este es un enfoque típico de fuerza bruta donde primero generamos todas las 
posibles combinaciones (permutaciones) y luego eliminamos las que no cumplen 
con la condición dada. 
Consiste en generar todas las permutaciones posibles de los números {1,2,3,4} y 
luego filtrar las permutaciones que no cumplan la condición, es decir, eliminar 
aquellas que tengan el 2 en la segunda o tercera posición y el 3 en la segunda o 
tercera posición.

Ejercicio ejemplo con fuerza bruta, complejidad
Una posible implementación está en el repo de la materia, clase 8.
La complejidad temporal del algoritmo es dominada por el número de 
permutaciones generadas, es decir, O(n!).
En nuestro caso específico con n=4, la complejidad es 
O(4!)=O(24), pero las restricciones no afectan el número de permutaciones 
generadas, ya que solo se filtran al final.

Actividad 1
Ejercicio: Suma de subconjuntos con un objetivo
Consigna: Dado un conjunto de números {2, 3, 5}, generar todos los subconjuntos 
cuya suma sea 5.
Para cada número, tenemos dos opciones:
Incluir el número en el subconjunto. 
No incluirlo. 
Imprimir solo los subconjuntos que cumplen la condición de suma.
Ejemplo de salida: [2,3]   [5]
El subconjunto [2, 5] no se imprime porque 2+5 = 7 ≠ 5

Backtraking tipo 2
Número variable de llamadas recursivas por nivel
•Ejemplo clásico: N reinas (en una fila puede haber hasta n posiciones posibles, 
pero algunas se descartan).
•El número de ramas depende de las restricciones y del estado parcial.
•Se suele implementar con un bucle que contiene un único llamado recursivo.

Ejemplo n reinas
Según wikipedia: “El problema de las ocho reinas es un pasatiempo que consiste 
en poner ocho reinas en el tablero de ajedrez sin que se amenacen. Fue 
propuesto por el ajedrecista alemán Max Bezzel en 1848.1  2  En el juego del 
ajedrez la reina amenaza a aquellas piezas que se encuentren en su misma fila, 
columna o diagonal. El juego de las 8 reinas consiste en poner sobre un tablero 
de ajedrez ocho reinas sin que estas se amenacen entre ellas. Para resolver este 
problema se puede emplear un esquema vuelta atrás (o Backtracking).”
Ejemplo 8 reinas

Ejemplo n reinas
¿ Hay solución para un tablero de 3 filas y 3 columnas con 3 reinas ?
En un tablero de ajedrez, una reina puede atacar:
Horizontalmente: Es decir, en cualquier casilla de su misma fila.
Verticalmente: En cualquier casilla de su misma columna.
Diagonalmente: En cualquiera de las dos diagonales que pasen por la casilla 
donde está ubicada la reina.

Ejemplo n reinas
El objetivo es colocar las reinas de manera que ninguna pueda atacar a otra. Para 
resolver este problema, es útil hacer algunas observaciones:
1. Restricciones de las Reinas:
En un tablero de 3x3, solo tenemos 3 filas y 3 columnas. Dado que las reinas atacan 
en filas, columnas y diagonales, al colocar una reina en cualquier posición, se 
eliminarán varias posiciones posibles donde podríamos colocar la siguiente reina.
En un tablero pequeño como este, las opciones para colocar reinas sin que se 
ataquen son extremadamente limitadas debido a la superposición de las zonas de 
ataque de cada reina.

Ejemplo n reinas
Intento de Colocar las Reinas:
Supongamos que colocamos una reina en la posición (1,1) (esquina superior 
izquierda).
Esta reina atacará toda la fila 1, toda la columna 1 y las dos diagonales (principal y 
secundaria). Las posiciones atacadas por la primera reina serían:
R . .
. . .
. . .
Ahora intentamos colocar la segunda reina en cualquier otra posición disponible. Las 
únicas posiciones no atacadas son (2,3) y (3,2), pero si colocamos la segunda reina 
en cualquiera de esas posiciones, esta atacará tanto su fila como su columna, lo que 
hará imposible evitar un ataque.

Ejemplo n reinas
Conclusión:
En un tablero de 3x3, no existe una disposición válida para colocar 3 reinas sin 
que se ataquen entre sí. Esto se debe a que el tamaño del tablero es demasiado 
pequeño para separar las áreas de ataque de las reinas. La cantidad de casillas 
no es suficiente para que dos reinas coexistan sin superponer sus ataques en 
filas, columnas o diagonales.
Este problema refleja una de las razones por las que el problema de las N reinas 
tiene sentido a partir de tableros de tamaño 4x4 o mayores. En un tablero más 
pequeño, como el de 3x3, no hay suficiente espacio para separar a las reinas y 
evitar conflictos.

Ejemplo n reinas, links para analizar
NQueens Visualizer : En este sitio puedes visualizar soluciones para diferentes 
tamaños de tablero y ajustar el número de reinas, lo que te permite interactuar 
directamente con las soluciones del problema.
N-Queen Visualizer : Ofrece un tablero donde puedes colocar manualmente las 
reinas y comprobar visualmente si están atacándose entre sí. Puedes configurar 
tableros de tamaños personalizados.
N-Queens: Aquí puedes resolver el problema de las N reinas, configurando el 
tamaño del tablero y el número de reinas. Además, muestra visualmente qué 
casillas están amenazadas por las reinas que has colocado.

Ejemplo
Posicionamiento de dos reinas en un tablero de 4x4 usando Backtracking
Descripción del Problema: Dado un tablero de ajedrez de tamaño 4x4, debes 
encontrar todas las posibles posiciones donde se pueden colocar dos reinas de tal 
manera que no se ataquen entre sí. El objetivo es imprimir todas las configuraciones 
válidas del tablero. Las dos reinas no deben compartir la misma fila, columna ni estar 
en la misma diagonal.
Para resolver este problema, deberás usar la técnica de Backtracking para explorar 
las posibles posiciones de las reinas de manera eficiente. El backtracking te permitirá 
descartar las configuraciones inválidas a medida que avances en la construcción de 
las soluciones.

Análisis Matemático del Problema
Número total de formas de colocar dos reinas en el tablero (sin restricciones):
Un tablero de 4x4 tiene 16 casillas en total.
El número total de formas de seleccionar dos casillas para colocar dos reinas es 
una combinación de 16 casillas tomadas de 2 en 2:
C(16,2)= (16×15)/2  =120
Esto significa que hay 120 formas de colocar dos reinas en un tablero de 4x4 si no 
imponemos restricciones de ataque.
Eliminar configuraciones en las que las reinas se atacan: Las reinas pueden 
atacarse si están en:
La misma fila. La misma columna. La misma diagonal (diagonal principal o 
diagonal secundaria).

Análisis Matemático del Problema
Ahora contamos cuántas configuraciones de ataque existen para restarlas del total.
Misma fila: En cada fila hay 4 casillas, y se pueden elegir 2 de esas casillas para colocar dos 
reinas que se ataquen en la fila: C(4,2)= (4×3)/2 =6 formas por fila.
Como hay 4 filas, esto da un total de: 6×4=24 configuraciones de ataque en la misma fila.
Misma columna: Similar al caso de las filas, hay 4 columnas, y en cada columna también hay 6 
formas de colocar dos reinas que se ataquen en la misma columna. Esto nos da:
6×4=24 configuraciones de ataque en la misma columna.
Diagonales: Para la diagonal principal (de izquierda a derecha), tenemos las siguientes 
posibilidades:
Diagonal de longitud 2: Hay 2 formas de colocar dos reinas en diagonales de longitud 2 (hay dos 
de estas diagonales en cada dirección).
Diagonal de longitud 3: Hay 3 formas de colocar dos reinas en diagonales de longitud 3 (dos 
diagonales de longitud 3).
Diagonal de longitud 4: Solo hay 1 forma de colocar dos reinas en una diagonal de longitud 4.
En total para la diagonal principal: 2+2+3+3+1=11 configuraciones de ataque.
Para la diagonal secundaria (de derecha a izquierda), tenemos las mismas configuraciones:
2+2+3+3+1=11 configuraciones de ataque.

Análisis Matemático del Problema
El número total de configuraciones en las que las reinas se atacan es:
24 (fila)+24 (columna)+11 (diagonal principal)+11 (diagonal secundaria)=70
Número de configuraciones válidas: Ahora, restamos las configuraciones inválidas (en las que las 
reinas se atacan) del total de configuraciones posibles:
120−70=50
Esto significa que hay 50 configuraciones válidas donde dos reinas pueden colocarse en un 
tablero de 4x4 sin atacarse entre sí.
Conclusión:
Hay 50 configuraciones posibles para colocar dos reinas en un tablero de 4x4 de manera que no 
se ataquen. Este cálculo matemático te permite comprobar el resultado que obtienes con la 
implementación en Java, asegurando que imprimes exactamente 50 configuraciones distintas.

Pseudocódigo Backtracking tipo 2
procedure backtrack(fila, n, tablero):
    if fila == n:
        imprimir(tablero)   // Se colocaron n reinas válidas
        return
    para cada columna c desde 0 hasta n-1:
        if es_valido(tablero, fila, c):
            colocar_reina(tablero, fila, c)
            backtrack(fila+1, n, tablero)
            quitar_reina(tablero, fila, c)   // retroceder
procedure principal():
    n = 4   // ejemplo con tablero 4x4
    tablero = estructura vacía
    backtrack(0, n, tablero)

Explicación del código en Java
Una posible implementación en el repo de la materia.
Este código resuelve el problema de N reinas colocando una reina por fila y 
verificando en cada paso si la posición es válida en columnas y diagonales.
Si la colocación es válida, la función avanza recursivamente a la siguiente fila y 
luego retrocede (quita la reina) para probar otras opciones.
Cuando se logran ubicar todas las reinas (fila==n), se imprime una configuración 
completa del tablero como solución.

Actividad 2
En el diseño de interiores de oficinas o hogares, necesitamos un programa que 
imprima las combinaciones posibles de la ubicación de escritorios y sillas, en una 
habitación de 4x4. Las restricciones son: garantizar que ningún elemento esté en 
la misma "fila" (por ejemplo, en una misma fila de escritorios) o en la misma 
"columna" (por ejemplo, alineación en una pared) para optimizar el uso del 
espacio y facilitar la circulación.

Actividad 3
Diseño de Distribución de Equipos Electrónicos en Oficinas
En una oficina de 4x4, se necesita organizar la disposición de computadoras y 
impresoras de manera que optimice el uso del espacio y facilite el acceso. Las 
restricciones de diseño son las siguientes:
No puede haber dos computadoras en la misma fila o columna.
No puede haber dos impresoras en la misma fila o columna.
Debes encontrar todas las combinaciones posibles para colocar 4 computadoras y 4 
impresoras en el tablero, respetando las restricciones anteriores.
Objetivo: Implementar un programa en Java que utilice la técnica de backtracking para 
encontrar todas las configuraciones posibles de colocación de computadoras e 
impresoras en el tablero de 4x4. Tu programa debe imprimir cada configuración válida.

Programación 3
Clase 9
Bibliografía: AHO, J.; HOPCROFT, John E. y ULLMAN, 
Jeffrey D.. Estructuras de Datos y Algoritmos. 1988. 
Wilmington : Addison Wesley Iberoamericana. 438 p. 
ISBN: 02016402449684443455.


Temas de la clase 9
Resolución de Problemas con Backtraking. 
Recorridos sobre grafos DFS/BFS. 
Práctica general y TPO

Definición
El backtracking (o vuelta atrás) es una técnica general de resolución de 
problemas que consiste en explorar todas las posibles soluciones de manera 
sistemática, desechando aquellas que no satisfacen las condiciones del problema, 
y retrocediendo cuando se llega a un estado inválido o no prometedor. 

Principales estrategias
1. Exploración sistemática del espacio de soluciones
En el backtracking, se exploran todas las posibles soluciones de un problema, lo que 
significa que en cada paso se toma una decisión (elegir una opción) y se avanza hacia 
una solución parcial. Si se descubre que esta solución parcial no puede llevar a una 
solución completa válida, se retrocede al paso anterior (vuelta atrás) y se toma otra 
opción.
2. Criterio de viabilidad (podas)
En cada paso del proceso de backtracking, se evalúa si una solución parcial es 
prometedora. Si la solución parcial ya no puede llevar a una solución completa válida, 
se detiene la exploración en esa rama del árbol de decisiones y se realiza una poda. 
Este criterio de viabilidad evita la exploración innecesaria de caminos que claramente 
no llevarán a una solución válida.

Principales estrategias
3. Retroceso controlado
Cuando una solución parcial resulta ser inválida o no prometedora, el algoritmo 
retrocede un paso y prueba otra opción. Este retroceso o backtracking es el 
aspecto clave del método. Permite al algoritmo explorar exhaustivamente todas 
las posibilidades sin quedarse atrapado en una rama incorrecta.
4. Soluciones parciales y solución completa
Se construyen soluciones de manera incremental, agregando elementos a una 
solución parcial. El proceso de agregar elementos a la solución parcial continúa 
hasta que se alcanza una solución completa o se descubre que no es posible 
obtener una solución válida a partir de la parcial actual.

Principales estrategias
5. Utilización de estructuras de datos adecuadas
El backtracking suele emplear estructuras de datos como pilas o recursión para 
gestionar el proceso de exploración y retroceso. La recursión es particularmente 
útil porque permite que el algoritmo se "meta" en cada nivel de decisión y luego 
vuelva a niveles anteriores de forma natural cuando sea necesario.
Conclusión : Estas estrategias aseguran que el algoritmo explore todas las 
posibles configuraciones de forma eficiente.

Recorridos sobre Grafos DFS/BFS
Los recorridos sobre grafos son técnicas fundamentales para explorar y analizar 
grafos, una estructura de datos que se utiliza para modelar relaciones entre 
objetos. Estas técnicas permiten descubrir propiedades importantes del grafo, 
como su conectividad, la existencia de ciclos, los caminos más cortos entre 
nodos, entre otras:
DFS (Depth-First Search):  Algoritmo de búsqueda en grafos que explora tan 
lejos como sea posible a lo largo de cada rama antes de retroceder.
BFS (Breadth-First Search):  Algoritmo de búsqueda en grafos que explora todos 
los vecinos de un nodo antes de pasar a los vecinos de esos vecinos.

Recorrido DFS (Depth-First Search)
El DFS (Depth-First Search) o búsqueda en profundidad es un algoritmo para recorrer 
o buscar en un grafo o árbol, que explora tan profundamente como sea posible a lo 
largo de una rama antes de retroceder. A diferencia del BFS, que explora por niveles, 
el DFS se adentra en cada rama del grafo, retrocediendo solo cuando ya no es posible 
avanzar más.
El DFS utiliza una pila (stack) para gestionar el orden de los nodos que deben ser 
explorados, y comúnmente se implementa de manera recursiva, aprovechando la pila 
del sistema de llamadas de funciones.
Ejemplo en el repo de la materia
https://es.wikipedia.org/wiki/B%C3%BAsqueda_en_profundidad

Funcionamiento del algoritmo DFS
Inicialización : Se comienza en un nodo de inicio y se marca como visitado.
Exploración profunda : Desde ese nodo, el algoritmo explora un vecino no 
visitado y continúa profundizando hasta llegar a un nodo sin vecinos no visitados.
Retroceso: Cuando no hay más vecinos no visitados, se retrocede al nodo 
anterior y se exploran los demás vecinos no visitados.
Repetición : Este proceso se repite hasta que todos los nodos alcanzables desde 
el nodo de inicio hayan sido procesados.

Ejemplo
Recorrido DFS: 0 1 3 5 4 2 6

Ejercicio
Ingresar a https://graphonline.ru/en
Cargar el grafo anterior y ver el recorrido DFS

Pseudocódigo DFS
// DFS para un grafo representado como lista de adyacencia
function DFS(grafo, nodo_inicial):
    visitado = set()  // Conjunto para almacenar los nodos visitados
    DFS_recursivo(grafo, nodo_inicial, visitado)
function DFS_recursivo(grafo, nodo, visitado):
    visitado.add(nodo)
    procesar(nodo)  // Aquí se realiza alguna operación con el nodo (ej. imprimirlo)
    // Explorar vecinos no visitados
    for vecino in grafo[nodo]:
        if vecino not in visitado:
            DFS_recursivo(grafo, vecino, visitado)

Actividad 1
Dado el siguiente grafo, generar el recorrido DFS, a partir del nodo 0.
Verificar en Ingresar a https://graphonline.ru/en


BFS (Recorrido en anchura) 
Este algoritmo explora el grafo por niveles, visitando primero todos los nodos a 
una misma distancia antes de pasar a los nodos más lejanos. Es especialmente 
útil para:
●Encontrar el camino más corto en grafos no ponderados.
●Explorar todos los nodos que están a una distancia mínima de un nodo dado.
●Detección de ciclos en grafos no dirigidos.
Ejemplo en el repo de la materia
https://es.wikipedia.org/wiki/B%C3%BAsqueda_en_anchura

Funcionamiento del algoritmo BFS
Inicialización : Se comienza en un nodo de inicio, que es marcado como visitado y 
añadido a una cola. Esta cola gestionará los nodos a explorar de manera ordenada 
por niveles.
Exploración por niveles : Se toma el primer nodo de la cola (FIFO - Primero en 
entrar, primero en salir), se exploran todos sus vecinos no visitados, y se agregan 
estos vecinos a la cola y se marcan como visitados.
Expansión en amplitud : El algoritmo sigue extrayendo nodos de la cola, explorando 
los vecinos no visitados y agregándolos a la cola. La exploración continúa en amplitud, 
es decir, primero se recorren todos los nodos en el nivel actual antes de pasar al 
siguiente nivel.
Repetición: Este proceso se repite hasta que todos los nodos alcanzables desde el 
nodo de inicio hayan sido procesados y la cola esté vacía.
Finalización : Cuando ya no queden nodos por explorar (es decir, la cola está vacía), 
el algoritmo termina. En este punto, se habrá recorrido todo el subgrafo conectado al 
nodo inicial.

Ejercicio
Ingresar a https://graphonline.ru/en
Cargar el grafo de ejemplo y ver el recorrido BFS

Pseudocódigo de BFS
function BFS(grafo, nodo_inicial):
    cola = new Queue()
    visitado = set()  // Conjunto para almacenar los nodos visitados
    // Marcar nodo inicial como visitado y agregarlo a la cola
    cola.enqueue(nodo_inicial)
    visitado.add(nodo_inicial)
    while not cola.isEmpty():
        nodo = cola.dequeue()
        procesar(nodo)  // Aquí se realiza alguna operación con el nodo (ej. imprimirlo)
        // Explorar vecinos no visitados
        for vecino in grafo[nodo]:
            if vecino not in visitado:
                visitado.add(vecino)
                cola.enqueue(vecino)

Actividad 2
Dado el siguiente grafo, generar el recorrido BFS, a partir del nodo 0.
Verificar en Ingresar a https://graphonline.ru/en


Actividad 3
Desarrolla una aplicación que permita modelar una red de almacenes interconectados. 
El sistema debe permitir agregar almacenes, conectar almacenes entre sí mediante 
rutas directas, y realizar recorridos en profundidad (DFS) y en anchura (BFS) para 
explorar la red de distribución. Implementa una clase Almacen que represente un 
almacén. Cada almacén debe tener un identificador único y un nombre. Implementa 
una clase Grafo que gestione la red de almacenes. Esta clase debe: Permitir agregar 
almacenes al grafo. Permitir conectar almacenes entre sí (crear rutas directas entre 
almacenes). Implementar un método DFS para realizar un recorrido en profundidad 
desde un almacén de inicio. Implementar un método BFS para realizar un recorrido en 
anchura desde un almacén de inicio.El grafo debe estar representado utilizando una 
lista de adyacencia.

Actividad 4
Diseña e implementa un sistema que modele una red social utilizando grafos. 
Cada usuario será un nodo, y las amistades entre usuarios serán las aristas. El 
sistema debe permitir:
Agregar usuarios a la red, cada uno con un identificador único y nombre.
Conectar usuarios indicando que son amigos (relación bidireccional).
Implementar un método para realizar un recorrido DFS (Depth First Search) desde 
un usuario dado, mostrando los usuarios alcanzables a través de sus amistades.
Implementar un recorrido BFS (Breadth First Search) para explorar la red desde 
un usuario dado, siguiendo las conexiones de amistad.

Programación 3
Clase 11
Bibliografía: AHO, J.; HOPCROFT, John E. y ULLMAN, Jeffrey D.. 
Estructuras de Datos y Algoritmos. 1988. Wilmington : Addison 
Wesley Iberoamericana. 438 p. ISBN: 02016402449684443455.


Temas de la clase 11
Concepto de ramificación y poda en la resolución de problemas
mediante backtracking. 
Optimización (Branch & Bound)
Recorridos sobre grafos UCS. 
Práctica TPO.

Backtracking
Algunas veces surge el problema de encontrar una solución optimal a un 
subproblema, pero sucede que no existe una teoría que pueda aplicarse para 
ayudar a encontrar lo óptimo, si no es recurriendo a una búsqueda exhaustiva, 
vamos a ver una técnica de búsqueda exhaustiva conocida como backtracking y 
una técnica llamada poda alfa-beta, que suele reducir mucho la búsqueda.

Pseudocódigo de backtracking
function backtrack(solution, candidatos):
    if solution es completa:
        return solution
    for cada candidato en candidates:
        if es válido(candidato, solution):
            solution.add(candidato)
            result = backtrack(solution, candidatos)
            if result es no nulo:
                return result
            solution.remove(candidato)
    return nulo

Definición
Ramificación: Técnica que consiste en dividir el problema en subproblemas más 
pequeños, generando una estructura en forma de árbol.
Poda: Técnica que elimina ramas del árbol de búsqueda que no pueden contener 
una solución óptima, reduciendo así el espacio de búsqueda.

Poda alfa-beta
La poda alfa-beta es una optimización del algoritmo minimax utilizada en juegos 
de dos jugadores, como ajedrez o damas, para mejorar la eficiencia de la 
búsqueda en el árbol de decisiones.
Objetivo: Reducir el número de nodos evaluados en el árbol sin afectar el 
resultado final.
Según wikipedia:“La poda alfa beta es una técnica de búsqueda que reduce el 
número de nodos evaluados en un árbol de juego por el algoritmo Minimax. Se 
trata de una técnica muy utilizada en programas de juegos entre adversarios 
como el ajedrez, el tres en raya o el Go.”

Poda alfa-beta
Funcionamiento:
Alfa: El valor más alto garantizado para el jugador maximizador en cualquier 
momento.
Beta: El valor más bajo garantizado para el jugador minimizador.
Se poda (es decir, se deja de explorar) una rama del árbol si se encuentra que no 
puede mejorar el resultado ya conocido para uno de los jugadores.
Beneficio: La poda alfa-beta reduce significativamente el tiempo de cálculo al evitar 
evaluaciones innecesarias, logrando un rendimiento cercano al de explorar solo la 
mitad de los nodos del árbol.
Ejemplo: En un juego de ajedrez, si se sabe que un movimiento del oponente ya es 
peor que otro movimiento previamente analizado, no es necesario seguir evaluando 
las opciones bajo ese movimiento, pues no será escogido.

Poda alfa-beta
Ejemplo del algoritmo en el repo de la materia.
Explicación:
alfa: El valor más alto que el maximizador puede garantizar.
beta: El valor más bajo que el minimizador puede garantizar.
Poda: Si beta <= alfa, se deja de explorar esa rama porque ya no puede 
proporcionar una mejor solución.

Ejemplo: Detección de fraudes
Suposiciones del modelo:
El sistema de detección intenta maximizar la detección de fraudes.
El fraudulento intenta minimizar la probabilidad de ser detectado.
Se utiliza un árbol de decisiones donde las acciones del sistema y del atacante se 
alternan.
Ejemplo simplificado: Imaginemos un sistema donde el detector de fraudes (el 
sistema) puede optar por monitorear diferentes acciones del usuario (transacciones 
inusuales, cambios de dirección, uso de tarjetas en diferentes ubicaciones, etc.), y el 
fraudulento puede intentar realizar fraudes de diferentes maneras (retirar dinero, 
cambiar contraseñas, transferencias inusuales).
La idea aquí es que el sistema intenta anticipar los movimientos del defraudador y 
optimizar sus estrategias de detección, mientras que el defraudador busca maximizar 
el beneficio sin ser detectado.

Sistema de detección de fraudes
Sistema de detección de fraudes (maximizador): Trata de detectar tantas actividades fraudulentas 
como sea posible, buscando maximizar su función de evaluación (por ejemplo, número de fraudes 
detectados o la certeza de detección).
Atacante o defraudador (minimizador): Trata de minimizar el riesgo de ser detectado por el 
sistema de detección.
Árbol de decisiones: Cada nodo en el árbol representa un estado (una situación en la que puede 
estar el sistema de detección o el atacante).
Las ramas del árbol representan las acciones posibles que pueden tomar el sistema de detección 
o el atacante.
Los hijos de un nodo representan las posibles respuestas del adversario (fraudulento o sistema de 
detección) en cada estado.
Evaluación de nodos
Si el sistema detecta un fraude, el nodo tiene un puntaje positivo.
Si el atacante logra realizar un fraude sin ser detectado, el nodo tiene un puntaje negativo.

Sistema de detección de fraudes: ejemplo
Imaginemos un escenario donde el defraudador puede intentar las siguientes 
acciones:
Retirar una cantidad grande de dinero.
Cambiar la dirección de envío de una tarjeta.
Realizar una transferencia inusual a un país extranjero.
El sistema de detección puede:
Monitorear transacciones grandes.
Detectar cambios en la dirección del usuario.
Monitorear transferencias internacionales.
El árbol de decisiones se formaría a partir de las opciones posibles de ambas partes.
Código java de ejemplo, en el repo de la materia.

Actividad 1
Consigna:
Escenario conceptual: Imagina que estás diseñando un sistema de detección de 
ciberataques para proteger una plataforma web. Este sistema debe identificar posibles 
acciones maliciosas que un atacante pueda intentar realizar sobre la plataforma.
1. Indicar algunos ejemplos de Acciones posibles del atacante: 
2. Indicar las Funciones del sistema de detección A su vez, el sistema de detección de 
ataques debe reaccionar para intentar evitar o mitigar estas acciones. ¿Qué técnicas o 
herramientas puede usar el sistema para detectar estos ataques? 
3. Explicar cómo se aplicaría la poda alfa-beta
4. Construir un árbol de decisión simplificado

Optimización
Optimización es el proceso de buscar la mejor solución posible a un problema 
entre muchas soluciones factibles. En otras palabras, no se trata solo de 
encontrar una solución, sino de encontrar la mejor, según algún criterio de 
calidad o costo.

Optimización, ejemplos clásicos
●Encontrar el camino más corto entre dos ciudades (mínimo costo).
●Seleccionar elementos para una mochila maximizando el valor total sin 
exceder el peso.
●Asignar tareas a trabajadores minimizando el tiempo total de trabajo.
Formalmente, en muchos casos se define una función objetivo:
f(x) -> mínimo o máximo
donde x representa una posible solución, y el objetivo es minimizar o maximizar

Optimización: ¿Qué tiene que ver con el backtracking?
En algunos problemas, el objetivo del backtracking es encontrar todas las 
soluciones (por ejemplo, todas las configuraciones posibles de N reinas).
Pero en otros casos, se lo usa para encontrar la mejor (es decir, resolver un 
problema de optimización). En esos casos, el backtracking incorpora una idea de 
minimizar o maximizar una función objetivo, explorando el espacio de soluciones 
con poda por optimalidad.

Backtraking y Optimización
Cuando combinamos backtracking  con poda basada en optimalidad , obtenemos 
una técnica llamada Branch and Bound (Ramificación y Poda) , que es una 
extensión orientada directamente a la optimización .
Concepto Objetivo Ejemplo
BacktrackingExplorar y construir soluciones 
válidas descartando las inválidas.Resolver un Sudoku o ubicar N 
reinas.
OptimizaciónEncontrar la mejor solución 
según un criterio 
(mínimo/máximo).Hallar el camino más corto o la 
mejor asignación de tareas.

Ejemplo simple de Branch & Bound (Ramificación y Poda)
Problema: Mochila con capacidad = 10 Objetos disponibles:
Objetivo: maximizar el valor sin superar los 10 kg.
Idea del método
Branch (ramificar): decidir incluir o no cada objeto.
Bound (acotar): estimar el mejor valor posible desde un nodo (cota superior).
Poda: si la cota ≤ mejor solución actual → descartar esa rama.Objeto Valor Peso
A 40 2
B 30 5
C 50 10

Ejemplo paso a paso
1.Nodo raíz: peso = 0, valor = 0 → cota = 120 (A+B+C).
2.Incluyo A: peso = 2, valor = 40 → cota = 85 (A+B+parte de C).
3.Incluyo B: peso = 7, valor = 70 → posible solución.
4.Intento incluir C: peso = 17 > 10 →  poda. ❌
5.Exploro otras ramas: ninguna supera valor = 70.
Mejor combinación: A + B
Valor total: 70
Peso total: 7

Diagrama del árbol de decisiones
●Ramas con peso > 10 o cota ≤ 70 → poda


Segundo ejemplo
Seleccionar monedas para alcanzar un monto con el menor número de monedas
Descripción del problema:
Tenemos monedas de valores {1, 2, 5}
y queremos alcanzar un monto total de 6 unidades
usando la menor cantidad de monedas posible. 
Objetivo
Encontrar una combinación mínima de monedas que sume el monto objetivo.

Árbol de desiciones
Cada nivel del árbol representa una decisión parcial, y cada nodo tiene un hijo 
por cada tipo de moneda posible (por eso es n-ario).
Raíz → elegir moneda de 1, 2 o 5
   |
   ├── agregar 1 → total = 1
   ├── agregar 2 → total = 2
   └── agregar 5 → total = 5
Y así sucesivamente hasta llegar o superar el monto (6).

Idea de Branch & Bound
En cada nodo tenemos:
Branch: probar agregar una moneda de cada tipo.
Bound: si el total actual supera el monto objetivo → poda.
Si el número de monedas ya es mayor al mejor resultado encontrado → poda.
De esta forma, no se exploran ramas inútiles.
Ejemplo en el repo de la materia.

Ejemplo paso a paso
Desde la raíz: total = 0, monedas = 0
Ramas:
+1 → total=1, monedas=1
+2 → total=2, monedas=1
+5 → total=5, monedas=1
Desde total=5 → +1 → total=6  (solución con 2 monedas)
Actualizamos mejor = 2.
Todas las ramas que lleguen con monedas >= 2 y total < 6ya no se exploran 
porque no pueden mejorar la solución óptima.

Resumen conceptual
Concepto Descripción
Tipo de árbol n-ario (n = cantidad de tipos de moneda)
Tipo de problema Optimización (minimizar cantidad de monedas)
Criterio de poda total > monto o usadas >= mejor
Cota usada mejor número de monedas encontrado
Técnica Branch & Bound con búsqueda en profundidad
Clasificación Árbol de decisiones n-ario, optimizador

Actividad 2
Asignación de proyectos a empleados
Descripción del problema:
Una empresa debe asignar n proyectos  a m empleados .
Cada proyecto requiere una cantidad de horas de trabajo  diferentes, y todos los 
empleados trabajan a la misma velocidad (empleados idénticos).
El objetivo es encontrar una asignación de proyectos a empleados tal que el 
tiempo total de finalización del conjunto de proyectos sea mínimo , es decir, 
que la carga de trabajo quede lo más equilibrada posible .

Actividad 3
●Consigna: Distribución de cargas en camiones
●Descripción del problema:
Una empresa de logística dispone de m camiones idénticos  y debe 
transportar n paquetes  con diferentes pesos.
Se requiere asignar los paquetes a los camiones  de forma que la carga 
máxima de cualquier camión sea mínima posible .
●Objetivo:
Minimizar el peso del camión más cargado.

Definición: Algoritmo de búsqueda de grafos que expande el nodo con el menor 
costo acumulado primero.
https://es.wikipedia.org/wiki/B%C3%BAsqueda_de_costo_uniforme
https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm#Practical_optimizations_and_
infinite_graphsUCS (Uniform Cost Search) 

UCS (Uniform Cost Search) 
El algoritmo de Uniform Cost Search (UCS) es una variante de la búsqueda en 
grafos que encuentra el camino de costo mínimo desde un nodo inicial hasta un 
nodo objetivo. A diferencia de otros algoritmos, como el de búsqueda en 
profundidad (DFS) o búsqueda en amplitud (BFS), UCS prioriza los caminos en 
función de su costo acumulado en lugar de la profundidad o la cantidad de nodos 
recorridos.

¿Cómo funciona UCS?
UCS se basa en un enfoque de búsqueda voraz en el que los nodos del grafo se expanden según 
el costo mínimo asociado al camino recorrido hasta ese punto. Esto se logra utilizando una cola 
de prioridad (normalmente implementada con un montículo binario) para asegurar que los 
caminos de menor costo sean explorados primero. Aquí están los pasos clave:
Inicialización : Se comienza con el nodo inicial, y su costo asociado es cero. Este nodo se inserta 
en la cola de prioridad.
Expansión: Se extrae el nodo con el costo acumulado más bajo de la cola de prioridad.
Exploración: Se examinan los nodos vecinos del nodo actual. Si un vecino aún no ha sido 
visitado, o si se encuentra un camino de menor costo para llegar a él, se actualiza su costo y se 
inserta en la cola.
Condición de parada : El algoritmo termina cuando se expande el nodo objetivo, ya que en ese 
momento se ha encontrado el camino de menor costo

Ejemplo en grafos
Imagina que tienes un grafo con los siguientes nodos y aristas (donde el número 
representa el costo de la arista):
Nodo A está conectado con B (costo 1) y C (costo 4).
Nodo B está conectado con D (costo 2).
Nodo C está conectado con D (costo 1).

Ejemplo en grafos
Si ejecutamos UCS para encontrar el camino de menor costo desde A hasta D:
Se comienza en A, con un costo acumulado de 0.
Se expande B (costo total 1) antes que C (costo total 4), porque UCS prioriza el 
menor costo.
Al expandir B, se llega a D con un costo acumulado de 3 (1 de A a B, más 2 de B 
a D).
Aunque C también tiene una conexión con D, su costo acumulado sería 5 (4 de A 
a C, más 1 de C a D), lo que no mejora la solución encontrada.
El camino más barato sería A → B → D, con un costo total de 3.

Pseudocódigo UCS  (para encontrar el camino de costo mínimo en un grafo)
función UCS(grafo, nodo_inicial, nodo_objetivo):
    // Crear una cola de prioridad para manejar los caminos en función del costo
    cola_prioridad = nueva ColaPrioridad()    
    // Insertar el nodo inicial con costo 0
    cola_prioridad.insertar(nodo_inicial, 0)    
    // Diccionario para llevar el seguimiento de los costos mínimos hasta cada nodo
    costos_min = {nodo_inicial: 0}    
    // Mientras haya nodos en la cola de prioridad
   

Pseudocódigo (continuación)
mientras no cola_prioridad.esta_vacia():
        // Extraer el nodo con el menor costo acumulado
        (nodo_actual, costo_actual) = cola_prioridad.extraer_min()        
        // Si llegamos al nodo objetivo, devolver el costo
        si nodo_actual == nodo_objetivo:
            devolver costo_actual        
        // Explorar los vecinos del nodo actual
        para cada vecino en grafo.vecinos(nodo_actual):
            costo_vecino = costo_actual + grafo.costo(nodo_actual, vecino)            
            // Si el costo para llegar al vecino es menor que el conocido
            si vecino no está en costos_min o costo_vecino < costos_min[vecino]:
                // Actualizar el costo mínimo para llegar al vecino
                costos_min[vecino] = costo_vecino                
                // Insertar o actualizar el vecino en la cola de prioridad con su nuevo costo
                cola_prioridad.insertar( vecino, costo_vecino )    
    // Si se sale del bucle sin haber encontrado el nodo objetivo
    devolver "No se encontró un camino"

Ejemplo UCS en una Clínica Médica
Supongamos que tenemos un grafo que representa las habitaciones de la clínica 
y el costo de moverse de una habitación a otra. Cada habitación tiene un costo 
diferente según la distancia o el tiempo que toma llegar a ella.
Representación del grafo
Nodos: Habitaciones de la clínica (A, B, C, D, E).
Aristas: Costo de moverse entre las habitaciones.

Ejemplo UCS en una Clínica Médica
Costo de las conexiones:
A -> B: 2
A -> C: 4
B -> C: 1
B -> D: 7
C -> E: 3
D -> E: 1
Código de ejemplo en el repo de la materia.

Actividad 2
Realizar un sistema de selección de viajes, para llegar a un destino realizando 
escalas, con el menor precio posible, cada destino es un nodo

Programación 3
Clase 12
Bibliografía: AHO, J.; HOPCROFT, John E. y ULLMAN, Jeffrey D.. 
Estructuras de Datos y Algoritmos. 1988. Wilmington : Addison Wesley 
Iberoamericana. 438 p. ISBN: 02016402449684443455.


Temas de la clase 12
Backtracking de juegos. 
TPO consulta y práctica. 
Ejercitación.

Definición Backtracking
Técnica algorítmica para resolver problemas mediante la construcción incremental 
de soluciones, eliminando aquellas que no satisfacen las condiciones del 
problema.
Esquema General del Backtracking:
Se exploran todas las posibilidades de solución, retrocediendo (backtracking) 
cuando se determina que una solución parcial no puede llevar a una solución 
completa válida.

Pseudocódigo de backtracking
function backtrack(solution, candidatos):
    if solution es completa:
        return solution
    for cada candidato en candidatos:
        if es válido(candidato, solution):
            solution.add(candidato)
            result = backtrack(solution, candidatos)
            if result es no nulo:
                return result
            solution.remove(candidato)
    return nulo

Problema de las ocho reinas
El problema de las ocho reinas es un pasatiempo que consiste en poner ocho 
reinas en el tablero de ajedrez sin que se amenacen. Fue propuesto por el 
ajedrecista alemán Max Bezzel en 1848.1  2  En el juego del ajedrez la reina 
amenaza a aquellas piezas que se encuentren en su misma fila, columna o 
diagonal. El juego de las 8 reinas consiste en poner sobre un tablero de ajedrez 
ocho reinas sin que estas se amenacen entre ellas. Para resolver este problema 
se puede emplear un esquema vuelta atrás (o Backtracking).
Fuente: wikipedia

Actividad 1
Copiar el código de muestra que está en el repo de la materia y observar en pytho
n tutor
¿Cómo funciona el backtracking en este problema?
¿Qué pasa cuando el algoritmo encuentra una solución? ¿Qué ocurre cuando no 
puede colocar más reinas?
¿Qué sucede en el código cuando el algoritmo "retrocede"? ¿Cómo se visualiza 
en Python Tutor?
¿Qué modificaciones harías para aumentar N a 8? ¿Cómo crees que cambiaría el 
tiempo de ejecución?
¿Por qué el método isSafe es crucial en este algoritmo?

Juego Sudoku
Reglas del juengo en: https://es.wikipedia.org/wiki/Sudoku
Para jugar: https://sudoku.com/
https://sudokugarden.de/en/
Generar sudokus
https://ksudoku.sourceforge.net/
Una implementación en java , está en el repo de la materia

Actividad 2
Modificar un programa de Sudoku para que trabaje con un tablero de 6x6, usando 
los números del 1 al 6 y con tres subcuadrantes de 6 números cada uno. 
Siguiendo estas instrucciones:
Cambiar el tamaño del tablero de 9x9 a 6x6.
Ajustar las reglas del juego para que en lugar de 9 subcuadrantes de 3x3, ahora 
el tablero tenga tres subcuadrantes de 6 números. Es decir, cada subcuadrante 
contendrá 6 celdas en total, de 2x3.
Asegurarse de que los números utilizados sean del 1 al 6.

