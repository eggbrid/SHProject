//package com.shpro.xus.shproject.view.find;
//
//import android.util.Log;
//import android.view.View;
//
//import com.shpro.xus.shproject.R;
//import com.shpro.xus.shproject.bean.Bag;
//import com.shpro.xus.shproject.util.ToastUtil;
//import com.shpro.xus.shproject.view.CommentActivity;
//import com.shpro.xus.shproject.view.views.BagShowDialog;
//import com.shpro.xus.shproject.view.views.TaiJiView;
//import com.skyfishjy.library.RippleBackground;
//
///**
// * Created by xus on 2017/2/8.
// */
//
//public class FindBagActivity extends CommentActivity implements View.OnClickListener {
//    protected TaiJiView centerImage;
//    protected RippleBackground content;
//
//    @Override
//    public int setContentView() {
//        return R.layout.activity_find_bag;
//    }
//
//    @Override
//    public void initView() throws Exception {
//        setCommentTitleView("寻找物品");
//        centerImage = (TaiJiView) findViewById(R.id.centerImage);
//        centerImage.setOnClickListener(FindBagActivity.this);
//        content = (RippleBackground) findViewById(R.id.content);
//        content.setOnClickListener(FindBagActivity.this);
//
//    }
//
//    @Override
//    public void onClick(View view) {
//        if (view.getId() == R.id.centerImage) {
//            content.startRippleAnimation();
//            centerImage.start();
////            Map<String,String> p=new HashMap<>();
////            p.put("userId", BmobUser.getCurrentUser(Account.class).getUserid());
////            User user=   ACacheUtil.getInstance().getObject(AndroidIDUtil.getID(FindBagActivity.this), User.class);
//////            p.put("lucky", user.getSelf().lucky+"");
////            HttpCloudUtil.post(p, "findBag",FindBagResponse.class, new HttpCloudUtil.OnMessageGet<FindBagItem>() {
////
////                @Override
////                public void onSuccess(List<FindBagItem> s) {
////                    stop(s.get(0),null,false);
////
////                }
////
////                @Override
////                public void onError(String s) {
////                    stop(null,s,true);
////                }
////            });
//
//        }
//    }
//
//    public void stop(final FindBagItem fbi, final String s , final boolean isError){
//                    new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            content.stopRippleAnimation();
//                            centerImage.stop();
//                            if (isError){
//                                ToastUtil.makeTextShort(FindBagActivity.this,s);
//                                Log.e("wangxu",s);
//                            }else{
//                                Bag bag=new Bag();
//                                bag.setInfo(fbi.getInfo());
//                                bag.setName(fbi.getName());
//                                bag.setAction(fbi.getAction());
//                                bag.setActionInfo(fbi.getActionInfo());
//                                bag.setIcon(fbi.getIcon());
//                                bag.setOther(fbi.getOther());
//                                BagShowDialog bagShowDialog=new BagShowDialog(FindBagActivity.this,bag);
//                                bagShowDialog.show();
//                            }
//                        }
//                    });
//
//                }
//            }).start();
//    }
//
//}
