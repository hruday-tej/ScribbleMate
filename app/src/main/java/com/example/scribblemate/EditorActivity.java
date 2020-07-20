package com.example.scribblemate;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.example.scribblemate.database.NoteEntity;
import com.example.scribblemate.viewmodel.EditorViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.scribblemate.utilities.Constants.NOTE_ID_KEY;

public class EditorActivity extends AppCompatActivity {

    private EditorViewModel mViewModel;

    @BindView(R.id.note_text)
    TextView mTextView;
    CollapsingToolbarLayout toolBarLayout;
    private boolean mNewNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.check_note);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
//                EditorActivity.super.onBackPressed();
//                Toast.makeText(EditorActivity.this,"Dani Daniels", Toast.LENGTH_LONG).show();
                saveAndReturn();
            }
        });

        ButterKnife.bind(this);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        initViewModel();
    }

    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    private void saveAndReturn() {
        mViewModel.saveNote(mTextView.getText().toString());
        finish();
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(EditorViewModel.class);

//        setting up observer
        mViewModel.mLiveNote.observe(this, new Observer<NoteEntity>() {
            @Override
            public void onChanged(NoteEntity noteEntity) {
//                it will be called automatically when the data is changed/posted

                mTextView.setText(noteEntity.getText());
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            toolBarLayout.setTitle("New Note");
            mNewNote = true;
        }
        else{
            toolBarLayout.setTitle("Edit Note");
            int noteId = extras.getInt(NOTE_ID_KEY);
            mViewModel.loadData(noteId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!mNewNote){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_editor, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_delete){
//            no need to send the note object because the view model already knows on which note we are working right now.
            mViewModel.deleteNote();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}