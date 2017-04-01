package com.oneapplab.shopnow.utilService;
/*
Copyright © 2016 Ariba. All rights reserved.
*/

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.os.Build;

import java.util.Random;

public class ColorUtils {
    private static ColorUtils ourInstance = new ColorUtils();

    private Context context;

    public static ColorUtils getInstance() {
        return ourInstance;
    }

    private ColorUtils() {
    }

    private Random rand;

    public int getRandomColor() {
        // generate the random integers for r, g and b value

        if (rand == null) {
            rand = new Random();
        }

        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);

        int randomColor;
        randomColor = Color.rgb(r, g, b);

        return randomColor;
    }

    public ColorMatrixColorFilter getInverseColorFilter() {
        final float[] NEGATIVE = {
                -1.0f, 0, 0, 0, 255, // red
                0, -1.0f, 0, 0, 255, // green
                0, 0, -1.0f, 0, 255, // blue
                0, 0, 0, 1.0f, 0  // alpha
        };

        return new ColorMatrixColorFilter(NEGATIVE); // setColorFilter and to invalidate to change color
    }

    public int getColorFromResourceId(int code) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getColor(code);
        } else {
            return context.getResources().getColor(code, null);
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
