package com.stcodesapp.noteit.tasks.utilityTasks;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.stcodesapp.noteit.R;

public class InfoThroughWebViewDialogShowingTask
{
    private Activity activity;
    private AlertDialog alertDialog;
    private View screenView;
    private WebView webView;
    private ProgressBar loadingProgressBar;
    private TextView headerText;
    private ImageView closeButton;

    public InfoThroughWebViewDialogShowingTask(Activity activity)
    {
        this.activity = activity;
    }

    public void showWhyUpgradeDialog()
    {
        screenView = activity.getLayoutInflater().inflate(R.layout.info_through_web_view_layout,null,false);
        buildWhyUpgradeDialog();
        alertDialog = new AlertDialog.Builder(activity,android.R.style.Theme_Black_NoTitleBar_Fullscreen)
                .setView(screenView)
                .create();
        alertDialog.show();
    }

    private void buildWhyUpgradeDialog()
    {
        webView = screenView.findViewById(R.id.info_view);
        loadingProgressBar = screenView.findViewById(R.id.progressBar);
        headerText = screenView.findViewById(R.id.header_text);
        closeButton = screenView.findViewById(R.id.close_button);
        headerText.setText(activity.getResources().getString(R.string.why_upgrade));
        setWebViewClient();
        webView.setHorizontalScrollBarEnabled(true);
        webView.loadUrl("file:///android_asset/freemium_description.html");

    }

    public void showPrivacyPolicyDialog()
    {
        screenView = activity.getLayoutInflater().inflate(R.layout.info_through_web_view_layout,null,false);
        buildPrivacyPolicyDialog();
        alertDialog = new AlertDialog.Builder(activity,android.R.style.Theme_Black_NoTitleBar_Fullscreen)
                .setView(screenView)
                .create();
        alertDialog.show();
    }

    private void buildPrivacyPolicyDialog()
    {
        webView = screenView.findViewById(R.id.info_view);
        loadingProgressBar = screenView.findViewById(R.id.progressBar);
        headerText = screenView.findViewById(R.id.header_text);
        closeButton = screenView.findViewById(R.id.close_button);
        headerText.setText(activity.getResources().getString(R.string.privacy_policy));
        setWebViewClient();
        webView.setHorizontalScrollBarEnabled(true);
        webView.loadUrl("file:///android_asset/privacy_policy.html");

    }

    private void setWebViewClient()
    {
        setClickListener();
        webView.setWebViewClient(new WebViewClient()
        {
           /* public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
*/
            public void onPageFinished(WebView view, String url)
            {
                if (loadingProgressBar!=null) {
                    loadingProgressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setClickListener(){
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alertDialog!=null && alertDialog.isShowing())
                    alertDialog.dismiss();;
            }
        });
}
}
