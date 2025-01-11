package com.example.bargainfindermax.dto;

import lombok.Data;
import java.util.List;

@Data
public class BargainFinderMaxRequest {
    private OTA_AirLowFareSearchRQ OTA_AirLowFareSearchRQ;

    @Data
    public static class OTA_AirLowFareSearchRQ {
        private String version;
        private POS pos;
        private List<OriginDestinationInformation> originDestinationInformation;
        private TravelPreferences travelPreferences;
        private TravelerInfoSummary travelerInfoSummary;
        private TPA_Extensions tpaExtensions;
    }

    @Data
    public static class POS {
        private List<Source> source;
    }

    @Data
    public static class Source {
        private String pseudoCityCode;
        private RequestorID requestorID;
    }

    @Data
    public static class RequestorID {
        private String type;
        private String id;
        private CompanyName companyName;
    }

    @Data
    public static class CompanyName {
        private String code;
    }

    @Data
    public static class OriginDestinationInformation {
        private String rph;
        private String departureDateTime;
        private Location originLocation;
        private Location destinationLocation;
    }

    @Data
    public static class Location {
        private String locationCode;
    }

    @Data
    public static class TravelPreferences {
        private TPA_Extensions tpaExtensions;
    }

    @Data
    public static class TPA_Extensions {
        private NumTrips numTrips;
        private DataSources dataSources;
        private PreferNDCSourceOnTie preferNDCSourceOnTie;
        private IntelliSellTransaction intelliSellTransaction;
    }

    @Data
    public static class NumTrips {
        private int number;
    }

    @Data
    public static class DataSources {
        private String ndc;
        private String atpco;
        private String lcc;
    }

    @Data
    public static class PreferNDCSourceOnTie {
        private boolean value;
    }

    @Data
    public static class IntelliSellTransaction {
        private RequestType requestType;
    }

    @Data
    public static class RequestType {
        private String name;
    }

    @Data
    public static class TravelerInfoSummary {
        private List<AirTravelerAvail> airTravelerAvail;
    }

    @Data
    public static class AirTravelerAvail {
        private List<PassengerTypeQuantity> passengerTypeQuantity;
    }

    @Data
    public static class PassengerTypeQuantity {
        private String code;
        private int quantity;
    }
}
