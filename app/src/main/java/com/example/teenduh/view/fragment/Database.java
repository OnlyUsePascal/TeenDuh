package com.example.teenduh.view.fragment;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.teenduh.R;
import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh.model.TicketForm;
import com.example.teenduh.view.activity.MainLayout;
import com.example.teenduh.view.activity.admin.AdminViewUserActivity;
import com.example.teenduh.view.adapter.ItemSpacingDecoration;
import com.example.teenduh.view.adapter.TicketFormAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Database#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Database extends Fragment {

    private Button buttonViewUser;
    private CardView cardViewUser, cardViewStatistic, cardViewReport;
    private MainLayout mActivity;
    private RecyclerView recyclerView;
    private TicketFormAdapter ticketFormAdapter;
    private List<TicketForm> ticketFormList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_database, container, false);
        cardViewUser = view.findViewById(R.id.cardView1);
        cardViewStatistic = view.findViewById(R.id.cardView2);
        cardViewReport = view.findViewById(R.id.cardView3);
        mActivity = (MainLayout) getActivity();

        cardViewUser.setOnClickListener(v -> {
            AndroidUtil._startActivity(getContext(), AdminViewUserActivity.class);
        });

        cardViewStatistic.setOnClickListener(v -> {
            mActivity.changeFragment(new Statistic(),5);
        });
        cardViewReport.setOnClickListener(v -> {
            mActivity.changeFragment(new Report(),6);
        });
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new ItemSpacingDecoration(50));

        ticketFormList = new ArrayList<TicketForm>();

        ticketFormList.add(new TicketForm("John Doe", "1","12/12/2021","I'm being bullied"));
        ticketFormList.add(new TicketForm("John Doe", "2","12/12/2021","I'm being bullied"));
        ticketFormList.add(new TicketForm("John Doe", "3","12/12/2021","I'm being bullied"));
        ticketFormList.add(new TicketForm("John Doe", "4","12/12/2021","I'm being bullied"));

        TicketFormAdapter ticketFormAdapter = new TicketFormAdapter(ticketFormList);
        recyclerView.setAdapter(ticketFormAdapter);





        return view;
    }

}