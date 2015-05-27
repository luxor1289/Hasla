package com.hasla;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ArrayAdapterHasla extends ArrayAdapter<Haslo>{
	
	private int resource;
	
	/*
	 * KONSTRUKTOR
	 */
	public ArrayAdapterHasla(Context context, int textViewResourceId, List<Haslo> objekty){
		super(context, textViewResourceId, objekty);
		resource = textViewResourceId;		
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		RelativeLayout haslaView;
		Haslo haslo = getItem(position);
		
		String nazwa = haslo.pobierzNazwe();
		String strona = haslo.pobierzStrone();
		String login = haslo.pobierzLogin();
		
		if(convertView == null){
			haslaView = new RelativeLayout(getContext());
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			inflater.inflate(resource, haslaView, true);			
		}else{
			haslaView = (RelativeLayout) convertView;
		}
		
		TextView tvNazwa = (TextView) haslaView.findViewById(R.id.textViewNazwa);
		TextView tvStrona = (TextView) haslaView.findViewById(R.id.textViewStrona);
		TextView tvLogin = (TextView) haslaView.findViewById(R.id.textViewLogin);
		
		tvNazwa.setText(nazwa);
		tvStrona.setText(strona);
		tvLogin.setText(login);
		
		return haslaView;
		
	}
}
