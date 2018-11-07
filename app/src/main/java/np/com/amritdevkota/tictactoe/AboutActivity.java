package np.com.amritdevkota.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;

    TextView developerMailTextView;
    TextView developerSiteTextView;
    TextView githubLinkTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        context = this;

        developerMailTextView = findViewById(R.id.developer_mail);
        developerSiteTextView = findViewById(R.id.developer_site);
        githubLinkTextView = findViewById(R.id.github_link);

        setClickListener();
    }

    // Add Click listeners on Text View so we can open mail and browsers in click
    private void setClickListener(){
        developerMailTextView.setOnClickListener(this);
        developerSiteTextView.setOnClickListener(this);
        githubLinkTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent aintent;
        switch (view.getId()) {
            case R.id.developer_mail:
                // if the view is TextView with id developer_mail
                // Open Gmail with your email
                aintent = new Intent(Intent.ACTION_SENDTO);
                aintent.setData(Uri.parse("mailto:hi@amritdevkota.com.np"));
                aintent.putExtra(Intent.EXTRA_SUBJECT,"App: Tic Tac Toe - ");
                startActivity(aintent);
                break;

            case R.id.developer_site:
                // Open your website
                aintent = new Intent(Intent.ACTION_VIEW);
                aintent.setData(Uri.parse("https://www.amritdevkota.com.np"));
                startActivity(aintent);
                break;

            case R.id.github_link:
                // Open github link
                aintent = new Intent(Intent.ACTION_VIEW);
                aintent.setData(Uri.parse("https://github.com/AmritDevkota/Tic-Tac-Toe"));
                startActivity(aintent);
        }

    }
}
