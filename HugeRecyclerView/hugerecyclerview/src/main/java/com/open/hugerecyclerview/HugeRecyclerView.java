package com.open.hugerecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * huge recycler view control
 */
public class HugeRecyclerView extends RecyclerView {

    public HugeRecyclerView(Context context) {
        super(context);
        init(null, 0);
    }

    public HugeRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public HugeRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.HugeRecyclerView, defStyle, 0);

//        mExampleString = a.getString(
//                R.styleable.HugeRecyclerView_exampleString);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
//        mExampleDimension = a.getDimension(
//                R.styleable.HugeRecyclerView_exampleDimension,
//                mExampleDimension);

//        if (a.hasValue(R.styleable.HugeRecyclerView_exampleDrawable)) {
//            mExampleDrawable = a.getDrawable(
//                    R.styleable.HugeRecyclerView_exampleDrawable);
//            mExampleDrawable.setCallback(this);
//        }

        a.recycle();
    }
}
