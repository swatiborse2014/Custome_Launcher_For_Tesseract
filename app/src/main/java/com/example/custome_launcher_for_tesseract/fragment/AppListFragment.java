package com.example.custome_launcher_for_tesseract.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.custome_launcher_for_tesseract.R;
import com.example.custome_launcher_for_tesseract.adapter.AppListAdapter;
import com.example.custome_launcher_for_tesseract.model.AppDetails;

import java.util.ArrayList;
import java.util.List;

public class AppListFragment extends Fragment {

    private ArrayList<AppDetails> apps;
    private PackageInfo pInfo;
    private AppListAdapter appListAdapter;

    private PackageManager packageManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        packageManager = getActivity().getPackageManager();

        appListAdapter = new AppListAdapter(getActivity(),  apps);
        loadApplication();
        loadListView(view);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void loadListView(View view) {
        RecyclerView appsRecycle = (RecyclerView) view.findViewById(R.id.apps_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        appsRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        appsRecycle.setHasFixedSize(true);
        appsRecycle.addItemDecoration(new DividerItemDecoration(appsRecycle.getContext(), DividerItemDecoration.VERTICAL));

        AppListAdapter appListAdapter = new AppListAdapter(getActivity(), apps);
        appsRecycle.setAdapter(appListAdapter);
        appListAdapter.notifyDataSetChanged();
    }

    private void loadApplication() {

        PackageManager manager = getActivity().getPackageManager();
        apps = new ArrayList<>();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        try {
            pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);
        for (ResolveInfo ri : availableActivities) {
            AppDetails app = new AppDetails();
            app.appName = String.valueOf(ri.loadLabel(manager));
            app.packageName = ri.activityInfo.packageName;
            app.icon = ri.activityInfo.loadIcon(manager);
            app.versionCode = String.valueOf(pInfo.versionCode);
            app.versionName = pInfo.versionName;
            app.MainActivityName = ri.activityInfo.targetActivity;
            apps.add(app);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(onQueryTextListener()); // text changed listener
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
    }

    private SearchView.OnQueryTextListener onQueryTextListener() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                appListAdapter.getFilter().filter(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)) {
                    Toast.makeText(getActivity(), "No words found.", Toast.LENGTH_SHORT).show();
                } else {
                    appListAdapter.getFilter().filter(s);
                }

                return true;
            }
        };
    }
}
