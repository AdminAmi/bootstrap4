
package Login;

import Login.loginKontroler;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import korisni.utility;

/**
 *
 * @author amel
 */
@SessionScoped
//@ManagedBean (name="LogIn")
public class webLogIn {
    private boolean localHost;
    private Login.loginKontroler lk = new loginKontroler();    
    private String user, pass;
    private boolean testRegistracije, zastavica=false;
    private String newPass, confirmPass;  
    private int serverResponse = 0;
    private List<String> tipovi = new ArrayList<>();   

    public webLogIn() {
         setTestRegistracije(false);
         setLocalHost(utility.provjeriLocal());
         reset();
         getTipovi().add("admin");
         getTipovi().add("korisnik");
         getTipovi().add("guest"); 
    }
    
    private void reset(){
        setUser(""); setPass(""); setNewPass(""); setConfirmPass("");
    }
    
    public String promjenaPass() throws NoSuchAlgorithmException{
        serverResponse++;
        if(lk.getKorisnik().getPass().equals(utility.sha1(getPass()) ) &&
                (getNewPass().equals(getConfirmPass())) ) {
        
            lk.getKorisnik().setPass(newPass);
            lk.azurirajOsobu(lk.getKorisnik(),lk.getKorisnik().getId());
            setZastavica(true);
            utility.poruka("promjenaLozinke:promjenaPass", 
                    "Uspješna promjena zaporke!!!");            
            reset();
            } else {
            setZastavica(false);
            utility.poruka("promjenaLozinke:promjenaPass", 
                    "Neuspješna promjena zaporke!!!"); 
            reset();
        }            
        return null;
    }    
    
    /**
     * Unosi novog korisnika
     * @return
     * @throws NoSuchAlgorithmException     * 
     */
    public String unesiNovogKorisnika() throws NoSuchAlgorithmException {
        serverResponse++;
        if( getNewPass().equals(getConfirmPass()) && getNewPass().length()>0){
            lk.getNoviKorisnik().setPass(getNewPass());
            lk.dodajOsobu(lk.getNoviKorisnik());            
            utility.poruka("unosNovogKorisnika:btnNoviKorisnik",
                    "Uspješno dodavanje novog korisnika!!!");
            reset();
            
        } else {            
            utility.poruka("unosNovogKorisnika:btnNoviKorisnik",
                    "Unešene vrijednosti za zaporke nisu iste!!!");
            reset();
        }
        return null;
    }
    
    public String registracija(){       
        if(lk.LogIN(user, pass)) {
            setTestRegistracije(true);
            reset();
            return "test";
        }
        else {
            setTestRegistracije(false);
            reset();
            utility.poruka("loginForm:myButton","Neuspješna prijava na sistem!!!");           
        }
        return null;
    }
    
    public String logOff(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();        
        lk=null;        
        reset();
        setTestRegistracije(false);       
        return "/prijava?faces-redirect=true";        
    }
    //get & set
    public Login.loginKontroler getLk() {  return lk;  }
    public void setLk(Login.loginKontroler lk) {   this.lk = lk;  }
    public String getUser() {  return user; }
    public void setUser(String user) { this.user = user; }
    public String getPass() {return pass;}
    public void setPass(String pass) { this.pass = pass;}
    public boolean isTestRegistracije() {  return testRegistracije;  }
    public void setTestRegistracije(boolean testRegistracije) {  this.testRegistracije = testRegistracije; }
    public String getNewPass() { return newPass; }
    public void setNewPass(String newPass) { this.newPass = newPass; }
    public String getConfirmPass() {   return confirmPass; }
    public void setConfirmPass(String confirmPass) { this.confirmPass = confirmPass; }  
    public boolean isZastavica() { return zastavica; }
    public void setZastavica(boolean zastavica) {this.zastavica = zastavica; }
    public int getServerResponse() { return serverResponse; }
    public void setServerResponse(int serverResponse) {this.serverResponse = serverResponse; }
    public List<String> getTipovi() {return tipovi;}
    public void setTipovi(List<String> tipovi) { this.tipovi = tipovi;}
    public boolean isLocalHost() {return localHost; }
    public void setLocalHost(boolean localHost) { this.localHost = localHost;}
    
}
