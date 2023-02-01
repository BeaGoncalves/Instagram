package com.example.instagram.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.example.instagram.R
import com.example.instagram.databinding.FragmentFeedBinding
import com.example.instagram.view.activity.MainActivity

class FeedFragment : Fragment() {

    private lateinit var _binding : FragmentFeedBinding

    private val toolbar by lazy {
      requireView().findViewById<Toolbar>(R.id.toolbar)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFeedBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }
}