package com.example.quiz

import android.os.Handler

class Quiz(val questions : List<Question>, val main : MainActivity) {
    var question = -1
    var waiting = false
    var qc = 0
    var score = 0

    fun score(x : Int){
        if(waiting){return}
        waiting = true
        if(x == qc){ score++ }
        main.questionScore.text = "$score/"+(question+1)
        main.scoreColor()
        Handler().postDelayed({
            nextQuestion()
        }, 1000)
    }

    fun nextQuestion(){
        question++
        if(question == 10){
            main.end()
            return
        }
        var q : Question = questions[question]
        qc = q.correct
        main.setTexts(q)
        waiting = false
    }
}