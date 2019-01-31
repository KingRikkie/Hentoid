package me.devsaki.hentoid.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;

import me.devsaki.hentoid.R;
import me.devsaki.hentoid.fragments.PinEntryFragment;
import me.devsaki.hentoid.interfaces.PinEntryView;
import me.devsaki.hentoid.util.Preferences;

// TODO: pin entry screen should appear when app lock is toggled or when "change pin" is selected
// TODO: it may be possible to refine architecture by having multiple extensions of PinEntryFragment
public class RegisterPinActivity extends AppCompatActivity implements PinEntryFragment.Parent {

    private static final int MODE_ADD = 0;

    public static final int MODE_RESET_1 = 1;

    public static final int MODE_RESET_2 = 2;

    public static final int MODE_REMOVE = 3;

    private int mode;

    private View infoGroup;

    private View resetPinText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pin);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        Switch lockSwitch = findViewById(R.id.switch_lock);
        lockSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> onLockSwitchToggled(isChecked));

        infoGroup = findViewById(R.id.group_info);

        resetPinText = findViewById(R.id.text_reset_pin);
        resetPinText.setOnClickListener(v -> onResetPinClick());

        lockSwitch.setChecked(!Preferences.getAppLockPin().isEmpty());
    }

    @Override
    public void onPinEntryReady(PinEntryView pinEntryView) {
        if (mode == MODE_ADD) {
            pinEntryView.setText(R.string.pin_new);
        } else if (mode == MODE_RESET_1) {
            pinEntryView.setText(R.string.pin_current);
        } else if (mode == MODE_REMOVE) {
            pinEntryView.setText(R.string.pin_current);
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public void onPinEntryAccept(PinEntryView pinEntryView, String pin) {
        if (mode == MODE_ADD) {
            // register new pin
        } else if (mode == MODE_RESET_1) {
            // validate current pin
            mode = MODE_RESET_2;
        } else if (mode == MODE_RESET_2) {
            // register new pin
        } else if (mode == MODE_REMOVE) {

        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public void onPinEntryDismiss() {
        detachPinEntryFragment();
    }

    private void onLockSwitchToggled(boolean isOn) {
        if (isOn) {
            mode = MODE_ADD;
            resetPinText.setVisibility(View.VISIBLE);
            infoGroup.setVisibility(View.GONE);
        } else {
            mode = MODE_REMOVE;
            resetPinText.setVisibility(View.GONE);
            infoGroup.setVisibility(View.VISIBLE);
            Preferences.setAppLockPin("");
        }
        attachPinEntryFragment();
    }

    private void onResetPinClick() {
        mode = MODE_RESET_1;
        attachPinEntryFragment();
    }

    private void attachPinEntryFragment() {
        View view = findViewById(R.id.layout_fragment);
        view.setVisibility(View.VISIBLE);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_fragment, new PinEntryFragment())
                .commit();
    }

    private void detachPinEntryFragment() {
        View view = findViewById(R.id.layout_fragment);
        view.setVisibility(View.GONE);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.layout_fragment);
        getSupportFragmentManager().beginTransaction()
                .remove(fragment)
                .commit();
    }
}
