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
import com.parse.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

/*
    Let user create a post by taking a photo with their camera
 */
class MainActivity : AppCompatActivity() {
    val CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034
    val photoFileName = "photo.jpg"
    var photoFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Setting the description of the post
        // 2. A button to launch the camera to take a picture
        // 3. An ImageView to show the picture user has taken
        // 4. A button to save and send the post to our Parse server

        findViewById<Button>(R.id.submitBtn).setOnClickListener {
            // send post to server without an image
            // get the description that they have inputted
            val description = findViewById<EditText>(R.id.etDescription).text.toString()
            val user = ParseUser.getCurrentUser()
            if (photoFile != null){
                submitPost(description, user, photoFile!!)
            } else{
                // Print error log message
                Log.e(TAG, "Error while submitting post")
                // Show a toast to the user to let them know to take a picture
                Toast.makeText(this, "There has been an error when submitting post.", Toast.LENGTH_SHORT).show()
            }

        }

        findViewById<Button>(R.id.takePictureBtn).setOnClickListener {
            // launch camera to let user take picture
            onLaunchCamera()

        }

        findViewById<Button>(R.id.logout_button).setOnClickListener {
            val user = ParseUser.getCurrentUser()
            if (user != null){
                logoutUser(user)
            } else{
                // Print error log message
                Log.e(TAG, "Error while logging user out")
                // Show a toast to the user to let them know to take a picture
                Toast.makeText(this, "There has been an error when logging out.", Toast.LENGTH_SHORT).show()
            }
        }

        queryPosts()
    }

    fun logoutUser(user: ParseUser){
        ParseUser.logOut()
        val currentUser = ParseUser.getCurrentUser() // this will now be null
        val intent =Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
    }
    // Send a post object to our Parse server
    fun submitPost(description: String, user: ParseUser, file: File){
        // Create the Post object
        val post = Post()
        post.setDescription(description)
        post.setUser(user)
        post.setImage(ParseFile(file))
        post.saveInBackground { exception ->
            if (exception != null){
                // Something has went wrong
                Log.e(TAG, "Error while saving post")
                exception.printStackTrace()
                Toast.makeText(this, "There has been an error when saving post.", Toast.LENGTH_SHORT).show()
            } else{
                Log.i(TAG, "Successfully saved post")
                // 1. Resetting the EditText field to be empty
                // 2. Resetting the ImageView field to be empty
                val intent =Intent(this@MainActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                val takenImage = BitmapFactory.decodeFile(photoFile!!.absolutePath)
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview

                // See code above

                // See code above
                val takenPhotoUri = Uri.fromFile(getPhotoFileUri(photoFileName))
                // by this point we have the camera photo on disk
                // by this point we have the camera photo on disk
                val rawTakenImage = BitmapFactory.decodeFile(takenPhotoUri.path)
                // See BitmapScaler.java: https://gist.github.com/nesquena/3885707fd3773c09f1bb
                // See BitmapScaler.java: https://gist.github.com/nesquena/3885707fd3773c09f1bb
                val resizedBitmap: Bitmap = BitmapScaler.scaleToFitWidth(rawTakenImage, SOME_WIDTH)
                // Configure byte output stream

                // Configure byte output stream
                val bytes = ByteArrayOutputStream()
                // Compress the image further
                // Compress the image further
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes)
                // Create a new file for the resized bitmap (`getPhotoFileUri` defined above)
                // Create a new file for the resized bitmap (`getPhotoFileUri` defined above)
                val resizedFile = getPhotoFileUri(photoFileName + "_resized")
                resizedFile.createNewFile()
                val fos = FileOutputStream(resizedFile)
                // Write the bytes of the bitmap to file
                // Write the bytes of the bitmap to file
                fos.write(bytes.toByteArray())
                fos.close()

                val ivPreview: ImageView = findViewById(R.id.ivPicture)
                ivPreview.setImageBitmap(takenImage)
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun onLaunchCamera() {
        // create Intent to take a picture and return control to the calling application
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName)

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        if (photoFile != null) {
            val fileProvider: Uri =
                FileProvider.getUriForFile(this, "com.codepath.fileprovider", photoFile!!)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)

            // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
            // So as long as the result is not null, it's safe to use the intent.

            // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
            // So as long as the result is not null, it's safe to use the intent.
            if (intent.resolveActivity(packageManager) != null) {
                // Start the image capture intent to take photo
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
            }
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    fun getPhotoFileUri(fileName: String): File {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        val mediaStorageDir =
            File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG)

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(TAG, "failed to create directory")
        }

        // Return the file target for the photo based on filename
        return File(mediaStorageDir.path + File.separator + fileName)
    }
    // Query for all posts in our se

    fun queryPosts() {
        // Specify which class to query
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)

        query.include(Post.KEY_USER)
        query.findInBackground(object : FindCallback<Post>{
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
                    }
                }
            }
        })

        }
    companion object{
        const val TAG = "MainActivity"
        const val SOME_WIDTH = 8000
    }
}


