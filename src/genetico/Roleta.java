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

        this.avaliarPopulacao(this.populacao);
        Print.mostrarPopulacao(this.populacao, "Populacao Inicial Avaliada: ");

        this.girarRoleta();

        this.avaliarPopulacao(this.individuosSelecionados);
    }

    public void avaliarPopulacao (ArrayList<Individuo> populacao) {
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
            }
        }

        return vetorPorcentagem;
    }

    public void girarRoleta() {

        while (!this.populacaoAtual.isEmpty()) {
            // Quando o Fit é zero, esse indivíduo nunca entra no vetorPorcentagem e nunca é selecionado,
            // ficando sempre o ultimo no vetor populacaoAtual
            if(this.populacaoAtual.get(0).fitness == 0 ){

                this.individuosSelecionados.add(this.populacaoAtual.get(0));
                this.populacaoAtual.remove(this.populacaoAtual.get(0));

            }
            else {
                Random random = new Random();

                int index = random.nextInt(99);
                ArrayList<Integer> vetorPorcentagem = this.calcularVetorPorcentagem();
                while (vetorPorcentagem.get(index) == -1) {
                    index = random.nextInt(99);
                }

                int probabilidadeEscolhida = vetorPorcentagem.get(index);

                Individuo individuo = selecionarIndividuoResultadoDaRoleta(probabilidadeEscolhida);
                this.individuosSelecionados.add(individuo);
                this.populacaoAtual.remove(individuo);

            }


        }

    }

    public ArrayList<Integer> calcularVetorPorcentagem() {
        this.avaliarPopulacao(this.populacaoAtual);
        ArrayList<Integer> probabilidadesDaPopulacaoAtual = new ArrayList<>(this.criarVetorComAsProbabilidadesDeEscolha(this.populacaoAtual));

        ArrayList<Integer> vetorPorcentagem = new ArrayList<>(this.preencherVetorPorcentagem(probabilidadesDaPopulacaoAtual));

        return vetorPorcentagem;
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
