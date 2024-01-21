package com.example.teenduh.view.adapter.discovery;


import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

import com.example.teenduh.model.User;

public class CardStackCallback extends DiffUtil.Callback {

    private List<User> old, baru;

    public CardStackCallback(List<User> usersOld, List<User> usersNew) {
        this.old = usersOld;
        this.baru = usersNew;
    }

    @Override
    public int getOldListSize() {
        return old.size();
    }

    @Override
    public int getNewListSize() {
        return baru.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
        // return old.get(oldItemPosition).get_tempImg() == baru.get(newItemPosition).get_tempImg();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
        // return old.get(oldItemPosition) == baru.get(newItemPosition);
    }
}