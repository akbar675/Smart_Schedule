package com.kelompoksembilan.smartschedule;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.kelompoksembilan.smartschedule.MyScheduleActivity;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {
    private Context context;
    private List<DatabaseHelper.Schedule> scheduleList;
    private OnItemClickListener mListener;
    DatabaseHelper scheduleDB;

    public interface OnItemClickListener{
        void onDeleteClick(int position);
        void onEditClick(int position);
        void onFavouriteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder{
        public TextView TITLE;
        public TextView DATE;
        public TextView TIME;
        public TextView PLACE;

        public ImageView delete;
        public ImageView favourite;
        public ImageView edit;

        public ScheduleViewHolder(View view, final OnItemClickListener listener){
            super(view);
            TITLE = view.findViewById(R.id.txt_title);
            DATE = view.findViewById(R.id.txt_date);
            TIME = view.findViewById(R.id.txt_time);
            PLACE = view.findViewById(R.id.txt_place);
            delete = view.findViewById(R.id.image_delete);
            favourite = view.findViewById(R.id.image_favourite);
            edit = view.findViewById(R.id.image_edit);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener !=null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onEditClick(position);
                        }
                    }
                }
            });
            favourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onFavouriteClick(position);
                        }
                    }
                }
            });
        }
    }

    public ScheduleAdapter(Context context, List<DatabaseHelper.Schedule> scheduleList){
        this.context = context;
        this.scheduleList = scheduleList;
    }


    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_list_view, viewGroup, false);

        return new ScheduleViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, int position) {
        DatabaseHelper.Schedule schedule = scheduleList.get(position);

        holder.TITLE.setText(schedule.getTITLE());
        holder.DATE.setText(schedule.getDATE());
        holder.TIME.setText(schedule.getTIME());
        holder.PLACE.setText(schedule.getPLACE());
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }
}
