/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.coffeelogs

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {

  internal val coffeeLoggerPersistence = VesiLoggerPersistence(this)
  private var today: Int = 0
  private var gramsValue: TextView? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    gramsValue = findViewById<TextView?>(R.id.grams)
    refreshTodayLabel()
    if (intent != null && intent.action == Constants.ADD_COFFEE_INTENT) {
      val coffeeIntake = intent.getIntExtra(Constants.GRAMS_EXTRA, 0)
      coffeeLoggerPersistence.saveTitlePref(today + coffeeIntake)
      saveCoffeeIntake(coffeeIntake)
    }
  }



  fun onEspressoPressed(v: View) {
    coffeeLoggerPersistence.saveTitlePref(today + Vesimaara.VESI.desia)
    saveCoffeeIntake(Vesimaara.VESI.desia)
  }



  fun refreshTodayLabel() {
    // Send a broadcast so that the Operating system updates the widget
    val man = AppWidgetManager.getInstance(this)
    val ids = man.getAppWidgetIds(ComponentName(this, CoffeeLoggerWidget::class.java))
    val updateIntent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
    updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
    sendBroadcast(updateIntent)

    today = coffeeLoggerPersistence.loadTitlePref()
    gramsValue?.text = today.toString()
  }

  private fun saveCoffeeIntake(intake: Int) {
    val mySnackbar = Snackbar.make(findViewById<CoordinatorLayout>(R.id.main_coordinator),
        R.string.intake_saved, Snackbar.LENGTH_LONG)
    mySnackbar.setAction(R.string.undo_string, MyUndoListener(intake))
    mySnackbar.show()

    refreshTodayLabel()
  }

  inner class MyUndoListener(private val intake: Int) : View.OnClickListener {
    override fun onClick(v: View) {
      coffeeLoggerPersistence.saveTitlePref(today - intake)
      refreshTodayLabel()
    }
  }
}
