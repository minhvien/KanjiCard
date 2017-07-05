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
    private static final String TAG = SwipeCardAdapter.class.getSimpleName();

    private List<Word> mData;
    private Context mContext;
    private CardViewHolder holder;

    public static class CardViewHolder {
        private View rootLayout;
        private TextView meanTextView;
        private TextView hanTextView;
        private TextView numberFrontTextView;
        private TextView numberBackTextView;
        private TextView kanjiFrontTextView;
        private TextView kanjiBackTextView;
        private CardView cardViewFront;
        private CardView cardViewBack;

        public void switchView(){
            if(cardViewFront.getVisibility() == View.GONE){
                cardViewFront.setVisibility(View.VISIBLE);
                cardViewBack.setVisibility(View.GONE);
            } else {
                cardViewFront.setVisibility(View.GONE);
                cardViewBack.setVisibility(View.VISIBLE);
            }
        }
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
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.card_view, parent, false);
            holder = new CardViewHolder();
            holder.rootLayout = convertView.findViewById(R.id.root_layout);
            holder.kanjiBackTextView = (TextView) convertView.findViewById(R.id.txt_kanji_back);
            holder.kanjiFrontTextView = (TextView) convertView.findViewById(R.id.txt_kanji_front);
            holder.numberBackTextView = (TextView) convertView.findViewById(R.id.txt_number_back);
            holder.numberFrontTextView = (TextView) convertView.findViewById(R.id.txt_number_front);
            holder.meanTextView = (TextView) convertView.findViewById(R.id.txt_mean);
            holder.hanTextView = (TextView) convertView.findViewById(R.id.txt_han);
            holder.cardViewFront = (CardView) convertView.findViewById(R.id.card_view_front);
            holder.cardViewBack = (CardView) convertView.findViewById(R.id.card_view_back);

            Typeface custom_font = Typeface.createFromAsset(mContext.getAssets(), "fonts/honoka-antique-maru.ttf");
            holder.kanjiBackTextView.setTypeface(custom_font);
            holder.kanjiFrontTextView.setTypeface(custom_font);
            convertView.setTag(holder);
        } else {
            holder = (CardViewHolder) convertView.getTag();
        }

        Word word = getItem(position);
        holder.kanjiBackTextView.setText(word.getKanji());
        holder.kanjiFrontTextView.setText(word.getKanji());
        holder.numberFrontTextView.setText(String.valueOf(position + 1));
        holder.numberBackTextView.setText(String.valueOf(position + 1));
        holder.meanTextView.setText(word.getShortMean());
        holder.hanTextView.setText(word.getHan());

        Resources res = mContext.getResources();
        TypedArray colors = res.obtainTypedArray(R.array.cardColors);

        holder.cardViewFront.setCardBackgroundColor(colors.getColor(position, 0));
        holder.cardViewBack.setCardBackgroundColor(colors.getColor(position, 0));

        return convertView;
    }
}
