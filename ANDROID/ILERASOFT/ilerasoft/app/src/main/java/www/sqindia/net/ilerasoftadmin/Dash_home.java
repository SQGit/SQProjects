package www.sqindia.net.ilerasoftadmin;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by ANANDH on 12-12-2015.
 */
public class Dash_home extends Fragment {
    ImageView scan,tracking,schedule,inventory;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.dash_one, container, false);
        scan=(ImageView)root.findViewById(R.id.scan);
        tracking=(ImageView)root.findViewById(R.id.tracking);

        schedule=(ImageView)root.findViewById(R.id.schudule);
        inventory=(ImageView)root.findViewById(R.id.inventory);
        scan.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getActivity(),ScanEquipment.class);
                startActivity(i);

            }
        });
        tracking.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getActivity(), TrackingDevice.class);
                startActivity(i);

            }
        });
        schedule.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getActivity(),Schedule_Test.class);
                startActivity(i);

            }
        });
        inventory.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getActivity(),InventoryControl.class);
                startActivity(i);

            }
        });
        return root;
    }


}

