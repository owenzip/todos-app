/**
 * TaskAdapter.java
 * Create by Nhut Nguyen
 * Date 31/11/2017
 */
package com.example.gamma.todoapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.InviteEvent;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* Class using for Adapter of Testview Task */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    private List<Task> mListTask;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private ApiService mApiService;

    public TaskAdapter(Context context,List<Task> mListTask) {
        this.mListTask = mListTask;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public EditText mEdtTask;
        public TextView mTxvTaskId;
        public CheckBox mCkbStatus;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTxvTaskId = itemView.findViewById(R.id.txvTaskId);
            mEdtTask = itemView.findViewById(R.id.edtTasks);
            mCkbStatus = itemView.findViewById(R.id.ckbStatus);
            /* Can set event for itemView here */
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = mLayoutInflater.inflate(R.layout.item_task,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final TaskAdapter.MyViewHolder holder, int position) {

        final Task task = mListTask.get(position);
        holder.mTxvTaskId.setText(task.getTaskId().toString());
        holder.mEdtTask.setText(task.getTask());
        holder.mCkbStatus.setChecked(Boolean.parseBoolean(task.getStatus().toString()));
        holder.mEdtTask.setInputType(InputType.TYPE_NULL);

        // Set strike text when load Task
        if (holder.mCkbStatus.isChecked()) {
            holder.mEdtTask.setPaintFlags(holder.mEdtTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.mEdtTask.setPaintFlags(0);
        }

        // Effect when User long click mEdtTask
        holder.mEdtTask.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                holder.mEdtTask.setTextColor(Color.GRAY);
                holder.mEdtTask.setInputType(InputType.TYPE_CLASS_TEXT);
                return true;
            }
        });

        //When User press key Enter , call request update Task
        holder.mEdtTask.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    holder.mEdtTask.setTextColor(Color.parseColor("#465b65"));
                    holder.mEdtTask.setInputType(InputType.TYPE_NULL);

                    mApiService = ApiUtils.getApiInterface();
                    mApiService.updateTask(Constant.AUTH_VALUE + LoginActivity.mAccessToken,
                            task.getTaskId().toString(), holder.mEdtTask.getText().toString(),
                            task.getStatus().toString()).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) { }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) { }
                    });
                    return true;
                }
                return false;
            }
        });

        //Event User check Checkbox
        holder.mCkbStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.mCkbStatus.isChecked()) {
                    holder.mEdtTask.setPaintFlags(holder.mEdtTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    mApiService = ApiUtils.getApiInterface();
                    mApiService.updateStatus(Constant.AUTH_VALUE + LoginActivity.mAccessToken,
                                task.getTaskId().toString(), Constant.STATUS_TRUE).enqueue(new Callback<Task>() {

                        @Override
                        public void onResponse(Call<Task> call, Response<Task> response) { }

                        @Override
                        public void onFailure(Call<Task> call, Throwable t) { }
                    });
                    holder.mCkbStatus.setChecked(true);
                } else {
                    holder.mEdtTask.setPaintFlags(0);
                    mApiService = ApiUtils.getApiInterface();
                    mApiService.updateStatus(Constant.AUTH_VALUE + LoginActivity.mAccessToken,
                                task.getTaskId().toString(), Constant.STATUS_FALSE).enqueue(new Callback<Task>() {
                        @Override
                        public void onResponse(Call<Task> call, Response<Task> response) { }

                        @Override
                        public void onFailure(Call<Task> call, Throwable t) { }
                    });
                    holder.mCkbStatus.setChecked(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListTask.size();
    }
}