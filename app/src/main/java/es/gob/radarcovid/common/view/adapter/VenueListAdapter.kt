/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.common.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.gob.radarcovid.R
import es.gob.radarcovid.common.extensions.getDayAndMonth
import es.gob.radarcovid.common.extensions.getHourString
import es.gob.radarcovid.common.extensions.getNameDayString
import es.gob.radarcovid.common.extensions.getTimeElapsed
import es.gob.radarcovid.models.domain.VenueHeaderItem
import es.gob.radarcovid.models.domain.VenueVisitedItem
import es.gob.radarcovid.models.domain.VenueVisitedRecyclerItem
import es.gob.radarcovid.models.domain.VenueVisitedRecyclerType

class VenueListAdapter(
    private val items: List<VenueVisitedRecyclerItem>,
    private val locale: String
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (VenueVisitedRecyclerType.values()[viewType]) {
            VenueVisitedRecyclerType.HEADER -> {
                VenueHeaderViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_venue_header, parent, false)
                )
            }
            VenueVisitedRecyclerType.VENUE -> {
                VenueItemViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_venue, parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (item.viewType) {
            VenueVisitedRecyclerType.HEADER -> {
                (holder as VenueHeaderViewHolder).bind(item as VenueHeaderItem, locale)
            }
            VenueVisitedRecyclerType.VENUE -> {
                (holder as VenueItemViewHolder).bind(item as VenueVisitedItem)
            }
        }
    }

    override fun getItemCount(): Int =
        items.size

    class VenueHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView = itemView.findViewById(R.id.textView) as TextView

        fun bind(item: VenueHeaderItem, locale: String) {
            textView.text =
                    //"${item.date.getNameDayString(locale)}, ${item.date.getDayAndMonth(locale)}"
                "${item.date.getNameDayString(locale)}, ${item.date.getDayAndMonth(locale)}"
        }
    }

    class VenueItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewName = itemView.findViewById(R.id.textViewName) as TextView
        private val textViewTime = itemView.findViewById(R.id.textViewTime) as TextView

        fun bind(item: VenueVisitedItem) {
            textViewName.text = item.venueItem.name
            textViewTime.text = "${item.venueItem.dateIn.getHourString()} (${
                item.venueItem.dateIn.getTimeElapsed(item.venueItem.dateOut!!)
            })"
        }
    }


}