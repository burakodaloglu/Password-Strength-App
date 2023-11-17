package com.burakkodaloglu.password_strength_app

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.burakkodaloglu.password_strength_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkRegex()
    }

    fun checkRegex() {
        val passwordStrengthCalculator = PasswordStrengthCalculator()
        binding.etPassword.addTextChangedListener(passwordStrengthCalculator)
        passwordStrengthCalculator.strengthLevel.observe(
            this,
            Observer { strengthLevel -> displayStrengthLevel(strengthLevel) })
        passwordStrengthCalculator.strengthColor.observe(
            this,
            Observer { strengthColor -> R.color.weak = strengthColor })

        passwordStrengthCalculator.lowerCase.observe(
            this,
            Observer { value ->
                displayPasswordSuggestions(
                    value,
                    binding.lowerCaseImg,
                    binding.lowerCaseTxt
                )
            })
        passwordStrengthCalculator.upperCase.observe(
            this,
            Observer { value ->
                displayPasswordSuggestions(
                    value,
                    binding.upperCaseImg,
                    binding.upperCaseTxt
                )
            })
        passwordStrengthCalculator.digit.observe(this, Observer { value ->
            displayPasswordSuggestions(value, binding.digitImg, binding.digitTxt)
        })
        passwordStrengthCalculator.specialChar.observe(this, Observer { value ->
            displayPasswordSuggestions(value, binding.specialCharImg, binding.specialCharTxt)
        })

    }

    private fun displayPasswordSuggestions(value: Int, imageView: ImageView, textView: TextView) {
        if (value == 1) {
            imageView.setColorFilter(ContextCompat.getColor(this, R.color.bulletproof))
            textView.setTextColor(ContextCompat.getColor(this, R.color.bulletproof))
        } else {
            imageView.setColorFilter(ContextCompat.getColor(this, R.color.darkGray))
            textView.setTextColor(ContextCompat.getColor(this, R.color.darkGray))
        }
    }

    private fun displayStrengthLevel(strengthLevel: StrengthLevel) {
        binding.btnLogin.isEnabled =
            strengthLevel == StrengthLevel.BULLETPROOF

        binding.strengthLevelTxt.text = strengthLevel.name
        binding.strengthLevelTxt.setTextColor(
            ContextCompat.getColor(
                this,
                R.color.weak
            )
        )
        binding.strengthLevelIndicator.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.weak
            )
        )
    }

}