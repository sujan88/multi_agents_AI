package ontologies;

import jade.content.*;


public class Information implements AgentAction {
// ----------------------------------------------

   private int type;
   private String scheduleId;

   public int getType() {
      return type;
   }

   public String getScheduleId() {
      return scheduleId;
   }

   public void setType(int type) {
      this.type = type;
   }

   public void setScheduleId(String scheduleId) {
      this.scheduleId = scheduleId;
   }
}