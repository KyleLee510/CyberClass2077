package com.example.cyberclass2077.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cyberclass2077.R;

public   class MutiChoiceAdapter extends RecyclerView.Adapter<MutiChoiceAdapter.MyViewHolder> {


    private String[] tag_content;

    public interface OnItemClikListener
    {
        void onItemClick(MyViewHolder viewHolder, int position);
    }
     OnItemClikListener onItemClikListener ;

    private SparseArray<Boolean> sparseArray ;

    public void setSparseArray(SparseArray sparseArray) {
        this.sparseArray = sparseArray;
    }

    public void setOnItemClikListener(OnItemClikListener onItemClikListener) {
        this.onItemClikListener = onItemClikListener;
    }

    private Context context ;

    public MutiChoiceAdapter(Context context)
    {
        this.context = context ;
        sparseArray = new SparseArray<>();
        tag_content = context.getResources().getStringArray(R.array.tag);//绑定tag_content
    }
    public  class  MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView text_check ;
        public ImageView imageView ;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            text_check = (TextView) itemView.findViewById(R.id.multiply_selection_text_check);
            imageView = (ImageView)itemView.findViewById(R.id.multiply_selection_image_check);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.multiple_selection_dialog_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final  int pos = holder.getLayoutPosition();
        holder.text_check.setText(tag_content[position]);
        if(sparseArray.get(pos)!=null) {
            holder.imageView.setSelected(sparseArray.get(pos));
        }else{
            holder.imageView.setSelected(false);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClikListener != null)
                {
                    onItemClikListener.onItemClick(holder,pos);
                }
            }
        });
    }


//显示tag数量
    @Override
    public int getItemCount() {
        return tag_content.length;
    }


}