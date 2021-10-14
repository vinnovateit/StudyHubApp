package com.vinnovateit.studyhub;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class BranchFragment extends Fragment {
    CardView it, cse, uc,firstsem, mech,eee;
    ImageView insta, github, twitter;
    private EditText searchView;
    private long pressedTime;

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    public void hideKeyboard() {
        // Check if no view has focus:
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public boolean isURLReachable(Context context) {
        if(CheckInternet(context)) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                try {
                    URL url = new URL("https://studyhub.vinnovateit.com");
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(10 * 1000);          // 10 s.
                    urlc.connect();
                    if (urlc.getResponseCode() == 200) {        // 200 = "OK" code (http connection is fine).
                        Log.wtf("Connection", "Success !");
                        return true;
                    } else {
                        return false;
                    }
                }catch (MalformedURLException e1) {
                    return false;
                }catch (IOException e) {
                    return false;
                }
            }
            return false;
        }
        return true;

    }
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
        if(isURLReachable(getContext())) {

            it = view.findViewById(R.id.it);
            cse = view.findViewById(R.id.cse);
            uc = view.findViewById(R.id.uc);
            eee = view.findViewById(R.id.eee);
            firstsem = view.findViewById(R.id.firstsem);
            mech = view.findViewById(R.id.mech);



            searchView=(EditText) view.findViewById(R.id.searchView);

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

            firstsem.setOnClickListener(view16 -> {
                if (CheckInternet(view.getContext())) {
                    ConstraintLayout layout = getActivity().findViewById(R.id.progress);
                    layout.setVisibility(View.VISIBLE);
                    //Diag.showSimpleProgressDialog(getContext(),"STUDY HUB","Loading",true);
                    //    Log.i("internet", "working");
                    Bundle bundle = new Bundle();
                    bundle.putString("branch", "/branch/firstsem");
                    bundle.putString("name", "SEM 1");
                    bundle.putString("subject", "firstsem");
                    Navigation.findNavController(requireView()).navigate(R.id.action_branchFragment_to_coursesFragment2, bundle);
                } else {
                    Navigation.findNavController(requireView()).navigate(R.id.action_branchFragment_to_internet);
                }

            });

            mech.setOnClickListener(view15 -> {
                if (CheckInternet(view.getContext())) {
                    ConstraintLayout layout = getActivity().findViewById(R.id.progress);
                    layout.setVisibility(View.VISIBLE);
                    //Diag.showSimpleProgressDialog(getContext(),"STUDY HUB","Loading",true);
                    //    Log.i("internet", "working");
                    Bundle bundle = new Bundle();
                    bundle.putString("branch", "/branch/mech");
                    bundle.putString("name", "MECH");
                    bundle.putString("subject", "mech");
                    Navigation.findNavController(requireView()).navigate(R.id.action_branchFragment_to_coursesFragment2, bundle);
                } else {
                    Navigation.findNavController(requireView()).navigate(R.id.action_branchFragment_to_internet);
                }

            });

            eee.setOnClickListener(view14 -> {
                if (CheckInternet(view.getContext())) {
                    ConstraintLayout layout = getActivity().findViewById(R.id.progress);
                    layout.setVisibility(View.VISIBLE);
                    //Diag.showSimpleProgressDialog(getContext(),"STUDY HUB","Loading",true);
                    //    Log.i("internet", "working");
                    Bundle bundle = new Bundle();
                    bundle.putString("branch", "/branch/eee");
                    bundle.putString("name", "EEE");
                    bundle.putString("subject", "eee");
                    Navigation.findNavController(requireView()).navigate(R.id.action_branchFragment_to_coursesFragment2, bundle);
                } else {
                    Navigation.findNavController(requireView()).navigate(R.id.action_branchFragment_to_internet);
                }

            });

            it.setOnClickListener(view13 -> {
                if (CheckInternet(view.getContext())) {
                    ConstraintLayout layout = getActivity().findViewById(R.id.progress);
                    layout.setVisibility(View.VISIBLE);
                    //Diag.showSimpleProgressDialog(getContext(),"STUDY HUB","Loading",true);
                    //    Log.i("internet", "working");
                    Bundle bundle = new Bundle();
                    bundle.putString("branch", "/branch/it");
                    bundle.putString("name", "IT");
                    bundle.putString("subject", "it");
                    Navigation.findNavController(requireView()).navigate(R.id.action_branchFragment_to_coursesFragment2, bundle);
                } else {
                    Navigation.findNavController(requireView()).navigate(R.id.action_branchFragment_to_internet);
                }

            });


            cse.setOnClickListener(view12 -> {
                if (CheckInternet(view.getContext())) {
                    ConstraintLayout layout = getActivity().findViewById(R.id.progress);
                    layout.setVisibility(View.VISIBLE);
                    //Diag.showSimpleProgressDialog(getContext(),"STUDY HUB","Loading",true);
                    Bundle bundle = new Bundle();
                    bundle.putString("branch", "/branch/cse");
                    bundle.putString("name", "CSE");
                    bundle.putString("subject", "cse");
                    Navigation.findNavController(requireView()).navigate(R.id.action_branchFragment_to_coursesFragment2, bundle);
                } else {
                    Navigation.findNavController(requireView()).navigate(R.id.action_branchFragment_to_internet);
                }
            });


            uc.setOnClickListener(view1 -> {
                if (CheckInternet(view.getContext())) {
                    ConstraintLayout layout = getActivity().findViewById(R.id.progress);
                    layout.setVisibility(View.VISIBLE);
                    Bundle bundle = new Bundle();
                    bundle.putString("branch", "/branch/uc");
                    bundle.putString("name", "U.C.");
                    bundle.putString("subject", "uc");
                    Navigation.findNavController(requireView()).navigate(R.id.action_branchFragment_to_coursesFragment2, bundle);
                } else {
                    Navigation.findNavController(requireView()).navigate(R.id.action_branchFragment_to_internet);
                }
            });

            searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
            searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i == EditorInfo.IME_ACTION_SEARCH) {
                        String text = searchView.getText().toString();
                        if (text.equals("")) {
                            //Toast.makeText(getContext(), "Sorry", Toast.LENGTH_SHORT).show();
                            searchView.setError("Cannot be empty");
                        } else {
                            hideKeyboard();
                            if(CheckInternet(view.getContext())) {
                                ConstraintLayout layout = getActivity().findViewById(R.id.progress);
                                layout.setVisibility(View.VISIBLE);
                                Bundle bundle = new Bundle();
                                bundle.putString("search", text);

                                Navigation.findNavController(requireView()).navigate(R.id.action_branchFragment_to_searchFragment, bundle);
                                searchView.setText("");
                            }
                            else {
                                Navigation.findNavController(requireView()).navigate(R.id.action_branchFragment_to_internet);
                            }
                        }
                        return true;
                    }
                    return false;

                }

            });

            view.setFocusableInTouchMode(true);
            view.requestFocus();
            view.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
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
                    return false;
                }
            });
        }
        else
        {
            Toast.makeText(getContext(), "Server is currently down!", Toast.LENGTH_SHORT).show();
        }

        return view;
    }


}