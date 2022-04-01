package com.example.simpleinstagram.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleinstagram.*
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser

open class FeedFragment : Fragment() {

    lateinit var postsRecyclerView: RecyclerView
    lateinit var adapter: PostAdapter

    val allPosts: MutableList<Post> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // This is where we setup our views and click listeners

        postsRecyclerView = view.findViewById(R.id.postRecyclerView)

        // Steps to populate RecyclerView
        // 1. Create layout for each row in list (item_post.xml)
        // 2. Create data source for each row (this is the Post class)
        // 3. Create adapter that will bridge data and row layout
        // 4. Set adapter on RecyclerView
        adapter = PostAdapter(requireContext(), allPosts)
        postsRecyclerView.adapter = adapter
        // 5. Set layout manager on RecyclerView
        postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        view.findViewById<Button>(R.id.logout_button).setOnClickListener {
            val user = ParseUser.getCurrentUser()
            if (user != null){
                logoutUser(user)
            } else{
                // Print error log message
                Log.e(MainActivity.TAG, "Error while logging user out")
                // Show a toast to the user to let them know to take a picture
                Toast.makeText(requireContext(), "There has been an error when logging out.", Toast.LENGTH_SHORT).show()
            }
        }
        queryPosts()
    }
    // Query for all posts in our server
    fun logoutUser(user: ParseUser){
        ParseUser.logOut()
        val currentUser = ParseUser.getCurrentUser() // this will now be null
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
    }
    open fun queryPosts() {
        // Specify which class to query
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)

        query.include(Post.KEY_USER)
        // Returns the post in descending order based on the time they were created at: ie newer posts will appear first
        query.addDescendingOrder("createdAt");

        // Only return the most recent 20 posts
        query.setLimit(20)
        query.findInBackground(object : FindCallback<Post> {
            //  Find all Post obe
            override fun done(posts: MutableList<Post>?, e: ParseException?) {
                if (e != null) {
                    // Something went wrong
                    Log.e(TAG, "Error fetching posts")
                } else {
                    if (posts != null) {
                        for (post in posts) {
                            Log.i(TAG, "Post: " + post.getDescription() + " , username: " + post.getUser()?.username)
                        }
                        allPosts.addAll(posts)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }
    companion object{
        const val TAG = "FeedFragment"
    }
}