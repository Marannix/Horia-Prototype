package com.example.android.horiavideoprototype;

import android.support.annotation.AnimRes;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class Animator {

  public interface Listener {
    Listener NULL = () -> {
      // Empty
    };

    void onAnimationEnd();
  }

  private Animator() {
    //
  }

  public static Animation load(View view, @AnimRes int anim) {
    return AnimationUtils.loadAnimation(view.getContext(), anim);
  }

  private static void animateOutAndRemove(View view, @AnimRes int anim) {
    animateOutAndRemove(view, anim, 0);
  }

  private static void animateOutAndRemove(View view, @AnimRes int anim, long startOffset) {
    if (view.getVisibility() == View.VISIBLE) {
      final Animation outAnimation = load(view, anim);
      outAnimation.setStartOffset(startOffset);
      outAnimation.setInterpolator(new FastOutSlowInInterpolator());
      outAnimation.setAnimationListener(new SimpleAnimationListener() {
        @Override public void onAnimationEnd(Animation animation) {
          view.setVisibility(View.GONE);
        }
      });
      view.startAnimation(outAnimation);
    }
  }

  public static void shrinkFadeOutRemove(View view) {
    animateOutAndRemove(view, android.support.v7.appcompat.R.anim.abc_shrink_fade_out_from_bottom);
  }
}
