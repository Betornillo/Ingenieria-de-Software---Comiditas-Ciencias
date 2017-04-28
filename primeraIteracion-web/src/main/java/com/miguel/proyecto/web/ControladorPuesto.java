package com.miguel.proyecto.web;


import com.miguel.proyecto.web.*;
import com.miguel.proyecto.db.*;
import com.miguel.proyecto.db.controller.*;
import java.util.Locale;

import javax.annotation.PostConstruct; 

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.util.*;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

import java.io.Serializable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;


/**
 *
 * @author miguel
 */
@ManagedBean
public class ControladorPuesto extends HttpServlet implements Serializable{
    


    private static final EntityManagerFactory ent = 
            Persistence.createEntityManagerFactory("MiProyectoPU");
    private PuestoBean pues = new PuestoBean();
    private List<Puesto> lisPuesTem;
    private LinkedList<PuestoBean> lisPues;
    private String id;
    private String idS;
    private PuestoBean puestoS;
    private List<AlimentosBean> listAlimentoS;
    private int caliS;
    private MapModel marcadoresS;
    private String idC;
    private boolean caliC=false;
    private String correC;
    private String lg;
    private boolean puedeCali;
    private Integer calificacionPuesto;
    
    private final FacesContext faceContext;
    private final HttpServletRequest httpServletRequest;
    private FacesMessage message;

    public Integer getCalificacionPuesto(){
        return this.calificacionPuesto;
    }

    public void setCalificacionPuesto(Integer calificacionPuesto){
        this.calificacionPuesto=calificacionPuesto;
    }

    public boolean getPuedeCali(){
        return this.puedeCali;
    }

    public void setPuedeCali(){
        this.puedeCali=puedeCali;
    }

    public String getCorreC(){
        return this.correC;
    }
    
    public void setCorreC(String correC){
        this.correC=correC;
    }

    public boolean getCalic(){
        return this.caliC;
    }

    public void setCaliC(boolean caliC){
        this.caliC=caliC;
    }

    public String getIdC(){
        return this.idC;
    }

    public void setIdC(String idC){
        this.idC=idC;
    }

    public MapModel getMarcadoresS() {
        return marcadoresS;
    }

    public int getCaliS(){
        return this.caliS;
    }

    public void setCaliS(int caliS){
        this.caliS=caliS;
    }

    public List<AlimentosBean> getListAlimentoS(){
        return this.listAlimentoS;
    }

    public void setListAlimentoS(List<AlimentosBean> listAlimentoS){
        this.listAlimentoS=listAlimentoS;
    }

    public PuestoBean getPuestoS(){
        return this.puestoS;
    }

    public void setPuestoS(PuestoBean puestoS){
        this.puestoS=puestoS;
    }
    
    public String getIdS(){
        return this.idS;
    }

    public void setIdS(String idS){
        this.idS=idS;
    }

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id=id;
    }

    public LinkedList getLisPues(){
        return this.lisPues;
    }

    

    private void setLisPues(LinkedList lisPues){
        this.lisPues=lisPues;
    }


    public ControladorPuesto(){
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("sesion") != null) {
            lg = httpServletRequest.getSession().getAttribute("sesion").toString();
        }
        //JOptionPane.showMessageDialog(null, lg);
    }
    
    @PostConstruct
    public void init(){ }
    /*
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        boolean create = true;         
        HttpSession session = request.getSession(create);
        response.setContentType("text/html;charset=UTF-8");
        
        lg = String.valueOf(request.getAttribute("sesion"));
        JOptionPane.showMessageDialog(null, lg);
        
    }*/


    public void calificaPuesto(){



        CalificacionJpaController calJpa = new CalificacionJpaController(ent);
        CalificacionPK calPk = new CalificacionPK();
        PuestoJpaController puesJpa = new PuestoJpaController(ent);
        Puesto pues = puesJpa.findPuesto(Integer.parseInt(idS));
        UsuarioJpaController userJpa = new UsuarioJpaController(ent);
        Usuario user = userJpa.findUsuario(Integer.parseInt(lg));
        calPk.setIdUsuario(Integer.parseInt(lg));
        calPk.setIdPuesto(Integer.parseInt(idS));
        Calificacion cal = new Calificacion();
        cal.setCalificacionPK(calPk);
        cal.setCalifica(calificacionPuesto);
        cal.setUsuario(user);
        cal.setPuesto(pues);

        try{
            calJpa.create(cal);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Calificacion guardada", ""));
        }catch (Exception e){
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
             "Error calificando puesto", ""));            
        }

    }


    

    //regresa true si puede calificar
    private boolean puedeCalificar(){
        CalificacionJpaController calJpa = new CalificacionJpaController(ent);
        List<Calificacion> listaCal = calJpa.findCalificacionEntities();
        UsuarioJpaController userJpa = new UsuarioJpaController(ent);
        Usuario us = userJpa.findUsuario(Integer.parseInt(lg));
        for(Calificacion c : listaCal){

            if(us.getIdUsuario()==c.getCalificacionPK().getIdUsuario() && 
                Integer.parseInt(idS)==c.getCalificacionPK().getIdPuesto()){
                return true;
            }
        }
        return false;
    }

    public void mostrarPuestoC(){

        //intentamos obtener los datos de un inicio de sesion

        puedeCali = puedeCalificar();
        listAlimentoS = new LinkedList();
        PuestoJpaController puesJpa = new PuestoJpaController(ent);
        Puesto pp = puesJpa.findPuesto(Integer.parseInt(idS));
        puestoS = new PuestoBean(pp.getIdPuesto(), pp.getZona(), pp.getNombre(),
            pp.getIdLocaliza(), pp.getDescripcion());
        
        AlimentosJpaController aliJpa = new AlimentosJpaController(ent);
        List<Alimentos> listAlimentoST = aliJpa.findAlimentosEntities();
        CalificacionJpaController calJpa = new CalificacionJpaController(ent);
        List<Calificacion> listCal = calJpa.findCalificacionEntities();


        if(!listAlimentoST.isEmpty()){
            for(Alimentos a : listAlimentoST){
                if(a.getIdPuesto().getIdPuesto().equals(Integer.parseInt(idS))) {
                    
                    AlimentosBean tt = new AlimentosBean(a.getIdAlimentos(), a.getAlimento(),
                        a.getIdPuesto());
                    listAlimentoS.add(tt);
                }
            }
        }
        poneMarcadoresS(pp.getIdLocaliza().getPunto1(), pp.getIdLocaliza().getPunto2());


        caliS = 0; 
        int cont = 0;
        if(!listCal.isEmpty()){

            for(Calificacion c : listCal){
            	if(Integer.parseInt(idS)==c.getCalificacionPK().getIdPuesto()){
            		caliS = caliS + c.getCalifica();	
            		cont++;
            	}
                
            }
        }
        if(cont!=0){
            caliS = caliS / cont;
        }
    }


    

    public void mostrarPuestoS(){

        listAlimentoS = new LinkedList();
        PuestoJpaController puesJpa = new PuestoJpaController(ent);
        Puesto pp = puesJpa.findPuesto(Integer.parseInt(idS));
        puestoS = new PuestoBean(pp.getIdPuesto(), pp.getZona(), pp.getNombre(),
            pp.getIdLocaliza(), pp.getDescripcion());

        AlimentosJpaController aliJpa = new AlimentosJpaController(ent);
        List<Alimentos> listAlimentoST = aliJpa.findAlimentosEntities();
        CalificacionJpaController calJpa = new CalificacionJpaController(ent);
        List<Calificacion> listCal = calJpa.findCalificacionEntities();

        if(!listAlimentoST.isEmpty()){
            for(Alimentos a : listAlimentoST){
                if(a.getIdPuesto().getIdPuesto().equals(Integer.parseInt(idS))) {
                    
                    AlimentosBean tt = new AlimentosBean(a.getIdAlimentos(), a.getAlimento(),
                        a.getIdPuesto());
                    listAlimentoS.add(tt);
                }
            }
        }
        poneMarcadoresS(pp.getIdLocaliza().getPunto1(), pp.getIdLocaliza().getPunto2());
        caliS = 0; 
        if(listCal.isEmpty()){
            for(Calificacion c : listCal){
                caliS = caliS + c.getCalifica();
            }
        }
        if(calJpa.getCalificacionCount()!=0){
            caliS = caliS / calJpa.getCalificacionCount();
        }
    }

    private void poneMarcadoresS(double a, double b){
        marcadoresS = new DefaultMapModel();
        LatLng coord1 = new LatLng(a, b);
        marcadoresS.addOverlay(new Marker(coord1, ""));
    }

    public String mostrarPuestosPorId(){
        
        PuestoJpaController puesJpa = new PuestoJpaController(ent);
        lisPuesTem = puesJpa.findPuestoEntities();

        lisPues = new LinkedList();
        
        for(int i = 0; i<lisPuesTem.size(); i++){

            if(Integer.parseInt(id) == lisPuesTem.get(i).getZona()){
                PuestoBean pb = new PuestoBean(lisPuesTem.get(i).getIdPuesto(), 
                    lisPuesTem.get(i).getZona(), lisPuesTem.get(i).getNombre(), 
                    lisPuesTem.get(i).getIdLocaliza(), lisPuesTem.get(i).getDescripcion());
                lisPues.add(pb);
            }
        }

        return "listaPuestoS";
    }

    public String mostrarPuestosPorIdC(){
        
        PuestoJpaController puesJpa = new PuestoJpaController(ent);
        lisPuesTem = puesJpa.findPuestoEntities();

        lisPues = new LinkedList();
        
        for(int i = 0; i<lisPuesTem.size(); i++){

            if(Integer.parseInt(idC) == lisPuesTem.get(i).getZona()){
                PuestoBean pb = new PuestoBean(lisPuesTem.get(i).getIdPuesto(), 
                    lisPuesTem.get(i).getZona(), lisPuesTem.get(i).getNombre(), 
                    lisPuesTem.get(i).getIdLocaliza(), lisPuesTem.get(i).getDescripcion());
                lisPues.add(pb);
            }
        }

        return "listaPuestoC";
    }

    

    
    public PuestoBean getPues() {
        return pues;
    }

    public void setPues(PuestoBean pues) {
        this.pues = pues;
    }

    

    //verifica que a la hora de crear los puestos no tengan valores vacios
    public void addPues() {

        //JOptionPane.showMessageDialog(null, id);

        if(pues.getZona()>5){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
             "Fallo de registro: la zona no debe de estar vacia", ""));            
        }else if(pues.getNombre().equals("")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
             "Fallo de registro: el nombre no debe de ser vacio", ""));            
        }else {
            
            PuestoJpaController t = new PuestoJpaController(ent);
            LocalizacionJpaController locJpa = new LocalizacionJpaController(ent);
            List<Localizacion> loca = locJpa.findLocalizacionEntities();
            Puesto ce = new Puesto();
            Integer ultIdMapa = locJpa.getLocalizacionCount();
            Localizacion loc = locJpa.findLocalizacion(loca.get(ultIdMapa-1).getIdLocaliza());

            

            ce.setZona(pues.getZona());
            
            ce.setNombre(pues.getNombre());
            
            ce.setDescripcion(pues.getDescripcion());

            ce.setIdLocaliza(loc);
            
            try {
                
                

                t.create(ce);
                
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Felicidades, el registro se ha realizado correctamente", ""));

            } catch (Exception e) {
            }
        }
        
    }

    public void anadeAlimento(){
        if(pues.getAlimentos().equals("")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
             "Fallo de registro: debe de proporcionar un alimento por lo menos", ""));            
        }else{
            
            PuestoJpaController t = new PuestoJpaController(ent);
            AlimentosJpaController t1 = new AlimentosJpaController(ent);
            
            Alimentos ali = new Alimentos();

            List<Puesto> lisTem = t.findPuestoEntities();
            List<Alimentos> lisTem1 = t1.findAlimentosEntities();

            int tamTem = lisTem.size();
            int tamTem1 = lisTem1.size();

            if(tamTem==0){
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Primero crea un puesto", ""));
                return;
            }
            if(tamTem1==0){
                ali.setIdAlimentos(1);
                Puesto temp = lisTem.get(tamTem-1);
                ali.setIdPuesto(temp);
                ali.setAlimento(pues.getAlimentos());
            }else{
                
                Puesto temp = lisTem.get(tamTem-1);
                Alimentos temp1 = lisTem1.get(tamTem1-1);
                ali.setIdAlimentos(temp1.getIdAlimentos()+1);
                ali.setIdPuesto(temp);  
                ali.setAlimento(pues.getAlimentos());                
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