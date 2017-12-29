package com.example.aks.echo.Fragments


import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.SearchView
import android.widget.TextView
import com.example.aks.echo.Adapters.FavoriteAdapter
import com.example.aks.echo.Databases.EchoDatabase
import com.example.aks.echo.R
import com.example.aks.echo.Songs
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {


    var myActivity: Activity? = null

    var noFavorites: TextView? = null
    var nowPlayingBottomBar: RelativeLayout? = null
    var playPauseButton: ImageButton? = null
    var songTitle: TextView? = null
    var recyclerView: RecyclerView? = null
    var trackPosition: Int = 0
    var favoriteContent: EchoDatabase? = null

    var refreshList: ArrayList<Songs>? = null
    var getListFromDatabase: ArrayList<Songs>? = null

    object Statified {
        var mediaPlayer: MediaPlayer? = null
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_favorite, container, false)

        setHasOptionsMenu(true)

        activity.title = "Now Playing"

        noFavorites = view?.findViewById(R.id.noFavorites)
        nowPlayingBottomBar = view?.findViewById(R.id.hiddenBarFavScreen)
        playPauseButton = view?.findViewById(R.id.playPauseButton)
        songTitle = view?.findViewById(R.id.songTitleFavScreen)
        recyclerView = view?.findViewById(R.id.favoriteRecycler)
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        myActivity = context as Activity
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        myActivity = context as Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        favoriteContent = EchoDatabase(myActivity)
        display_favorites_by_searching()
        bottomBarSetup()

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
    }

    fun getSongsFromPhone(): ArrayList<Songs> {
        var arrayList = ArrayList<Songs>()
        var contentResolver = myActivity?.contentResolver
        var songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        var songCursor = contentResolver?.query(songUri, null, null, null, null)

        if (songCursor != null && songCursor.moveToFirst()) {
            val songId = songCursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val songData = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA)
            val dateIndex = songCursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED)
            while (songCursor.moveToNext()) {
                var currentId = songCursor.getLong(songId)
                var currentTitle = songCursor.getString(songTitle)
                var currentArtist = songCursor.getString(songArtist)
                var currentData = songCursor.getString(songData)
                var currentDate = songCursor.getLong(dateIndex)
                arrayList.add(Songs(currentId, currentTitle, currentArtist, currentData, currentDate))
            }
        }
        return arrayList
    }


    fun bottomBarSetup() {
        try {
            bottomBarClickHandler()
            songTitle?.setText(SongPlayingFragment.Statified.currentSongHelper?.songTitle)
            SongPlayingFragment.Statified.mediaPlayer?.setOnCompletionListener({
                songTitle?.setText(SongPlayingFragment.Statified.currentSongHelper?.songTitle)
                SongPlayingFragment.Staticated.onSongComplete()
            })

            if (SongPlayingFragment.Statified.mediaPlayer?.isPlaying as Boolean) {
                nowPlayingBottomBar?.visibility = View.VISIBLE
            } else {
                nowPlayingBottomBar?.visibility = View.INVISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun bottomBarClickHandler() {
        nowPlayingBottomBar?.setOnClickListener({
            val songPlayingFragment = SongPlayingFragment()
            Statified.mediaPlayer = SongPlayingFragment.Statified.mediaPlayer
            var args = Bundle()
            args.putString("songArtist", SongPlayingFragment.Statified.currentSongHelper?.songArtist)
            args.putString("path", SongPlayingFragment.Statified.currentSongHelper?.songPath)
            args.putString("songTitle", SongPlayingFragment.Statified.currentSongHelper?.songTitle)
            args.putInt("SongId", SongPlayingFragment.Statified.currentSongHelper?.songId?.toInt() as Int)
            args.putInt("songPosition", SongPlayingFragment.Statified.currentSongHelper?.currentPosition?.toInt() as Int)
            args.putParcelableArrayList("songData", SongPlayingFragment.Statified._fetchSongs)
            args.putString("FavBottomBar", "success")
            songPlayingFragment.arguments = args       // linking songPlayingFragment to Bundle obj

            fragmentManager.beginTransaction()
                    .replace(R.id.details_fragment, songPlayingFragment)
                    .addToBackStack("SongPlayingFragment")
                    .commit()

        })

        playPauseButton?.setOnClickListener({
            if (SongPlayingFragment.Statified.mediaPlayer?.isPlaying as Boolean) {
                SongPlayingFragment.Statified.mediaPlayer?.pause()
                trackPosition = SongPlayingFragment.Statified.mediaPlayer?.currentPosition as Int
                playPauseButton?.setBackgroundResource(R.drawable.play_icon)
            } else {
                SongPlayingFragment.Statified.mediaPlayer?.seekTo(trackPosition)
                SongPlayingFragment.Statified.mediaPlayer?.start()
                playPauseButton?.setBackgroundResource(R.drawable.pause_icon)
            }
        })
    }

    fun display_favorites_by_searching() {
        if (favoriteContent?.checkSize() as Int > 0) {
            refreshList = ArrayList<Songs>()
            getListFromDatabase = favoriteContent?.queryDBList()
            var fetchListfromDevice = getSongsFromPhone()

            if (fetchListfromDevice != null) {
                for (i in 0..fetchListfromDevice?.size - 1) {
                    for (j in 0..getListFromDatabase?.size as Int - 1) {
                        if (getListFromDatabase?.get(j)?.songId === (fetchListfromDevice?.get(i)?.songId)) {
                            refreshList?.add((getListFromDatabase as ArrayList<Songs>)[j])
                        }
                    }
                }
            }

            if (refreshList == null) {
                recyclerView?.visibility = View.INVISIBLE
                noFavorites?.visibility = View.VISIBLE
            } else {
                var favoriteAdapter = FavoriteAdapter(refreshList as ArrayList<Songs>, myActivity as Context)
                val mLayoutManager = LinearLayoutManager(activity)
                recyclerView?.layoutManager = mLayoutManager
                recyclerView?.itemAnimator = DefaultItemAnimator()
                recyclerView?.adapter = favoriteAdapter
                recyclerView?.setHasFixedSize(true)
            }
        } else {
            recyclerView?.visibility = View.INVISIBLE
            noFavorites?.visibility = View.VISIBLE
        }

    }

//    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
//        menu?.clear()
//        inflater?.inflate(R.menu.main, menu)
//
//        var searchManager : SearchManager = myActivity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(myActivity?.componentName))
//        super.onCreateOptionsMenu(menu, inflater)
//    }

//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        val switcher = item?.itemId
//        val favoriteSongList : ArrayList<Songs>? = favoriteContent?.queryDBList()
//        val favoriteAdapter = FavoriteAdapter(favoriteSongList as ArrayList<Songs>, myActivity as Activity)
//
//        if (switcher == R.id.action_sort_ascending) {
//
//            val editor = myActivity?.getSharedPreferences("action_sort", Context.MODE_PRIVATE)?.edit()
//            editor?.putString("action_sort_ascending", "true")
//            editor?.putString("action_sort_recent", "false")
//            editor?.apply()
//
//
//
//            if (favoriteSongList != null) {
//                Collections.sort(favoriteSongList, Songs.Statified.nameComparator)
//                favoriteAdapter?.notifyDataSetChanged()
//            }
//
//            return false
//        } else if (switcher == R.id.action_sort_recent) {
//            val editor = myActivity?.getSharedPreferences("action_sort", Context.MODE_PRIVATE)?.edit()
//            editor?.putString("action_sort_ascending", "false")
//            editor?.putString("action_sort_recent", "true")
//            editor?.apply()
//            if (favoriteSongList != null) {
//                Collections.sort(favoriteSongList, Songs.Statified.dateComparator)
//                favoriteAdapter?.notifyDataSetChanged()
//            }
//            return false
//        }
//        return super.onOptionsItemSelected(item)
//    }

}// Required empty public constructor
