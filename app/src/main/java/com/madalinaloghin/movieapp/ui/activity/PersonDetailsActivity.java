package com.madalinaloghin.movieapp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.madalinaloghin.movieapp.R;
import com.madalinaloghin.movieapp.api.RequestManager;
import com.madalinaloghin.movieapp.api.response.ResponseImageList;
import com.madalinaloghin.movieapp.ui.adapter.AdapterPersonImages;
import com.madalinaloghin.util.Util;
import com.madalinaloghin.util.object.Person;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonDetailsActivity extends AppCompatActivity {

    private Person person;
    private LinearLayoutManager mLayoutManagerImages;
    private AdapterPersonImages mAdapterImages;

    @BindView(R.id.tv_person_name)
    TextView tvPersonName;

    @BindView(R.id.tv_birth_date)
    TextView tvBirthdate;

    @BindView(R.id.tv_birth_date_value)
    TextView tvBirthDateValue;

    @BindView(R.id.tv_death_day_value)
    TextView tvDeathDayValue;

    @BindView(R.id.tv_death_day)
    TextView tvDeathDay;

    @BindView(R.id.tv_birthplace_person)
    TextView tvBirthPlace;

    @BindView(R.id.tv_birthplace_person_value)
    TextView tvBirthPlaceValue;

    @BindView(R.id.tv_biography_person)
    TextView tvBiography;

    @BindView(R.id.tv_biography_person_value)
    TextView tvBiographyValue;

    @BindView(R.id.rv_images_persons)
    RecyclerView mRvImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);
        Bundle b = getIntent().getExtras();
        ButterKnife.bind(this);

        person = (Person) b.getSerializable(Util.PERSON);
        getPersonDetails(person.getId());

        setupRecycleView();
        getImages(person.getId());


    }

    void getPersonDetails(final int actorId) {
        RequestManager.getInstance(this.getBaseContext()).queryPersonDetail(
                Util.API_KEY,
                actorId,
                new Callback<Person>() {
                    @Override
                    public void onResponse(Call<Person> call, Response<Person> response) {
                        updatePersonInfo(response.body());
                    }

                    @Override
                    public void onFailure(Call<Person> call, Throwable t) {

                    }
                }
        );
    }


    void updatePersonInfo(Person p) {
        tvPersonName.setText(p.getName());

        if (p.getBirthday() == null) {
            tvBirthDateValue.setText(" ");
        } else {
            tvBirthdate.setVisibility(View.VISIBLE);
            tvBirthDateValue.setText(p.getBirthday());
        }

        if (p.getDeathday() == null) {
            tvDeathDay.setText(" ");
        } else {
            tvDeathDay.setVisibility(View.VISIBLE);
            tvDeathDayValue.setText(p.getDeathday());
        }

        if (p.getPlaceOfBirth() == null) {
            tvBirthPlace.setText(" ");
        } else {
            tvBirthPlace.setVisibility(View.VISIBLE);
            tvBirthPlaceValue.setText(p.getPlaceOfBirth());
        }

        tvBiography.setVisibility(View.VISIBLE);

        if (p.getBiography() == "") {
            tvBiographyValue.setText(" No more details about this person. ");
        } else {
            tvBiographyValue.setText(p.getBiography());
        }

    }

    void setupRecycleView() {
        mLayoutManagerImages = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvImages.setLayoutManager(mLayoutManagerImages);
        mAdapterImages = new AdapterPersonImages();
        mRvImages.setAdapter(mAdapterImages);

    }

    void getImages(int id) {
        RequestManager.getInstance(getBaseContext()).queryPersonImages(
                id,
                Util.API_KEY,
                new Callback<ResponseImageList>() {
                    @Override
                    public void onResponse(Call<ResponseImageList> call, Response<ResponseImageList> response) {
                        mAdapterImages.setItems(response.body().getImageList());
                    }

                    @Override
                    public void onFailure(Call<ResponseImageList> call, Throwable t) {
                    }
                }
        );
    }
}
