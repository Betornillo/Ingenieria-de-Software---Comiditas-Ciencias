package com.miguel.proyecto.web;

import java.util.*;
import java.io.Serializable;

import com.miguel.proyecto.web.*;
import com.miguel.proyecto.db.*;
import com.miguel.proyecto.db.controller.*;

public class AlimentosBean implements Serializable{

	private Integer idAlimentos;
	private String alimento;
	private Puesto idPuesto;

	private boolean selecEli;

	public AlimentosBean(Integer idAlimentos, String alimento, Puesto idPuesto){

		this.idAlimentos=idAlimentos;
		this.alimento=alimento;
		this.idPuesto=idPuesto;
	}

	public Integer getIdAlimentos(){
		return idAlimentos;
	}

	public String getAlimento(){
		return alimento;
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

	public void setIdAlimentos(Integer idAlimentos){
		this.idAlimentos=idAlimentos;
	}

	public void setIdAlimentos(String alimento){
		this.alimento=alimento;
	}

	public void setIdAlimentos(Puesto idPuesto){
		this.idPuesto=idPuesto;
	}





}