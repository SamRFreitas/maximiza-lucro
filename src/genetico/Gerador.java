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
    double taxaDeReproducao;


    Integer taxaDeMutacao;


    int numeroDeGeracoes;


    int quantidadeDeIndividuos;

    double pesoLimite;


    public Gerador (double taxaDeReproducao, double taxaDeMutacao, int numeroDeGeracoes, int quantidadeDeIndividuos, ArrayList<Objeto> objetos, double pesoLimite) {

        this.pesoLimite = pesoLimite;
        this.quantidadeDeIndividuos = quantidadeDeIndividuos;
        this.taxaDeMutacao = (int) Math.round(taxaDeMutacao * 100);
        this.taxaDeReproducao = (int) Math.round(taxaDeReproducao * 100);

        for (int i = 1; i <= quantidadeDeIndividuos; i++) {
            this.populacao.add(new Individuo(objetos, 1, i));
        }

        for (int i = 2; i <= numeroDeGeracoes; i++) {
            this.numeroDeGeracoes = i;

            Collections.sort(this.populacao, Individuo.fitComparator);

            Print.mostrarPopulacao(this.populacao, "População Inicial");

            Roleta roleta = new Roleta(this.populacao);

            Print.mostrarPopulacao(roleta.individuosSelecionadosParaRproducao, "Indivíduos Selecionados Para Reprodução: ");



            // reproduzir os selecionados gerando novos individuos e aqui entra a taxa de reprodução
            // levando em conta que cada par gera 2 individuos
            // Solução -> Função botarPraCruzar
            ArrayList<Individuo> novaGeracao = new ArrayList<Individuo>();
            int maximoDeIndividuosBaseadoNaTaxaDeReproducao = (int) this.taxaDeReproducao * this.populacao.size()/100;
            if (maximoDeIndividuosBaseadoNaTaxaDeReproducao < 2) {
                System.out.println("Baseado na Taxa de Reprodução: " + this.taxaDeReproducao + "%");
                System.out.println("A quantidade de pares de indivíduos disponíveis não consegue se reproduzir!");
                System.out.println("Por favor, aumente a taxa de reprodução e rode o programa novamente.");
                break;
            } else {
                novaGeracao = this.botarPraCruzar(roleta.individuosSelecionadosParaRproducao, maximoDeIndividuosBaseadoNaTaxaDeReproducao);
            }

            this.populacao.addAll(novaGeracao);

            Collections.sort(this.populacao, Individuo.fitComparator);
            // gerar nova geração
            roleta.avaliarPopulacao(this.populacao);

            System.out.println("Tamanho da população: " + this.populacao.size());
            Print.mostrarPopulacao(this.populacao, "População Atual Sem a Disseminação");

            // diseemeniação -> Remover os individuos que não respeitem o peso limite e que são as piores
            // soluções, deixando sempre a quantidade de invdividuos baseado na quantidadeDeIndividuos
            this.disseminar(this.populacao);

        }

        System.out.println("---------------------------------------");
        System.out.print("Melhor Solução: ");
        System.out.print(this.populacao.get(0).getNomeIndividuo());
        this.populacao.get(0).mostrarCromossomos(this.populacao.get(0).cromossomo);
        System.out.println();
        System.out.println("Lucro: " + this.populacao.get(0).fitness);
        System.out.println("Peso: " + this.populacao.get(0).peso);

    }

    public ArrayList<Individuo> botarPraCruzar(ArrayList<Individuo> individuosSelecionados, int maximoDeIndividuosBaseadoNaTaxaDeReproducao) {
        System.out.println("-------------Botando pra cruzar-------------");
        // Utilizar cada par do vetor (Levando em conta que a taxa de reprodução é de 100%)
        // Os pares não estão organizados, eles estão na ordem que foram selecionados


        // Vetor utilizado para adicionar a nova geração e aplicar a mutação depois
        ArrayList<Individuo> novaGeracao = new ArrayList<Individuo>();

        int maximoDeParesBaseadoNaTaxaDeReproducao = (int) this.taxaDeReproducao * individuosSelecionados.size()/100;

        for(int i=0; i < maximoDeIndividuosBaseadoNaTaxaDeReproducao; i=i+2) {
            //System.out.println(individuosSelecionados.get(i).probabilidade);

            // misturar os cromossomos gerando dois novos individuos
            novaGeracao.addAll(this.recombinar(individuosSelecionados.get(i), individuosSelecionados.get(i+1)));
        }


        // Aplicar mutação baseado na taxa de mutação
        if( this.aplicaMutacao()) {
            novaGeracao = this.aplicarMutacao(novaGeracao);
        }

        System.out.println("--------------------------------------------");

        return novaGeracao;
    }

    public ArrayList<Individuo> recombinar(Individuo individuo1, Individuo individuo2) {
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


        Individuo novoIndividuo1 = new Individuo(individuo1.objetos, this.numeroDeGeracoes, individuo1.indice, novoCromossomo1);

        Individuo novoIndividuo2 = new Individuo(individuo1.objetos, this.numeroDeGeracoes, individuo2.indice, novoCromossomo2);


        this.debugarRecombinacao(individuo1, individuo2, individuo1Sublista, individuo2Sublista, novoIndividuo1, novoIndividuo2, posicao);

        ArrayList<Individuo> novosIndividuos = new ArrayList<Individuo>();
        novosIndividuos.add(novoIndividuo1);
        novosIndividuos.add(novoIndividuo2);
        System.out.println("---------------------------------------------------");

        return novosIndividuos;
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
        if(numeroDoPai == 2){
            if(posicao == 2) {
                for(int i=0; i < (posicao * 5) + 2; i++) {
                    System.out.print(" ");
                }
            } else if(posicao == 0) {
                for(int i=0; i < (posicao * 5) + 4; i++) {
                    System.out.print(" ");
                }
            } else if(posicao == 1) {
                for(int i=0; i < (posicao * 5) + 3; i++) {
                    System.out.print(" ");
                }
            }else {
                for(int i=0; i < (posicao * 5) + 1; i++) {
                    System.out.print(" ");
                }
            }
        }
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

    public boolean aplicaMutacao() {

        Random random = new Random();
        boolean aplica;

        System.out.println("Taxa de mutação " + this.taxaDeMutacao + "%");

        ArrayList<Integer> vetorPorcentagem = new ArrayList<Integer>(100);
        for(int i=0; i <= 99; i++) {
            if (i < this.taxaDeMutacao - 1) {
                vetorPorcentagem.add(this.taxaDeMutacao);
            } else {
                vetorPorcentagem.add(-1);
            }
        }

        int indexSorteado = random.nextInt(99);

        System.out.println("Valor sorteado: " + vetorPorcentagem.get(indexSorteado));
        aplica = vetorPorcentagem.get(indexSorteado) == taxaDeMutacao ? true : false;

        return aplica;
    }

    public ArrayList<Individuo> aplicarMutacao(ArrayList<Individuo> novaGeracao) {
        System.out.println("--------------Aplicando Mutação------------------------");

        Print.mostrarPopulacao(novaGeracao, "Verificar se vetor antes de aplicar a mutação");

        Random random = new Random();

        int indexDoVetorParaApliarMutacao = random.nextInt(novaGeracao.size() - 1);

        System.out.println("Index do vetor para aplicar mutação: " + indexDoVetorParaApliarMutacao);

        Individuo individuoSelecionado = novaGeracao.get(indexDoVetorParaApliarMutacao);

        System.out.println("Nome do Indivíduo sorteado: " + individuoSelecionado.getNomeIndividuo());

        int indexDoCromossoParaMutacao = random.nextInt(individuoSelecionado.cromossomo.size() - 1);

        System.out.println("Index do cromossomo para aplicar mutação: " + indexDoCromossoParaMutacao);

        System.out.println("Antes da mutação: ");
        individuoSelecionado.mostrarCromossomos(individuoSelecionado.cromossomo);
        System.out.println("");


        int gene = individuoSelecionado.cromossomo.get(indexDoCromossoParaMutacao) == 1 ? 0 : 1;
        individuoSelecionado.cromossomo.set(indexDoCromossoParaMutacao, gene);

        System.out.println("Depois da mutação: ");
        individuoSelecionado.mostrarCromossomos(individuoSelecionado.cromossomo);
        System.out.println("");

        individuoSelecionado.selecionarObjetos();

        Print.mostrarPopulacao(novaGeracao, "Verificar se vetor alterou o individuo");

        System.out.println("--------------------------------------------------");

        return novaGeracao;

    }

    public void disseminar(ArrayList<Individuo> populacao) {
        ArrayList<Individuo> individuosParaRemover = new ArrayList<Individuo>();
        // Iterar vetor this.populacao e remover individuo > pesoLimite && pioresSolucoes

        for(Individuo individuo : populacao) {

            if(individuo.peso > this.pesoLimite) {
                individuosParaRemover.add(individuo);
            }

        }

        this.populacao.removeAll(individuosParaRemover);


        System.out.println("Tamanho da População: " + this.populacao.size());
        Print.mostrarPopulacao(this.populacao, "População Sem Passar do Peso Limite: ");

        // No final o vetor deve possuir o mesmo tamanho de numeroDeIndividuos

        for(int i=this.populacao.size()-1; i > quantidadeDeIndividuos -1; i--) {
            this.populacao.remove(this.populacao.get(i));
        }

        System.out.println("Tamanho da População: " + this.populacao.size());
        Print.mostrarPopulacao(this.populacao, "População Disseminada: ");
    }

}

