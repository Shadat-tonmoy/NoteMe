package com.stcodesapp.noteit.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.controllers.fragmentController.CheckListFragmentController;
import com.stcodesapp.noteit.controllers.fragmentController.EmailFragmentController;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.CheckListFragmentScreenView;
import com.stcodesapp.noteit.ui.views.screenViews.fragmentScreenView.EmailFragmentScreenView;

public class CheckListFragment extends BaseFragment {

    private CheckListFragmentScreenView checkListFragmentScreenView;
    private CheckListFragmentController checkListFragmentController;

    public static CheckListFragment newInstance()
    {
        Bundle args = new Bundle();
        CheckListFragment emailsFragment = new CheckListFragment();
        emailsFragment.setArguments(args);
        return emailsFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        checkListFragmentScreenView = getCompositionRoot().getViewFactory().getCheckListFragmentScreenView(null);
        checkListFragmentController = getCompositionRoot().getFragmentControllerFactory().getCheckListFragmentController();
        checkListFragmentController.bindView(checkListFragmentScreenView);
        setHasOptionsMenu(true);
        return checkListFragmentScreenView.getRootView();
    }


    @Override
    public void onStart() {
        super.onStart();
        checkListFragmentController.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        checkListFragmentController.onStop();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.checklist_list_menu,menu);
        checkListFragmentScreenView.onCreateOptionMenu(menu, (MaterialSearchView) requireActivity().findViewById(R.id.search_view));
        checkListFragmentScreenView.getSearchView().setOnQueryTextListener(checkListFragmentController);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        secondaryScreenController.onOptionMenuClicked(item.getItemId());
        return true;
    }

//    public boolean onBackPressed()
//    {
//        return emailFragmentController.onBackPressed();
//    }
}
