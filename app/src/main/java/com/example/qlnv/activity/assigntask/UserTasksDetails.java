package com.example.qlnv.activity.assigntask;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlnv.R;

public class UserTasksDetails extends AppCompatActivity {

    private Button assign_new_task;
    private RecyclerView ordersList;
    private String p2_number = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_tasks_details);

        p2_number = getIntent().getStringExtra("uid");
       assign_new_task=findViewById(R.id.assign_new_task);


        ordersList = findViewById(R.id.task_details);
        ordersList.setLayoutManager(new LinearLayoutManager(this));

        assign_new_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserTasksDetails.this,AssignTaskActivity.class);
                intent.putExtra("uid",p2_number);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

//        FirebaseRecyclerOptions<Tasks> options =
//                new FirebaseRecyclerOptions.Builder<Tasks>()
//                        .setQuery(search,Tasks.class)
//                        .build();
//
//        FirebaseRecyclerAdapter<Task, AdminOrdersViewHolder> adapter =
//                new FirebaseRecyclerAdapter<Tasks, AdminOrdersViewHolder>(options) {  //firebase recycler "options" object
//                    @Override
//                    protected void onBindViewHolder(@NonNull final AdminOrdersViewHolder adminOrdersViewHolder, final int ix, @NonNull final Tasks adminOrders) {
//                        int xy=ix;
//                        xy++;
//                        adminOrdersViewHolder.task_id.setText("Task number: "+xy);
//                        adminOrdersViewHolder.task_name.setText("To Do: "+adminOrders.getTask_name());
//                        adminOrdersViewHolder.task_status.setText("Status: "+adminOrders.getTask_status());
//                        adminOrdersViewHolder.reason.setText("Reason: "+adminOrders.getReason());
//                    }
//
//                    @NonNull
//                    @Override
//                    public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);
//                        return new AdminOrdersViewHolder(view);
//                    }
//                };
//        ordersList.setAdapter(adapter);
//        adapter.startListening();
    }



//    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder{
//        public Button ShowOrdersBtn;
//        public TextView task_id,task_name,task_status,reason;
//        public AdminOrdersViewHolder(@NonNull View itemView) {
//            super(itemView);
//            task_id = itemView.findViewById(R.id.task_id);
//            task_name = itemView.findViewById(R.id.task_name);
//            task_status = itemView.findViewById(R.id.task_status);
//            reason=itemView.findViewById(R.id.reason);
//
//        }
//    }



}
