package tk.sweetvvck.actionbarpulltorefreshsample;

import java.util.ArrayList;
import java.util.List;

import tk.sweetvvck.actionbarpulltorefresh.PullToRefreshLayout;
import tk.sweetvvck.actionbarpulltorefresh.PullToRefreshLayout.OnRefreshListener;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnRefreshListener{

	private PullToRefreshLayout mPullToRefreshLayout;
	private ListView mListView;
	private FakeAdapter mAdapter;
	private List<String> mData = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();
		mListView = (ListView) findViewById(R.id.list);
		mAdapter = new FakeAdapter(this, mData);
		mPullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.pull_to_refresh_layout);
		mPullToRefreshLayout.setOnRefreshListener(this);
		mListView.setAdapter(mAdapter);
	}

	private void initData() {
		
		for (int i = 0; i < 10; i++) {
			mData.add(i + "Item");
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onRefresh() {
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				initData();
				mAdapter.notifyDataSetChanged();
				mPullToRefreshLayout.onSyncFinished();
			}
		}, 3000);
	}
	
	static class FakeAdapter extends BaseAdapter{
		
		private List<String> mItems;
		private Context mContext;
		
		public FakeAdapter(Context context, List<String> items){
			this.mItems = items;
			this.mContext = context;
		}

		@Override
		public int getCount() {
			return mItems.size();
		}

		@Override
		public String getItem(int position) {
			return mItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			convertView = new TextView(mContext);
			
			((TextView)convertView).setText(getItem(position));
			
			return convertView;
		}
		
	}

}
