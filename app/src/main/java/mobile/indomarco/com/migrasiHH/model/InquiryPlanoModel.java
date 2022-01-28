package mobile.indomarco.com.migrasiHH.model;

public class InquiryPlanoModel {

    String kodelokasi, prdcd, desc, frac, qty, maxPlano, qtyLPP;

    public InquiryPlanoModel(){

    }

    public InquiryPlanoModel(String kodelokasi, String prdcd, String desc, String frac, String qty, String maxPlano, String qtyLPP) {
        this.kodelokasi = kodelokasi;
        this.prdcd = prdcd;
        this.desc = desc;
        this.frac = frac;
        this.qty = qty;
        this.maxPlano = maxPlano;
        this.qtyLPP = qtyLPP;
    }

    public String getKodelokasi() {
        return kodelokasi;
    }

    public String getPrdcd() {
        return prdcd;
    }

    public String getDesc() {
        return desc;
    }

    public String getFrac() {
        return frac;
    }

    public String getQty() {
        return qty;
    }

    public String getMaxPlano() {
        return maxPlano;
    }

    public String getQtyLPP() {
        return qtyLPP;
    }
}
