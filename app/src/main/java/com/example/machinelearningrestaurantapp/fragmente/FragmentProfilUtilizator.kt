package com.example.machinelearningrestaurantapp.fragmente

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.machinelearningrestaurantapp.MainActivity
import com.example.machinelearningrestaurantapp.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*


class FragmentProfilUtilizator : Fragment() {
    private lateinit var nume: TextInputEditText
    private lateinit var mail: TextInputEditText
    private lateinit var mobileNumber: TextInputEditText
    var userRef: DatabaseReference? = null
    private lateinit var imagineUser: ImageView
    private lateinit var btnDelogare: Button

    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private val user = FirebaseAuth.getInstance().currentUser


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_profil_utilizator, container, false)
        nume = fragmentView.findViewById(R.id.etProfilNume)
        mail = fragmentView.findViewById(R.id.etProfilEmail)
        mobileNumber = fragmentView.findViewById(R.id.etProfilNumar)
        imagineUser = fragmentView.findViewById(R.id.imageProfilUtilizator)
        imagineUser.setOnClickListener{
            launchGallery()
        }
        btnDelogare = fragmentView.findViewById(R.id.btnDelogare)
        userRef = FirebaseDatabase.getInstance().reference.child("Users")
        btnDelogare.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(context, MainActivity::class.java))
        }
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        return fragmentView
    }

    override fun onStart() {
        super.onStart()
        userRef!!.child(user!!.uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                nume.setText(snapshot.child("nume").value.toString())
                mail.setText(snapshot.child("mail").value.toString())
                mobileNumber.setText(snapshot.child("numarTelefon").value.toString())
                context?.let {
                    Glide.with(it)
                            .load(snapshot.child("imageProfile").value.toString())
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .transform(CenterCrop())
                            .circleCrop()
                            .into(imagineUser)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun updateProfile(key: String, value: String){
        userRef?.child(user!!.uid)?.child(key)?.setValue(value)
    }

    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }
            filePath = data.data
            uploadImage()
        }
    }

    private fun uploadImage(){
        if(filePath != null){
            val imageName = UUID.randomUUID().toString()
            val ref = storageReference?.child("profile/" + imageName)
            val uploadTask = ref?.putFile(filePath!!)?.addOnSuccessListener {
                ref.downloadUrl.addOnCompleteListener { task ->
                    var url = task.result.toString()
                    updateProfile("imageProfile",url)
                }
            }

        }else{
            Toast.makeText(activity, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }
    }

}