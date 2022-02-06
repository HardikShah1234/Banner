package com.deliveryhero.banners

import android.animation.*
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.GradientDrawable.*
import android.graphics.drawable.GradientDrawable.Orientation.*
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.deliveryhero.banners.Constants.Companion.COLLAPSED_MAX_LINES
import com.deliveryhero.banners.Constants.Companion.DEFAULT_ANIM_DURATION
import kotlinx.android.synthetic.main.banner_info.view.*

class CustomTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = R.attr.customTextView) : AppCompatTextView(context, attrs, defStyleAttr),
    View.OnClickListener {

    private var originalText: CharSequence? = ""
    private var collapsedLines: Int? = 0
    var isExpanded: Boolean = false
    private var animationDuration: Int? = 0
    private var foregroundColor: Int? = 0
    private var initialText: String? = ""
    private var isUnderlined: Boolean? = false
    private var ellipsizeTextColor: Int? = 0

    private lateinit var visibleText: String

    override fun onClick(v: View?) {
        toggle()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (initialText.isNullOrBlank()) {
            initialText = text.toString()
            visibleText = visibilityText()
            setEllipsizedText(isExpanded)
            setForeground(isExpanded)
        }
    }

    fun toggle() {
        if (visibleText.isAllTextVisible()) {
            return
        }

        isExpanded = !isExpanded

        maxLines = if (!isExpanded) {
            collapsedLines!!
        } else {
            COLLAPSED_MAX_LINES
        }

        val startHeight = measuredHeight

        measure(
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        )
        val endHeight = measuredHeight

        animationSet(startHeight, endHeight).apply {
            duration = animationDuration?.toLong()!!
            start()

            addListener(object : Animator.AnimatorListener {
                override fun onAnimationEnd(animation: Animator?) {
                    if (!isExpanded)
                        setEllipsizedText(isExpanded)
                }

                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}
            })
        }

        setEllipsizedText(isExpanded)
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        originalText = text
        super.setText(text, type)
    }

    //private functions
    init {
        context.obtainStyledAttributes(attrs, R.styleable.CustomTextView).apply {
            try {
                collapsedLines = getInt(R.styleable.CustomTextView_collapsedLines, COLLAPSED_MAX_LINES)
                animationDuration = getInt(R.styleable.CustomTextView_animDuration, DEFAULT_ANIM_DURATION)
                foregroundColor = getColor(R.styleable.CustomTextView_foregroundColor, Color.TRANSPARENT)
                isUnderlined = getBoolean(R.styleable.CustomTextView_isUnderlined, false)
                isExpanded = getBoolean(R.styleable.CustomTextView_isExpanded, false)
                ellipsizeTextColor = getColor(R.styleable.CustomTextView_ellipsizeTextColor, Color.BLUE)
            } finally {
                this.recycle()
            }
        }

        if (!isExpanded)
            maxLines = collapsedLines!!
        setOnClickListener(this)
    }

    private fun setEllipsizedText(isExpanded: Boolean) {
        if (initialText?.isBlank()!!)
            return

        text = if (isExpanded || visibleText.isAllTextVisible() || collapsedLines!! == COLLAPSED_MAX_LINES) {
            SpannableStringBuilder(
                initialText.toString())
        } else {
            SpannableStringBuilder(
                visibleText.substring(0,
                    visibleText.length))
        }
    }

    private fun visibilityText():String = initialText!!

    private fun setForeground(isExpanded: Boolean) {
        foreground = GradientDrawable(BOTTOM_TOP, intArrayOf(foregroundColor!!, Color.TRANSPARENT))
        foreground.alpha = if (isExpanded) {
            MIN_VALUE_ALPHA
        } else {
            MAX_VALUE_ALPHA
        }
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun animationSet(startHeight: Int, endHeight: Int): AnimatorSet {
        return AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofInt(
                    this,
                    ANIMATION_PROPERTY_MAX_HEIGHT,
                    startHeight,
                    endHeight
                ),
                ObjectAnimator.ofInt(
                    this@CustomTextView.foreground,
                    ANIMATION_PROPERTY_ALPHA,
                    foreground.alpha,
                    MAX_VALUE_ALPHA - foreground.alpha
                )
            )
        }
    }

    private fun String.isAllTextVisible(): Boolean = this == text

    private fun String.span(): SpannableString =
        SpannableString(this).apply {
            setSpan(
                ForegroundColorSpan(ellipsizeTextColor!!),
                0,
                this.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            if (isUnderlined!!)
                setSpan(
                    UnderlineSpan(),
                    0,
                    this.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
        }

    companion object {
        const val TAG = "CustomTextView"
        const val MAX_VALUE_ALPHA = 255
        const val MIN_VALUE_ALPHA = 0
        const val ANIMATION_PROPERTY_MAX_HEIGHT = "maxHeight"
        const val ANIMATION_PROPERTY_ALPHA = "alpha"
    }
}
