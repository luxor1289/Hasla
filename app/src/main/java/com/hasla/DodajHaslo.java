package com.hasla;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class DodajHaslo extends Activity {
	
	SQLiteHelper db;
	Haslo haslo;
	Button dodajButon;
	
	EditText nazwa;
	EditText login;
	EditText hasloET;
	EditText strona;
	EditText opis;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dodaj_haslo);
		
		nazwa = (EditText) findViewById(R.id.et_dodaj_nazwa);
		login = (EditText) findViewById(R.id.et_dodaj_login);
		hasloET = (EditText) findViewById(R.id.et_dodaj_haslo);
		strona = (EditText) findViewById(R.id.et_dodaj_strona);
		opis = (EditText) findViewById(R.id.et_dodaj_opis);
		dodajButon = (Button) findViewById(R.id.button_dodaj);
		
		dodajButon.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DodajHasloDoBazy().execute();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dodaj_haslo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	
	/*
	 * Klasa dodajaca has�o 
	 */
	private class DodajHasloDoBazy extends AsyncTask<Void, Void, Void>{

		private ProgressDialog pDialog;
				
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(DodajHaslo.this);
			pDialog.setMessage("Dodawanie do bazy...");
	        pDialog.setIndeterminate(false);
	        pDialog.setCancelable(false);
	        pDialog.show();
	        haslo = pobierzDane();
	        db = new SQLiteHelper(DodajHaslo.this);
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method 
			db.dodajHaslo(haslo);
			return null;
		}
		
		@Override
        protected void onPostExecute(Void result){
			pDialog.dismiss();
			Intent intentReturn = new Intent();
			setResult(RESULT_OK,intentReturn);
			finish();
		}
	}/*
	 *Koniec klasy usuwaj�cej has�o
	 */

	public Haslo pobierzDane(){
		Haslo h = new Haslo();
		h.ustawNazwe(nazwa.getText().toString());
		h.ustawLogin(login.getText().toString());
		h.ustawHaslo(hasloET.getText().toString());
		h.ustawStrone(strona.getText().toString());
		h.ustawOpis(opis.getText().toString());
		return h;
	}

}
