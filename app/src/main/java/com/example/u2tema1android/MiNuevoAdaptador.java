package com.example.u2tema1android;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MiNuevoAdaptador extends
        RecyclerView.Adapter<MiNuevoAdaptador.ViewHolder> {
  private LayoutInflater inflador;
  private ArrayList<Cliente> lista;
  Context micontext;

  public MiNuevoAdaptador(Context context, ArrayList<Cliente> lista) {
    this.lista = lista;
    micontext=context;
    inflador = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = inflador.inflate(R.layout.minuevoitem, parent, false);
    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int i) {
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(micontext, DetalleCliente.class);
        intent.putExtra("codigocli", holder.codigo.getText());
        micontext.startActivity(intent);
      }
    });
    holder.codigo.setText(lista.get(i).getcodigo());
    holder.titulo.setText(lista.get(i).getNombre()+" "+lista.get(i).getApellido());
  }

  @Override
  public int getItemCount() {
    return lista.size();
  }
  public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView titulo, subtitutlo,codigo;
    public ImageView icon;
    ViewHolder(View itemView) {
      super(itemView);
      codigo = (TextView) itemView.findViewById(R.id.codigo);
      titulo = (TextView)itemView.findViewById(R.id.titulo);
      subtitutlo = (TextView)itemView.findViewById(R.id.subtitulo);
      icon = (ImageView)itemView.findViewById(R.id.icono);
    }
  }

  public void update(ArrayList<Cliente> datas){
    lista.clear();
    lista.addAll(datas);
    notifyDataSetChanged();
  }
}