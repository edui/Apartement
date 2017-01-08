package id.co.kurindo.apartment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import id.co.kurindo.apartment.base.BaseFragment;
import id.co.kurindo.apartment.model.ImageModel;
import id.co.kurindo.apartment.util.DummyData;

/**
 * Created by aspire on 12/23/2016.
 */

public class WarjokFragment extends BaseFragment implements BaseSliderView.OnSliderClickListener {
    @Bind(R.id.slider1)
    SliderLayout sliderShow1;
    @Bind(R.id.slider2)
    SliderLayout sliderShow2;
    @Bind(R.id.slider3)
    SliderLayout sliderShow3;
    @Bind(R.id.progress)
    ProgressBar progressBar;

    ArrayList<ImageModel> data = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflateAndBind(inflater, container, R.layout.fragment_warjok);

        sliderShow1.setDuration(5000);
        sliderShow2.setDuration(6000);
        sliderShow3.setDuration(7000);
        progressBar.setVisibility(View.GONE);
        onPostExecute(null);
        return v;
    }
    public void onPostExecute(Object o) {
        List<ImageModel> newsList = new ArrayList<>();
        Log.i("loadNewsTask","newsList size:"+newsList.size());
        sliderShow1.removeAllSliders();
        sliderShow2.removeAllSliders();
        sliderShow3.removeAllSliders();
        newsList.addAll(DummyData.ads);
        data.clear();
        data.addAll(newsList);
        Collections.shuffle(newsList);
        for (int i = 0; i < newsList.size(); i++) {
            ImageModel n = newsList.get(i);
            //data.add(n);

            DefaultSliderView sliderView = new DefaultSliderView(getContext());
            if(n.getUrl() ==null) {
                sliderView.image(n.getDrawable())
                        .setScaleType(BaseSliderView.ScaleType.FitCenter)
                        .setOnSliderClickListener(this);
            }else {
                sliderView.image(n.getUrl())
                        .setScaleType(BaseSliderView.ScaleType.FitCenter)
                        .setOnSliderClickListener(this);
            }
            sliderView.setOnImageLoadListener(new BaseSliderView.ImageLoadListener() {
                @Override
                public void onStart(BaseSliderView baseSliderView) {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onEnd(boolean b, BaseSliderView baseSliderView) {
                    progressBar.setVisibility(View.GONE);
                }
            });
            Bundle bundle = new Bundle();
            bundle.putParcelable("news", n);
            sliderView.bundle(bundle);

            if(i < 3) sliderShow1.addSlider(sliderView);
            else if(i < 6) sliderShow2.addSlider(sliderView);
            else sliderShow3.addSlider(sliderView);
        }
    }

    @Override
    public void onSliderClick(BaseSliderView baseSliderView) {

    }
}
