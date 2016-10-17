package com.example.testmode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Button btnShow;
	private ListView listView;
	private List<Person> persons = new ArrayList<Person>();
	private MyAdapter mAdapter = new MyAdapter(persons);
	private List<Integer> listitemID = new ArrayList<Integer>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnShow = (Button) findViewById(R.id.btnshow);
		listView = (ListView) findViewById(R.id.list);

		initData();
		mAdapter = new MyAdapter(persons);
		listView.setAdapter(mAdapter);

		btnShow.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				listitemID.clear();
				for (int i = 0; i < mAdapter.mChecked.size(); i++) {
					if (mAdapter.mChecked.get(i)) {
						listitemID.add(i);
					}
				}

				if (listitemID.size() == 0) {
					AlertDialog.Builder builder1 = new AlertDialog.Builder(
							MainActivity.this);
					builder1.setMessage("没有选中任何记录");
					builder1.show();
				} else {
					StringBuilder sb = new StringBuilder();

					for (int i = 0; i < listitemID.size(); i++) {
						sb.append("ItemID=" + listitemID.get(i) + " . ");
					}
					AlertDialog.Builder builder2 = new AlertDialog.Builder(
							MainActivity.this);
					builder2.setMessage(sb.toString());
					builder2.show();
				}
			}
		});

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stu
				final int position = arg2;
				AlertDialog.Builder builder3 = new AlertDialog.Builder(
						MainActivity.this);
				builder3.setMessage("是否删除此项?");
				builder3.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						persons.remove(position);
						mAdapter.notifyDataSetChanged();
					}
				});
				builder3.setNegativeButton("取消", null);
				builder3.show();
				return false;
			}
		});
	}

	private void initData() {
		Person mPerson = new Person();
		mPerson.setName("Andy");
		mPerson.setAddress("GuangZhou");
		persons.add(mPerson);
		Person mPerson1 = new Person();
		mPerson1.setName("Jdy");
		mPerson1.setAddress("ShangHai");
		persons.add(mPerson1);
	}

	class MyAdapter extends BaseAdapter {
		List<Boolean> mChecked;
		List<Person> listPerson;
		HashMap<Integer, View> map = new HashMap<Integer, View>();

		public MyAdapter(List<Person> list) {
			listPerson = new ArrayList<Person>();
			listPerson = list;

			mChecked = new ArrayList<Boolean>();
			for (int i = 0; i < list.size(); i++) {
				mChecked.add(false);
			}
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listPerson.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return listPerson.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (null == arg1) {
				arg1 = getLayoutInflater().inflate(R.layout.listitem, arg2,
						false);
				holder = new ViewHolder(arg1);

				final int p = arg0;
				map.put(arg0, arg1);
				holder.getBox().setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						CheckBox cb = (CheckBox) arg0;
						mChecked.set(p, cb.isChecked());
					}
				});
			} else {
				Log.e("MainActivity", "position2 = " + arg0);
				//arg1 = map.get(arg0);
				holder = (ViewHolder) arg1.getTag();
			}
			holder.getName().setText(listPerson.get(arg0).getName());
			holder.getAddress().setText(listPerson.get(arg0).getAddress());
			holder.getBox().setChecked(mChecked.get(arg0));
			return arg1;
		}

	}

	static class ViewHolder {
		private CheckBox selected;
		private TextView name;
		private TextView address;
		View view;

		public ViewHolder(View view) {
			this.view = view;
			if (selected == null) {
				selected = (CheckBox) view.findViewById(R.id.checkBox1);
			}
			if (name == null) {
				name = (TextView) view.findViewById(R.id.textView1);
			}
			if (address == null) {
				address = (TextView) view.findViewById(R.id.textView2);
			}
			view.setTag(this);
		}

		public CheckBox getBox() {
			return selected;
		}

		public TextView getName() {
			return name;
		}

		public TextView getAddress() {
			return address;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
