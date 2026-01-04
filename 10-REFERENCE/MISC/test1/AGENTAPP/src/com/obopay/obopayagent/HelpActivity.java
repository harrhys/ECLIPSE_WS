package com.obopay.obopayagent;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HelpActivity extends Activity {
	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if(position == 0){
				Intent intent = new Intent(HelpActivity.this,ShowHelpActivity.class);
				intent.putExtra("page", "about.html");
				startActivity(intent);
			} else if(position == 1){
				Intent intent = new Intent(HelpActivity.this,ShowHelpActivity.class);
				intent.putExtra("page", "faq.html");
				startActivity(intent);
			} else if(position == 2){
				Intent intent = new Intent(HelpActivity.this,ShowHelpActivity.class);
				intent.putExtra("page", "eula.html");
				startActivity(intent);
			}
			
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		List<String>helpList = new ArrayList<String>();
		helpList.add("About");
		helpList.add("FAQs");
		helpList.add("EULA");
		
		ListView helpListView = (ListView) findViewById(R.id.list_view_help);
		helpListView.setAdapter(new ArrayAdapter<String>(HelpActivity.this, android.R.layout.simple_list_item_1, helpList));
		helpListView.setOnItemClickListener(listener);
	}
}
