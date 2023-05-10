package com.example.apicazione;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.material.transition.Hold;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {


    private final Context context;
    private ArrayList<CardItems> items;


    public CustomAdapter(Context context, ArrayList<CardItems> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        HolderView holderView;

        if(view == null){
            view= LayoutInflater.from(context).inflate(R.layout.card_item,
                    viewGroup,false);
            holderView= new HolderView(view);
            view.setTag(holderView);


        } else {
            holderView = (HolderView) view.getTag();

        }
        CardItems list = items.get(i);
        holderView.descrizione.setText(list.getDescrizione());
        holderView.titolo.setText(list.getTitolo());
        holderView.durata.setText(list.getDurata());


        return view;
    }

    private static class HolderView{
        private final TextView titolo;
        private final TextView descrizione;
        private final TextView durata;


        public HolderView(View view){
            titolo = view.findViewById(R.id.titolo);
            durata = view.findViewById(R.id.durata);
            descrizione = view.findViewById(R.id.descrizione);
        }



    }




}
