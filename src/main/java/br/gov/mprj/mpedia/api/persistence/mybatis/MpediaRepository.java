package br.gov.mprj.mpedia.api.persistence.mybatis;

import br.gov.mprj.mpedia.domain.dao.AreasDAO;
import br.gov.mprj.mpedia.domain.dao.TemasDAO;

import java.util.List;

public interface MpediaRepository {
    List<AreasDAO> areas();
    List<TemasDAO> temas();
}
