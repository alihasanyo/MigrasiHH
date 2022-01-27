package mobile.indomarco.com.migrasiHH.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import mobile.indomarco.com.migrasiHH.R;
import mobile.indomarco.com.migrasiHH.model.InfoPluModel;
import mobile.indomarco.com.migrasiHH.model.ListTransaksiSOModel;

public class InfoPluAdapter extends ArrayAdapter<InfoPluModel> {

    private final Activity context;
    private ArrayList<InfoPluModel> mData;


    public InfoPluAdapter(Activity context, ArrayList<InfoPluModel> data) {
        super(context, R.layout.item_info_plu, data);
        this.context = context;
        this.mData = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_info_plu, null, true);
        rowView.setId(position);

        TextView tvkode_produk = rowView.findViewById(R.id.tvprdcd);
        TextView tv_desc = rowView.findViewById(R.id.tvdesc);
        TextView tv_unit = rowView.findViewById(R.id.tvunit);
        TextView tv_tag = rowView.findViewById(R.id.tvtag);
        TextView tv_price = rowView.findViewById(R.id.tvprice);

        InfoPluModel m = mData.get(position);

        tvkode_produk.setText(m.getProdCode());
        tv_desc.setText(m.getDesc());
        tv_unit.setText(m.getUnit());
        tv_tag.setText(m.getTagCode());
        tv_price.setText(m.getHarga());

        return rowView;

    }
}
