package com.miguel.proyecto.web;


import com.miguel.proyecto.web.*;
import com.miguel.proyecto.db.*;
import com.miguel.proyecto.db.controller.*;
import java.util.Locale;

import javax.annotation.PostConstruct; 

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

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



/**
 *
 * @author miguel
 */
@ManagedBean
public class Correo implements Serializable{
    
    public void registro(String correo){

        try{

            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", "comiditasCiencias@gmail.com");
            props.setProperty("mail.smtp.auth", "true");
            Session session = Session.getDefaultInstance(props);
            
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correo));
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(correo));
            message.setSubject("[Comiditas Ciencias]Confirmación de cuenta");
            //message.setText("Tu contraseña es:\n "+ contra +"\n Atentamente Soporte de Préstamo de Libros");
            message.setText("Te damos la bienvinida a Comiditas Ciencias. Para que el registro finalice por"+
                " favor ingresa a la siguiente liga:\n"+"http://localhost:8080/mi-primer-aplicacion-web/confirma"+
                 "cionCorreo.xhtml?mail="+correo+"\nSi usted no realizo el registro ignore este mensaje.");
            // Lo enviamos.
            Transport t = session.getTransport("smtp");
            //soporte.prestamo.libros.riesgo@gmail.com
            //riesgo12345
            t.connect("comiditasCiencias@gmail.com", "ricarica");
            t.sendMessage(message, message.getAllRecipients());

            // Cierre.
            t.close();
        }catch(Exception e){

        }

    }


}