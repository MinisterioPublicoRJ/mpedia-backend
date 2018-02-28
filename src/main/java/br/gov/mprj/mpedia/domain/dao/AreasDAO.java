package br.gov.mprj.mpedia.domain.dao;


public class AreasDAO {

    String id = "";
    String titulo = "";
    String endpoint = "";
    String area_mae_id = "";
    String area_mae_nome = "";
    String cor= "";
    String icone="";
    String areas_correlatas="";
    String subtitulo="";
    String descricao="";
    String fonte="";
    String observacao="";
    String url="";
    String prioridade="";

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getArea_mae_id() {
        return area_mae_id;
    }

    public String getArea_mae_nome() {
        return area_mae_nome;
    }

    public String getCor() {
        return cor;
    }

    public String getIcone() {
        return icone;
    }

    public String getAreas_correlatas() {
        return areas_correlatas;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getFonte() {
        return fonte;
    }

    public String getObservacao() {
        return observacao;
    }

    public String getUrl() {
        return url;
    }

    public String getPrioridade() {
        return prioridade;
    }
}
