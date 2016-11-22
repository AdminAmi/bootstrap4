package Login;

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
import korisni.utility;
import korisni.PaginationHelper;

//za pagination
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;




/**
 *
 * @author amel
 */
//@RequestScoped
//@SessionScoped
@ViewScoped
@ManagedBean 
public class WebKorisnik implements Serializable{
    private Login.loginKontroler lk = new loginKontroler();
    private login selektovaniKorisnik = new login();
    private int selektovaniID, serverResponse;
    private String imePretraga, selektovaniTip;
    //Za paginator
    private int pageSize;//
    private int[] page;//
    private int currPage;
    private int finalPage;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private DataModel dtmdl = null;


    public WebKorisnik() {
        page = new int[]{5, 10, 15, 20, 30, 50};
        pageSize = 10;
        currPage=1;
        finalPage=(lk.getKorisnici().size()/pageSize)+1;
    } 
    
    
    public void ucitajOsobu(){
        selektovaniKorisnik=lk.getUserFromID(getSelektovaniID());
        setSelektovaniTip(selektovaniKorisnik.getRola());              
    }    
    public void pretraga(){
        resetLista();
        lk.pretragaPoImenu(getImePretraga());
        setImePretraga("");
    }
    public void resetLista(){lk.getPretraga().clear();}
    
    public void azurirajKorisnika() throws NoSuchAlgorithmException{ 
        selektovaniKorisnik.setRola(getSelektovaniTip());
        if(lk.azurirajOsobu(selektovaniKorisnik, getSelektovaniID())){
            utility.poruka("editKorisnik:btnAzKorisnik", "Uspješno ažuriranje korisnika");
        }
        else {utility.poruka("editKorisnik:btnAzKorisnik", "Neuspješao ažuriranje korisnika");}
    }
    
    public String obrisiKorisnika(){
        if(lk.obrisiOsobu(selektovaniKorisnik)){
            utility.poruka("editKorisnik:btnBrKorisnik", "Uspješno brisanje korisnika");
            return null;//"/test";
        }else {
            utility.poruka("editKorisnik:btnBrKorisnik", "Neuspješno brisanje korisnika");
            return null;
        }  
    }
    private List podLista(int od, int pado) {
        return  lk.getKorisnici().subList(od ,pado);
        
   // list.subList(pagesize*(currentpage-1), pagesize*currentpage);
    
    
    }
    
//    // o0vdje
//    public PaginationHelper getPagination() {
//        if (pagination == null) {
//            pagination = new PaginationHelper(this.pageSize, 0) {
//                @Override
//                public int getItemsCount() {
//                    //return itembean.count();
//                    return lk.getKorisnici().size();
//                }
//
//                @Override
//                public DataModel createPageDataModel() {
//                   // return new ListDataModel(korisni.utility.getPage(lk.getKorisnici(), page, pageSize));
//                      
//                   return new ListDataModel(this.podLista(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
//                }
//            };
//        }
//        return pagination;
//
//    }
    public DataModel createDataModel(){       
       int pado=getPageSize()*getCurrPage();
       if(pado>lk.getKorisnici().size()) pado=lk.getKorisnici().size();
       return new ListDataModel(podLista(getPageSize()*(getCurrPage()-1),pado));
    }
    
    public DataModel getdtmdl() {
        if (dtmdl == null) {
            dtmdl = createDataModel();
              
        }
        return dtmdl;
    }
   
    private void recreateModel() {
        setDtmdl(null);
    }

//    private void recreatePagination() {
//        pagination = null;
//    }
/*
    private void updateCurrentItem() {
        int count = lk.getKorisnici().size();
        if (selectedItemIndex >= count) {

            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;

            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            K = itembean.findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }
*/
    public void next() {
//        getPagination().nextPage();
        setCurrPage(getCurrPage() + 1);
        recreateModel();
        //return "home";
    }

    public void previous() {
//        getPagination().previousPage();
        setCurrPage(getCurrPage() - 1);
        recreateModel();
       // return "home";
    }

    //go to the 1st page

    public void firstPage() {
        //recreatePagination();
        currPage=1;
        recreateModel();
        //return "home";
    }

    //go to the last page
    public void lastPage() {

        //getPagination().setFinalPages();
        setCurrPage(lk.getKorisnici().size()%getPageSize());
        recreateModel();
        //return "home";
    }
    public boolean imaLiJos(){
        return currPage<finalPage;
    }
    public boolean imaLiPrije(){
        return currPage>1;
    }

    public void recreatePageSize(AjaxBehaviorEvent e) {
        currPage=1;
        finalPage=(lk.getKorisnici().size()/getPageSize())+1;
        recreateModel();
       // return "home";
    }



    public Login.loginKontroler getLk() { return lk; }
    public void setLk(Login.loginKontroler lk) { this.lk = lk; }   
    public String getImePretraga() {return imePretraga; }
    public void setImePretraga(String imePretraga) {this.imePretraga = imePretraga;}
    public login getSelektovaniKorisnik() {return selektovaniKorisnik;}
    public void setSelektovaniKorisnik(login selektovaniKorisnik) { this.selektovaniKorisnik = selektovaniKorisnik; }
    public int getSelektovaniID() {return selektovaniID; }
    public void setSelektovaniID(int selektovaniID) {this.selektovaniID = selektovaniID;}
    public int getServerResponse() {return serverResponse;}
    public void setServerResponse(int serverResponse) {this.serverResponse = serverResponse;}
    public String getSelektovaniTip() {return selektovaniTip;}
    public void setSelektovaniTip(String selektovaniTip) { this.selektovaniTip = selektovaniTip;} 
    // pag
    
    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the page
     */
    public int[] getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(int[] page) {
        this.page = page;
    }

    /**
     * @return the currPage
     */
    public int getCurrPage() {
        return currPage;
    }

    /**
     * @param currPage the currPage to set
     */
    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    /**
     * @return the finalPage
     */
    public int getFinalPage() {
        return finalPage;
    }

    /**
     * @param finalPage the finalPage to set
     */
    public void setFinalPage(int finalPage) {
        this.finalPage = finalPage;
    }

    /**
     * @return the dtmdl
     */
    /*
    public DataModel getDtmdl() {
        return dtmdl;
    }
*/
    /**
     * @param dtmdl the dtmdl to set
     */
    public void setDtmdl(DataModel dtmdl) {
        this.dtmdl = dtmdl;
    }
    
}
