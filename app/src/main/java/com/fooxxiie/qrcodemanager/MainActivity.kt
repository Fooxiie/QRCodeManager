package com.fooxxiie.qrcodemanager

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.form_email.view.*
import kotlinx.android.synthetic.main.form_sms.view.*

class MainActivity : AppCompatActivity() {

    private var typeItems =  arrayOf("Text", "SMS", "Email", "URL", "Contact")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var ii = IntentIntegrator(this)

        txt_run_scan.setOnClickListener() {
            ii.setBeepEnabled(false)
            ii.initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(applicationContext, "No found data", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, result.contents, Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun onClickActionBtn(view : View) {
        val choices = typeItems
        val builder = AlertDialog.Builder(this)
        with(builder) {
            setTitle(getString(R.string.chooseType))
            setItems(choices) { dialog, which ->
                showAdaptedForm(choices[which])
            }
            setPositiveButton(getString(R.string.cancel), null)
            show()
        }
    }

    private fun showAdaptedForm(choice:String) {
        when (choice) {
            typeItems[0] -> buildAlertForText(typeItems[0])
            typeItems[1] -> buildAlertForSMS(typeItems[1])
            typeItems[2] -> Toast.makeText(applicationContext, getString(R.string.inDev), Toast.LENGTH_SHORT).show()
            typeItems[3] -> Toast.makeText(applicationContext, getString(R.string.inDev), Toast.LENGTH_SHORT).show()
            else -> Toast.makeText(applicationContext, getString(R.string.noData), Toast.LENGTH_SHORT).show()
        }
    }

    private fun buildAlertForText(str: String) {
        var alert = AlertDialog.Builder(this)
        var view = layoutInflater.inflate(R.layout.form_text, null)
        alert.setTitle(str)
                .setView(view)
                .setPositiveButton(getString(R.string.btnRun)) { dialog, p1 ->
                    var i = Intent(applicationContext, QrCodeShowText::class.java)
                    i.putExtra("textQR", view.form_input_text.text.toString())
                    startActivity(i)
                }
                .setNegativeButton(getString(R.string.cancel)) { dialog, p1 ->
                    Toast.makeText(applicationContext, getString(R.string.cancel), Toast.LENGTH_SHORT).show()
                }
                .create()
                .show()
    }

    private fun buildAlertForSMS(str: String) {
        var alert = AlertDialog.Builder(this)
        var view = layoutInflater.inflate(R.layout.form_sms, null)
        alert.setTitle(str)
                .setView(view)
                .setPositiveButton(getString(R.string.btnRun)) { dialog, p1 ->
                    var i = Intent(applicationContext, QrCodeShowText::class.java)
                    var strSms = "sms:" + view.form_input_number.text.toString() + ":" + view.form_input_msg_sms.text.toString()
                    i.putExtra("textQR", strSms)
                    startActivity(i)
                }
                .setNegativeButton(getString(R.string.cancel)) { dialog, p1 ->
                    Toast.makeText(applicationContext, getString(R.string.cancel), Toast.LENGTH_SHORT).show()
                }
                .create()
                .show()
    }
}
