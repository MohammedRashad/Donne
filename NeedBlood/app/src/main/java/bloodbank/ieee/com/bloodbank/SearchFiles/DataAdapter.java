package bloodbank.ieee.com.bloodbank.SearchFiles;

/**
 * .
 * Created by rashad on 6/10/16.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import bloodbank.ieee.com.bloodbank.R;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataObjectHolder> {

    private ArrayList<DataObject> mDataset;
    private static MyClickListener myClickListener;

    /////////////////////////////////////////Constructor////////////////////////////////////////////////////////

    public DataAdapter(ArrayList<DataObject> myDataset) {
        mDataset = myDataset;
    }


    /////////////////////////////////////////Subclass////////////////////////////////////////////////////////

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, email, facebook, blood,phone;

        public DataObjectHolder(View itemView) {

            super(itemView);

            name = (TextView) itemView.findViewById(R.id.textView);
            email = (TextView) itemView.findViewById(R.id.textView2);
            facebook = (TextView) itemView.findViewById(R.id.textView3);
            blood = (TextView) itemView.findViewById(R.id.textView4);
            phone = (TextView) itemView.findViewById(R.id.textView5);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            myClickListener.onItemClick(getPosition(), v);

        }
    }


    ///////////////////////////////////////////Methods/////////////////////////////////////////////////////////

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);

        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        holder.name.setText(mDataset.get(position).getmText1());
        holder.email.setText(mDataset.get(position).getmText2());
        holder.facebook.setText(mDataset.get(position).getmText3());
        holder.blood.setText(mDataset.get(position).getmText4());
        holder.phone.setText(mDataset.get(position).getmText5());

    }

    public void swapItems(ArrayList<DataObject> list) {

        this.mDataset = list;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {

        return mDataset.size();

    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

}