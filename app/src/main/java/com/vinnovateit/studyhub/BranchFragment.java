package com.vinnovateit.studyhub;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


public class BranchFragment extends Fragment {
    CardView it, cse, uc;
    ImageView insta, github, twitter;
    public static boolean CheckInternet(Context context)
    {
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return wifi.isConnected() || mobile.isConnected();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch,
                container, false);
        it = view.findViewById(R.id.it);
        cse = view.findViewById(R.id.cse);
        uc = view.findViewById(R.id.uc);
        insta = view.findViewById(R.id.instagram);
        github = view.findViewById(R.id.github);
        twitter = view.findViewById(R.id.twitter);
        insta.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/vinnovateit/?hl=en"));
            startActivity(browserIntent);
        });
        twitter.setOnClickListener(v -> {

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/v_innovate_it?lang=en"));
            startActivity(browserIntent);
        });
        github.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/vinnovateit"));
            startActivity(browserIntent);
        });
        it.setOnClickListener(view13 -> {
            if(CheckInternet(view.getContext()))
            {
                Log.i("internet","working");
                Bundle bundle = new Bundle();
                bundle.putString("branch", "/branch/it");
                bundle.putString("name", "IT");
                bundle.putString("subject", "it");
                Navigation.findNavController(requireView()).navigate(R.id.action_branchFragment_to_coursesFragment2, bundle);
            }
            else
            {
                Toast.makeText(view.getContext(), "No Internet", Toast.LENGTH_SHORT).show();
            }

        });
        cse.setOnClickListener(view12 -> {
            if(CheckInternet(view.getContext())) {
                Bundle bundle = new Bundle();
                bundle.putString("branch", "/branch/cse");
                bundle.putString("name", "CSE");
                bundle.putString("subject", "cse");
                Navigation.findNavController(requireView()).navigate(R.id.action_branchFragment_to_coursesFragment2, bundle);
            }
            else
            {
                Toast.makeText(view.getContext(), "No Internet", Toast.LENGTH_SHORT).show();
            }
        });
        uc.setOnClickListener(view1 -> {
            if(CheckInternet(view.getContext())) {

                Bundle bundle = new Bundle();
                bundle.putString("branch", "/branch/uc");
                bundle.putString("name", "U.C.");
                bundle.putString("subject", "uc");
                Navigation.findNavController(requireView()).navigate(R.id.action_branchFragment_to_coursesFragment2, bundle);
            }
            else
            {
                Toast.makeText(view.getContext(), "No Internet", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}