package com.example.qlnv;

import com.example.qlnv.model.Employee;
import com.example.qlnv.model.Room;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Injector {
    public static Employee employee;
    public static String IP = "172.16.2.66";
    public static String URL_USER = "http://"+IP+":8080/QLNS_V1/Staffs/getStaff.php";
    public static String URL_ROOM = "http://"+IP+":8080/QLNS_V1/Departments/getPhongBan.php";
    public static String URL_QUERY_USER_ROOM = "http://" + IP +":8080/QLNS_V1/Staffs/getStaffsRoom.php";
    public static String URL_ADD_USER = "http://" + IP +":8080/QLNS_V1/Staffs/addStaff.php";
    public static String URL_DEL_USER = "http://" + IP +":8080/QLNS_V1/Staffs/delStaff.php";
    public static String URL_EDIT_USER = "http://" + IP +":8080/QLNS_V1/Staffs/editStaff.php";

    public static String URL_ADD_ROOM = "http://" + IP +":8080/QLNS_V1/Departments/addPhongBan.php";
    public static String URL_DEL_ROOM = "http://" + IP +":8080/QLNS_V1/Departments/delPhongBan.php";
    public static String URL_EDIT_ROOM = "http://" + IP +":8080/QLNS_V1/Departments/editPhongBan.php";
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



}
