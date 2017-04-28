package com.miguel.proyecto.web;

import java.util.*;
import java.io.Serializable;

import com.miguel.proyecto.web.*;
import com.miguel.proyecto.db.*;
import com.miguel.proyecto.db.controller.*;

public class UsuarioBean implements Serializable{


	private Integer idUsuario;
	private String nombre;
	private String app;
	private String apm;
	private String correo;
	private String contrasena;
	private Boolean activo;
	private byte[] imagen;

	private Boolean userSelec;

	public UsuarioBean(){}

	public UsuarioBean(Integer idUsuario, String nombre, String app, String apm, String correo,
		String contrasena){

		this.idUsuario=idUsuario;
		this.nombre=nombre;
		this.app=app;
		this.apm=apm;
		this.correo=correo;
		this.contrasena=contrasena;
		this.activo=false;
		this.userSelec=false;
	}

	public Integer getIdUsuario(){
		return this.idUsuario;
	}

	public String getNombre(){
		return this.nombre;
	}

	public String getApp(){
		return this.app;
	}

	public String getApm(){
		return this.apm;
	}

	public String getCorreo(){
		return this.correo;
	}

	public String getContrasena(){
		return this.contrasena;
	}

	public Boolean getActivo(){
		return this.activo;
	}

	public byte[] getImagen(){
		return this.imagen;
	}

	public Boolean getUserSelec(){
		return this.userSelec;
	}

	public void setIdUsuario(Integer idUsuario){
		this.idUsuario=idUsuario;
	}

	public void setNombre(String nombre){
		this.nombre=nombre;
	}

	public void setApp(String app){
		this.app=app;
	}	

	public void setApm(String apm){
		this.apm=apm;
	}	

	public void setCorreo(String correo){
		this.correo=correo;
	}		

	public void setContrasena(String contrasena){
		this.contrasena=contrasena;
	}	

	public void setActivo(Boolean activo){
		this.activo=activo;
	}

	public void setImagen(byte[] imagen){
		this.imagen=imagen;
	}

	public void setUserSelec(Boolean userSelec){
		this.userSelec=userSelec;
	}


}