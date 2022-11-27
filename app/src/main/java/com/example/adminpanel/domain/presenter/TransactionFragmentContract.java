package com.example.adminpanel.domain.presenter;

import com.example.adminpanel.domain.entities.Operation;
import com.example.adminpanel.domain.entities.Person;

import java.util.List;

import kotlinx.coroutines.flow.Flow;

public interface TransactionFragmentContract {
    interface Presenter{
        Flow<List<Operation>> getOperationList();
    }

    interface View{
        //void getUserList();
    }
}
