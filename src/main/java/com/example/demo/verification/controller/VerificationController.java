package com.example.demo.verification.controller;

import com.example.demo.subscription.service.SubscriptionService;
import com.example.demo.url.dto.URLStatusDTO;
import com.example.demo.verification.service.VerificationService;
import com.example.demo.verification.dto.ResultDto;
import com.example.demo.verification.dto.SMSDto;
import com.example.demo.web_risk.dto.ResponseDto;
import com.example.demo.web_risk.enums.ThreatType;
import com.example.demo.web_risk.dto.URIThreatDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class VerificationController {

    @Value("${service.number}")
    private long serviceNumber;
    @Value("${service.url}")
    private String serviceURL;

    private static final String START_MESSAGE = "START";
    private static final String STOP_MESSAGE = "STOP";

    private static final String URL_REGEX = ".*(https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6})\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*).*";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    private static final RestTemplate restTemplate = new RestTemplate();

    private final VerificationService verificationService;
    private final SubscriptionService subscriptionService;

    public VerificationController(VerificationService verificationService, SubscriptionService subscriptionService) {
        this.verificationService = verificationService;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/test")
    public String test(){
        return "works!";
    }

    @PostMapping("/verify")
    public ResponseEntity<ResultDto> verifySMS(@RequestBody SMSDto smsDto) throws URISyntaxException {
        if(smsDto.getRecipient() == serviceNumber){
            String message = smsDto.getMessage().toUpperCase();
            if(message.equals(START_MESSAGE)){
                subscriptionService.subscribe(smsDto.getSender());
                return new ResponseEntity<>(new ResultDto(false, "Subscription activated"), HttpStatus.OK);
            }else if(message.equals(STOP_MESSAGE)){
                subscriptionService.unsubscribe(smsDto.getSender());
                return new ResponseEntity<>(new ResultDto(false, "Subscription deactivated"), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ResultDto(false, "Valid messages are only START/STOP"), HttpStatus.BAD_REQUEST);
        }else{
            if(!subscriptionService.isActiveSubscription(smsDto.getRecipient())){
                return new ResponseEntity<>(new ResultDto(false, "No active subscription"), HttpStatus.BAD_REQUEST);
            }
            Matcher matcher = URL_PATTERN.matcher(smsDto.getMessage());
            if(matcher.matches()){ // message contains URL
                String url = matcher.group(1);
                URLStatusDTO urlStatus = verificationService.getURLStatus(url.toLowerCase());
                System.out.println(String.format("url %s blacklisted: %s, known: %s", urlStatus.url(), urlStatus.blacklisted(), urlStatus.known()));
                if(urlStatus.known()){
                    return new ResponseEntity<>(urlStatus.blacklisted()? new ResultDto(true, "BLACKLISTED") : new ResultDto(false, "OK"), HttpStatus.OK);
                }else {
                    HttpEntity<URIThreatDto> request = new HttpEntity<>(
                            new URIThreatDto(url, new ThreatType[]{ThreatType.SOCIAL_ENGINEERING}, true));
                    ResponseEntity<ResponseDto> result = restTemplate.exchange(serviceURL, HttpMethod.POST, request, ResponseDto.class);
                    boolean whitelisted = !Arrays.stream(result.getBody().getScores()).noneMatch(score -> score.isHighConfidenceLevel());
                    verificationService.saveURLStatus(url, !whitelisted);
                    return new ResponseEntity<>(whitelisted? new ResultDto(false, "OK") : new ResultDto(false, "BLACKLISTED"), HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(new ResultDto(false, "Message contains no URL"), HttpStatus.OK);
            }
        }
    }
}
