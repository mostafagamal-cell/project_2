package com.udacity.asteroidradar

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ExpandableListView.OnChildClickListener
import androidx.compose.ui.layout.Layout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemBinding

class Calls:DiffUtil.ItemCallback<Asteroid>()
{
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
       return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem==newItem
    }

}


class RecycleAdapter(private val clickListener:(ast:Asteroid)->Unit):
    ListAdapter<Asteroid,RecycleAdapter.ItemHolder>(Calls())
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =ItemHolder.inflateFrom(parent)


    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
       holder.bind(getItem(position),clickListener)
    }
    class ItemHolder(private val db:ItemBinding):RecyclerView.ViewHolder(db.root)
    {
        companion object
        {
            fun inflateFrom(parent: ViewGroup):ItemHolder
            {        val lf=LayoutInflater.from(parent.context)
                      val ly=ItemBinding.inflate(lf,parent,false)
                      return ItemHolder(ly)
            }

        }
        fun bind(asteroid: Asteroid,clickListener: (ast: Asteroid) -> Unit)
        {
            db.myvar=asteroid
            db.cardOfdata.setOnClickListener {clickListener(asteroid)}
        }
    }

}