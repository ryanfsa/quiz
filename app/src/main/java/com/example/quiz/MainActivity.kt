package com.example.quiz

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    lateinit var startGroup : Group
    lateinit var questionGroup : Group
    lateinit var endGroup: Group
    lateinit var start : Button
    lateinit var startText : TextView
    lateinit var buttons : Array<Button>
    lateinit var image : ImageView
    lateinit var questionText : TextView
    lateinit var questionScore : TextView
    lateinit var endText : TextView
    lateinit var restart : Button
    lateinit var exit : Button
    lateinit var quiz : Quiz
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        quiz = Quiz(setQuestions(),this)
        wire()
    }
    fun wire(){
        findViewById<ConstraintLayout>(R.id.main_layout).setBackgroundColor(Color.rgb(0.8f,1f,1f))
        startGroup = findViewById(R.id.main_group_start)
        questionGroup = findViewById(R.id.main_group_question)
        endGroup = findViewById(R.id.main_group_end)
        endText = findViewById(R.id.main_endText)
        restart = findViewById(R.id.main_restart)
        exit = findViewById(R.id.main_exit)
        start  = findViewById(R.id.main_start)
        startText = findViewById(R.id.main_startText)
        image = findViewById(R.id.main_image)
        questionText = findViewById(R.id.main_questionText)
        questionScore = findViewById(R.id.main_questionScore)
        startText.textSize = 30f

        buttons = arrayOf<Button>(
            findViewById(R.id.main_a),
            findViewById(R.id.main_b),
            findViewById(R.id.main_c),
            findViewById(R.id.main_d))

        buttons.forEach{it.textSize = 20f}

        questionGroup.visibility = View.INVISIBLE
        endGroup.visibility = View.INVISIBLE

        start.setOnClickListener {
            startGroup.visibility = View.GONE
            questionGroup.visibility = View.VISIBLE
            quiz.nextQuestion()
        }
        restart.setOnClickListener {
            startGroup.visibility = View.VISIBLE
            endGroup.visibility = View.INVISIBLE
            questionScore.text = "0/0"
            quiz.question = -1
            quiz.score = 0
        }
        exit.setOnClickListener {
            finish()
        }
        buttons[0].setOnClickListener { quiz.score(0) }
        buttons[1].setOnClickListener { quiz.score(1) }
        buttons[2].setOnClickListener { quiz.score(2) }
        buttons[3].setOnClickListener { quiz.score(3) }
    }
    fun scoreColor(){
        buttons.forEach{ it.setBackgroundColor(Color.rgb(0.7f,0.3f,0.3f)) }
        buttons[quiz.qc].setBackgroundColor(Color.rgb(0f,0.5f,0f))
    }
    fun setTexts(q : Question){
        var bcol = randomColor(0.2f,0.4f)
        buttons.forEach{ it.setBackgroundColor(bcol) }
        findViewById<ConstraintLayout>(R.id.main_layout).setBackgroundColor(randomColor(0.8f,0.2f))
        questionText.text = q.text
        buttons[0].text = q.a
        buttons[1].text = q.b
        buttons[2].text = q.c
        buttons[3].text = q.d
        if(q.image != null) {
            image.visibility = View.VISIBLE
            val resource =
                getResources().getIdentifier(q.image, "drawable", this.getPackageName())
            image.setImageResource(resource)
        }
        else{
            image.visibility = View.INVISIBLE
        }
    }
    fun randomColor(x : Float, y : Float) : Int{
        return Color.rgb(x+y*Math.random().toFloat(),x+y*Math.random().toFloat(),x+y*Math.random().toFloat())
    }
    fun end(){
        questionGroup.visibility = View.INVISIBLE
        endGroup.visibility = View.VISIBLE
        endText.textSize = 40f
        val score = quiz.score
        endText.text = "Score: $score/10"
    }
    fun setQuestions() : List<Question>{
        val inputStream = resources.openRawResource(R.raw.questions)
        val jsonText = inputStream.bufferedReader().use { it.readText() }
        val gson = Gson()
        val type = object : TypeToken<List<Question>>(){}.type
        return gson.fromJson(jsonText,type)
    }
}