package bloodbank.ieee.com.bloodbank.SearchFiles;

/**
 * Created by rashad on 6/10/16.
 */

public class DataObject {

    String mText1, mText2, mText3, mText4, mText5;

    public DataObject(String text1, String text2, String text3, String text4, String text5) {

        this.mText1 = text1;
        this.mText2 = text2;
        this.mText3 = text3;
        this.mText4 = text4;
        this.mText5 = text5;

        System.out.println(this.mText1 + "  " + this.mText2 + "  " + this.mText3 + "  " + this.mText4 + "  " + this.mText5);

    }

    ////////////////////////getters//////////////////////////

    public String getmText1() {
        return mText1;
    }

    public String getmText2() {
        return mText2;
    }

    public String getmText3() {
        return mText3;
    }

    public String getmText4() {
        return mText4;
    }

    public String getmText5() {
        return mText5;
    }

    /////////////////////////setters/////////////////////////


    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }

    public void setmText2(String mText2) {
        this.mText2 = mText2;
    }

    public void setmText3(String mText3) {
        this.mText3 = mText3;
    }

    public void setmText4(String mText4) {
        this.mText4 = mText4;
    }

    public void setmText5(String mText5) {
        this.mText4 = mText5;
    }

}

