package com.example.myapplication.model;

import java.util.List;

public class _Quiz {
    public _Trolls trolls;
    public List<_Math> maths;

    public _Quiz(_Trolls trolls, List<_Math> maths) {
        this.trolls = trolls;
        this.maths = maths;
    }

    public _Trolls getTrolls() {
        return trolls;
    }

    public void setTrolls(_Trolls trolls) {
        this.trolls = trolls;
    }

    public List<_Math> getMaths() {
        return maths;
    }

    public void setMaths(List<_Math> maths) {
        this.maths = maths;
    }
}
