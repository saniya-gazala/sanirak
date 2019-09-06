package com.example.m.myapplication.Interface;

import com.example.m.myapplication.Model.Plants;

import java.util.List;

public interface IFireStoreLoadDone {

    void onFireStoreLoadSuccess(List<Plants> plants);
    void onFireStoreLoadFailed(String message);
}
