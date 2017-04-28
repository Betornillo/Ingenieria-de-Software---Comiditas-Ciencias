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
//@SessionScoped
public class EliminarAlimentoBean implements Serializable{
    


    private static final EntityManagerFactory ent = 
            Persistence.createEntityManagerFactory("MiProyectoPU");

    private List<Alimentos> lisAliT;
    private List<AlimentosBean> lisAli;
    private List<AlimentosBean> alimentosEliminados;

    public EliminarAlimentoBean() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
    }

    @PostConstruct
    public void init(){
        alimentosEliminados = new LinkedList();
        lisAli = new LinkedList();
        AlimentosJpaController aliJpa = new AlimentosJpaController(ent);
        lisAliT = aliJpa.findAlimentosEntities();    

        

        for(int i = 0; i<lisAliT.size(); i++){
            
            Puesto temp = lisAliT.get(i).getIdPuesto();

            if(temp.getZona()==1){

                AlimentosBean ab = new AlimentosBean(lisAliT.get(i).getIdAlimentos(),
                    lisAliT.get(i).getAlimento(), null);

                lisAli.add(ab);
            }
            
        }
    }

    public String eliminarAlimento(){

        for(AlimentosBean a : lisAli){
            if(a.getselecEli()){
                alimentosEliminados.add(a);
            }
        }

        if(!alimentosEliminados.isEmpty()){

            AlimentosJpaController aliJpa = new AlimentosJpaController(ent);
            for(AlimentosBean b : alimentosEliminados){
                Integer idAli = b.getIdAlimentos();

                try {
                
                    aliJpa.destroy(idAli);
                    lisAli.removeAll(alimentosEliminados);    
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Felicidades, se borro correctamente", ""));

                } catch (Exception e) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
             "Fallo. No se pudo borrar el alimento", ""));
                }
            }

            
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
             "No hay alimentos que borrar", ""));
        }

        return "editarPuesto";
    }

    public void setLisAliT(List lisAliT){
        this.lisAliT = lisAliT;
    }

    public List getLisAliT(){
        return this.lisAliT;
    }

    public void setLisAli(List lisAli){
        this.lisAli = lisAli;
    }

    public List getLisAli(){
        return this.lisAli;
    }
        

}