package com.example.quiz

import com.example.quiz.model.Answer
import com.example.quiz.model.Quiz

object QuizRepo {
   private val quiz:MutableList<Quiz> = arrayListOf(
        Quiz("что такое Java?", Answer("Мотоцикл","Остров","Сигареты","Язык программирования","Чай"),3),
        Quiz("Какой модификатор доступа необходимо использовать, чтобы переменная была видна везде?", Answer("default (package visible)","public","private","protected","Open"),1),
        Quiz("Какой модификатор доступа необходимо использовать, чтобы переменная была видна только в текущем классе?", Answer("default (package visible)","public","private","protected","Open"),2),
        Quiz("Какой результат работы данного фрагмента кода?\n" +
                "for(;;) {\n" +
                "}", Answer("ошибка на этапе выполнения","ошибка на этапе компиляции","бесконечный цикл","этот код никогда не выполнится","ArrayIndexOutOfBoundsException"),3),
        Quiz("Как указать индекс последнего элемента массива?", Answer("array.length-1;","\n" +
                "array.size;","array.length;","\n" +
                "array.size-1;","array.size+1"),0),

    )
    fun getQuiz()=quiz

}