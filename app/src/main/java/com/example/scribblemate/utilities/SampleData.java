package com.example.scribblemate.utilities;

import com.example.scribblemate.database.NoteEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SampleData {

    private static final String SAMPLE_TEXT_1 = "Complete maths homework";
    private static final String SAMPLE_TEXT_2 = "Complete JAVA homework because \n you have a mini project to submit";
    private static final String SAMPLE_TEXT_3 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n In euismod nisl condimentum erat sollicitudin pretium.\n Proin vulputate quam sem, vel auctor velit lobortis sed.\n Phasellus pulvinar elit augue, eu iaculis felis egestas sed.\n In sagittis urna ac molestie aliquet.\n Fusce elementum maximus faucibus.\n Integer eget varius orci.\n Vivamus eget sapien felis.\n Interdum et malesuada fames ac ante ipsum primis in faucibus.\n In scelerisque commodo lorem.\n Integer volutpat tellus in mi tristique, lobortis accumsan eros egestas.\n Integer ornare odio a massa luctus consequat.\n Proin quis erat id nibh sollicitudin rhoncus eu ac metus.\n Nam viverra nibh nec metus eleifend, a interdum felis sagittis.\n Etiam interdum euismod enim, nec scelerisque ipsum varius a.\n";

    private static Date getDate(int diff){
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MILLISECOND, diff);
        return  cal.getTime();
    }
    public static List<NoteEntity> getNotes(){
        List<NoteEntity> notes = new ArrayList<>();
        notes.add(new NoteEntity(getDate(0),SAMPLE_TEXT_1));
        notes.add(new NoteEntity(getDate(-1),SAMPLE_TEXT_2));
        notes.add(new NoteEntity(getDate(-2),SAMPLE_TEXT_3));
        return notes;
    }
}
