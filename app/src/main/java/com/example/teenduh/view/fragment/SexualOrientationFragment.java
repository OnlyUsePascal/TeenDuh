package com.example.teenduh.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.teenduh.Adapter.listOrientationAdapter;
import com.example.teenduh.R;
import com.example.teenduh.util.ListOfSexualOrientation;
import com.example.teenduh.util.Util;
import com.example.teenduh.view.activity.SignUpPage;

import java.util.ArrayList;
import java.util.List;

public class SexualOrientationFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    TextView next;
    SignUpPage signUpPage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sexual_orientation, container, false);
        signUpPage = (SignUpPage) getActivity();
        recyclerView = view.findViewById(R.id.recyclerView);
        next = view.findViewById(R.id.nextButton);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        List<ListOfSexualOrientation> listOrientation = new ArrayList<>();
        listOrientation.add(new ListOfSexualOrientation("Straight",false));
        listOrientation.add(new ListOfSexualOrientation("Gay", false));
        listOrientation.add(new ListOfSexualOrientation("Lesbian", false));
        listOrientation.add(new ListOfSexualOrientation("Bisexual", false));
        listOrientation.add(new ListOfSexualOrientation("Asexual", false));
        listOrientation.add(new ListOfSexualOrientation("Pansexual", false));
        listOrientation.add(new ListOfSexualOrientation("Demisexual", false));
        listOrientation.add(new ListOfSexualOrientation("Queer", false));
        listOrientation.add(new ListOfSexualOrientation("Questioning", false));
        listOrientationAdapter adapter = new listOrientationAdapter(listOrientation, view);
        recyclerView.setAdapter(adapter);

        next.setOnClickListener(v -> {
            ArrayList<String> sexualOrientation = new ArrayList<>();
            for(int i = 0; i < listOrientation.size(); i++){
                if(listOrientation.get(i).isSelected()){
                    System.out.println(listOrientation.get(i).getName());
                    sexualOrientation.add(listOrientation.get(i).getName());
                }
            }
            //Store sexual orientation to user
            Util.curUser.setSexualOrientationList(sexualOrientation);
            // Go to next fragment
            signUpPage.replaceFragment(new InterestGender());

        });
        return view;
    }
}