package com.example.u2tema1android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity  extends AppCompatActivity {
  Retrofit retrofit;
  servicesRetrofit miserviceretrofit;
  private RecyclerView recyclerView;
  private RecyclerView.LayoutManager layoutManager;
  private MiNuevoAdaptador adaptador;
  private ArrayList<Cliente> misdatos;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    misdatos = new ArrayList<>();
    recyclerView = findViewById(R.id.recycler_view);

    final String url = "https://fagsgs.000webhostapp.com/upt/";
    Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    retrofit = new Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    miserviceretrofit = retrofit.create(servicesRetrofit.class);

    Call<List<Cliente>> call = miserviceretrofit.getUsersGet();
    //Apartir de aqui la forma cambia de la manera sincrona a la asincrona
    //basicamente mandamos a llamar el metodo enqueue, y le pasamos como parametro el call back
    //Recuerda que el IDE es para ayudarte asi que lo creara automaticamente al escribir "new"
    call.enqueue(new Callback<List<Cliente>>() {
      //Metodo que se ejecutara cuando no hay problemas y obtenemos respuesta del server
      @Override
      public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
      //Exactamente igual a la manera sincrona,la respuesta esta en el body
        for (Cliente res : response.body()) {

          misdatos.add(new Cliente(res.getCod_persona(), res.getNombre(), res.getApellidos()));
          adaptador = new MiNuevoAdaptador(MainActivity.this , misdatos);
          recyclerView.setAdapter(adaptador);
          layoutManager = new LinearLayoutManager(MainActivity.this);
          recyclerView.setLayoutManager(layoutManager);
          Log.e("Usuario: ", res.getNombre() + " " + res.getApellidos());

        }
      }

      //Metodo que se ejecutara cuando ocurrio algun problema
      @Override
      public void onFailure(Call<List<Cliente>> call, Throwable t) {
        Log.e("onFailure", t.toString());// mostrmos el error
      }

    });

    //listar()
  }

  private ArrayList<Cliente> ListaClientes() {
    final ArrayList<Cliente> Clientes = new ArrayList<>();
    Call<List<Cliente>> call = miserviceretrofit.getUsersGet();
    call.enqueue(new Callback<List<Cliente>>() {
      @Override
      public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
        Log.e("mirespuesta: ", response.toString());
        for (Cliente res : response.body()) {
          Clientes.add(new Cliente(res.getCod_persona(), res.getNombre(), res.getApellidos()));
          Log.e("mirespuesta: ", "id= " + res.getCod_persona() + " prod= " + res.getNombre() + " precio " + res.getApellidos());
        }
      }

      @Override
      public void onFailure(Call<List<Cliente>> call, Throwable t) {
        Log.e("onFailure", t.toString());// mostrmos el error
      }
    });
    return Clientes;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_insertar:
        startActivity(new Intent(this, InsertarCliente.class));
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}