package com.enexse.intranet.ms.accounting.services;

import com.enexse.intranet.ms.accounting.constants.EesTimesheetResponse;
import com.enexse.intranet.ms.accounting.models.EesTimeSheetActivity;
import com.enexse.intranet.ms.accounting.models.EesTimesheetContractHour;
import com.enexse.intranet.ms.accounting.openfeign.EesUserService;
import com.enexse.intranet.ms.accounting.repositories.EesTimesheetContractHourRepository;
import com.enexse.intranet.ms.accounting.request.EesTimesheetContractHourRequest;
import com.enexse.intranet.ms.accounting.response.EesMessageResponse;
import com.enexse.intranet.ms.accounting.response.EesTimeSheetContractHourResponse;
import com.enexse.intranet.ms.accounting.utils.EesCommonUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EesTimesheetContractHourService {

    private EesUserService userService;

    private static char rndChar() {
        int rnd = (int) (Math.random() * 26);
        return (char) ('A' + rnd);
    }

    private EesTimesheetContractHourRepository contractHoursRepository;

    public ResponseEntity<Object> insertContractHours(EesTimesheetContractHourRequest request) {
        String contractHoursCode = EesTimesheetResponse.EES_TIMESHEET_CONTRACTHOURS_PREFIX;
        char ch1 = rndChar();
        char ch2 = rndChar();
        char ch3 = rndChar();
        contractHoursCode+=ch1;
        contractHoursCode+=ch2;
        contractHoursCode+=ch3;

        if (contractHoursCode.trim().isEmpty()) {
            return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_ACTIVITY_CODE_COULD_NOT_BE_NULL), HttpStatus.BAD_REQUEST);
        }

        if (!contractHoursCode.startsWith(EesTimesheetResponse.EES_TIMESHEET_CONTRACTHOURS_PREFIX)) {
            return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_CONTRACTHOURS_CODE_INVALID_FORMAT), HttpStatus.BAD_REQUEST);
        }

        if ((contractHoursRepository.findByCode(contractHoursCode)).isPresent()) {
            return new ResponseEntity<Object>(new EesMessageResponse(String.format(EesTimesheetResponse.EES_TIMESHEET_CONTRACTHOURS_CODE_ALREADY_EXISTS, contractHoursCode)), HttpStatus.BAD_REQUEST);
        }

        Optional<EesTimesheetContractHour> eesTimeSheetContractHours = null;
        eesTimeSheetContractHours = contractHoursRepository.findByContractHours(request.getContractHours());
        if(eesTimeSheetContractHours.isPresent()){
            return new ResponseEntity<Object>(new EesMessageResponse(String.format(EesTimesheetResponse.EES_TIMESHEET_CONTRACTHOURS_ALREADY_EXISTS, request.getContractHours())), HttpStatus.BAD_REQUEST);
        }

        float contractHoursInt = Float.parseFloat(request.getContractHours());
        float contractHoursPerDay = contractHoursInt / 5;

        EesTimesheetContractHour contractHours = EesTimesheetContractHour
                .builder()
                .contractHoursCode(contractHoursCode)
                .contractHours(request.getContractHours())
                .contractHoursDay(contractHoursPerDay)
                .createdAt(EesCommonUtil.generateCurrentDateUtil())
                .updatedAt(EesCommonUtil.generateCurrentDateUtil())
                .userId(request.getUserId())
                .build();
        contractHoursRepository.save(contractHours);
        return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_CONTRACTHOURS_CREATED), HttpStatus.CREATED);
    }

    public List<EesTimeSheetContractHourResponse> getAllTimeSheetContractHours() {
        List<EesTimesheetContractHour> contractHours = contractHoursRepository.findAll()
                .stream().sorted(Comparator.comparing(EesTimesheetContractHour::getCreatedAt).reversed()).collect(Collectors.toList());
        List<EesTimeSheetContractHourResponse> contractHoursResponse = new ArrayList<EesTimeSheetContractHourResponse>();
        contractHours.stream().map(contractHour -> {
                    EesTimeSheetContractHourResponse response = new EesTimeSheetContractHourResponse()
                            .builder()
                            .contractHoursId(contractHour.getContractHoursId())
                            .contractHoursDay(contractHour.getContractHoursDay())
                            .contractHours(contractHour.getContractHours())
                            .contractHoursCode(contractHour.getContractHoursCode())
                            .createdAt(contractHour.getCreatedAt())
                            .updatedAt(contractHour.getUpdatedAt())
                            .createdBy(userService.findById(contractHour.getUserId()))
                            .build();
                    return contractHoursResponse.add(response);
                }
        ).collect(Collectors.toList());
        return contractHoursResponse;
    }

    public ResponseEntity<Object> updateTimeSheetContractHoursByCode(String contractHoursCode, EesTimesheetContractHourRequest request) {
        if (!contractHoursCode.startsWith(EesTimesheetResponse.EES_TIMESHEET_CONTRACTHOURS_PREFIX)) {
            contractHoursCode = EesTimesheetResponse.EES_TIMESHEET_CONTRACTHOURS_PREFIX + contractHoursCode;
        }
        Optional<EesTimesheetContractHour> contractHours = contractHoursRepository.findByCode(contractHoursCode);
        Optional<EesTimesheetContractHour> contract = getTimeSheetContractHoursByCode(contractHoursCode);
        if(Objects.equals(contract.get().getContractHours(), request.getContractHours())){
            return new ResponseEntity<Object>(new EesMessageResponse(String.format(EesTimesheetResponse.EES_TIMESHEET_CONTRACTHOURS_NOTHING_TO_UPDATE)), HttpStatus.BAD_REQUEST);
        }
        Optional<EesTimesheetContractHour> eesTimeSheetContractHours = null;
        eesTimeSheetContractHours = contractHoursRepository.findByContractHours(request.getContractHours());
        if(eesTimeSheetContractHours.isPresent()){
            return new ResponseEntity<Object>(new EesMessageResponse(String.format(EesTimesheetResponse.EES_TIMESHEET_CONTRACTHOURS_ALREADY_EXISTS, request.getContractHours())), HttpStatus.BAD_REQUEST);
        }
        int contractHoursInt = Integer.parseInt(request.getContractHours());
        int contractHoursPerDay = contractHoursInt / 5;
        contractHours.get().setContractHours(request.getContractHours());
        contractHours.get().setContractHoursDay(contractHoursPerDay);
        contractHours.get().setUpdatedAt(EesCommonUtil.generateCurrentDateUtil());
        contractHours.get().setUserId(request.getUserId());
        contractHoursRepository.save(contractHours.get());
        return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_UPDATE_BY_CODE_TIMESHEET_CONTRACTHOURS), HttpStatus.OK);

    }

    public Optional<EesTimesheetContractHour> getTimeSheetContractHoursByCode(String contractHoursCode) {
        if (!contractHoursCode.startsWith(EesTimesheetResponse.EES_TIMESHEET_CONTRACTHOURS_PREFIX)) {
            contractHoursCode = EesTimesheetResponse.EES_TIMESHEET_CONTRACTHOURS_PREFIX + contractHoursCode;
        }
        if ((contractHoursRepository.findByCode(contractHoursCode)).isEmpty()) {
            return null;
        } else {
            return contractHoursRepository.findByCode(contractHoursCode);
        }
    }

    public ResponseEntity<Object> deleteTimeSheetContractHoursByCode(String contractHoursCode) {
        Optional<EesTimesheetContractHour> contractHours = contractHoursRepository.findByCode(contractHoursCode);
        if (contractHours.isEmpty()) {
            return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_CONTRACTHOURS_NOT_FOUND), HttpStatus.NOT_FOUND);
        } else {
            contractHoursRepository.delete(contractHours.get());
            return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_CONTRACTHOURS_DELETED), HttpStatus.OK);
        }
    }


}
