package br.gov.mprj.mpedia.domain.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TabelaDescDAO {
    @JsonProperty(value="nome",index = -1)
    String column_name = "";
    String data_type = "";

    public String getNome() {
        return column_name;
    }

    public String getTipo() {
        return data_type;
    }
}
