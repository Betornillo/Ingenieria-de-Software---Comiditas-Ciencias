/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miguel.proyecto.web;

import com.miguel.proyecto.web.*;
import com.miguel.proyecto.db.*;
import com.miguel.proyecto.db.controller.*;
import java.util.Locale;
import javax.annotation.PostConstruct;

import java.util.*;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;

/**
 *
 * Bean manejado qué se utiliza para el manejo de inicio de Sesión en
 * la aplicación web.
 *
 * @author alex
 */
@ManagedBean // LEER LA DOCUMENTACIÖN DE ESTA ANOTACIÓN: Permite dar de alta al bean en la aplicación
@RequestScoped // Sólo está disponible a partir de peticiones al bean
public class Login implements Serializable{

    private String usuario;
    private String password;
    private final HttpServletRequest httpServletRequest; // Obtiene información de todas las peticiones de usuario.
    private final FacesContext faceContext; // Obtiene información de la aplicación
    private FacesMessage message;
    private static final EntityManagerFactory ent = 
            Persistence.createEntityManagerFactory("MiProyectoPU");
    private boolean adminAct = false;
    private Usuario usuaGuar = null;
    
    /**
     * Constructor para inicializar los valores de faceContext y
     * httpServletRequest.
     */
    public Login() {
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
    }

    /**
     * Obtiene el nombre de usuario.
     *
     * @return El nombre de usuario.
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Establece el nombre de usuario.
     *
     * @param usuario El nombre de usuario a establecer.
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Regresa la contraseña del usuario.
     *
     * @return La contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param contrasena La contraseña del usuario a establecer.
     */
    public void setPassword(String password) {
        this.password = password;
    }




    public boolean validate(String user, String password) throws Exception{
        UsuarioJpaController useJpa = new UsuarioJpaController(ent);
        List<Usuario> listaUsuario = useJpa.findUsuarioEntities();
        AdministradorJpaController admJpa = new AdministradorJpaController(ent);
        List<Administrador> listaAdmin = admJpa.findAdministradorEntities();
        Boolean regresa = false;
        int idUser = -1;

        for(int i= 0; i<listaUsuario.size();i++){
        	
        	String correo = listaUsuario.get(i).getCorreo();
        	String contra = listaUsuario.get(i).getContrasena();
        	Boolean activ = listaUsuario.get(i).getActivo();

        	if(correo.equals(user) && contra.equals(password) && activ){

                idUser= listaUsuario.get(i).getIdUsuario();
                usuaGuar = listaUsuario.get(i);
        		regresa=true;
        	}
        }

        if(!listaAdmin.isEmpty()){
            for(Administrador a : listaAdmin){
                if(idUser==a.getIdUsuario().getIdUsuario()){
                adminAct = true;
                }    
            }
        }
        return regresa;
    }

    /**
     * Método encargado de validar el inicio de sesión.
     *
     * @return El nombre de la vista que va a responder.
     */
    public String login() throws Exception {
        boolean valid = validate(usuario, password);
        JOptionPane.showMessageDialog(null, String.valueOf(adminAct));
        if(valid && adminAct){
            httpServletRequest.getSession().setAttribute("sesion", usuaGuar.getIdUsuario());
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Acceso Correcto", null);
            faceContext.addMessage(null, message);
            return "principalA";
        }
        if (valid){
            httpServletRequest.getSession().setAttribute("sesion", usuaGuar.getIdUsuario());
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Acceso Correcto", null);
            faceContext.addMessage(null, message);
            return "principalC.xhtml";
        }
        message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario o contraseña incorrecto", null);
        faceContext.addMessage(null, message);
        return "principalS";
        
    }

}
