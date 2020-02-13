package com.example.u2tema1android;

public class Cliente  {
  private String Cod_persona;
  private String Nombre;
  private String Apellidos;
  public Cliente(String cod_persona, String nombre, String apellidos) {
    Cod_persona = cod_persona;
    Nombre = nombre;
    Apellidos = apellidos;
  }
  public String getCod_persona() {
    return Cod_persona;
  }

  public void setCod_persona(String cod_persona) {
    Cod_persona = cod_persona;
  }

  public String getNombre() {
    return Nombre;
  }

  public void setNombre(String nombre) {
    Nombre = nombre;
  }

  public String getApellidos() {
    return Apellidos;
  }

  public void setApellidos(String apellidos) {
    Apellidos = apellidos;
  }



}