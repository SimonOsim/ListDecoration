package at.osim.sample.listdecoration;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import at.osim.listdecoration.LineDecorationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rv = findViewById(R.id.rv_list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new SimpleAdapter(this));
    }


    private class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleHolder> {

        private static final int ITEMS = 5;

        private final LayoutInflater inflater;

        SimpleAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public SimpleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemType) {
            return new SimpleHolder(inflater.inflate(R.layout.list_item_line_decoration, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull SimpleHolder simpleHolder, int index) {
            simpleHolder.setItem(index);
        }

        @Override
        public int getItemCount() {
            return ITEMS;
        }

        class SimpleHolder extends RecyclerView.ViewHolder {

            private final TextView tvTitle = itemView.findViewById(R.id.tv_title);
            private final LineDecorationView ldDecoration = itemView.findViewById(R.id.ld_decoration);

            SimpleHolder(@NonNull View itemView) {
                super(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }

            public void setItem(int num) {
                tvTitle.setText(getString(R.string.item, num));
                ldDecoration.setText(Integer.toString(num));
                if (num == 0) {
                    ldDecoration.isStart();
                } else if (num == ITEMS - 1) {
                    ldDecoration.isEnd();
                } else {
                    ldDecoration.isMiddle();
                }
            }
        }
    }
}
