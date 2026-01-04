package com.obopay.obopayagent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.obopay.obopayagent.EnterPinDialog.EnterPinDialogListener;
import com.obopay.obopayagent.domain.Transaction;
import com.obopay.obopayagent.restclient.Metadata;
import com.obopay.obopayagent.restclient.OboServices;
import com.obopay.obopayagent.restclient.OboServicesConstants;
import com.obopay.obopayagent.restclient.OboServicesListener;
import com.obopay.obopayagent.utils.PopUpUtil;
import com.obopay.obopayagent.utils.Utils;

public class ShowHistoryActivity extends FragmentActivity implements 
EnterPinDialogListener, OboServicesListener {

	private static final int MAX_PER_PAGE = 5;
	private int currentIndex = 1;
	private String pin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_history);
		final ListView historyListView = (ListView) findViewById(R.id.history_list_view);
		TransactionListAdapter transactionListAdapter = new TransactionListAdapter(this, R.layout.history_list_item_view,
				new ArrayList<Transaction>());
		historyListView.setAdapter(transactionListAdapter);
		historyListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(ShowHistoryActivity.this, ShowTransactionActivity.class);
				intent.putExtra(Utils.TRANSACTION, (Parcelable)historyListView.getAdapter().getItem(position));
				startActivity(intent);
			}
		});
		FragmentManager fm = getSupportFragmentManager();
		EnterPinDialog enterPinDialog = new EnterPinDialog() {

			@Override
			public void onCancel(DialogInterface dialog) {
				finish();
			}
			
		};
        enterPinDialog.show(fm, "fragment_enter_pin");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.help_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_help:
			Intent intent = new Intent(this,ShowHelpActivity.class);
			intent.putExtra("page", "help_history_list.html");
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onFinishPinDialog(String pin) {
		this.pin = pin;
		retrieveHistory();
	}
	
	private void retrieveHistory() {
		Hashtable params = new Hashtable();
		params.put(OboServicesConstants.DATA_FIELD_PIN, pin);
		params.put(OboServicesConstants.DATA_FIELD_SERIES_START, currentIndex);
		params.put(OboServicesConstants.DATA_FIELD_SERIES_END, currentIndex + MAX_PER_PAGE - 1);
		params.put(OboServicesConstants.DATA_FIELD_CLEAR_CACHE, currentIndex == 1);
		OboServices services = new OboServices(this, this);
		services.getHistory(params);
	}

	
	public void onOboServicesResult(int serviceType, Object result,
			OboServices svcObj) {
		if(serviceType == OboServicesConstants.METHOD_GET_HISTORY) {
			Log.d(Utils.LOG_TAG, result.toString());
			DateFormat dateFormat = new SimpleDateFormat("dd/MM");
			DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy hh:mmaaa");
			Vector vector = (Vector) result;
			List<Transaction> transactions = new ArrayList<Transaction>(vector.size());
			boolean paginationPanelInitialized = false;
			if(vector.isEmpty()) {
				TextView pagingText = (TextView) findViewById(R.id.paging_text);
				pagingText.setText(getString(R.string.no_transactions));
				findViewById(R.id.history_paging_panel).setVisibility(View.VISIBLE);
				return;
			}
			for(int i=0;i<vector.size();i++) {
				Hashtable table = (Hashtable) vector.get(i);
				Integer count = (Integer) OboServices.getParam(table, OboServicesConstants.DATA_FIELD_COUNT);
				if(!paginationPanelInitialized) {
					paginationPanelInitialized = true;
					findViewById(R.id.history_paging_panel).setVisibility(View.VISIBLE);
					Button prevButton = (Button) findViewById(R.id.previous_button);
					Button nextButton = (Button) findViewById(R.id.next_button);
					TextView pagingText = (TextView) findViewById(R.id.paging_text);
					prevButton.setEnabled(currentIndex > 1);
					nextButton.setEnabled(currentIndex + MAX_PER_PAGE <= count);
					pagingText.setText(getString(R.string.pagination_message, currentIndex, (currentIndex + vector.size() - 1), count));
					if(count <= MAX_PER_PAGE) {
						prevButton.setVisibility(View.GONE);
						nextButton.setVisibility(View.GONE);
					} else {
						prevButton.setVisibility(View.VISIBLE);
						nextButton.setVisibility(View.VISIBLE);
					}
					prevButton.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							if(currentIndex < 1) return;
							currentIndex = currentIndex - MAX_PER_PAGE;
							retrieveHistory();
						}
					});
					nextButton.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							currentIndex = currentIndex + MAX_PER_PAGE;
							retrieveHistory();
						}
					});
				}
				Integer seriesNumber = (Integer) OboServices.getParam(table, OboServicesConstants.DATA_FIELD_SERIES_NUMBER);
				String shortDescription = (String) OboServices.getParam(table, OboServicesConstants.DATA_FIELD_SHORT_DESC);
				String description = (String) OboServices.getParam(table, OboServicesConstants.DATA_FIELD_DESC);
				Vector vec = (Vector)OboServices.getParam(table, OboServicesConstants.DATA_FIELD_METADATA);
				Map<String, String> responseMetaData = new LinkedHashMap<String, String>();
				Transaction transaction = new Transaction();
				for (int j = 0; j < vec.size(); j++) {
					Metadata metadata = (Metadata)vec.elementAt(j);
					Log.d(Utils.LOG_TAG, metadata.label + ":" + metadata.value);
					responseMetaData.put(metadata.label, metadata.value.toString());
					if(metadata.label.equalsIgnoreCase("Date/Time")) {
						Date date = adjustForLocalTimeZone((Long) metadata.value);
						transaction.setDate(dateFormat.format(date));
						transaction.setDateTime(dateTimeFormat.format(date));
					}
				}
				transaction.setSeriesNumber(seriesNumber);
				transaction.setShortDescription(shortDescription);
				transaction.setDescription(description);
				transaction.setResponseMetadata(responseMetaData);
				transactions.add(transaction);
			}
			ListView historyListView = (ListView) findViewById(R.id.history_list_view);
			TransactionListAdapter adapter = (TransactionListAdapter) historyListView.getAdapter();
			adapter.clear();
			// adapter.addAll(transactions); // API not available till API 11
			for (Transaction transaction : transactions) {
				adapter.add(transaction);
			}
			adapter.notifyDataSetChanged();
		}
	}
	
	public Date adjustForLocalTimeZone(long utcMilliSeconds) {
		Date date = new Date(utcMilliSeconds);
		Calendar calendar = Calendar.getInstance();
		
		// find the local time zone (if available)
		TimeZone localZone = TimeZone.getDefault();
		String id = localZone.getID();
		if (localZone != null && !"GMT".equals(id) && !"UTC".equals(id))
		{
			// If local time zone is not GMT or UTC, then we convert the date to local time zone.
			// (Assume that the given date is in GMT).
			calendar.setTime(date);
			int millis = 
				calendar.get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000 + 
				calendar.get(Calendar.MINUTE) * 60 * 1000 +
				calendar.get(Calendar.SECOND) * 1000 + 
				calendar.get(Calendar.MILLISECOND);

			long offset = localZone.getOffset(
				1,	// AD  
				calendar.get(Calendar.YEAR), 
				calendar.get(Calendar.MONTH), 
				calendar.get(Calendar.DAY_OF_MONTH), 
				calendar.get(Calendar.DAY_OF_WEEK), 
				millis);
			date.setTime(date.getTime() + offset);
		}
		calendar.setTime(date);
		return calendar.getTime();
	}

	@Override
	public void onOboServicesError(int serviceType, Exception e,
			OboServices svcObj) {
		if(serviceType == OboServicesConstants.METHOD_GET_HISTORY) {
			PopUpUtil.error(this, getString(R.string.failed_to_get_history, e.getMessage()), new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});
		}
	}
	
	static class TransactionListAdapter extends ArrayAdapter<Transaction> {
		private int layoutResourceId;

		public TransactionListAdapter(Context context, int layoutResourceId, List<Transaction> objects) {
			super(context, layoutResourceId, objects);
			this.layoutResourceId = layoutResourceId;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View returnView = convertView;
			if(returnView == null) {
				LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
				returnView = inflater.inflate(layoutResourceId, parent, false);
			}
			TextView dateText = (TextView)returnView.findViewById(R.id.date_text);
			TextView descriptionText = (TextView)returnView.findViewById(R.id.description_text);
			TextView amountText = (TextView)returnView.findViewById(R.id.amount_text);
			Transaction transaction = getItem(position);
			dateText.setText(transaction.getDate());
			descriptionText.setText(transaction.getShortDescription());
			amountText.setText(transaction.getAmount());
			if(position % 2 == 0)
				returnView.setBackgroundColor(0x30AAAAAA);
	        else
	        	returnView.setBackgroundColor(0x30555555);
//			returnView.setOnClickListener(new View.OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					Drawable d = v.getBackground();
//		            PorterDuffColorFilter filter = new PorterDuffColorFilter(Color.GREEN, Mode.SRC_ATOP);
//		            d.setColorFilter(filter);
//				}
//			});
			return returnView;
		}
		
	}
}
