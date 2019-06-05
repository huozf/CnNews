package com.demo.cnnews.pager;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.demo.cnnews.R;
import com.demo.cnnews.base.BasePager;
import com.demo.cnnews.utils.CacheUtils;
import com.demo.cnnews.utils.ShareUtil;

import org.xutils.x;

/**
 * 设置页面
 */
public class SettingPager extends BasePager implements View.OnClickListener{

    private View view;

    private LinearLayout setting_view; //设置界面
    private TextView tv_uname; //设置界面用户名
    private TextView order; //订阅
    private TextView btClear; //清理缓存
    private TextView btShare; //分享给朋友
    private TextView aboutUs; //关于我们
    private Button btn_logout; //退出登录

    //注册界面
    private LinearLayout reg_view;
    private EditText re_user_name; //注册界面用户名
    private EditText re_psw; //注册界面密码
    private EditText re_rpsw; //注册界面重复密码
    private Button btn_reg; //注册按钮
    private TextView go_login; //去登录

    //登录界面
    private LinearLayout log_view;
    private EditText log_user_name; //登录界面用户名
    private EditText log_psw; //登录界面用户名
    private Button btn_login; //登录
    private TextView go_reg; //去注册



    public SettingPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        //1、设置标题
        tv_title.setText("设置页面");
        //2、联网请求，得到数据

        view = View.inflate(context, R.layout.setting_pager,null);
        x.view().inject(SettingPager.this,view);
        //初始化各组件
        findViews();
        //3、把子视图添加到BasePager的FragmentLayout中
        fl_content.addView(view);

        if (!TextUtils.isEmpty(CacheUtils.getString(context,"user"))){
            showSetView();
        }else{
            showLoginView();
        }

      }

    private void findViews() {
        //设置界面
        setting_view = view.findViewById(R.id.setting_view);
        tv_uname = view.findViewById(R.id.tv_uname);
        order = view.findViewById(R.id.order);
        btShare = view.findViewById(R.id.btShare);
        btClear = view.findViewById(R.id.btClear);
        aboutUs = view.findViewById(R.id.aboutUs);
        btn_logout = view.findViewById(R.id.btn_logout);

        //注册界面
        reg_view = view.findViewById(R.id.reg_view);
        re_user_name = view.findViewById(R.id.re_user_name);
        re_psw = view.findViewById(R.id.re_psw);
        re_rpsw = view.findViewById(R.id.re_rpsw);
        btn_reg = view.findViewById(R.id.btn_reg);
        go_login = view.findViewById(R.id.go_login);

        //登录界面
        log_view = view.findViewById(R.id.log_view);
        log_user_name = view.findViewById(R.id.log_user_name);
        log_psw = view.findViewById(R.id.log_psw);
        btn_login = view.findViewById(R.id.btn_login);
        go_reg = view.findViewById(R.id.go_reg);

        order.setOnClickListener(this);
        btShare.setOnClickListener(this);
        btClear.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
        btn_logout.setOnClickListener(this);

        btn_reg.setOnClickListener(this);
        go_login.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        go_reg.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v == order){
            Toast.makeText(context, "点击订阅", Toast.LENGTH_SHORT).show();
        } else if (v == btClear) {
            Toast.makeText(context, "已清理缓存", Toast.LENGTH_SHORT).show();
        } else if (v == btShare){
            ShareUtil.showShare(context);
        } else if(v == aboutUs){
            showAboutUs();
        } else if(v == btn_logout){
            logout();
        }else if(v == btn_reg){
            //注册
            register();
        }else if(v == go_login){
            showLoginView();
        }else if(v == btn_login){
            //登录
            login();
        }else if(v == go_reg){
            showResView();
        }

    }

    //显示设置界面
    private void showSetView(){
        setting_view.setVisibility(View.VISIBLE);
        reg_view.setVisibility(View.GONE);
        log_view.setVisibility(View.GONE);
    }

    //显示注册界面
    private void showResView(){
        setting_view.setVisibility(View.GONE);
        reg_view.setVisibility(View.VISIBLE);
        log_view.setVisibility(View.GONE);
    }

    //显示登录界面
    private void showLoginView(){
        setting_view.setVisibility(View.GONE);
        reg_view.setVisibility(View.GONE);
        log_view.setVisibility(View.VISIBLE);
        fl_content.removeAllViews();
        fl_content.addView(view);
    }

    //登录
    private void login(){
        String uname = String.valueOf(log_user_name.getText());
        String psw = String.valueOf(log_psw.getText());
        if (TextUtils.isEmpty(uname)){
            Toast.makeText(context, "用户名不能为空", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(psw)){
            Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
        }else {
            CacheUtils.putString(context,"user",uname);
            showSetView();
        }
    }

    //注册
    private void register(){
        String uname = String.valueOf(re_user_name.getText());
        String psw = String.valueOf(re_psw.getText());
        String rpsw = String.valueOf(re_rpsw.getText());
        if (TextUtils.isEmpty(uname)){
            Toast.makeText(context, "用户名不能为空", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(psw)){
            Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(rpsw)){
            Toast.makeText(context, "请确认密码", Toast.LENGTH_SHORT).show();
        }else if (psw != rpsw){
            Toast.makeText(context, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
        }else {
            CacheUtils.putString(context,"user",uname);
            showSetView();
        }
    }

    //退出登录
    private void logout(){
        CacheUtils.putString(context,"user","");
        showLoginView();
    }

    //弹出关于我们
    private void showAboutUs() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setNegativeButton("知道了", null);
        builder.setTitle("关于菜鸟");
        builder.setMessage("菜鸟新闻是一个为亿万用户打造的一款移动资讯类APP，" +
                "内容涵盖新闻、财经、科技、娱乐、体育等多个资讯类别。" +
                "自上线以来，菜鸟新闻客户端以其专业、全面的新闻报道为用户提供24小时资讯服务，" +
                "并通过提供精品阅读、开设原创栏目、建立用户积分体系和活动广场等创新形式获得了良好的用户口碑。");
        builder.setCancelable(true);
        builder.show();
    }


}
