package com.singularity.roommate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

//    private FragmentHistoryBinding binding;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Transaction> transactionList;
    private ProgressBar spinner;

    public HistoryFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        spinner = rootView.findViewById(R.id.progressBar1);
        transactionList = new ArrayList<>();
        return rootView;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
//        ViewPager2 viewPager = view.findViewById(R.id.pager);
//
//        new TabLayoutMediator(tabLayout, viewPager,
//                new TabLayoutMediator.TabConfigurationStrategy() {
//                    @Override public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
//                        tab.setText("Tab " + (position + 1));
//                    }
//                }).attach();
//    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }

    @Override
    public void onStart() {
        super.onStart();
        DatabaseReference databaseTransactions = FirebaseDatabase.getInstance().getReference(MainActivity.uid);
        databaseTransactions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                transactionList.clear();
                for (DataSnapshot transactionData : dataSnapshot.getChildren()) {
                    Transaction transaction = transactionData.getValue(Transaction.class);
                    transactionList.add(transaction);
                }

                RecyclerView transactionDisplayRecyclerView = getActivity().findViewById(R.id.transaction_display_recyclerView);
                transactionDisplayRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                transactionDisplayRecyclerView.setAdapter(new HistoryFragmentRecyclerViewAdapter(transactionList));

                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
