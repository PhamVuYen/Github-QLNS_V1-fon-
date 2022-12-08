package com.example.qlnv;

import com.example.qlnv.model.Employee;
import com.example.qlnv.model.Room;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Injector {
    public static Employee employee;
    public static String IP = "172.16.2.54";
    public static String URL_USER = "http://"+IP+":8080/QLNS_V1/Staffs/getStaff.php";
    public static String URL_QUERY_USER_ROOM = "http://" + IP +":8080/QLNS_V1/Staffs/getStaffsRoom.php";
    public static String URL_ADD_USER = "http://" + IP +":8080/QLNS_V1/Staffs/addStaff.php";
    public static String URL_DEL_USER = "http://" + IP +":8080/QLNS_V1/Staffs/delStaff.php";
    public static String URL_EDIT_USER = "http://" + IP +":8080/QLNS_V1/Staffs/editStaff.php";
    public static String URL_UPDATE_PASS = "http://" + IP +":8080/QLNS_V1/Login/updatePassword.php";

    public static String URL_ADD_TASK = "aewbewvwveewqwdqwdef111fff";



    public static String URL_ROOM = "http://"+IP+":8080/QLNS_V1/Departments/getPhongBan.php";
    public static String URL_ADD_ROOM = "http://" + IP +":8080/QLNS_V1/Departments/addPhongBan.php";
    public static String URL_DEL_ROOM = "http://" + IP +":8080/QLNS_V1/Departments/delPhongBan.php";
    public static String URL_EDIT_ROOM = "http://" + IP +":8080/QLNS_V1/Departments/editPhongBan.php";
    public static String URL_EDIT_LANHDAO = "http://" + IP +":8080/QLNS_V1/Staffs/setChucVu.php";
    public static String URL_INSERT_LANHDAO = "http://" + IP +":8080/QLNS_V1/Staffs/bonhiemChucVu.php";

    public static String URL_ASSIGN_TASK = "http://" + IP +":8080/QLNS_V1/Jobs/addJob.php";
    public static String URL_QUERY_TASK = "http://" + IP +":8080/QLNS_V1/Jobs/getJob.php";
    public static String URL_UPDATE_TASK = "http://" + IP +":8080/QLNS_V1/Jobs/editJob.php";
    public static String URL_DELETE_TASK = "http://" + IP +":8080/QLNS_V1/Jobs/delJob.php";



    public static Employee getEmployee() {
        if (employee == null) {
            employee = new Employee();
        }
        return employee;
    }

    public static String dateToString(Date date) {
        String dateToStr = date.toInstant()
                .atOffset(ZoneOffset.UTC)
                .format( DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return  dateToStr;
    }
    public static String datetoStringTime(Date date) {
        String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        String todayAsString = df.format(date);
        return todayAsString;
    }



}
