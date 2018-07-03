package mobile.proj.spoilers.model;

/**
 * Created by Ahmed on 12/30/2017.
 */

public class Spoil {
    private  String idSpoil, spoilContent ;

    public String getIdSpoil() {
        return idSpoil;
    }

    public void setIdSpoil(String idSpoil) {
        this.idSpoil = idSpoil;
    }

    public String getSpoilContent() {
        return spoilContent;
    }

    public void setSpoilContent(String spoilContent) {
        this.spoilContent = spoilContent;
    }

    @Override
    public String toString() {
        return "Spoil{" +
                "idSpoil='" + idSpoil + '\'' +
                ", spoilContent='" + spoilContent + '\'' +
                '}';
    }
}
