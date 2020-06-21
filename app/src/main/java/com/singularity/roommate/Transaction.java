package com.singularity.roommate;

import java.util.ArrayList;

public class Transaction {
    private Long time;
    private String id;
    private Double totalAmount;
    private Double eachAmount;
    private int memberCount;
    private ArrayList<String> members = new ArrayList<>();

    public Transaction(){
    }

    Transaction(String id, Double totalAmount, Double eachAmount, int memberCount, ArrayList<String> members) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.eachAmount = eachAmount;
        this.memberCount = memberCount;
        this.members = members;
    }

    public Long getTime() {
        return time;
    }

    public String getId() {
        return id;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public Double getEachAmount() {
        return eachAmount;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public ArrayList<String> getMembers() {
        return members;
    }
}
