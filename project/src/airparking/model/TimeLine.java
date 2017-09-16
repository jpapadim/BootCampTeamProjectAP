package airparking.model;

import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Interval;

public class TimeLine {
	private ArrayList<Date> timeline;

	public ArrayList<Date> getTimeline() {
		return timeline;
	}

	public double availability(Schedule requestPeriod){
		Interval interv1 = new Interval(requestPeriod.getStartDateTime().getTime(), requestPeriod.getEndDateTime().getTime());
		if(timeline!=null){
			if(timeline.isEmpty()) return 0.0;
			else if(timeline.size()==2){
				Interval interv2 = new Interval(timeline.get(0).getTime(), timeline.get(1).getTime());
				if(interv2.isEqual(interv1)) return 100.0;
				else if(interv2.overlaps(interv1)){
					Interval interv3=interv2.overlap(interv1);
					double p=interv3.toDuration().getStandardSeconds()*100.0/(interv1.toDuration().getStandardSeconds());
					return p;
				}
				else return 0.0;
			}
			else{
				int seconds=0;
				for(int i=0;i<timeline.size();i+=2){
					Interval interv2 = new Interval(timeline.get(i).getTime(), timeline.get(i+1).getTime());
					seconds+=interv2.toDuration().getStandardSeconds();
				}
				return seconds*100.0/(interv1.toDuration().getStandardSeconds());
			}
		}
		else return 0.0;
		
	}
	
	public void calculate(Schedule requestPeriod, Schedule availability, ArrayList<Schedule> bookings,
			ArrayList<Advancedschedule> advancedscheduleList) {
		this.timeline = timeLine(requestPeriod, availability, bookings, advancedscheduleList);
	}

	public void print() {
		int size = this.timeline.size();
		for (int i = 0; i < size; i+=2) {
			System.out.println(this.timeline.get(i) + "  ---->   " + this.timeline.get(i + 1));
		}
	}

	private ArrayList<Date> timeLineMonth(Schedule requestPeriod, Advancedschedule advancedschedule) {
		ArrayList<Date> list = new ArrayList<>();
		int startMonth = advancedschedule.getsMonthOfYear();
		int endMonth = advancedschedule.geteMonthOfYear();
		//System.out.println("startMonth = " + startMonth + ",  endMonth = " + endMonth);
		Interval interv1 = new Interval(requestPeriod.getStartDateTime().getTime(),
				requestPeriod.getEndDateTime().getTime());
		DateTime start = interv1.getStart();
		DateTime end = interv1.getEnd();
		DateTime current = start.toDateTime();
		if (!advancedschedule.hasMonthPeriot()) {
			list.add(start.toDate());
			list.add(end.toDate());
			return list;
		}
		while (current.isBefore(end)) {
			int currentMonth = current.getMonthOfYear();
			start = current.toDateTime();
			if (advancedschedule.hasMonth(currentMonth)) {
				list.add(current.toDate());

				current = current.withMonthOfYear((endMonth == 12) ? 1 : (endMonth + 1));
				current = current.withDayOfMonth(1);
				current = current.withMillisOfDay(0);
				current = current.minusMinutes(1);
				if (current.isBefore(start))
					current = current.plusYears(1);
				if (current.isBefore(end) || current.isEqual(end)) {
					list.add(current.toDate());
					current = current.plusMinutes(1);
				} else {
					list.add(end.toDate());
				}
			} else {
				current = current.withMonthOfYear(startMonth);
				current = current.withDayOfMonth(1);
				current = current.withMillisOfDay(0);
				if (current.isBefore(start))
					current = current.plusYears(1);
			}
		}
		return list;

	}

	private ArrayList<Date> timeLineDay(Schedule requestPeriod, Advancedschedule advancedschedule) {
		ArrayList<Date> list = new ArrayList<>();
		int startDay = advancedschedule.getsDayOfWeek();
		int endDay = advancedschedule.geteDayOfWeek();
		//System.out.println("startMonth = " + startDay + ",  endMonth = " + endDay);
		Interval interv1 = new Interval(requestPeriod.getStartDateTime().getTime(),
				requestPeriod.getEndDateTime().getTime());
		DateTime start = interv1.getStart();
		DateTime end = interv1.getEnd();
		DateTime current = start.toDateTime();
		if (!advancedschedule.hasDayPeriot()) {
			list.add(start.toDate());
			list.add(end.toDate());
			return list;
		}
		while (current.isBefore(end)) {
			int currentDay = current.getDayOfWeek();
			start = current.toDateTime();
			if (advancedschedule.hasDay(currentDay)) {
				list.add(current.toDate());

				current = current.withDayOfWeek((endDay == 7) ? 1 : (endDay + 1));
				current = current.withMillisOfDay(0);
				current = current.minusMinutes(1);
				if (current.isBefore(start))
					current = current.plusWeeks(1);
				if (current.isBefore(end) || current.isEqual(end)) {
					list.add(current.toDate());
					current = current.plusMinutes(1);
				} else {
					list.add(end.toDate());
				}
			} else {
				current = current.withDayOfWeek(startDay);
				current = current.withMillisOfDay(0);
				if (current.isBefore(start))
					current = current.plusWeeks(1);
			}
		}
		return list;

	}

	private ArrayList<Date> timeLine(Schedule requestPeriod, Advancedschedule advancedschedule) {
		ArrayList<Date> monthList = this.timeLineMonth(requestPeriod, advancedschedule);
		//for (Date d : monthList)
			//System.out.println(d);
		//System.out.println("*************************");

		ArrayList<Date> monthDayList = new ArrayList<>();
		ArrayList<Date> list = new ArrayList<>();
		if (monthList.isEmpty())
			return monthList;
		else {
			int size = monthList.size();
			for (int i = 0; i < size; i += 2) {
				Schedule periot = new Schedule(0, true, monthList.get(i), monthList.get(i + 1));
				ArrayList<Date> dayList = this.timeLineDay(periot, advancedschedule);
				if (!dayList.isEmpty()) {
					for (Date d : dayList)
						monthDayList.add(d);
				}
			}
			if (monthDayList.isEmpty())
				return monthDayList;
			else {
				size = monthDayList.size();
				for (int i = 0; i < size; i += 2) {
					Schedule periot = new Schedule(0, true, monthDayList.get(i), monthDayList.get(i + 1));
					ArrayList<Date> timeList = this.timeLineTime(periot, advancedschedule);
					if (!timeList.isEmpty()) {
						for (Date d : timeList)
							list.add(d);
					}
				}
				return list;
			}
		}
	}

	private Schedule overlap(Schedule requestPeriod, Schedule availability) {
		Interval interv1 = new Interval(requestPeriod.getStartDateTime().getTime(),
				requestPeriod.getEndDateTime().getTime());
		Interval interv2 = new Interval(availability.getStartDateTime().getTime(),
				availability.getEndDateTime().getTime());
		Interval overl = interv1.overlap(interv2);
		if (overl == null)
			return null;
		else
			return new Schedule(0, false, overl.getStart().toDate(), overl.getEnd().toDate());
	}

	private ArrayList<Date> timeLineTime(Schedule requestPeriod, Advancedschedule advancedschedule) {
		ArrayList<Date> list = new ArrayList<>();
		int startMillis = advancedschedule.getsMillisOfDay();
		int endMillis = advancedschedule.geteMillisOfDay();
		//System.out.println("startMonth = " + startMillis + ",  endMonth = " + endMillis);
		Interval interv1 = new Interval(requestPeriod.getStartDateTime().getTime(),
				requestPeriod.getEndDateTime().getTime());
		DateTime start = interv1.getStart();
		DateTime end = interv1.getEnd();
		DateTime current = start.toDateTime();
		if (!advancedschedule.hasTimePeriot()) {
			list.add(start.toDate());
			list.add(end.toDate());
			return list;
		}
		while (current.isBefore(end)) {
			int currentMillis = current.getMillisOfDay();
			start = current.toDateTime();
			if (advancedschedule.hasMillis(currentMillis)) {
				list.add(current.toDate());

				current = current.withMillisOfDay(endMillis);
				if (current.isBefore(start))
					current = current.plusDays(1);
				if (current.isBefore(end) || current.isEqual(end)) {
					list.add(current.toDate());
					current = current.plusMinutes(1);
				} else {
					list.add(end.toDate());
				}
			} else {
				current = current.withMillisOfDay(startMillis);
				if (current.isBefore(start))
					current = current.plusDays(1);
			}
		}
		return list;

	}

	private ArrayList<Date> add(ArrayList<Date> timeLine1, ArrayList<Date> timeLine2) {
		ArrayList<Date> finallist = new ArrayList<>();
		if (timeLine1.isEmpty())
			return timeLine2;
		if (timeLine2.isEmpty())
			return timeLine1;
		boolean exitFlag = false;
		int index1 = 0;
		int index2 = 0;
		int size1 = timeLine1.size();
		int size2 = timeLine2.size();

		while (!exitFlag) {
			Interval interv1 = new Interval(timeLine1.get(index1).getTime(), timeLine1.get(index1 + 1).getTime());
			Interval interv2 = new Interval(timeLine2.get(index2).getTime(), timeLine2.get(index2 + 1).getTime());
			if (interv1.overlaps(interv2) || interv1.abuts(interv2)) {
				finallist.add((interv1.getStart().isBefore(interv2.getStart())) ? (interv1.getStart().toDate())
						: (interv2.getStart().toDate()));
				finallist.add((interv1.getEnd().isAfter(interv2.getEnd())) ? (interv1.getEnd().toDate())
						: (interv2.getEnd().toDate()));
				index1 += 2;
				index2 += 2;
				if (index1 >= size1 || index2 >= size2)
					exitFlag = true;
			} else if (interv1.isBefore(interv2)) {
				finallist.add(interv1.getStart().toDate());
				finallist.add(interv1.getEnd().toDate());
				index1 += 2;
				if (index1 >= size1)
					exitFlag = true;
			} else {
				finallist.add(interv2.getStart().toDate());
				finallist.add(interv2.getEnd().toDate());
				index2 += 2;
				if (index2 >= size2)
					exitFlag = true;
			}
		}

		if (index1 >= size1 && index2 >= size2)
			return finallist;
		else if (index2 < size2) {
			for (int i = index2; i < size2; i++)
				finallist.add(timeLine2.get(i));
		} else {
			for (int i = index1; i < size1; i++)
				finallist.add(timeLine1.get(i));
		}
		return finallist;
	}

	private ArrayList<Date> remove(ArrayList<Date> timeLine1, Schedule booking) {
		ArrayList<Date> finallist = new ArrayList<>();
		if (timeLine1 == null || timeLine1.isEmpty())
			return finallist;
		else {
			Interval interv2 = new Interval(booking.getStartDateTime().getTime(), booking.getEndDateTime().getTime());
			ArrayList<Interval> intervList = new ArrayList<>();
			int size = timeLine1.size();
			for (int i = 0; i < size; i += 2) {
				intervList.add(new Interval(timeLine1.get(i).getTime(), timeLine1.get(i + 1).getTime()));
			}
			int index = 0;
			size = intervList.size();
			while (index < size && intervList.get(index).isBefore(interv2.getStart().minusMinutes(1))) {
				finallist.add(intervList.get(index).getStart().toDate());
				finallist.add(intervList.get(index).getEnd().toDate());
				index++;
			}
			if (index >= size)
				return finallist;
			while (index < size && (intervList.get(index).overlaps(interv2) || intervList.get(index).abuts(interv2))) {
				if (intervList.get(index).getStart().isBefore(interv2.getStart())) {
					finallist.add(intervList.get(index).getStart().toDate());
					finallist.add(interv2.getStart().minusMinutes(1).toDate());
				}
				if (intervList.get(index).getEnd().isAfter(interv2.getEnd())) {
					finallist.add(interv2.getEnd().plusMinutes(1).toDate());
					finallist.add(intervList.get(index).getEnd().toDate());
				}
				index++;
			}
			if (index >= size)
				return finallist;
			else {
				for (int i = index; i < size; i++) {
					finallist.add(intervList.get(index).getStart().toDate());
					finallist.add(intervList.get(index).getEnd().toDate());
				}
			}
			return finallist;
		}
	}

	private ArrayList<Date> remove(ArrayList<Date> timeLine1, ArrayList<Date> timeLine2) {
		if (timeLine1.isEmpty() || timeLine2.isEmpty())
			return timeLine1;
		int size2 = timeLine2.size();
		for (int i = 0; i < size2; i += 2) {
			Schedule schedule = new Schedule(0, true, timeLine2.get(i), timeLine2.get(i + 1));
			timeLine1 = remove(timeLine1, schedule);
		}
		return timeLine1;
	}

	private ArrayList<Date> timeLine(Schedule requestPeriod, Schedule availability, ArrayList<Schedule> bookings,
			ArrayList<Advancedschedule> advancedscheduleList) {
		ArrayList<Date> list = new ArrayList<>();
		Schedule requestPeriod1 = this.overlap(requestPeriod, availability);

		if (requestPeriod1 == null)
			return list;
		//System.out.println("overlap==========requestPeriod1");
		//System.out.println(requestPeriod1.getStartDateTime());
		//System.out.println(requestPeriod1.getEndDateTime());

		list.add(requestPeriod1.getStartDateTime());
		list.add(requestPeriod1.getEndDateTime());
		if (bookings != null && !bookings.isEmpty()) {

			for (Schedule b : bookings) {
				if (b != null) {
					ArrayList<Date> bookPeriod = new ArrayList<>();
					bookPeriod.add(b.getStartDateTime());
					bookPeriod.add(b.getEndDateTime());
					list = this.remove(list, bookPeriod);
				}
			}
			//System.out.println("remove booking periods==========list");
			//for (Date d : list)
				//System.out.println(d);
		}
		if (list.isEmpty() || advancedscheduleList == null || advancedscheduleList.isEmpty())
			return list;
		int size = list.size();
		ArrayList<Date> finallist = new ArrayList<>();
		for (int i = 0; i < size; i += 2) {
			Schedule schedule = new Schedule(0, false, list.get(i), list.get(i + 1));
			ArrayList<Date> timeline = new ArrayList<>();
			;
			for (Advancedschedule as : advancedscheduleList) {
				timeline = add(timeline, timeLine(schedule, as));
			}
			for (Date d : timeline) {
				finallist.add(d);
			}

		}
		return finallist;
	}

}
