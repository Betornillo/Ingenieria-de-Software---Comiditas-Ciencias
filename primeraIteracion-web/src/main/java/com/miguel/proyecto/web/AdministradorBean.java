package com.miguel.proyecto.web;

import java.util.*;
import java.io.Serializable;

import com.miguel.proyecto.web.*;
import com.miguel.proyecto.db.*;
import com.miguel.proyecto.db.controller.*;

public class AdministradorBean implements Serializable{

	private Integer idAdministrador;
	private Usuario idUsuario;

	public AdministradorBean(){}

	public AdministradorBean(Integer idAdministrador, Usuario idUsuario){

		this.idAdministrador=idAdministrador;
		this.idUsuario=idUsuario;
	}

	public Integer getIdAdministrador(){
		return this.idAdministrador;
	}

	public Usuario getIdUsuario(){
		return this.idUsuario;
	}

	public void setIdAdministrador(Integer idAdministrador){
		this.idAdministrador=idAdministrador;
	}

	public void setIdUsuario(Usuario idUsuario){
		this.idUsuario=idUsuario;
	}

}