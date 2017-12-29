package com.example.aks.echo.Adapters

import android.content.Context
import android.os.Build.VERSION_CODES.N
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.aks.echo.Activities.MainActivity
import com.example.aks.echo.Fragments.AboutUsFragment
import com.example.aks.echo.Fragments.FavoriteFragment
import com.example.aks.echo.Fragments.MainScreenFragment
import com.example.aks.echo.Fragments.SettingsFragment
import com.example.aks.echo.R

/**
 * Created by aks on 6/12/17.
 */

class NavigationDrawerAdapter(_contentList:ArrayList<String>, _getImages: IntArray, _context: Context)
    : RecyclerView.Adapter<NavigationDrawerAdapter.NavViewHolder>(){

    var contentList: ArrayList<String>? =null
    var getImages: IntArray? =null
    var mContext: Context? =null

    init {
        this.contentList = _contentList
        this.getImages = _getImages
        this.mContext = _context
    }

    override fun onBindViewHolder(holder: NavViewHolder?, position: Int) {
        holder?.icon_GET?.setBackgroundResource(getImages?.get(position) as Int)
        holder?.text_GET?.setText(contentList?.get(position))
        holder?.contentHolder?.setOnClickListener({
            if(position == 0){
                val mainScreenFragment = MainScreenFragment()
                (mContext as MainActivity).supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.details_fragment, mainScreenFragment)
                        .addToBackStack("MainFrag")
                        .commit()
            }
            else if(position == 1){
                val favoriteFragment = FavoriteFragment()
                (mContext as MainActivity).supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.details_fragment, favoriteFragment)
                        .addToBackStack("FavFrag")
                        .commit()
            }
            else if(position == 2){
                val settingsFragment = SettingsFragment()
                (mContext as MainActivity).supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.details_fragment, settingsFragment)
                        .addToBackStack("SettingsFrag")
                        .commit()
            }
            else{
                val aboutUsFragment = AboutUsFragment()
                (mContext as MainActivity).supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.details_fragment, aboutUsFragment)
                        .addToBackStack("AboutUsFrag")
                        .commit()
            }

            MainActivity.Statified.drawerLayout?.closeDrawers()
        })

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NavViewHolder{

        var itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.row_custom_navigationdrawer,parent,false)
        val returnThis = NavViewHolder(itemView)
        return returnThis
    }

    override fun getItemCount(): Int {
        // Returns the total number of items in the data set held by the adapter.
       return 4
    }

    class NavViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var icon_GET : ImageView? = null
        var text_GET : TextView? = null
        var contentHolder: RelativeLayout? = null
        init{
            icon_GET =itemView?.findViewById(R.id.icon_navdrawer)
            text_GET = itemView?.findViewById(R.id.text_navdrawer)
            contentHolder = itemView?.findViewById(R.id.navdrawer_item_content_holder)
        }
    }
}