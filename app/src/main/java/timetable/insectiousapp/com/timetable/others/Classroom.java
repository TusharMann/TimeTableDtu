package timetable.insectiousapp.com.timetable.others;

import java.io.Serializable;

/**
 * Created by Dheeraj on 22-Apr-16.
 */
public class Classroom implements Serializable {

    public String id;
    public String name;

    public Classroom(String id, String name)
    {
        this.id=id;
        this.name=name;
    }

}
