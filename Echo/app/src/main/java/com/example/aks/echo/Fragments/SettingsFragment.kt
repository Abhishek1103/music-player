package com.example.aks.echo.Fragments

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.Switch
import com.example.aks.echo.R


/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : Fragment() {

    var myActivity : Activity? = null
    var shakeSwitch: Switch? = null

    object Statified{
        val MY_PREFS_NAME  = "ShakeFeature"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view  = inflater!!.inflate(R.layout.fragment_settings, container, false)

        //setHasOptionsMenu(true)

        activity.title = "Settings"

        shakeSwitch = view?.findViewById(R.id.switchShake)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        myActivity = context as Activity
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        myActivity = context as Activity
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val prefs = myActivity?.getSharedPreferences(Statified.MY_PREFS_NAME, Context.MODE_PRIVATE)
        val isAllowed = prefs?.getBoolean("feature", false) // Default value is given false

        if(isAllowed as Boolean){
            shakeSwitch?.isChecked = true
        }else {
            shakeSwitch?.isChecked = false
        }

        shakeSwitch?.setOnCheckedChangeListener({compoundButton, b ->
            if(b){
                val editor = myActivity?.getSharedPreferences(Statified.MY_PREFS_NAME, Context.MODE_PRIVATE)?.edit()
                editor?.putBoolean("feature", true)
                editor?.apply()
            }else {
                val editor = myActivity?.getSharedPreferences(Statified.MY_PREFS_NAME, Context.MODE_PRIVATE)?.edit()
                editor?.putBoolean("feature", true)
                editor?.apply()
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.menu_blank, menu)
        super.onCreateOptionsMenu(menu, inflater)
        //menu?.clear()
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        //menu?.clear()
        super.onPrepareOptionsMenu(menu)
        //menu?.clear()
    }




}// Required empty public constructor
