package com.example.appbaothuc;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.appbaothuc.alarmsetting.SettingAlarmActivity;
import com.example.appbaothuc.services.NotificationService;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {
    private Context context;                                // Context là class cha của Activity, nó có nhiều phương thức quan trọng chịu trách nhiệm xử lý liên quan tới hệ thống và giao diện. Vì vậy ta thường truyền Context đi vòng vòng khắp mọi nơi
    private List<Alarm> listAlarm;                          // Dĩ nhiên là một Adapter thì phải được nhận vào một list các item
    private HashMap<Integer, Alarm> hashMapIdViewAlarm;


    // Phương thức khởi tạo AlarmAdapter, đòi hỏi nhận vào cái Context và một list các Alarm
    public AlarmAdapter(Context context, List<Alarm> listAlarm) {
        // Lấy context và listAlarm
        this.context = context;
        this.listAlarm = listAlarm;

        // Khởi tạo hashMapIdViewAlarm
        hashMapIdViewAlarm = new HashMap<>();
    }

    // Phương thức này chỉ dùng để trả về số lượng item trong list
    @Override
    public int getItemCount() {
        return this.listAlarm.size();
    }


    // Phương thức onCreateViewHolder được gọi khi có một item được thêm vào list
    // Nó nhận vào 2 tham số. ViewGroup là class cha của RecyclerView, nó có khả năng nhét thêm View vào trong RecyclerView
    // i là một con số đại diện cho loại của View mà sẽ được thêm vào list RecyclerView. Ở đây chúng ta không quan tâm giá trị này
    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Để nhét thêm View vào RecyclerView, đầu tiên ta tạo ra một biến kiểu LayoutInflater
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());

        // LayoutInflater có khả năng tạo ra một View từ một layout cho trước, rồi đưa cho ViewGroup. Ở đây ta không cần quan tâm attachToRoot làm gì
        View alarmView = layoutInflater.inflate(R.layout.alarm_item, viewGroup, false);

        // TODO Lưu ý: Để đạt được mục địch cuối cùng là truyền một Alarm sang cho SettingAlarmActivity, ta cần chuẩn bị trước một điều, đó là gán Id cho cái View vừa tạo
        // TODO Lưu ý: Tuy ta có thể hoàn toàn đặt các Id trùng nhau cho các View mà không bị lỗi, nhưng để việc làm việc với các View sau này diễn ra một cách chính xác, như findViewById
        // TODO Lưu ý: thì ta nên sử dụng phương thức View.generateViewId để sinh ra các Id không bao giờ trùng nhau
        alarmView.setId(View.generateViewId());

        // Đăng ký sự kiện click cho cái alarmView vừa tạo
        alarmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ở đây v chính là alarmView
                openAlarmSetting(v);
            }
        });

        // TODO Lưu ý: Để đạt được mục đích cuối cùng là truyền một Alarm sang cho SettingAlarmActivity, ta cần làm một công việc tiếp theo nữa, đó là
        // TODO Lưu ý: cất cái Id (key) của cái alarmView vừa tạo và cái Alarm (value) vừa mới thêm vào listAlarm vào trong hashMapIdViewAlarm
        hashMapIdViewAlarm.put(alarmView.getId(), listAlarm.get(getItemCount() - 1));

        // Ta bắt buộc phải trả về một cái ViewHolder, đó là yêu cầu của cái phương thức mà chúng ta đang ghi đè này
        // Bây giờ hãy xem tiếp class AlarmViewHolder ở bên dưới
        return new AlarmViewHolder(alarmView);

        // Sau khi lệnh return được chạy, một cái AlarmViewHolder sẽ được nhả ra và truyền vào cái phương thức onBindViewHolder bên dưới
    }


    // Phương thức onBindViewHolder nhận vào một cái AlarmViewHolder do phương thức onCreateViewHolder bên trên trả về
    // và i chính là cái vị trí position của cái View mà vừa được thêm vào RecyclerView
    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder alarmViewHolder, int i) {
        // Lấy ra cái Alarm từ vị trí i
        Alarm alarm = listAlarm.get(i);

        // Lúc này, chắc chắn cái checkBoxEnable là của đúng cái item tại vị trí thứ i trong cái RecyclerView, chứ không phải của cái checkBoxEnable của cái item tại vị trí nào khác
        // Chắc chắn cái textViewHour là của đúng cái item tại vị trí thứ i trong cái RecyclerView, chứ không phải của cái textViewHour của cái item tại vị trí nào khác
        // Chắc chắn cái textViewMinute là của đúng cái item tại vị trí thứ i trong cái RecyclerView, chứ không phải của cái textViewMinute của cái item tại vị trí nào khác
        CheckBox checkBoxEnable = alarmViewHolder.checkBoxEnable;
        TextView textViewHour = alarmViewHolder.textViewHour;
        TextView textViewMinute = alarmViewHolder.textViewMinute;

        // Nhờ việc lấy ra đúng chính xác các CheckBox và TextView tại vị trí thứ i
        // ta có thể đặt thuộc tính cho chúng một cách chính xác, dựa theo cái Alarm tại vị trí thứ i đã được lấy ra bên trên
        checkBoxEnable.setChecked(alarm.isEnable());
        textViewHour.setText(String.valueOf(alarm.getHour()));
        if (alarm.getMinute() < 10) {
            textViewMinute.setText("0" + String.valueOf(alarm.getMinute()));
        } else {
            textViewMinute.setText(String.valueOf(alarm.getMinute()));
        }
    }

    public void openAlarmSetting(View v) {
        Intent intent = new Intent(context, SettingAlarmActivity.class);

        // TODO Lưu ý: Bởi vì ta đã cất từng cái Id (key) của cái View mà được thêm vào RecyclerView, với cái Alarm (value) tương ứng
        // TODO Lưu ý: Nên ở bước này, ta có thể lấy ra được đúng chính xác Alarm, bất kể người dùng chọn vào item nào trong RecyclerView
        // TODO Lưu ý: Bởi vì mỗi khi người dùng chọn vào một item, thì phương thức openAlarmSetting này được gọi tới
        // TODO Lưu ý: Và View v chính là cái View mà người dùng chọn vào
        // TODO Lưu ý: Rồi từ đó, ta sử dụng v.getId để ra được đúng cái Alarm của cái View đó, rồi truyền cho SettingAlarmActivity
        intent.putExtra("alarm", hashMapIdViewAlarm.get(v.getId()));
        context.startActivity(intent);
    }



    // Class AlarmViewHolder chứa các thuộc tính là các View như CheckBox, TextView nằm bên trong một cái View nằm bên trong RecyclerView
    class AlarmViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBoxEnable;
        private TextView textViewHour;
        private TextView textViewMinute;

        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            // Lấy ra các View con như CheckBox, TextView nằm trong cái itemView. Mà cái itemView thực ra chính là một cái item bên trong RecyclerView
            checkBoxEnable = itemView.findViewById(R.id.checkBox_enable);
            textViewHour = itemView.findViewById(R.id.textView_hour);
            textViewMinute = itemView.findViewById(R.id.textView_minute);




            // TODO: Debug purpose.
            final DatabaseHandler databaseHandler = new DatabaseHandler(context);
            textViewHour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar time1 = Calendar.getInstance();
                    Calendar time2 = Calendar.getInstance();
                    Calendar time3 = Calendar.getInstance();
                    Calendar time4 = Calendar.getInstance();
                    Calendar time5 = Calendar.getInstance();
                    Calendar time6 = Calendar.getInstance();
                    Calendar time7 = Calendar.getInstance();
                    Calendar time8 = Calendar.getInstance();
                    Calendar time9 = Calendar.getInstance();
                    Calendar time0 = Calendar.getInstance();
                    time1.add(Calendar.MINUTE, 5);
                    time2.add(Calendar.MINUTE, 3);
                    time3.add(Calendar.MINUTE, 1);
                    time4.add(Calendar.MINUTE, 4);
                    time5.add(Calendar.MINUTE, 2);
                    time6.add(Calendar.MINUTE, 6);
                    time7.add(Calendar.MINUTE, 7);
                    time8.add(Calendar.MINUTE, 8);
                    time9.add(Calendar.MINUTE, 9);
                    time0.add(Calendar.MINUTE, 10);
                    int hour1 = time1.get(Calendar.HOUR_OF_DAY);
                    int hour2 = time2.get(Calendar.HOUR_OF_DAY);
                    int hour3 = time3.get(Calendar.HOUR_OF_DAY);
                    int hour4 = time4.get(Calendar.HOUR_OF_DAY);
                    int hour5 = time5.get(Calendar.HOUR_OF_DAY);
                    int hour6 = time6.get(Calendar.HOUR_OF_DAY);
                    int hour7 = time7.get(Calendar.HOUR_OF_DAY);
                    int hour8 = time8.get(Calendar.HOUR_OF_DAY);
                    int hour9 = time9.get(Calendar.HOUR_OF_DAY);
                    int hour0 = time0.get(Calendar.HOUR_OF_DAY);
                    int minute1 = time1.get(Calendar.MINUTE);
                    int minute2 = time2.get(Calendar.MINUTE);
                    int minute3 = time3.get(Calendar.MINUTE);
                    int minute4 = time4.get(Calendar.MINUTE);
                    int minute5 = time5.get(Calendar.MINUTE);
                    int minute6 = time6.get(Calendar.MINUTE);
                    int minute7 = time7.get(Calendar.MINUTE);
                    int minute8 = time8.get(Calendar.MINUTE);
                    int minute9 = time9.get(Calendar.MINUTE);
                    int minute0 = time0.get(Calendar.MINUTE);
                    List<Boolean> listRepeatDay1 = Arrays.asList(true, true, true, true, true, true, true);
                    List<Boolean> listRepeatDay2 = Arrays.asList(true, true, true, true, true, true, true);
                    List<Boolean> listRepeatDay3 = Arrays.asList(true, true, true, true, true, true, true);
                    List<Boolean> listRepeatDay4 = Arrays.asList(true, true, true, true, true, true, true);
                    List<Boolean> listRepeatDay5 = Arrays.asList(true, true, true, true, true, true, true);
                    List<Boolean> listRepeatDay6 = Arrays.asList(true, true, true, true, true, true, true);
                    List<Boolean> listRepeatDay7 = Arrays.asList(true, true, true, true, true, true, true);
                    List<Boolean> listRepeatDay8 = Arrays.asList(true, true, true, true, true, true, true);
                    List<Boolean> listRepeatDay9 = Arrays.asList(true, true, true, true, true, true, true);
                    List<Boolean> listRepeatDay0 = Arrays.asList(true, true, true, true, true, true, true);
                    databaseHandler.insertAlarm(true, hour1, minute1, listRepeatDay1);
                    databaseHandler.insertAlarm(true, hour2, minute2, listRepeatDay2);
                    databaseHandler.insertAlarm(true, hour3, minute3, listRepeatDay3);
                    databaseHandler.insertAlarm(true, hour4, minute4, listRepeatDay4);
                    databaseHandler.insertAlarm(true, hour5, minute5, listRepeatDay5);
                    databaseHandler.insertAlarm(true, hour6, minute6, listRepeatDay6);
                    databaseHandler.insertAlarm(true, hour7, minute7, listRepeatDay7);
                    databaseHandler.insertAlarm(true, hour8, minute8, listRepeatDay8);
                    databaseHandler.insertAlarm(true, hour9, minute9, listRepeatDay9);
                    databaseHandler.insertAlarm(true, hour0, minute0, listRepeatDay0);
                    Intent intent = new Intent(context, NotificationService.class);
                    context.startService(intent);
                }
            });
            // TODO:...
        }
    }
}