package com.tokopedia.home

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.meituan.robust.patch.RobustModify
import com.meituan.robust.patch.annotaion.Add
import com.meituan.robust.patch.annotaion.Modify
import robust.tokopedia.R
import java.lang.reflect.Field

class ThirdActivity : AppCompatActivity(), View.OnClickListener {
    private var listView: ListView? = null
    private val multiArr = arrayOf("列表1", "列表2", "列表3", "列表4")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        listView = findViewById<View>(R.id.listview) as ListView
        val textView = findViewById<View>(R.id.secondtext) as TextView
        textView.setOnClickListener {
            RobustModify.modify()
            Log.d("", " onclick  in Listener")
        }
        //change text on the  SecondActivity
        textView.text = textInfo
        //test array
        val adapter: BaseAdapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, multiArr)
        listView!!.adapter = adapter
    }

    //        return "error fixed";
    @get:Modify
    val textInfo: String
        get() {
            array
            return "error occur "
//                    return "error fixed";
        }

    @get:Add
    val array: Array<String>
        get() = arrayOf("hello", "world")

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    override fun onClick(v: View) {
        Toast.makeText(this@ThirdActivity, "from implements onclick ", Toast.LENGTH_SHORT).show()
    }

    companion object {
        protected var name = "SecondActivity"
        @Throws(NoSuchFieldException::class)
        fun getReflectField(name: String, instance: Any): Field {
            var clazz: Class<*>? = instance.javaClass
            while (clazz != null) {
                try {
                    val field = clazz.getDeclaredField(name)
                    if (!field.isAccessible) {
                        field.isAccessible = true
                    }
                    return field
                } catch (e: NoSuchFieldException) { // ignore and search next
                }
                clazz = clazz.superclass
            }
            throw NoSuchFieldException("Field " + name + " not found in " + instance.javaClass)
        }

        fun getFieldValue(name: String, instance: Any): Any? {
            try {
                return getReflectField(name, instance)[instance]
            } catch (e: Exception) {
                Log.d("", "getField error $name   target   $instance")
                e.printStackTrace()
            }
            return null
        }
    }
}