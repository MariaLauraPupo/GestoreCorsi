package it.polito.tdp.corsi.db;

import it.polito.tdp.corsi.model.Corso;

public class TestDAO {

	public static void main(String[] args) {
		CorsoDAO cdao = new CorsoDAO();
		System.out.println(cdao.getStudentiByCorso(new Corso("01KSUPG", null, null, null)));

	}

}
