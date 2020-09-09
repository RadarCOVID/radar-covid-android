/*
 * Copyright (c) 2020 Gobierno de España
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package es.gob.radarcovid.common.view


import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Animatable
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import kotlin.math.roundToInt

class AnimatedCircleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), Animatable {

    private var animationFirstCircleGroup: ValueAnimator? = null
    private var animationSecondCircleGroup: ValueAnimator? = null
    private var widthFirstCircleGroup = 0
    private var widthSecondCircleGroup = 0
    private val animationDuration = 2000L
    private var circleSpacing = 70f
    private val circlesNumber = 3

    private val paint = Paint().apply {
//        color = ContextCompat.getColor(context, R.color.purple_9C)
        color = Color.WHITE
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        for (it in circlesNumber downTo 1) {
            var circleWidth = widthFirstCircleGroup - (circleSpacing.toInt() * it)
            if (circleWidth < 0 || circleWidth > width) {
                circleWidth = 0
            }
            paint.alpha = 255 - ((circleWidth.toFloat() / width.toFloat()) * 255f).roundToInt()
            canvas.drawCircle(
                width / 2f,
                width / 2f,
                circleWidth / 2f,
                paint
            )
        }

        for (it in circlesNumber downTo 1) {
            var circleWidth = widthSecondCircleGroup - (circleSpacing.toInt() * it)
            if (circleWidth < 0 || circleWidth > width) {
                circleWidth = 0
            }
            paint.alpha = 255 - ((circleWidth.toFloat() / width.toFloat()) * 255f).roundToInt()
            canvas.drawCircle(
                width / 2f,
                width / 2f,
                circleWidth / 2f,
                paint
            )
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        start()
    }

    override fun isRunning(): Boolean =
        (animationFirstCircleGroup?.isRunning == true
                && animationSecondCircleGroup?.isRunning == true)

    override fun start() {
        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                circleSpacing = (width / 4).toFloat()
                animationFirstCircleGroup = createFirstCircleGroupAnimation()
                animationSecondCircleGroup = createSecondCircleGroupAnimation()
                animationFirstCircleGroup?.start()
                animationSecondCircleGroup?.start()
            }
        })
    }

    override fun stop() {
        animationFirstCircleGroup?.cancel()
        animationSecondCircleGroup?.cancel()
    }

    private fun createFirstCircleGroupAnimation(): ValueAnimator = ValueAnimator().apply {
        setValues(
            PropertyValuesHolder.ofInt(
                "width",
                1,
                width + (circleSpacing.toInt() * circlesNumber)
            )
        )
        duration = animationDuration
        repeatCount = ValueAnimator.INFINITE
        setCurrentFraction(0.4f)
        addUpdateListener { animation ->
            widthFirstCircleGroup = animation.getAnimatedValue("width") as Int
            invalidate()
        }
    }

    private fun createSecondCircleGroupAnimation(): ValueAnimator = ValueAnimator().apply {
        setValues(
            PropertyValuesHolder.ofInt(
                "width",
                1,
                width + (circleSpacing.toInt() * circlesNumber)
            )
        )
        duration = animationDuration
        repeatCount = ValueAnimator.INFINITE
        addUpdateListener { animation ->
            widthSecondCircleGroup = animation.getAnimatedValue("width") as Int
            invalidate()
        }
    }

}