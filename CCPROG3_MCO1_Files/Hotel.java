public class Hotel{ 

    String hotelName;
    
    public Hotel(String hotelName){ //constructor
  
      this.hotelName = hotelName;
      
    }
  
    public void setHotelName(String newHotelName){
      this.hotelName = newHotelName;
    }
  
    public String getHotelName(){
      return this.hotelName;
    }
    
  }