package br.gov.mprj.mpedia.api.rest;

import br.gov.mprj.mpedia.api.persistence.mybatis.MpediaRepository;
import br.gov.mprj.mpedia.api.services.ValidaJsonsService;
import br.gov.mprj.mpedia.domain.dao.AreasDAO;
import br.gov.mprj.mpedia.domain.dao.TabelaDescDAO;
import br.gov.mprj.mpedia.domain.dao.TabelasDAO;
import br.gov.mprj.mpedia.domain.dao.TemasDAO;
import br.gov.mprj.mpedia.domain.dto.AreasDTO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.sql.DataSource;
import java.net.InetAddress;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/")
public class MpediaController {
    @Autowired
    DataSource dataSource;
    @Resource
    @Qualifier(value = "MpediaRepository")
    private MpediaRepository mpediaRepository;
    @Resource
    @Qualifier(value = "ValidaJsonsService")
    private ValidaJsonsService validaJsonsService;

    public static class ResultSetConverter {
        public static JSONArray convert(ResultSet rs) throws SQLException, JSONException {
            JSONArray json = new JSONArray();
            ResultSetMetaData rsmd = rs.getMetaData();
            while(rs.next()) {
                int numColumns = rsmd.getColumnCount();
                JSONObject obj = new JSONObject();
                for (int i=1; i<numColumns+1; i++) {
                    obj.put(rsmd.getColumnName(i), rs.getObject(rsmd.getColumnName(i)));
                }
                json.put(obj);
            }
            return json;
        }
    }

    @RequestMapping(value = "/views/{esquema}", method = RequestMethod.GET)
    public HttpEntity<List<TabelasDAO>> views(@PathVariable String esquema) {
        String addr="";
        InetAddress ip;
        String port="";
        //  try {
        //  addr= InetAddress.getLocalHost().getHostName();
        // }catch (Exception e){}
        //  System.out.println(addr);
        addr="apps.mprj.mp.br";
        List<TabelasDAO> lt = mpediaRepository.views(esquema);
        for(TabelasDAO t:lt){
            List<TabelaDescDAO> lc = mpediaRepository.desc(esquema,t.nome());
            t.colunas(lc);
            t.link("http://"+addr+"/mpedia/api/dados/"+esquema+"/"+t.nome());
        }
        return new HttpEntity<>(lt);
    }

    @RequestMapping(value = "/dados/{esquema}/{tabela}", method = RequestMethod.GET)
    public HttpEntity<String> dados(@PathVariable String esquema,
                                    @PathVariable String tabela,
                                    @RequestParam(required = false, defaultValue = "", value="campo") String campo,
                                    @RequestParam(required = false, defaultValue = "=", value="oper") String oper,
                                    @RequestParam(required = false, defaultValue = "", value="valor") String valor){
        JSONArray jrst = new JSONArray();
        ResultSet rst;
        String query ="";
        if( !campo.equals("") && !valor.equals(""))  query = " where "+ campo + " " + oper + " " + valor;
        try {
            Connection conn = dataSource.getConnection();
            Statement stmt=conn.createStatement();
            rst=stmt.executeQuery("Select * from " + esquema +"."+ tabela + query);
            jrst = ResultSetConverter.convert(rst);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new HttpEntity<>(jrst.toString());
    }

    @RequestMapping(value = "/areas", method = RequestMethod.GET)
    public HttpEntity<List<AreasDTO>> areas() {
        List<AreasDTO> areas = generateAreasDTO(mpediaRepository.areas());
        return new HttpEntity<>(validaJsonsService.validaAreas(areas));
    }

    private List<AreasDTO> generateAreasDTO(List<AreasDAO> areasDAO) {
        List<AreasDTO> areasDTO = new ArrayList<>();
        List<TemasDAO> temas = validaJsonsService.validaTemas(mpediaRepository.temas());

        for (AreasDAO areaDAO: areasDAO){
            AreasDTO areaDTO = new AreasDTO();
            String area_id = areaDAO.getId();

            areaDTO.setId(area_id);
            areaDTO.setNome(areaDAO.getNome());
            areaDTO.setCor(areaDAO.getCor());
            areaDTO.setIcone(areaDAO.getIcone());
            areaDTO.setPrioridade(areaDAO.getPrioridade());
            areaDTO.setCount(countTemasArea(temas, area_id));

            areasDTO.add(areaDTO);
        }
        return areasDTO;
    }

    private Integer countTemasArea(List<TemasDAO> temasValidos, String areaId) {
        Integer count = 0;
        for (TemasDAO tema : temasValidos){
            if (tema.getArea_mae_id().equals(areaId))
                count++;
        }
        return count;
    }

    @RequestMapping(value = "/temas", method = RequestMethod.GET)
    public HttpEntity<List<TemasDAO>> temas() {
        return new HttpEntity<>(validaJsonsService.validaTemas(mpediaRepository.temas()));
    }
}
