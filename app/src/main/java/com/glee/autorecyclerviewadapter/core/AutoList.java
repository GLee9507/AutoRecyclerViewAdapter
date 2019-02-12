package com.glee.autorecyclerviewadapter.core;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ListChangeRegistry;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;

public class AutoList<T> implements ObservableList<T>, ListUpdateCallback {
    private transient ListChangeRegistry mListeners = new ListChangeRegistry();
    private List<T> list = new ArrayList<>();
    private final DiffUtil.ItemCallback<T> itemCallback;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private ExecutorService singleThreadExecutor = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>()) {
        @Override
        public Future<?> submit(Runnable task) {
            return super.submit(task);
        }
    };
    private final Object lock = new Object();
    private AtomicBoolean aBoolean = new AtomicBoolean(false);

    public AutoList(DiffUtil.ItemCallback<T> itemCallback) {
        this.itemCallback = itemCallback;
    }

    @Override
    public void addOnListChangedCallback(OnListChangedCallback<? extends ObservableList<T>> callback) {
        mListeners.add(callback);
    }

    @Override
    public void removeOnListChangedCallback(OnListChangedCallback<? extends ObservableList<T>> callback) {
        mListeners.remove(callback);
    }

    private void notifyAdd(int start, int count) {
        if (mListeners != null) {
            mListeners.notifyInserted(this, start, count);
        }
    }

    private void notifyRemove(int start, int count) {
        if (mListeners != null) {
            mListeners.notifyRemoved(this, start, count);
        }
    }

    public void submitList(@Nullable final List<T> newList) {
        if (newList != this.list) {
            if (newList == null) {
                int countRemoved = this.list.size();
                this.list = null;
                notifyRemove(0, countRemoved);
            } else if (this.list == null) {
                this.list = newList;
                notifyAdd(0, newList.size());
            } else {
                final List<T> oldList = this.list;
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        synchronized (lock) {
                            final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                                public int getOldListSize() {
                                    return oldList.size();
                                }

                                public int getNewListSize() {
                                    return newList.size();
                                }

                                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                                    T oldItem = oldList.get(oldItemPosition);
                                    T newItem = newList.get(newItemPosition);
                                    if (oldItem != null && newItem != null) {
                                        return itemCallback.areItemsTheSame(oldItem, newItem);
                                    } else {
                                        return oldItem == null && newItem == null;
                                    }
                                }

                                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                                    T oldItem = oldList.get(oldItemPosition);
                                    T newItem = newList.get(newItemPosition);
                                    if (oldItem != null && newItem != null) {
                                        return itemCallback.areContentsTheSame(oldItem, newItem);
                                    } else if (oldItem == null && newItem == null) {
                                        return true;
                                    } else {
                                        throw new AssertionError();
                                    }
                                }

                                @Nullable
                                public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                                    T oldItem = oldList.get(oldItemPosition);
                                    T newItem = newList.get(newItemPosition);
                                    if (oldItem != null && newItem != null) {
                                        return itemCallback.getChangePayload(oldItem, newItem);
                                    } else {
                                        throw new AssertionError();
                                    }
                                }
                            });
                            list = newList;
                            aBoolean.set(false);
                            result.dispatchUpdatesTo(AutoList.this);
                            lock.notifyAll();
                            Log.d(TAG, "run: notifyAll");
                        }
                    }
                };
                singleThreadExecutor.submit(runnable);
                aBoolean.set(true);
            }
        }
    }

    private String TAG = "glee9507";

    private void checkLock() {
        if (aBoolean.get()) {
            synchronized (lock) {
                try {
                    lock.wait();
                    Log.d(TAG, "checkLock() lock release");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public int size() {
        checkLock();
        return list.size();

    }

    @Override
    public boolean isEmpty() {
        checkLock();
        return list.isEmpty();
    }

    @Override
    public boolean contains(@Nullable Object o) {
        checkLock();
        return list.contains(o);
    }

    @Deprecated
    @NonNull
    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    @Nullable
    @Override
    public Object[] toArray() {
        checkLock();
        return list.toArray();
    }

    @Override
    public <T1> T1[] toArray(@Nullable T1[] a) {
        checkLock();
        return list.toArray(a);
    }

    @Override
    public boolean add(T t) {
        checkLock();
        list.add(t);
        notifyAdd(size() - 1, 1);
        return true;
    }

    @Override
    public boolean remove(@Nullable Object o) {
        checkLock();
        int index = indexOf(o);
        if (index >= 0) {
            remove(index);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> c) {
        checkLock();
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends T> c) {
        checkLock();
        int oldSize = size();
        boolean added = list.addAll(c);
        if (added) {
            notifyAdd(oldSize, size() - oldSize);
        }
        return added;
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends T> c) {
        checkLock();
        boolean added = list.addAll(index, c);
        if (added) {
            notifyAdd(index, c.size());
        }
        return added;
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
        checkLock();
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
        checkLock();
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        checkLock();
        int oldSize = size();
        list.clear();
        if (oldSize != 0) {
            notifyRemove(0, oldSize);
        }
    }

    @Override
    public T get(int index) {
        checkLock();
        return list.get(index);
    }

    @Override
    public T set(int index, T element) {
        checkLock();
        T val = list.set(index, element);
        if (mListeners != null) {
            mListeners.notifyChanged(this, index, 1);
        }
        return val;
    }

    @Override
    public void add(int index, T element) {
        checkLock();
        list.add(index, element);
        notifyAdd(index, 1);
    }

    @Override
    public T remove(int index) {
        checkLock();
        T val = list.remove(index);
        notifyRemove(index, 1);
        return val;
    }

    @Override
    public int indexOf(@Nullable Object o) {
        checkLock();
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(@Nullable Object o) {
        checkLock();
        return list.lastIndexOf(o);
    }

    @NonNull
    @Override
    public ListIterator<T> listIterator() {
        checkLock();
        return list.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<T> listIterator(int index) {
        checkLock();
        return list.listIterator(index);
    }

    @NonNull
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        checkLock();
        return list.subList(fromIndex, toIndex);
    }

    @Override
    public void onInserted(int i, int i1) {
        checkLock();
        mListeners.notifyInserted(this, i, i1);
    }

    @Override
    public void onRemoved(int i, int i1) {
        checkLock();
        mListeners.notifyRemoved(this, i, i1);
    }

    @Override
    public void onMoved(int i, int i1) {
        checkLock();
        mListeners.notifyMoved(this, i, i1, 1);
    }

    @Override
    public void onChanged(int i, int i1, @Nullable Object o) {
        checkLock();
        mListeners.notifyChanged(this, i, i1);
    }
}
