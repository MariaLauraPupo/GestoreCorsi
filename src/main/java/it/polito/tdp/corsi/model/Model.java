package it.polito.tdp.corsi.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.db.CorsoDAO;

public class Model {
	private CorsoDAO corsoDAO;
	
	public Model() {
		corsoDAO = new CorsoDAO();
			
		
	}
	public List<Corso> getCorsiByPeriodo(Integer pd){
		return corsoDAO.getCorsiByPeriodo(pd);
	}
	
	public Map<Corso,Integer> getIscrittiByPeriodo(Integer pd){
		return corsoDAO.getIscrittiByPeriodo(pd);
	}
	public List<Studente> getStudentiByCorso(String codice){
		return corsoDAO.getStudentiByCorso(new Corso(codice, null, null, null));
	}
	
	public Map<String, Integer> getDivisioneCDS (String codice) {
		/*cosa ci aspettiamo:
		  dato il codice ABC
		  GEST -> 50
		  INF -> 40
		  MEC -> 30 */
		
		//SOLUZIONE 1
		/*Map<String, Integer> divisione = new HashMap<String, Integer>();
		//String --> CDS
		//Integer --> numero di studenti con quel CDS
		
		List<Studente> studenti = this.getStudentiByCorso(codice);
		for(Studente s : studenti) {
			//se quel CDS c'è già andiamo a incrementarne il valore, se on c'è ancora andiamo a creare la coppia e impostiamo il valore a 1 
			if(s.getCDS() != null && s.getCDS().equals(" ")) {
			if(divisione.get(s.getCDS()) == null) {
				divisione.put(s.getCDS(), 1);
			}else {
				divisione.put(s.getCDS(), divisione.get(s.getCDS()) +1);
			}
		}
		}
		return divisione;*/
		//SOLUZIONE 2
		//faccio una nuova query nella classe CorsoDAO
		return corsoDAO.getDivisioneStudenti(new Corso(codice,null,null,null));
	}
	
	public boolean esisteCorso(String codice) {
		return corsoDAO.esisteCorso(new Corso(codice,null, null, null));

	}
}
