package com.example.chall3.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.example.chall3.R
import com.google.android.material.textfield.TextInputEditText

class EmailEditText : TextInputEditText, View.OnTouchListener {

    private lateinit var clearButton: Drawable
    private var emailValid = false

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
        setBackgroundResource(R.drawable.custom_border)
        textSize = 17f
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {
        clearButton =
            ContextCompat.getDrawable(context, R.drawable.ic_clear) as Drawable

        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (s.toString().isNotEmpty()) showClearButton() else hideClearButton()

                emailValid = isEmailValid(s)

                if (!emailValid) {
                    showError()
                }

            }
        })
    }

    private fun isEmailValid(email: CharSequence): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun emailValid(): Boolean {
        return emailValid
    }

    private fun showError() {
        error = context.getString(R.string.email_invalid)
    }

    private fun showClearButton() {
        setButtonDrawables(endOfTheText = clearButton)
    }

    private fun hideClearButton() {
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
            val clearButtonStart: Float
            val clearButtonEnd: Float
            var isClearButtonClicked = false

            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                clearButtonEnd = (clearButton.intrinsicWidth + paddingStart).toFloat()
                if (event.x < clearButtonEnd) isClearButtonClicked = true
            } else {
                clearButtonStart = (width - paddingEnd - clearButton.intrinsicWidth).toFloat()
                if (event.x > clearButtonStart) isClearButtonClicked = true
            }

            if (isClearButtonClicked) {
                return when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        clearButton = ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_clear
                        ) as Drawable
                        showClearButton()
                        true
                    }

                    MotionEvent.ACTION_UP -> {
                        clearButton = ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_clear
                        ) as Drawable
                        if (text != null) text?.clear()
                        hideClearButton()
                        true
                    }

                    else -> false
                }
            } else return false
        }
        return false
    }
}