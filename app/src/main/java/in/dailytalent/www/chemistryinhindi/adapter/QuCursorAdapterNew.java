package in.dailytalent.www.chemistryinhindi.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.ResourceCursorAdapter;
import android.view.View;
import android.widget.TextView;

import in.dailytalent.www.chemistryinhindi.R;


public class QuCursorAdapterNew extends ResourceCursorAdapter {
    public QuCursorAdapterNew(Context context, int layout, Cursor c, int flags) {
        super(context, layout, c, flags);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        ((TextView) view.findViewById(R.id.catname)).setText(cursor.getString(cursor.getColumnIndex("quid")) + ". " + cursor.getString(cursor.getColumnIndex("quname")));
    }
}
