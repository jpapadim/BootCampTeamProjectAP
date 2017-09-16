package airparking.model;

import java.util.ArrayList;
import java.util.Date;

import org.joda.time.Interval;

public class Schedule {

	private int id;
	private boolean isSimple;
	private Date startDateTime;
	private Date endDateTime;
	public Schedule(int id, boolean isSimple, Date startDateTime, Date endDateTime) {
		super();
		this.id = id;
		this.isSimple = isSimple;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}
	public int getId() {
		return id;
	}
	public boolean isSimple() {
		return isSimple;
	}
	public Date getStartDateTime() {
		return startDateTime;
	}
	public Date getEndDateTime() {
		return endDateTime;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setSimple(boolean isSimple) {
		this.isSimple = isSimple;
	}
	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}
	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}
	
	public boolean overlaps(Schedule schedule){
		Interval interv1 = new Interval(this.getStartDateTime().getTime(), this.getEndDateTime().getTime());
		Interval interv2 = new Interval(schedule.getStartDateTime().getTime(), schedule.getEndDateTime().getTime());
		if(interv1.overlaps(interv2)) return true;
		else return false;
	}
	@Override
	public String toString() {
		return "Schedule [id=" + id + ", isSimple=" + isSimple + ", startDateTime=" + startDateTime + ", endDateTime="
				+ endDateTime + "]";
	}
	
	public boolean isAvailable(ArrayList<Schedule> availability, ArrayList<Schedule> bookedPeriods) throws Exception{
		boolean ok=false;

		Date sStart=this.startDateTime;
		Date sEnd=this.endDateTime;
		for(int i=0; i<availability.size(); i++){
			Date aStart=availability.get(i).getStartDateTime();
			Date aEnd=availability.get(i).getEndDateTime();		
			
				for(int j=0; j<bookedPeriods.size(); j++){
					if(availability.get(i).getId()==bookedPeriods.get(j).getId() && this.id==bookedPeriods.get(j).getId()){
						
						Date bStart=bookedPeriods.get(j).getStartDateTime();
						Date bEnd=bookedPeriods.get(j).getEndDateTime();
						if(sStart.after(aStart)&&sEnd.before(aEnd)){
							if((sEnd.before(bStart)||sStart.after(bEnd))){
								ok= true;
							}
							else if((sEnd.before(bStart)&&sStart.after(aStart))||(sStart.after(bEnd)&&sEnd.before(aEnd))){
								ok= true;
							}
							else{
								ok= false;
							}
						}
						else{
							ok=false;
						}
					}
				
				}
			
			
		}
		
		return ok;
	}
	
	public boolean isAvailableWithoutBookingPeriod(ArrayList<Schedule> availability) throws Exception{
		boolean ok=false;

		Date sStart=this.startDateTime;
		Date sEnd=this.endDateTime;
		for(int i=0; i<availability.size(); i++){
			Date aStart=availability.get(i).getStartDateTime();
			Date aEnd=availability.get(i).getEndDateTime();		
			
				
					if(availability.get(i).getId()== this.id){
						
						if(sStart.after(aStart)&&sEnd.before(aEnd)){
							ok=true;
						}
						else{
							ok=false;
						}
					}
				
		}		
		return ok;
	}
	
}
