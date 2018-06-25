package br.gov.mprj.mpedia.api.persistence.mybatis;

import br.gov.mprj.mpedia.domain.dao.AreasDAO;
import br.gov.mprj.mpedia.domain.dao.TabelaDescDAO;
import br.gov.mprj.mpedia.domain.dao.TabelasDAO;
import br.gov.mprj.mpedia.domain.dao.TemasDAO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MpediaRepository {
    List<TabelasDAO> views(@Param("esquema") String esquema);

    List<TabelaDescDAO> desc(@Param("esquema") String esquema, @Param("tablename") String tablename);

    List<AreasDAO> areas();

    List<TemasDAO> temas();
}
