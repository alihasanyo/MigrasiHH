package mobile.indomarco.com.migrasiHH.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import mobile.indomarco.com.migrasiHH.R;
import mobile.indomarco.com.migrasiHH.model.ListTransaksiSOModel;

public class LIstTransaksiSOAdapter extends ArrayAdapter<ListTransaksiSOModel> {

    private final Activity context;
    private ArrayList<ListTransaksiSOModel> mData;


    public LIstTransaksiSOAdapter(Activity context, ArrayList<ListTransaksiSOModel> data) {
        super(context, R.layout.item_list_so_transaksi, data);
        this.context = context;
        this.mData = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_list_so_transaksi, null, true);
        rowView.setId(position);

        TextView tvkode_lokasi = rowView.findViewById(R.id.tvkodelokasi);
        TextView tvkode_produk = rowView.findViewById(R.id.tvprdcd);
        TextView tv_desc = rowView.findViewById(R.id.tvdesc);
        TextView tv_frac_qty = rowView.findViewById(R.id.tvqty_frac);
        TextView tv_avgCost = rowView.findViewById(R.id.tvavg_cost);
        TextView tv_modifBy = rowView.findViewById(R.id.tvmodify_by);
        TextView tv_modifDt = rowView.findViewById(R.id.tvmodify_dt);

        ListTransaksiSOModel m = mData.get(position);

        tvkode_lokasi.setText(m.getKodelokasi());
        tvkode_produk.setText(m.getPlu());
        tv_desc.setText(m.getDesc());
        tv_frac_qty.setText(m.getQty() + " / " + m.getFrac());
        tv_avgCost.setText(m.getAvgcost());
        tv_modifBy.setText(m.getModify_by_());
        tv_modifDt.setText(m.getModify_dt());

        return rowView;

    }
}
