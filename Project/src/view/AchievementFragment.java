package view;

import android.app.Fragment;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gamma.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import controller.AchievementController;
import dao.HandlerAchievement;
import model.Achievement;

public class AchievementFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_achievement, container, false);
        ImageView starter = (ImageView) v.findViewById(R.id.starter);
        ImageView halfway = (ImageView) v.findViewById(R.id.halfway);
        ImageView finisher = (ImageView) v.findViewById(R.id.finisher);
        ImageView bookworm = (ImageView) v.findViewById(R.id.bookworm);

        Context context = getActivity().getApplicationContext();
        final AchievementController achievementController = new AchievementController(context);
        final ArrayList<Achievement> allAchievement = achievementController.getHandler().getAllAchievement();
//        for (int i = 0; i < allAchievement.size(); i++) {
//            Log.i("Nama achievement = ", "" + allAchievement.get(i).getNama());
//        }
        Bitmap bm = null;

        // retrieve gambar
        try {
            bm = getBitmapFromAsset(allAchievement.get(0).getPathLogo());
            starter.setImageBitmap(bm);
            bm = getBitmapFromAsset(allAchievement.get(1).getPathLogo());
            halfway.setImageBitmap(bm);
            bm = getBitmapFromAsset(allAchievement.get(2).getPathLogo());
            finisher.setImageBitmap(bm);
            bm = getBitmapFromAsset(allAchievement.get(3).getPathLogo());
            bookworm.setImageBitmap(bm);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //set listener
        starter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String progress = "Progress : " + allAchievement.get(0).getProgress() + "% dari " + allAchievement.get(0).getRequirement() + "%";
                if (achievementController.getHandler().getAchievement("Starter").isGet() == true) {
                    progress = "Status : unlocked.";
                }
                CharSequence text = allAchievement.get(0).getNama() + " : " + allAchievement.get(0).getDeskripsi() + "\n" + progress;
                Toast.makeText(view.getContext(), text, Toast.LENGTH_SHORT).show();
                //Log.i("", "Starter diklik");
            }
        });
        halfway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String progress = "Progress : " + allAchievement.get(1).getProgress() + "% dari " + allAchievement.get(1).getRequirement() + "%";
                if (achievementController.getHandler().getAchievement("Halfway").isGet() == true) {
                    progress = "Status : unlocked.";
                }
                CharSequence text = allAchievement.get(1).getNama() + " : " + allAchievement.get(1).getDeskripsi() + "\n" + progress;
                Toast.makeText(view.getContext(), text, Toast.LENGTH_SHORT).show();
                //Log.i("", "Halfway diklik");
            }
        });
        finisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String progress = "Progress : " + allAchievement.get(2).getProgress() + "% dari " + allAchievement.get(2).getRequirement() + "%";
                if (achievementController.getHandler().getAchievement("Finisher").isGet() == true) {
                    progress = "Status : unlocked.";
                }
                CharSequence text = allAchievement.get(2).getNama() + " : " + allAchievement.get(2).getDeskripsi() + "\n" + progress;
                Toast.makeText(view.getContext(), text, Toast.LENGTH_SHORT).show();
                //Log.i("", "Finisher diklik");
            }
        });
        bookworm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String progress = "Progress : " + allAchievement.get(3).getProgress() + " dari " + allAchievement.get(3).getRequirement();
                if (achievementController.getHandler().getAchievement("Bookworm").isGet() == true) {
                    progress = "Status : unlocked.";
                }
                CharSequence text = allAchievement.get(3).getNama() + " : " + allAchievement.get(3).getDeskripsi() + "\n" + progress;
                Toast.makeText(view.getContext(), text, Toast.LENGTH_SHORT).show();
                //Log.i("", "Bookworm diklik");
            }
        });

        return v;
    }

    public Bitmap getBitmapFromAsset(String strName) throws IOException {
        AssetManager assetManager = getResources().getAssets();
        InputStream istr = assetManager.open(strName);
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        return bitmap;
    }
}