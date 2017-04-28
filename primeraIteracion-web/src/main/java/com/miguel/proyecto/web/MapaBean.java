package com.miguel.proyecto.web;

import com.miguel.proyecto.db.*;
import com.miguel.proyecto.db.controller.*;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.*;
import java.io.Serializable;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import javax.annotation.PostConstruct; 

import javax.swing.JOptionPane;


@ManagedBean
public class MapaBean implements Serializable{

	private static final EntityManagerFactory ent = 
            Persistence.createEntityManagerFactory("MiProyectoPU");
    

	private MapModel emptyModel;
      
    private String title;
      
    private double lat;
      
    private double lng;
  	
    public MapaBean() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
    }

    @PostConstruct
    public void init() {
        emptyModel = new DefaultMapModel();
    }
      
    public MapModel getEmptyModel() {
        return emptyModel;
    }
      
    public String getTitle() {
        return title;
    }
  
    public void setTitle(String title) {
        this.title = title;
    }
  
    public double getLat() {
        return lat;
    }
  
    public void setLat(double lat) {
        this.lat = lat;
    }
  
    public double getLng() {
        return lng;
    }
  
    public void setLng(double lng) {
        this.lng = lng;
    }

    public void addMarker() {
        Marker marker = new Marker(new LatLng(lat, lng), title);
        emptyModel.addOverlay(marker);
        //JOptionPane.showMessageDialog(null, title);

        LocalizacionJpaController ljc = new LocalizacionJpaController(ent);
        Localizacion loc = new Localizacion();
        loc.setPunto1(lat);
        loc.setPunto2(lng);
        try {
                
			ljc.create(loc);             
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", "Lat:" + lat + ", Lng:" + lng));
            } catch (Exception e) {
            	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
             "Fallo de registro: No se puede registrar el mapa", ""));  
            }

        
    }


}