package com.example.roomdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "User")
data class User(
    @PrimaryKey(autoGenerate = true) var id : Int,
    var Name : String,
    var Designation : String,
    var Company : String
):Serializable {}