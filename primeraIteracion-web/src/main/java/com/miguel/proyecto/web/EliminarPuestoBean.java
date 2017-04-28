package com.miguel.proyecto.web;

import com.miguel.proyecto.web.*;
import com.miguel.proyecto.db.*;
import com.miguel.proyecto.db.controller.*;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import java.util.*;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import javax.annotation.PostConstruct; 

import java.io.Serializable;


/**
 *
 * @author miguel
 */
@ManagedBean
@SessionScoped
public class EliminarPuestoBean implements Serializable{

	private static final EntityManagerFactory ent = 
            Persistence.createEntityManagerFactory("MiProyectoPU");

    private List<Puesto> lisPuesTem;
    private List<PuestoBean> lisPues;
    private List<PuestoBean> puesEliminados;
    
    public void setLisPuesTem(List lisPuesTem){
        this.lisPuesTem=lisPuesTem;
    }
    
    public void setLisPues(List lisPues){
        this.lisPues=lisPues;
    }
    
    public void setPuesEliminados(List puesEliminados){
        this.puesEliminados=puesEliminados;
    }

    public List getLisPuesTem(){
        return lisPuesTem;
    }
    
    public List getLisPues(){
        return lisPues;
    }
    
    public List getPuesEliminados(){
        return puesEliminados;
    }

    

    public EliminarPuestoBean() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
    }

    @PostConstruct
    public void init(){
        
        puesEliminados = new LinkedList();
        lisPues = new LinkedList();

        PuestoJpaController puesJpa = new PuestoJpaController(ent);
        lisPuesTem = puesJpa.findPuestoEntities();

        for(int i = 0; i<lisPuesTem.size(); i++){

            PuestoBean pb = new PuestoBean(lisPuesTem.get(i).getIdPuesto(),
             lisPuesTem.get(i).getZona(), lisPuesTem.get(i).getNombre(), 
             lisPuesTem.get(i).getIdLocaliza(), lisPuesTem.get(i).getDescripcion());

            lisPues.add(pb);
        }

    }

    private void eliminarCalIdPuesto(Integer idPuesto){

        CalificacionJpaController calJpa = new CalificacionJpaController(ent);
        List<Calificacion> listTem = calJpa.findCalificacionEntities();
        List<Calificacion> listCal = new LinkedList();

        for(Calificacion c : listTem){
            if(c.getCalificacionPK().getIdPuesto()==idPuesto){
                try{
                    calJpa.destroy(c.getCalificacionPK());
                }catch (Exception e){
                    
                }
            }
        }

    }

    public String eliminarPuesto(){

    	for(PuestoBean a : lisPues){
            if(a.getSelect()){
            	puesEliminados.add(a);
            }
        }

        if(!puesEliminados.isEmpty()){

            PuestoJpaController puesJpa = new PuestoJpaController(ent);

            for(PuestoBean pb : puesEliminados){

                Integer idEliminar = pb.getIdPuesto();
                
                try {
                    eliminarCalIdPuesto(idEliminar);    
                    puesJpa.destroy(idEliminar);
                    lisPues.removeAll(puesEliminados);    
                    FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Felicidades, se borro correctamente", ""));

                } catch (Exception e) {
                    FacesContext.getCurrentInstance().addMessage(null,
                     new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Fallo. No se pudo borrar el puesto-"+e, ""));
                }

            }


        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
             "No hay usuarios que borrar", ""));
        }

        return "eliminarUsuarios";
    }


    


}


