package br.gov.mprj.mpedia.api.services;

import br.gov.mprj.mpedia.domain.dao.AreasDAO;
import br.gov.mprj.mpedia.domain.dao.TemasDAO;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Scanner;

@Service
public class ValidaJsonsService {



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

    public boolean validaTemas(List<TemasDAO> temas){
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
}
