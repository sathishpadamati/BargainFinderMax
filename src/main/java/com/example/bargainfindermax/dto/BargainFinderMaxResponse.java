package com.example.bargainfindermax.dto;

import lombok.Data;
import java.util.List;

@Data
public class BargainFinderMaxResponse {
    private GroupedItineraryResponse groupedItineraryResponse;

    @Data
    public static class GroupedItineraryResponse {
        private String version;
        private List<Message> messages;
        private Statistics statistics;
        private List<Itinerary> itineraries;
    }

    @Data
    public static class Message {
        private String severity;
        private String type;
        private String code;
        private String text;
    }

    @Data
    public static class Statistics {
        private int itineraryCount;
    }

    @Data
    public static class Itinerary {
        private int id;
        private String pricingSource;
        private List<Leg> legs;
        private List<PricingInformation> pricingInformation;
        private int itinGrp;
    }

    @Data
    public static class Leg {
        private int id;
        private List<Schedule> schedules;
    }

    @Data
    public static class Schedule {
        private int ref;
    }

    @Data
    public static class PricingInformation {
        private String pricingSubsource;
        private Offer offer;
        private Fare fare;
    }

    @Data
    public static class Offer {
        private String offerId;
        private int timeToLive;
        private String source;
    }

    @Data
    public static class Fare {
        private String validatingCarrierCode;
        private boolean eTicketable;
        private List<PassengerInfo> passengerInfoList;
        private TotalFare totalFare;
    }

    @Data
    public static class PassengerInfo {
        private String offerItemId;
        private boolean mandatoryInd;
        private String serviceId;
        private String passengerType;
        private int passengerNumber;
        private List<FareComponent> fareComponents;
        private PassengerTotalFare passengerTotalFare;
        private List<BaggageInformation> baggageInformation;
    }

    @Data
    public static class FareComponent {
        private int ref;
        private List<Segment> segments;
    }

    @Data
    public static class Segment {
        private String bookingCode;
        private String cabinCode;
    }

    @Data
    public static class PassengerTotalFare {
        private double totalFare;
        private double totalTaxAmount;
        private String currency;
        private double baseFareAmount;
        private String baseFareCurrency;
        private double equivalentAmount;
        private String equivalentCurrency;
    }

    @Data
    public static class BaggageInformation {
        private String provisionType;
        private String airlineCode;
        private List<SegmentReference> segments;
        private Allowance allowance;
    }

    @Data
    public static class SegmentReference {
        private int id;
    }

    @Data
    public static class Allowance {
        private int ref;
    }

    @Data
    public static class TotalFare {
        private double totalPrice;
        private double totalTaxAmount;
        private String currency;
        private double baseFareAmount;
        private String baseFareCurrency;
        private double equivalentAmount;
        private String equivalentCurrency;
    }
}
