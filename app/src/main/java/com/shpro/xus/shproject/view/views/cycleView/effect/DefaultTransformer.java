package com.shpro.xus.shproject.view.views.cycleView.effect;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.nineoldandroids.view.ViewPropertyAnimator;

public class DefaultTransformer implements PageTransformer {

	@Override
	public void transformPage(View view, float arg1) {
		/*
		ViewPropertyAnimator.animate(view).alpha(1.0f).setDuration(1000).start();
		ViewPropertyAnimator.animate(view).translationX(0).setDuration(1000).start();
		ViewPropertyAnimator.animate(view).translationY(0).animate(view).scaleX(1).scaleY(1).setDuration(1000).start();*/

		ViewPropertyAnimator.animate(view).translationXBy(0).translationYBy(0).setDuration(1000).setInterpolator(new DecelerateInterpolator()).start();
		/*
		view.setAlpha(1);
		view.setTranslationX(0);
		view.setTranslationY(0);
		view.setPivotX(view.getWidth() / 2);
		view.setPivotY(view.getHeight() / 2);
		view.setScaleX(1);
		view.setScaleY(1);
		view.setRotation(0);
		*/
	}

}
