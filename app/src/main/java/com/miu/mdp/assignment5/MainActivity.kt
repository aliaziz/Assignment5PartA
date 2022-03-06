package com.miu.mdp.assignment5

import android.content.DialogInterface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.miu.mdp.assignment5.databinding.ActivityMainBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var binder: ActivityMainBinding

    private var correctCount = 0.0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binder.root)

        binder.qnRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            correctCount = 0.0
            when (checkedId) {
                R.id.neutral, R.id.true_choice -> {
                    correctCount += 0
                }
                R.id.false_choice -> {
                    correctCount += 1
                }
            }
        }

        with(binder) {
            submitBtn.setOnClickListener {
                if (safeCall.isChecked) correctCount += 1
                if (elvis.isChecked) correctCount += 1
                if (whenOperator.isChecked) correctCount -= 0
                val date = LocalDateTime.now()
                val format = date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

                val alertDialog = AlertDialog.Builder(this@MainActivity)
                    .setTitle("Results")
                    .setMessage(
                        "Congratulations! You submitted on $format, You have achieved ${
                            String.format(
                                "%.2f",
                                scoreCalc()
                            )
                        }%"
                    )
                    .setPositiveButton(
                        "Okay"
                    ) { _, _ ->
                        qnRadioGroup.clearCheck()
                        correctCount = 0.0
                    }

                alertDialog.show()
            }

            reset.setOnClickListener {
                correctCount = 0.0
                qnRadioGroup.clearCheck()
                elvis.isChecked = false
                whenOperator.isChecked = false
                safeCall.isChecked = false
            }
        }

    }

    private fun scoreCalc(): Double {
        return (correctCount / 3) * 100
    }
}
