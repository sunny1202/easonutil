package idv.eason.utility;

public class TestCompute {
    private String year,lastyear,month,lastmonth;
    
    public void computeYM(String CurrentYM){
        String[] strMonth = new String[] {"1","2","3","4","5","6","7","8","9","A","B","C"} ;
        String currentMonth = String.valueOf(Integer.parseInt(CurrentYM.substring(CurrentYM.length()-2)));
        String currentYear = CurrentYM.substring(CurrentYM.length()-3,CurrentYM.length()-2);
        this.year = currentYear ; //今年牌價
        //去年牌價
        if((Integer.parseInt(currentYear)-1) == -1)
            this.lastyear = "9";
        else
            this.lastyear = String.valueOf(Integer.parseInt(currentYear)-1);
        this.month = currentYear+strMonth[Integer.parseInt(currentMonth)-1] ; //當月牌價
        if((Integer.parseInt(currentMonth)-2) == -1){
            if((Integer.parseInt(currentYear)-1) == -1)
                this.lastmonth = "9C" ;
            else
                this.lastmonth = this.lastyear + strMonth[(Integer.parseInt(currentMonth)-2)<0?11:(Integer.parseInt(currentMonth)-2)];
        }else
            this.lastmonth = this.year + strMonth[(Integer.parseInt(currentMonth)-2)];
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        TestCompute tc = new TestCompute();
        tc.computeYM("9701");
        System.out.println(tc.year);
        System.out.println(tc.lastyear);
        System.out.println(tc.month);
        System.out.println(tc.lastmonth);

    }

}
