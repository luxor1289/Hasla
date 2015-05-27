package com.hasla;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;



public class Hasla extends Activity {
	
	private ListView lista;
	SQLiteHelper db;
	ArrayAdapterHasla haslaAdapter;
	List<Haslo> hasla;
	
	

	/*
	 * Nadpisanie onCreate
	 * ********************************************************
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hasla);
		
		lista = (ListView) findViewById(R.id.listViewHasla);	
		db = new SQLiteHelper(this);
		
		new WypelnijListe().execute();

	}/*
	 * Koniec onCreate
	 * *********************************************************
	 */
	
	
	
	//
	// Utworzenie Menu
	//
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.hasla, menu);
		return true;
	}

	
	
	//
	// Dodanie item�w do Menu
	//
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		//itemy Menu
		if (id == R.id.add){
			Intent intentDodaj = new Intent(Hasla.this, DodajHaslo.class);
			startActivityForResult(intentDodaj,2);		
		}
		else if (id == R.id.settings) {
			Intent intent = new Intent(Hasla.this, Ustawienia.class);
			startActivity(intent);
		}else if (id == R.id.exit){
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	//
	// Sprawdzenie warto�ci zwr�conych przez aktywno�ci
	// od�wie�enie listy hase�
	// 
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == 1){
			if(resultCode == RESULT_OK){
				new WypelnijListe().execute();
				Toast.makeText(getApplicationContext(), "Usunięto", Toast.LENGTH_SHORT).show();
			} else if(resultCode == 5){ //5 = EDIT_OK
				new WypelnijListe().execute();
			} 
			
		}else if(requestCode == 2){
			if(resultCode == RESULT_OK){
				new WypelnijListe().execute();
				Toast.makeText(getApplicationContext(), "Dodano", Toast.LENGTH_SHORT).show();
			}
		}
	}



	/*
	 * Klasa wype�niaj�ca list� 
	 * --------------------------------------------------------------------------------------------------
	 */
	class WypelnijListe extends AsyncTask<Void, Void, Void>{

		private ProgressDialog pDialog;
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(Hasla.this);
			pDialog.setMessage("Ładowanie...");
	        pDialog.setIndeterminate(false);
	        pDialog.setCancelable(false);
	        pDialog.show();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method 
			hasla = db.pobierzHasla();
			return null;
		}
		
		@Override
        protected void onPostExecute(Void result){
			pDialog.dismiss();
			haslaAdapter = new ArrayAdapterHasla(Hasla.this, R.layout.lista_hasel_item, hasla);
			lista.setAdapter(haslaAdapter);
			 
			//nas�uchiwacz klikniecia Itemu
			lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	    		  @Override
				  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	    			  	
						// Utworzenie aktywnosci Szczegoly i przekazanie obiektu Haslo
		    			Haslo h = (Haslo) parent.getItemAtPosition(position);
		    			Intent intentSzczegoly = new Intent(Hasla.this, Szczegoly.class);
		    			intentSzczegoly.putExtra("haslo",h);
		    			startActivityForResult(intentSzczegoly,1);
	    		  }
	         });
		}
	}/*
	 *Koniec klasy WypelnijListe
	 *--------------------------------------------------------------------------------------------------------
	 */
	
}
