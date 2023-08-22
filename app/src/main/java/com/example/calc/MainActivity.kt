package com.example.calc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatDelegate
import com.example.calc.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var lastNumeric = false
    var stateError = false
    var lastDot = false
    private lateinit var expression: Expression



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        setContentView(binding.root)

//        binding.dataTv.movementMethod = ScrollingMovementMethod()
//        binding.dataTv.setHorizontallyScrolling(true)


    }



    fun onEqualClick(view: View) {
        binding.scrollView.fullScroll(ScrollView.FOCUS_RIGHT)

        onEqual()
        binding.dataTv.text = binding.resultTv.text.toString().drop(1)


    }


    fun onDigitClick(view: View) {



        if (stateError){
            binding.dataTv.text = (view as Button).text
            stateError = false
        }
        else{
            binding.dataTv.append((view as Button).text)
            binding.scrollView.fullScroll(ScrollView.FOCUS_RIGHT)

        }
        lastNumeric = true
        onEqual()

    }


    fun onAllclearClick(view: View) {

        binding.scrollView.fullScroll(ScrollView.FOCUS_RIGHT)

        binding.dataTv.text = ""
        binding.resultTv.text = ""
        stateError = false
        lastDot = false
        lastNumeric = false
        binding.resultTv.visibility = View.GONE


    }


    fun onClearClick(view: View) {

        binding.scrollView.fullScroll(ScrollView.FOCUS_RIGHT)
        binding.dataTv.text = ""
        lastNumeric = false

    }


    fun onBackClick(view: View) {


        binding.dataTv.text = binding.dataTv.text.toString().dropLast(1)
        binding.scrollView.fullScroll(ScrollView.FOCUS_RIGHT)


        try{
            var lastChar = binding.dataTv.text.toString().last()

            if(lastChar.isDigit()){
                onEqual()
            }
        }
        catch (e : Exception){

            binding.resultTv.text = ""
            binding.resultTv.visibility = View.GONE
            Log.e("last char error",e.toString())

        }


    }


    fun onOperatorClick(view: View) {

        if (!stateError&& lastNumeric){
            binding.dataTv.append((view as Button).text)
            binding.scrollView.fullScroll(ScrollView.FOCUS_RIGHT)

            lastDot = false
            lastNumeric = false
            onEqual()

        }
    }

    fun onEqual(){
        if(lastNumeric && !stateError){
            val txt = binding.dataTv.text.toString()

            expression = ExpressionBuilder(txt).build()

            try {
                val result = expression.evaluate()

                binding.resultTv.visibility = View.VISIBLE
                binding.resultTv.text = "=" + result.toString()
            }
            catch (ex : ArithmeticException){
                Log.e("evaluate error", ex.toString())
                binding.resultTv.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }
    }
}

private fun CharSequence.append(button: Button) {

}
