package com.example.joan.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carson_Ho on 16/7/22.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles = new String[]{"律師", "咨詢", "法條","律所", "判決", "新聞與評論"};

    static private Fragment c, l, f, lr, j, n;

    public List<Fragment> fl = new ArrayList<>();

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        c = new SearchResultActivity.MainSearchResultCounsel();
        l = new SearchResultActivity.MainSearchResultLaw();
        f = new SearchResultActivity.MainSearchResultFirm();
        lr = new SearchResultActivity.MainSearchResultLawyer();
        j = new SearchResultActivity.MainSearchResultJudgement();
        n = new SearchResultActivity.MainSearchResultNews();
        fl.add(lr);
        fl.add(c);
        fl.add(l);
        fl.add(f);
        fl.add(j);
        fl.add(n);
    }

    @Override
    public Fragment getItem(int position) {
//        switch (position) {
//            case 0: return lr;
//            case 1: return c;
//            case 2: return l;
//            case 3: return f;
//            case 4: return j;
//            case 5: return n;
//            default: return lr;
//        }
        return fl.get(position);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    public void initView(){
        ((SearchResultActivity.MainSearchResultLawyer) lr).initView();
        ((SearchResultActivity.MainSearchResultCounsel) c).initView();
        ((SearchResultActivity.MainSearchResultLaw) l).initView();
        ((SearchResultActivity.MainSearchResultFirm) f).initView();
        ((SearchResultActivity.MainSearchResultJudgement) j).initView();
        ((SearchResultActivity.MainSearchResultNews) n).initView();
    }

    public void clear(){
        ((SearchResultActivity.MainSearchResultLawyer) lr).setFlag(1);
        ((SearchResultActivity.MainSearchResultCounsel) c).setFlag(1);
        ((SearchResultActivity.MainSearchResultLaw) l).setFlag(1);
        ((SearchResultActivity.MainSearchResultFirm) f).setFlag(1);
        ((SearchResultActivity.MainSearchResultJudgement) j).setFlag(1);
        ((SearchResultActivity.MainSearchResultNews) n).setFlag(1);
    }
}
