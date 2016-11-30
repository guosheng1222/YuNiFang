package com.bwie.factory;

import android.support.v4.app.Fragment;

import com.bwie.fragment.CartFragment;
import com.bwie.fragment.CategoryFragment;
import com.bwie.fragment.HomeFragment;
import com.bwie.fragment.MineFragment;

import java.util.HashMap;

/**
 * Created by PC on 2016/11/28.
 */
public class FragmentFactory {
    public static HashMap<Integer,Fragment> fragmentHashMap=new HashMap<>();
    public static Fragment getFragment(int position)
    {
        Fragment fragment = fragmentHashMap.get(position);
        if(fragment!=null)
        {
            return  fragment;
        }
        switch(position)
        {
            case 0:
                fragment=new HomeFragment();
                break;
            case 1:
                fragment=new CategoryFragment();
                break;
            case 2:
                fragment=new CartFragment();
                break;
            case 3:
                fragment=new MineFragment();
                break;
        }
        fragmentHashMap.put(position,fragment);
        return fragment;
    }
}
