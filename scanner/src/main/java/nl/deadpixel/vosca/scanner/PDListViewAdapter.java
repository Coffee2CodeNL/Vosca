package nl.deadpixel.vosca.scanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by resco on 16-12-14.
 */
public class PDListViewAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public PDListViewAdapter(Context context, String[] values)
    {
        super(context, R.layout.pdrow, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.pdrow, parent, false);
        TextView tvPDENummer = (TextView) rowView.findViewById(R.id.tvPDENumber);
        TextView tvPDFullName = (TextView) rowView.findViewById(R.id.tvPDFullname);
        //TextView tvPDDescription = (TextView) rowView.findViewById(R.id.tvPDDescription);
        tvPDENummer.setText("E420");
        tvPDFullName.setText(values[position]);
        //tvPDDescription.setText("Veroorzaakt nare dingen!");

        return rowView;
    }
}
