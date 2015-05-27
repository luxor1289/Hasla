package com.hasla;

import android.os.Bundle;
import android.preference.PreferenceActivity;


public class Ustawienia extends PreferenceActivity {

	
	/*
	 * Nadpisanie onCreate
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getFragmentManager().beginTransaction().replace(android.R.id.content, new UstawieniaFragment()).commit();
		
	}/*
	 * Koniec onCreate
	 */
}
