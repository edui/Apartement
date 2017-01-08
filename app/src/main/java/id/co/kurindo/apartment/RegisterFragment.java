package id.co.kurindo.apartment;

import android.os.Bundle;

import id.co.kurindo.apartment.base.BaseFragment;

/**
 * Created by dwim on 1/7/2017.
 */

public class RegisterFragment extends BaseFragment{

    public static RegisterFragment newInstance(String task) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString("task", task);
        fragment.setArguments(args);
        return fragment;
    }
}
