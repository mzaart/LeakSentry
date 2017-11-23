package com.mzaart.leaksentry.mvp.time;

import com.mzaart.leaksentry.mvp.BasePresenter;

public abstract class TimeContract {

    public abstract static class ViewActions extends BasePresenter<TimeView> {
        public abstract void startTimer();
    }

    public interface TimeView {
        void setTime(String timeStr);
    }
}
