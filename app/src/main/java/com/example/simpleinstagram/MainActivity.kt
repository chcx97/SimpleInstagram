package com.example.simpleinstagram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseQuery

/*
    Let user create a post by taking a photo with their camera
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        queryPosts()
    }
    // Query for all posts in our server

    fun queryPosts(){}
    // Specify which class to query
    val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
    query.getInBackground(object: FindCallback<Post>{
        override fun done(objects:MutableList<Post>?, e: ParseException){

        }
    }

}