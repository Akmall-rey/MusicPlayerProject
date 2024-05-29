package com.example.musicplayerproject.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayerproject.R
import com.example.musicplayerproject.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var rvExplore: RecyclerView
    private val listExplore = ArrayList<Relic>()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvExplore = view.findViewById(R.id.rv_explore)
        setupRecyclerView(rvExplore, listExplore)

        listExplore.addAll(getListRelic())
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, list: ArrayList<Relic>) {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val listRelicAdapter = ListRelicAdapter(list)
        recyclerView.adapter = listRelicAdapter

        listRelicAdapter.setOnItemClickCallback(object : ListRelicAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Relic) {
                showSelectedRelic(data)
            }
        })
    }

    private fun getListRelic(): ArrayList<Relic> {
        val dataImg = resources.obtainTypedArray(R.array.relic_img)
        val listHero = ArrayList<Relic>()
        try {
            for (i in 0 until dataImg.length()) {
                val hero = Relic(dataImg.getResourceId(i, -1))
                listHero.add(hero)
            }
        } finally {
            dataImg.recycle()
        }
        return listHero
    }

    private fun showSelectedRelic(relic: Relic) {
        context?.let {
            Toast.makeText(it, "is selected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}