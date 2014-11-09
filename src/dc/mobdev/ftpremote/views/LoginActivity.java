package dc.mobdev.ftpremote.views;

import dc.mobdev.ftpremote.configs.FTPConnection;
import dc.mobdev.ftpremote.tasks.ListTask;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends ActionBarActivity{

	private Button btConnectToServer;
	private EditText editTextHostname, editTextPort, editTextUsername,
	editTextPassword;
	private Context contex = this;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		this.init();
	}
	
	private void init(){
		this.editTextHostname = (EditText) findViewById(R.id.edittextHostName);
		this.editTextPort = (EditText) findViewById(R.id.edittextPort);
		this.editTextUsername = (EditText) findViewById(R.id.edittextUserName);
		this.editTextPassword = (EditText) findViewById(R.id.edittextPasword);
		this.btConnectToServer = (Button)findViewById(R.id.btnConnectToServer);
		
		this.btConnectToServer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String hostname = editTextHostname.getText().toString();
				String port = editTextPort.getText().toString();
				String username = editTextUsername.getText().toString();
				String password = editTextPassword.getText().toString();
				
				FTPConnection.init(contex);
							
				String[] keys = {"port", "hostname", "username", "password"};
				String[] values = {port, hostname, username, password};
				FTPConnection.writeToSharedPreferenceFile(keys, values);
				
				ListTask listTaks = new ListTask(contex);
				listTaks.execute("/");
				
			}
		});
	}
}
