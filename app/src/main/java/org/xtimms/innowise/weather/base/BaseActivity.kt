package org.xtimms.innowise.weather.base

import android.os.Build
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import org.xtimms.innowise.weather.R

abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: B
        private set

    protected fun setContentView(binding: B) {
        this.binding = binding
        super.setContentView(binding.root)
        val toolbar = (binding.root.findViewById<View>(R.id.toolbar) as? Toolbar)
        toolbar?.let(this::setSupportActionBar)
    }

    override fun onBackPressed() {
        if ( // https://issuetracker.google.com/issues/139738913
            Build.VERSION.SDK_INT == Build.VERSION_CODES.Q &&
            isTaskRoot &&
            supportFragmentManager.backStackEntryCount == 0
        ) {
            finishAfterTransition()
        } else {
            super.onBackPressed()
        }
    }

}