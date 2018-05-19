package eyes.video.ui.adapter;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eyes.video.R;
import eyes.video.model.bean.Home;

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Home> homes;
    private ItmeClick lister2  = null;
    public MenuAdapter(List<Home> homes) {
        this.homes = homes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_itme, parent, false);
        return new MenuHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MenuHolder) {
            MenuHolder menuHolder = (MenuHolder) holder;
            menuHolder.bindItem(homes.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return homes.size();
    }

    class MenuHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.menu_str)
        TextView menustr;
        @BindView(R.id.itmecard)
        CardView itmecard;
        public MenuHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        public void bindItem(final Home item) {
            menustr.setText(item.getMenutitle());
            itmecard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lister2.onItmeClick(item);
                }
            });
        }
    }
    public interface ItmeClick{
        void  onItmeClick(Home item);
    }
    public void setItmeClick(ItmeClick lister){
        lister2 = lister;
    }
}
