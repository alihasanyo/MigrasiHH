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
import mobile.indomarco.com.migrasiHH.model.InquiryPlanoModel;

public class InquiryPlanoAdapter extends ArrayAdapter<InquiryPlanoModel> {

    private final Activity context;
    private ArrayList<InquiryPlanoModel> mData;


    public InquiryPlanoAdapter(Activity context, ArrayList<InquiryPlanoModel> data) {
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
        TextView tv_frac = rowView.findViewById(R.id.tvunit);
        TextView tv_qty = rowView.findViewById(R.id.tvtag);
        TextView tv_qty_lpp = rowView.findViewById(R.id.tvprice);

        InquiryPlanoModel m = mData.get(position);

        tvkode_produk.setText(m.getPrdcd());
        tv_desc.setText(m.getDesc());
        tv_frac.setText(m.getFrac());
        tv_qty.setText(m.getQty());
        tv_qty_lpp.setText(m.getQtyLPP());

        return rowView;

    }
}
