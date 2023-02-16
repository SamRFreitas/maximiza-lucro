package maximiza;

import java.util.Comparator;

public class Objeto {

    int id;
    double peso;
    double lucro;

    public boolean selecionado;

    public Objeto (int id, double peso, double lucro) {
        this.id= id;
        this.peso = peso;
        this.lucro = lucro;
    }

    public double getRazaoLucroPeso() {
        return lucro/peso;
    }

    public static Comparator<Objeto> taxaComparator = new Comparator<Objeto>() {
        @Override
        public int compare(Objeto obj1, Objeto obj2) {

            if (obj1.getRazaoLucroPeso() > obj2.getRazaoLucroPeso()) {
                return -1;
            } else if (obj2.getRazaoLucroPeso() > obj1.getRazaoLucroPeso()) {
                return 1;
            }

            return 0;
        }
    };
}
