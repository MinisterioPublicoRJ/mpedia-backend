package br.gov.mprj.mpedia.domain.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class TabelasDAO {

    @JsonProperty(value="nome",index = 1)
    String table_name = "";
    @JsonProperty(value="descricao",index = 2)
    String description = "";
    @JsonProperty(value="link",index = 3)
    String link_dados = "";
    @JsonProperty(value="campos",index = 4)
    List<TabelaDescDAO> colunas = null;

    public String nome() { return table_name; }
    public void colunas(List<TabelaDescDAO> colunas) { this.colunas = colunas; }
    public void link(String link){this.link_dados=link;}
}
