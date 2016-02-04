package entity;

/**
 * Created by mrpan on 15/12/27.
 */
public class Share {
    String TITLE;
    String SUMMARY;
    String TARGET_URL;
    String IMAGE_URL;
    String APP_NAME;

    public Share(){
        TITLE="";
        SUMMARY="";
        TARGET_URL="";
        IMAGE_URL="";
        APP_NAME="Ð¡°²°²";
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String tITLE) {
        TITLE = tITLE;
    }

    public String getSUMMARY() {
        return SUMMARY;
    }

    public void setSUMMARY(String sUMMARY) {
        SUMMARY = sUMMARY;
    }

    public String getTARGET_URL() {
        return TARGET_URL;
    }

    public void setTARGET_URL(String tARGET_URL) {
        TARGET_URL = tARGET_URL;
    }

    public String getIMAGE_URL() {
        return IMAGE_URL;
    }

    public void setIMAGE_URL(String iMAGE_URL) {
        IMAGE_URL = iMAGE_URL;
    }

    public String getAPP_NAME() {
        return APP_NAME;
    }

    public void setAPP_NAME(String aPP_NAME) {
        APP_NAME = aPP_NAME;
    }
}
