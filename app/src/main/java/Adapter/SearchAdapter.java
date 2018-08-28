package Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.micke.weatherapp.R;

import java.util.List;

import model.city;

class SearchViewHolder extends RecyclerView.ViewHolder {

    public TextView cityName;


    public SearchViewHolder(View itemView) {
        super(itemView);
        cityName = (TextView)itemView.findViewById(R.id.cityName);


    }
}

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder>{

    private Context context;
    List<city> cities;

    public SearchAdapter(Context context, List<city> cities){
        this.context = context;
        this.cities = cities;


    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.layout_item,parent,true);
        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {

        holder.cityName.setText(cities.get(position).getCityName());

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
