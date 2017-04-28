package com.miguel.proyecto.web;

import java.util.*;
import java.io.Serializable;

import com.miguel.proyecto.web.*;
import com.miguel.proyecto.db.*;
import com.miguel.proyecto.db.controller.*;

public class PuestoCaliBean implements Serializable{

	private Integer idPuesto;
	private int zona;
	private String nombre;
	private String local1;
	private String local2;
	private Integer calificacion;
	private String descripcion;
	private LinkedList lali = new LinkedList();
	private Localizacion idLocaliza;

	private Boolean select;

	public PuestoCaliBean(){}

	public PuestoCaliBean(Integer idPuesto, int zona, String nombre, Localizacion idLocaliza,
		String descripcion){

		this.idPuesto=idPuesto;
		this.zona=zona;
		this.nombre=nombre;
		this.idLocaliza=idLocaliza;
		this.descripcion=descripcion;

	}

	public void darEleLis(Integer calificacion){
		lali.add(calificacion);   
	}

	public Integer getIdPuesto(){
		return this.idPuesto;
	}
	
	public LinkedList getLAli(){
		return lali;
	}

	public void setDescripcion(String descripcion){
		this.descripcion=descripcion;
	}

	public void setZona(int zona){
		this.zona=zona;
	}

	public void setNombre(String nombre){
		this.nombre=nombre;
	}

	public void setLocal1(String local1){
		this.local1=local1;
	}

	public void setLocal2(String local2){
		this.local2=local2;
	}

	public void setCalificacion(Integer calificacion){
		this.calificacion=calificacion;
	}


	public int getZona(){
		return this.zona;
	}

	public String getDescripcion(){
		return this.descripcion;
	}

	public String getNombre(){
		return this.nombre;
	}

	public String getLocal1(){
		return this.local1;
	}

	public String getLocal2(){
		return this.local2;
	}

	public Integer getCalificacion(){
		return this.calificacion;
	}

	public void setIdPuesto(Integer idPuesto){
		this.idPuesto=idPuesto;
	}

	public Localizacion getIdLocaliza() {
        return idLocaliza;
    }

    public void setIdLocaliza(Localizacion idLocaliza) {
        this.idLocaliza = idLocaliza;
    }

    public Boolean getSelect(){
    	return this.select;
    }

    public void setSelect(Boolean select){
    	this.select=select;
    }
}
