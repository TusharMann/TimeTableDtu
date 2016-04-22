package timetable.insectiousapp.com.timetable.others;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import timetable.insectiousapp.com.timetable.R;

/**
 * Created by Dheeraj on 22-Apr-16.
 */
public class AllClassesListAdapter extends ArrayAdapter<Classroom> {

    LayoutInflater l;
    Context context;
    List<Classroom> objects;

    public AllClassesListAdapter(Context context, int resource, List<Classroom> objects, LayoutInflater l) {
        super(context, resource, objects);
        this.context=context;
        this.objects=objects;
        this.l=l;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=convertView;
        if(v==null) {
            v = l.inflate(R.layout.all_classroom_listview, null);
        }

        TextView tvId=(TextView)v.findViewById(R.id.all_classroom_id);
        TextView tvName=(TextView)v.findViewById(R.id.all_classroom_name);

        Classroom c1=objects.get(position);
        tvId.setText(c1.id);
        tvName.setText(c1.name);

        return v;
    }
}
