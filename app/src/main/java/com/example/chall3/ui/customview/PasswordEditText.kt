package com.example.chall3.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.example.chall3.R
import com.google.android.material.textfield.TextInputEditText

class PasswordEditText : TextInputEditText, View.OnTouchListener {

    private var isPasswordValid = false
    private lateinit var toggleIcon: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        showToggleIcon()
        setBackgroundResource(R.drawable.custom_border)
        textSize = 17f
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {
        toggleIcon =
            ContextCompat.getDrawable(context, R.drawable.ic_visibility_off) as Drawable

        setOnTouchListener(this)

        transformationMethod = PasswordTransformationMethod.getInstance()

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                isPasswordValid = s.length >= 5
            }

            override fun afterTextChanged(s: Editable) {

                if (s.toString().length < 8) showError()
            }
        })
    }

    private fun showError() {
        error = context.getString(R.string.invalid_password)
    }

    fun isPasswordValid(): Boolean {
        return isPasswordValid
    }

    private fun showToggleIcon() {
        setButtonDrawables(endOfTheText = toggleIcon)
    }

    private fun hideToggleIcon() {
        setButtonDrawables()
    }

    private fun setButtonDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }


    override fun onTouch(p0: View?, event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            val toggleIconStart: Float
            val toggleIconEnd: Float
            var isToggleIconClicked = false

            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                toggleIconEnd = (toggleIcon.intrinsicWidth + paddingStart).toFloat()
                if (event.x < toggleIconEnd) isToggleIconClicked = true
            } else {
                toggleIconStart = (width - paddingEnd - toggleIcon.intrinsicWidth).toFloat()
                if (event.x > toggleIconStart) isToggleIconClicked = true
            }

            if (isToggleIconClicked) {
                return when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        hideToggleIcon()
                        if (transformationMethod is HideReturnsTransformationMethod) {
                            transformationMethod = PasswordTransformationMethod.getInstance()
                            toggleIcon = ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_visibility_off
                            ) as Drawable
                            showToggleIcon()
                        } else {
                            transformationMethod = HideReturnsTransformationMethod.getInstance()
                            toggleIcon = ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_visibility
                            ) as Drawable
                            showToggleIcon()
                        }
                        true
                    }

                    else -> false
                }
            } else return false
        }
        return false
    }
}