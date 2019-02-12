package com.glee.autorecyclerviewadapter.core;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

public class AutoAdapter extends RecyclerView.Adapter<AutoHolder> {
    private AutoBinder binder;
    private LayoutInflater inflater;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        inflater = LayoutInflater.from(recyclerView.getContext());
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        inflater = null;
        binder.getData().removeOnListChangedCallback(callback);
    }

    public void setBinder(AutoBinder autoBinder) {
        this.binder = autoBinder;
        binder.getData().addOnListChangedCallback(callback);
    }

    @NonNull
    @Override
    public AutoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AutoHolder(DataBindingUtil.inflate(inflater, viewType, parent, false), findBrIdByLayoutId(viewType));
    }

    @Override
    public void onBindViewHolder(@NonNull AutoHolder holder, int position) {
        holder.bind(binder.getData().get(position));
    }

    @Override
    public int getItemCount() {
        return binder == null ? 0 : binder.getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return getAutoItem(position).getLayoutId();
    }

    private int findBrIdByLayoutId(int layoutId) {
        if (binder.getLayoutId() == layoutId) {
            return binder.getBrId();
        }

        List headers = binder.getHeaders();
        if (headers != null) {
            for (int i = 0; i < headers.size(); i++) {
                Header header = (Header) headers.get(i);
                if (header.getLayoutId() == layoutId) {
                    return header.getBrId();
                }
            }
        }
        List footers = binder.getFooters();
        if (footers != null) {
            for (int i = 0; i < footers.size(); i++) {
                Footer footer = (Footer) footers.get(i);
                if (footer.getLayoutId() == layoutId) {
                    return footer.getBrId();
                }
            }
        }
        return binder.getBrId();
    }

    private AutoItem getAutoItem(int position) {
        if (binder.getHeaderCount() > position) {
            return ((Header) binder.getHeaders().get(position));
        } else if (binder.getHeaderCount() + binder.getItemCount() > position) {
            return binder;
        } else {
            return ((Footer) binder.getFooters().get(position - binder.getHeaderCount() - binder.getItemCount()));
        }
    }

    private ObservableList.OnListChangedCallback callback = new ObservableList.OnListChangedCallback() {
        @Override
        public void onChanged(ObservableList sender) {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
            notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
            notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
            for (int i = 0; i < itemCount; i++) {
                notifyItemMoved(fromPosition + i, toPosition + i);
            }
        }

        @Override
        public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
            notifyItemRangeRemoved(positionStart, itemCount);
        }
    };
}
