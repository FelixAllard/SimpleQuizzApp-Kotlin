package com.example.simplequizzapp
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var questionTextView: TextView
    private lateinit var answerButton1: Button
    private lateinit var answerButton2: Button
    private lateinit var answerButton3: Button
    private lateinit var answerButton4: Button
    private lateinit var submitButton: Button
    // Initialize with your questions and answers
    private var selectedAnswerIndex = -1
    private var score = 0
    private var currentQuestion = 1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        questionTextView = findViewById(R.id.questionTextView)
        answerButton1 = findViewById(R.id.answerButton1)
        answerButton2 = findViewById(R.id.answerButton2)
        answerButton3 = findViewById(R.id.answerButton3)
        answerButton4 = findViewById(R.id.answerButton4)
        submitButton = findViewById(R.id.submitButton)
        loadQuestion(currentQuestion)
        val answerButtons = listOf(answerButton1, answerButton2, answerButton3, answerButton4)
        answerButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                selectedAnswerIndex = index
                Toast.makeText(this, "Answer ${index + 1} selected", Toast.LENGTH_SHORT).show()
            }
        }
        submitButton.setOnClickListener {
            checkAnswer()
        }
    }
    private fun loadQuestion(number :Int) {
        val questionResId = resources.getIdentifier("question_${number}", "string", packageName)
        questionTextView.text = getString(questionResId)

        val answers = when (currentQuestion) {
            1 -> resources.getStringArray(R.array.answers_1)
            2 -> resources.getStringArray(R.array.answers_2)
            3 -> resources.getStringArray(R.array.answers_3)
            // Can add more questions
            else -> throw IllegalArgumentException("Invalid variable value")
        }
        answerButton1.text = answers[0]
        answerButton2.text = answers[1]
        answerButton3.text = answers[2]
        answerButton4.text = answers[3]
// Reset selected answer index for the new question
        selectedAnswerIndex = -1
    }
    private fun checkAnswer() {
        val correctAnswerResId = resources.getIdentifier("correct_answer_${currentQuestion}", "integer", packageName)
        val correctAnswer = resources.getInteger(correctAnswerResId)
        if (selectedAnswerIndex + 1 == correctAnswer) { // +1 because array index starts at 0
            score++
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            currentQuestion +=1


        } else {
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show()
            //Well go to next question even if there is a mistake
            currentQuestion+=1;
        }
        if (currentQuestion < 4) {
            loadQuestion(currentQuestion)
        } else {
            //It's gonna be stuck but I can add additional behaviour if we need to after here when the quiz is over.
            Toast.makeText(this, "Quiz finished! Your score: $score", Toast.LENGTH_LONG).show()

        }
    }
}