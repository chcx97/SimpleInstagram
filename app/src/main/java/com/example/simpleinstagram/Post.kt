package com.example.simpleinstagram

import com.parse.*

// Description: String
// Image: File
// User: User

@ParseClassName("Post") //this is from back4app, the Post Class name
class Post: ParseObject() {
    fun getDescription():String?{
        // getString is a special method used as part of a parse object
        // this gets the appropiate column that we want
        return getString(KEY_DESCRIPTION)
    }

    fun setDescription(description: String){
        // put is a special method of parse object that puts the
        // key coming from the parameters and place it inside the description column in back4app
        put(KEY_DESCRIPTION, description)
    }

    fun getImage():ParseFile?{
        return getParseFile(KEY_IMAGE)
    }

    fun setImage(parsefile: ParseFile){
        put(KEY_DESCRIPTION,parsefile)
    }

    fun getUser(): ParseUser?{
        return getParseUser(KEY_USER)
    }
    fun setUser(user: ParseUser){
        put(KEY_USER,user)
    }
    companion object{
        // the string values are from the columns in back4app
        const val KEY_DESCRIPTION = "description"
        const val KEY_IMAGE = "image"
        const val KEY_USER = "user"

    }
}