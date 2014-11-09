package dc.mobdev.ftpremote.views;

import dc.mobdev.ftpremote.tasks.DeleteTask;
import dc.mobdev.ftpremote.tasks.DownloadTask;
import dc.mobdev.ftpremote.tasks.ListTask;
import dc.mobdev.ftpremote.tasks.UploadTask;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListFileActivity extends ActionBarActivity{

	private ListView lvRoot;
	private Button btDelete, btUpload, btDownload;
	private Context context = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listfile);
		this.init();
	}
	
	private void init() {
		System.out.println("ListFile is started successful!");
		this.lvRoot = (ListView) findViewById(R.id.lvFiles);
		this.btDelete = (Button) findViewById(R.id.btnDelete);
		this.btUpload = (Button) findViewById(R.id.btnUpload);
		this.btDownload = (Button) findViewById(R.id.btnDownload);
		
		final String[] names = this.getIntent().getExtras()
				.getStringArray("names");
		final int[] types = this.getIntent().getExtras().getIntArray("types");

		this.lvRoot.setChoiceMode(ListView.CHOICE_MODE_SINGLE);	
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, names);
		this.lvRoot.setAdapter(adapter);
		
		final String currentDir = this.getIntent().getExtras().getString("currentDir");
		
		
		this.lvRoot.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String fileName = lvRoot.getItemAtPosition(position).toString();
				Toast.makeText(context, fileName + "_" + position, Toast.LENGTH_SHORT).show();
				
				if((names[position] == fileName) && (types[position] == 1)){
					System.out.println("CurrentDir: " + currentDir);
					String path = currentDir + "/" +fileName;
					System.out.println("PATH: " + path);
					new ListTask(context).execute(path);
				}	
				
			}
		});
		
		this.btDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String itemToDelete = "";
				for (int i = 0; i < lvRoot.getCount() ; i++) {
					if (lvRoot.isItemChecked(i)) {
						itemToDelete = lvRoot.getItemAtPosition(i).toString();
					}
				}
				String fileToDelete = currentDir + "/" + itemToDelete;
				new DeleteTask(context).execute(fileToDelete);
				new ListTask(context).execute(currentDir);
				Toast.makeText(context, "Delete Sucessful !", Toast.LENGTH_SHORT).show();
			}
		});
		
		this.btUpload.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new UploadTask(context).execute("Download/person.xml", currentDir);
				Toast.makeText(context, "Upload Sucessful !", Toast.LENGTH_SHORT).show();
				new ListTask(context).execute(currentDir);
			}
		});
		
		this.btDownload.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				String itemForDownload = "";
				for(int i = 0; i < lvRoot.getCount(); i++){
					if(lvRoot.isItemChecked(i)){
						itemForDownload = lvRoot.getItemAtPosition(i).toString();
					}
				}
				System.out.println("#ListFileActivity_CurrentDir: " + currentDir);
				new DownloadTask(context).execute(currentDir ,itemForDownload, "Download");
			}
		});
	}
}
