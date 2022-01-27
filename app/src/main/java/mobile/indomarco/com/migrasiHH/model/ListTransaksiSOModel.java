package mobile.indomarco.com.migrasiHH.model;

public class ListTransaksiSOModel {

    String plu, qty, frac, kodelokasi,avgcost,desc,modify_by_, modify_dt;

    public ListTransaksiSOModel(){

    }

    public ListTransaksiSOModel(String plu, String qty, String frac, String kodelokasi, String avgcost, String desc, String modify_by_, String modify_dt) {
        this.plu = plu;
        this.qty = qty;
        this.frac = frac;
        this.kodelokasi = kodelokasi;
        this.avgcost = avgcost;
        this.desc = desc;
        this.modify_by_ = modify_by_;
        this.modify_dt = modify_dt;
    }

    public String getPlu() {
        return plu;
    }

    public String getQty() {
        return qty;
    }

    public String getFrac() {
        return frac;
    }

    public String getKodelokasi() {
        return kodelokasi;
    }

    public String getAvgcost() {
        return avgcost;
    }

    public String getDesc() {
        return desc;
    }

    public String getModify_by_() {
        return modify_by_;
    }

    public String getModify_dt() {
        return modify_dt;
    }
}
