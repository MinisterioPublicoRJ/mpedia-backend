package br.gov.mprj.mpedia.api.rest;

import br.gov.mprj.mpedia.api.persistence.mybatis.MpediaRepository;
import br.gov.mprj.mpedia.api.services.ValidaJsonsService;
import br.gov.mprj.mpedia.domain.dao.AreasDAO;
import br.gov.mprj.mpedia.domain.dao.TemasDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/")
public class MpediaController {


    @Resource
    @Qualifier(value = "MpediaRepository")
    private MpediaRepository mpediaRepository;

    @Resource
    @Qualifier(value = "ValidaJsonsService")
    private ValidaJsonsService validaJsonsService;

    static List<AreasDAO> listaAreasDaoValidado = null;

    @RequestMapping(value = "/areas", method = RequestMethod.GET)
    public HttpEntity<List<AreasDAO>> areas() {
        List<AreasDAO> areas = mpediaRepository.areas();
        if (validaJsonsService.validaAreas(areas)) listaAreasDaoValidado = areas;
        if (listaAreasDaoValidado == null) listaAreasDaoValidado =  new ArrayList<>();
        return new HttpEntity<>(listaAreasDaoValidado);
    }

    @RequestMapping(value = "/temas", method = RequestMethod.GET)
    public HttpEntity<List<TemasDAO>> temas() {
        return new HttpEntity<>(validaJsonsService.validaTemas(mpediaRepository.temas()));
    }
}
