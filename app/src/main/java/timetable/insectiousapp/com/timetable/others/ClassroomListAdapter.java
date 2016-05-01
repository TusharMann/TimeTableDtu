package timetable.insectiousapp.com.timetable.others;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import timetable.insectiousapp.com.timetable.R;

/**
 * Created by Dheeraj on 22-Apr-16.
 */
public class ClassroomListAdapter extends ArrayAdapter<Classroom> {

    LayoutInflater l;
    Context context;
    List<Classroom> objects;
    LinearLayout llColorBackground;
    Random crazy;

    int[] colors={R.color.bluelight, R.color.grey4, R.color.grey2, R.color.darkblue4, R.color.orange2, R.color.lightgreen1};

    public ClassroomListAdapter(Context context, int resource, List<Classroom> objects, LayoutInflater l) {
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
        llColorBackground=(LinearLayout)v.findViewById(R.id.all_classroom_ll_color);

        crazy=new Random();
        llColorBackground.setBackgroundResource(colors[crazy.nextInt(colors.length-1)]);
        Log.i("colorvalues", "crazy.nextInt :"+crazy.nextInt(colors.length-1));
        Log.i("colorvalues", "color :"+colors[crazy.nextInt(colors.length-1)]);
        Classroom c1=objects.get(position);
        tvId.setText(c1.id);
        tvName.setText(c1.name);

        return v;
    }
}
