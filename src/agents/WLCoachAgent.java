
package agents;
/********************************************************************************
* BankClienAgent:
* ---------------
* This class is an example showing how to use the classes SequentialBehaviour,
* OneShotBehaviour and CyclicBehaviour to build an agent.
*
* Version 3.0 - July 2003
* Author: Ambroise Ncho, under the supervision of Professeur Jean Vaucher.
*
* Universite de Montreal - DIRO
*
*********************************************************************************/

import jade.content.Concept;
import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.util.leap.ArrayList;
import jade.util.leap.HashMap;
import jade.util.leap.List;
import jade.util.leap.Map;

import ontologies.WLOntology;
import ontologies.WLVocabulary;
import ontologies.CreateSchedule;
import ontologies.Information;
import ontologies.AddSession;
import ontologies.Session;
import ontologies.Problem;
import ontologies.Schedule;
import java.util.*;


public class WLCoachAgent extends Agent implements WLVocabulary {
// -------------------------------------------------------------------

   private int idCnt = 0;
   private Map accounts = new HashMap();
   private Map operations = new HashMap();
   private Codec codec = new SLCodec();
   private Ontology ontology = WLOntology.getInstance();
   java.util.ArrayList<Schedule> schedulelist=new java.util.ArrayList<Schedule>();
   protected void setup() {
// ------------------------

      // Register language and ontology
      getContentManager().registerLanguage(codec);
      getContentManager().registerOntology(ontology);

      // Set this agent main behaviour
      SequentialBehaviour sb = new SequentialBehaviour();
      sb.addSubBehaviour(new RegisterInDF(this));
      sb.addSubBehaviour(new ReceiveMessages(this));
      addBehaviour(sb);
   }

   class RegisterInDF extends OneShotBehaviour {
// ---------------------------------------------  Register in the DF for the Athlete agent
//                                                be able to retrieve its AID
      RegisterInDF(Agent a) {
         super(a);
      }

      public void action() {

         ServiceDescription sd = new ServiceDescription();
         sd.setType(COACHE_AGENT);
         sd.setName(getName());
         sd.setOwnership("Prof6802");
         DFAgentDescription dfd = new DFAgentDescription();
         dfd.setName(getAID());
         dfd.addServices(sd);
         try {
            DFAgentDescription[] dfds = DFService.search(myAgent, dfd);
            if (dfds.length > 0 ) {
               DFService.deregister(myAgent, dfd);
            }
            DFService.register(myAgent, dfd);
            System.out.println(getLocalName() + " is ready.");
         }
         catch (Exception ex) {
            System.out.println("Failed registering with DF! Shutting down...");
            ex.printStackTrace();
            doDelete();
         }
      }
   }

   class ReceiveMessages extends CyclicBehaviour {
// -----------------------------------------------  Receive requests and queries from athlete
//                                                  agent and launch appropriate handlers

      public ReceiveMessages(Agent a) {

         super(a);
      }

      public void action() {

         ACLMessage msg = receive();
         if (msg == null) { block(); return; }
         try {
            ContentElement content = getContentManager().extractContent(msg);
            Concept action = ((Action)content).getAction();

            switch (msg.getPerformative()) {

               case (ACLMessage.REQUEST):

                  System.out.println("Request from " + msg.getSender().getLocalName());

                  if (action instanceof CreateSchedule)
                     addBehaviour(new HandleCreateAccount(myAgent, msg));
                  else if (action instanceof AddSession)
                     addBehaviour(new HandleOperation(myAgent, msg));
                  else
                     replyNotUnderstood(msg);
                  break;

               case (ACLMessage.QUERY_REF):

                  System.out.println("Query from " + msg.getSender().getLocalName());

                  if (action instanceof Information)
                     addBehaviour(new HandleInformation(myAgent, msg));
                  else
                     replyNotUnderstood(msg);
                  break;

               default:
                     replyNotUnderstood(msg);
            }
         }
         catch(Exception ex) { ex.printStackTrace(); }
      }
   }

   class HandleCreateAccount extends OneShotBehaviour {
// ----------------------------------------------------  Handler for a CreateAccount request

      private ACLMessage request;

      HandleCreateAccount(Agent a, ACLMessage request) {

         super(a);
         this.request = request;
      }

      public void action() {

         try {
            ContentElement content = getContentManager().extractContent(request);
            CreateSchedule ca = (CreateSchedule)((Action)content).getAction();
           Schedule sch=new Schedule();
            String id = generateId();
            sch.setId(id);
           
            sch.setName(ca.getName());
            Result result = new Result((Action)content, sch);
            ACLMessage reply = request.createReply();
            reply.setPerformative(ACLMessage.INFORM);
            getContentManager().fillContent(reply, result);
            send(reply);
            accounts.put(id, sch);
            operations.put(id, new ArrayList());
            System.out.println("Schedule  [" + sch.getName() +"     schID: "+
            		sch.getId() + "] created!");
         }
         catch(Exception ex) { ex.printStackTrace(); }
      }
   }

   class HandleOperation extends OneShotBehaviour {
// ------------------------------------------------  Handler for an Operation request

      private ACLMessage request;

      HandleOperation(Agent a, ACLMessage request) {

         super(a);
         this.request = request;
      }

      public void action() {

         try {
            ContentElement content = getContentManager().extractContent(request);
            AddSession mo = (AddSession)((Action)content).getAction();
            Object obj = processOperation(mo);
            if (obj == null) replyNotUnderstood(request);
            else {
               ACLMessage reply = request.createReply();
               reply.setPerformative(ACLMessage.INFORM);            
               Result result = new Result((Action)content, obj);
               getContentManager().fillContent(reply, result);
               send(reply);
               System.out.println("Operation processed.");
            }
         }
         catch(Exception ex) { ex.printStackTrace(); }
      }
   }

   class HandleInformation extends OneShotBehaviour {
// --------------------------------------------------  Handler for an Information query

      private ACLMessage query;

      HandleInformation(Agent a, ACLMessage query) {

         super(a);
         this.query = query;
         
      }

      public void action() {

         try {
            ContentElement content = getContentManager().extractContent(query);
            Information info = (Information)((Action)content).getAction();
            Object obj = processInformation(info);
            
           
            if (obj == null) replyNotUnderstood(query);
            else {
            	
               ACLMessage reply = query.createReply();
               reply.setPerformative(ACLMessage.INFORM);
               Result result = new Result((Action)content, obj);
               getContentManager().fillContent(reply, result);
               send(reply);
               System.out.println("Information processed.");
            }
         }
         catch(Exception ex) { ex.printStackTrace(); }
      }
   }

   void replyNotUnderstood(ACLMessage msg) {
// -----------------------------------------

      try {
         ContentElement content = getContentManager().extractContent(msg);
         ACLMessage reply = msg.createReply();
         reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
         getContentManager().fillContent(reply, content);
         send(reply);
         System.out.println("Not understood!");
      }
      catch(Exception ex) { ex.printStackTrace(); }
   }

   Object processOperation(AddSession mo) {
// -------------------------------------------

      Schedule acc = (Schedule)accounts.get(mo.getScheduleId());
     
      System.out.println(mo.getScheduleId()+" shc id");
      
    
      
     
      if (acc == null) return newProblem(SCHEDULE_NOT_FOUND);
      
     
      if (mo.getType() != PERCENTAGE && mo.getType() != LIFT&& mo.getType() != SQUAT)
         return null;
      
      
      if (mo.getType() == PERCENTAGE){
    	  
     /*****************************    scheduling session by coach     *********************************/
    	  
      float per=0;
      switch(acc.getId()){
    	  case "0": schedulelist.add(acc); per=70;
    	  break;
    	  case "1": schedulelist.add(acc); per=80;
    	  break;
    	  case "2": schedulelist.add(acc); per=85;
    	  break;
    	  case "3": schedulelist.add(acc); per=90;
    	  break;
    	  case "4":  schedulelist.add(acc); per=100;
    	  break;
    	  case "5":  schedulelist.add(acc); per=105;
    	  break;
    	  default :  return newProblem(Finished_Schedule); 
    	 
    	  
    	  
      }
    
      if ( mo.getSnatch() > mo.getClean_jerk())
          return newProblem(WRONG_LIFT);
     
    	  
      float snatch = mo.getSnatch()*per/100;
      float clean_jerk = mo.getClean_jerk()*per/100;
     
      acc.setSnatch(snatch);
      acc.setClean_jerk(clean_jerk);
      acc.setType(PERCENTAGE);
     /**************************************************************************************************/
      
     
   }
      else if (mo.getType() == LIFT) {
    	 if( acc.getType()!=PERCENTAGE) return newProblem(NO_PERCENTAGE);
    	 if( acc.getType()==LIFT) return newProblem(DONE_LIFT);
    	 
    	  /** Adding each lifted weight into lifted attribute in schedule class**/
    	  acc.setLifted( acc.getLifted()+mo.getSnatch() + mo.getClean_jerk());
       
    	  acc.setType(LIFT);
      }
      else if(mo.getType() == SQUAT&&!schedulelist.isEmpty()){
    	 
    	//with out lift of snatch or clea and jerk athlete can not do squats.
    	  if(acc.getType()!=LIFT) 
    		  return newProblem(NO_SQUAT); 	  
    	  
    	  
    	
    	  float backsquat=0.0f,frontsquat=0.0f;
    	 
    	  switch(acc.getId()){
    	 
    	  case "0":  backsquat= schedulelist.get(0).getSnatch()>100? mo.getMaxBackSquat()*80/100 :  mo.getMaxBackSquat()*70/100 ; 
    	 
    	  break;
    	 
    	  case "1":  frontsquat= schedulelist.get(0).getClean_jerk()>150? mo.getMaxFrontSquat()*80/100 :  mo.getMaxFrontSquat()*70/100 ; 
    	  
    	  break;
    	  case "2":  backsquat= schedulelist.get(0).getSnatch()>110? mo.getMaxBackSquat()*90/100 :  mo.getMaxBackSquat()*85/100 ; 
     	 
    	  break;
    	  case "3": frontsquat= schedulelist.get(0).getClean_jerk()>160? mo.getMaxFrontSquat()*95/100 :  mo.getMaxFrontSquat()*85/100 ; 
    	  
    	  break;
    	  case "4":  backsquat= schedulelist.get(0).getSnatch()>120? mo.getMaxBackSquat()*95/100 :  mo.getMaxBackSquat()*90/100 ; 
      	 
    	  break;
    	  case "5":  frontsquat= schedulelist.get(0).getClean_jerk()>170? mo.getMaxFrontSquat()*100/100 :  mo.getMaxFrontSquat()*95/100 ; 
    	  
    	  break;
    	  default :  return newProblem(Finished_Schedule); 
    	 
    	  
    	  
      }
    	  acc.setBackSquat(backsquat) ;
    	  acc.setFrontSquat(frontsquat);
    	  acc.setType(SQUAT);
    	  
      }
      java.util.Date date = new java.util.Date();
      Session op = new Session();            // <-- register operation
      op.setType(mo.getType());
      op.setSnatch(mo.getSnatch());
      op.setClean_jerk(mo.getClean_jerk());
      op.setLifted(acc.getLifted());
      op.setScheduleId(acc.getId());
      op.setDate(date);
      List l = (List)operations.get(acc.getId());
      l.add(op);
      operations.put(acc.getId(), l);
      return acc;
   }

   Object processInformation(Information info) {
// -------------------------------------------

      Schedule acc = (Schedule)accounts.get(info.getScheduleId());
      if (acc == null) return newProblem(SCHEDULE_NOT_FOUND);

      java.util.Date date = new java.util.Date();
      Session op = new Session();              // <-- Apply admin charge
      op.setType(ADMIN);
     
      op.setLifted(acc.getLifted());
      op.setScheduleId(acc.getId());
      op.setDate(date);
      List l = (List)operations.get(acc.getId());
      l.add(op);
      operations.put(acc.getId(), l);

      if (info.getType() == LIFTED) return acc;
      if (info.getType() == TASKS) return l;
      return null;
   }

//--------------------------- Utility methods ----------------------------//

   Problem newProblem(int num) {
// -----------------------------

      String msg = "";

      if (num == SCHEDULE_NOT_FOUND)
          msg = PB_SCHEDULE_NOT_FOUND;

       else if (num == WRONG_LIFT)
          msg = PB_WRONG_LIFT;

       else if (num == WRONG_TASK)
          msg = PB_WRONG_TASK;
       else if (num == Finished_Schedule)
           msg = PB_Finished_Schedule;
       else if (num == NO_SQUAT)
           msg = PB_NO_SQUAT;
       else if (num == NO_PERCENTAGE)
           msg = PB_NO_PERCENTAGE;
       else if (num == DONE_LIFT)
           msg = PB_DONE_LIFT;

      Problem prob = new Problem();
      prob.setNum(num);
      prob.setMsg(msg);
      return prob;
   }

   String generateId() {
// ---------------------

      return ""+idCnt++;
   }

}

