package com.example.hellofacebook;

import java.util.Arrays;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FacebookLoginSample extends Activity implements OnClickListener{
			
	 	Button btnLogin;
	 	TextView txtName;
	 	CallbackManager callbackManager;
	 	private boolean isLogin =false;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_fb_login);
			
		      btnLogin = (Button)findViewById(R.id.btnLogin);
		      txtName  = (TextView)findViewById(R.id.txtName);
		      btnLogin.setOnClickListener(this);
		      
		      FacebookSdk.sdkInitialize(getApplicationContext());
		      callbackManager = CallbackManager.Factory.create();
			  
		      LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
				
				@Override
				public void onSuccess(LoginResult result) {
					updateUI();
				}
				
				@Override
				public void onError(FacebookException error) {
					updateUI();
				}
				
				@Override
				public void onCancel() {
					
				}
			});
		      
			
			
		}
		
		
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		    super.onActivityResult(requestCode, resultCode, data);
		    callbackManager.onActivityResult(requestCode, resultCode, data);
		}


		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnLogin:
					if(!isLogin()){
						LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends","email"));
					}else {
						LoginManager.getInstance().logOut();
						updateUI();
					}
				break;
			default:
				break;
			}
		}
		
	    private void updateUI() {
	        
	    	boolean enableButtons = AccessToken.getCurrentAccessToken() != null;
	        Profile profile = Profile.getCurrentProfile();
	        if (enableButtons && profile != null) {
	        	btnLogin.setText("Logout");
	        	setLogin(!isLogin());
	            txtName.setText((getString(R.string.hello_user, profile.getFirstName())));
	        }else {
	        	btnLogin.setText("Login");
	        	setLogin(!isLogin());
	        	txtName.setText("Name");
	        }
	    }
		public boolean isLogin() {
			return isLogin;
		}

		public void setLogin(boolean isLogin) {
			this.isLogin = isLogin;
		}
		
}
