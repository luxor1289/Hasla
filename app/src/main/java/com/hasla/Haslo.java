package com.hasla;

import java.io.Serializable;

public class Haslo implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private int id;
	private String nazwa;
	private String haslo;
	private String strona;
	private String opis;
	private String login;
	
	/*
	 * PRZECI��ONE KONSTRUKTORY
	 */
	public Haslo(){
	}
	
	public Haslo(String nazwa, String haslo, String strona, String opis, String login){
		this.nazwa = nazwa;
		this.haslo = haslo;
		this.strona = strona;
		this.opis = opis;
		this.login = login;
	}
	
	
	/*
	 * Przypisanie warto�ci
	 */
	public void ustawId(int id){
		this.id = id;
	}
	
	public void ustawNazwe(String n){
		this.nazwa = n;
	}
	
	public void ustawHaslo(String h){
		this.haslo = h;
	}
	
	public void ustawStrone(String s){
		this.strona = s;
	}
	
	public void ustawOpis(String o){
		this.opis = o;
	}
	
	public void ustawLogin(String l){
		this.login = l;
	}
	
	
	/*
	 * Pobieranie wartosci
	 */
	public int pobierzId(){
		return this.id;
	}
	
	public String pobierzNazwe(){
		return this.nazwa;
	}
	
	public String pobierzHaslo(){
		return this.haslo;
	}
	
	public String pobierzStrone(){
		return this.strona;
	}
	
	public String pobierzOpis(){
		return this.opis;
	}
	
	public String pobierzLogin(){
		return this.login;
	}
	
	@Override
	public String toString(){
		String i = Integer.toString(this.id);
		return "Haslo [id = "+i+", nazwa = "+nazwa+", haslo = "+haslo+", strona = "+strona+", opis = "+opis+", login: "+login+" ]";
	}

}
