package com.example.smartsiren;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ExpandableListAdapterActivity extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int HEADER1 = 0;
    public static final int HEADER2 = 1;
    public static final int HEADER3 = 2;

    public static final int CHILD1 = 3;
    public static final int CHILD2 = 4;
    public static final int CHILD3 = 5;

    private List<Item> data;

    public List<Button> buttons;

    public ExpandableListAdapterActivity(List<Item> data) {
        this.data = data;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = null;
        Context context = parent.getContext();
        switch (type) {
            case HEADER1:
                LayoutInflater inflaterHeader1 = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflaterHeader1.inflate(R.layout.header1, parent, false);
                ListHeaderViewHolder header1 = new ListHeaderViewHolder(view);
                return header1;
            case HEADER2:
                LayoutInflater inflaterHeader2 = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflaterHeader2.inflate(R.layout.header2, parent, false);
                ListHeaderViewHolder header2 = new ListHeaderViewHolder(view);
                return header2;
            case HEADER3:
                LayoutInflater inflaterHeader = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflaterHeader.inflate(R.layout.header3, parent, false);
                ListHeaderViewHolder header3 = new ListHeaderViewHolder(view);
                return header3;
            case CHILD1:
                LayoutInflater inflaterChild1 = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflaterChild1.inflate(R.layout.child1, parent, false);
                ListChildViewHolder child1 = new ListChildViewHolder(view);
                return child1;
            case CHILD2:
                LayoutInflater inflaterChild2 = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflaterChild2.inflate(R.layout.child2, parent, false);
                ListChildViewHolder child2 = new ListChildViewHolder(view);
                return child2;
            case CHILD3:
                LayoutInflater inflaterChild3 = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflaterChild3.inflate(R.layout.child3, parent, false);
                ListChildViewHolder child3 = new ListChildViewHolder(view);
                return child3;

        }
        return null;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Item item = data.get(position);


        switch (item.type) {
            case HEADER1:
            case HEADER2:
            case HEADER3:
                final ListHeaderViewHolder headerHolder = (ListHeaderViewHolder) holder;
                headerHolder.refferalItem = item;
                headerHolder.header_title.setText(item.text);

                if (item.invisibleChildren == null) {
                    headerHolder.btn_expand_toggle.setImageResource(R.drawable.ic_arrow_up);
                } else {
                    headerHolder.btn_expand_toggle.setImageResource(R.drawable.ic_arrow_down);
                }

                headerHolder.btn_expand_toggle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = data.indexOf(item);
                        if (item.invisibleChildren == null) {
                            item.invisibleChildren = new ArrayList<>();
                            int count = 0;
                            // Find the correct header item by looping and checking the type
                            while (data.size() > pos + 1 && data.get(pos + 1).type != HEADER1 &&
                                    data.get(pos + 1).type != HEADER2 && data.get(pos + 1).type != HEADER3) {
                                item.invisibleChildren.add(data.remove(pos + 1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos + 1, count);
                            headerHolder.btn_expand_toggle.setImageResource(R.drawable.ic_arrow_down);
                        } else {
                            int index = pos + 1;
                            for (Item i : item.invisibleChildren) {
                                data.add(index, i);
                                index++;
                            }
                            notifyItemRangeInserted(pos + 1, index - pos - 1);
                            headerHolder.btn_expand_toggle.setImageResource(R.drawable.ic_arrow_up);
                            item.invisibleChildren = null;
                        }
                    }
                });
                break;
            case CHILD1:
                final ListChildViewHolder childHolder = (ListChildViewHolder) holder;
                childHolder.refferalItem = item;
                childHolder.child_title.setText(item.text);
                break;
            case CHILD2:
                ListChildViewHolder childViewHolder = (ListChildViewHolder) holder;
                childViewHolder.buttons = new ArrayList<>();

                for (int i = 1; i <= 6; i++) {
                    int buttonId = childViewHolder.itemView.getResources().getIdentifier("btn_dan" + i, "id",
                            childViewHolder.itemView.getContext().getPackageName());
                    Button button = childViewHolder.itemView.findViewById(buttonId);
                    childViewHolder.buttons.add(button);

                    // Setting click listeners for buttons
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Button clickedButton = (Button) v;

                            // Button click logic
                            if (clickedButton.isSelected()) {
                                clickedButton.setSelected(false);
                            } else {
                                for (Button b : childViewHolder.buttons) {
                                    b.setSelected(false);
                                }
                                clickedButton.setSelected(true);
                            }
                        }
                    });
                }
                break;
            case CHILD3:
                final ListChildViewHolder childHolder2 = (ListChildViewHolder) holder;
                childHolder2.refferalItem = item;
                childHolder2.child_title.setText(item.text);



                break;
        }
    }



    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    private static class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView header_title;
        public ImageView btn_expand_toggle;
        public Item refferalItem;

        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            header_title = (TextView) itemView.findViewById(R.id.header_title);
            btn_expand_toggle = (ImageView) itemView.findViewById(R.id.btn_expand_toggle);
        }
    }
    private static class ListChildViewHolder extends RecyclerView.ViewHolder {
        public TextView child_title;
        public Button btn;
        public Item refferalItem;

        public List<Button> buttons;


        public ListChildViewHolder(View itemView) {
            super(itemView);
            child_title = (TextView) itemView.findViewById(R.id.child_title);
        }
    }
    public static class Item {
        public int type;
        public String text;
        public List<Item> invisibleChildren;

        public Item() {
        }

        public Item(int type, String text) {
            this.type = type;
            this.text = text;
        }
    }
}
