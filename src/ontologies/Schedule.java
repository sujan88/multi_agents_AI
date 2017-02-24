package ontologies;
import jade.content.*;

public class Schedule implements Concept {
// --------------------------------------

   private String id;
   private String name;
   private int type;
   private float backsquat;
   private float frontsquat;
   private float lifted = 0;
   private float snatch;
   private float clean_jerk;

   public String getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public float getLifted() {
      return lifted;
   }

   public float getSnatch() {
	     return snatch;
	   }
   public float getClean_jerk() {
	     return clean_jerk;
	   }
   
  
   public float getBackSquat() {
	      return backsquat;
	   }

   public int getType() {
	      return type;
	   }

public void setType(int type) {
	      this.type=type;
	   }
 public void setBackSquat(float backsquat) {
	      this.backsquat=backsquat;
	   }

 public float getFrontSquat() {
     return frontsquat;
  }

public void setFrontSquat(float frontsquat) {
     this.frontsquat=frontsquat;
  }
 
   public void setSnatch(float snatch) {
	      this.snatch = snatch;
	   }
   
   public void setClean_jerk(float clean_jerk) {
	      this.clean_jerk = clean_jerk;
	   }
   
   public void setId(String id) {
      this.id = id;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setLifted(float lifted) {
      this.lifted = lifted;
   }

   public boolean equals(Schedule acc) {
      return acc.getId().equals(this.id);
   }

   public String toString() {
      return name + "  # " + id + "  --> balance lifted weight= " + lifted;
   }
}