package com.AmazonTestAPI.SimpleAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class APIController {

    private APIService apiService;

    @Autowired
    public void setApiService(APIService apiService){
        this.apiService = apiService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/general")
    public ResponseEntity getGeneralData(){
        try {
            return ResponseEntity.ok().body(apiService.getGeneralData());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @RequestMapping(method = RequestMethod.GET, path = "/nearest")
    public ResponseEntity getNearest(@RequestParam(name = "lat", required = true) double latitude, @RequestParam(name = "long", required = true) double longitude){
        try {
            return ResponseEntity.ok().body(apiService.gerNearestStation(latitude, longitude));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
