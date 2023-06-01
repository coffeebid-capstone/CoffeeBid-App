package com.aplimelta.coffeebidapp.customview

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
import androidx.annotation.AttrRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.aplimelta.coffeebidapp.R

class CoffeeEditText : AppCompatEditText, View.OnTouchListener {

    @AttrRes
    private val stateError: IntArray = intArrayOf(R.attr.state_error)

    private lateinit var mNameStartDrawable: Drawable
    private lateinit var mEmailStartDrawable: Drawable
    private lateinit var mPasswordStartDrawable: Drawable
    private lateinit var mAddressStartDrawable: Drawable
    private lateinit var mPhoneStartDrawable: Drawable
    private lateinit var mCetBgDrawable: Drawable
    private lateinit var mPasswordDrawableSelector: Drawable

    private var isName: Boolean = false
    private var isEmail: Boolean = false
    private var isPassword: Boolean = false
    private var isAddress: Boolean = false
    private var isPhone: Boolean = false
    private var isError: Boolean = false

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs, defStyleAttr)
    }

    private fun init(attrs: AttributeSet?, defStyleAttr: Int = 0) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CoffeeEditText, defStyleAttr, 0)

        isName = typedArray.getBoolean(R.styleable.CoffeeEditText_name, false)
        isEmail = typedArray.getBoolean(R.styleable.CoffeeEditText_email, false)
        isPassword = typedArray.getBoolean(R.styleable.CoffeeEditText_password, false)
        isAddress = typedArray.getBoolean(R.styleable.CoffeeEditText_address, false)
        isPhone = typedArray.getBoolean(R.styleable.CoffeeEditText_phone, false)

        mNameStartDrawable = ContextCompat.getDrawable(context, R.drawable.ic_person_24) as Drawable
        mEmailStartDrawable = ContextCompat.getDrawable(context, R.drawable.ic_email_24) as Drawable
        mPasswordStartDrawable =
            ContextCompat.getDrawable(context, R.drawable.ic_key_24) as Drawable
        mAddressStartDrawable =
            ContextCompat.getDrawable(context, R.drawable.ic_location_24) as Drawable
        mPhoneStartDrawable = ContextCompat.getDrawable(context, R.drawable.ic_phone_24) as Drawable
        mCetBgDrawable =
            ContextCompat.getDrawable(context, R.drawable.ic_cet_bg_drawable) as Drawable
        mPasswordDrawableSelector =
            ContextCompat.getDrawable(context, R.drawable.ic_password_end_drawable) as Drawable

        when {
            isName -> setDrawableImages(startOfTheText = mNameStartDrawable)
            isEmail -> setDrawableImages(startOfTheText = mEmailStartDrawable)
            isAddress -> setDrawableImages(startOfTheText = mAddressStartDrawable)
            isPhone -> setDrawableImages(startOfTheText = mPhoneStartDrawable)
            isPassword -> setDrawableImages(
                startOfTheText = mPasswordStartDrawable,
                endOfTheText = mPasswordDrawableSelector
            )
        }

        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                error = when {
                    isEmail && s.toString().isNotEmpty() -> {
                        resources.getString(R.string.error_email)
                    }

                    isPassword && s.toString().isNotEmpty() && s.toString().length < 8 -> {
                        resources.getString(R.string.error_password)
                    }

                    isAddress && s.toString().isNotEmpty() -> {
                        resources.getString(R.string.error_address)
                    }

                    isPhone && s.toString().isNotEmpty() -> {
                        resources.getString(R.string.error_phone)
                    }

                    else -> {
                        showClearButton()
                        null
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        typedArray.recycle()
    }

    private fun showClearButton() {
        when {
            isName -> setDrawableImages(startOfTheText = mNameStartDrawable)
            isEmail -> setDrawableImages(startOfTheText = mEmailStartDrawable)
            isAddress -> setDrawableImages(startOfTheText = mAddressStartDrawable)
            isPhone -> setDrawableImages(startOfTheText = mPhoneStartDrawable)
            isPassword -> setDrawableImages(
                startOfTheText = mPasswordStartDrawable,
                endOfTheText = mPasswordDrawableSelector
            )

            else -> setDrawableImages()
        }
    }

    private fun setDrawableImages(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null,
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        background = mCetBgDrawable

        setPadding(36, 0, 36, 0)
        compoundDrawablePadding = 34
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (v != null && event != null) {
            if (compoundDrawables[2] != null) {
                val clearDrawableStart: Float
                val clearDrawableEnd: Float
                var isEndDrawableClicked = false

                if (v.layoutDirection == LAYOUT_DIRECTION_RTL) {
                    clearDrawableEnd =
                        (mPasswordDrawableSelector.intrinsicWidth + v.paddingStart).toFloat()

                    when {
                        event.x > clearDrawableEnd -> isEndDrawableClicked = true
                    }
                } else {
                    clearDrawableStart =
                        (v.width + v.paddingEnd - mPasswordDrawableSelector.intrinsicWidth).toFloat()

                    when {
                        event.x > clearDrawableStart -> isEndDrawableClicked = true
                    }
                }

                if (isEndDrawableClicked) {
                    when {
                        isPassword -> {
                            return when (event.action) {
                                MotionEvent.ACTION_DOWN -> {
                                    transformationMethod = if (v.isPressed) {
                                        v.isPressed = !v.isPressed
                                        PasswordTransformationMethod.getInstance()
                                    } else {
                                        v.isPressed = !v.isPressed
                                        HideReturnsTransformationMethod.getInstance()
                                    }
                                    true
                                }

                                MotionEvent.ACTION_UP -> {
                                    transformationMethod = if (v.isPressed) {
                                        v.isPressed = !v.isPressed
                                        PasswordTransformationMethod.getInstance()
                                    } else {
                                        v.isPressed = !v.isPressed
                                        HideReturnsTransformationMethod.getInstance()
                                    }
                                    true
                                }

                                else -> false
                            }
                        }

                        else -> showClearButton()
                    }
                } else return false
            }
        }
        return false
    }

    override fun setError(error: CharSequence?) {
        isError = error != null
        super.setError(error)
        refreshDrawableState()
    }

    override fun setError(error: CharSequence?, icon: Drawable?) {
        isError = error != null
        super.setError(error, icon)
        refreshDrawableState()
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isError) mergeDrawableStates(drawableState, stateError)
        return drawableState
    }
}