package ontologies;

import jade.content.*;


public class AddSession implements AgentAction {
// ------------------------------------------------

   private int type;
   private String accountId;
   private float maxbacksquat;
   private float maxfrontsquat;
   private float snatch;
   private float clean_jerk;
  

   public int getType() {
      return type;
   }

   public float getSnatch() {
     return snatch;
   }

   public float getClean_jerk() {
	     return clean_jerk;
	   }
   public String getScheduleId() {
      return accountId;
   }
   
   
   public float getMaxBackSquat() {
	      return maxbacksquat;
	   }
   public float getMaxFrontSquat() {
	      return maxfrontsquat;
	   }
   
   public void setMaxBackSquat(float maxbacksquat) {
	      this.maxbacksquat=maxbacksquat;
	   }
   public void setMaxFrontSquat(float maxfrontsquat) {
	      this.maxfrontsquat=maxfrontsquat;
	   }
   
   public void setType(int type) {
      this.type = type;
   }

   public void setSnatch(float snatch) {
      this.snatch = snatch;
     
   }
   public void setClean_jerk(float clean_jerk) {
	      this.clean_jerk = clean_jerk;
	     
	   }

  
   
   public void setScheduleId(String accountId) {
      this.accountId = accountId;
   }

}