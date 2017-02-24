package ontologies;


public interface WLVocabulary {

	 //-------> Basic vocabulary
	   public static final int NEW_SCHEDULE = 1;
	   public static final int PERCENTAGE = 2;
	   public static final int LIFT = 3;
	   public static final int LIFTED = 4;
	   public static final int TASKS = 5;
	   public static final int ADMIN = 6;
	   public static final int SQUAT = 7;
	 
	   public static final int WRONG_LIFT = 10;
	   public static final int SCHEDULE_NOT_FOUND = 11;
	   public static final int WRONG_TASK = 12;
	   public static final int Finished_Schedule=13;
	   public static final int NO_SQUAT=14;
	   public static final int NO_PERCENTAGE=15;
	   public static final int DONE_LIFT=16;

	   

	   
	   public static final String COACHE_AGENT = "Coache agent";
	   public static final String PB_SCHEDULE_NOT_FOUND = "Schedule not found";
	   public static final String PB_WRONG_LIFT = "Wrong lift values  you requested.";
	   public static final String PB_WRONG_TASK = "wrong task  you have requeted";
	   public static final String PB_Finished_Schedule = "Your Session is over. Check your Progress.";
	   public static final String PB_NO_SQUAT = "You cant not do Squats without complete Lifts.";
	   public static final String PB_NO_PERCENTAGE = "Check Your Percentage of lifts before click Lift.";
	   public static final String PB_DONE_LIFT = "You have alredy done this Lift. Go to next schedule or Squats.";

	   
	   //-------> Ontology vocabulary
	   public static final String SCHEDULE = "Schedule";
	   public static final String SCHEDULE_ID = "id";
	   public static final String SCHEDULE_TYPE = "type";
	   public static final String SCHEDULE_NAME = "name";
	   public static final String SCHEDULE_LIFTED = "lifted";
	   public static final String SCHEDULE_SNATCH = "snatch";
	   public static final String SCHEDULE_BACK_SQUAT = "backsquat";
	   public static final String SCHEDULE_FRONT_SQUAT = "frontsquat";
	   public static final String SCHEDULE_CLEAN_JERK = "clean_jerk";

	   public static final String CREATE_SCHEDULE = "Create_schedule";
	   public static final String CREATE_SCHEDULE_NAME = "name";


   public static final String SESSION = "Session";
   public static final String SESSION_TYPE = "type";

   public static final String SESSION_SNATCH = "snatch";
   public static final String SESSION_CLEAN_JERK = "clean_jerk";
   public static final String SESSION_LIFTED = "lifted";
   public static final String SESSION_SCHEDULEID = "scheduleId";
   public static final String SESSION_DATE = "date";

   public static final String ADD_SESSION = "MakeOperation";
   public static final String ADD_SESSION_TYPE = "type";
   public static final String ADD_SESSION_FRONT_SQUAT = "maxfrontsquat";
   public static final String ADD_SESSION_BACK_SQUAT = "maxbacksquat";
   public static final String ADD_SESSION_SNATCH = "snatch";
   public static final String ADD_SESSION_CLEAN_JERK = "clean_jerk";
   public static final String ADD_SESSION_SCHEDULEID = "scheduleId";

   public static final String INFORMATION = "Information";
   public static final String INFORMATION_TYPE = "type";
   public static final String INFORMATION_SCHEDULEID = "scheduleId";

   public static final String PROBLEM = "Problem";
   public static final String PROBLEM_NUM = "num";
   public static final String PROBLEM_MSG="msg";

}