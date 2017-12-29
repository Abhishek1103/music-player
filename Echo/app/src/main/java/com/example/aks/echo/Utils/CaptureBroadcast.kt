package com.example.aks.echo.Utils

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import com.example.aks.echo.Activities.MainActivity
import com.example.aks.echo.Fragments.SongPlayingFragment
import com.example.aks.echo.R

/**
 * Created by aks on 22/12/17.
 */
class CaptureBroadcast : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        if(p1?.action == Intent.ACTION_NEW_OUTGOING_CALL){
            try{
                MainActivity.Statified.notificationMAnager?.cancel(1111)
            }catch (e : Exception){
                e.printStackTrace()
            }

            try {
                if(SongPlayingFragment.Statified.mediaPlayer?.isPlaying as Boolean){
                    SongPlayingFragment.Statified.mediaPlayer?.pause()
                    SongPlayingFragment.Statified.playpauseImageButton?.setBackgroundResource(R.drawable.play_icon)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            val tm : TelephonyManager = p0?.getSystemService(Service.TELEPHONY_SERVICE) as TelephonyManager

            when(tm?.callState) {
                TelephonyManager.CALL_STATE_RINGING -> {
                    try{
                        MainActivity.Statified.notificationMAnager?.cancel(1111)
                    }catch (e : Exception){
                        e.printStackTrace()
                    }

                    try {
                        if(SongPlayingFragment.Statified.mediaPlayer?.isPlaying as Boolean){
                            SongPlayingFragment.Statified.mediaPlayer?.pause()
                            SongPlayingFragment.Statified.playpauseImageButton?.setBackgroundResource(R.drawable.play_icon)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

}
