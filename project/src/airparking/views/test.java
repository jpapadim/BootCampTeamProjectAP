package airparking.views;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import airparking.model.*;
import airparking.dao.*;

public class test {

	public static void main(String[] args) {
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");

			Schedule requestPeriod = new Schedule(0, true, formatter.parse("2017/03/05 03:30"),formatter.parse("2017/05/05 23:30"));

			Schedule parkingAvailability = new Schedule(0, true, formatter.parse("2017/02/05 03:30"),formatter.parse("2019/03/05 03:30"));

			ArrayList<Advancedschedule> advancedschedules = new ArrayList<>();
			advancedschedules.add(new Advancedschedule(0, 0, "02:00", "05:00", 1, 3, 9, 4));
			advancedschedules.add(new Advancedschedule(0, 0, "02:00", "14:00", 6, 7, 9, 4));

			ArrayList<Schedule> bookings = new ArrayList<>();
			bookings.add(new Schedule(0, true, formatter.parse("2017/02/05 04:30"), formatter.parse("2017/04/05 04:30")));
			bookings.add(new Schedule(0, true, formatter.parse("2017/04/15 04:30"), formatter.parse("2017/05/01 04:30")));

			TimeLine timeline = new TimeLine();
			timeline.calculate(requestPeriod, parkingAvailability, bookings, advancedschedules);
			timeline.print();
			
			AllDAO dao=new AllDAO();
			for(int i=1;i<31;i++){
			ParkingReport pr=dao.getParkingReport(i);
			if(pr!=null){
			System.out.println("----------------------------------------------------------");
			System.out.println(pr.getParking());
			System.out.println(pr.getAvailability());
			for(Advancedschedule x: pr.getAdvancedschedule()){
				System.out.println(x);
			}
			for(Offer x: pr.getOffer()){
				System.out.println(x);
			}
			for(Booking x: pr.getBooking()){
				System.out.println(x);
			}
			for(Review x: pr.getReview()){
				System.out.println(x);
			}
			}
			}
			
			boolean[] a=new boolean[3];
			a[0]=false; a[1]=false; a[2]=true;
			System.out.println(a[0] && a[1] || a[2]);
			System.out.println(3<2);
			System.out.println(3<<2);
			Float f = new Float("12"); 
			System.out.println(f);
		
			  
			String number="10m000";  
			System.out.println(number.matches("([0-9]*)(\\.)?([0-9]*)")); //if true then decimal else not  

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
