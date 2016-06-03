package www.sqindia.net.ilerasoftadmin;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTabHost;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cleveroad.slidingtutorial.PageFragment;
import com.cleveroad.slidingtutorial.TransformItem;


/**
 * Created by Ramya on 08-03-2016.
 */
public class AnalyticsLevel2_dash extends PageFragment
{

    private FragmentTabHost mTabHost;

    private View mRoot;

    @Override
    protected TransformItem[] provideTransformItems() {
        return new TransformItem[0];
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
       mRoot = inflater.inflate(R.layout.analytics_level2_dash, null);

        mTabHost = (FragmentTabHost)mRoot.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(),  getChildFragmentManager(), R.id.realtabcontent);
        View tabView1 = createTabView(getActivity(), "QUANTITY");
        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator(tabView1), Analytics1_quantity.class, null);
        View tabView = createTabView1(getActivity(), "EFFICIENCY");
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator(tabView), Analytics1_efficiencyscore.class, null);
        mTabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.rgb(00, 219, 239));
        return mRoot;

    }
    private static View createTabView1(Context context, String tabText)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.quantity, null, true);
        TextView tv = (TextView)view.findViewById(R.id.tabTitleText1);
        tv.setText(tabText);

        return view;

    }
    private static View createTabView(Context context, String tabText)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.efficiency, null, true);
        TextView tv = (TextView) view.findViewById(R.id.tabTitleText);
        tv.setText(tabText);
        return view;
    }


}