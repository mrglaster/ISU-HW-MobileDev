package com.example.tabbedactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso


class CityFragment : Fragment() {

    companion object {
        private const val ARG_CITY_NAME = "title"
        private const val ARG_CITY_DESCRIPTION = "description"
        private const val ARG_CITY_IMAGE_URL = "url"


        fun newInstance(cityName: String, cityDescription: String, cityImageUrl: String): CityFragment {
            val fragment = CityFragment()
            val args = Bundle()
            args.putString(ARG_CITY_NAME, cityName)
            args.putString(ARG_CITY_DESCRIPTION, cityDescription)
            args.putString(ARG_CITY_IMAGE_URL, cityImageUrl)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_city, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val cityName = arguments?.getString(ARG_CITY_NAME) ?: ""
        val cityDescription = arguments?.getString(ARG_CITY_DESCRIPTION) ?: ""
        val cityImageUrl = arguments?.getString(ARG_CITY_IMAGE_URL) ?: ""

        val title: TextView = view.findViewById(R.id.cityName)
        val cityDescriptionTV = view.findViewById<TextView>(R.id.cityDescription)
        val cityImageView: ImageView = view.findViewById(R.id.cityImage)
        Picasso.get().load(cityImageUrl).into(cityImageView)
        title.text = cityName
        cityDescriptionTV.text = cityDescription


    }
}