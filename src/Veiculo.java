
import java.util.ArrayList;

public class Veiculo {

    double capacidade;
    ArrayList<Objeto> objetos = new ArrayList<>();

    public Veiculo (double capacidade) {
        this.capacidade = capacidade;
    }

    public double getCapacidadeDisponivel() {
        double capacidadeDisponivel = this.capacidade;

        for (Objeto objeto : this.objetos) {
            capacidadeDisponivel -= objeto.peso;
        }

        return capacidadeDisponivel;
    }

    public double calcularMassaTotalDosObjetos() {

        double massaTotal = 0;

        for(Objeto objeto : this.objetos) {
            massaTotal += objeto.peso;
        }

        return massaTotal;
    }

    public void insereObjeto(Objeto objeto) {
        this.objetos.add(objeto);
    }

    public void imprimeListaDeObjetos() {

    }

}
