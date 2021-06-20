package com.example.quiz.model

data class Quiz(
    val quiz:String="",
    val answer: Answer,
    val correctAnswer:Int=0,
    var userAnswer:Int=-1
)
data class Answer(
    val a1:String="",
    val a2:String="",
    val a3:String="",
    val a4:String="",
    val a5:String="",
)