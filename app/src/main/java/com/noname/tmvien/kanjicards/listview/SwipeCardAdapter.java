package com.noname.tmvien.kanjicards.listview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.noname.tmvien.kanjicards.R;
import com.noname.tmvien.kanjicards.models.Word;

import java.util.List;

/**
 * Created by tmvien on 7/3/17.
 */

public class SwipeCardAdapter extends BaseAdapter {
    private List<Word> mData;
    private Context mContext;

    static class CardViewHolder {
        private TextView textView;
        private CardView cardView;
    }

    public SwipeCardAdapter(Context context, List<Word> data) {
        this.mData = data;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Word getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CardViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.card_view, parent, false);
            holder = new CardViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.textView);
            holder.cardView = (CardView) convertView.findViewById(R.id.cardView);
            Typeface custom_font = Typeface.createFromAsset(mContext.getAssets(),  "fonts/honoka-antique-maru.ttf");
            holder.textView.setTypeface(custom_font);
            convertView.setTag(holder);
        }
        else {
            holder = (CardViewHolder) convertView.getTag();
        }

        Word word = getItem(position);
        holder.textView.setText(word.getKanji());
        Resources res = mContext.getResources();
        TypedArray colors = res.obtainTypedArray(R.array.cardColors);

        holder.cardView.setCardBackgroundColor(colors.getColor(position, 0));
        return convertView;
    }
}
