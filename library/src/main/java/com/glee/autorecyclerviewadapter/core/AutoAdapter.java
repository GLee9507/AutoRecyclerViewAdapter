package com.glee.autorecyclerviewadapter.core;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;


public class AutoAdapter extends RecyclerView.Adapter<AutoHolder> {
    AutoBinder binder;
    LayoutInflater inflater;

    public AutoAdapter(AutoBinder binder) {
        setBinder(binder);
    }

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
        binder.getFooters().removeOnListChangedCallback(footerCallback);
        binder.getHeaders().removeOnListChangedCallback(headerCallback);
    }

    public void setBinder(AutoBinder autoBinder) {
        this.binder = autoBinder;
        binder.getData().addOnListChangedCallback(callback);
        binder.getHeaders().addOnListChangedCallback(headerCallback);
        binder.getFooters().addOnListChangedCallback(footerCallback);
    }

    @NonNull
    @Override
    public AutoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AutoItem autoItem = findAutoItemByLayoutId(viewType);
        boolean isItem = autoItem == binder;
        return new AutoHolder(this,
                DataBindingUtil.inflate(inflater, viewType, parent, false),
                autoItem.getBrId(),
                isItem
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AutoHolder holder, int position) {
        if (binder.getHeaderCount() > position) {
            holder.bind(((Header) binder.getHeaders().get(position)).getData());
        } else if ((binder.getHeaderCount() + binder.getItemCount() > position)) {
            holder.bind(binder.getData().get(position - binder.getHeaderCount()));
        } else {
            holder.bind(((Footer) binder.getFooters().get(position - binder.getHeaderCount() - binder.getItemCount())).getData());
        }
    }

    @Override
    public int getItemCount() {
        return binder == null ? 0 : binder.getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return findAutoItemByPosition(position).getLayoutId();
    }

    private AutoItem findAutoItemByLayoutId(int layoutId) {
        if (binder.getLayoutId() == layoutId) {
            return binder;
        }

        List headers = binder.getHeaders();
        if (headers != null) {
            for (int i = 0; i < headers.size(); i++) {
                Header header = (Header) headers.get(i);
                if (header.getLayoutId() == layoutId) {
                    return header;
                }
            }
        }
        List footers = binder.getFooters();
        if (footers != null) {
            for (int i = 0; i < footers.size(); i++) {
                Footer footer = (Footer) footers.get(i);
                if (footer.getLayoutId() == layoutId) {
                    return footer;
                }
            }
        }
        return binder;
    }

    private AutoItem findAutoItemByPosition(int position) {
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
            notifyItemRangeChanged(binder.getHeaderCount(), binder.getItemCount());
        }

        @Override
        public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
            notifyItemRangeChanged(positionStart + binder.getHeaderCount(), itemCount);
        }

        @Override
        public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
            notifyItemRangeInserted(positionStart + binder.getHeaderCount(), itemCount);
        }

        @Override
        public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
            for (int i = 0; i < itemCount; i++) {
                notifyItemMoved(fromPosition + i + binder.getHeaderCount(), toPosition + i);
            }
        }

        @Override
        public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
            notifyItemRangeRemoved(positionStart + binder.getHeaderCount(), itemCount);
        }
    }, headerCallback = new ObservableList.OnListChangedCallback() {
        @Override
        public void onChanged(ObservableList sender) {
            notifyItemRangeChanged(0, binder.getHeaderCount());
        }

        @Override
        public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
            notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
            notifyItemRangeInserted(0, itemCount);
        }

        @Override
        public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
            for (int i = 0; i < itemCount; i++) {
                notifyItemMoved(fromPosition, toPosition + i);
            }
        }

        @Override
        public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
            notifyItemRangeRemoved(positionStart, itemCount);
        }
    }, footerCallback = new ObservableList.OnListChangedCallback() {
        @Override
        public void onChanged(ObservableList sender) {
            notifyItemRangeChanged(binder.getHeaderCount() + binder.getItemCount(), binder.getFooterCount());
        }

        @Override
        public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
            notifyItemRangeChanged(binder.getHeaderCount() + binder.getItemCount(), itemCount);
        }

        @Override
        public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
            notifyItemRangeInserted(binder.getHeaderCount() + binder.getItemCount(), itemCount);
        }

        @Override
        public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
            for (int i = 0; i < itemCount; i++) {
                notifyItemMoved(binder.getHeaderCount() + binder.getItemCount(), toPosition + i);
            }
        }

        @Override
        public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
            notifyItemRangeRemoved(binder.getHeaderCount() + binder.getItemCount(), itemCount);
        }
    };

}
