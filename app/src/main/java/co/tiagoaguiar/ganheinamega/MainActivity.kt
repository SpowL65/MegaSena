package co.tiagoaguiar.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //buscar objetos
        val editText: EditText = findViewById(R.id.editNumber)
        val txtResult: TextView = findViewById(R.id.txtResult)
        val btnGenerate: Button = findViewById(R.id.btnGenerate)

        //banco de prefs
        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result", null)

        result?.let {  //if(result != null){
            txtResult.text = "Ultima Aposta: $result"

        }

        //OPÇÃO 2: variavel do tipo view.onclicklistener
        //btnGenerate.setOnClickListener(buttonClickListener)

        //OPÇÃO 3: simples
        btnGenerate.setOnClickListener {
            val text = editText.text.toString()
            numberGenerator(text, txtResult)
        }

    }

    private fun numberGenerator(text: String, txtResult: TextView) {
        if (text.isEmpty()) {
            //aviso na tela
            Toast.makeText(this, "Informe um numero entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        val qnt = text.toInt() //converte pra inteiro

        if (qnt < 6 || qnt > 15) {
            Toast.makeText(this, "Informe um numero entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        //sucesso
        val numbers = mutableSetOf<Int>()
        val random = Random

        while (true) {
            val number = random.nextInt(60)
            numbers.add(number + 1)
            if (numbers.size == qnt) {
                break
            }
        }

        txtResult.text = numbers.joinToString(separator = " - ")

        val editor = prefs.edit()
        editor.putString("result",txtResult.text.toString())
        //editor.commit() //sincrona
        val saved = editor.apply() //assincrona

        /*
        //ALTERNATIVA
        prefs.edit().apply {
            editor.putString("result",txtResult.text.toString())
            apply()
        }

         */
    }



    //OPÇÃO 2: variavel do tipo view.onclicklistener
//    val buttonClickListener = object : View.OnClickListener {
//        override fun onClick(v: View?) {
//            Log.i("teste", "botao clicado")
//        }
//    }

    //OPÇÃO 1: XML
    //fun buttonClicked(view: View){
    //    Log.i("teste", "botao clicado")

    //}
}