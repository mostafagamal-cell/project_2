package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMainBinding
import com.udacity.asteroidradar.RecycleAdapter
import com.udacity.asteroidradar.Room.DataBaseAsteroid


class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val application= requireNotNull(this.activity).application
        ViewModelProvider(this,Factory(DataBaseAsteroid.getDatabase(application))).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel
        val recy= RecycleAdapter{
            viewModel.start(it)
        }
        binding.asteroidRecycler.adapter=recy
        viewModel.li.observe(viewLifecycleOwner,
            {
                if(it!=null)
                {
                  findNavController().navigate( MainFragmentDirections.actionShowDetail(it))
                    viewModel.end()
                }
            })

        viewModel.list.observe(viewLifecycleOwner, Observer {
            recy.submitList(it)
        })
        binding.executePendingBindings()
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.today_menu)
        {
            viewModel.GetToday()
        }
        if (item.itemId==R.id.week_menu)
        {
            viewModel.GetWeek()
        }
        if (item.itemId== R.id.saved_menu)
        {
            viewModel.GetAll()
        }
        return true
    }
}
