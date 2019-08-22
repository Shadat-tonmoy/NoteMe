package com.stcodesapp.noteit.tasks.screenManipulationTasks.fragmentScreenManipulationTass;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.PermissionType;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.BackupFragmentScreenView;

public class BackupFragmentScreenManipulationTask
{

    private Activity activity;
    private BackupFragmentScreenView backupFragmentScreenView;
    private LinearLayout backupProgressView, backupDoneView;

    public BackupFragmentScreenManipulationTask(Activity activity) {
        this.activity = activity;
    }

    public void bindView(BackupFragmentScreenView backupFragmentScreenView) {
        this.backupFragmentScreenView = backupFragmentScreenView;
    }

    public void showPermissionRequiredMessage(PermissionType permissionType)
    {
        String message = activity.getResources().getString(R.string.permission_is_required);
        switch (permissionType)
        {
            case WRITE_EXTERNAL_STORAGE:
                message = activity.getResources().getString(R.string.write_storage_permission_is_required);
                break;
        }
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public void showBackupProgressDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.backup_progress_dialog_layout,null,false);
        TextView doneButton = view.findViewById(R.id.done_button);
        backupProgressView = view.findViewById(R.id.backup_progress_view);
        backupDoneView = view.findViewById(R.id.backup_done_view);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        alertDialog.show();
    }

    public void showBackupDoneView()
    {
        backupProgressView.setVisibility(View.GONE);
        backupDoneView.setVisibility(View.VISIBLE);
    }
}
