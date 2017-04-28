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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.Serializable;


/**
 *
 * @author miguel
 */
@ManagedBean
public class RegistraUsuarioBean implements Serializable{
    
    private static final EntityManagerFactory ent = 
            Persistence.createEntityManagerFactory("MiProyectoPU");

    Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@ciencias.unam.mx$");
    
    UsuarioBean userBean = new UsuarioBean();

    private String conContrasena;

    private String mailCon;

    public RegistraUsuarioBean() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
    }

    //verifica que a la hora de crear los puestos no tengan valores vacios
    public void addUsuario() {

        Boolean pasa = true;

        if(verificaCorreoEnBase(userBean.getCorreo())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
             "Este correo ya ha sido registrado", ""));   
            pasa=false;
        }

        if(userBean.getNombre().equals("")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
             "Fallo de registro: el nombre es vacio", ""));   
             pasa=false;         
        }
        if(userBean.getApp().equals("")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
             "Fallo de registro: el apellido paterno es vacio", "")); 
             pasa=false;           
        }

        if(userBean.getApm().equals("")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
             "Fallo de registro: el apellido materno es vacio", "")); 
             pasa=false;           
        }

        if(userBean.getCorreo().equals("")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
             "Fallo de registro: el correo es vacio", "")); 
             pasa=false;           
        }

        if(userBean.getContrasena().equals("")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
             "Fallo de registro: la contraseña es vacia", ""));  
             pasa=false;          
        }

        String mail = userBean.getCorreo();
        Matcher mather = pattern.matcher(mail);

        if(mather.find() == false){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
             "Fallo de registro: el correo no es valido", ""));   
             pasa=false;            
        }

        if(!conContrasena.equals(userBean.getContrasena())){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
             "Fallo de registro: las contraseñas no son iguales", ""));  
             pasa=false;                
        }

        if(pasa){
            UsuarioJpaController useJpa = new UsuarioJpaController(ent);
            Usuario user = new Usuario();

            user.setNombre(userBean.getNombre());
            user.setApp(userBean.getApp());
            user.setApm(userBean.getApm());
            user.setCorreo(userBean.getCorreo());
            user.setContrasena(userBean.getContrasena());
            user.setActivo(false);
            try{
                useJpa.create(user);

                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Felicidades, el registro se ha realizado correctamente", ""));

            Correo cc = new Correo();
            cc.registro(userBean.getCorreo());

            }catch (Exception e){
                FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Fallo de registro: error a la hora de registrar", "")); 
            }
        }
    }

    private Boolean verificaCorreoEnBase(String correo){

        UsuarioJpaController useJpa = new UsuarioJpaController(ent);

        List<Usuario> lista = useJpa.findUsuarioEntities();

        for(int i = 0; i<lista.size(); i++){
            
            if(lista.get(i).getCorreo().equals(correo)){
                return true;
            }
        }
        return false;
    }

    public void confirmacionCorreo(){

        UsuarioJpaController useJpa = new UsuarioJpaController(ent);
        
        List<Usuario> lista = useJpa.findUsuarioEntities();

        for(Usuario u: lista){

            if(u.getCorreo().equals(mailCon)){

                try{
                    String aa = String.valueOf(u.getActivo());
                    JOptionPane.showMessageDialog(null, aa);
                    u.setActivo(true);
                    useJpa.edit(u);
                    String bb = String.valueOf(u.getActivo());
                    JOptionPane.showMessageDialog(null, bb);
                    FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Felicidades, el registro finalizo. Puede ingresar ahora.", ""));
                
                }catch(Exception e){
                    FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Fallo de activacion", ""));         
                }

                
            }

        }


    }



    public UsuarioBean getUserBean(){
        return this.userBean;
    }

    public String getConContrasena(){
        return this.conContrasena;
    }

    public void setConContrasena(String conContrasena){
        this.conContrasena=conContrasena;
    }

    public void setUserBean(UsuarioBean userBean){
        this.userBean=userBean;
    }

    public String getMailCon(){
        return this.mailCon;
    }

    public void setMailCon(String mailCon){
        this.mailCon=mailCon;
    }
}