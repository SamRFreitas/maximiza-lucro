package genetico;

import java.util.ArrayList;

public class Geracao {
    ArrayList<Individuo> individuos = new ArrayList<Individuo>();
    int quantidadeDeIndividuos = individuos.size();

    public Geracao (ArrayList<Individuo> individuos) {
        this.individuos = individuos;
    }

}
