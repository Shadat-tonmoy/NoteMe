package com.stcodesapp.noteit.factory;

import com.stcodesapp.noteit.ui.fragments.AudioOptionsBottomSheets;
import com.stcodesapp.noteit.ui.fragments.ColorPallateBottomSheets;
import com.stcodesapp.noteit.ui.fragments.ImageOptionsBottomSheets;
import com.stcodesapp.noteit.ui.fragments.PhoneNoOptionsBottomSheets;

public class UIComponentFatory {

    public ColorPallateBottomSheets getColorPallateBottomSheets()
    {
        return new ColorPallateBottomSheets();
    }

    public PhoneNoOptionsBottomSheets getphoneNoOptionsBottomSheets()
    {
        return new PhoneNoOptionsBottomSheets();
    }

    public AudioOptionsBottomSheets getAudioOptionsBottomSheets()
    {
        return new AudioOptionsBottomSheets();
    }

    public ImageOptionsBottomSheets getImageOptionsBottomSheets()
    {
        return new ImageOptionsBottomSheets();
    }
}
