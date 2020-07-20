package com.example.scribblemate.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.scribblemate.database.AppRepository;
import com.example.scribblemate.database.NoteEntity;
import com.example.scribblemate.utilities.SampleData;

import java.util.List;

//public class MainViewModel extends AndroidViewModel {
//
//    public LiveData<List<NoteEntity>> mNotes;
//    private AppRepository mRepository;
//
//    public MainViewModel(@NonNull Application application) {
//        super(application);
//
//        mRepository = AppRepository.getInstance(application.getApplicationContext());
//        mNotes = mRepository.mNotes;
//    }
//
//    public void addSampleData() {
//        mRepository.addSampleData();
//    }
//}
public class MainViewModel extends AndroidViewModel {

    public LiveData<List<NoteEntity>> mNotes;
    private AppRepository mRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mNotes = mRepository.mNotes;
    }

    public void addSampleData() {
        mRepository.addSampleData();
    }

    public void deleteAllNotes() {
        mRepository.deleteAllNotes();
    }
}