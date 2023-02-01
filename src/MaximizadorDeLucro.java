import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MaximizadorDeLucro {
    ArrayList<Objeto> objetos;
    int[] solucao;

    Veiculo veiculo;

    MaximizadorDeLucro() {
        this.veiculo = new Veiculo(2000);

        Objeto obj1 = new Objeto(1, 400, 200);
        Objeto obj2 = new Objeto(2, 500, 200);
        Objeto obj3 = new Objeto(3, 700, 300);
        Objeto obj4 = new Objeto(4, 900, 400);
        Objeto obj5 = new Objeto(5, 600, 400);

        this.objetos = new ArrayList<>();
        this.objetos.add(obj1);
        this.objetos.add(obj2);
        this.objetos.add(obj3);
        this.objetos.add(obj4);
        this.objetos.add(obj5);


        this.solucao = new int[objetos.size()];
        for(int i : this.solucao) {
            i = 0;
        }

        this.imprimeListaDeObjetos(this.objetos, "Lista de Objetos:");
    }

    public void imprimeListaDeObjetos(ArrayList<Objeto> objetos, String titulo) {
        System.out.println(titulo);
        for(Objeto objeto :objetos) {
            System.out.print(objeto.id + "(" + String.format("%.2f",objeto.getRazaoLucroPeso())+") | " );
        }

        System.out.println("");
        System.out.println("");
    }

    public void otimizar() {
        Collections.sort(this.objetos, Objeto.taxaComparator);

        for(Objeto objeto : this.objetos) {
            // Verificar se objeto passa do capacidade do veículoo, caso nao, adicionar ele na lista do veiculo.
            // Levando em conta que ja possa ter objetos ocupando a capacidade do veículo
            if(objeto.peso < veiculo.getCapacidadeDisponivel()) {
                this.veiculo.insereObjeto(objeto);
                this.solucao[objeto.id - 1] = 1;
            }
        }

        this.imprimeListaDeObjetos(veiculo.objetos, "Lista de Objetos Carregados:");

        System.out.println("VETOR SOLUÇÃO");
        for(int i : this.solucao) {
            System.out.print(i + " | " );
        }
    }

}
