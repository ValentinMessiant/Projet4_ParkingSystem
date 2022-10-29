package com.parkit.parkingsystem.util;

import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;

public class UserUtils {
	public boolean isRecurringUser(String vehicleRegNumber) {
		TicketDAO ticketDAO = new TicketDAO();
		boolean isTicketExist = false;
		try {
            Ticket ticket = ticketDAO.getExistingTicket(vehicleRegNumber);
            if(ticket != null) {
            	isTicketExist = true;
            }
		} catch(Exception e){
			System.out.println("Error fetching the existing ticket"+e);
		}
		return isTicketExist;
	}
}
