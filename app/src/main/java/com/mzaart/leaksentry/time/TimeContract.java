package com.mzaart.leaksentry.time;

import com.mzaart.leaksentry.base.BasePresenter;

public abstract class TimeContract {

    public abstract static class ViewActions extends BasePresenter<TimeView> {
        public abstract void startTimer();
    }

    public interface TimeView {
        void setTime(String timeStr);
    }
}
