package com.glass.plat4mation.assignment5;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FactAdapter extends ArrayAdapter {

    private Context context;
    private List<Fact> factList;

    public FactAdapter(@NonNull Context context, ArrayList<Fact> list) {
        super(context, 0 , list);
        this.context = context;
        factList = list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listItem = LayoutInflater.from(context).inflate(R.layout.fact_list_item,parent,false);

        Fact currentFact = factList.get(position);

        TextView name = listItem.findViewById(R.id.fact_number);
        name.setText(currentFact.getNumber());

        TextView release = listItem.findViewById(R.id.fact_text);
        release.setText(currentFact.getText());

        return listItem;
    }
}
