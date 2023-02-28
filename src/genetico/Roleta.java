package genetico;

import java.util.ArrayList;
import java.util.Random;

public class Roleta {
    ArrayList<Individuo> populacao = new ArrayList<Individuo>();
    ArrayList<Individuo> populacaoAtual = new ArrayList<Individuo>();

    ArrayList<Individuo> individuosSelecionados = new ArrayList<Individuo>();

    public Roleta (ArrayList<Individuo> populacao) {
        this.populacao = new ArrayList<>(populacao);
        this.populacaoAtual = new ArrayList<>(populacao);

        ArrayList<Integer> probabilidadesDaPopulacaoAtual = this.criarVetorComAsProbabilidadesDeEscolha(this.populacao);

        ArrayList<Integer> vetorPorcentagem = this.preencherVetorPorcentagem(probabilidadesDaPopulacaoAtual);

        this.girarRoleta(vetorPorcentagem);

        System.out.println("Selecionados: ");
        Print.mostrarPopulacao(this.individuosSelecionados);
    }

    public void calcularProbabilidades (ArrayList<Individuo> populacao) {
        double fitnessTotal = 0;

        for(Individuo individuo : populacao) {
            fitnessTotal += individuo.fitness;
        }

        for(Individuo individuo : populacao) {
            individuo.probabilidade = individuo.fitness/fitnessTotal;
        }
    }

    public ArrayList<Integer> criarVetorComAsProbabilidadesDeEscolha(ArrayList<Individuo> populacao){
        ArrayList<Integer> probabilidadesDaPopulacaoAtual = new ArrayList<>();


        for(Individuo individuo : populacao) {
            int porcentagem = this.calcularProbabilidade(individuo);
            probabilidadesDaPopulacaoAtual.add(porcentagem);
        }

        return probabilidadesDaPopulacaoAtual;
    }

    public int calcularProbabilidade(Individuo individuo) {
        return (int ) Math.round(individuo.probabilidade * 100);
    }

    public ArrayList<Integer> preencherVetorPorcentagem(ArrayList<Integer> probabilidadesDaPopulacaoAtual) {
        //PREENCHER UM VETOR BASEADO A PROBABILIDADE DE ESCOLHA DO INDIVIDUDO
        ArrayList<Integer> vetorPorcentagem = new ArrayList<Integer>(100);


        for(int porcentagem : probabilidadesDaPopulacaoAtual) {
            for(int i=0; i < porcentagem; i++) {
                vetorPorcentagem.add(porcentagem);
                System.out.println("Index: "+ (vetorPorcentagem.size()-1) + " Value: " + porcentagem);
            }
        }

        return vetorPorcentagem;
    }

    public void girarRoleta(ArrayList<Integer> vetorPorcentagem) {

        while (!this.populacaoAtual.isEmpty()) {
            System.out.println("Populacao Atual: ");
            Print.mostrarPopulacao(this.populacaoAtual);

            if(this.populacaoAtual.get(0).fitness == 0 ){

                this.individuosSelecionados.add(this.populacaoAtual.get(0));
                this.populacaoAtual.remove(this.populacaoAtual.get(0));

            }
            else {

                Random random = new Random();

                int index = random.nextInt(99);
                while (vetorPorcentagem.get(index) == -1) {
                    index = random.nextInt(99);
                }
                int probabilidadeEscolhida = vetorPorcentagem.get(index);
                vetorPorcentagem = tirarProbabilidadeEscolhidaDoVetorPorcentagem(probabilidadeEscolhida, vetorPorcentagem);

                for (int i = 0; i < vetorPorcentagem.size() - 1; i++) {
                    System.out.println("Index: " + i + " Value: " + vetorPorcentagem.get(i));
                }


                System.out.println("Index: " + index);
                System.out.println("Porcentagem escolhida: " + probabilidadeEscolhida);

                // Remover e recalcular a probabilidade de escolha do individudo?
                // OU
                // Limpar vetor, e se cair ali rodar de novo?
                Individuo individuo = selecionarIndividuoResultadoDaRoleta(probabilidadeEscolhida);
                this.individuosSelecionados.add(individuo);
                this.populacaoAtual.remove(individuo);

            }


        }

    }

    public ArrayList<Integer> tirarProbabilidadeEscolhidaDoVetorPorcentagem(int probabilidadeEscolhida, ArrayList<Integer> vetorPorcentagem) {

        ArrayList<Integer> vetor = new ArrayList<>(vetorPorcentagem);

        int inicio = 0;

        for(int i=0; i < vetor.size() -1; i++) {
            if (vetor.get(i) == probabilidadeEscolhida) {
                inicio = i;
                break;
            }
        }

        for(int i=inicio; i <= (inicio+probabilidadeEscolhida) - 1; i++) {
            vetor.set(i, -1);
        }

        return vetor;
    }

    public Individuo selecionarIndividuoResultadoDaRoleta(int probabilidadeEscolhida) {
        for (Individuo individuo : this.populacaoAtual) {

            if (this.calcularProbabilidade(individuo) == probabilidadeEscolhida) {
                return individuo;
            }
        }

        return null;
    }

}
