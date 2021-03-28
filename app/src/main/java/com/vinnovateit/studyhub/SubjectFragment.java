package com.vinnovateit.studyhub;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class SubjectFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subject,
                container, false);
        TextView t=view.findViewById(R.id.textView5);
        Bundle bundle = this.getArguments();
        assert bundle != null;
        String descUrl = bundle.getString("detailsURL");

        try{
        //String uri = "https://studyhub.vinnovateit.com/courses/603f82d34b48f40004358e53";
            String uri = descUrl;
        Document doc = (Document) Jsoup.connect(uri).get();
        Elements data = doc.select("div.note");
        Elements p = data.select("p");
        CardView c=view.findViewById(R.id.notescard);
        String d = "";
        for (Element x : p)
        {
            d += d + x.text();
            t.setText(t.getText()+"\n"+x.text());
            t.setText(t.getText()+"\n"+x.select("a").attr("abs:href"));
        }
        if(d.equals(""))
        {
            c.setVisibility(View.GONE);
        }

    }catch (Exception e) {
        e.printStackTrace();
    }
        return view;
    }
}