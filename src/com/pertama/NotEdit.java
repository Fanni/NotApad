package com.pertama;

import android.app.Activity;
//import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NotEdit extends Activity{
	
	private EditText mTitleText;
    private EditText mBodyText;
    private Long mRowId;
    private NotAdbAdapter mDbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbhelper = new NotAdbAdapter(this);
        mDbhelper.open();
        setContentView(R.layout.main_edit);
        setTitle(R.string.edit);

        mTitleText = (EditText) findViewById(R.id.title);
        mBodyText = (EditText) findViewById(R.id.body);

        Button confirmButton = (Button) findViewById(R.id.ok);

        /*mRowId = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString(NotAdbAdapter.KEY_TITLE);
            String body = extras.getString(NotAdbAdapter.KEY_BODY);
            mRowId = extras.getLong(NotAdbAdapter.KEY_ROWID);

            if (title != null) {
                mTitleText.setText(title);
            }
            if (body != null) {
                mBodyText.setText(body);
            }*/
        
        mRowId = (savedInstanceState == null) ? null :
            (Long) savedInstanceState.getSerializable(NotAdbAdapter.KEY_ROWID);
		if (mRowId == null) {
			Bundle extras = getIntent().getExtras();
			mRowId = extras != null ? extras.getLong(NotAdbAdapter.KEY_ROWID) : null;
		}

		populateFields();

        confirmButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }

        });
        /*confirmButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Bundle bundle = new Bundle();

                bundle.putString(NotAdbAdapter.KEY_TITLE, mTitleText.getText().toString());
                bundle.putString(NotAdbAdapter.KEY_BODY, mBodyText.getText().toString());
                if (mRowId != null) {
                    bundle.putLong(NotAdbAdapter.KEY_ROWID, mRowId);
                }

                Intent mIntent = new Intent();
                mIntent.putExtras(bundle);
                setResult(RESULT_OK, mIntent);
                finish();
            }

        });*/
    }
    
    private void populateFields() {
        if (mRowId != null) {
            Cursor note = mDbhelper.fetchNote(mRowId);
            startManagingCursor(note);
            mTitleText.setText(note.getString(
                    note.getColumnIndexOrThrow(NotAdbAdapter.KEY_TITLE)));
            mBodyText.setText(note.getString(
                    note.getColumnIndexOrThrow(NotAdbAdapter.KEY_BODY)));
        }
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putSerializable(NotAdbAdapter.KEY_ROWID, mRowId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }
    
    private void saveState() {
        String title = mTitleText.getText().toString();
        String body = mBodyText.getText().toString();

        if (mRowId == null) {
            long id = mDbhelper.createNote(title, body);
            if (id > 0) {
                mRowId = id;
            }
        } else {
            mDbhelper.updateNote(mRowId, title, body);
        }
    }
}
