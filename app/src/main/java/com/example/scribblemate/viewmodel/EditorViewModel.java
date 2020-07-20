package com.example.scribblemate.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.scribblemate.database.AppRepository;
import com.example.scribblemate.database.NoteEntity;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

//mutable live data -> change the data during the runtime.. live data s not mutable
public class EditorViewModel extends AndroidViewModel {

    public MutableLiveData<NoteEntity> mLiveNote = new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public EditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadData(int noteId) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                NoteEntity note = mRepository.getNoteById(noteId);
                mLiveNote.postValue(note);
//                this will force observer to rerun onCreate method
            }
        });

    }

    public void saveNote(String noteText) {
        NoteEntity note = mLiveNote.getValue();
        if (note == null){
            if(TextUtils.isEmpty(noteText.trim())){
                return;
            }
            else{
                note = new NoteEntity(new Date(), noteText.trim());
            }
        }
        else{
            note.setText(noteText.trim());
        }

        mRepository.insertNote(note);
    }

    public void deleteNote() {
        mRepository.deleteNote(mLiveNote.getValue());
    }
}
