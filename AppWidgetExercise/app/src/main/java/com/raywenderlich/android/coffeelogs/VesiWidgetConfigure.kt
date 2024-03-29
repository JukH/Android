package com.raywenderlich.android.coffeelogs

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class VesiWidgetConfigure : AppCompatActivity() {

  private lateinit var appWidgetText: EditText
  private var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
  private val VesiLoggerPersistence = VesiLoggerPersistence(this)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_coffee_logger_widget_configure)

    findViewById<View>(R.id.add_button).setOnClickListener(onClickListener)

    appWidgetText = findViewById(R.id.appwidget_text)
    val extras = intent.extras
    appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)

    setResult(Activity.RESULT_CANCELED)
  }

  private var onClickListener: View.OnClickListener = View.OnClickListener {
    val widgetText = appWidgetText.text.toString()
    VesiLoggerPersistence.saveLimitPref(widgetText.toInt(), appWidgetId)
    updateWidget()
    val resultValue = Intent()
    resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
    setResult(RESULT_OK, resultValue)
    finish()
  }

  private fun updateWidget() {
    val appWidgetManager = AppWidgetManager.getInstance(this)
    CoffeeLoggerWidget.updateAppWidget(this, appWidgetManager, appWidgetId)
  }
}
