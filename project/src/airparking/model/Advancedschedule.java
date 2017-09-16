package airparking.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

public class Advancedschedule {

	private int advschID;
	private int parkingID;
	private Date sTimeOfDay;
	private Date eTimeOfDay;
	private int sDayOfWeek;
	private int eDayOfWeek;
	private int sMonthOfYear;
	private int eMonthOfYear;

	public Advancedschedule(int advschID, int parkingID, Date sTimeOfDay, Date eTimeOfDay, int sDayOfWeek,
			int eDayOfWeek, int sMonthOfYear, int eMonthOfYear) {
		super();
		this.advschID = advschID;
		this.parkingID = parkingID;
		this.sTimeOfDay = sTimeOfDay;
		this.eTimeOfDay = eTimeOfDay;
		this.sDayOfWeek = sDayOfWeek;
		this.eDayOfWeek = eDayOfWeek;
		this.sMonthOfYear = sMonthOfYear;
		this.eMonthOfYear = eMonthOfYear;
		DateTime start = new DateTime(sTimeOfDay.getTime());
		DateTime end = new DateTime(eTimeOfDay.getTime());
		if (start.isAfter(end)) {
			end.plusDays(1);
			this.eTimeOfDay = end.toDate();
		}
	}

	public Advancedschedule(Advancedschedule as) {
		super();
		this.advschID = as.advschID;
		this.parkingID = as.parkingID;
		this.sTimeOfDay = as.sTimeOfDay;
		this.eTimeOfDay = as.eTimeOfDay;
		this.sDayOfWeek = as.sDayOfWeek;
		this.eDayOfWeek = as.eDayOfWeek;
		this.sMonthOfYear = as.sMonthOfYear;
		this.eMonthOfYear = as.eMonthOfYear;
	}

	public Advancedschedule(int advschID, int parkingID, String sTimeOfDay, String eTimeOfDay, int sDayOfWeek,
			int eDayOfWeek, int sMonthOfYear, int eMonthOfYear) {
		DateFormat formatter = new SimpleDateFormat("HH:mm");
		try {
			Date sTime= formatter.parse(sTimeOfDay);
			Date eTime= formatter.parse(eTimeOfDay);
			this.advschID = advschID;
			this.parkingID = parkingID;
			this.sTimeOfDay = sTime;
			this.eTimeOfDay = eTime;
			this.sDayOfWeek = sDayOfWeek;
			this.eDayOfWeek = eDayOfWeek;
			this.sMonthOfYear = sMonthOfYear;
			this.eMonthOfYear = eMonthOfYear;
			DateTime start = new DateTime(sTime.getTime());
			DateTime end = new DateTime(eTime.getTime());
			if (start.isAfter(end)) {
				end.plusDays(1);
				this.eTimeOfDay = end.toDate();
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getAdvschID() {
		return advschID;
	}

	public void setAdvschID(int advschID) {
		this.advschID = advschID;
	}

	public int getParkingID() {
		return parkingID;
	}

	public Date getsTimeOfDay() {
		return sTimeOfDay;
	}
	public int getsMillisOfDay() {
		DateTime date= new DateTime(sTimeOfDay.getTime());
		return date.getMillisOfDay();	
	}

	public Date geteTimeOfDay() {
		return eTimeOfDay;
	}
	public int geteMillisOfDay() {
		DateTime date= new DateTime(eTimeOfDay.getTime());
		return date.getMillisOfDay();	
	}

	public int getsDayOfWeek() {
		return sDayOfWeek;
	}

	public int geteDayOfWeek() {
		return eDayOfWeek;
	}

	public int getsMonthOfYear() {
		return sMonthOfYear;
	}

	public int geteMonthOfYear() {
		return eMonthOfYear;
	}

	public void setParkingID(int parkingID) {
		this.parkingID = parkingID;
	}

	public void setsTimeOfDay(Date sTimeOfDay) {
		this.sTimeOfDay = sTimeOfDay;
	}

	public void seteTimeOfDay(Date eTimeOfDay) {
		this.eTimeOfDay = eTimeOfDay;
	}

	public void setsDayOfWeek(int sDayOfWeek) {
		this.sDayOfWeek = sDayOfWeek;
	}

	public void seteDayOfWeek(int eDayOfWeek) {
		this.eDayOfWeek = eDayOfWeek;
	}

	public void setsMonthOfYear(int sMonthOfYear) {
		this.sMonthOfYear = sMonthOfYear;
	}

	public void seteMonthOfYear(int eMonthOfYear) {
		this.eMonthOfYear = eMonthOfYear;
	}

	public boolean hasTimePeriot() {
		DateFormat formatter = new SimpleDateFormat("HH:mm");
		if (formatter.format(this.sTimeOfDay).equals("00:00") && formatter.format(this.eTimeOfDay).equals("00:00")) {
			return false;
		} else
			return true;
	}

	public boolean hasDayPeriot() {
		if (this.sDayOfWeek == 0 || this.eDayOfWeek == 0) {
			return false;
		} else
			return true;
	}

	public boolean hasMonthPeriot() {
		if (this.sMonthOfYear == 0 || this.eMonthOfYear == 0) {
			return false;
		} else
			return true;
	}

	public void setTimePeriodToFalse() {
		DateFormat formatter = new SimpleDateFormat("HH:mm");
		try {
			this.sTimeOfDay.setTime(formatter.parse("00:00").getTime());
			this.eTimeOfDay.setTime(formatter.parse("00:00").getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setDayPeriodToFalse() {
		this.setsDayOfWeek(0);
		this.seteDayOfWeek(0);
	}
	
	public void setMonthPeriodToFalse() {
		this.setsMonthOfYear(0);
		this.seteMonthOfYear(0);
	}

	public String type() {
		if (!hasTimePeriot() && !hasDayPeriot() && !hasMonthPeriot()) {
			return null;
		} else {
			String stype = "";
			if (hasTimePeriot())
				stype += "T";
			if (hasDayPeriot())
				stype += "D";
			if (hasMonthPeriot())
				stype += "M";
			return stype;
		}
	}
    public Advancedschedule combineTimePeriods(Advancedschedule as){
    	Interval interv1 = new Interval(this.sTimeOfDay.getTime(), this.eTimeOfDay.getTime());
		Interval interv2 = new Interval(as.sTimeOfDay.getTime(), as.eTimeOfDay.getTime());
		if (interv1.overlaps(interv2) || interv1.abuts(interv2)) {
			Date start = (this.sTimeOfDay.before(as.sTimeOfDay)) ? this.sTimeOfDay : as.sTimeOfDay;
			Date end = (this.eTimeOfDay.after(as.eTimeOfDay)) ? this.eTimeOfDay : as.eTimeOfDay;
			Duration duration = new Duration(start.getTime(), end.getTime());
			Advancedschedule asOut = new Advancedschedule(as);
			
			if (duration.toPeriod().toStandardDays().getDays() >= 1){
				asOut.setTimePeriodToFalse();
			}
			else{
				asOut.setsTimeOfDay(start);
			    asOut.seteTimeOfDay(end);
			}
			return asOut;
		} else
			return null;
    }
    
    public Advancedschedule combineDayPeriods(Advancedschedule as){
    	int startd1=this.getsDayOfWeek();
		int endd1=this.geteDayOfWeek();
		int startd2 = as.getsDayOfWeek();
		int endd2 = as.geteDayOfWeek();
		if (startd1 > endd1)
			endd1 += 7;
		if (startd2 > endd2)
			endd1 += 7;
		if (endd1 >= startd2 || endd2 >= startd1) {
			int newStart = (startd1 < startd2) ? startd1 : startd2;
			int newEnd = (endd1 > endd2) ? endd1 : endd2;
			Advancedschedule asOut = new Advancedschedule(as);
			if (newEnd - newStart >= 6)
				asOut.setDayPeriodToFalse();
			else {
				asOut.setsDayOfWeek(newStart);
				asOut.seteDayOfWeek((newEnd > 7) ? (newEnd - 7) : newEnd);
			}
			return asOut;
		} else
			return null;
	}
    public Advancedschedule combineMonthPeriods(Advancedschedule as){
    	int startm1=this.getsMonthOfYear();
		int endm1=this.geteMonthOfYear();
		int startm2=as.getsMonthOfYear();
		int endm2=as.geteMonthOfYear();
          if(startm1>endm1) endm1+=12;
          if(startm2>endm2) endm1+=12;
          if (endm1 >= startm2 || endm2 >= startm1) {
         int newStart=(startm1<startm2)?startm1:startm2;
         int newEnd=(endm1>endm2)?endm1:endm2;
         Advancedschedule asOut = new Advancedschedule(as);
         if(newEnd-newStart>=11) asOut.setMonthPeriodToFalse();
         else{
        	 asOut.setsMonthOfYear(newStart);
             asOut.seteMonthOfYear((newEnd>12)?(newEnd-12):newEnd);
         }
         return asOut;
          }
          else return null;
    }
	public Advancedschedule combine(Advancedschedule as) {
		String type1 = this.type();
		String type2 = as.type();
		if (type2 == null)
			return this;
		else if (type1 == null)
			return as;
		else {

			if (type1.equals(type2)) {
				if (type1.length() == 1) {
					if (type1.equals("T")) {
						return combineTimePeriods(as);
					} else if (type1.equals("D")) {
						return combineDayPeriods(as);
					} else if(type1.equals("M")){
						return combineMonthPeriods(as);
					}
				}
				
			}

		}

		return null;
	}
	
	public boolean hasMonth(int month){
		int start=this.getsMonthOfYear();
		int end=this.geteMonthOfYear();
		if(!this.hasMonthPeriot())return true;
		if(start<=end){
			if(month>=start && month<=end) return true;
			else return false;
		}
		else {
			if(month>=start || month<=end) return true;
			else return false;
		}
	}
	
	public boolean hasDay(int day){
		int start=this.getsDayOfWeek();
		int end=this.geteDayOfWeek();
		if(!this.hasDayPeriot())return true;
		if(start<=end){
			if(day>=start && day<=end) return true;
			else return false;
		}
		else {
			if(day>=start || day<=end) return true;
			else return false;
		}
	}
	
	public boolean hasMillis(int millis){
		int start=this.getsMillisOfDay();
		int end=this.geteMillisOfDay();
		if(!this.hasTimePeriot())return true;
		if(start<=end){
			if(millis>=start && millis<=end) return true;
			else return false;
		}
		else {
			if(millis>=start || millis<=end) return true;
			else return false;
		}
	}
	
	public int numOfMonthsInPeriod(){
		if(!this.hasMonthPeriot()) return 12;
		else{
			int start=this.getsMonthOfYear();
			int end=this.geteMonthOfYear();
			if(end<start) end+=12;
			return end-start+1;
		}
	}
	
	public int numOfDaysInPeriod(){
		if(!this.hasDayPeriot()) return 7;
		else{
			int start=this.getsDayOfWeek();
			int end=this.geteDayOfWeek();
			if(end<start) end+=7;
			return end-start+1;
		}
	}

	@Override
	public String toString() {
		return "Advancedschedule [advschID=" + advschID + ", parkingID=" + parkingID + ", sTimeOfDay=" + sTimeOfDay
				+ ", eTimeOfDay=" + eTimeOfDay + ", sDayOfWeek=" + sDayOfWeek + ", eDayOfWeek=" + eDayOfWeek
				+ ", sMonthOfYear=" + sMonthOfYear + ", eMonthOfYear=" + eMonthOfYear + "]";
	}
	


}
