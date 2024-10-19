package com.example.quizzadanie1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private TextView questionTextView;
    int counter = 0;


    private Question[] questions = new Question[] {
            new Question(R.string.q_activity, true),
            new Question(R.string.q_find_resources, false),
            new Question(R.string.q_listener, true),
            new Question(R.string.q_resources, true),
            new Question(R.string.q_version, false)
    };

    private int currentIndex = 0;

    private void checkAnswerCorrectness(boolean userAnswer) {
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;

        if (userAnswer == correctAnswer) {
            resultMessageId = R.string.correct_answer;
            counter++;
        } else {
            resultMessageId = R.string.incorrect_answer;
        }

        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show(); //wyswietlamy komunikat czy odpowiedz jest poprawna czy nie

        trueButton.setEnabled(false);
        falseButton.setEnabled(false);
    }

    private void setNextQuestion() {

        if (currentIndex == questions.length)
        {
            showQuizResult();
        }
        else{
        questionTextView.setText(questions[currentIndex].getQuestionId());
        trueButton.setEnabled(true);
        falseButton.setEnabled(true);}
    }

    private void showQuizResult() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Koniec quizu");
        builder.setMessage("Liczba poprawnych odpowiedzi: " + counter + "/" + questions.length);
        builder.setPositiveButton("OK", (dialog, which) -> {

            currentIndex = 0;
            counter = 0;
            setNextQuestion();
        });
        builder.setCancelable(false);
        builder.show();
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);

        setNextQuestion();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(true);

            }

        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(false);

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex + 1);
                setNextQuestion();
            }
        });
    }


}