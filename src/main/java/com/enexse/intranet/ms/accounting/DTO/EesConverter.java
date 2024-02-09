package com.enexse.intranet.ms.accounting.DTO;

import com.enexse.intranet.ms.accounting.models.partials.EesCustomer;
import com.enexse.intranet.ms.accounting.models.partials.EesRequest;
import com.enexse.intranet.ms.accounting.models.partials.EesSubRequest;
import com.enexse.intranet.ms.accounting.models.EesTimesheetAppointment;
import com.enexse.intranet.ms.accounting.openfeign.EesCustomerService;
import com.enexse.intranet.ms.accounting.openfeign.EesRequestService;
import com.enexse.intranet.ms.accounting.openfeign.EesSubRequestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@AllArgsConstructor
public class EesConverter {

    EesCustomerService customerService;
    EesRequestService requestService;
    EesSubRequestService subRequestService;

    public EesTimesheetAppointmentDTO mapToDTO(EesTimesheetAppointment appointment) {
        EesTimesheetAppointmentDTO appointmentDTO = new EesTimesheetAppointmentDTO();

        appointmentDTO.setId(appointment.getId());
        appointmentDTO.setUserId(appointment.getUserId());

        // Fetch the related entities (customer and subrequest)
        try {
            if (appointment.getCustomerId() != null) {
                EesCustomer customer =
                        customerService.findById(appointment.getCustomerId());

                // Populate the DTO with the desired fields
                appointmentDTO.setCustomerName(customer.getCustomerTitle());
            }
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        appointmentDTO.setProjectName(appointment.getProjectName());
        try {
            if (appointment.getRequestId() != null && !appointment.getRequestId().isEmpty()) {
                EesRequest request = requestService.findByCode(appointment.getRequestId());
                appointmentDTO.setRequestTitle(request.getRequestTitle());
            } else {
                appointmentDTO.setRequestTitle("No Absence");
            }
        } catch (NullPointerException e) {
            appointmentDTO.setRequestTitle("No Absence");
        }

        try {
            if (appointment.getSubRequestId() != null && !appointment.getSubRequestId().isEmpty()) {
                EesSubRequest subRequest = subRequestService.findById(appointment.getSubRequestId());
                appointmentDTO.setSubRequestTitle(subRequest.getDescription());
            } else {
                appointmentDTO.setSubRequestTitle("No Absence");
            }
        } catch (NullPointerException e) {
            appointmentDTO.setSubRequestTitle("No Absence");
        }

        appointmentDTO.setHoursDay(appointment.getHoursDay());
        appointmentDTO.setActivityName(appointment.getActivity().getActivityDesignation());
        appointmentDTO.setWorkplaceName(appointment.getWorkplace().getWorkPlaceDesignation());
        appointmentDTO.setWorktimeTitle(appointment.getWorkTime().getWorktimeTitle());
        appointmentDTO.setStartDateTime(appointment.getStartDateTime());
        appointmentDTO.setEndDateTime(appointment.getEndDateTime());
        appointmentDTO.setCreatedAt(appointment.getCreatedAt());
        appointmentDTO.setUpdatedAt(appointment.getUpdatedAt());

        return appointmentDTO;
    }
}
