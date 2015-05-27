package com.hasla;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;


public class SQLiteHelper extends SQLiteOpenHelper{

	//Nazwa tabeli
	private static final String TABELA_HASLA = "hasla";

	//Nazwy kolumn tabeli hasla
	private static final String KOLUMNA_ID = "id";
	private static final String KOLUMNA_NAZWA = "nazwa";
	private static final String KOLUMNA_HASLO = "haslo";
	private static final String KOLUMNA_STRONA = "strona";
	private static final String KOLUMNA_OPIS = "opis";
	private static final String KOLUMNA_LOGIN = "login";



	//Wersja bazy danych
	private static final int DATABASE_VERSION = 1;
	
	//Nazwa bazy danych
	private static final String DATABASE_NAME = "hasla.db";

	public SQLiteHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		SQLiteDatabase.loadLibs(context);
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// Zapytanie tworz�ce tabele hasla
		String CREATE_TABLE_HASLA = "CREATE TABLE hasla("+
									"id INTEGER PRIMARY KEY AUTOINCREMENT, "+
									"nazwa TEXT, "+
									"strona TEXT, " +
									"haslo TEXT, "+
									"opis TEXT, "+
									"login TEXT )";
		
		// Wykonanie zapytania 
		db.execSQL(CREATE_TABLE_HASLA);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO usuniecie tabeli hasla jesli istnieje
		db.execSQL("DROP TABLE IF EXISTS hasla");
		
		//utworzenie swiezej tabeli hasla
		this.onCreate(db);	
	}
	
	//--------------------------------------------------------------------
	/*
	 * Operacje na danych w bazie 
	 */
	

	
	/*
	 * Dodanie has�a do bazy
	 */
	public void dodajHaslo(Haslo haslo){
		Log.e("dodajHaslo", haslo.toString());
		
		//1. pobranie referencji do bazy danych z podanie has�a
		SQLiteDatabase db = getWritableDatabase("12345");
		
		//2. utworzenie obiektu ContentValues 
		ContentValues wartosci = new ContentValues();
		wartosci.put(KOLUMNA_NAZWA, haslo.pobierzNazwe());
		wartosci.put(KOLUMNA_HASLO, haslo.pobierzHaslo());
		wartosci.put(KOLUMNA_STRONA, haslo.pobierzStrone());
		wartosci.put(KOLUMNA_OPIS, haslo.pobierzOpis());
		wartosci.put(KOLUMNA_LOGIN, haslo.pobierzLogin());
		
		//3. insert
		db.insert(TABELA_HASLA, null, wartosci);
		
		db.close();	
		Log.e("dodajHaslo", "DODANO");
	}
	
	/*
	 * Usni�cie z bazy
	 */
	public void usunHaslo(int id){
		String i = Integer.toString(id);
		Log.e("usunHaslo","Usuwania hasla o ID: "+i);
		
		//1. pobranie referencji do bazy danych z podanie has�a
		SQLiteDatabase db = getWritableDatabase("12345");
		
		db.delete(TABELA_HASLA, KOLUMNA_ID+"=?",new String[] {String.valueOf(id) });
		db.close();
	}
	
	
	
	/*
	 * Edycja has�a
	 */
	public void edytujHaslo(Haslo h){
		//1. pobranie referencji do bazy danych z podanie has�a
		SQLiteDatabase db = getWritableDatabase("12345");
		
		//2. utworzenie obiektu ContentValues 
		ContentValues wartosci = new ContentValues();
		wartosci.put(KOLUMNA_NAZWA, h.pobierzNazwe());
		wartosci.put(KOLUMNA_HASLO, h.pobierzHaslo());
		wartosci.put(KOLUMNA_STRONA, h.pobierzStrone());
		wartosci.put(KOLUMNA_OPIS, h.pobierzOpis());
		wartosci.put(KOLUMNA_LOGIN, h.pobierzLogin());
		
		//3.update
		db.update(TABELA_HASLA, wartosci, KOLUMNA_ID + "=" + h.pobierzId(), null);
		db.close();
	}
	
	
	
	
	/*
	 * Pobranie listy obiektow Haslo z bazy
	 */
	public List<Haslo> pobierzHasla(){
		List<Haslo> listaHasel = new ArrayList<Haslo>();
		
		//1. Zapytanie do bazy
		String zapytanie = "SELECT id, nazwa, haslo, strona, opis, login FROM "+TABELA_HASLA+" ORDER BY LOWER(nazwa);";
		
		//2. Referencja do bazy
		SQLiteDatabase db = getWritableDatabase("12345");
		Cursor cursor = db.rawQuery(zapytanie, null);
		
		//3. Pobranie ka�dego rekorku i utworzenie z niego obiektu Haslo
		Haslo haslo = null;
		if(cursor.moveToFirst()){
			do{
				haslo = new Haslo();
				haslo.ustawId(cursor.getInt(0));
				haslo.ustawNazwe(cursor.getString(1));
				haslo.ustawHaslo(cursor.getString(2));
				haslo.ustawStrone(cursor.getString(3));
				haslo.ustawOpis(cursor.getString(4));
				haslo.ustawLogin(cursor.getString(5));
				
				//dodanie do listy
				listaHasel.add(haslo);
				Log.d("********* LOG *********","Pobrano z bazy");
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();
		//zwrocenie listy Hasel
		return listaHasel;
	}
	
	

}
