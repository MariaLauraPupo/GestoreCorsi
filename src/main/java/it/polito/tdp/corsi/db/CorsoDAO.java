package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Studente;

public class CorsoDAO {
	
	
//devo creare il metodo per eseguire la query
	public List<Corso> getCorsiByPeriodo(Integer periodo) {
	String sql=" SELECT * "
			+ " FROM corso "
	        + " WHERE pd = ? ";
	
	//mi costruisco la struttura dati in cui saverò i miei risultati e che poi ritirnrò al chiamante 
	List<Corso> result = new ArrayList<Corso>();
	
	//operazioni che vado ad effettuare sul database da inserire dentro un try catch
	
	try {
		Connection conn = DBConnect.getConnection();
		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, periodo);
		ResultSet rs = st.executeQuery();
		
		while(rs.next()) {
			//per ogni riga creo un nuovo corso
			Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
			//questo corso lo vado ad aggiungere a result
			result.add(c);
		}
		//chiudo le connessioni
		rs.close();
		st.close();
	    conn.close();
	
          }catch(SQLException e){
        	  throw new RuntimeException(e);
	
} 
	return result;
	}
	public Map<Corso, Integer> getIscrittiByPeriodo(Integer periodo){
		String sql= "SELECT c.codins, c.nome, c.crediti, c.pd, COUNT(*) AS tot "
				+"FROM corso c, iscrizione i "
				+"WHERE c.codins = i.codins AND c.pd = ? "
				+"GROUP BY c.codins, c.nome, c.crediti, c.pd ";
		
		//mi costruisco la struttura dati in cui saverò i miei risultati e che poi ritirnrò al chiamante 
		Map<Corso,Integer> result = new HashMap<Corso,Integer>();
		
		//operazioni che vado ad effettuare sul database da inserire dentro un try catch
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, periodo);
			ResultSet rs = st.executeQuery();//risultato della query
			
			while(rs.next()) {
				//per ogni riga creo un nuovo corso
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				Integer n = rs.getInt("tot");
				//questo corso lo vado ad aggiungere a result
				result.put(c,n);
			}
			//chiudo le connessioni
			rs.close();
			st.close();
		    conn.close();
		
	          }catch(SQLException e){
	        	  throw new RuntimeException(e);
		
	} 
		return result;
		}
	public List<Studente> getStudentiByCorso(Corso corso){
		String sql = "SELECT s.matricola, s.nome, s.cognome, s.CDS "
				+"FROM studente s, iscrizione i "
				+"WHERE s.matricola=i.matricola AND i.codins = ? ";
		List<Studente> result = new LinkedList<Studente>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1,corso.getCodins());
			ResultSet rs = st.executeQuery();
			while(rs.next()) {  
				//creo uno studente e a questo studente passo in ordine(del costruttore) i dati estratti dal DB
				Studente s = new Studente(rs.getInt("matricola"), rs.getString("nome"), rs.getString("cognome"), rs.getString("CDS"));
			result.add(s);
			}
			rs.close();
			st.close();
			conn.close();
			
		}catch(SQLException e){
			throw new RuntimeException(e);
			
		}
		return result;
	}
	
	
	public Map<String, Integer> getDivisioneStudenti(Corso corso){
		String sql = "SELECT s.CDS, COUNT(*) AS tot "
				+"FROM studente s, iscrizione i "
				+"WHERE s.matricola = i.matricola AND i.codins = ? AND s.CDS <> '' "
				+"GROUP BY s.CDS";
		
		Map<String,Integer> result = new HashMap<String,Integer>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				result.put(rs.getString("CDS"), rs.getInt("tot"));				
			}
			rs.close();
			st.close();
			conn.close();
			
		}catch(SQLException e){
			throw new RuntimeException(e);
			
		}return result;
	}
	
	public boolean esisteCorso(Corso corso) {
	   String sql = "SELECT * FROM corso WHERE codins = ?";
	   try {
		   Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1,corso.getCodins());
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				rs.close();
				st.close();
				conn.close();
				return true;
			}else {
				rs.close();
				st.close();
				conn.close();
				return false;
			}
	   }catch(SQLException e){
		   throw new RuntimeException(e);
	   }
			   
	}
	
}
