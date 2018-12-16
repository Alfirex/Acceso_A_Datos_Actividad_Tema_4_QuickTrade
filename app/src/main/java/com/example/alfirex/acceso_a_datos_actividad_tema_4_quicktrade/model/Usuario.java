package com.example.alfirex.acceso_a_datos_actividad_tema_4_quicktrade.model;

public class Usuario {
    private String usuario;
    private String correo;
    private String nombre;
    private String apellidos;
    private String contraseña;
    private String direccion;
    private String iduser;

    public Usuario() {

    }

    public Usuario(String usuario, String correo, String nombre, String apellidos, String contraseña, String direccion, String iduser) {
        this.usuario = usuario;
        this.correo = correo;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.contraseña = contraseña;
        this.direccion = direccion;
        this.iduser = iduser;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }
}
