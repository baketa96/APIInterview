import com.maxmind.geoip.Location;
import sun.reflect.annotation.TypeAnnotation;

public class Main {
    public static void main(String[] args) {
        GetData gd = new GetData();
        WorkWithData workWithData = new WorkWithData(gd.getData());
        PostData pd = new PostData();
        pd.post(pd.createNoOfBikesAndStations(workWithData.getNoOfOperative(), workWithData.getNoOfBikes()));
      //  pd.post(pd.createNearest(workWithData.getNearest(48.8404, 2.31543)));
//        System.out.println(pd.createNearest(workWithData.getNearest(48.8404, 2.31543)));
//        System.out.println("------------------");
//        System.out.println(pd.createNoOfBikesAndStations(workWithData.getNoOfOperative(), workWithData.getNoOfBikes()));
    }
}
