package com.vinnovateit.studyhub;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class internet extends Fragment {
    private long pressedTime;

    public static boolean CheckInternet(Context context)
    {
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return wifi.isConnected() || mobile.isConnected();
    }

    public internet() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_internet, container, false);
        TextView noInt = view.findViewById(R.id.noInternet);
        TextView retry = view.findViewById(R.id.retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckInternet(view.getContext())) {
                    Navigation.findNavController(requireView()).navigateUp();}
                else{
                    noInt.setText(R.string.no_internet_connection_again);
                }
            }
        });
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if(keyCode==KeyEvent.KEYCODE_BACK && keyEvent.getAction()==KeyEvent.ACTION_UP){
                    if(CheckInternet(view.getContext())) {
                    Navigation.findNavController(requireView()).navigateUp();
                    return true;}
                    else{
                        noInt.setText(R.string.no_internet_connection_again);
                        if (pressedTime + 3000 > System.currentTimeMillis()) {
                            getActivity().finish();
                            return true;
                        }
                        else{
                            Toast.makeText(getContext(),"Press back again to exit!",Toast.LENGTH_SHORT).show();
                            pressedTime=System.currentTimeMillis();
                            return true;
                        }
                    }
                }
                return false;
            }
        });
        return view;
    }
}