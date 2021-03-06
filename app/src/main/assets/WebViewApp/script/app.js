var jObj = window;        // Window Object to Manipulate from Kotlin
var label = document.getElementById('label').innerHTML;

setTimeout(function() {   // Show a Greet Message after 0.4 sec of loading JS
  setLabel();
}, 400);

function setLabel() {
  if (jObj != window)
    label = "Msg from Android...";
  else
    label = "Msg from Web Page...";

  document.getElementById('label').innerHTML = label;
  document.getElementById('label').style.visibility = "visible";
}

function sendToKT() {                                 // Calling Injected Kotlin function
  var msg = document.getElementById('msg').value;
  if (jObj != window) {
    document.getElementById('msg').value = "";
    jObj.dispKt(msg);
  } else {
    label = "Msg from Web Page...";
    document.getElementById('msg').value = "";
    dispJS(msg);
  }
}

function dispJS(msg) {                                // Called By Kotlin
  if (msg=="")
    msg = label;
  document.getElementById('label').innerHTML = msg;
}

/*

// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//                           JavaScript Functions
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

var label = document.getElementById('label').innerHTML;

dispJS(msg) {
  if (msg=="")
    msg = label;
  document.getElementById('label').innerHTML = msg;
}

function sendToKt() {
  var msg = document.getElementById('msg').value;
  KT.dispKt(msg);         // Calling Kotlin fun with Object : KT
}

// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -




// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//                            Kotlin Functions
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

// KT Function for Being Called By JS Function (with Object 'KT') :
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
private inner class JavaScriptInterface {
  @JavascriptInterface
  fun dispKt(webMsg: String) {
    if (webMsg.isNullOrEmpty() || webMsg.isNullOrBlank())
      label.text = label.hint.toString()
    else
      label.text = webMsg
  }
}

// Kt Function for calling JS Function :
// - - - - - - - - - - - - - - - - - -
fun sendToJS() {
  myWebView.evaluateJavascript("javascript: "
      +"dispJS('"+msg.text+"');"
    ,null
  )
}

// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

*/
