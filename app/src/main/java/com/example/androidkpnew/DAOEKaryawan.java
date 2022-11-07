package com.example.androidkpnew;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOEKaryawan {
    private DatabaseReference databaseReference;
    public DAOEKaryawan(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Karyawan.class.getSimpleName());
    }

    public Task<Void> add(Karyawan orang){
        return databaseReference.push().setValue(orang);
    }
}
