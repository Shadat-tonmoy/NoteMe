package com.stcodesapp.noteit.constants;

import com.stcodesapp.noteit.R;

import java.util.HashMap;
import java.util.Map;

public class BackgroundColors {

    public static final Integer[] colorIds = {
            R.drawable.round_shape_gray,
            R.drawable.round_shape_light_red, R.drawable.round_shape_black,
            R.drawable.round_shape_blue, R.drawable.round_shape_purple,
            R.drawable.round_shape_green, R.drawable.round_shape_blaze_hole,
            R.drawable.round_shape_pumpkin, R.drawable.round_shape_carrot};

    public static final String[] colorNames = {
            "Gray","Red","Black","Blue","Purple","Green","Blaze Hole","Pumpkin","Carrot"
    };


    public static Map<String,Integer> getColorMaps()
    {
        final Map<String,Integer> colorMaps = new HashMap<>();
        colorMaps.put("Gray",R.drawable.round_shape_gray);
        colorMaps.put("Red",R.color.lightRed);
        colorMaps.put("Black",R.color.black);
        colorMaps.put("Blue",R.color.blue);
        colorMaps.put("Purple",R.color.purple700);
        colorMaps.put("Green",R.color.green);
        colorMaps.put("Blaze Hole",R.color.blaze_hole);
        colorMaps.put("Pumpkin",R.color.pumpkin);
        colorMaps.put("Carrot",R.color.carrot);
        return colorMaps;
    }



}
