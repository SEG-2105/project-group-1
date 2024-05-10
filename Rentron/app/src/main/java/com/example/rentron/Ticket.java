package com.example.rentron;

public class Ticket {
    private String type;
    private String message;
    private int urgency;
    private Date date;
    private String status;

    public Ticket(String type,String message,int urgency,Date date){
        setType(type);
        setMessage(message);
        setUrgency(urgency);
    }
    private void setType(String type){
        boolean test=false;
        for (String element:new String[] {"Maintenance","Security","Damage","Infestation"}){
            if (element.equals(type)){
                test=true;
                break;
            }
        }
        if(test){
            this.type=type;
        }
        else{
            throw new IllegalArgumentException(type+" is not a type please ");
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public void setUrgency(int urgency){
        boolean test=false;
        for(int element:new int[]{1,2,3,4,5}){
            if(element==urgency){
                test=true;
                break;
            }
        }
        if(test){
            this.urgency=urgency;
        }
        else{
            throw new IllegalArgumentException("Urgency must be between 1 and 5 where 1 is low urgency and 5 is high urgency");
        }
    }
}
