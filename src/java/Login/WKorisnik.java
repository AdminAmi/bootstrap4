/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import javax.faces.model.DataModel;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.ListDataModel;
import korisni.utility;

/**
 *
 * @author ami
 */
@ViewScoped
@ManagedBean 
public class WKorisnik extends korisni.paginator{
    private Login.loginKontroler lk = new loginKontroler();
    private login selektovaniKorisnik = new login();
    private int selektovaniID, serverResponse;
    private String imePretraga, selektovaniTip;

    public WKorisnik() {
        super();
        initParameter();        
    }
    private void initParameter(){
        setVelicinaListe(getLk().getKorisnici().size());
        setFinalPage((getVelicinaListe()/getPageSize())+1);        
    }
    private List podLista(int firstIndex, int lastIndex) {
        return  lk.getKorisnici().subList(firstIndex ,lastIndex);
    }
    @Override
    public void recreatePageSize(AjaxBehaviorEvent e) {
        setCurrPage(1);
        setFinalPage((getVelicinaListe()/pageSize)+1);
        recreateModel();
       // return "home";
    }
    @Override
    public DataModel createDataModel() {
      int pado1=getPageSize()*getCurrPage();
      if(pado1>lk.getKorisnici().size()) pado1=lk.getKorisnici().size();        
      return new ListDataModel(podLista(getPageSize()*(getCurrPage()-1),pado1));
    }
    
    public void ucitajOsobu(){
        setSelektovaniKorisnik(getLk().getUserFromID(getSelektovaniID()));
        setSelektovaniTip(getSelektovaniKorisnik().getRola());              
    }    
    public void pretraga(){
        resetLista();
        getLk().pretragaPoImenu(getImePretraga());
        setImePretraga("");
    }
    public void resetLista(){getLk().getPretraga().clear();}
    
    public void azurirajKorisnika() throws NoSuchAlgorithmException{ 
        getSelektovaniKorisnik().setRola(getSelektovaniTip());
        if(getLk().azurirajOsobu(getSelektovaniKorisnik(), getSelektovaniID())){
            utility.poruka("editKorisnik:btnAzKorisnik", "Uspješno ažuriranje korisnika");
        }
        else {utility.poruka("editKorisnik:btnAzKorisnik", "Neuspješao ažuriranje korisnika");}
    }
    
    public String obrisiKorisnika(){
        if(getLk().obrisiOsobu(getSelektovaniKorisnik())){
            utility.poruka("editKorisnik:btnBrKorisnik", "Uspješno brisanje korisnika");
            return null;//"/test";
        }else {
            utility.poruka("editKorisnik:btnBrKorisnik", "Neuspješno brisanje korisnika");
            return null;
        }  
    }

    /**
     * @return the lk
     */
    public Login.loginKontroler getLk() {
        return lk;
    }

    /**
     * @param lk the lk to set
     */
    public void setLk(Login.loginKontroler lk) {
        this.lk = lk;
    }

    /**
     * @return the selektovaniKorisnik
     */
    public login getSelektovaniKorisnik() {
        return selektovaniKorisnik;
    }

    /**
     * @param selektovaniKorisnik the selektovaniKorisnik to set
     */
    public void setSelektovaniKorisnik(login selektovaniKorisnik) {
        this.selektovaniKorisnik = selektovaniKorisnik;
    }

    /**
     * @return the selektovaniID
     */
    public int getSelektovaniID() {
        return selektovaniID;
    }

    /**
     * @param selektovaniID the selektovaniID to set
     */
    public void setSelektovaniID(int selektovaniID) {
        this.selektovaniID = selektovaniID;
    }

    /**
     * @return the serverResponse
     */
    public int getServerResponse() {
        return serverResponse;
    }

    /**
     * @param serverResponse the serverResponse to set
     */
    public void setServerResponse(int serverResponse) {
        this.serverResponse = serverResponse;
    }

    /**
     * @return the imePretraga
     */
    public String getImePretraga() {
        return imePretraga;
    }

    /**
     * @param imePretraga the imePretraga to set
     */
    public void setImePretraga(String imePretraga) {
        this.imePretraga = imePretraga;
    }

    /**
     * @return the selektovaniTip
     */
    public String getSelektovaniTip() {
        return selektovaniTip;
    }

    /**
     * @param selektovaniTip the selektovaniTip to set
     */
    public void setSelektovaniTip(String selektovaniTip) {
        this.selektovaniTip = selektovaniTip;
    }

   

   
   
    
}
