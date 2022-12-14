package com.example.qlnv;

import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testTime() throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
//        try {
//            Date dateTime = sf.parse("24-11-2022 18:55");
////            Date now = new Date(System.currentTimeMillis()); // 2016-03-10 22:06:10
//            Date now = sf.parse("25-11-2022 18:55");
//           Log.d("compare",dateTime.compareTo(now) +"");
//            System.out.println(dateTime);
//            System.out.println(now);
////            System.out.println("");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date date1 = sdf.parse("2020-07-20");
//        Date date2 = sdf.parse("2020-06-18");
//        Log.d("compare",date1.compareTo(date2) +"");
        Date now = new Date(System.currentTimeMillis());

        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");
        String strDate = dateFormat.format(now);
        Log.d("now",strDate+"");
    }
}