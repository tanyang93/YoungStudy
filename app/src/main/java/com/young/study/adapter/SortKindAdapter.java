package com.young.study.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import com.young.study.R;
import com.young.study.bean.Novels;

/**
 * Created by edz on 2017/8/1.
 */

public class SortKindAdapter extends BaseAdapter {

    private final static String TAG = "SortKindAdapter";
    private ArrayList<Novels> novelList;
    private LayoutInflater mInflater;
    private int layoutId;

    public SortKindAdapter(Context context, ArrayList<Novels> result) {
        this.novelList = result;
        mInflater = LayoutInflater.from(context);
        layoutId = R.layout.kind_list;
    }


    @Override
    public int getCount() {
        return novelList.size();
    }

    @Override
    public Object getItem(int i) {
        Log.d(TAG," i = "+i+",,,,novels string = "+novelList.get(i).getNovels().toString());
        return novelList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.i("tanyang","getView i = "+ i);
        NovelItem item = null;
        if(view == null) {
            view = mInflater.inflate(layoutId, null);
            item = new NovelItem(view);
            view.setTag(item);
        } else {
            item = (NovelItem) view.getTag();
        }
        item.bind((Novels)getItem(i));
        return view;
    }

    private class NovelItem{

        private View mView;
        private ImageView openRight;
        private ImageView thumb;
        private TextView kindName;
        private TextView[] tv1 = new TextView[6];


        private NovelItem(View view) {
            mView = view;
            thumb = (ImageView) view.findViewById(R.id.img_kind_thumb);
            kindName = (TextView) view.findViewById(R.id.tv_kind_name);
            tv1[0] = (TextView) view.findViewById(R.id.tv_novel_name_1);
            tv1[1] = (TextView) view.findViewById(R.id.tv_novel_name_2);
            tv1[2] = (TextView) view.findViewById(R.id.tv_novel_name_3);
            tv1[3] = (TextView) view.findViewById(R.id.tv_novel_name_4);
            tv1[4] = (TextView) view.findViewById(R.id.tv_novel_name_5);
            tv1[5] = (TextView) view.findViewById(R.id.tv_novel_name_6);
            openRight = (ImageView) view.findViewById(R.id.img_openright);
        }

        public synchronized void bind(Novels novels) {
            Log.d(TAG,"bind novels = "+novels.getNovels().toString());
            kindName.setText(novels.getKindName());
            if (novels.getNovels().size() == 0) {

                return;
            }
            if (novels.getNovels() == null){
                return;
            } else if ((novels.getNovels() != null) && (novels.getNovels().size()>=6)){
                for (int i = 0; i <tv1.length ; i++) {
                    String name = novels.getNovels().get(i).getName();
                    if (name.length() > 6){
                        name = name.substring(0,5)+"...";
                    }
                    tv1[i].setText(name);
                    tv1[i].setVisibility(View.VISIBLE);
                }
            } else {
                for (int i = 0; i < novels.getNovels().size(); i++) {
                    String name = novels.getNovels().get(i).getName();
                    if (name.length() > 6){
                        name = name.substring(0,5)+"...";
                    }
                    tv1[i].setText(name);
                    tv1[i].setVisibility(View.VISIBLE);
                }
            }
            //显示第一本书的图标
            Glide.with(mView.getContext()).load(novels.getNovels().get(0).getThumb()).into(thumb);
//            Glide.with(mView.getContext()).load(novel.getThumb()).placeholder(R.drawable.default_cover).centerCrop()
//                    .into(thumb);
        }
    }

}
