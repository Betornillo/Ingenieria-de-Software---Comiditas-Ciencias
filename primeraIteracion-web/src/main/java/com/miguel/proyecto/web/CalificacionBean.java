package com.miguel.proyecto.web;

import java.util.*;
import java.io.Serializable;

import com.miguel.proyecto.web.*;
import com.miguel.proyecto.db.*;
import com.miguel.proyecto.db.controller.*;

public class CalificacionBean implements Serializable{

	private Integer calificacion;
	private Puesto idPuesto;
        private Usuario idUsuario;

	private boolean selecEli;

	public CalificacionBean(Integer calificacion, Usuario idUsuario, Puesto idPuesto){

		this.calificacion=calificacion;
		this.idUsuario=idUsuario;
		this.idPuesto=idPuesto;
	}

	public Integer getCalificacion(){
		return calificacion;
	}

	public Usuario getIdUsuario(){
		return idUsuario;
	}

	public Puesto getIdPuesto(){
		return idPuesto;
	}

	public boolean getselecEli(){
		return selecEli;
	}

	public void setselecEli(boolean selecEli){
		this.selecEli=selecEli;
	}

	public void setCalificacion(Integer calificacion){
		this.calificacion=calificacion;
	}

	public void setIdUsuario(Usuario idUsuario){
		this.idUsuario=idUsuario;
	}

	public void setIdPuesto(Puesto idPuesto){
		this.idPuesto=idPuesto;
	}





}
