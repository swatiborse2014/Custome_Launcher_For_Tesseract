package com.example.custome_launcher_for_tesseract.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.custome_launcher_for_tesseract.R;
import com.example.custome_launcher_for_tesseract.model.AppDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder> implements Filterable {
    private Context context;
    private LayoutInflater mInflater;
    private List<AppDetails> appDetailsSearch;
    private List<AppDetails> appDetails;

    public AppListAdapter(Context context, ArrayList<AppDetails> appDetail) {
        this.context = context;
        appDetails = appDetail;
        appDetailsSearch = appDetail;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public AppListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = mInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(listItem);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AppListAdapter.ViewHolder holder, final int position) {
        AppDetails appDetails1 = appDetails.get(position);
        holder.appIcons.setImageDrawable(appDetails1.getIcon());
        holder.appNames.setText(appDetails1.getAppName());
        holder.appPkgName.setText("Package :" + appDetails1.getPackageName());
        holder.version_code.setText("Version Code :" + appDetails1.getVersionCode());
        holder.version_name.setText("Version Name :" + appDetails1.getVersionName());
        holder.activity_name.setText("Activity Name :" + appDetails1.getMainActivityName());

        Collections.sort(appDetails, new Comparator<AppDetails>() {
            @Override
            public int compare(AppDetails lhs, AppDetails rhs) {
                return lhs.getAppName().compareTo(rhs.getAppName());
            }
        });

        holder.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(appDetails.get(position).packageName);
                if (launchIntent != null) {
                    context.startActivity(launchIntent);
                } else {
                    Toast.makeText(context, "There is no package available in android", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return appDetails.size();
    }

    @Override
    public Filter getFilter() {
        return customFilter;
    }

    private Filter customFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            String filterString = constraint.toString().toLowerCase();

            final List<AppDetails> list = appDetails;

            int count = list.size();
            final ArrayList<AppDetails> nlist = new ArrayList<AppDetails>(count);

            AppDetails filterableCompany;

            for (int i = 0; i < count; i++) {
                filterableCompany = list.get(i);
                if (filterableCompany.getAppName().toLowerCase().contains(filterString)) {
                    nlist.add(filterableCompany);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            appDetailsSearch = (ArrayList<AppDetails>) results.values;
            notifyDataSetChanged();
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView appIcons;
        public TextView appPkgName;
        public TextView appNames;
        public TextView activity_name;
        public TextView version_name;
        public TextView version_code;
        public RelativeLayout relative;

        public ViewHolder(View listItem) {
            super(listItem);
            this.appIcons = (ImageView) listItem.findViewById(R.id.app_icon);
            this.appPkgName = (TextView) listItem.findViewById(R.id.app_package_name);
            this.appNames = (TextView) listItem.findViewById(R.id.app_name);
            this.relative = (RelativeLayout) listItem.findViewById(R.id.relative);
            this.version_code = (TextView) listItem.findViewById(R.id.version_code);
            this.version_name = (TextView) listItem.findViewById(R.id.version_name);
            this.activity_name = (TextView) listItem.findViewById(R.id.activity_name);
        }
    }
}
