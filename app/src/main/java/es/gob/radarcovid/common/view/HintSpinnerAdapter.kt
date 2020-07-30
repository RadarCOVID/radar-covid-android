package es.gob.radarcovid.common.view

import android.content.Context
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.SpinnerAdapter
import android.widget.TextView
import es.gob.radarcovid.R


class HintSpinnerAdapter(
    context: Context,
    private val hintText: String,
    private val dropdownLayout: Int = R.layout.row_spinner,
    objects: List<Any>
) : SpinnerAdapter, ListAdapter {

    constructor(
        context: Context,
        hintResourceId: Int,
        dropdownLayout: Int = R.layout.row_spinner,
        objects: List<Any>
    ) : this(context, context.resources.getString(hintResourceId), dropdownLayout, objects)

    private val EXTRA = 1

    private val adapter: SpinnerAdapter = ArrayAdapter(
        context,
        dropdownLayout,
        objects
    )
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val emptyView: View = View(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View =
        if (position == 0) {
            layoutInflater.inflate(dropdownLayout, parent, false).apply {
                (this as TextView).text = hintText
            }
        } else
            adapter.getView(position - EXTRA, null, parent)

    override fun getDropDownView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View? { // Android BUG! http://code.google.com/p/android/issues/detail?id=17128 -
// Spinner does not support multiple view types
        return if (position == 0) {
//            layoutInflater.inflate(nothingSelectedDropdownLayout, parent, false).apply {
//                (this as TextView).text = hintText
//            }
            emptyView
        } else adapter.getDropDownView(position - EXTRA, null, parent)
        // Could re-use the convertView if possible, use setTag...
    }

    override fun getCount(): Int {
        val count = adapter.count
        return if (count == 0) 0 else count + EXTRA
    }

    override fun getItem(position: Int): Any? =
        if (position == 0)
            null
        else
            adapter.getItem(position - EXTRA)


    override fun getItemViewType(position: Int): Int = 0

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long =
        if (position >= EXTRA)
            adapter.getItemId(position - EXTRA)
        else
            (position - EXTRA).toLong()


    override fun hasStableIds(): Boolean = adapter.hasStableIds()

    override fun isEmpty(): Boolean = adapter.isEmpty

    override fun registerDataSetObserver(observer: DataSetObserver?) {
        adapter.registerDataSetObserver(observer)
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver?) {
        adapter.unregisterDataSetObserver(observer)
    }

    override fun areAllItemsEnabled(): Boolean = false

    override fun isEnabled(position: Int): Boolean =
        position != 0 // Don't allow the 'nothing selected'

}