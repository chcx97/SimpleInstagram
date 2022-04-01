package com.example.simpleinstagram

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.simpleinstagram.fragments.ComposeFragment
import com.example.simpleinstagram.fragments.FeedFragment
import com.example.simpleinstagram.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.parse.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

/*
    Let user create a post by taking a photo with their camera
 */
class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {
            // item -> will create a variable called item
            item ->

            var fragmentToShow: Fragment? = null
            when (item.itemId){
                R.id.action_home -> {
                    // Navigate to the home screen/feed fragment
                    fragmentToShow = FeedFragment()
                }
                R.id.action_compose -> {
                    // Navigate to the compose screen
                    fragmentToShow = ComposeFragment()
                }
                R.id.action_profile -> {
                    // Navigate to the profile screen
                   fragmentToShow = ProfileFragment()
                }
            }
            if (fragmentToShow != null){
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow).commit()
            }
            // Returns true means that we've handled this user interaction on the item
            true
        }
        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.action_home

      //  queryPosts()
    }

    companion object{
        const val TAG = "MainActivity"
        const val SOME_WIDTH = 8000
    }
}


