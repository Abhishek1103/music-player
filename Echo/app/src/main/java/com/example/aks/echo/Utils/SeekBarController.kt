package com.example.aks.echo.Utils

import android.widget.SeekBar
import com.example.aks.echo.Fragments.SongPlayingFragment

class SeekBarController : SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
        if (SongPlayingFragment.Statified.mediaPlayer == null)
            return
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
        if (p0?.progress!! < SongPlayingFragment.Statified.mediaPlayer!!.duration) {
            SongPlayingFragment.Statified.mediaPlayer!!.seekTo(p0?.progress!!)
        } else {
            SongPlayingFragment.Statified.mediaPlayer?.seekTo((SongPlayingFragment.Statified.mediaPlayer?.duration)!!.toInt())
        }
    }
}