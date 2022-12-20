package com.example.qlnv;

import android.util.Log;

import com.example.qlnv.model.Employee;
import com.example.qlnv.model.Room;
import com.example.qlnv.model.TimeKeeping;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Injector {
    public static Employee employee;
    public static TimeKeeping timeKeeping;
    public static String IP = "192.168.252.149";
    public static String URL_USER = "http://" + IP + ":80/QLNS_V1/Staffs/getStaff.php";
    public static String URL_QUERY_USER_ROOM = "http://" + IP + ":80/QLNS_V1/Staffs/getStaffsRoom.php";
    public static String URL_ADD_USER = "http://" + IP + ":80/QLNS_V1/Staffs/addStaff.php";
    public static String URL_DEL_USER = "http://" + IP + ":80/QLNS_V1/Staffs/delStaff.php";
    public static String URL_EDIT_USER = "http://" + IP + ":80/QLNS_V1/Staffs/editStaff.php";
    public static String URL_UPDATE_PASS = "http://" + IP + ":80/QLNS_V1/Login/updatePassword.php";

    public static String URL_ROOM = "http://" + IP + ":80/QLNS_V1/Departments/getPhongBan.php";
    public static String URL_ADD_ROOM = "http://" + IP + ":80/QLNS_V1/Departments/addPhongBan.php";
    public static String URL_DEL_ROOM = "http://" + IP + ":80/QLNS_V1/Departments/delPhongBan.php";
    public static String URL_EDIT_ROOM = "http://" + IP + ":80/QLNS_V1/Departments/editPhongBan.php";
    public static String URL_EDIT_LANHDAO = "http://" + IP + ":80/QLNS_V1/Staffs/setChucVu.php";
    public static String URL_INSERT_LANHDAO = "http://" + IP + ":80/QLNS_V1/Staffs/bonhiemChucVu.php";

    public static String URL_ASSIGN_TASK = "http://" + IP + ":80/QLNS_V1/Jobs/addJob.php";
    public static String URL_QUERY_TASK = "http://" + IP + ":80/QLNS_V1/Jobs/getJob.php";
    public static String URL_UPDATE_TASK = "http://" + IP + ":80/QLNS_V1/Jobs/editJob.php";
    public static String URL_DELETE_TASK = "http://" + IP + ":80/QLNS_V1/Jobs/delJob.php";
    public static String URL_UPDATE_STATUS_TASK = "http://" + IP + ":80/QLNS_V1/Jobs/getStatus.php";
    public static String URL_QUERY_ALL_TASK = "http://" + IP + ":80/QLNS_V1/Jobs/getJobRoom.php";

    public static String URL_CHECK_CHAMCONGNGAY = "http://" + IP + ":80/QLNS_V1/TimeRecorder/CheckNgayChamCong.php";
    public static String URL_CHECKIN_CHAMCONG = "http://" + IP + ":80/QLNS_V1/TimeRecorder/addChechIn.php";
    public static String URL_CHECHKOUT_CHAMCONG = "http://" + IP + ":80/QLNS_V1/TimeRecorder/addChechOut.php";

    public static String URL_ADD_TIME_RECORER = "http://" + IP + ":80/QLNS_V1/TimeRecorder/addTimeRecorder.php";
    public static String URL_UPDATE_TIME_RECORER = "http://" + IP + ":80/QLNS_V1/TimeRecorder/editTimeRecorder.php";
    public static String URL_GET_TIME_RECORER = "http://" + IP + ":80/QLNS_V1/TimeRecorder/getTimeRecorder.php";


    public static String HOUR_ARRIVE = "08:00";
    public static String TIME_LEAVE = "17:00";


    public static Employee getEmployee() {
        if (employee == null) {
            employee = new Employee();
        }
        return employee;
    }

    public static TimeKeeping getTimeKeeping() {
        if (timeKeeping == null) {
            timeKeeping = new TimeKeeping();
        }
        return timeKeeping;
    }

    public static String dateToString(Date date) {
        String dateToStr = date.toInstant()
                .atOffset(ZoneOffset.UTC)
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return dateToStr;
    }

    public static String datetoStringTime(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String todayAsString = formatter.format(date);
        Log.d("todayAsString", todayAsString);
        return todayAsString;
    }

    public static int compareDateWithCurrent(String time) throws Exception {
        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date dateTime = sf.parse(time);
        Date now = new Date(System.currentTimeMillis()); // 2016-03-10 22:06:10
        Log.d("now", now + "");
        return dateTime.compareTo(now);
    }

    public static String getCurrentDate() {
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String strDate = dateFormat.format(currentTime);
        return strDate;
    }

    public static String getCurrentTime() {
        String date = getCurrentDate();
        String[] dateTime = date.split(" ");
        return dateTime[1];
    }

    public static String getCurrentDay() {
        String date = getCurrentDate();
        String[] dateTime = date.split(" ");
        return dateTime[0];
    }

    public static String getLateTime(String currentTime) throws ParseException {
        DateFormat df = new SimpleDateFormat("hh:mm");
        Date date1 = df.parse(currentTime);
        Date date2 = df.parse(HOUR_ARRIVE);
        long diff = Math.abs(date1.getTime() - date2.getTime());
        long rs = diff / (1000 * 60);
        return String.valueOf(rs);
    }

}
