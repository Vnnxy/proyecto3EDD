package mx.unam.ciencias.edd;

import java.util.NoSuchElementException;

/**
 * <p>
 * Clase abstracta para árboles binarios genéricos.
 * </p>
 *
 * <p>
 * La clase proporciona las operaciones básicas para árboles binarios, pero
 * deja la implementación de varias en manos de las subclases concretas.
 * </p>
 */
public abstract class ArbolBinario<T> implements Coleccion<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class Vertice implements VerticeArbolBinario<T> {

        /** El elemento del vértice. */
        protected T elemento;
        /** El padre del vértice. */
        protected Vertice padre;
        /** El izquierdo del vértice. */
        protected Vertice izquierdo;
        /** El derecho del vértice. */
        protected Vertice derecho;

        /**
         * Constructor único que recibe un elemento.
         * 
         * @param elemento el elemento del vértice.
         */
        protected Vertice(T elemento) {
            this.elemento = elemento;
        }

        /**
         * Nos dice si el vértice tiene un padre.
         * 
         * @return <code>true</code> si el vértice tiene padre,
         *         <code>false</code> en otro caso.
         */
        @Override
        public boolean hayPadre() {
            return padre != null;
        }

        /**
         * Nos dice si el vértice tiene un izquierdo.
         * 
         * @return <code>true</code> si el vértice tiene izquierdo,
         *         <code>false</code> en otro caso.
         */
        @Override
        public boolean hayIzquierdo() {
            return izquierdo != null;
        }

        /**
         * Nos dice si el vértice tiene un derecho.
         * 
         * @return <code>true</code> si el vértice tiene derecho,
         *         <code>false</code> en otro caso.
         */
        @Override
        public boolean hayDerecho() {
            return derecho != null;
        }

        /**
         * Regresa el padre del vértice.
         * 
         * @return el padre del vértice.
         * @throws NoSuchElementException si el vértice no tiene padre.
         */
        @Override
        public VerticeArbolBinario<T> padre() {
            if (padre == null)
                throw new NoSuchElementException("NSE");
            return padre;
        }

        /**
         * Regresa el izquierdo del vértice.
         * 
         * @return el izquierdo del vértice.
         * @throws NoSuchElementException si el vértice no tiene izquierdo.
         */
        @Override
        public VerticeArbolBinario<T> izquierdo() {
            if (izquierdo == null)
                throw new NoSuchElementException("NSE");
            return izquierdo;
        }

        /**
         * Regresa el derecho del vértice.
         * 
         * @return el derecho del vértice.
         * @throws NoSuchElementException si el vértice no tiene derecho.
         */
        @Override
        public VerticeArbolBinario<T> derecho() {
            if (derecho == null)
                throw new NoSuchElementException("NSE");
            return derecho;
        }

        /**
         * Regresa la altura del vértice.
         * 
         * @return la altura del vértice.
         */
        @Override
        public int altura() {
            if (raiz == null)
                return -1;
            if (izquierdo == null && derecho == null)
                return 0;
            return alturaVertex(raiz);

        }

        private int alturaVertex(Vertice p) {
            if (p == null)
                return -1;
            return Math.max(alturaVertex(p.izquierdo) + 1, alturaVertex(p.derecho) + 1);
        }

        /**
         * Regresa la profundidad del vértice.
         * 
         * @return la profundidad del vértice.
         */
        @Override
        public int profundidad() {
            return profundidadVertex(this);
        }

        private int profundidadVertex(Vertice v) {
            if (v.padre == null)
                return 0;
            return profundidadVertex(v.padre) + 1;
        }

        /**
         * Regresa el elemento al que apunta el vértice.
         * 
         * @return el elemento al que apunta el vértice.
         */
        @Override
        public T get() {
            return elemento;
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>. Las clases que extiendan {@link Vertice} deben
         * sobrecargar el método {@link Vertice#equals}.
         * 
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link Vertice}, su elemento es igual al elemento de éste
         *         vértice, y los descendientes de ambos son recursivamente
         *         iguales; <code>false</code> en otro caso.
         */
        @Override
        public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
            Vertice vertice = (Vertice) objeto;
            return equalsVertex(raiz, vertice);
        }

        private boolean equalsVertex(Vertice v, Vertice vc) {
            if (v == null && vc == null)
                return true;
            else if (v == null)
                return false;
            else if (vc == null)
                return false;

            if (!v.elemento.equals(vc.elemento))
                return false;
            else
                return equalsVertex(v.izquierdo, vc.izquierdo) && equalsVertex(v.derecho, vc.derecho);

        }

        /**
         * Regresa una representación en cadena del vértice.
         * 
         * @return una representación en cadena del vértice.
         */
        @Override
        public String toString() {
            return elemento.toString();
        }
    }

    /** La raíz del árbol. */
    protected Vertice raiz;
    /** El número de elementos */
    protected int elementos;

    /**
     * Constructor sin parámetros. Tenemos que definirlo para no perderlo.
     */
    public ArbolBinario() {
    }

    /**
     * Construye un árbol binario a partir de una colección. El árbol binario
     * tendrá los mismos elementos que la colección recibida.
     * 
     * @param coleccion la colección a partir de la cual creamos el árbol
     *                  binario.
     */
    public ArbolBinario(Coleccion<T> coleccion) {
        for (T elemento : coleccion) {
            agrega(elemento);
        }
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link Vertice}. Para
     * crear vértices se debe utilizar este método en lugar del operador
     * <code>new</code>, para que las clases herederas de ésta puedan
     * sobrecargarlo y permitir que cada estructura de árbol binario utilice
     * distintos tipos de vértices.
     * 
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    protected Vertice nuevoVertice(T elemento) {
        return new Vertice(elemento);
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol es la altura de su
     * raíz.
     * 
     * @return la altura del árbol.
     */
    public int altura() {
        return alturaVertex(raiz);
    }

    private int alturaVertex(Vertice p) {
        if (p == null)
            return -1;
        return Math.max(alturaVertex(p.izquierdo) + 1, alturaVertex(p.derecho) + 1);
    }

    /**
     * Regresa el número de elementos que se han agregado al árbol.
     * 
     * @return el número de elementos en el árbol.
     */
    @Override
    public int getElementos() {
        return elementos;
    }

    /**
     * Nos dice si un elemento está en el árbol binario.
     * 
     * @param elemento el elemento que queremos comprobar si está en el árbol.
     * @return <code>true</code> si el elemento está en el árbol;
     *         <code>false</code> en otro caso.
     */
    @Override
    public boolean contiene(T elemento) {
        return buscaElem(elemento, raiz) != null;

    }

    /**
     * Busca el vértice de un elemento en el árbol. Si no lo encuentra regresa
     * <code>null</code>.
     * 
     * @param elemento el elemento para buscar el vértice.
     * @return un vértice que contiene el elemento buscado si lo encuentra;
     *         <code>null</code> en otro caso.
     */
    public VerticeArbolBinario<T> busca(T elemento) {
        return buscaElem(elemento, raiz);

    }

    private VerticeArbolBinario<T> buscaElem(T elemento, Vertice v) {
        if (v == null)
            return null;
        if (elemento.equals(v.elemento))
            return v;
        VerticeArbolBinario<T> izq = buscaElem(elemento, v.izquierdo);
        VerticeArbolBinario<T> der = buscaElem(elemento, v.derecho);
        if (izq != null)
            return izq;
        if (der != null)
            return der;
        if (der == null && izq == null)
            return null;
        return null;
    }

    /**
     * Regresa el vértice que contiene la raíz del árbol.
     * 
     * @return el vértice que contiene la raíz del árbol.
     * @throws NoSuchElementException si el árbol es vacío.
     */
    public VerticeArbolBinario<T> raiz() {
        if (raiz == null)
            throw new NoSuchElementException("NSEE");
        return raiz;
    }

    /**
     * Nos dice si el árbol es vacío.
     * 
     * @return <code>true</code> si el árbol es vacío, <code>false</code> en
     *         otro caso.
     */
    @Override
    public boolean esVacia() {
        return raiz == null;
    }

    /**
     * Limpia el árbol de elementos, dejándolo vacío.
     */
    @Override
    public void limpia() {
        raiz = null;
        elementos = 0;
    }

    /**
     * Compara el árbol con un objeto.
     * 
     * @param objeto el objeto con el que queremos comparar el árbol.
     * @return <code>true</code> si el objeto recibido es un árbol binario y los
     *         árboles son iguales; <code>false</code> en otro caso.
     */
    @Override
    public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked")
        ArbolBinario<T> arbol = (ArbolBinario<T>) objeto;
        return equalsRoot(arbol.raiz, raiz);
    }

    private boolean equalsRoot(Vertice vertex, Vertice vertexTwo) {
        if (vertex == null && vertexTwo == null)
            return true;
        if (vertex == null || vertexTwo == null)
            return false;
        if (!vertex.elemento.equals(vertexTwo.elemento))
            return false;
        else
            return equalsRoot(vertex.izquierdo, vertexTwo.izquierdo) && equalsRoot(vertex.derecho, vertexTwo.derecho);

    }

    /**
     * Regresa una representación en cadena del árbol.
     * 
     * @return una representación en cadena del árbol.
     */
    @Override
    public String toString() {
        if (raiz == null)
            return "";
        int[] arr = new int[altura() + 1];
        for (int i = 0; i < altura() + 1; i++)
            arr[i] = 0;
        return toString(raiz, 0, arr);
    }

    private String toString(Vertice v, int l, int[] arr) {
        String s = v.toString() + "\n";
        arr[l] = 1;
        if (v.izquierdo != null && v.derecho != null) {
            s += dibujaEspacios(l, arr);
            s += "├─›";
            s += toString(v.izquierdo, l + 1, arr);
            s += dibujaEspacios(l, arr);
            s += "└─»";
            arr[l] = 0;
            s += toString(v.derecho, l + 1, arr);
        } else if (v.izquierdo != null) {
            s += dibujaEspacios(l, arr);
            s += "└─›";
            arr[l] = 0;
            s += toString(v.izquierdo, l + 1, arr);
        } else if (v.derecho != null) {
            s += dibujaEspacios(l, arr);
            s += "└─»";
            arr[l] = 0;
            s += toString(v.derecho, l + 1, arr);
        }
        return s;
    }

    private String dibujaEspacios(int l, int[] arr) {
        String s = "";
        for (int i = 0; i <= l - 1; i++) {
            if (arr[i] == 1)
                s += "│  ";
            else
                s += "   ";
        }
        return s;
    }

    /**
     * Convierte el vértice (visto como instancia de {@link
     * VerticeArbolBinario}) en vértice (visto como instancia de {@link
     * Vertice}). Método auxiliar para hacer esta audición en un único lugar.
     * 
     * @param vertice el vértice de árbol binario que queremos como vértice.
     * @return el vértice recibido visto como vértice.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *                            Vertice}.
     */
    protected Vertice vertice(VerticeArbolBinario<T> vertice) {
        return (Vertice) vertice;
    }
}
