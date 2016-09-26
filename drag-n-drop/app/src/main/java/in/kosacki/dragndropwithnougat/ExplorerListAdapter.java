package in.kosacki.dragndropwithnougat;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hubert on 26/09/16.
 */

public class ExplorerListAdapter extends RecyclerView.Adapter<ExplorerListAdapter.FileItemViewHolder> {

    interface OnItemClickListener{
        void onItemClick(Object o);
    }

    interface OnItemLongClickListener{
        void onItemLongClick(View v, Object o);
    }

    private OnItemClickListener listener = new OnItemClickListener() {
        @Override
        public void onItemClick(Object o) {
            Log.w("TAG", o.toString());
        }
    };

    private OnItemLongClickListener longClickListener = new OnItemLongClickListener() {
        @Override
        public void onItemLongClick(View view, Object o) {
            if(Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
                view.startDragAndDrop(new ClipData(new ClipDescription(null,
                        new String[] { ClipDescription.MIMETYPE_TEXT_PLAIN }), new ClipData.Item(o.toString())), new View.DragShadowBuilder(view), new Object(), View.DRAG_FLAG_OPAQUE | View.DRAG_FLAG_GLOBAL);
            }
            else {
                view.startDrag(new ClipData(new ClipDescription(null,
                        new String[] { ClipDescription.MIMETYPE_TEXT_PLAIN }), new ClipData.Item(o.toString())), new View.DragShadowBuilder(view), new Object(), 0);
            }
//            return false;
        }
    };

    @Override
    public FileItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_item, parent, false);
        return new FileItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FileItemViewHolder holder, int position) {
        holder.bind("Item #" + position, listener, longClickListener);
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    static class FileItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fileItemIcon)
        protected ImageView icon;
        @BindView(R.id.fileItemNameTextView)
        protected TextView itemName;

        public FileItemViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void bind(final String text, final OnItemClickListener listener, final OnItemLongClickListener longClickListener){
            icon.setImageDrawable(icon.getContext().getResources().getDrawable(R.mipmap.ic_launcher));
            itemName.setText(text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(text);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    longClickListener.onItemLongClick(itemView, text);
                    return false;
                }
            });
        }
    }
}
