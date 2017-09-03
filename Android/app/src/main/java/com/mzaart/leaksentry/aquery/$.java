package com.mzaart.leaksentry.aquery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mzaart.leaksentry.aquery.exceptions.IllegalParentException;
import com.mzaart.leaksentry.aquery.exceptions.IllegalViewActionException;
import com.mzaart.leaksentry.aquery.exceptions.ViewNotFoundException;
import com.mzaart.leaksentry.aquery.interfaces.Executable;
import com.mzaart.leaksentry.aquery.interfaces.OnClickListener;

@SuppressWarnings({"unused", "WeakerAccess", "Convert2Lambda", "Anonymous2MethodRef"})
public class $ {

    private Activity activity;
    private View raw;

    /**
     * Instantiates an AQuery instance from a activity and sets
     * the Activity's root view as the current view.
     *
     * @param activity Activity activity
     *
     * @exception IllegalArgumentException If activity is null.
     * @see  IllegalArgumentException
     */
     $(@NonNull Activity activity) {
        Utils.requireNotNull(activity);

        this.activity = activity;
        this.raw = activity.findViewById(android.R.id.content);
    }

    /**
     * Instantiates an AQuery instance from a view.
     *
     * @param raw The view to be used.
     *
     * @exception IllegalArgumentException If view is null.
     * @see  IllegalArgumentException
     */
     $(@NonNull View raw) {
        Utils.requireNotNull(raw);

        this.raw = raw;
        this.activity = (Activity) raw.getContext();
    }

    /**
     * Instantiates an AQuery instance from a view given its Id.
     * Throws ViewNotFoundException if the view doesn't exist or the id is invalid.
     *
     * @param activity The Activity's activity.
     * @param id The view's id.
     *
     * @exception ViewNotFoundException If the view doesn't exist or the id is invalid.
     * @see  ViewNotFoundException
     *
     * @exception IllegalArgumentException If activity is null.
     * @see  IllegalArgumentException
     */
     $(@NonNull Activity activity, int id) {
        this.activity = activity;
        this.raw = activity.findViewById(id);

        if (raw == null)
            throw new ViewNotFoundException();
    }

    /**
     * Instantiates an AQuery instance from a view given its Id in the parent view.
     * Throws ViewNotFoundException if the view doesn't exist or the id is invalid.
     *
     * @param view The view's parent.
     * @param id The view's id.
     *
     * @exception ViewNotFoundException If the view doesn't exist or the id is invalid.
     * @see  ViewNotFoundException
     *
     * @exception IllegalArgumentException If view is null.
     * @see  IllegalArgumentException
     */
     $(View view, int id) {
        Utils.requireNotNull(view);

        this.activity = (Activity) view.getContext();
        this.raw = view.findViewById(id);

        if (raw == null)
            throw new ViewNotFoundException();
    }

    /**
     * Instantiates an AQuery instance from a view given its Id in an AQuery object.
     * Throws ViewNotFoundException if the view doesn't exist or the id is invalid.
     *
     * @param aquery The containing AQuery object.
     * @param id The view's id.
     *
     * @exception ViewNotFoundException If the view doesn't exist or the id is invalid.
     * @see  ViewNotFoundException
     *
     * @exception IllegalArgumentException If AQuery is null.
     * @see  IllegalArgumentException
     */
     $($ aquery, int id) {
        Utils.requireNotNull(aquery);

        this.activity = aquery.activity;
        this.raw = aquery.raw.findViewById(id);

        if (raw == null)
            throw new ViewNotFoundException();
    }

    /**
     * Returns base view of the AQuery object.
     *
     * @return View The base view of the AQuery object.
     */
    public View raw() {
        return raw;
    }

    /**
     * Returns an AQuery object containing a view with a specific id.
     *
     * @param  id The View's Id.
     * @return View The base view of the AQuery object.
     *
     * @exception ViewNotFoundException If the view doesn't exist or the id is invalid.
     * @see  ViewNotFoundException
     */
    public $ find(int id) {
        View target =  raw.findViewById(id);

        if (target == null)
            throw new ViewNotFoundException();

        return new $(target);
    }

    /**
     * Returns an AQuery object containing the view's parent.
     *
     * @return $ AQuery object containing the view's parent.
     *
     * @exception IllegalParentException If the view's parent isn't a view.
     * @see IllegalParentException
     */
    public $ parent() {
        try {
            return new $((View) raw.getParent());
        } catch (ClassCastException e) {
            throw new IllegalParentException();
        }
    }

    /**
     * Removes the view.
     *
     * @exception RuntimeException If the View can't be removed.
     * @see  RuntimeException
     */
    public void remove() {
        try {
            ((ViewGroup) raw.getParent()).removeView(raw);
        } catch (ClassCastException e) {
            throw new RuntimeException("Can't remove view.");
        }
    }

    /**
     * Appends a view to the current AQuery's view.
     *
     * @param v The view to append.
     * @return The current AQuery object.
     *
     * @exception IllegalArgumentException If the view passed is null.
     * @see IllegalArgumentException
     * @exception IllegalViewActionException If the view isn't a ViewGroup
     * @see IllegalViewActionException
     */
    public $ append(@NonNull View v) {
        Utils.requireNotNull(v);
        try {
            ((ViewGroup) raw).addView(v);
            return this;
        } catch (ClassCastException e) {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Appends a view to the current AQuery's view.
     *
     * @param v The AQuery object containing the view to append.
     * @return The current AQuery object.
     *
     * @exception IllegalArgumentException If the AQuery passed is null.
     * @see IllegalArgumentException
     * @exception IllegalViewActionException If the view isn't a ViewGroup
     * @see IllegalViewActionException
     */
    public $ append(@NonNull $ v) {
        Utils.requireNotNull(v);

        try {
            ((ViewGroup) raw).addView(v.raw);
            return this;
        } catch (ClassCastException e) {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Appends a view to the current AQuery's view.
     *
     * @param v The view to append.
     * @param width The appended view's width
     * @param height The appended view's height
     * @return The current AQuery object.
     *
     * @exception IllegalArgumentException If the view passed is null.
     * @see IllegalArgumentException
     * @exception IllegalViewActionException If the view isn't a ViewGroup
     * @see IllegalViewActionException
     */
    public $ append(View v, int width, int height) {
        Utils.requireNotNull(v);

        try {
            ((ViewGroup) raw).addView(v, width, height);
            return this;
        } catch (ClassCastException e) {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Appends a view to the current AQuery's view.
     *
     * @param v The AQuery object containing the view to append.
     * @param width The appended view's width
     * @param height The appended view's height
     * @return The current AQuery object.
     *
     * @exception IllegalArgumentException If the AQuery passed is null.
     * @see IllegalArgumentException
     * @exception IllegalViewActionException If the view isn't a ViewGroup
     * @see IllegalViewActionException
     */
    public $ append($ v, int width, int height) {
        Utils.requireNotNull(v);

        try {
            ((ViewGroup) raw).addView(v.raw, width, height);
            return this;
        } catch (ClassCastException e) {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Appends a view to the current AQuery's view.
     *
     * @param v The view to append.
     * @param params The appended view's layout params.
     * @return The current AQuery object.
     *
     * @exception IllegalArgumentException If the view or layout params passed are null.
     * @see IllegalArgumentException
     * @exception IllegalViewActionException If the view isn't a ViewGroup
     * @see IllegalViewActionException
     */
    public $ append(View v, ViewGroup.LayoutParams params) {
        Utils.requireNotNull(v, params);

        try {
            ((ViewGroup) raw).addView(v, params);
            return this;
        } catch (ClassCastException e) {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Appends a view to the current AQuery's view.
     *
     * @param v The AQuery object containing the view to append.
     * @param params The appended view's layout params.
     * @return The current AQuery object.
     *
     * @exception IllegalArgumentException If the AQuery or layout params passed are null.
     * @see IllegalArgumentException
     * @exception IllegalViewActionException If the view isn't a ViewGroup
     * @see IllegalViewActionException
     */
    public $ append($ v, ViewGroup.LayoutParams params) {
        Utils.requireNotNull(v, params);

        try {
            ((ViewGroup) raw).addView(v.raw, params);
            return this;
        } catch (ClassCastException e) {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Appends a view to the current AQuery's view.
     *
     * @param v The view to append.
     * @param index The index to append the view at.
     * @return The current AQuery object.
     *
     * @exception IllegalArgumentException If the view passed is null.
     * @see IllegalArgumentException
     * @exception IllegalViewActionException If the view isn't a ViewGroup
     * @see IllegalViewActionException
     */
    public $ append(View v, int index) {
        Utils.requireNotNull(v);

        try {
            ((ViewGroup) raw).addView(v, index);
            return this;
        } catch (ClassCastException e) {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Appends a view to the current AQuery's view.
     *
     * @param v The AQuery object containing the view to append.
     * @param index The index to append the view at.
     * @return The current AQuery object.
     *
     * @exception IllegalArgumentException If the AQuery passed is null.
     * @see IllegalArgumentException
     * @exception IllegalViewActionException If the view isn't a ViewGroup
     * @see IllegalViewActionException
     */
    public $ append($ v, int index) {
        Utils.requireNotNull(v);

        try {
            ((ViewGroup) raw).addView(v.raw, index);
            return this;
        } catch (ClassCastException e) {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Appends a view to the current AQuery's view.
     *
     * @param v The view to append.
     * @param index The index to append the view at.
     * @param params The appended view's layout params.
     * @return The current AQuery object.
     *
     * @exception IllegalArgumentException If the view or layout params passed are null.
     * @see IllegalArgumentException
     * @exception IllegalViewActionException If the view isn't a ViewGroup
     * @see IllegalViewActionException
     */
    public $ append(View v, int index, ViewGroup.LayoutParams params) {
        Utils.requireNotNull(v, params);

        try {
            ((ViewGroup) raw).addView(v, index, params);
            return this;
        } catch (ClassCastException e) {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Appends a view to the current AQuery's view.
     *
     * @param v The AQuery object containing the view to append.
     * @param index The index to append the view at.
     * @param params The appended view's layout params.
     * @return The current AQuery object.
     *
     * @exception IllegalArgumentException If the AQuery or layout params passed are null.
     * @see IllegalArgumentException
     * @exception IllegalViewActionException If the view isn't a ViewGroup
     * @see IllegalViewActionException
     */
    public $ append($ v, int index, ViewGroup.LayoutParams params) {
        Utils.requireNotNull(v, params);

        try {
            ((ViewGroup) raw).addView(v.raw, index, params);
            return this;
        } catch (ClassCastException e) {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Sets an on-click listener.
     *
     * @param onClickListener The onClickListener to be executed when the view is clicked.
     * @return $ Current AQuery object.
     *
     * @exception IllegalArgumentException If the onClickListener passed is null.
     * @see IllegalArgumentException
     */
    public $ click(@NonNull OnClickListener onClickListener) {
        Utils.requireNotNull(onClickListener);
        raw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClick(view);
            }
        });

        return this;
    }

    public $ ready(@NonNull Executable executable) {
        Utils.requireNotNull(executable);
        ViewTreeObserver treeObserver = raw.getViewTreeObserver();
        treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressLint("ObsoleteSdkInt")
            @Override
            public void onGlobalLayout() {
                executable.execute();

                if (Build.VERSION.SDK_INT < 16)
                    raw.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                else
                    raw.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        return this;
    }

    /**
     * Returns the text of the TextView.
     *
     * @exception IllegalViewActionException If the view isn't a TextView.
     * @see IllegalViewActionException
     */
    public CharSequence text() {
        try {
            return ((TextView) raw).getText();
        } catch (ClassCastException e) {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Sets the text of the TextView.
     *
     * @param  text The text to set.
     * @return $ Current AQuery object
     *
     * @exception IllegalViewActionException If the view isn't a TextView.
     * @see IllegalViewActionException
     */
    public $ text(String text) {
        try {
            ((TextView) raw).setText(text);
            return this;
        } catch (ClassCastException e) {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Sets a Bitmap to an ImageView.
     *
     * @param  bitmap The bitmap to set.
     * @return $ Current AQuery object
     *
     * @exception IllegalViewActionException If the view isn't an ImageView.
     * @see IllegalViewActionException
     * @exception IllegalArgumentException If the bitmap passed is null.
     * @see IllegalArgumentException
     */
    public $ bitmap(Bitmap bitmap) {
        Utils.requireNotNull(bitmap);

        try {
            ((ImageView) raw).setImageBitmap(bitmap);
            return this;
        } catch (ClassCastException e) {
            throw new IllegalViewActionException();
        }
    }

    /**
     * Gets the width of the view.
     *
     * @return int the width of the view.
     */
    public int width() {
        return raw.getWidth();
    }

    /**
     * Sets the view's width.
     *
     * @param width The new width.
     * @return The current AQuery object.
     */
    public $ width(int width) {
        ViewGroup.LayoutParams params = raw.getLayoutParams();
        params.width = width;
        raw.requestLayout();
        return this;
    }

    /**
     * Gets the height of the view.
     *
     * @return int the height of the view.
     */
    public int height() {
        return raw.getHeight();
    }

    /**
     * Sets the view's width.
     *
     * @param height The new height.
     * @return The current AQuery object.
     */
    public $ height(int height) {
        ViewGroup.LayoutParams params = raw.getLayoutParams();
        params.height = height;
        raw.requestLayout();
        return this;
    }


    /**
     * Inflates a layout. Note that this method doesn't attach the layout to its parent.
     *
     * @param activity The required activity.
     * @param id The layout's Id.
     * @param parent The layout's parent.
     * @return An AQuery object containing the inflated layout.
     *
     * @exception IllegalArgumentException If the activity or parent is null.
     * @see IllegalArgumentException
     */
    public static  $ inflate(@NonNull Activity activity, int id, @NonNull ViewGroup parent) {
        Utils.requireNotNull(activity, parent);
        return inflate(activity, id, parent, false);
    }

    /**
     * Inflates a layout. Note that this method doesn't attach the layout to its parent.
     *
     * @param activity The required activity.
     * @param id The layout's Id.
     * @param parent The layout's parent.
     * @return An AQuery object containing the inflated layout.
     *
     * @exception IllegalArgumentException If the activity or parent is null or
     * the parent view is a ViewGroup.
     * @see IllegalArgumentException
     */
    public static $ inflate(@NonNull Activity activity, int id, @NonNull $ parent) {
        Utils.requireNotNull(activity, parent);
        return inflate(activity, id, parent, false);
    }

    /**
     * Inflates a layout.
     *
     * @param activity The required activity.
     * @param id The layout's Id.
     * @param parent The layout's parent.
     * @param attachToParent If true the inflated layout will be attached to its parent.
     * @return An AQuery object containing the inflated layout.
     *
     * @exception IllegalArgumentException If the activity or parent is null.
     * @see IllegalArgumentException
     */
    public static $ inflate(Activity activity, int id, ViewGroup parent, boolean attachToParent) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View layout = inflater.inflate(id, parent, attachToParent);
        return new $(layout);
    }

    /**
     * Inflates a layout.
     *
     * @param activity The required activity.
     * @param id The layout's Id.
     * @param parent The AQuery object containing the layout's parent.
     * @param attachToParent If true the inflated layout will be attached to its parent.
     * @return An AQuery object containing the inflated layout.
     *
     * @exception IllegalArgumentException If the activity or parent is null or
     * the parent view isn't a ViewGroup.
     * @see IllegalArgumentException
     */
    public static $ inflate(Activity activity, int id, $ parent, boolean attachToParent) {
        try {
            LayoutInflater inflater = LayoutInflater.from(activity);
            View layout = inflater.inflate(id, (ViewGroup) parent.raw, attachToParent);
            return new $(layout);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("The parent view isn't a ViewGroup.");
        }
    }

    /**
     * Displays a toast message. Note that the toast's duration is Toast.LENGTH_SHORT. Call
     * toastLong() to display a toast with duration Toast.LENGTH_LONG.
     *
     * @param activity The required activity.
     * @param msg The message to display.
     *
     * @exception IllegalArgumentException If activity or msg is null.
     * @see IllegalArgumentException
     */
    public static void toast(Activity activity, String msg) {
        Utils.requireNotNull(activity, msg);
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Displays a toast message. Note that the toast's duration is Toast.LENGTH_LONG. Call
     * toast() to display a toast with duration Toast.LENGTH_SHORT.
     *
     * @param activity The required activity.
     * @param msg The message to display.
     *
     * @exception IllegalArgumentException If activity or msg is null.
     * @see IllegalArgumentException
     */
    public static void toastLong(Activity activity, String msg) {
        Utils.requireNotNull(activity, msg);
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
    }
}
