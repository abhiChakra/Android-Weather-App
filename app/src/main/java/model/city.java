package model;

public class city {

    private String cityName;

    public city(String cityName) {
        this.cityName = cityName;

    }

    public city() {
        this.cityName = "";

    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

//    public String getCountryName() {
//        return countryName;
//    }
//
//    public void setCountryName(String countryName) {
//        this.countryName = countryName;
//    }


    //private String countryName;

}
