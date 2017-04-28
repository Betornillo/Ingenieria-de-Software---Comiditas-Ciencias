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
//  @SessionScoped
public class EliminarUsuarioBean implements Serializable{

	private static final EntityManagerFactory ent = 
            Persistence.createEntityManagerFactory("MiProyectoPU");

    private List<Usuario> lisUserTem;
    private List<UsuarioBean> lisUser;
    private List<UsuarioBean> userEliminados;
    

    

    public EliminarUsuarioBean() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
    }

    @PostConstruct
    public void init(){
        
        userEliminados = new LinkedList();
        lisUser = new LinkedList();

        UsuarioJpaController useJpa = new UsuarioJpaController(ent);
        lisUserTem =useJpa.findUsuarioEntities();

        for(int i = 0; i<lisUserTem.size(); i++){

            UsuarioBean ub = new UsuarioBean(lisUserTem.get(i).getIdUsuario(), lisUserTem.get(i).getNombre(),
            lisUserTem.get(i).getApp(), lisUserTem.get(i).getApm(), lisUserTem.get(i).getCorreo(),
            lisUserTem.get(i).getContrasena());

            lisUser.add(ub);
        }

    }

    public String eliminarUsuarios(){

        

    	for(UsuarioBean a : lisUser){
            if(a.getUserSelec()){

            	userEliminados.add(a);
            }
        }

        
        

        if(!userEliminados.isEmpty()){

            UsuarioJpaController useJpa = new UsuarioJpaController(ent);
           
            for(UsuarioBean ub : userEliminados){

                Integer idEliminar = ub.getIdUsuario();

                
                try {
                    
                    
                    useJpa.destroy(idEliminar);
                    
                    
                    lisUser.removeAll(userEliminados);    

                    FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Felicidades, se borro correctamente", ""));

                } catch (Exception e) {
                    FacesContext.getCurrentInstance().addMessage(null,
                     new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Fallo. No se pudo borrar el usuario", ""));
                }

            }


        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
             "No hay usuarios que borrar", ""));
        }

        return "eliminarUsuarios";
    }


    public void setLisUserTem(List lisUserTem){
    	this.lisUserTem=lisUserTem;
    }
    
    public void setLisUser(List lisUser){
    	this.lisUser=lisUser;
    }
    
    public void setUserEliminados(List userEliminados){
    	this.userEliminados=userEliminados;
    }

    public List getLisUserTem(){
    	return lisUserTem;
    }
    
    public List getLisUser(){
    	return lisUser;
    }
    
    public List getUserEliminados(){
    	return userEliminados;
    }


}




