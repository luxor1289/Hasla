package com.hasla;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class Szczegoly extends Activity {

	private Haslo haslo;
	private TextView nazwa;
	private TextView login;
	private TextView hasloTV;
	private TextView strona;
	private TextView opis;
	private Button buton;
	private ImageButton buttonPokaz;
	
	private int edytowano=0;
	private int image_flag =0;
	
	SQLiteHelper db;
	
	
	/*
	 *   OnCreate
	 *  ********************************************************************
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_szczegoly);
		
		// powi�zanie obiekt�w z zasobami
		nazwa = (TextView) findViewById(R.id.tv_szczegoly_nazwa);
		login = (TextView) findViewById(R.id.tv_szczegoly_login);
		hasloTV = (TextView) findViewById(R.id.tv_szczegoly_haslo);
		strona = (TextView) findViewById(R.id.tv_szczegoly_strona);
		opis = (TextView) findViewById(R.id.tv_szczegoly_opis);
		buton = (Button) findViewById(R.id.button_szcegoly);
		buttonPokaz = (ImageButton) findViewById(R.id.button_pokaz);
		
		// nas�uchiwacz buttona Wstecz
		buton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(edytowano==1){
					Intent intentReturn = new Intent();
					setResult(5,intentReturn);  //5 = EDIT_OK
					finish();}
				else{
					finish();
		    }}
		});
		
		// nas�uchiwacz buttona Pokaz
		buttonPokaz.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
					if(image_flag == 0){
						hasloTV.setText(haslo.pobierzHaslo());
						buttonPokaz.setImageResource(R.drawable.padlock_close);
						image_flag=1;
					}else{
						hasloTV.setText(zamienHaslo(hasloTV.getText().toString()));
						buttonPokaz.setImageResource(R.drawable.padlock_open);
						image_flag=0;
					}
			}
		});
					
		// pobranie obiektu Haslo z aktywnosci Hasla
		// wypelnienie widowk�w danymi
		Intent intent = getIntent();
		haslo = (Haslo) intent.getSerializableExtra("haslo");
		wypelnijDane(haslo);
	}
	/*
	 *  Koniec OnCreate
	 *  **********************************************************************
	 */

	
	
	 //
	 // Nadpisanie przycisku Back
	 // sprawdzenie czy nast�pi�a edycja has�a
	 //
	 public boolean onKeyDown(int keycode, KeyEvent event) {
	     if (keycode == KeyEvent.KEYCODE_BACK) {
	    	 if(edytowano==1){
					Intent intentReturn = new Intent();
					setResult(5,intentReturn);  //5 = EDIT_OK
					finish();}
				else{
					finish();
				}
	     }
	     return super.onKeyDown(keycode, event);
	 }
	
	
	//
	// Utworzenie menu
	//
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.szczegoly, menu);
		return true;
	}
	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		//opcja w menu ustawienia
		if (id == R.id.action_settings2) {
			Intent intent = new Intent(Szczegoly.this, Ustawienia.class);
			startActivity(intent);		
			
		//opcja w menu edytuj
		} else if (id == R.id.action_edit){
			Intent intentEdycja = new Intent(Szczegoly.this, Edycja.class);
			intentEdycja.putExtra("hasloEdycja",haslo);
			startActivityForResult(intentEdycja, 3);	
			
		//opcja w menu usun
		} else if (id == R.id.action_delete) {	
			
			//
			//potwierdzenie usuni�cia rekordu
			//
			AlertDialog alertDialog = new AlertDialog.Builder(Szczegoly.this).create();
			alertDialog.setMessage("Czy napewno usunąć?");
			alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Usuń", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					new UsunHaslo().execute();	
				}
			});
			alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Anuluj", new DialogInterface.OnClickListener() {			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub			
				}
			});
			alertDialog.show();
			    
		}
		return super.onOptionsItemSelected(item);
	}

	
			
	//
	// OBs�uga wynik�w aktywno�ci, w tym przypadku
	// edycja rekordu
	//
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		
		//pobranie zedytowanego obiektu Haslo
		if(requestCode == 3){
			if(resultCode == RESULT_OK){
				//Haslo hasloZmienione = (Haslo) data.getSerializableExtra("haslozmienione");
				haslo = (Haslo) data.getSerializableExtra("haslozmienione");
				Toast.makeText(getApplicationContext(), "Zapisano zmiany", Toast.LENGTH_SHORT).show();
				wypelnijDane(haslo);
				edytowano=1;
				}	
			}	
	}

		
	
	
	//
	// Wype�nienie TextViews danymi
	//
	public void wypelnijDane(Haslo h){
		nazwa.setText(h.pobierzNazwe());	
		login.setText(h.pobierzLogin());
		hasloTV.setText(zamienHaslo(h.pobierzHaslo()));
		strona.setText(h.pobierzStrone());
		opis.setText(h.pobierzOpis());
	}
	
	
	
	//
	// Zamiana has�a na gwiazdki
	//
	public String zamienHaslo(String pw){
		String gwiazdki = "";
		for(int i=0; i<pw.length(); i++){
			gwiazdki = gwiazdki.concat("*");
		}		
		return gwiazdki;
	}
	
	
	
	
	/*
	 * Klasa usuwaj�ca has�o 
	 * ----------------------------------------------------------------
	 */
	private class UsunHaslo extends AsyncTask<Void, Void, Void>{

		private ProgressDialog pDialog;
				
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(Szczegoly.this);
			pDialog.setMessage("Usuwanie...");
	        pDialog.setIndeterminate(false);
	        pDialog.setCancelable(false);
	        pDialog.show();
	        db = new SQLiteHelper(Szczegoly.this);
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method 
			db.usunHaslo(haslo.pobierzId());
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
	 *---------------------------------------------------------------
	 */
	

}
