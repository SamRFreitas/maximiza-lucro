package genetico;


import maximiza.Objeto;


import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Gerador {
    // ArrayList<Geracao> geracao = new ArrayList<Geracao>();


    ArrayList<Individuo> populacao = new ArrayList<Individuo>();

    ArrayList<Individuo> individuosSelecionadosParaRproducao = new ArrayList<Individuo>();
    float taxaDeReproducao;


    float taxaDeMutacao;


    int numeroDeGeracoes;


    int quantidadeDeIndividuos;


    public Gerador (float taxaDeReproducao, double taxaDeMutacao, int numeroDeGeracoes, int quantidadeDeIndividuos, ArrayList<Objeto> objetos) {

        for (int i = 1; i <= numeroDeGeracoes; i++) {
            for (int j = 1; j <= quantidadeDeIndividuos; j++) {
                this.populacao.add(new Individuo(objetos, i, j));
            }
        }

        Collections.sort(this.populacao, Individuo.fitComparator);

        Print.mostrarPopulacao(this.populacao, "População Inicial");

        Roleta roleta = new Roleta(this.populacao);

        Print.mostrarPopulacao(roleta.individuosSelecionadosParaRproducao, "Indivíduos Selecionados Para Reprodução: ");



        // reproduzir os selecionados gerando novos individuos e aqui entra a taxa de reprodução
        // levando em conta que cada par gera 2 individuos
        // Solução -> Função botarPraCruzar
        this.botarPraCruzar(roleta.individuosSelecionadosParaRproducao);

        Collections.sort(this.populacao, Individuo.fitComparator);
        // gerar nova geração
        roleta.avaliarPopulacao(this.populacao);

        Print.mostrarPopulacao(this.populacao, "População Atual Sem a Disseminação");

        // mutação é o proximo passo


        // diseemeniação -> Remover os individuos que não respeitem o peso limite e que são as piores
        // soluções, deixando sempre a quantidade de invdividuos baseado na quantidadeDeIndividuos



    }

    public void botarPraCruzar(ArrayList<Individuo> individuosSelecionados) {
        System.out.println("-------------Botando pra cruzar-------------");
        // Utilizar cada par do vetor (Levando em conta que a taxa de reprodução é de 100%)
        // Os pares não estão organizados, eles estão na ordem que foram selecionados


        // Vetor utilizado para adicionar a nova geração
        ArrayList<Individuo> novaGeracao = new ArrayList<Individuo>();

        for(int i=0; i < individuosSelecionados.size() - 1; i=i+2) {
            //System.out.println(individuosSelecionados.get(i).probabilidade);

            // misturar os cromossomos gerando dois novos individuos
            this.recombinar(individuosSelecionados.get(i), individuosSelecionados.get(i+1));
        }
        System.out.println("--------------------------------------------");
    }

    public void recombinar(Individuo individuo1, Individuo individuo2) {
        System.out.println("-------------Recombinando Indivíduos-------------");

        Random random = new Random();
        int posicao = random.nextInt(individuo1.cromossomo.size() - 1);

        ArrayList<Integer> individuo1Sublista = new ArrayList<Integer>(individuo1.cromossomo.subList(0, posicao + 1));

        ArrayList<Integer> individuo2Sublista = new ArrayList<Integer>(individuo2.cromossomo.subList(posicao+1, individuo2.cromossomo.size()));


        ArrayList<Integer> novoCromossomo1 = new ArrayList<Integer>();
        novoCromossomo1.addAll(individuo1Sublista);
        novoCromossomo1.addAll(individuo2Sublista);

        ArrayList<Integer> novoCromossomo2 = new ArrayList<Integer>();
        novoCromossomo2.addAll(individuo2Sublista);
        novoCromossomo2.addAll(individuo1Sublista);


        Individuo novoIndividuo1 = new Individuo(individuo1.objetos, 2, individuo1.indice, novoCromossomo1);

        Individuo novoIndividuo2 = new Individuo(individuo1.objetos, 2, individuo2.indice, novoCromossomo2);


        this.debugarRecombinacao(individuo1, individuo2, individuo1Sublista, individuo2Sublista, novoIndividuo1, novoIndividuo2, posicao);

        this.populacao.add(novoIndividuo1);
        this.populacao.add(novoIndividuo2);
        System.out.println("---------------------------------------------------");
    }

    public void debugarRecombinacao(Individuo individuo1, Individuo individuo2, ArrayList<Integer> individuoSubLista1, ArrayList<Integer> individuoSubLista2, Individuo novoIndividuo1, Individuo novoIndividuo2, int posicao) {
        System.out.println("Cross Over dos Indivíduos");
        System.out.print(individuo1.getNomeIndividuo() + " -> " );
        individuo1.mostrarCromossomos(individuo1.cromossomo);
        System.out.print(" + " + individuo2.getNomeIndividuo() + " -> " );
        individuo2.mostrarCromossomos(individuo2.cromossomo);
        System.out.println("");
        System.out.println("Posição do corte: " + posicao);
        this.debugarPosicaoDaRecombinacao(individuo1, individuoSubLista1, 1, posicao);
        this.debugarPosicaoDaRecombinacao(individuo2, individuoSubLista2, 2, posicao);
        System.out.println("");
        this.debugarNovoIndividuo(novoIndividuo1, 1);
        this.debugarNovoIndividuo(novoIndividuo2, 2);
    }

    public void debugarPosicaoDaRecombinacao(Individuo individuo, ArrayList<Integer> individuoSubLista, int numeroDoPai, int posicao) {
        System.out.println("Individuo " + numeroDoPai + ": ");
        System.out.println(individuo.getNomeIndividuo());
        individuo.mostrarCromossomos(individuo.cromossomo);
        System.out.println("");
        ArrayList<Integer> sublistaIndividuo =
                numeroDoPai == 1 ?
                new ArrayList<Integer>(individuo.cromossomo.subList(0, posicao + 1))
                        :
                        new ArrayList<Integer>(individuo.cromossomo.subList(posicao+1, individuo.cromossomo.size()));
        individuo.mostrarCromossomos(sublistaIndividuo);
        System.out.println("");
    }

    public void debugarNovoIndividuo(Individuo individuo, int numeroDoNovoIndividuo) {
        System.out.println("Novo Indivíduo" + numeroDoNovoIndividuo + ": ");
        System.out.println(individuo.getNomeIndividuo());
        individuo.mostrarCromossomos(individuo.cromossomo);
        System.out.println("");
        System.out.println("");
    }


}

