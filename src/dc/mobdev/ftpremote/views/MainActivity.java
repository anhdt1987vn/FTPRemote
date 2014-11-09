package dc.mobdev.ftpremote.views;

import dc.mobdev.ftpremote.tasks.ListTask;
import dc.mobdev.ftpremote.configs.FTPConnection;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

	private Button btListRootFile, btLogin;
	private TextView textview1;
	private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); 
        
        this.init();
    }

    private void init() {
		FTPConnection.init(context);
		
		this.textview1 = (TextView) findViewById(R.id.tv1);
		this.btListRootFile = (Button) findViewById(R.id.btnListFile);
		this.btLogin = (Button) findViewById(R.id.btnLogin);

		final boolean status = FTPConnection.checkFileConfig("MyPreference.xml");

		if (status == false) {
			this.textview1.setText("Unavailable! Please login");
			this.btListRootFile.setVisibility(View.VISIBLE);
		}else{
			this.textview1.setText("Available");
			this.btLogin.setVisibility(View.GONE);
		}

		this.btListRootFile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {	
					new ListTask(context).execute("/");
				
			}
		});

		this.btLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, LoginActivity.class);
				startActivity(intent);

			}
		});
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
}
