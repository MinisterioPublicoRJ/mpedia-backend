package br.gov.mprj.mpedia.api.services;

import br.gov.mprj.mpedia.domain.dao.AreasDAO;
import br.gov.mprj.mpedia.domain.dao.TemasDAO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class ValidaJsonsService {
    private static boolean isInteger(String s, int radix) {
        if (s == null)
            return false;
        Scanner sc = new Scanner(s.trim());
        if (!sc.hasNextInt(radix))
            return false;
        sc.nextInt(radix);
        sc.close();
        return true;
    }

    private static boolean testeAreasCorrelatas(String ac) {
        String id_ac[];
        if (ac.charAt(0) == '{')
            ac = ac.substring(1);
        else
            return false;
        if (ac.charAt(ac.length() - 1) == '}')
            ac = ac.substring(0, ac.length() - 1);
        else
            return false;
        Scanner sc = new Scanner(ac.trim());
        String x[] = sc.next().split(",");
        for (int i = 0; i < x.length; i++) {
            if (!isInteger(x[i], 10))
                return false;
        }
        sc.close();
        return true;
    }

    private static boolean testeUrl(String url) {
        if ((!url.contains("http://")) && (!url.contains("https://")))
            return false;
        return true;
    }

    private static boolean testeCor(String cor) {
        if ((cor.charAt(0)) != '#' || (cor.substring(1).length() != 6) || (!isInteger(cor.substring(1), 16)))
            return false;
        return true;// Cor
    }

    public boolean validaAreasOld(List<AreasDAO> areas) {
        boolean ret = true;
        for (AreasDAO area : areas) {
            if (area.getId() == null)
                ret = false;
            else if (!isInteger(area.getId(), 10))
                ret = false;// Id
            if ((area.getNome() == null))
                ret = false;
            else if (area.getNome().equals(""))
                ret = false;// Nome
            if (area.getCor() == null)
                ret = false;
            else if (area.getCor().equals(""))
                ret = false;
            else
                ret = testeCor(area.getCor());// Cor
            if (area.getIcone() == null)
                ret = false;// Icone
            if (area.getPrioridade() == null)
                ret = false;
            else if (!isInteger(area.getPrioridade(), 10))
                ret = false;// Prioridade
            if (Integer.valueOf(area.getCount()) == null)
                ret = false;// Count
        }
        return ret;
    }

    public List<AreasDAO> validaAreas(List<AreasDAO> areas) {
        boolean ret;
        List<AreasDAO> lar = new ArrayList<>();
        for (AreasDAO area : areas) {
            ret = true;
            if (area.getId() == null)
                ret = false;
            else if (!isInteger(area.getId(), 10))
                ret = false;// Id
            if ((area.getNome() == null))
                ret = false;
            else if (area.getNome().equals(""))
                ret = false;// Nome
            if (area.getCor() == null)
                ret = false;
            else if (area.getCor().equals(""))
                ret = false;
            else
                ret = testeCor(area.getCor());// Cor
            if (area.getIcone() == null)
                ret = false;// Icone
            if (area.getPrioridade() == null)
                ret = false;
            else if (!isInteger(area.getPrioridade(), 10))
                ret = false;// Prioridade
            if (Integer.valueOf(area.getCount()) == null)
                ret = false;// Count
            if (ret)
                lar.add(area);
        }
        return lar;
    }

    public boolean validaTemasOld(List<TemasDAO> temas) {
        boolean ret = true;
        int temaId;
        List<TemasDAO> t = new ArrayList<>();
        String ac, url;
        for (TemasDAO tema : temas) {
            if (!isInteger(tema.getId(), 10))
                ret = false;// Id
            if (tema.getData_criacao() == null)
                ret = false;// Data_Criação
            if ((tema.getTitulo() == null) || tema.getTitulo().equals(""))
                ret = false;// Título
            if ((tema.getEndpoint() == null) || tema.getEndpoint().equals(""))
                ret = false;// EndPoint
            if (!isInteger(tema.getArea_mae_id(), 10))
                ret = false;// Area_Mae
            if ((tema.getArea_mae_nome() == null) || tema.getArea_mae_nome().equals(""))
                ret = false;// Area_Mae_Nome
            if ((tema.getCor().charAt(0)) != '#' || (tema.getCor().substring(1).length() != 6)
                    || (!isInteger(tema.getCor().substring(1), 16)))
                ret = false;// Cor
            if (tema.getIcone() == null)
                ret = false;// Icone
            if (((ac = tema.getAreas_correlatas()) != null) && (!ac.equals("")))
                ret = testeAreasCorrelatas(ac);// Areas Correlatas
            if ((tema.getSubtitulo() == null) || tema.getSubtitulo().equals(""))
                ret = false;// Subtítulo
            if ((tema.getFonte() == null) || tema.getFonte().equals(""))
                ret = false;// Fonte
            if (((url = tema.getUrl()) == null) || (url.equals("")))
                ret = false;
            else
                ret = testeUrl(url);// Url
            if ((tema.getPrioridade() == null) || (!isInteger(tema.getPrioridade(), 10)))
                ret = false;// Prioridade

            if (!ret)
                t.add(tema);
        }
        for (TemasDAO tema : t) {
            temas.remove(tema);
        }
        return true;
    }

    public List<TemasDAO> validaTemas(List<TemasDAO> temas) {
        boolean ret;
        List<TemasDAO> ltr = new ArrayList<>();
        String ac, url;
        for (TemasDAO tema : temas) {
            ret = true;
            if (tema.getId() == null)
                ret = false;
            else if (!isInteger(tema.getId(), 10))
                ret = false;// Id
            if (tema.getData_criacao() == null)
                ret = false;// Data_Criação
            if ((tema.getTitulo() == null))
                ret = false;
            else if (tema.getTitulo().equals(""))
                ret = false;// Título
            if ((tema.getEndpoint() == null))
                ret = false;
            else if (tema.getEndpoint().equals(""))
                ret = false;// EndPoint
            if (tema.getArea_mae_id() == null)
                ret = false;
            else if (!isInteger(tema.getArea_mae_id(), 10))
                ret = false;// Area_Mae
            if ((tema.getArea_mae_nome() == null))
                ret = false;
            else if (tema.getArea_mae_nome().equals(""))
                ret = false;// Area_Mae_Nome
            if ((tema.getCor() == null))
                ret = false;
            else if (tema.getCor().equals(""))
                ret = false;
            else
                ret = testeCor(tema.getCor());
            if (tema.getIcone() == null)
                ret = false;// Icone
            if (((ac = tema.getAreas_correlatas()) != null) && (!ac.equals("")))
                ret = testeAreasCorrelatas(ac);// Areas Correlatas
            if ((tema.getSubtitulo() == null))
                ret = false;
            else if (tema.getSubtitulo().equals(""))
                ret = false;// Subtítulo
            if ((tema.getFonte() == null))
                ret = false;
            else if (tema.getFonte().equals(""))
                ret = false;// Fonte
            if (((url = tema.getUrl()) == null))
                ret = false;
            else if ((url.equals("")))
                ret = false;
            else
                ret = testeUrl(url);// Url
            if ((tema.getPrioridade() == null))
                ret = false;
            else if ((!isInteger(tema.getPrioridade(), 10)))
                ret = false;// Prioridade
            if (ret)
                ltr.add(tema);
        }
        return ltr;
    }
}
