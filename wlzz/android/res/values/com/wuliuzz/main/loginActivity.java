package com.wuliuzz.main;



import com.wuliuzz.sql.*;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class loginActivity extends Activity {
	TextView hins1 = null;
	Button ok = null;
	Button show = null;
	EditText context = null;
	EditText showcontext = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        hins1 = (TextView)findViewById(R.id.hins1);
        context = (EditText)findViewById(R.id.context);
        showcontext = (EditText)findViewById(R.id.showcontext);
        ok = (Button)findViewById(R.id.ok);
        show = (Button)findViewById(R.id.show);
        ok.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				String neirong = context.getText().toString();
				Role role = new Role();
				role.setName(neirong);
				RoleHelper roleHelper = DAOFactory.getRoleInstance(loginActivity.this);
				try {
					roleHelper.insert(roleHelper.getReadableDatabase(), role);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
        	
        });
        show.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Role role = new Role();
				RoleHelper roleHelper = DAOFactory.getRoleInstance(loginActivity.this);
				try {
					role = roleHelper.queryById(roleHelper.getReadableDatabase(), 3);
					System.out.println("already back");
					showcontext.setText("roleID: "+role.getId()+" roleName: "+role.getName());
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
        	
        });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    
}
