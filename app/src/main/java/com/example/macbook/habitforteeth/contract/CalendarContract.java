package com.example.macbook.habitforteeth.contract;

import com.example.macbook.habitforteeth.view.BaseView;
import com.example.macbook.habitforteeth.model.Star;
import com.example.macbook.habitforteeth.presenter.BasePresenter;

import java.util.ArrayList;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface CalendarContract {

    interface View extends BaseView<Presenter> {


    }

    interface Presenter extends BasePresenter {

        void initData();
        void updateData(Star s);
        void deleteAll();
        ArrayList<Star> initList();
    }
}

