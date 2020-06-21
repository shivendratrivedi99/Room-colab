package com.singularity.roommate;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


public class AddFragment extends Fragment {
    private Double totalAmount;
    private ArrayList<String> members = new ArrayList<>();
    private int memberCount = 0;
    private Double sum;
    private DatabaseReference databaseTransactions;
    private View rootViewAdd;
    private String[] allMembers = {"Ankul", "Ankur", "Pranav", "Shivendra"};

    SharedPreferences preferences;
    private ConstraintLayout constraintLayout;
    SharedPreferences.Editor editor;


    public AddFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootViewAdd = inflater.inflate(R.layout.fragment_add, container, false);
        ProgressBar spinner = rootViewAdd.findViewById(R.id.add_fragment_progressbar);

        constraintLayout = rootViewAdd.findViewById(R.id.add_fragment);
        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("settings", MODE_PRIVATE);
        constraintLayout.setBackgroundResource(preferences.getInt("gradientColor", R.drawable.color_gradient_green));
        Toast.makeText(getActivity(), "Color fetched from saved preferences", Toast.LENGTH_SHORT).show();

//        databaseTransactions = FirebaseDatabase.getInstance().getReference("transactions");
        databaseTransactions = FirebaseDatabase.getInstance().getReference(MainActivity.uid);
        setOnClickOnColorButtons();
        Button submit = rootViewAdd.findViewById(R.id.submit);

        spinner.setVisibility(View.GONE);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAmount();
                getMembers();
                calculateIndividualShare();
                addToDatabase();
                moveToHistoryFragment();
//                add data to table after calculation
            }
        });

        return rootViewAdd;
    }

    private void moveToHistoryFragment() {
        ViewPager2 vp = Objects.requireNonNull(getActivity()).findViewById(R.id.pager);
        vp.setCurrentItem(1);
    }

    private void setOnClickOnColorButtons() {
        editor = preferences.edit();
        Button b1 = rootViewAdd.findViewById(R.id.set_fire_color);
        Button b2 = rootViewAdd.findViewById(R.id.set_smooth_color);
        Button b3 = rootViewAdd.findViewById(R.id.set_ice_color);
        Button b4 = rootViewAdd.findViewById(R.id.set_grass_color);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            b1.setVisibility(View.VISIBLE);
            b2.setVisibility(View.VISIBLE);
            b3.setVisibility(View.VISIBLE);
            b4.setVisibility(View.VISIBLE);

        } else {

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    constraintLayout.setBackgroundResource(R.drawable.color_gradient_red);
                    editor.putInt("gradientColor", R.drawable.color_gradient_red);
                    editor.apply();
                }
            });

            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    constraintLayout.setBackgroundResource(R.drawable.color_gradient_white);
                    editor.putInt("gradientColor", R.drawable.color_gradient_white);
                    editor.commit();
                }
            });

            b3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    constraintLayout.setBackgroundResource(R.drawable.color_gradient_blue);
                    editor.putInt("gradientColor", R.drawable.color_gradient_blue);
                    editor.commit();
                }
            });

            b4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    constraintLayout.setBackgroundResource(R.drawable.color_gradient_green);
                    editor.putInt("gradientColor", R.drawable.color_gradient_green);
                    editor.commit();
                }
            });
        }
    }

    private void addToDatabase() {
        Long time = System.currentTimeMillis() / 1000;
        Log.e("message", String.valueOf(time));
        String id = databaseTransactions.push().getKey();

        Transaction transaction = new Transaction(id, totalAmount, sum, memberCount, members);
        assert id != null;
        databaseTransactions.child(id).setValue(transaction);
        Toast.makeText(getActivity(), "Transaction completed", Toast.LENGTH_LONG).show();
        resetVariables();
    }

    private void resetVariables() {
        totalAmount = 0.0;
        members.clear();
        memberCount = 0;
        sum = 0.0;
    }

    private void calculateIndividualShare() {
        sum = totalAmount / memberCount;
    }

    private void getMembers() {

        CheckBox a0 = rootViewAdd.findViewById(R.id.c0);
        if (a0.isChecked()) {
            members.add(allMembers[0]);
            memberCount++;
        }

        CheckBox a1 = rootViewAdd.findViewById(R.id.c1);
        if (a1.isChecked()) {
            members.add(allMembers[1]);
            memberCount++;
        }

        CheckBox a2 = rootViewAdd.findViewById(R.id.c2);
        if (a2.isChecked()) {
            members.add(allMembers[2]);
            memberCount++;
        }

        CheckBox a3 = rootViewAdd.findViewById(R.id.c3);
        if (a3.isChecked()) {
            members.add(allMembers[3]);
            memberCount++;
        }

    }

    private void getAmount() {
        TextView amount = rootViewAdd.findViewById(R.id.amount);
        totalAmount = Double.parseDouble(amount.getText().toString());
    }

}
