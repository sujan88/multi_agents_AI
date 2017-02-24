package agents;

/********************************************************************************
* BankClienAgent:
* ---------------
* This class is an example showing how to use the classes OneShotBehaviour,
* SimpleBehaviour, WakerBehaviour and ParallelBehaviour to program an agent.
*
* Version 3.0 - July 2003
* Author: Ambroise Ncho, under the supervision of Professeur Jean Vaucher
*
* Universite de Montreal - DIRO
*
*********************************************************************************/

import jade.content.AgentAction;
import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.ArrayList;
import jade.util.leap.List;

import ontologies.AddSession;
import ontologies.Session;
import ontologies.WLOntology;
import ontologies.WLVocabulary;
import ontologies.CreateSchedule;
import ontologies.Information;
import ontologies.AddSession;
import ontologies.Problem;
import ontologies.Schedule;


public class WLAthleteAgent extends GuiAgent implements WLVocabulary {
// ----------------------------------------------------------------------

   static final int WAIT = -1;
   static final int QUIT = 0;
   private int command = WAIT;
   private int cnt = 0;
   private AID coacher;
   private List accounts = new ArrayList();
   private Codec codec = new SLCodec();
   private Ontology ontology = WLOntology.getInstance();
   private Schedule schedule_tmp;

   transient protected WLAgentGui myGui;  // The gui

   protected void setup() {
// ------------------------

      // Register language and ontology
      getContentManager().registerLanguage(codec);
      getContentManager().registerOntology(ontology);

      // Set up the gui
      myGui = new WLAgentGui(this);
      myGui.setVisible(true);
   }

   protected void takeDown() {
// ---------------------------  Terminate the program properly

       System.out.println(getLocalName() + " is now shutting down.");
       if (myGui!=null) {
          myGui.setVisible(false);
          myGui.dispose();
       }
    }

   protected void onGuiEvent(GuiEvent ev) {
// ----------------------------------------  Receive user command via the gui

	   command = ev.getType();
	      if (command == QUIT) {
	         alertGui("Bye!");
	         doDelete();
	         System.exit(0);
	      }
	      if (command == NEW_SCHEDULE) {
	         createSchedule(myGui.getAgentName());
	         
	      }
	      else if (command == PERCENTAGE ) {
	         command = ev.getType();
	         Schedule acc = (Schedule)ev.getParameter(0);
	         float snatch = ((Float)ev.getParameter(1)).floatValue();
	         float clean_jerk = ((Float)ev.getParameter(2)).floatValue();
	         
	         
	       requestSession(acc, snatch,clean_jerk);
	       
	      }
	      else if (command == LIFT ) {
		         command = ev.getType();
		      
		        
		         float snatch = schedule_tmp.getSnatch();
		         float clean_jerk = schedule_tmp.getClean_jerk();
		         float backsqt= schedule_tmp.getBackSquat();
		         float frontsqt=	schedule_tmp.getFrontSquat();	 
		         
		       requestSession(schedule_tmp, snatch,clean_jerk);
		       
		      }
	      
	      else if (command == SQUAT ) {
		         command = ev.getType();
		      
		         Schedule acc = (Schedule)ev.getParameter(0);
		         
		         float backsqt=  ((Float)ev.getParameter(1)).floatValue();
		         float frontsqt= ((Float)ev.getParameter(2)).floatValue();	 
		         
		       requestSquatSession(acc, backsqt,frontsqt);
		       
		      }
	     
	      else if (command == LIFTED || command == TASKS) {
	         Schedule acc = (Schedule)ev.getParameter(0);
	         queryInformation(acc);
      }
   }

   void alertGui(Object response) {
// --------------------------------  Process the response of the Coacher
//                                   to the gui for display
      myGui.alertResponse(response); 
   }

   void resetStatusGui() {
// -----------------------  Reset the status of the gui
      myGui.resetStatus();
   }

   void createSchedule(String agentName) {
// ----------------------  Process to the coacher agent the request
//                         to create a new schedule

      CreateSchedule ca = new CreateSchedule();
      ca.setName(agentName+ "- Schedule " + cnt++);
      sendMessage(ACLMessage.REQUEST, ca);
   }


   void requestSession(Schedule acc, float max_snatch,float max_clean_jerk) {
// --------------------------------------------------  Process to the server agent the
//                                                     request to make an session
      AddSession mo = new AddSession();
      mo.setType(command);
      mo.setSnatch(max_snatch);
      mo.setClean_jerk(max_clean_jerk);
      mo.setScheduleId(acc.getId());
      sendMessage(ACLMessage.REQUEST, mo);
   }
   
   void requestSquatSession(Schedule acc, float max_back_squat,float max_front_squat) {
	// --------------------------------------------------  Process to the coach agent the
//	                                                     request to make an session
	      AddSession mo = new AddSession();
	     
	      mo.setType(command);
	      mo.setMaxBackSquat(max_back_squat);
	      mo.setMaxFrontSquat(max_front_squat);
	      mo.setScheduleId(acc.getId());
	     
	      sendMessage(ACLMessage.REQUEST, mo);
	   }


   void queryInformation(Schedule acc) {
// ------------------------------------   Process to the server agent the request
//                                        a query for information
      Information info = new Information();
      info.setType(command);
      info.setScheduleId(acc.getId());
      sendMessage(ACLMessage.QUERY_REF, info);
   }


   class WaitServerResponse extends ParallelBehaviour {
// ----------------------------------------------------  launch a SimpleBehaviour to receive
//                                                       servers response and a WakerBehaviour
//                                                       to terminate the waiting if there is
//                                                       no response from the coacher
      WaitServerResponse(Agent a) {

         super(a, 1);

         addSubBehaviour(new ReceiveResponse(myAgent));

         addSubBehaviour(new WakerBehaviour(myAgent, 5000) {

            protected void handleElapsedTimeout() {
               alertGui("No response from server. Please, try later!");
               resetStatusGui();
            }
         });
      }
   }


   class ReceiveResponse extends SimpleBehaviour {
// -----------------------------------------------  // Receive and handle coach responses

      private boolean finished = false;

      ReceiveResponse(Agent a) {
         super(a);
      }

      public void action() {

         ACLMessage msg = receive(MessageTemplate.MatchSender(coacher));

         if (msg == null) { block(); return; }

         if (msg.getPerformative() == ACLMessage.NOT_UNDERSTOOD){
            alertGui("Response from server: NOT UNDERSTOOD");
         }
         else if (msg.getPerformative() != ACLMessage.INFORM){
            alertGui("\nUnexpected msg from server!");
         }
         else {
            try {
               ContentElement content = getContentManager().extractContent(msg);

               if (content instanceof Result) {

                  Result result = (Result) content;

                  if (result.getValue() instanceof Problem) {

                     Problem prob = (Problem)result.getValue();
                     alertGui(prob);
                  }
                  else if (result.getValue() instanceof Schedule) {

                     Schedule acc = (Schedule) result.getValue();
                    schedule_tmp=acc;
                     if (command == NEW_SCHEDULE) {
                        accounts.add(acc);
                     }
                     alertGui(acc);
                  }
                  else if (result.getValue() instanceof List) {
                     alertGui(result.getItems());
                  }
                  else alertGui("\nUnexpected result from server!");
               }
               else {
                  alertGui("\nUnable de decode response from server!");
               }
            }
            catch (Exception e) { e.printStackTrace(); }
         }
         resetStatusGui();
         finished = true;
      }

      public boolean done() { return finished; }

      public int onEnd() { command = WAIT; return 0; }
   }


   void lookupServer() {
// ---------------------  Search in the DF to retrieve the server AID

      ServiceDescription sd = new ServiceDescription();
      sd.setType(COACHE_AGENT);
      DFAgentDescription dfd = new DFAgentDescription();
      dfd.addServices(sd);
      try {
         DFAgentDescription[] dfds = DFService.search(this, dfd);
         if (dfds.length > 0 ) {
            coacher = dfds[0].getName();
            alertGui("Localized server");
         }
         else alertGui("Unable to localize server. Please try later!");
      }
      catch (Exception ex) {
         ex.printStackTrace();
         System.out.println("Failed searching int the DF!");
      }
   }


//--------------------------- Utility methods ----------------------------//


   void sendMessage(int performative, AgentAction action) {
// --------------------------------------------------------
      if (coacher == null) lookupServer();
      if (coacher == null) {
         alertGui("Unable to localize the server! Operation aborted!");
         return;
      }
      ACLMessage msg = new ACLMessage(performative);
      msg.setLanguage(codec.getName());
      msg.setOntology(ontology.getName());
      try {
         getContentManager().fillContent(msg, new Action(coacher, action));
         msg.addReceiver(coacher);
         send(msg);
         alertGui("Contacting server... Please wait!");
         addBehaviour(new WaitServerResponse(this));
      }
      catch (Exception ex) { ex.printStackTrace(); }
   }

}
