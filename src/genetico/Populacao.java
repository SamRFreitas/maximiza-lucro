package genetico;

import java.util.ArrayList;

public class Populacao {
    ArrayList<Individuo> individuos = new ArrayList<Individuo>();
    int quantidadeDeIndividuos = individuos.size();

    public Populacao(ArrayList<Individuo> individuos) {
        this.individuos = individuos;
    }

}
