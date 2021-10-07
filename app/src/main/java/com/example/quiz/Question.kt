package com.example.quiz

data class Question(val text : String,
                    val correct : Int,
                    val a : String,
                    val b : String,
                    val c : String,
                    val d : String,
                    val image : String? = null){}