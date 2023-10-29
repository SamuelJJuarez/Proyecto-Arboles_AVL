public class Arbol_AVL <T extends Integer>{
    private NodoA raiz;

    public void add(T val){
        if(raiz == null){
            raiz = new NodoA(val);
        }
        else{
            add(raiz, val);
        }
    }
  
    public void add(NodoA r, T val){
        if(val > r.dato){//va a la derecha
            if (r.der == null){
                r.der = new NodoA(val);
                r.der.padre = r; //Conectamos el nuevo nodo con su padre
                balancea(r);
            } 
            else{
                add(r.der, val);
            }
        }
        else if(val < r.dato){//va a la izquierda
            if(r.izq == null){
                r.izq = new NodoA(val);
                r.izq.padre = r; //Conectamos el nuevo nodo con su padre
                balancea(r);
            } 
            else{
                add(r.izq, val);
            }
        }
    }

    public int size(){
        if(raiz != null){
            return 1 + size(raiz.izq) + size(raiz.der);
        }
        else{
            return 0;
        }
    }

    private int size(NodoA r){
        if(r != null){
            return 1 + size(r.izq) + size(r.der);
        }
        else{
            return 0;
        }
    }
  
    public int altura(){
        if(raiz != null){
            return 1 + Math.max(altura(raiz.izq), altura(raiz.der));
        }
        else{
            return 0;
        }
    }

    private int altura(NodoA r){
        if(r != null){
            return 1 + Math.max(altura(r.izq), altura(r.der));
        }
        else{
            return 0;
        }
    }
  
    @Override
    public String toString(){
        return "Arbol Bin {" + rec_indorden(raiz) + "}";
    }

    private String rec_indorden(NodoA r){
        if(r != null){
            return rec_indorden(r.izq) + " " + r + " " + rec_indorden(r.der);
        } 
        else {
            return "";
        }
    }

    private String rec_preorden(NodoA r){
        if(r != null){
            return r + " " + rec_preorden(r.izq) + " " + rec_preorden(r.der);
        } else {
            return "";
        }
    }

    private String rec_postorden(NodoA r){
        if(r != null){
            return rec_postorden(r.izq) + " " + rec_postorden(r.der) + " " + r;
        } else{
            return "";
        }
    }
  
    public NodoA getRaiz(){
        return raiz;
    }

    public int fe(NodoA r){
        if(r != null){
            return altura(r.izq)-altura(r.der);
        } 
        else {
            return 0;
        }
    }
  
  //Revisar el factor de equilibrio en toda la ruta hacia la raiz realizando los balanceos adecuados
    private void balancea(NodoA r){
        while(r != null){
            if(fe(r) == -2){
                balanceoDD(r);
            }
            if(fe(r) == 2){
                balanceoII(r);
            }    
            r = r.padre;
        }
    }

    private void balanceoDD(NodoA r){
        NodoA auxiliar3 = null;
        r.der.padre = null;
        NodoA auxiliar = r.der;
        if(r.der.izq != null){
            r.der.izq.padre = null;
            auxiliar3 = r.der.izq;
            r.der.izq = null;
        }
        r.der = null;
        NodoA auxiliar2 = r.padre;
        r.padre = null;
        NodoA temporal = r;
        r = auxiliar;
        r.padre = auxiliar2;
        r.izq = temporal;
        r.izq.padre = r;
        if(auxiliar3 != null){
            r.izq.der = auxiliar3;
            r.izq.der.padre = r.izq;
        }
    }
    
    private void balanceoII(NodoA r){
        NodoA auxiliar3 = null;
        r.izq.padre = null;
        NodoA auxiliar = r.izq;
        if(r.izq.der != null){
            r.izq.der.padre = null;
            auxiliar3 = r.izq.der;
            r.izq.der = null;
        }
        r.izq = null;
        NodoA auxiliar2 = r.padre;
        r.padre = null;
        NodoA temporal = r;
        r = auxiliar;
        r.padre = auxiliar2;
        r.der = temporal;
        r.der.padre = r;
        if(auxiliar3 != null){
            r.der.izq = auxiliar3;
            r.der.izq.padre = r.der;
        }
    }
    
    public NodoA eliminar(T val){
        NodoA borrado = buscar(val);
        if(borrado.der == null && borrado.izq == null){
            if(borrado.getDato() > borrado.padre.getDato()){
                borrado.padre.der = null;
                NodoA r = borrado.padre;
                borrado.padre = null;
                while(r.padre != null){
                    balancea(r);
                    r = r.padre;
                }
            } 
            else if(borrado.getDato() < borrado.padre.getDato()){
                borrado.padre.izq = null;
                NodoA r = borrado.padre;
                borrado.padre = null;
                while(r.padre != null){
                    balancea(r);
                    r = r.padre;
                }
            }
        }
        if(borrado.der != null && borrado.izq == null){
            borrado.padre.der = null;
            NodoA auxiliar = borrado.padre;
            borrado.padre = null;
            borrado.der.padre = null;
            NodoA auxiliar2 = borrado.der;
            borrado.der = null;
            auxiliar.der = auxiliar2;
            auxiliar2.padre = auxiliar;
            NodoA r = auxiliar2;
            while(r.der != null){
                r = r.der;
            }
            while(r.padre != null){
                balancea(r);
                r = r.padre;
            }
        }
        if(borrado.izq != null && borrado.der == null){
            borrado.padre.izq = null;
            NodoA auxiliar = borrado.padre;
            borrado.padre = null;
            borrado.izq.padre = null;
            NodoA auxiliar2 = borrado.izq;
            borrado.izq = null;
            auxiliar.izq = auxiliar2;
            auxiliar2.padre = auxiliar;
            NodoA r = auxiliar2;
            while(r.izq != null){
                r = r.izq;
            }
            while(r.padre != null){
                balancea(r);
                r = r.padre;
            }
        }
        if(borrado.getDato() == raiz.getDato()){
            NodoA r = raiz.izq;
            while(r.der != null){
                r = r.der;
            }
            NodoA nuevo = eliminar(r.getDato());
            raiz.der.padre = null;
            NodoA auxiliar = raiz.der;
            raiz.der = null;
            raiz.izq.padre = null;
            NodoA auxiliar2 = raiz.izq;
            raiz.izq = null;
            borrado = raiz;
            raiz = nuevo;
            nuevo.der = auxiliar;
            nuevo.der.padre = nuevo;
            nuevo.izq = auxiliar2;
            nuevo.izq.padre = nuevo;
        }
        return borrado;
    }
    
    public NodoA buscar(T val){
        if(raiz != null){
            if(val < raiz.getDato()){
                buscar(raiz.izq, val);
            }
            else if(val > raiz.getDato()){
                buscar(raiz.der, val);
            }
            else if(val == raiz.getDato()){
                return raiz;
            }
        }
        return null;
    }
    
    public NodoA buscar(NodoA r, T val){
        if(r != null){
            if(val < r.getDato()){
                buscar(r.izq, val);
            }
            else if(val > r.getDato()){
                buscar(r.der, val);
            }
            else if(val == r.getDato()){
                return r;
            }
        }
        return null;
    }

    public class NodoA{
        private T dato;
        private NodoA der, izq, padre;

        public NodoA(T d){
            this.dato = d;
        }

        public T getDato() {
            return dato;
        }

        @Override
        public String toString(){
            return "" + dato + ' ';
        }
    }
}
