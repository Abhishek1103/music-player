package com.example.aks.echo.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.aks.echo.R


/**
 * A simple [Fragment] subclass.
 */
class AboutUsFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
       val view =  inflater!!.inflate(R.layout.fragment_about_us, container, false)
        activity.title = "About Us"
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.menu_blank, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onPrepareOptionsMenu(menu: Menu?) {

        super.onPrepareOptionsMenu(menu)
       // menu?.clear()
    }
}// Required empty public constructor
