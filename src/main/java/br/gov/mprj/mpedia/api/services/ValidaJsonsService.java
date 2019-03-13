package br.gov.mprj.mpedia.api.services;

import br.gov.mprj.mpedia.domain.dao.TemasDAO;
import br.gov.mprj.mpedia.domain.dto.AreasDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class ValidaJsonsService {
    private static boolean isInteger(String s, int radix) {
        if (s==null) return false;
        Scanner sc = new Scanner(s.trim());
        if(!sc.hasNextInt(radix)) return false;
        sc.nextInt(radix);
        sc.close();
        return true;
    }

    private static boolean testeAreasCorrelatas(String ac){
        String id_ac[];
        if(ac.charAt(0)=='{')
            ac = ac.substring(1);
        else return false;
        if(ac.charAt(ac.length()-1) == '}')
            ac = ac.substring(0,ac.length()-1);
        else return false;
        Scanner sc = new Scanner(ac.trim());
        String x[]= sc.next().split(",");
        for (int i=0;i<x.length;i++){ if (!isInteger(x[i],10)) return false;}
        sc.close();
        return true;
    }

    private static boolean testeUrl(String url){
        if((!url.contains("http://"))&&(!url.contains("https://"))) return false;
        return true;
    }

    private static boolean testeCor(String cor){
        if((cor.charAt(0))!='#'||(cor.substring(1).length()!=6)||(!isInteger(cor.substring(1),16)))  return false;
        return true;//Cor
    }

    public List<AreasDTO> validaAreas(List<AreasDTO> areas){
        boolean ret;
        List<AreasDTO> lar =  new ArrayList<>();
        for (AreasDTO area: areas) {
            if(area.getId()==null) continue; else if (!isInteger(area.getId(),10))continue;//Id
            if ((area.getNome()==null)) continue; else if(area.getNome().equals("")) continue;//Nome
            if (area.getCor()==null) continue; else if(area.getCor().equals("")) continue; else if(!testeCor(area.getCor()))continue ;//Cor
            if (area.getIcone()==null) continue;//Icone
            if(area.getPrioridade()==null) continue; else if (!isInteger(area.getPrioridade(),10)) continue;//Prioridade
            if (Integer.valueOf(area.getCount())==null) continue;//Count
            lar.add(area);
        }
        return lar;
    }


    public List<TemasDAO> validaTemas(List<TemasDAO> temas){
        List<TemasDAO> ltr= new ArrayList<>();
        String ac,url;
        for (TemasDAO tema: temas) {
            if(tema.getId()==null) continue; else if(!isInteger(tema.getId(),10)) continue;//Id
            if(tema.getData_criacao()==null) continue;//Data_Criação
            if((tema.getTitulo()==null)) continue; else if(tema.getTitulo().equals("")) continue;//Título
            if((tema.getEndpoint()==null)) continue; else if(tema.getEndpoint().equals("")) continue;//EndPoint
            if(tema.getArea_mae_id()==null) continue; else if (!isInteger(tema.getArea_mae_id(),10)) continue;//Area_Mae
            if((tema.getArea_mae_nome()==null)) continue; else if(tema.getArea_mae_nome().equals("")) continue;//Area_Mae_Nome
            if((tema.getCor()==null)) continue; else if(tema.getCor().equals("")) continue; else {if(!testeCor(tema.getCor())) continue;}
            if (tema.getIcone()==null) continue;//Icone
            if(((ac=tema.getAreas_correlatas())!=null)&&(!ac.equals(""))) {if(!testeAreasCorrelatas(ac)) continue;}//Areas Correlatas
            if((tema.getSubtitulo()==null))continue; else if(tema.getSubtitulo().equals("")) continue;//Subtítulo
            if((tema.getFonte()==null)) continue; else if(tema.getFonte().equals("")) continue;//Fonte
            if(((url=tema.getUrl())==null)) continue; else if((url.equals(""))) continue; else {if(!testeUrl(url)) continue;}//Url
            if((tema.getPrioridade()==null)) continue; else if((!isInteger(tema.getPrioridade(),10))) continue;//Prioridade
            ltr.add(tema);
        }
        return ltr;
    }
}
