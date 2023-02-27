package genetico;

import java.util.ArrayList;
import java.util.Random;

public class Roleta {
    ArrayList<Individuo> populacao = new ArrayList<Individuo>();
    ArrayList<Individuo> populacaoAtual = new ArrayList<Individuo>();

    ArrayList<ArrayList<Individuo>> paresSelecionados = new ArrayList<ArrayList<Individuo>>();

    public Roleta (ArrayList<Individuo> populacao) {
        this.populacao = new ArrayList<>(populacao);
        this.populacaoAtual = new ArrayList<>(populacao);

        ArrayList<Integer> probabilidadesDaPopulacaoAtual = this.criarVetorComAsProbabilidadesDeEscolha(this.populacao);

        ArrayList<Integer> vetorPorcentagem = this.preencherVetorPorcentagem(probabilidadesDaPopulacaoAtual);

        this.girarRoleta(vetorPorcentagem);

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
        Random random = new Random();

        int index = random.nextInt(99);
        int probabilidadeEscolhida = vetorPorcentagem.get(index);


        System.out.println("Index: " + index);
        System.out.println("Porcentagem escolhida: " + probabilidadeEscolhida);

        // Remover e recalcular a probabilidade de escolha do individudo?
        // OU
        // Limpar vetor, e se cair ali rodar de novo?

        for (Individuo individuo : this.populacao) {
            if(this.calcularProbabilidade(individuo) == probabilidadeEscolhida) {
                this.populacaoAtual.remove(individuo);
            }
        }

        Print.mostrarPopulacao(this.populacaoAtual);
    }

}
