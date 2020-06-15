package com.chs.lib_common_ui.webview

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.chs.lib_common_ui.R
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import kotlinx.android.synthetic.main.act_web.*

/**
 * author：chs
 * date：2020/6/15
 * des：
 */
class BaseWebActivity : AppCompatActivity(){

    private var mAgentWeb: AgentWeb? = null
    private val mWebViewClient: WebViewClient =
        object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                //do you  work
                Log.i("Info", "BaseWebActivity onPageStarted")
            }
        }
    private val mWebChromeClient: WebChromeClient =
        object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)
                if (toolbar_title != null) {
                    toolbar_title.text = title
                }
            }
        }

    companion object{
        private const val KEY_URL = "key_url"
        fun start(context:Context,url:String?){
            val intent = Intent(context,BaseWebActivity::class.java)
            intent.putExtra(KEY_URL,url)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_web)
        val url = intent.getStringExtra(KEY_URL)
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(container, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .setWebChromeClient(mWebChromeClient)
//            .setWebViewClient(mWebViewClient)
            .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setWebLayout(SmartRefreshWebLayout(this))
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK) //打开其他应用时，弹窗咨询用户是否前往其他应用
            .interceptUnkownUrl() //拦截找不到相关页面的Scheme
            .createAgentWeb()
            .ready()
            .go(url)
    }

    override fun onPause() {
        mAgentWeb!!.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb!!.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mAgentWeb!!.webLifeCycle.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (mAgentWeb!!.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }
}