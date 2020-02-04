package com.tokopedia.home

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitcompat.SplitCompat
import com.meituan.robust.patch.RobustModify
import com.meituan.robust.patch.annotaion.Add
import com.meituan.robust.patch.annotaion.Modify
import kotlinx.android.synthetic.main.home_activity.*
import robust.tokopedia.R

/**
 * Author errysuprayogi on 01,February,2020
 */
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        setText()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase)
        SplitCompat.installActivity(this)
    }

    fun setText(){
        RobustModify.modify()
        val textView = findViewById<View>(R.id.textView) as TextView
        textView.text = textInfo()
    }

    fun textInfo(): String = "error sdkmskdmskdms"

    @Add
    fun getTextInfo3(): String = "sdsdsdsdsd"

}