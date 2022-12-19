package com.example.azshop;

import static com.example.azshop.utils.Utils.setCurrentFragment;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.azshop.ui.bashetscreen.BasketFragment;
import com.example.azshop.ui.homescreen.HomeFragment;
import com.example.azshop.ui.menuscreen.MenuFragment;
import com.example.azshop.ui.searchscreen.SearchFragment;
import com.example.azshop.ui.whishlistscreen.WhishListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        setCurrentFragment(new HomeFragment(), MainActivity.this, R.id.flFragment);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home: {
                        setCurrentFragment(new HomeFragment(), MainActivity.this, R.id.flFragment);
                        return true;
                    }

                    case R.id.basket: {
                        setCurrentFragment(new BasketFragment(), MainActivity.this, R.id.flFragment);
                        return true;
                    }

                    case R.id.search:
                        setCurrentFragment(new SearchFragment(), MainActivity.this, R.id.flFragment);
                        return true;

                    case R.id.menu:
                        setCurrentFragment(new MenuFragment(), MainActivity.this, R.id.flFragment);
                        return true;

                    case R.id.favoris:
                        setCurrentFragment(new WhishListFragment(), MainActivity.this, R.id.flFragment);
                        return true;
                }
                return false;
            }
        });
    }
}