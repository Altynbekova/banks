package com.altynbekova.banks;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.altynbekova.banks.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private static final String KEY_IS_DARK_MODE = "is_dark_mode";
    private static final String PREFS_NAME = "theme_prefs";
    private ActivityMainBinding binding;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        applySavedTheme();

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setSupportActionBar(binding.toolbar);

        FragmentStateAdapter pageAdapter = new MyAdapter(this);
        binding.pager.setAdapter(pageAdapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                binding.tabLayout,
                binding.pager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(TabLayout.Tab tab, int position) {
                        if (position == 0) {
                            tab.setText("Карта");
                        } else {
                            tab.setText("Список");
                        }
                    }
                });
        tabLayoutMediator.attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        /*MenuItem menuItem = menu.findItem(R.id.action_theme);
        menuItem.setTitle("Dark");*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_theme){
            //todo toggle theme
            Snackbar.make(this.findViewById(R.id.action_theme), "Смена темы", Snackbar.LENGTH_LONG).show();
            return true;
        }
        //headerView.setText(item.getTitle());
        return super.onOptionsItemSelected(item);
    }

    private boolean isDarkModeActive(){
        return (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) ==
                Configuration.UI_MODE_NIGHT_YES;
    }

    private void applySavedTheme() {
        boolean isDarkSystem = (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) ==
                Configuration.UI_MODE_NIGHT_YES;
        boolean isDarkMode = sharedPreferences.getBoolean(KEY_IS_DARK_MODE, isDarkSystem);
        AppCompatDelegate.setDefaultNightMode(isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES :
                AppCompatDelegate.MODE_NIGHT_NO);
    }
}