package www.sqindia.net.ilerasoftadmin;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by RSA on onew/8/2016.
 */
public class Tab_one extends Fragment
{
String ImagePath;
    ImageView img;
    Bitmap bitmap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab1, container, false);
        ImagePath=BookingFragment.category_Image;
        img = (ImageView)root.findViewById(R.id.img);
        int loader = R.drawable.d;
        Log.d("tag", "<-----ImagePath------>" + ImagePath);
        //StringToBitMap(ImagePath);

        ImageLoader imgLoader = new ImageLoader(getActivity());

        // whenever you want to load an image from url
        // call DisplayImage function
        // url - image url to load
        // loader - loader image, will be displayed before getting image
        // image - ImageView
        imgLoader.DisplayImage(ImagePath, loader, img);

        /*if(bitmap != null)
        {
            img.setImageBitmap(bitmap);

        }else{

            Toast.makeText(getActivity(), "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

        }
*/


        return root;
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
             bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;


        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}
