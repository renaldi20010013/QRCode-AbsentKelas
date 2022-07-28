package com.example.uasrungkad

data class Response (
    val status : Int,
    val message : String,
    val data : DataMahasiswa? = null
)