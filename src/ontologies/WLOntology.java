package ontologies;

/********************************************************************************
* BankOntology:
* -------------
* This class is an example showing how to use JADE content language support  ,
* to define an application ontology.
*
* Version 1.0 - July 2003
* Author: Ambroise Ncho, under the supervision of Professeur Jean Vaucher.
*
* Universite de Montreal - DIRO
*
*********************************************************************************/



import jade.content.onto.*;
import jade.content.schema.*;


public class WLOntology extends Ontology implements WLVocabulary {

   // ----------> The name identifying this ontology
   public static final String ONTOLOGY_NAME = "Weightlifting-Ontology";

   // ----------> The singleton instance of this ontology
   private static Ontology instance = new WLOntology();

   // ----------> Method to access the singleton ontology object
   public static Ontology getInstance() { return instance; }


   // Private constructor
   private WLOntology() {

      super(ONTOLOGY_NAME, BasicOntology.getInstance());

      try {

         // ------- Add Concepts

         // Schedule
         ConceptSchema cs = new ConceptSchema(SCHEDULE);
         add(cs, Schedule.class);
         cs.add(SCHEDULE_ID, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.MANDATORY);
         cs.add(SCHEDULE_TYPE, (PrimitiveSchema) getSchema(BasicOntology.INTEGER), ObjectSchema.MANDATORY);
         cs.add(SCHEDULE_NAME, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.MANDATORY);
         cs.add(SCHEDULE_LIFTED, (PrimitiveSchema) getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);
         cs.add(SCHEDULE_SNATCH, (PrimitiveSchema) getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);
         cs.add(SCHEDULE_CLEAN_JERK, (PrimitiveSchema) getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);
         cs.add(SCHEDULE_BACK_SQUAT, (PrimitiveSchema) getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);
         cs.add(SCHEDULE_FRONT_SQUAT, (PrimitiveSchema) getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);
         
         // Problem
         add(cs = new ConceptSchema(PROBLEM), Problem.class);
         cs.add(PROBLEM_NUM, (PrimitiveSchema) getSchema(BasicOntology.INTEGER), ObjectSchema.MANDATORY);
         cs.add(PROBLEM_MSG, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.MANDATORY);

         // Session
         add(cs = new ConceptSchema(SESSION), Session.class);
         cs.add(SESSION_TYPE, (PrimitiveSchema) getSchema(BasicOntology.INTEGER), ObjectSchema.MANDATORY);
         cs.add(SESSION_SNATCH, (PrimitiveSchema) getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);
         cs.add(SESSION_CLEAN_JERK, (PrimitiveSchema) getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);
         cs.add(SESSION_LIFTED, (PrimitiveSchema) getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);
         cs.add(SESSION_SCHEDULEID, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.MANDATORY);
        
         cs.add(SESSION_DATE, (PrimitiveSchema) getSchema(BasicOntology.DATE), ObjectSchema.MANDATORY);

         // ------- Add AgentActions

         // CreateSchedule
         AgentActionSchema as = new AgentActionSchema(CREATE_SCHEDULE);
         add(as, CreateSchedule.class);
         as.add(CREATE_SCHEDULE_NAME, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.MANDATORY);

         // Addsession
         add(as = new AgentActionSchema(ADD_SESSION), AddSession.class);
         as.add(ADD_SESSION_TYPE, (PrimitiveSchema) getSchema(BasicOntology.INTEGER), ObjectSchema.MANDATORY);
         as.add(ADD_SESSION_CLEAN_JERK, (PrimitiveSchema) getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);
         as.add(ADD_SESSION_SNATCH, (PrimitiveSchema) getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);
         as.add(ADD_SESSION_SCHEDULEID, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.MANDATORY);
         as.add(ADD_SESSION_BACK_SQUAT, (PrimitiveSchema) getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);
         as.add(ADD_SESSION_FRONT_SQUAT, (PrimitiveSchema) getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);

         // Information
         add(as = new AgentActionSchema(INFORMATION), Information.class);
         as.add(INFORMATION_TYPE, (PrimitiveSchema) getSchema(BasicOntology.INTEGER), ObjectSchema.MANDATORY);
         as.add(INFORMATION_SCHEDULEID, (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.MANDATORY);
      }
      catch (OntologyException oe) {
         oe.printStackTrace();
      }
   }
}// WeightlifitngOntology