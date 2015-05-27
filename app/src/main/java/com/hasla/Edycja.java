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
public class Edycja extends Activity {
	
	private Haslo haslo;
	private Haslo hasloEdytowane;
	private SQLiteHelper db;
	
	private EditText edNazwa;
	private EditText edLogin;
	private EditText edHaslo;
	private EditText edStrona;
	private EditText edOpis;
	private Button zapisz;
	
	
	
	/*
	 * Metoda onCreate
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edycja);
		
		//przypisanie przekazanego obiektu Haslo 
		Intent intent = getIntent();
		haslo = (Haslo) intent.getSerializableExtra("hasloEdycja");
		
		//powi�zanie obiekt�w z zasobami
		edNazwa = (EditText) findViewById(R.id.et_edycja_nazwa);
		edLogin = (EditText) findViewById(R.id.et_edycja_login);
		edHaslo = (EditText) findViewById(R.id.et_edycja_haslo);
		edStrona = (EditText) findViewById(R.id.et_edycja_strona);
		edOpis = (EditText) findViewById(R.id.et_edycja_opis);
		
		zapisz = (Button) findViewById(R.id.button_zapisz);
		// nas�uchiwacz buttona
		zapisz.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new EdytujHaslo().execute();
			}
		});
		
		//wype�nienie kontrolek danymi z obiektu Haslo
		wypelnijKontrolki(haslo);
	
	}
	/*
	 * Koniec onCreate
	 */

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edycja, menu);
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

	
	//metoda wype�niaj�ca kontrolki danymi
	public void wypelnijKontrolki(Haslo h){
		edNazwa.setText(h.pobierzNazwe());
		edLogin.setText(h.pobierzLogin());
		edHaslo.setText(h.pobierzHaslo());
		edStrona.setText(h.pobierzStrone());
		edOpis.setText(h.pobierzOpis());
	}
	
	
	//metoda pobieraj�ca dane do zmodyfikowanego
	//has�a 
	public Haslo pobierzDane(){
		Haslo h = new Haslo();
		h.ustawId(haslo.pobierzId());
		h.ustawNazwe(edNazwa.getText().toString());
		h.ustawLogin(edLogin.getText().toString());
		h.ustawHaslo(edHaslo.getText().toString());
		h.ustawStrone(edStrona.getText().toString());
		h.ustawOpis(edOpis.getText().toString());
		return h;
	}
	
	
	
	/*
	 * Klasa edytuj�ca has�o 
	 */
	private class EdytujHaslo extends AsyncTask<Void, Void, Void>{

		private ProgressDialog pDialog;
				
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(Edycja.this);
			pDialog.setMessage("Zapisywanie...");
	        pDialog.setIndeterminate(false);
	        pDialog.setCancelable(false);
	        pDialog.show();
	        hasloEdytowane = pobierzDane();
	        db = new SQLiteHelper(Edycja.this);
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method 
			db.edytujHaslo(hasloEdytowane);
			return null;
		}
		
		@Override
        protected void onPostExecute(Void result){
			pDialog.dismiss();
			Intent intentReturn = new Intent();
			intentReturn.putExtra("haslozmienione", hasloEdytowane);
			setResult(RESULT_OK,intentReturn);
			finish();
		}
	}/*
	 *Koniec klasy usuwaj�cej has�o
	 */
	
	
}
