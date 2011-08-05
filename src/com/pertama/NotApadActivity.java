package com.pertama;

//import android.app.Activity;
import com.pertama.NotEdit;
import com.pertama.NotAdbAdapter;
import com.pertama.R;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class NotApadActivity extends ListActivity {
	
	//private int notNumb = 1;
	private NotAdbAdapter dBhelper;
	public static final int LOGOUT = Menu.FIRST;
	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_EDIT = 1;
	private static final int DELETE_ID = Menu.FIRST + 1;
	//private Cursor mNotesCursor;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        setContentView(R.layout.layout_test);
        dBhelper = new NotAdbAdapter(this);
        dBhelper.open();
        fillData();
        registerForContextMenu(getListView());
        //dBhelper.close();
        
        final Button button = (Button) findViewById(R.id.addNoteButton);
        button.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
		    		createNote();
			}
		});
    }
    
    public boolean onCreateOptionsMenu(Menu menu){
    	super.onCreateOptionsMenu(menu);
    	//menu.add(0, LOGOUT, 0, R.string.logout);
    	menu.add(R.string.logout);
    	return true;
    }
    
    public void onMenuItemSelected(/*int featureId, MenuItem item,*/ View view){
    	//switch(item.getItemId()){
    	//case LOGOUT:
    		Intent myIntent = new Intent(view.getContext(), UserLogin.class);
    		startActivity(myIntent);
    	//}
    	//return super.onMenuItemSelected(featureId, item);
    }
    
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo info){
    	super.onCreateContextMenu(menu, view, info);
    	menu.add(0, DELETE_ID, 0, R.string.menu_delete);
    }
    
    public boolean onContextItemSelected(MenuItem item){
    	switch(item.getItemId()){
    	case DELETE_ID:
    		AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) item.getMenuInfo();
    		dBhelper.deleteNote(aInfo.id);
    		fillData();
    		return true;
    	}
    	return super.onContextItemSelected(item);
    }
    
    private void createNote(){
    	Intent i = new Intent(this, NotEdit.class);
    	/*String notName = "Note " + notNumb++;
    	dBhelper.createNote(notName, "");
    	fillData();*/
    	startActivityForResult(i, ACTIVITY_CREATE);
    }
    
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this, NotEdit.class);
        i.putExtra(NotAdbAdapter.KEY_ROWID, id);
        startActivityForResult(i, ACTIVITY_EDIT);
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }
    
    private void fillData(){
    	Cursor notesCursor = dBhelper.fetchAllNotes();
    	//String bodyNotes = dBhelper.fetchBody();
        startManagingCursor(notesCursor);
        String[] from = new String[]{ NotAdbAdapter.KEY_TITLE, NotAdbAdapter.KEY_BODY };
        int[] to = new int[]{R.id.text1, R.id.text2};
        
        SimpleCursorAdapter notes = 
            new SimpleCursorAdapter(this, R.layout.layout_testrow, notesCursor, from, to);
        setListAdapter(notes);
    }
}