package com.stcodesapp.noteit.ui.fragments;

import android.os.Bundle;

public class ImportantNoteFragment extends HomeScreenFragment {


    public static ImportantNoteFragment newInstance()
    {
        Bundle args = new Bundle();
        ImportantNoteFragment importantNoteFragment = new ImportantNoteFragment();
        importantNoteFragment.setArguments(args);
        return importantNoteFragment;
    }


//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        navigationMenu1FragmentScreenView = getCompositionRoot().getViewFactory().getNavigationMenu1Screen(null);
////        secondaryScreenController= getCompositionRoot().getFragmentControllerFactory().getSecondaryController();
////        secondaryScreenController.bindView((NoteFieldScreenView) secondaryActivityScreenView);
////        ((AppCompatActivity)requireActivity()).setSupportActionBar(secondFragmentScreenView.getToolbar());
//        setHasOptionsMenu(true);
//        return navigationMenu1FragmentScreenView.getRootView();
//    }
//
//
    @Override
    public void onStart() {
        super.onStart();
        homeScreenController.onStartImportant();
    }

    @Override
    public void onStop() {
        super.onStop();
        homeScreenController.onStop();
//        secondaryScreenController.onStop();
    }
//
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.home_menu,menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
////        secondaryScreenController.onOptionMenuClicked(item.getItemId());
//        return true;
//    }
}
