package com.example.foodonate.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodonate.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class firebaseViewModel(application: Application) : AndroidViewModel(application) {
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var db = Firebase.firestore.collection("Users")
    var storageReference = Firebase.storage.reference
    private var currentUser = MutableLiveData<UserModel>(UserModel("","","","",""))
    var user = UserModel()
    fun user(): LiveData<UserModel> {
        return currentUser
    }
    fun UpdateUserImage(imageUrl : String) = CoroutineScope(Dispatchers.IO).launch {
        Log.d("@@Inside","HERE ${imageUrl}")
        try {
            val personQuery = db
                .whereEqualTo("uid",auth.currentUser?.uid)
                .get()
                .await()
            Log.d("@@Inside0","${personQuery.documents}")
            if (personQuery.documents.isNotEmpty()) {
                Log.d("@@Inside1","HERE")
                try {
                    for (Doc in personQuery) {
                        Log.d("@@Inside2","HERE ${imageUrl}")
                        db.document(Doc.id).update("profileUrl" , imageUrl).await()
                        Log.d("@@Save", "${imageUrl}")
                    }
                    currentUser = MutableLiveData(user)
                } catch (e: Exception) {
                    Log.e("@@FireBaseModel","Error",e)
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Log.d("@@@@", e.message!!)
            }
        }

    }
    fun getCurrentUser() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("@@firebaseViewModel","Here1")
            db.get().addOnSuccessListener{
                val list : List<DocumentSnapshot> = it.documents
                Log.d("@@firebaseViewModel","Here2")
                for(currUser in list){
                    val User : UserModel? = currUser.toObject(UserModel::class.java)
                    Log.d("@@firebaseViewModel","Here3")
                    if(User != null && User.uid == auth.currentUser?.uid){
                        Log.d("UserDataFetch","inside if")
                        Log.d("UserDataFetch",User?.uid.toString())
                        user = User
                        currentUser.postValue(User!!)
                        Log.d("@@btnFollow" , user.toString())
                        break
                    }
                }
            }.addOnFailureListener {
                Log.d("@@firebaseViewModel","Error",it)
            }
        }
    }
}