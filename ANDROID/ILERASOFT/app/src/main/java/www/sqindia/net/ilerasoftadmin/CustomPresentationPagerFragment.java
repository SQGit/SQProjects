package www.sqindia.net.ilerasoftadmin;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;

import android.widget.TextView;


import com.cleveroad.slidingtutorial.PageFragment;
import com.cleveroad.slidingtutorial.SimplePagerFragment;

public class CustomPresentationPagerFragment extends SimplePagerFragment
{

	@Override
	protected int getPagesCount() {

		return 1;
	}

	@Override
	protected PageFragment getPage(int position) {

		if (position == 0)
			return new Analytics();

		/*if (position == 1)
			return new Analytics2_bar();
*/

		throw new IllegalArgumentException("Unknown position: " + position);
	}

	@ColorInt
	@Override
	protected int getPageColor(int position) {
		if (position == 0)
			return ContextCompat.getColor(getContext(), android.R.color.transparent);
		if (position == 1)
			return ContextCompat.getColor(getContext(),android.R.color.transparent);

		return Color.TRANSPARENT;
	}

	@Override
	protected boolean isInfiniteScrollEnabled()
	{

		return true;
	}


}
