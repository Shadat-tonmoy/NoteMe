package com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.adapter.PhoneEmailListAdapter;
import com.stcodesapp.noteit.constants.ComponentType;
import com.stcodesapp.noteit.constants.EventTypes;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreenView;
import com.stcodesapp.noteit.ui.views.screens.fragmentScreen.BackupFragmentScreen;
import com.stcodesapp.noteit.ui.views.screens.fragmentScreen.EmailFragmentScreen;

import java.util.List;

public class BackupFragmentScreenView extends BaseObservableScreenView<BackupFragmentScreen.Listener> implements BackupFragmentScreen
{

    private ConstraintLayout backupToLocalStorageButton,backupToCloudStorageButton,restoreFromLocalStorageButton,restoreFromCloudStorageButton;
    private RadioGroup storageOption;
    private AdView adMobBannerAdView;

    public BackupFragmentScreenView(LayoutInflater layoutInflater, @Nullable ViewGroup parent)
    {
        setRootView(layoutInflater.inflate(R.layout.backup_fragment_layout,parent,false));
        inflateUIElements();
        initUserInteractions();
    }

    @Override
    public void initUserInteractions()
    {
        setClickListener(backupToLocalStorageButton,EventTypes.BACKUP_TO_LOCAL_STORAGE_BUTTON_CLICKED);
        setClickListener(restoreFromLocalStorageButton,EventTypes.RESTORE_FROM_LOCAL_STORAGE_BUTTON_CLICKED);
        setClickListener(backupToCloudStorageButton,EventTypes.BACKUP_TO_CLOUD_STORAGE_BUTTON_CLICKED);
        setClickListener(restoreFromCloudStorageButton,EventTypes.RESTORE_FROM_CLOUD_STORAGE_BUTTON_CLICKED);
        storageOption.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for(Listener listener:getListeners())
                {
                    listener.onLocalBackupStorageOptionSelected(checkedId);
                }
            }
        });

    }

    @Override
    public void inflateUIElements()
    {
        backupToLocalStorageButton = findViewById(R.id.local_backup_button);
        restoreFromLocalStorageButton = findViewById(R.id.local_restore_button);
        backupToCloudStorageButton = findViewById(R.id.cloud_backup_button);
        restoreFromCloudStorageButton = findViewById(R.id.cloud_restore_button);
        storageOption = findViewById(R.id.storage_option_radio);
        adMobBannerAdView = findViewById(R.id.admob_banner_ad_view);


    }

    private void setClickListener(View view, final int eventTypes)
    {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener:getListeners())
                {
                    switch (eventTypes)
                    {

                        case EventTypes.BACKUP_TO_LOCAL_STORAGE_BUTTON_CLICKED:
                            listener.onBackupToLocalStorageClicked();
                            break;
                        case EventTypes.RESTORE_FROM_LOCAL_STORAGE_BUTTON_CLICKED:
                            listener.onRestoreFromLocalStorageClicked();
                            break;
                        case EventTypes.BACKUP_TO_CLOUD_STORAGE_BUTTON_CLICKED:
                            listener.onBackupToCloudStorageClicked();
                            break;
                        case EventTypes.RESTORE_FROM_CLOUD_STORAGE_BUTTON_CLICKED:
                            listener.onRestoreFromCloudStorageClicked();
                            break;
                    }
                }

            }
        });

    }

    public AdView getAdMobBannerAdView() {
        return adMobBannerAdView;
    }
}
