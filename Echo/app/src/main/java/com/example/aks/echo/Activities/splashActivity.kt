package com.example.aks.echo.Activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import com.example.aks.echo.R


class splashActivity : AppCompatActivity() {

    // An array storing all the required permissions
    var permissionStrings = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.RECORD_AUDIO
            )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Checking is has all pernissions, if not then request, if yes then start new Act
        if(!hasPermission(this@splashActivity, *permissionStrings)){
            // Asking for permissions
            ActivityCompat.requestPermissions(this@splashActivity,permissionStrings,131) // Any arbitrary requestCode
        }
        else{
            // All permissions already granted, open new activity
            displaySplashScreen()
        }
    }


    // called when requestPermissions is called
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            131 ->{
                if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED
                        && grantResults[1]==PackageManager.PERMISSION_GRANTED
                        && grantResults[2]==PackageManager.PERMISSION_GRANTED
                        && grantResults[3]==PackageManager.PERMISSION_GRANTED
                        && grantResults[4]==PackageManager.PERMISSION_GRANTED)
                {
                    displaySplashScreen()                                           // open splash screen if all permissions are granted
                }
                else{
                    Toast.makeText(this@splashActivity,"Grant Permissions", Toast.LENGTH_SHORT).show()
                    this.finish()
                }
                return
            }
            else -> {
                Toast.makeText(this@splashActivity,"Something Went Wrong", Toast.LENGTH_SHORT).show()
                this.finish()
                return
            }
        }
    }

    // Checks whether all permissions are granted
    // arguments are: "Context" and "list of permissions"
    fun hasPermission(context: Context, vararg permissions: String): Boolean{
        for(permission in permissions){
            val res = context.checkCallingOrSelfPermission(permission)
            if(res != PackageManager.PERMISSION_GRANTED)
                return false
        }
        return true
    }

    // Function to display the splash screen with a 1 sec delay
    fun displaySplashScreen(){
        Handler().postDelayed({         // Executes the code after the mentioned delay
            val startAct = Intent(this@splashActivity, MainActivity::class.java)
            startActivity(startAct)
            this.finish()
        }, 1000)
    }
}
