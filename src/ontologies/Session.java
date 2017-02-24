package ontologies;

import jade.content.*;

public class Session implements WLVocabulary, Concept {
// --------------------------------------------------------

   private int type;
   private float snatch;
   private float clean_jerk;
   private float lifted;
   private String scheduleId;

   private java.util.Date date;

   public int getType() {
      return type;
   }

   public float getSnatch() {
     return snatch;
   }

   public float getClean_jerk() {
	     return clean_jerk;
	   }
   
   public float getLifted() {
     return lifted;
   }

   public String getScheduleId() {
      return scheduleId;
   }

   public java.util.Date getDate() {
     return date;
   }

   


   public void setType(int type) {
      this.type = type;
   }

   public void setClean_jerk(float clean_jerk) {
      this.clean_jerk = clean_jerk;
   }
   
   public void setSnatch(float snatch) {
	      this.snatch = snatch;
	   }

   

   public void setLifted(float lifted) {
      this.lifted = lifted;
   }

   public void setScheduleId(String scheduleId) {
      this.scheduleId = scheduleId;
   }

   public void setDate(java.util.Date date) {
      this.date = date;
   }

   public String getName() {
      if (type == PERCENTAGE) return "Percentages.";
      if (type == LIFT) return "Lifted.";
      return "Admin.";
   }

   public String toString() {
      return "\n\t" + date + "  " + getName() +
             "  " + snatch + "  "+ clean_jerk +"  " + lifted;
   }

}