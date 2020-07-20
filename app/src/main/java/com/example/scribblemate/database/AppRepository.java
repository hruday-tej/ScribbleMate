package com.example.scribblemate.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.scribblemate.utilities.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

//it is serving the data to the viewModel, before this the data from the sample data class was done in the viewmodel class but noe this job will be assigned
// ot AppRepository, now this will get to decide from where to retrieve the data and the data will be sent to the ViewModel, then this viewmodel is used
//by the MainActivity to bind the data to the UI components.

//this repository class is made singleton so that all the activities and fragments can share the same data between them.

//public class AppRepository {
//    public static AppRepository ourInstance ;
//
//    private AppDatabase mDb;
//
//    public LiveData<List<NoteEntity>> mNotes;
//
////    all room executions operations must be done in the background thread
//    private Executor executor = Executors.newSingleThreadExecutor();
//
//    public static AppRepository getInstance(Context context){
//        if (ourInstance == null){
//            ourInstance = new AppRepository(context);
//        }
//        return ourInstance;
//    }
////    here we store the data reference....
//    private AppRepository(Context context){
//        mNotes = SampleData.getNotes();
//        mDb = AppDatabase.getInstance(context);
//    }
//
//    public void addSampleData() {
//
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                mDb.noteDao().insertAll(SampleData.getNotes());
//            }
//        });
//
//    }
//
//    private LiveData<List<NoteEntity>> getAllNotes(){
//
//        return mDb.noteDao().getAll();
//
//    }
//}
public class AppRepository {
    private static AppRepository ourInstance;

    public LiveData<List<NoteEntity>> mNotes;
    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    private AppRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
        mNotes = getAllNotes();
    }

    public void addSampleData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.noteDao().insertAll(SampleData.getNotes());
            }
        });
    }

    private LiveData<List<NoteEntity>> getAllNotes() {
        return mDb.noteDao().getAll();
    }

    public void deleteAllNotes() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.noteDao().deleteAll();
            }
        });
    }

    public NoteEntity getNoteById(int noteId) {
        return  mDb.noteDao().getNoteById(noteId);
    }

    public void insertNote(NoteEntity note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.noteDao().insertNote(note);
            }
        });
    }

    public void deleteNote(NoteEntity note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.noteDao().deleteNote(note);
            }
        });
    }
}