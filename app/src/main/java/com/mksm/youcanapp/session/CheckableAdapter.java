package com.mksm.youcanapp.session;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.mksm.youcanapp.R;
import com.mksm.youcanapp.entities.interfaces.Checkable;

import java.util.List;

/**
 * Created by Техно on 07.02.2016.
 */
public class CheckableAdapter extends ArrayAdapter<Checkable> {
    private final List<? extends Checkable> listOfCheckable;
    private final Activity context;

    public CheckableAdapter (Activity context, List<? extends Checkable> objects) {
        super(context, R.layout.checkbox_fragment, (List<Checkable>) objects);
        this.context = context;
        this.listOfCheckable = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CheckBox checkbox;
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.checkbox_fragment, null);
            checkbox = (CheckBox) convertView.findViewById(R.id.checkBox);
            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int position = (Integer) buttonView.getTag();
                    if (isChecked)
                        listOfCheckable.get(position).markChecked();
                    else
                        listOfCheckable.get(position).markUnchecked();
                }
            });
            convertView.setTag(checkbox);
        } else {
            checkbox = (CheckBox) convertView.getTag();
        }
        checkbox.setTag(position); // This line is important.

        checkbox.setText(listOfCheckable.get(position).getText());
        checkbox.setChecked(listOfCheckable.get(position).isChecked());

        return convertView;
    }
}
