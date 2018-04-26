package br.gov.mprj.mpedia.api.services;

import br.gov.mprj.mpedia.domain.dao.AreasDAO;
import br.gov.mprj.mpedia.domain.dao.TemasDAO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class ValidaJsonsService {


    private static boolean testeAreasCorrelatas(String ac){
        String id_ac[];

        if(ac.charAt(0)=='{')
            ac = ac.substring(1);
        else
            return false;

        if(ac.charAt(ac.length()-1) == '}')
            ac = ac.substring(0,ac.length()-1);
        else
            return false;

        Scanner sc = new Scanner(ac.trim());
        String x[]= sc.next().split(",");

        for (int i=0;i<x.length;i++){
            if (!isInteger(x[i],10))
                return false;
        }

        sc.close();
        return true;
    }

    private static boolean testeUrl(String url){
        if((!url.contains("http://"))&&(!url.contains("https://")))
            return false;
        return true;
    }


    private static boolean isInteger(String s, int radix) {
        if (s==null) return false;
        Scanner sc = new Scanner(s.trim());
        if(!sc.hasNextInt(radix)) return false;
        sc.nextInt(radix);
        return !sc.hasNext();
    }

    public boolean validaAreas(List<AreasDAO> areas){
        boolean ret = true;
        for (AreasDAO area: areas) {
            if (!isInteger(area.getId(),10))ret = false; // ID
            if ((area.getNome() == null) || area.getNome().equals("")) ret = false; // Nome
            if ((area.getCor().charAt(0) != '#' ) || (area.getCor().substring(1).length()!=6) || (!isInteger(area.getCor().substring(1),16))) ret = false; // Cor
            if (area.getIcone() == null) ret = false; // Icone
            if (!isInteger(area.getPrioridade(),10)) ret = false; // Prioridade
            if (Integer.valueOf(area.getCount()) == null) ret = false; // Count
        }
        return ret;
    }

    public boolean oldValidaTemas(List<TemasDAO> temas){
        boolean ret = true;
        for (TemasDAO tema: temas) {
            if (!isInteger(tema.getId(),10)) ret = false; // Id
            if (tema.getData_criacao() == null) ret = false;
            if ((tema.getTitulo() == null) || tema.getTitulo().equals("")) ret = false; // Título
            if ((tema.getEndpoint() == null) || tema.getEndpoint().equals("")) ret = false;
            if (!isInteger(tema.getArea_mae_id(),10)) ret = false;
            if ((tema.getArea_mae_nome() == null) || tema.getArea_mae_nome().equals("")) ret = false;
            if ((tema.getCor().charAt(0)) != '#' || (tema.getCor().substring(1).length()!=6) ||(!isInteger(tema.getCor().substring(1),16)))  ret = false; // Cor
            if (tema.getIcone() == null) ret = false; // Icone
            //if (tema.getAreas_correlatas() == null) ret = false; // Areas Correlatas
            //if ((tema.getSubtitulo() == null) || tema.getSubtitulo().equals("")) ret = false; //Subtítulo
            if ((tema.getFonte() == null) || tema.getFonte().equals("")) ret = false;
            if (tema.getUrl() == null ) ret = false;
            if ((tema.getPrioridade() == null) || (!isInteger(tema.getPrioridade(),10))) ret = false;
        }
        return ret;
    }

    public List<TemasDAO> validaTemas(List<TemasDAO> temas){
        boolean ret;
        List<TemasDAO> ltr= new ArrayList<>();
        String ac,url;
        for (TemasDAO tema: temas) {
            ret = true;
            if (!isInteger(tema.getId(),10)) ret=false;//Id
            if (tema.getData_criacao()==null) ret=false;//Data_Criação
            if ((tema.getTitulo()==null)||tema.getTitulo().equals("")) ret=false;//Título
            if ((tema.getEndpoint()==null)||tema.getEndpoint().equals("")) ret=false;//EndPoint
            if (!isInteger(tema.getArea_mae_id(),10)) ret=false;//Area_Mae
            if ((tema.getArea_mae_nome()==null)||tema.getArea_mae_nome().equals("")) ret=false;//Area_Mae_Nome
            if ((tema.getCor().charAt(0))!='#'||(tema.getCor().substring(1).length()!=6)||(!isInteger(tema.getCor().substring(1),16)))  ret=false;//Cor
            if (tema.getIcone()==null) ret=false;//Icone
            if (((ac=tema.getAreas_correlatas())!=null)&&(!ac.equals(""))) ret = testeAreasCorrelatas(ac);//Areas Correlatas
            if ((tema.getSubtitulo()==null)||tema.getSubtitulo().equals("")) ret=false;//Subtítulo
            if ((tema.getFonte()==null)||tema.getFonte().equals("")) ret=false;//Fonte
            if (((url=tema.getUrl())==null)||(url.equals(""))) ret=false; else ret = testeUrl(url);//Url
            if ((tema.getPrioridade()==null)||(!isInteger(tema.getPrioridade(),10))) ret=false;//Prioridade

            if (ret) ltr.add(tema);
        }
        return ltr;
    }

}
