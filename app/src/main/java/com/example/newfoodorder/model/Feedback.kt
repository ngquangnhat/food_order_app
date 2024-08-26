package com.example.newfoodorder.model

import android.app.Activity
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.newfoodorder.MyApplication
import com.example.newfoodorder.R
import com.example.newfoodorder.constant.GlobalFunction
import com.example.newfoodorder.utils.StringUtil

class Feedback(
    private var _name: String = "",
    private var _phone: String = "",
    private var _email: String = "",
    private var _comment: String = ""
) : BaseObservable() {
    var name: String
        @Bindable get() = _name
        set(value) {
            _name = value
            notifyChange()
        }
    var phone: String
        @Bindable get() = _phone
        set(value) {
            _phone = value
            notifyChange()
        }
    var email: String
        @Bindable get() = _email
        set(value) {
            _email = value
            notifyChange()
        }
    var comment: String
        @Bindable get() = _comment
        set(value) {
            _comment = value
            notifyChange()
        }

    fun clickSendFeedback(view: View) {
        val context = view.context
        when {
            StringUtil.isEmpty(name) -> {
                GlobalFunction.showToastMessage(context, context.getString(R.string.name_require))
            }

            StringUtil.isEmpty(comment) -> {
                GlobalFunction.showToastMessage(
                    context,
                    context.getString(R.string.comment_require)
                )
            }

            else -> {
                val feedback = Feedback(name, phone, email, comment)
                val id = System.currentTimeMillis()
                MyApplication.get(context).getFeedbackDatabaseReference()
                    .child(id.toString())
                    .setValue(feedback) { _, _ ->
                        GlobalFunction.hideSoftKeyboard(context as Activity)
                        GlobalFunction.showToastMessage(
                            context,
                            context.getString(R.string.send_feedback_success)
                        )
                        name = ""
                        phone = ""
                        email = ""
                        comment = ""
                    }
            }
        }
    }

}