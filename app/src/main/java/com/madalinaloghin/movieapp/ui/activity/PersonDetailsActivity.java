package com.madalinaloghin.movieapp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.madalinaloghin.movieapp.R;
import com.madalinaloghin.movieapp.api.RequestManager;
import com.madalinaloghin.util.Util;
import com.madalinaloghin.util.object.Categories;
import com.madalinaloghin.util.object.Person;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonDetailsActivity extends AppCompatActivity {

    private Person person;

    @BindView(R.id.tv_person_name)
    TextView tvPersonName;

    @BindView(R.id.tv_birth_date_value)
    TextView tvBirthDate;

    @BindView(R.id.tv_death_day_value)
    TextView tvDeathDayValue;

    @BindView(R.id.tv_death_day)
    TextView tvDeathDay;

    @BindView(R.id.tv_birthplace_person_value)
    TextView tvBirthPlace;

    @BindView(R.id.tv_biography_person_value)
    TextView tvBiography;

    @BindView(R.id.iv_person_image_details)
    ImageView ivPersonImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);
        Bundle b = getIntent().getExtras();
        ButterKnife.bind(this);
        person = (Person) b.getSerializable(Categories.PERSON);

        getActortDetails(person.getId());

    }

    void getActortDetails(final int actorId) {
        RequestManager.getInstance(this.getBaseContext()).queryPersonDetail(
                Util.getApiKey,
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
        Glide.with(this).load(p.getImagePath()).into(ivPersonImage);
        tvPersonName.setText(p.getName());
        tvBirthDate.setText(p.getBirthday());
        if (p.getDeathday() == null) {
            tvDeathDay.setText(" ");
        } else {
            tvDeathDay.setVisibility(View.VISIBLE);
            tvDeathDayValue.setText(p.getDeathday());
        }
        tvBirthPlace.setText(p.getPlaceOfBirth());
        tvBiography.setText(p.getBiography());

    }

}
