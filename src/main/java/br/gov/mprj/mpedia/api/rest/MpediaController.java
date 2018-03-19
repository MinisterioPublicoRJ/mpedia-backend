package br.gov.mprj.mpedia.api.rest;

import br.gov.mprj.mpedia.api.persistence.mybatis.MpediaRepository;
import br.gov.mprj.mpedia.domain.dao.AreasDAO;
import br.gov.mprj.mpedia.domain.dao.TemasDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value="/")
public class MpediaController {

    @Resource
    @Qualifier(value = "MpediaRepository")
    private MpediaRepository mpediaRepository;

    @RequestMapping(value = "/areas", method = RequestMethod.GET)
      public HttpEntity<List<AreasDAO>> areas() {
        return new HttpEntity<>(mpediaRepository.areas());
    }

    @RequestMapping(value = "/temas", method = RequestMethod.GET)
    public HttpEntity<List<TemasDAO>> temas() {
        return new HttpEntity<>(mpediaRepository.temas());
    }
}
