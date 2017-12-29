package com.example.aks.echo

/**
 * Created by aks on 10/12/17.
 */

// A helper class to facilitate easy song changes
class CurrentSongHelper{
    var songArtist: String? = null
    var songTitle: String? = null
    var songPath: String? = null
    var songId: Long = 0
    var currentPosition: Int = 0
    var isPlaying: Boolean  = false
    var isLoop: Boolean = false
    var isShuffle: Boolean = false
    var trackPosition: Int = 0
}