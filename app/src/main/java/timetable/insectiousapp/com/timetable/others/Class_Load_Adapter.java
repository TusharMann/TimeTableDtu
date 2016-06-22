package timetable.insectiousapp.com.timetable.others;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import timetable.insectiousapp.com.timetable.R;

/**
 * Created by Tushar on 22-06-2016.
 */
public class Class_Load_Adapter extends ArrayAdapter<Class_Load> {


    Context context;
    ArrayList<Class_Load> classlist;

    public Class_Load_Adapter(Context context, ArrayList<Class_Load> list) {
        super(context,0,list);
        this.context = context;
        this.classlist = list;
    }

    public class ViewHolder {
        TextView showid;
        TextView showname;
        TextView showapi;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(context, R.layout.class_load_adapter_layout, null);

            ViewHolder vh = new ViewHolder();

            vh.showid = (TextView) convertView.findViewById(R.id.load_class_id);
            vh.showname = (TextView) convertView.findViewById(R.id.load_class_name);
            vh.showapi = (TextView) convertView.findViewById(R.id.load_class_api);

            convertView.setTag(vh);

        }

        ViewHolder vh = (ViewHolder) convertView.getTag();

        Class_Load c = classlist.get(position);

        vh.showid.setText(c.id);
        vh.showname.setText(c.name);
        vh.showapi.setText(c.api);



        return convertView;
    }





}
