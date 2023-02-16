package genetico;

import maximiza.Objeto;

import java.util.ArrayList;
import java.util.Random;

public class Individuo {
    ArrayList<Objeto> objetos = new ArrayList<Objeto>();
    ArrayList<Integer> cromossomo = new ArrayList<Integer>();
    int geracao;

    int indice;

    public Individuo(ArrayList<Objeto> objetos){
        this.objetos = objetos;
        this.gerarCromossomos();
    }

    public void gerarCromossomos() {
        Random random = new Random();

        for (int i=0; i < 5; i++) {
            this.cromossomo.add(random.nextInt(2));
            System.out.print(this.cromossomo.get(i) + " | ");
        }

        this.selecionarObjetos();
    }

    public void selecionarObjetos() {

        for (int i=0; i < 5; i++) {
            if (this.cromossomo.get(i) == 1) {
                this.objetos.get(i).selecionado = true;
            }
        }


        System.out.println();

        for (Objeto objeto : this.objetos) {
            System.out.print(objeto.selecionado + " | ");
        }

    }

}
