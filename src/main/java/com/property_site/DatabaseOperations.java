package com.property_site;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DatabaseOperations {
    private static final String INSERT_QUERY = "INSERT INTO public.properties_data (ibapi_property_id, bank_name, branch_name, state, district, emd_last_date, city, borrower_name, owner_name, ownership_type, summary_description, property_type, property_sub_type, title_deed_type, possession_status, auction_open_date, auction_close_date, sealed_bid_last_date, sealed_bid_extended_date, address, nearest_airport_railway_bus_stand, authorised_officer_detail, property_visited) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

//    public void insertProperties(List<Properties> propertiesList) {
//        try (Connection connection = DatabaseConnection.getConnection()) {
//            for (Properties property : propertiesList) {
//                try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
//                    preparedStatement.setString(1, property.getPropertyId());
//                    preparedStatement.setString(2, property.getBankName());
//                    preparedStatement.setString(3, property.getBranchName());
//                    preparedStatement.setString(4, property.getState());
//                    preparedStatement.setString(5, property.getDistrict());
//                    preparedStatement.setString(6, property.geteMDLastDate());
//                    preparedStatement.setString(7, property.getCity());
//                    preparedStatement.setString(8, property.getBorrowerName());
//                    preparedStatement.setString(9, property.getOwnerName());
//                    preparedStatement.setString(10, property.getOwnershipType());
//                    preparedStatement.setString(11, property.getSummaryDescription());
//                    preparedStatement.setString(12, property.getPropertyType());
//                    preparedStatement.setString(13, property.getPropertySubType());
//                    preparedStatement.setString(14, property.getTypeofTitleDeed());
//                    preparedStatement.setString(15, property.getStatusofPossession());
//                    preparedStatement.setString(16, property.getAuctionOpenDate());
//                    preparedStatement.setString(17, property.getAuctionCloseDate());
//                    preparedStatement.setString(18, property.getSealedBidLastDate());
//                    preparedStatement.setString(19, property.getSealedBidExtendedDate());
//                    preparedStatement.setString(20, property.getAddress());
//                    preparedStatement.setString(21, property.getNearestAirportRailwayBusStand());
//                    preparedStatement.setString(22, property.getAuthorisedOfficerDetail());
//                    preparedStatement.setString(23, property.getPropertyVisited());
//                    preparedStatement.executeUpdate();
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    
    public void insertRecordIBAPI(List<Properties> propertiesList) throws SQLException {
        System.out.println(INSERT_QUERY);
        // Step 1: Establishing a Connection
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "Steeprise@123");

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
        	
        	int count = 0;

            for (Properties resultData: propertiesList) 
            {
            	preparedStatement.setString(1, resultData.getPropertyId());
            	preparedStatement.setString(2, resultData.getBankName());
            	preparedStatement.setString(3, resultData.getBranchName());
            	preparedStatement.setString(4, resultData.getState());
            	preparedStatement.setString(5, resultData.getDistrict());
            	preparedStatement.setString(6, resultData.geteMDLastDate());
            	preparedStatement.setString(7, resultData.getCity());
            	preparedStatement.setString(8, resultData.getBorrowerName());
            	preparedStatement.setString(9, resultData.getOwnerName());
            	preparedStatement.setString(10, resultData.getOwnershipType());
            	preparedStatement.setString(11, resultData.getSummaryDescription());
                preparedStatement.setString(12, resultData.getPropertyType());
                preparedStatement.setString(13, resultData.getPropertySubType());
            	preparedStatement.setString(14, resultData.getTypeofTitleDeed());
            	preparedStatement.setString(15, resultData.getStatusofPossession());
            	preparedStatement.setString(16, resultData.getAuctionOpenDate());
            	preparedStatement.setString(17, resultData.getAuctionCloseDate());
            	preparedStatement.setString(18, resultData.getSealedBidLastDate());
            	preparedStatement.setString(19, resultData.getSealedBidExtendedDate());
            	preparedStatement.setString(20, resultData.getAddress());
            	preparedStatement.setString(21, resultData.getNearestAirportRailwayBusStand());
            	preparedStatement.setString(22, resultData.getAuthorisedOfficerDetail());
            	preparedStatement.setString(23, resultData.getPropertyVisited());
            	preparedStatement.executeUpdate();
	            
	            preparedStatement.addBatch();
	            
	            count++;
	            // execute every 100 rows or less
	            if (count % 100 == 0 || count == propertiesList.size()) {
	            	preparedStatement.executeBatch();
	            }
            }

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
           // preparedStatement.executeUpdate();
        } catch (SQLException e) {

            // print SQL exception information
            printSQLException(e);
        }

        // Step 4: try-with-resource statement will auto close the connection.
    }
    
    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
