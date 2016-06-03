package www.sqindia.net.ilerasoftadmin;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by RSA on 2/8/2016.
 */
public class Category_search implements SearchSuggestion {
    public String Category_name;
    public String Category_id;
    @Override
    public String getBody() {
        return Category_name;
    }
    public  Category_search(String cn,String cid) {
        Category_name=cn;Category_id=cid;
    }
    @Override
    public Creator getCreator() {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Category_name);
    }
}