package mobile.indomarco.com.migrasiHH.model;

public class InfoPluModel {

    String prodCode, desc, unit, tagCode, harga;

    public InfoPluModel(){

    }

    public InfoPluModel(String prodCode, String desc, String unit, String tagCode, String harga) {
        this.prodCode = prodCode;
        this.desc = desc;
        this.unit = unit;
        this.tagCode = tagCode;
        this.harga = harga;
    }

    public String getProdCode() {
        return prodCode;
    }

    public String getDesc() {
        return desc;
    }

    public String getUnit() {
        return unit;
    }

    public String getTagCode() {
        return tagCode;
    }

    public String getHarga() {
        return harga;
    }
}
