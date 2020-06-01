package com.indra.contacttracing.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager


class StaticViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ViewPager(context, attrs) {

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return false
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        return false
    }

}