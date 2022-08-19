package com.example.candidateevaluationsystemandroid;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {
    Context context;
  //  private final ArrayList<String> name;
  //  private final ArrayList<String> email;
    private final ArrayList<String> address;
    private final ArrayList<String> contact;
    private final ArrayList<String> score;

    View view;
    LayoutInflater layoutInflater;

    public Adapter(Context context, ArrayList<String> address, ArrayList<String> contact, ArrayList<String> score) {

        this.context = context;
      //  this.name = name;
       // this.email = email;
        this.address = address;
        this.contact = contact;
        this.score = score;
    }


    @Override
    public int getCount() {
        return score.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        if(view == null)
        {
            view = new View(context);
            view = layoutInflater.inflate(R.layout.card_design,null);
          //  ImageView img = (ImageView) view.findViewById(R.id.imageview);
       //     TextView txtname = (TextView) view.findViewById(R.id.txtnameans);
        //    TextView txtemail = (TextView) view.findViewById(R.id.txtemailans);
            TextView txtaddress = (TextView) view.findViewById(R.id.txtaddressans);
            TextView txtcontact = (TextView) view.findViewById(R.id.txtcontactnoans);
            TextView txtscore = (TextView) view.findViewById(R.id.txtscoreans);

//                txtname.setText(name.get(i));
  //              txtemail.setText(email.get(i));
                txtaddress.setText(address.get(i));
                txtcontact.setText(contact.get(i));
                txtscore.setText(score.get(i));

                //  img.setImageResource(image[i]);



        }
        return view;
    }
}
