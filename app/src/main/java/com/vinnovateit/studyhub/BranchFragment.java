package com.vinnovateit.studyhub;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;


public class BranchFragment extends Fragment {
    CardView it,cse,uc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch,
                container, false);
        it=view.findViewById(R.id.it);
        cse=view.findViewById(R.id.cse);
        uc=view.findViewById(R.id.uc);
        it.setOnClickListener(view13 -> {
            Bundle bundle = new Bundle();
            bundle.putString("branch","/branch/it");
            bundle.putString("name","IT");
            Navigation.findNavController(requireView()).navigate(R.id.action_branchFragment_to_coursesFragment2,bundle);
        });
        cse.setOnClickListener(view12 -> {
            Bundle bundle = new Bundle();
            bundle.putString("branch","/branch/cse");
            bundle.putString("name","CSE");
            Navigation.findNavController(requireView()).navigate(R.id.action_branchFragment_to_coursesFragment2,bundle);
        });
        uc.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("branch","/branch/uc");
            bundle.putString("name","U.C.");
            Navigation.findNavController(requireView()).navigate(R.id.action_branchFragment_to_coursesFragment2,bundle);
        });
        return view;
    }

}