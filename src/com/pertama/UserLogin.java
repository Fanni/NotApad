package com.pertama;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//import android.app.Service;

public class UserLogin extends Activity{
	
	private EditText etUsername;
	private EditText etPass;
	private Button bLogin;
	private TextView tResult;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_login);
		
		etUsername = (EditText) findViewById(R.id.tUsername);
		etPass = (EditText) findViewById(R.id.tPassword);
		bLogin = (Button) findViewById(R.id.btnLogin);
		tResult = (TextView) findViewById(R.id.t3);
		
		bLogin.setOnClickListener(new OnClickListener(){

			public void onClick(View viw) {
				// TODO Auto-generated method stub
				String username = etUsername.getText().toString();
				String password = etPass.getText().toString();
				
				if( username.equals("Fanni") && password.equals("rahasia")){
					Intent myIntent = new Intent(viw.getContext(),NotApadActivity.class);
					  startActivityForResult(myIntent, 0);
				}else{
					tResult.setText("Login failed, please try again.");
				}
			}
		});
	}
}
