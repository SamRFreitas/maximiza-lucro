
import java.util.ArrayList;

public class Veiculo {

    double capacidade;
    ArrayList<Objeto> objetos;

    public Veiculo (double capacidade) {
        this.capacidade = capacidade;
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
