package br.gov.mprj.mpedia.domain.dao;


public class AreasDAO {

    String id = "";
    String nome = "";
    String cor = "";
    String icone = "";
    String prioridade = "";
    int count = 0;

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCor() {
        return cor;
    }

    public String getIcone() {
        return icone;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public int getCount() {
        return count;
    }
}
