package com.example.adminpanel.domain.presenter

interface AddVotingFragmentContract {
    interface View{

    }

    interface Presenter{
        fun addToFireBaseVoting(question:String, variantsList: ArrayList<String>)
    }
}