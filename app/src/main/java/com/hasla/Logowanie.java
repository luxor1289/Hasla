package com.hasla;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Logowanie extends Activity {
	
	private Button b1;
	private Button b2;
	private Button b3;
	private Button b4;
	private Button b5;
	private Button b6;
	private Button b7;
	private Button b8;
	private Button b9;
	private Button b0;
	private Button odblokuj;
	private Button wyjdz;
	private Button x;
	private TextView pin;
	private TextView pin_info;
	
	private Preferencje preferencje;
	private String pinn;

	
	/*
	 * nadpisanie metody onCreate
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logowanie);
        
        
        pin = (TextView) findViewById(R.id.logowanie_pin);
        pin_info = (TextView) findViewById(R.id.logowanie_tv1);
        
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        b5 = (Button) findViewById(R.id.button5);
        b6 = (Button) findViewById(R.id.button6);
        b7 = (Button) findViewById(R.id.button7);
        b8 = (Button) findViewById(R.id.button8);
        b9 = (Button) findViewById(R.id.button9);
        b0 = (Button) findViewById(R.id.button0);
        x = (Button) findViewById(R.id.button_x);
        odblokuj = (Button) findViewById(R.id.button_odblokuj);
        wyjdz = (Button) findViewById(R.id.button_wyjdz);
        
        preferencje = new Preferencje(Logowanie.this);
    	
    	if(!preferencje.hasloIstnieje()){
    		pin_info.setText("Utwórz PIN do włączania aplikacji");
    		odblokuj.setText("Utwórz");
    	}
    	
    	
        
        b1.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View v){
        		pin.append("1");
        	}
        });
        
        b2.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View v){
        		pin.append("2");
        	}
        });
        
        b3.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View v){
                pin.append("3");
        	}
        });
        
        b4.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View v){
                pin.append("4");
        	}
        });
        
        b5.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View v){
                pin.append("5");
        	}
        });
        
        b6.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View v){
                pin.append("6");
        	}
        });
        
        b7.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View v){
                pin.append("7");
        	}
        });
        
        b8.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View v){
                pin.append("8");
        	}
        });
        
        b9.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View v){
                pin.append("9");
        	}
        });
        
        x.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View v){
        		//kasowanie pinu w oknie logowania
        		pinn=pin.getText().toString();
        		if(pinn.length()>0) {
					pin.setText(pinn.substring(0, pinn.length() - 1));
				}
        	}
        });
        
        
        b0.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View v){
                pin.append("0");
        	}
        });
        
        wyjdz.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View v){
        		finish();
        	}
        });
        
        odblokuj.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View v){
        		if(!preferencje.hasloIstnieje()){
        			preferencje.utworzHaslo(pin.getText().toString());
                        Log.d("********* LOG *********", "Utworzono hasło ");
        			Intent intent2 = new Intent(Logowanie.this, Hasla.class);
            		startActivity(intent2);
            		finish();
            	}else{
            		if(preferencje.sprawdzPIN(pin.getText().toString())){
                        Log.d("********* LOG *********", "Sprawdzam PIN  " + pinn);
            		Intent intent = new Intent(Logowanie.this, Hasla.class);
            		startActivity(intent);
            		finish();
            		}else{
            			Toast.makeText(Logowanie.this, "Błędny PIN", Toast.LENGTH_SHORT).show();
            		}
            	}      		
        	}
        });             
        
    }
    /*
	 * koniec metody onCreate
	 */
    

    
    
}
