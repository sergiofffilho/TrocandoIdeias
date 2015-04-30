package ufc.quixada.trocandoideias;

import java.util.ArrayList;
import java.util.List;

import ufc.quixada.trocandoideias.bd.DatabaseAdapter;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class TelaLogin extends Activity {

	public String login;
	public String senha;
	
	EditText senhaText;
	EditText loginText;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);
                
        
        loginText = (EditText) findViewById(R.id.usernameCadastro);
        senhaText = (EditText) findViewById(R.id.senha);
        
        
                
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tela_login, menu);
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
    
    public void irParaCadastro(View view){
    	Intent i = new Intent(TelaLogin.this, Cadastro.class);
    	startActivity(i);
    }
    
    public void login(View view){
    	Intent i = new Intent(TelaLogin.this, AddProjetos.class);
    	
    	List<String> nomes = new ArrayList<String>();
    	
    	DatabaseAdapter dba = new DatabaseAdapter(getBaseContext());
    	dba.open();
    	
    	login = loginText.getText().toString();
    	senha = senhaText.getText().toString();
    	
    	Cursor cursor = dba.getUserByName(login);
    	
    	while (cursor.moveToNext()) {
    		nomes.add(cursor.getString(cursor.getColumnIndex(DatabaseAdapter.COL_NOME)));
    	}  	
    	
    	if(!(nomes.contains(login)))
    		Toast.makeText(this, "Usu�rio n�o cadastrado", Toast.LENGTH_SHORT).show();
    	else {
    		
    		String senhaBanco = dba.getSenha(nomes.get(0));
    		
    		if(senhaBanco.equals(senha)){
        		startActivity(i);
        	}
    		else
    			Toast.makeText(this,  "Senha Incorreta", Toast.LENGTH_SHORT).show();
    	}
    	
    	nomes.clear();
    	    	
    	dba.close();
    	
    }
    
    public void select(View view){
    	DatabaseAdapter dba = new DatabaseAdapter(getBaseContext());
    	dba.open();
    	
    	List<String> nomes = new ArrayList<String>();
    	
    	Cursor cursor = dba.getAllUsersNames();
    	
    	while (cursor.moveToNext()) {
    		nomes.add(cursor.getString(cursor.getColumnIndex(DatabaseAdapter.COL_NOME)));
    	}   	
    	
    	//login = nomes.get(0);
    	
    	dba.close();
    	
    	//TESTAR
    	for (String nome : nomes) {
    		Toast.makeText(this, nome, Toast.LENGTH_SHORT).show();
		}
    	
    }
}
