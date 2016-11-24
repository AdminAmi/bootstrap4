/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package korisni;

import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author ami
 */
public abstract class paginator {
    protected int pageSize;//
    protected int[] page;//
    protected int currPage;
    protected int finalPage;
    protected int pado;   
    protected int selectedItemIndex;
    protected int velicinaListe;
    protected DataModel dtmdl = null;
   

    public paginator() {
        //Postavljam defaultne opcije
        page = new int[]{5, 10, 15, 20, 30, 50};
        pageSize = 5;
        currPage=1;     
        
    }
    
     public DataModel getDtmdl() {
        if (dtmdl == null) {
            dtmdl = createDataModel();           
              
        }
        return dtmdl;
    }
   
    protected void recreateModel() { setDtmdl(null);  }
    
    public void next() {
        setCurrPage(getCurrPage() + 1);
        recreateModel();        
    }
    public void previous() {
        setCurrPage(getCurrPage() - 1);
        recreateModel();       
    }
    public void firstPage() {        
        currPage=1;
        recreateModel();        
    }   
    public void lastPage() {
        setCurrPage(getFinalPage());
        recreateModel();       
    }
    public boolean imaLiJos(){
        return currPage<finalPage;
    }
    public boolean imaLiPrije(){
        return currPage>1;
    }

    public void recreatePageSize(AjaxBehaviorEvent e) {
        setCurrPage(1);
        setFinalPage((getVelicinaListe()/getPageSize())+1);
        recreateModel();
       // return "home";
    }
    

    

    
    public int getPageSize() {return pageSize;  }
    public void setPageSize(int pageSize) {  this.pageSize = pageSize;  }
    public int[] getPage() {   return page;  }
    public void setPage(int[] page) {  this.page = page; }
    public int getCurrPage() { return currPage; }
    public void setCurrPage(int currPage) {   this.currPage = currPage;  }
    public int getFinalPage() {    return finalPage;   }
    public void setFinalPage(int fp) {this.finalPage = fp;}
    public int getPado() {  return getPageSize()*getCurrPage();   }//do kojeg indexa u
    public void setPado(int index) {this.pado=index;}
    public int getSelectedItemIndex() {   return selectedItemIndex;  }
    public void setSelectedItemIndex(int selectedItemIndex) {   this.selectedItemIndex = selectedItemIndex;  }
    public void setDtmdl(DataModel dtmdl) {  this.dtmdl = dtmdl;  }
    public int getVelicinaListe() {    return velicinaListe; }
    public  void setVelicinaListe(int velicinaListe){
        this.velicinaListe=velicinaListe;
    } 
    /**
     * Sljedeći primjer kako se kreira ova metoda
     * int pado1=getPageSize()*getCurrPage();
     * if(pado1>lk.getKorisnici().size()) pado1=lk.getKorisnici().size();        
     * return new ListDataModel(podLista(getPageSize()*(getCurrPage()-1),pado1));
     * @return the dtmdl
     */
    public abstract DataModel createDataModel() ;
    /**
     * 
     * Treba postaviti osnovne parametre a to su velicina liste i i postaviti zadnju stranicu
     * npr :
     * setVelicinaListe(getLk().getKorisnici().size());
     * setFinalPage((getVelicinaListe()/getPageSize())+1);
     */
    protected abstract void initParameters();
     
    
    
    
    
}
