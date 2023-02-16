import genetico.Individuo;
import maximiza.MaximizadorDeLucro;
import maximiza.Objeto;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        //MaximizadorDeLucro m = new MaximizadorDeLucro();
        //m.otimizar();

        Objeto obj1 = new Objeto(1, 400, 200);
        Objeto obj2 = new Objeto(2, 200, 200);
        Objeto obj3 = new Objeto(3, 700, 300);
        Objeto obj4 = new Objeto(4, 900, 400);
        Objeto obj5 = new Objeto(5, 600, 400);

        ArrayList objetos = new ArrayList<>();
        objetos.add(obj1);
        objetos.add(obj2);
        objetos.add(obj3);
        objetos.add(obj4);
        objetos.add(obj5);

        Individuo i = new Individuo(objetos);
    }

}