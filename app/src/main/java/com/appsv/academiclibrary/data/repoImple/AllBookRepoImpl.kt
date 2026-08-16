package com.appsv.academiclibrary.data.repoImple

import com.appsv.academiclibrary.domain.repo.AllBookRepo
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class AllBookRepoImpl @Inject constructor(val firebaseDatabase: FirebaseDatabase) : AllBookRepo {

}