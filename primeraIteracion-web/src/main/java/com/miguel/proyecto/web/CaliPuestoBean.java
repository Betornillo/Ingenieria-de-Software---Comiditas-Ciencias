package com.miguel.proyecto.web;

import com.miguel.proyecto.web.*;
import com.miguel.proyecto.db.*;
import com.miguel.proyecto.db.controller.*;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.util.*;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

import java.io.Serializable;


/**
 *
 * @author miguel
 */
@ManagedBean
public class CaliPuestoBean implements Serializable{
    


    private static final EntityManagerFactory ent = 
            Persistence.createEntityManagerFactory("MiProyectoPU");
    
    
    private PuestoCaliBean pues = new PuestoCaliBean();
    private LinkedList lisAli = pues.getLAli();

    private UsuarioBean usa = new UsuarioBean();

    public UsuarioBean getUse() {
	return usa;
    }

    public void setUse(UsuarioBean usa) {
	this.usa = usa;
    }

    public LinkedList getLisAli(){
        return this.lisAli;
    }

    public void setLisAli(LinkedList lisAli){
        this.lisAli=lisAli;
    }


    public PuestoCaliBean getPues() {
        return pues;
    }

    public void setPues(PuestoCaliBean pues) {
        this.pues = pues;
    }

    public CaliPuestoBean() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
    }

    //aqui empieza lo loquito

    public void anadeAlimento(){
        if(pues.getCalificacion().equals("")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
             "Fallo de registro: debe de proporcionar un alimento por lo menos", ""));            
        }else{
            
            PuestoJpaController t = new PuestoJpaController(ent);
	    UsuarioJpaController t2 = new UsuarioJpaController(ent);
            CalificacionJpaController t1 = new CalificacionJpaController(ent);
            
            Calificacion ali = new Calificacion();

            List<Puesto> lisTem = t.findPuestoEntities();
	    List<Usuario> lisTem2 = t2.findUsuarioEntities();
            List<Calificacion> lisTem1 = t1.findCalificacionEntities();

            int tamTem = lisTem.size();
            int tamTem1 = lisTem1.size();
	    int tamTem2 = lisTem2.size();

            if(tamTem==0){
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Primero crea un puesto", ""));
                return;
            }
            if(tamTem1==0){
                Puesto temp = lisTem.get(tamTem-1);
		Usuario temp2 = lisTem2.get(tamTem2-1);
                ali.setPuesto(temp);
		ali.setUsuario(temp2);
                ali.setCalifica(pues.getCalificacion());
            }else{
                
                Puesto temp = lisTem.get(tamTem-1);
		Usuario temp2 = lisTem2.get(tamTem2-1);
                Calificacion temp1 = lisTem1.get(tamTem1-1);
                ali.setPuesto(temp);
		ali.setUsuario(temp2);
                ali.setCalifica(pues.getCalificacion());                
            }

            
            //lisAli.add(pues.getAlimentos());

            try {

                t1.create(ali);
                    
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Felicidades, el alimento se ha realizado correctamente", ""));

            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
             "Fallo de registro: no se puedo registrar el alimento", ""));            
            }

             //JOptionPane.showMessageDialog(null, pues.getAlimentos());
            //lisAli.add(pues.getAlimentos());   
            //lisAli.add(pues.getAlimentos());
            //pues.darEleLis(pues.getAlimentos());
            
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Añadio un alimento correctamente", ""));
        }

    }




}
