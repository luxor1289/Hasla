package com.hasla;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class Preferencje {
	
	private SharedPreferences sPreferences;

	/*
	 * KONSTRUKTOR
	 */
	public Preferencje(Context context){
		sPreferences = PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	
	/* 
	 * Sprawdzenie czy has�o do uruchamiania aplikacji istnieje
	 */
	public boolean hasloIstnieje(){
		boolean haslo_istnieje;
		haslo_istnieje = sPreferences.getBoolean("haslo_istnieje", false);
		return haslo_istnieje;
	}
	
	
	/*
	 * Utowrzenie has�a do w��czania aplikacji
	 */
	public void utworzHaslo(String pw){
		SharedPreferences.Editor editor = sPreferences.edit();
		editor.putBoolean("haslo_istnieje", true);
		editor.putString("pw", pw);
		editor.commit();
	}
	
	
	/*
	 * Sprawdzenie poprawno�ci PIN-u
	 */
    public boolean sprawdzPIN(String pin){
    	String pin_prawdziwy =  sPreferences.getString("pw", "");  	
    	if(pin.equals(pin_prawdziwy)){
			Log.d("********* LOG *********", "Pin poprawny");
    		return true;
    	}else{
			Log.d("********* LOG *********", "Pin bledny");
    		return false;
    	}
    }
	
	
}
