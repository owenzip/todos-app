package com.example.gamma.todoapp;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by Bean on 17/01/2018.
 */
public class AnimationEffect {

    public static Animation animCircular (Context context) {
        Animation animCircular = AnimationUtils.loadAnimation(context,R.anim.anim_circular);
        return animCircular;
    }

    public static Animation animHideToZoom (Context context) {
        Animation animHideToZoom = AnimationUtils.loadAnimation(context, R.anim.anim_hide_to_zoom);
        return animHideToZoom;
    }

    public static Animation animLeftToRight (Context context) {
        Animation animLeftToRight = AnimationUtils.loadAnimation(context, R.anim.anim_left_to_right);
        return animLeftToRight;
    }

    public static Animation animTopToBottom (Context context) {
        Animation animTopToBottom = AnimationUtils.loadAnimation(context, R.anim.anim_top_to_bottom);
        return animTopToBottom;
    }
}
