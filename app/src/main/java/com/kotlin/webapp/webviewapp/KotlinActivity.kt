package com.kotlin.webapp.webviewapp

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_kotlin.*

class KotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)


        WebView.setWebContentsDebuggingEnabled(true)                        // STEP - 02
        myWebView.settings.javaScriptEnabled = true                         // STEP - 03
        myWebView.addJavascriptInterface(JavaScriptInterface(), J_OBJ)      // STEP - 04
        myWebView.webChromeClient = object : WebChromeClient() {}           // STEP - 05
        myWebView.webViewClient = object : WebViewClient() {                // STEP - 06
            override fun onPageFinished(view: WebView?, url: String?) {     // STEP - 06
                var javaScript: String = "javascript: try { jObj = eval($J_OBJ); } catch(err) { jObj = window; }"
                myWebView.loadUrl(javaScript)
            }
        }

        if (isNetworkAvailable) {                                           // STEP - 07
            myWebView.loadUrl(BASE_URL)
            toast("Online Mode : Loading Live Page ...")
        } else {
            myWebView.loadUrl(INNER_URL)
            toast("Offline Mode : Loading App Page ...")
        }

        btn.setOnClickListener {
            sendToJS()
            msg.setText("")
        }

    } // End of fun onCreate()


    override fun onDestroy() {                                              // STEP - 08
        myWebView.removeJavascriptInterface(J_OBJ)
        super.onDestroy()
    } // End of fun onDestroy()


    private inner class JavaScriptInterface {                               // STEP - 09
        @JavascriptInterface
        fun dispKt(webMsg: String) {
            if (webMsg.isNullOrEmpty() || webMsg.isNullOrBlank())
                label.text = label.hint.toString()
            else
                label.text = webMsg
        }
    } // End of JavaScriptInterface class


    fun sendToJS() {                                                        // Step - 10
        myWebView.evaluateJavascript("javascript: "
                +"dispJS('"+msg.text+"');"
                ,null
        )
    }


    companion object {                                                      // STEP - 01
        private val J_OBJ = "KT"
        private val INNER_URL = "file:///android_asset/WebViewApp/index.html"
        private val BASE_URL = "https://fifthsirean02.github.io/webviewpage1/"
    } // End of Companion Object

    private val isNetworkAvailable: Boolean
        get() {
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

    fun toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this.applicationContext, message, duration).show()
    }

}
