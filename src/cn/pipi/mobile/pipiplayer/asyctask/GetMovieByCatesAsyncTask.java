package cn.pipi.mobile.pipiplayer.asyctask;


import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import cn.pipi.mobile.pipiplayer.adapter.MoiveListAdapter;
import cn.pipi.mobile.pipiplayer.beans.MovieInfo;
import cn.pipi.mobile.pipiplayer.constant.PipiPlayerConstant;
import cn.pipi.mobile.pipiplayer.parser.JsonUtil;
import cn.pipi.mobile.pipiplayer.util.HandlerUtil;

public	class GetMovieByCatesAsyncTask extends AsyncTask<String, Void, List<MovieInfo>> {
	private boolean isNetWork;
	private Handler handler;
	private int position;//用于分析多个adapter填充哪个分类的数据
	public GetMovieByCatesAsyncTask(Handler handler,int position) {
		this.handler=handler;
		this.position=position;
		if(HandlerUtil.isConnect()){
			isNetWork=true;
		}
	}
	@Override
	protected void onPostExecute(List<MovieInfo> result) {
		if(!isNetWork){
			HandlerUtil.sendMsgToHandler(handler, PipiPlayerConstant.NONETWORK);
			return;
		}
		if(result == null){
			HandlerUtil.sendMsgToHandler(handler, PipiPlayerConstant.NO_DATA_RETURN);
		}else if(result.size()!=0){
			HandlerUtil.sendMsgToHandler(handler, PipiPlayerConstant.GETMOVIEBYCATE,position,result);
		}else if(result.size()==0){
			HandlerUtil.sendMsgToHandler(handler, PipiPlayerConstant.DATA_RETURN_ZERO);
		}
	}

    @Override
    protected void onCancelled() {}
	
	protected List<MovieInfo> doInBackground(String... params){
		if (isNetWork) { 
			return JsonUtil.getMovieByCate(params[0], handler);
		}
		return null;
	}
}