package com.appheader.DingQuPostman.ui.main.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.appheader.DingQuPostman.R;
import com.appheader.DingQuPostman.common.activity.BaseActivity;
import com.appheader.DingQuPostman.common.network.RequestCallback;
import com.appheader.DingQuPostman.common.network.RequestHelper;
import com.appheader.DingQuPostman.common.network.ResponseEntity;
import com.appheader.DingQuPostman.common.network.UrlConstants;
import com.appheader.DingQuPostman.common.utils.ParamBuilder;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class ArticleDetailActivity extends BaseActivity {

    @Bind(R.id.img_publish_photo)
    ImageView imgPublishPhoto;
    @Bind(R.id.txt_author)
    TextView txtAuthor;
    @Bind(R.id.txt_publish_time)
    TextView txtPublishTime;
    @Bind(R.id.btn_collect)
    Button btnCollect;
    @Bind(R.id.re_content_title)
    RelativeLayout reContentTitle;
    @Bind(R.id.txt_article_title)
    TextView txtArticleTitle;
    @Bind(R.id.txt_article_content)
    TextView txtArticleContent;
    @Bind(R.id.img_content_one)
    ImageView imgContentOne;
    @Bind(R.id.img_content_two)
    ImageView imgContentTwo;
    @Bind(R.id.img_content_four)
    ImageView imgContentFour;
    @Bind(R.id.re_text_content)
    RelativeLayout reTextContent;
    @Bind(R.id.sco_article)
    ScrollView scoArticle;
    @Bind(R.id.et_comment)
    EditText etComment;
    @Bind(R.id.btn_confirm)
    Button btnConfirm;
    @Bind(R.id.lv_comment_list)
    ListView lvCommentList;
    @Bind(R.id.ll_comment_layout)
    LinearLayout llCommentLayout;
    private int cellId;
    private List commentList;
    private ArrayAdapter comAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artical_detail);
        ButterKnife.bind(this);
        title.setText("内容");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cellId=getIntent().getIntExtra("cellID",-1);
        commentList=new ArrayList();
        initData();
    }

    private void initData() {
        //获取评论 cellID，page，rows
        HashMap<String, String> paramsMap = ParamBuilder.buildParam().append("cellID", String.valueOf(cellId))
                .append("page", String.valueOf(1)).append("rows", String.valueOf(20)).toHashMap();
        RequestHelper.sendAsyncRequest(this, UrlConstants.PINGLUNYILAN, paramsMap, new RequestCallback() {
            @Override
            public void onSuccess(ResponseEntity response) {
                if (response.isSuccess()) {
                    JSONArray resDataArray=response.getDataObject().optJSONArray("list");
                    for (int i = 0; i < resDataArray.length(); i++) {
                        commentList.add(resDataArray.optString(i));
                    }
                }
                else
                    Toast.makeText(ArticleDetailActivity.this,response.getErrorMessage(), Toast.LENGTH_SHORT).show();
                comAdapter=new ArrayAdapter(ArticleDetailActivity.this,android.R.layout.simple_expandable_list_item_1,commentList);
            }

            @Override
            public void onFailure(Exception ex) {
            }
            @Override
            public void onComplete() {
            }
        });
    }

    @OnClick({R.id.btn_collect,R.id.btn_confirm})
    public void btnClickEent(View view){
        switch (view.getId()) {
            case R.id.btn_collect:
               /* HashMap<String, String> paramsMap = ParamBuilder.buildParam().append("category", category).toHashMap();
                RequestHelper.sendAsyncRequest(this, UrlConstants.RENWU_DENGJI, paramsMap, new RequestCallback() {
                    @Override
                    public void onSuccess(ResponseEntity response) {
                        //提交信息成功
                        if (response.isSuccess()) {
                            Toast.makeText(ArticleDetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(ArticleDetailActivity.this,response.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Exception ex) {
                        Toast.makeText(ArticleDetailActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {

                    }
                });*/
                break;
            case R.id.btn_confirm:
                //commentTime,petName,comment,title,cellID
                HashMap<String, String> paramsConfirm = ParamBuilder.buildParam().append("commentTime", "2016-12-34 23:34").
                        append("petName","屈淮南").append("comment",etComment.getText().toString()).append("cellID",
                        String.valueOf(cellId)).toHashMap();

                RequestHelper.sendAsyncRequest(this, UrlConstants.PINGLUNTIJIAO, paramsConfirm, new RequestCallback() {
                    @Override
                    public void onSuccess(ResponseEntity response) {
                        //提交信息成功
                        if (response.isSuccess()) {
                            Toast.makeText(ArticleDetailActivity.this, "评论成功", Toast.LENGTH_SHORT).show();

                        }
                        else
                            Toast.makeText(ArticleDetailActivity.this,response.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Exception ex) {
                        Toast.makeText(ArticleDetailActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {

                    }
                });
                break;
        }
    }

}
