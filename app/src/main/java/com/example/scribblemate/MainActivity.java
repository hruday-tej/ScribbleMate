package com.example.scribblemate;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.example.scribblemate.database.NoteEntity;
import com.example.scribblemate.ui.NotesAdapter;
import com.example.scribblemate.viewmodel.MainViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @OnClick(R.id.fab)
    void fabClickHandler(){
        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
    }
//  making the instance of the view model to handle to coming data..
    private MainViewModel mViewModel;

    private List<NoteEntity> notesData = new ArrayList<>();
    NotesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
//        initiliaizing the view model class
        initViewModel();
        initRecyclerView();

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

//        here the notes are being loaded from view model class before we would be writing SampleData.getNotes() now it is written in the View Model class. So the intermediate thing
//        is being managed  by the view model

//        notesData.addAll(mViewModel.mNotes);
//        for (NoteEntity note: notesData){
//            Log.e("Plain Old Notes", note.toString());
//        }
    }

//  here we are initializing the view model class so that the MainViewModel would be pointing to this activity......and gets the data and binds it to the elements in the MainActivitry
    private void initViewModel() {
//        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        final Observer<List<NoteEntity>> notesObserver =
                new Observer<List<NoteEntity>>() {
                    @Override
                    public void onChanged(@Nullable List<NoteEntity> noteEntities) {
                        notesData.clear();
                        notesData.addAll(noteEntities);

                        if (mAdapter == null) {
                            mAdapter = new NotesAdapter(notesData,
                                    MainActivity.this);
                            mRecyclerView.setAdapter(mAdapter);
                        } else {
                            mAdapter.notifyDataSetChanged();
                        }

                    }
                };

        mViewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);
        mViewModel.mNotes.observe(this, notesObserver);
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NotesAdapter(notesData, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_sample_data) {
            addSampleData();
            return true;
        }
        else if(id == R.id.action_delete_all){
            deleteAllNotes();
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllNotes() {

        mViewModel.deleteAllNotes();

    }

    private void addSampleData() {
        mViewModel.addSampleData();
    }
}