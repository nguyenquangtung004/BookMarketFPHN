package DuAn1_FPLHN.Nhom2.Book_Market.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import DuAn1_FPLHN.Nhom2.Book_Market.R;


public class ThongTinApp extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_thong_tin_app, container,false);

        return view;
    }
}