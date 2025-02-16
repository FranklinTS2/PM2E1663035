package com.example.pm2e1663035;

import android.content.Context;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.example.pm2e1663035.Models.Contacto;

import java.util.List;

public class ShapeAdapter extends ArrayAdapter<Contacto> {

    public ShapeAdapter(Context context, int resource, List<Contacto> shapeList)
    {
        super(context,resource,shapeList);
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        Contacto shape = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.shape_cell,parent,false);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.shapeName);
        ImageView iv = (ImageView) convertView.findViewById(R.id.shapeImage);

        tv.setText(shape.getId() + "\n" + shape.getNombre() + "\n"+ shape.getPais() + "\n "+ shape.getTelefono() + "\n" + shape.getNota());
        tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
        iv.setImageBitmap(imageUtils.decodeFromBase64(shape.getImagen()));




        return convertView;
    }
}
